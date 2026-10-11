use anyhow::{anyhow, bail, Context, Result};
use std::fs;
use std::path::Path;
use zenoh::config::Config;

const MAX_CREDENTIAL_BYTES: u64 = 1024 * 1024;
const MAX_ENDPOINT_LENGTH: usize = 260;

/// Client-only mTLS configuration. Secrets are never serialized into source or log messages.
pub struct SecurePeer {
    endpoint: String,
    ca_file: String,
    client_certificate: String,
    client_key: String,
}

impl SecurePeer {
    pub fn new(endpoint: &str, ca_file: &str, certificate: &str, key: &str) -> Result<Self> {
        if !endpoint.starts_with("tls/") || endpoint.len() > MAX_ENDPOINT_LENGTH {
            bail!("THOR native transport requires a bounded tls/host:port endpoint");
        }
        let address = &endpoint[4..];
        let (host, port) = address
            .rsplit_once(':')
            .context("TLS endpoint must specify a port")?;
        if host.is_empty()
            || !host.bytes().all(|b| b.is_ascii_alphanumeric() || b == b'.' || b == b'-')
            || host.starts_with('-')
            || host.ends_with('-')
            || host.contains("..")
        {
            bail!("TLS endpoint contains an invalid DNS name or IPv4 address");
        }
        if port.parse::<u16>().ok().filter(|n| *n > 0).is_none() {
            bail!("TLS endpoint port is outside the allowed range");
        }
        validate_credential_path(ca_file)?;
        validate_credential_path(certificate)?;
        validate_credential_path(key)?;

        Ok(Self {
            endpoint: endpoint.to_owned(),
            ca_file: ca_file.to_owned(),
            client_certificate: certificate.to_owned(),
            client_key: key.to_owned(),
        })
    }

    pub fn to_config(&self) -> Result<Config> {
        let mut config = Config::default();
        insert_config(&mut config, "mode", r#""client""#)?;
        insert_config(&mut config, 
            "connect/endpoints",
            &serde_json::to_string(&vec![&self.endpoint])?,
        )?;
        // No UDP, TCP, WS or implicit insecure discovery. Only authenticated TLS.
        insert_config(&mut config, "scouting/multicast/enabled", "false")?;
        insert_config(&mut config, "scouting/gossip/enabled", "false")?;
        insert_config(&mut config, "transport/link/protocols", r#"["tls"]"#)?;
        insert_config(&mut config, "transport/link/tls/enable_mtls", "true")?;
        insert_config(&mut config, "transport/link/tls/verify_name_on_connect", "true")?;
        insert_config(&mut config, "transport/link/tls/close_link_on_expiration", "true")?;
        insert_config(&mut config, 
            "transport/link/tls/root_ca_certificate",
            &serde_json::to_string(&self.ca_file)?,
        )?;
        insert_config(&mut config, 
            "transport/link/tls/connect_certificate",
            &serde_json::to_string(&self.client_certificate)?,
        )?;
        insert_config(&mut config, 
            "transport/link/tls/connect_private_key",
            &serde_json::to_string(&self.client_key)?,
        )?;
        Ok(config)
    }
}

fn validate_credential_path(path: &str) -> Result<()> {
    if path.is_empty() || path.len() > 1024 || path.contains('\0') {
        bail!("TLS credential path must be non-empty and bounded");
    }
    let file = Path::new(path);
    if !file.is_absolute() {
        bail!("TLS credential must be an absolute file path");
    }
    let metadata = fs::symlink_metadata(file).context("TLS credential is unavailable")?;
    if !metadata.file_type().is_file()
        || metadata.len() == 0
        || metadata.len() > MAX_CREDENTIAL_BYTES
    {
        bail!("TLS credential must be a regular non-empty file under 1 MiB");
    }
    Ok(())
}

fn insert_config(config: &mut Config, field: &str, value: &str) -> Result<()> {
    config.insert_json5(field, value).map_err(|_| anyhow!("Zenoh rejected TLS configuration field: {field}"))
}
