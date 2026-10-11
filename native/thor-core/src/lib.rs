pub mod config;
pub mod engine;

use anyhow::{bail, Result};
use jni::errors::{Result as JniResult, ThrowRuntimeExAndDefault};
use jni::objects::{JByteArray, JClass, JString};
use jni::strings::JNIString;
use jni::sys::{jboolean, jbyteArray, jint, jlong, JNI_FALSE, JNI_TRUE};
use jni::{jni_str, Env, EnvUnowned};
use zenoh::key_expr::KeyExpr;

pub const MAX_PAYLOAD_BYTES: usize = 1_048_576;

pub fn validate_key(key: &str, allow_wildcards: bool) -> Result<()> {
    if key.len() > 256 || !key.starts_with("thor/") {
        bail!("Zenoh key must belong to the bounded thor/ namespace");
    }
    if !allow_wildcards && key.contains('*') {
        bail!("Publications require an exact non-wildcard Zenoh key");
    }
    KeyExpr::try_from(key).map_err(|_| anyhow::anyhow!("Invalid Zenoh key expression"))?;
    Ok(())
}

pub fn validate_payload(payload: &[u8]) -> Result<()> {
    if payload.is_empty() || payload.len() > MAX_PAYLOAD_BYTES {
        bail!("Payload must contain 1..1048576 bytes");
    }
    Ok(())
}

fn report_error(env: &mut Env<'_>) -> JniResult<()> {
    // Do not expose TLS certificate paths, session identifiers or remote responses.
    env.throw_new(
        jni_str!("java/io/IOException"),
        JNIString::new("THOR native operation denied; inspect bounded diagnostics"),
    )
}

#[unsafe(no_mangle)]
pub extern "system" fn Java_com_newoether_agora_thor_nativecore_ThorNativeCoreBridge_nativeOpen<'local>(
    mut unowned: EnvUnowned<'local>,
    _class: JClass<'local>,
    endpoint: JString<'local>,
    ca_file: JString<'local>,
    client_certificate: JString<'local>,
    client_key: JString<'local>,
) -> jlong {
    unowned
        .with_env(|env| -> JniResult<jlong> {
            let endpoint = endpoint.try_to_string(env)?;
            let ca_file = ca_file.try_to_string(env)?;
            let client_certificate = client_certificate.try_to_string(env)?;
            let client_key = client_key.try_to_string(env)?;
            let operation = (|| {
                let peer = config::SecurePeer::new(
                    &endpoint,
                    &ca_file,
                    &client_certificate,
                    &client_key,
                )?;
                engine::instance()?.connect(peer)
            })();
            match operation {
                Ok(id) => Ok(id as jlong),
                Err(_) => {
                    report_error(env)?;
                    Ok(0)
                }
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
}

#[unsafe(no_mangle)]
pub extern "system" fn Java_com_newoether_agora_thor_nativecore_ThorNativeCoreBridge_nativePublish<'local>(
    mut unowned: EnvUnowned<'local>,
    _class: JClass<'local>,
    handle: jlong,
    key: JString<'local>,
    payload: JByteArray<'local>,
) -> jboolean {
    unowned
        .with_env(|env| -> JniResult<jboolean> {
            let key = key.try_to_string(env)?;
            if payload.len(env)? > MAX_PAYLOAD_BYTES {
                report_error(env)?;
                return Ok(JNI_FALSE);
            }
            let bytes = env.convert_byte_array(&payload)?;
            match engine::instance().and_then(|core| core.publish(handle as u64, &key, &bytes)) {
                Ok(()) => Ok(JNI_TRUE),
                Err(_) => {
                    report_error(env)?;
                    Ok(JNI_FALSE)
                }
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
}

#[unsafe(no_mangle)]
pub extern "system" fn Java_com_newoether_agora_thor_nativecore_ThorNativeCoreBridge_nativeSubscribe<'local>(
    mut unowned: EnvUnowned<'local>,
    _class: JClass<'local>,
    handle: jlong,
    key: JString<'local>,
) -> jlong {
    unowned
        .with_env(|env| -> JniResult<jlong> {
            let key = key.try_to_string(env)?;
            match engine::instance().and_then(|core| core.subscribe(handle as u64, &key)) {
                Ok(id) => Ok(id as jlong),
                Err(_) => {
                    report_error(env)?;
                    Ok(0)
                }
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
}

#[unsafe(no_mangle)]
pub extern "system" fn Java_com_newoether_agora_thor_nativecore_ThorNativeCoreBridge_nativePoll<'local>(
    mut unowned: EnvUnowned<'local>,
    _class: JClass<'local>,
    subscription: jlong,
    timeout_ms: jint,
) -> jbyteArray {
    unowned
        .with_env(|env| -> JniResult<jbyteArray> {
            let operation = if timeout_ms < 0 || timeout_ms > 1000 {
                Err(anyhow::anyhow!("Invalid subscriber poll timeout"))
            } else {
                engine::instance().and_then(|core| core.poll(subscription as u64, timeout_ms as u64))
            };
            match operation {
                Ok(Some(payload)) => {
                    let array = env.byte_array_from_slice(&payload)?;
                    Ok(array.into_raw() as jbyteArray)
                }
                Ok(None) => Ok(std::ptr::null_mut()),
                Err(_) => {
                    report_error(env)?;
                    Ok(std::ptr::null_mut())
                }
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
}

#[unsafe(no_mangle)]
pub extern "system" fn Java_com_newoether_agora_thor_nativecore_ThorNativeCoreBridge_nativeUnsubscribe<'local>(
    mut unowned: EnvUnowned<'local>,
    _class: JClass<'local>,
    subscription: jlong,
) {
    unowned
        .with_env(|env| -> JniResult<()> {
            if engine::instance().and_then(|core| core.unsubscribe(subscription as u64)).is_err() {
                report_error(env)?;
            }
            Ok(())
        })
        .resolve::<ThrowRuntimeExAndDefault>()
}

#[unsafe(no_mangle)]
pub extern "system" fn Java_com_newoether_agora_thor_nativecore_ThorNativeCoreBridge_nativeClose<'local>(
    mut unowned: EnvUnowned<'local>,
    _class: JClass<'local>,
    handle: jlong,
) {
    unowned
        .with_env(|env| -> JniResult<()> {
            if engine::instance().and_then(|core| core.disconnect(handle as u64)).is_err() {
                report_error(env)?;
            }
            Ok(())
        })
        .resolve::<ThrowRuntimeExAndDefault>()
}

#[unsafe(no_mangle)]
pub extern "system" fn Java_com_newoether_agora_thor_nativecore_ThorNativeCoreBridge_nativeCounters<'local>(
    mut unowned: EnvUnowned<'local>,
    _class: JClass<'local>,
) -> jbyteArray {
    unowned
        .with_env(|env| -> JniResult<jbyteArray> {
            match engine::instance() {
                Ok(core) => {
                    let (sent, received, dropped) = core.counters();
                    let json = serde_json::json!({
                        "sent": sent,
                        "received": received,
                        "dropped": dropped,
                    })
                    .to_string();
                    Ok(env.byte_array_from_slice(json.as_bytes())?.into_raw() as jbyteArray)
                }
                Err(_) => {
                    report_error(env)?;
                    Ok(std::ptr::null_mut())
                }
            }
        })
        .resolve::<ThrowRuntimeExAndDefault>()
}
