#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
MANIFEST="$ROOT/native/thor-core/Cargo.toml"
OUT="$ROOT/app/src/main/jniLibs"
NDK_VERSION="28.2.13676358"

if ! command -v cargo >/dev/null 2>&1; then
  echo "Rust Cargo is required." >&2
  exit 2
fi
if ! cargo ndk --help >/dev/null 2>&1; then
  echo "Install cargo-ndk: cargo install cargo-ndk --locked" >&2
  exit 2
fi
if ! rustup target list --installed | grep -qx 'aarch64-linux-android'; then
  echo "Install the Rust Android target: rustup target add aarch64-linux-android" >&2
  exit 2
fi
if [[ -z "${ANDROID_NDK_HOME:-}" ]]; then
  if [[ -z "${ANDROID_HOME:-}" ]]; then
    echo "ANDROID_NDK_HOME or ANDROID_HOME must be configured." >&2
    exit 2
  fi
  export ANDROID_NDK_HOME="$ANDROID_HOME/ndk/$NDK_VERSION"
fi
if [[ ! -d "$ANDROID_NDK_HOME" ]]; then
  echo "Android NDK not found: $ANDROID_NDK_HOME" >&2
  exit 2
fi

cargo fmt --manifest-path "$MANIFEST" --all --check
cargo test --manifest-path "$MANIFEST" --all-targets
cargo ndk -t arm64-v8a -o "$OUT" build --manifest-path "$MANIFEST" --release

if [[ ! -s "$OUT/arm64-v8a/libthor_nativecore.so" ]]; then
  echo "Rust native library was not generated." >&2
  exit 1
fi

echo "Native arm64 library built: $OUT/arm64-v8a/libthor_nativecore.so"
echo "This does not create or publish an APK."
