use anyhow::{anyhow, bail, Context, Result};
use std::collections::HashMap;
use std::sync::atomic::{AtomicU64, Ordering};
use std::sync::{mpsc, Arc, Mutex, OnceLock};
use std::time::Duration;
use tokio::runtime::Runtime;
use zenoh::key_expr::KeyExpr;
use zenoh::Session;

use crate::config::SecurePeer;
use crate::{validate_key, validate_payload, MAX_PAYLOAD_BYTES};

const MAX_ACTIVE_SESSIONS: usize = 8;
const MAX_SUBSCRIPTIONS: usize = 64;
const SUBSCRIPTION_QUEUE: usize = 32;
const CONNECT_TIMEOUT: Duration = Duration::from_secs(15);
const SEND_TIMEOUT: Duration = Duration::from_secs(5);

struct Subscription {
    session_id: u64,
    receiver: Mutex<mpsc::Receiver<Vec<u8>>>,
    task: tokio::task::JoinHandle<()>,
}

#[derive(Default)]
pub struct Counters {
    sent: AtomicU64,
    received: AtomicU64,
    dropped: Arc<AtomicU64>,
}

impl Counters {
    pub fn snapshot(&self) -> (u64, u64, u64) {
        (
            self.sent.load(Ordering::Relaxed),
            self.received.load(Ordering::Relaxed),
            self.dropped.load(Ordering::Relaxed),
        )
    }
}

/// Owns transport independently of JNI stack frames. The global mutex is never held
/// while awaiting a Zenoh network operation.
pub struct NativeCoreEngine {
    runtime: Runtime,
    next_id: AtomicU64,
    sessions: Mutex<HashMap<u64, Session>>,
    subscriptions: Mutex<HashMap<u64, Subscription>>,
    metrics: Counters,
}

impl NativeCoreEngine {
    fn create() -> Result<Self> {
        let runtime = tokio::runtime::Builder::new_multi_thread()
            .worker_threads(2)
            .thread_name("thor-zenoh")
            .enable_all()
            .build()
            .context("Unable to initialize the THOR Tokio executor")?;
        Ok(Self {
            runtime,
            next_id: AtomicU64::new(1),
            sessions: Mutex::new(HashMap::new()),
            subscriptions: Mutex::new(HashMap::new()),
            metrics: Counters::default(),
        })
    }

    fn allocate_id(&self) -> Result<u64> {
        let id = self.next_id.fetch_add(1, Ordering::Relaxed);
        if id == 0 || id > i64::MAX as u64 {
            bail!("THOR handle space exhausted");
        }
        Ok(id)
    }

    pub fn connect(&self, peer: SecurePeer) -> Result<u64> {
        {
            let sessions = self.sessions.lock().map_err(|_| anyhow!("Session registry poisoned"))?;
            if sessions.len() >= MAX_ACTIVE_SESSIONS {
                bail!("THOR session capacity reached");
            }
        }

        let config = peer.to_config()?;
        let session = self.runtime.block_on(async {
            tokio::time::timeout(CONNECT_TIMEOUT, zenoh::open(config))
                .await
                .context("Secure Zenoh handshake exceeded 15 seconds")?
                .map_err(|_| anyhow!("Secure Zenoh handshake failed"))
        })?;

        let id = self.allocate_id()?;
        let mut sessions = self.sessions.lock().map_err(|_| anyhow!("Session registry poisoned"))?;
        if sessions.len() >= MAX_ACTIVE_SESSIONS {
            drop(sessions);
            let _ = self.runtime.block_on(async { session.close().await });
            bail!("THOR session capacity reached during handshake");
        }
        sessions.insert(id, session);
        Ok(id)
    }

    fn session(&self, id: u64) -> Result<Session> {
        if id == 0 {
            bail!("Zero is not a valid THOR session handle");
        }
        let sessions = self.sessions.lock().map_err(|_| anyhow!("Session registry poisoned"))?;
        let session = sessions.get(&id).context("Unknown THOR session handle")?;
        if session.is_closed() {
            bail!("THOR session is already closed");
        }
        Ok(session.clone())
    }

    pub fn publish(&self, id: u64, key: &str, bytes: &[u8]) -> Result<()> {
        validate_key(key, false)?;
        validate_payload(bytes)?;
        let session = self.session(id)?;
        let address = KeyExpr::try_from(key.to_owned())
            .map_err(|_| anyhow!("Invalid Zenoh publication key"))?;
        self.runtime.block_on(async {
            tokio::time::timeout(SEND_TIMEOUT, session.put(address, bytes.to_vec()))
                .await
                .context("Zenoh publication timed out")?
                .map_err(|_| anyhow!("Zenoh publication failed"))
        })?;
        self.metrics.sent.fetch_add(1, Ordering::Relaxed);
        Ok(())
    }

