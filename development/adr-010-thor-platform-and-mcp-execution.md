# THOR AI — ADR-010: weryfikowalny rdzeń, granice wykonania i wymagania Nordyckiego UI

**Data:** 2026-10-10  
**Status:** przyjęto kierunek architektoniczny, wdrożono etap bezpieczeństwa MCP; pozostałe elementy weryfikować oddzielnie.  
**Podstawa:** „Raport Badawczy: Architektura Systemu Thor AI — Wieloagentowy Ekosystem z Interfejsem Nordyckim”, przekazany przez użytkownika.

## 1. Ustalenie źródłowe a stan faktyczny

Raport stanowi **specyfikację docelową i przykłady koncepcyjne**, nie dowód kompilacji ani działającej infrastruktury.

| Zagadnienie z raportu | Kierunek docelowy | Co naprawdę istnieje w aktualnym repozytorium | Wymagana weryfikacja |
|---|---|---|---|
| Główny Agent Thor | Rozdzielony control plane i execution plane | Android/Kotlin, `StudioSwarmRuntime`, `GgufSwarmInference` z dwoma slotami, aktualny kod MCP | Testy JNI, GGUF i realnych zleceń |
| Agora / roje | Protokół komunikacji i zarządzanie grafem | Lokalne, ograniczone do budżetu wykonywanie przekazań między rolami; 160 profili jest metadanymi | Interoperacyjność i testy wielu workerów |
| Rust + Zenoh BFT | Autoryzowany strumień zdarzeń z odpornym konsensusem | Brak udowodnionego konsensusu BFT lub gotowego rdzenia Rust w tym repo | Specyfikacja protokołu, implementacja quorum, chaos tests |
| TEE / TrustZone / SGX | Sprzętowa izolacja sekretów | Brak potwierdzenia uruchomienia ogólnych serwerów MCP w TEE | Attestation, vendor/platform support, threat model |
| Z3 + WASM | Formalne reguły uprawnień i invariants dla narzędzi | Brak potwierdzonej weryfikacji dowolnych skryptów solverem | SMT encoding, testy soundness, niezależna polityka wykonania |
| MCP | Ujednolicony dostęp do Tools, Resources i Prompts | Istniejący `McpProtocolClient`, HTTP/SSE, `McpRegistry`, `McpToolProvider` | E2E handshake, test polityki, SSRF i autoryzacja |
| Bezpieczeństwo operacji MCP | Zatwierdzenie przez użytkownika przed niebezpiecznym działaniem | `McpExecutionGate`, `confirmRequired`, aktywna blokada przed `tools/call` | Test Gradle/JUnit i audyt TOCTOU |
| GGUF | On-device llama.cpp | Istniejące lokalne silniki, JNI i konfiguracja Android | Realny benchmark n_ctx/n_batch, CPU/GPU, OOM i warm startup |
| Model cascade | Tiny NPU do frontier | Nie udowodniono stałego NPU ani routingu chmurowego per wynik złożoności | Zależności vendor NPU, profile sprzętu i pomiary |
| Multimedia | Wan/Hunyuan/LTX, RIFE, AudioCraft, Kokoro | Dotąd głównie katalog źródeł i istniejące moduły app; brak potwierdzonego GPU workera | Pipeline job API, rzeczywisty video/audio output, koszty |
| Code / NoCode | Monaco i XYFlow | Raport proponuje webowe komponenty; nie są to już gotowe moduły Android | Hostowana usługa web, auth, walidacja workflow |
| Raporty | Typst PDF | Koncepcja bez zweryfikowanego exportera | System fontów PL, PDF/A/UA walidowane zewnętrznie |
| Piaskownice | E2B/Firecracker | Koncepcja, brak poświadczenia dostawcy sandboxu | Test granic sieci, TTL, image integrity i wyłączenia |
| Nordic Polish UI | Minimalizm, grafit, morska zieleń i język polski | Część ekranów Android/Compose, brak pełnego audytu wizualnego | Design tokens, kontrast WCAG, testy zrzutów ekranów |

## 2. Skorygowane założenia inżynieryjne

