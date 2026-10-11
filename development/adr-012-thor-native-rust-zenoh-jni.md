# ADR-012 — THOR Punkt 1: natywny rdzeń Rust / Zenoh / JNI

**Data:** 2026-10-12
**Gałąź:** `omega/thor-agent-swarm`
**Status:** kod rdzenia Rust, most JNI, klient Kotlin i testy kontraktowe dodane. Udane `cargo test`, połączenie mTLS i kompilacja arm64 wymagają potwierdzenia w CI i środowisku fizycznym.
**Źródło:** dostarczony raport „Kompleksowa Architektura i Implementacja Punktu 1: Warstwa Rdzenia Natywnego i Bezpieczeństwa w Ekosystemie Rust i Eclipse Zenoh”.

## Zakres rzeczywistego wdrożenia

| Składnik | Plik | Funkcjonalność i granica |
|---|---|---|
| Biblioteka natywna | `native/thor-core/Cargo.toml` | `cdylib` / `rlib`, Zenoh 1.10.1, JNI 0.22.4, Tokio; ograniczone funkcje TLS |
| Konfiguracja | `native/thor-core/src/config.rs` | Tylko `tls/host:port`, klient mTLS, weryfikacja nazwy, CA + certyfikat + klucz jako pliki absolutne; bez plaintext i multicast discovery |
| Zarządzanie transportem | `native/thor-core/src/engine.rs` | Limit sesji, subskrypcji, asynchroniczny odbiór, ograniczona kolejka, obsługa błędów, timeout i jawne zamknięcie |
| ABI JNI | `native/thor-core/src/lib.rs` | Statyczne, przewidywalne eksporty `nativeOpen`, `nativePublish`, `nativeSubscribe`, `nativePoll`, `nativeUnsubscribe`, `nativeClose`, `nativeCounters`; błędy Java `IOException` |
| Bridge JVM | `app/src/main/java/com/newoether/agora/thor/nativecore/ThorNativeCoreBridge.java` | Zgodne nazwy symboli JNI; brak zdalnych uprawnień na podstawie samej nazwy klasy |
| Kotlin | `app/src/main/java/com/newoether/agora/thor/nativecore/ThorZenohClient.kt` | `suspend` API na `Dispatchers.IO`, limity payloadu, jawne zamknięcie, wskaźniki transmisji |
| Testy kontraktowe | `native/thor-core/tests/security_contract.rs` | Odrzucenie plaintext, błędnych portów, braku plików TLS, wildcard publish, oversize |
| Build natywny | `scripts/build-thor-native.sh` | Jawny proces `cargo ndk` arm64 (NDK 28.2.13676358); nie buduje APK |
| CI | `.github/workflows/build.yml` | Osobny `native` job uruchamia `cargo test` i `cargo build --release`; wydanie APK pozostaje ręcznie ograniczone do `master` |

## Przykładowy kontrakt systemu

```text
Android Kotlin / user-authorized operation
    ↓ Dispatchers.IO
ThorZenohClient
    ↓ JNI (typed Java static native methods)
Rust FFI guards (jni::EnvUnowned, panic conversion, max 1 MiB)
    ↓
NativeCoreEngine (bounded handles, worker pool, session registry)
    ↓
Zenoh 1.10.1 transport_tls + mTLS + server-name verification
    ↓
Trusted, separately provisioned Zenoh router
```

Funkcje nie są automatycznie aktywowane przez indeks katalogu agentów ani przez niezaufane dane wejściowe. `libthor_nativecore.so` musi zostać jawnie skompilowana i umieszczona w prywatnym pakiecie aplikacji; przy braku biblioteki `System.loadLibrary` odrzuca wywołanie, co zapobiega przejściu na transport niezabezpieczony.

## Kluczowe korekty fragmentów raportu

1. **Config TLS 1.10.1:** aktualny schemat Zenoh używa `transport/link/tls/connect_certificate`, `connect_private_key`, `root_ca_certificate` i `verify_name_on_connect`; fragment raportu podaje nieistniejące w tym schemacie `client_certificate` i `client_private_key`. W kodzie stosujemy wariant aktualnej konfiguracji Zenoh.
2. **JNI 0.22:** `JNIEnv` jest aliasem deprecjonowanym, poprawny ABI przyjmuje `EnvUnowned`; dostęp do JNI odbywa się przez `with_env` wraz z obsługą panik bez przechodzenia przez granicę FFI.
3. **Brak „zero-copy JNI”:** `byte[]` przechodzi przez kopię do `Vec<u8>`; Zenoh `ZBytes` nie gwarantuje, że pamięć JVM będzie żyła dłużej niż natywne wywołanie. SHM jest oddzielną optymalizacją po testach.
4. **Brak absolutnej ochrony:** mTLS zapewnia uwierzytelnianie transportu przy prawidłowym wdrożeniu kluczy i CA. Nie chroni automatycznie przed przejęciem Android OS, złośliwym certyfikowanym węzłem, injection w treści payloadu, kompromitacją klucza ani błędem polityki.
5. **Dynamiczna rejestracja JNI:** ukrycie symboli nie jest wiarygodnym zabezpieczeniem przed reverse engineering; w tym etapie użyto jawnych symboli, przejrzystych dla audytu.
6. **BFT / PQC / TEE:** nie są dostarczane przez samą bibliotekę Zenoh 1.10.1. Nie wolno deklarować konsensusu, ochrony postkwantowej ani TEE, dopóki nie istnieją osobne wdrożenie, threat model i testy.
7. **Storage Manager:** jest mechanizmem routera/usługi infrastrukturalnej, nie zaszyfrowanym WAL Android. Wysłanie `session.put` nie oznacza trwałego potwierdzenia, exactly-once ani czasu odtworzenia.
8. **Zenoh no_std / WASM:** to odrębne targety i warianty API, nie bezpośrednia translacja Tokio/JNI na mikrokontroler. W punkcie 1 implementowany jest Android arm64 i build host Linux.
9. **Telemetria:** liczniki `sent/received/dropped` są lokalne i jawnie mierzą operacje API; nie stanowią zaimplementowanego eksportera OpenMetrics. Numery z tabeli raportu to przykład, nie wyniki benchmarku THOR.

## Warunki odbioru Punktu 1

| Dowód | Status |
|---|---|
| `cargo test --manifest-path native/thor-core/Cargo.toml --all-targets` | Weryfikacja CI |
| `cargo build --manifest-path native/thor-core/Cargo.toml --release` | Weryfikacja CI |
| `scripts/build-thor-native.sh` + test loadLibrary na Android arm64 | Niepotwierdzone |
| Połączenie z rzeczywistym routerem Zenoh mTLS i odmowa bez klienta cert | Niepotwierdzone |
| Testy utraty łączności / backpressure / timeout / ponownego łączenia | Niepotwierdzone |
| Polityka zaufania i rotacja kluczy + audyt zewnętrzny | Niepotwierdzone |
| Dodatkowe opcje SHM / storage plugin / OpenMetrics / embedded targets | Poza tym etapem |

**Zakaz oznaczenia Punktu 1 jako ukończonego przed przejściem build, JNI smoke test i testem rzeczywistego mTLS.** W gałęzi nie powstaje końcowy APK — aktualna praca dotyczy rdzenia i testów.