    pub fn subscribe(&self, id: u64, key: &str) -> Result<u64> {
        validate_key(key, true)?;
        {
            let active = self.subscriptions.lock().map_err(|_| anyhow!("Subscriber registry poisoned"))?;
            if active.len() >= MAX_SUBSCRIPTIONS {
                bail!("THOR subscriber capacity reached");
            }
        }
        let session = self.session(id)?;
        let address = KeyExpr::try_from(key.to_owned())
            .map_err(|_| anyhow!("Invalid Zenoh subscriber key"))?;
        let subscriber = self.runtime.block_on(async {
            tokio::time::timeout(SEND_TIMEOUT, session.declare_subscriber(address))
                .await
                .context("Zenoh subscription timed out")?
                .map_err(|_| anyhow!("Zenoh subscription failed"))
        })?;
        let (sender, receiver) = mpsc::sync_channel(SUBSCRIPTION_QUEUE);
        let dropped = Arc::clone(&self.metrics.dropped);
        let id_sub = self.allocate_id()?;
        let task = self.runtime.spawn(async move {
            while let Ok(sample) = subscriber.recv_async().await {
                let payload = sample.payload().to_bytes();
                if payload.is_empty() || payload.len() > MAX_PAYLOAD_BYTES {
                    dropped.fetch_add(1, Ordering::Relaxed);
                    continue;
                }
                match sender.try_send(payload.into_owned()) {
                    Ok(()) => {}
                    Err(mpsc::TrySendError::Full(_)) => {
                        dropped.fetch_add(1, Ordering::Relaxed);
                    }
                    Err(mpsc::TrySendError::Disconnected(_)) => break,
                }
            }
        });
        let mut subscriptions = self.subscriptions.lock()
            .map_err(|_| anyhow!("Subscriber registry poisoned"))?;
        if subscriptions.len() >= MAX_SUBSCRIPTIONS {
            task.abort();
            bail!("THOR subscriber capacity reached during registration");
        }
        subscriptions.insert(id_sub, Subscription {
            session_id: id,
            receiver: Mutex::new(receiver),
            task,
        });
        Ok(id_sub)
    }

    pub fn poll(&self, subscription_id: u64, timeout_ms: u64) -> Result<Option<Vec<u8>>> {
        if timeout_ms > 1_000 {
            bail!("THOR poll timeout must not exceed 1000 ms");
        }
        let subscriptions = self.subscriptions.lock()
            .map_err(|_| anyhow!("Subscriber registry poisoned"))?;
        let subscription = subscriptions.get(&subscription_id)
            .context("Unknown THOR subscription handle")?;
        let receiver = subscription.receiver.lock()
            .map_err(|_| anyhow!("Subscriber queue poisoned"))?;
        let event = receiver.recv_timeout(Duration::from_millis(timeout_ms));
        match event {
            Ok(value) => {
                self.metrics.received.fetch_add(1, Ordering::Relaxed);
                Ok(Some(value))
            }
            Err(mpsc::RecvTimeoutError::Timeout) => Ok(None),
            Err(mpsc::RecvTimeoutError::Disconnected) => {
                bail!("Zenoh subscriber has disconnected")
            }
        }
    }

    pub fn unsubscribe(&self, subscription_id: u64) -> Result<()> {
        let mut subscriptions = self.subscriptions.lock()
            .map_err(|_| anyhow!("Subscriber registry poisoned"))?;
        let subscription = subscriptions.remove(&subscription_id)
            .context("Unknown THOR subscription handle")?;
        subscription.task.abort();
        Ok(())
    }

    pub fn disconnect(&self, id: u64) -> Result<()> {
        {
            let mut subscriptions = self.subscriptions.lock()
                .map_err(|_| anyhow!("Subscriber registry poisoned"))?;
            subscriptions.retain(|_, entry| {
                if entry.session_id == id {
                    entry.task.abort();
                    false
                } else {
                    true
                }
            });
        }
        let session = self.sessions.lock()
            .map_err(|_| anyhow!("Session registry poisoned"))?
            .remove(&id)
            .context("Unknown THOR session handle")?;
        self.runtime.block_on(async {
            tokio::time::timeout(SEND_TIMEOUT, session.close())
                .await
                .context("Zenoh disconnect timed out")?
                .map_err(|_| anyhow!("Zenoh disconnect failed"))
        })
    }

    pub fn counters(&self) -> (u64, u64, u64) {
        self.metrics.snapshot()
    }
}

static ENGINE: OnceLock<Result<NativeCoreEngine, String>> = OnceLock::new();

pub fn instance() -> Result<&'static NativeCoreEngine> {
    match ENGINE.get_or_init(|| NativeCoreEngine::create().map_err(|err| err.to_string())) {
        Ok(engine) => Ok(engine),
        Err(message) => bail!("THOR native core initialization failed: {message}"),
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn invalid_handles_fail_closed() {
        let engine = NativeCoreEngine::create().unwrap();
        assert!(engine.publish(0, "thor/test", b"payload").is_err());
        assert!(engine.subscribe(123, "thor/**").is_err());
        assert!(engine.poll(999, 0).is_err());
        assert!(engine.poll(999, 1001).is_err());
        assert!(engine.disconnect(123).is_err());
        assert!(engine.unsubscribe(123).is_err());
        assert_eq!(engine.counters(), (0, 0, 0));
    }

    #[test]
    fn ids_are_monotonic_and_nonzero() {
        let engine = NativeCoreEngine::create().unwrap();
        assert_eq!(engine.allocate_id().unwrap(), 1);
        assert_eq!(engine.allocate_id().unwrap(), 2);
    }
}