1. **Zenoh to nie gotowy algorytm konsensusu BFT.** Kworum i poświadczenie podpisami muszą powstać jako oddzielna warstwa. Wartość SHA wpisana do Git również nie gwarantuje autentyczności osoby wykonującej zmianę.
2. **Nie wolno utożsamiać SAT z formalnym dowodem bezpieczeństwa programu.** W przykładzie z raportu zmienne Bool `memory_safe` i `no_infinite_loops` są nieskrępowane semantyką kodu; solver może znaleźć wartości `true`, co nie dowodzi braku wycieków pamięci ani zakończenia każdej pętli.
3. **TEE jest zależne od urządzenia i ABI.** TrustZone nie jest dowolnym środowiskiem Linux do hostowania pełnego Rust/MCP, a obecność TEE wymaga zdalnej atestacji, wsparcia platformy i właściwego zarządzania kluczami.
4. **WebContainers nie zastępuje Pythona w przeglądarce.** Do arbitralnych zadań Python/Node/C++ potrzebny jest prawidłowo izolowany worker po stronie serwera lub natywne środowisko Android.
5. **Model 14B/35B Q4 nie ma gwarancji działania przy 12 GB RAM.** Potrzebne są pomiary rzeczywistego RSS/PSS, KV cache, context window, pamięci systemu i budżetów GPU. `mmap` nie przenosi całego modelu wyłącznie do VRAM.
6. **Zmienne Vulkan i lokalizacja sterownika Adreno nie są uniwersalne.** Nie wymuszamy `VK_ICD_FILENAMES` ani niezdefiniowanej konfiguracji sterownika na każdym Androidzie bez wykrycia sprzętu i testów ABI.
7. **MCP optymalizuje objętość kontekstu tylko dla konkretnych strategii.** Wartość „98,7%” nie jest automatyczną cechą protokołu. Wymaga porównawczego eksperymentu tokenów i użycia kodu.
8. **Licencje modeli i materiałów generatywnych nie są automatycznie wolne od ograniczeń.** Przed publikacją wideo, audio i modeli trzeba zweryfikować osobno licencje checkpointów, danych treningowych oraz posiadane prawa.
9. **Przykłady Python w raporcie mają błędy składniowe i niedookreślone interfejsy.** Nie importujemy ich jako produkcyjnych plików wykonywalnych.

## 3. Wdrożona kontrola MCP w Android

Ścieżka rzeczywistego wykonania:

```text
LLM / interfejs użytkownika
    ↓
GenerationToolExecutor — walidacja tool/pass/argument object/timeout
    ↓
McpToolProvider
    ↓
McpRegistry.execute — identyfikator aktywnego serwera i narzędzia
    ↓
McpExecutionGate — fail-closed; ogranicza request, wymaga approval
    ↓
ShellConfirmationController.confirmRequired — obowiązkowy dialog foreground
    ↓
ponowne sprawdzenie konfiguracji runtime + statusu + narzędzia (TOCTOU)
    ↓
McpProtocolClient.callTool — dopiero teraz network tools/call
    ↓
ToolExecutionResult — wynik lub odmowa
```

**Reguły bezpieczeństwa:**
- Każde wywołanie zewnętrznego narzędzia MCP wymaga oddzielnego zatwierdzenia (również deklarowane read-only; samoopis serwera jest niezaufany).
- Brak callbacku akceptacji, brak UI w tle, odmowa użytkownika, błąd callbacku lub zdezaktualizowane narzędzie powoduje zablokowanie działania.
- Globalne wyłączenie potwierdzeń powłoki i „zawsze ufaj serwerowi” nie wyłączają obowiązkowej polityki MCP.
- Żądanie autoryzacji wyświetla serwer, nazwę narzędzia i **nazwy** parametrów; wartości mogą zawierać dane wrażliwe i nie są kopiowane do UI.
- Zakaz automatycznego wykonywania surowych fragmentów Python, Rust, shell lub JSON z niezaufanych repozytoriów.
- Limit serializacji parametrów: 64 KiB; limit liczby kluczy: 64.
- W razie zmiany konfiguracji serwera lub listy narzędzi po zatwierdzeniu wywołanie jest odrzucane.

**Uwaga:** to gate interakcyjny, **nie** pełny odrębny serwer bezpieczeństwa ani weryfikacja Z3. Wymaga testów end-to-end i hardeningu transportu (SSRF/DNS rebind/redirect/HTTPS). W samym MCP nazwy/parametry od zewnętrznego serwera pozostają nieufne.

## 4. Kolejność niezależnych faz realizacji

| Faza | Dostarczalny artefakt | Kryterium PASS |
|---:|---|---|
| 01 | Mandatory MCP gate | Kotlin JUnit, zatwierdzenie foreground, test odmowy w background, brak globalnego whitelist |
| 02 | Secure MCP transport | test DNS rebinding, redirect, local-only policy, session expiry, token redaction |
| 03 | GGUF hardware profiler | 5 fizycznych profilów RAM/SOC i pomiary OOM, TPS, P95 |
| 04 | Worker gateway | rust/Go API worker, auth, signed job receipts, budget i idempotency |
| 05 | Tool/Skill generators | signed manifest, ephemeral branch, code review, test sandbox; żadnego autoexec |
| 06 | Code/NoCode | Monaco + XYFlow z walidowanym DSL i zatwierdzeniem deployment |
| 07 | Multimedia | GPU job queue Wan/LTX, TTS stream, FFmpeg/RIFE, artefakty i metryki |
| 08 | Polskojęzyczny Nordic UI | design tokens, WCAG 2.2 AA, testy dostępności i screenshoty |
| 09 | Audyt i release | CI, test signed APK, SBOM, Semgrep/Trivy, rollback |

## 5. Reguły dowodowe

Nie twierdzić, że działają: rust/zenoh BFT, Z3 WASM, TEE, NPU prompt injection filter, 333 zainstalowane skills, GPU Wan, E2B worker, typst PDF/A, pełny Monaco/Python czy 160 jednocześnie aktywnych agentów, dopóki w repozytorium nie ma kompilowalnego kodu i wiarygodnych logów uruchomienia. Kontrola statyczna jest odrębną kategorią niż test jednostkowy i E2E.
