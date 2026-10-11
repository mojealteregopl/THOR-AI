use thor_nativecore::config::SecurePeer;

#[test]
fn refuses_insecure_endpoints_and_missing_certificates() {
    assert!(SecurePeer::new("tcp/example.org:7447", "/bad/ca", "/bad/client", "/bad/key").is_err());
    assert!(SecurePeer::new("tls/example.org:7447", "", "", "").is_err());
    assert!(SecurePeer::new("tls/example.org:0", "/bad/ca", "/bad/client", "/bad/key").is_err());
    assert!(SecurePeer::new("tls/example.org:65536", "/bad/ca", "/bad/client", "/bad/key").is_err());
}

#[test]
fn secure_config_forbids_cleartext_transport_and_discovery() {
    let root = std::env::temp_dir().join(format!(
        "thor-native-{}-{}",
        std::process::id(),
        std::thread::current().name().unwrap_or("test")
    ));
    std::fs::create_dir_all(&root).unwrap();
    let ca = root.join("ca.pem");
    let cert = root.join("client.pem");
    let key = root.join("client.key");
    for path in [&ca, &cert, &key] {
        std::fs::write(path, "test-credential").unwrap();
    }
    let peer = SecurePeer::new(
        "tls/example.org:7447",
        ca.to_str().unwrap(),
        cert.to_str().unwrap(),
        key.to_str().unwrap(),
    )
    .unwrap();
    let config = peer.to_config().unwrap();
    assert_eq!(config.get_json("transport/link/tls/enable_mtls").unwrap(), "true");
    assert_eq!(
        config.get_json("transport/link/tls/verify_name_on_connect").unwrap(),
        "true"
    );
    assert_eq!(config.get_json("scouting/multicast/enabled").unwrap(), "false");
    assert!(peer.to_config().is_ok());
    std::fs::remove_dir_all(root).unwrap();
}

#[test]
fn refuses_wildcards_and_oversized_payloads() {
    assert!(thor_nativecore::validate_key("thor/agent/output", false).is_ok());
    assert!(thor_nativecore::validate_key("thor/**", false).is_err());
    assert!(thor_nativecore::validate_key("thor/**", true).is_ok());
    assert!(thor_nativecore::validate_payload(&vec![42; 1024]).is_ok());
    assert!(thor_nativecore::validate_payload(&vec![42; 1_048_577]).is_err());
}
