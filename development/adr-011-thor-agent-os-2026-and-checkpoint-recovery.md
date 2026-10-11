# ADR-011 — THOR Agent OS 2026: warstwy, trwałość stanów i integracja raportu

**Data:** 2026-10-11
**Gałąź:** `omega/thor-agent-swarm`
**Status:** wdrożono pierwszy bezpieczny mechanizm wznowienia GGUF i usunięto blokadę konfiguracji SDK w CI; architektura docelowa pozostaje w realizacji.
**Źródło:** przekazany przez właściciela raport „Ekskluzywny Raport Analityczny: Ekosystem Agentów AI, MCP, Automatyzacji i Tworzenia Gier (2026)”.

## 1. Status twierdzeń raportu

Raport jest **wizją i listą wymagań**, a nie dowodem, że kod Rust/Zenoh/Z3, 333 tools, 333 skills, NPU lub mechanika Unity już działają.

Liczniki gwiazdek i przywoływane pozycje rynkowe projektów (LangGraph, OpenClaw, Dify, Langflow itd.) są migawkami bez datowanych dowodów w samym raporcie; nie traktujemy ich jako zweryfikowanych faktów implementacyjnych. Implementacje repozytoriów trzeba sprawdzać w wersjach przypiętych do commitów.

W szczególności:
- Zenoh jest transportem/pub-sub, **nie dostarcza automatycznie kworum BFT ani PQC**. Są to niezależne moduły architektury.
- Z3 wymaga poprawnego kodowania własności w SMT. Wynik SAT na niepowiązanych zmiennych boolowskich nie jest dowodem bezpieczeństwa kodu ani braku wycieków pamięci.
- TEE/TrustZone na Androidzie nie jest uniwersalnym hostem dla kontenerów i MCP. Sprzęt, API, uwierzytelniona atestacja oraz oddzielne granice wykonania wymagają walidacji na konkretnym urządzeniu.
- AccessibilityNodeInfo jest drzewem semantycznym widoków, nie DOM-em przeglądarki dla każdej aplikacji; nie obejmuje niezawodnie wszystkich ekranów, a interakcje wymagają świadomej zgody i respektowania ochrony aplikacji.
- „333 narzędzia” (50+70+63+50+50+50) i „333 umiejętności” (40+55+60+78+50+50) to osobne **docelowe liczności**, nie obecne aktywne zdolności systemu.
- Wielkości społeczności, przepustowość NPU i domniemane gwarancje bezpieczeństwa z raportu nie są kryteriami odbioru bez pomiarów na działającym środowisku.
- „Zero utraty kontekstu” nie jest możliwe do zagwarantowania przy awarii między wykonaniem nieidempotentnego skutku ubocznego i zapisem potwierdzenia. Zastosować klucze idempotencji, durable receipts i semantykę co najmniej raz/na pewno nie więcej niż raz tam, gdzie wspierane.

## 2. Architektura warstwowa — kontrakty docelowe

| Warstwa | Odpowiedzialność | Faktyczny stan | Następny krytyczny test |
|---|---|---|---|
| UX Android | Polski UI, dostępność, potwierdzanie operacji | Android/Compose + ekran Swarm | Instrumentation, kontrast i ergonomia |
| Flow | Graf, checkpoint, retry, cancellation | `StudioSwarmRuntime` + deterministyczny plan, **lokalne szyfrowane checkpointy etapów** | Resume po kill-process / restart telefonu |
| I/O | MCP JSON-RPC, granice uprawnień | `McpProtocolClient`, `McpExecutionGate` | SSRF, DNS-rebinding, redirect, OAuth, audit |
| Skills | Skatalogowane umiejętności, polityki i wersje | Katalog 282 źródeł / 178 profili **bez uruchomienia** | Signed manifest, test kompetencji, licencja |
| Reasoning | GGUF + przyszłe NPU/frontier routing | Dwa lokalne sloty GGUF | TPS, OOM, pamięć, opóźnienia |
| State | WAL / historyczne checkpointy / consensus | **Wyłącznie jednostanowiskowy szyfrowany checkpoint** | Idempotency, append-only WAL, test recovery |
| Control plane | Rust + Zenoh + quorum BFT + PQC | **Niezaimplementowane** | Protokół wiadomości, signed epochs, chaos test |
| Proof | Z3 + WASM, statyczne zasady bezpieczeństwa | **Niezaimplementowane** | Formalnie określone invariants i niezależna walidacja |
| Isolation | TEE, worker E2B/Firecracker | **Niezaimplementowane** | Atestacja i izolacja dostępów |
| Game dev | Unity/UE5 poprzez zatwierdzane MCP | **Niezaimplementowane E2E** | Sandbox unity test + edycja diff + test + rollback |
| Mobile actions | Android Accessibility ze świadomą zgodą | **Brak kompletnego agenta automatyzacji** | Test uprawnień i ochrony danych |
| Multimedia | Vision/Audio/TTS/T2V | **Brak połączonego pipeline** | Generacja artefaktów i audyt licencji |

## 3. Wdrożone: lokalne checkpointy wykonywania Swarm (etap 1)

Ścieżka:
```text
Studio UI -> plan i fingerprint SHA-256(task + prompts + graph + model slots)
          -> FileSwarmCheckpointStore.read (AES-256-GCM; AndroidKeyStore)
          -> walidacja prefiksu ukończonych kroków
          -> tylko brakujące wywołania GGUF
          -> FileSwarmCheckpointStore.write (fsync temp + atomic rename)
          -> oznaczenie ukończenia w UI
          -> usunięcie checkpointu po ukończeniu całego grafu
```

**Kontrakty:**
1. Snapshot przechowuje wyłącznie wyniki ukończonych etapów, bez praw dostępu lub połączeń MCP. Model nie może sam zlecać narzędzi przez tę ścieżkę.
2. Zmiana zadania, system promptu, grafu lub przydziału slotów unieważnia możliwość wznowienia starego przebiegu.
3. Nieudane uwierzytelnienie AES-GCM, błędna kolejność agentów i niepoprawny format przerywają wznowienie — *fail closed*.
4. Zmiana kolejności zapisów i warunki brzegowe mają testy jednostkowe; potwierdzenie zielonego CI wymagane przed uznaniem za ukończone.
5. Zapis odbywa się w prywatnym katalogu aplikacji na Androidzie, a klucz pozostaje w Android Keystore.
6. Udane zakończenie usuwa checkpoint. Nie ma historii ani cofania do wybranego etapu — **to jeszcze nie time travel**.
7. Model nie odtwarza częściowo wykonanego etapu, jeśli proces zginął przed zapisem. Ponowne generowanie tekstu jest dopuszczalne, ale **nie wolno stosować tej samej strategii do skutków ubocznych** bez idempotency key i receipt ledger.

## 4. Migracja do docelowego Agent OS

| Faza | Artefakt wymagany do oznaczenia PASS | Stan |
|---|---|---|
| 0 | Android CI + przejście unit tests + sprawdzenie APK | CI w trakcie naprawy |
| 1 | Szyfrowane checkpointy lokalnego Swarm | Kod zapisany, testy oczekują na CI |
| 2 | Wersjonowana schema durable run/task/tool + serializacja narzędzi | Nie rozpoczęto |
| 3 | MCP permission policy, OAuth/scoped tokens, log audytowy, test SSRF | Wykonano gate; reszta nie |
| 4 | Worker API ze signed execution receipts i kluczami idempotencji | Nie rozpoczęto |
| 5 | Rust transport/Zenoh, kworum podpisów, consensus safety/liveness | Nie rozpoczęto |
| 6 | Narzędzia Unity/Unreal przez MCP z testem i zatwierdzeniem zmian | Nie rozpoczęto |
| 7 | Accessibility z odrębnym consent i ochroną przed prompt injection | Nie rozpoczęto |
| 8 | NPU router, audio/wideo, nowy Polish Nordic UI | Nie rozpoczęto |
| 9 | Z3 na właściwych modelach programów, benchmarki i niezależny audyt | Nie rozpoczęto |

**Zakaz raportowania funkcji jako działającej na podstawie samego importu README, dodania profilu, napisania pliku lub obecności tool name.** Każdy moduł wymaga uruchomienia, testów oraz artefaktów dowodowych.
