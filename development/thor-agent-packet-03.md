# THOR AI · PACZKA 03 — inwentaryzacja 40 repozytoriów

**Data:** 2026-10-10  
**Repozytorium docelowe:** `mojealterego/THOR-AI`  
**Gałąź izolowana:** `omega/thor-agent-swarm`  
**Dowód źródłowy:** odczyt 40 dostępnych repozytoriów, identyfikacja bazowych gałęzi oraz obiektów Git tree, przegląd README (dla Ren'Py: `README.rst`).

## Stan i znaczenie

Nie istnieje automatyczna równość między źródłem GitHub, profilem agenta, zainstalowanym runtime i działającym adapterem. Katalog THOR to **zestaw sprawdzonych referencji z jawną granicą wykonania**. Zawiera wyłącznie dane źródłowe; bez uruchamiania zależności, serwerów, wtyczek, pobierania modeli albo wykonywania zewnętrznych skryptów.

W trzeciej paczce sprawdzono **40/40** repozytoriów (bez duplikatów względem paczek 01 i 02). Zidentyfikowano 24 użyteczne, ograniczone profile specjalistów. Dwa podwyższone obszary ryzyka wymagają osobnej ścieżki: `AllHackingTools` pozostaje wyłącznie referencją do defensywnej weryfikacji, a `pegasus_spyware` otrzymuje **RESTRICTED**, bez przypisania do jakiegokolwiek agenta, wykonywania lub instalacji.

## Repozytoria

| # | Źródło | Klasa | Weryfikowane zastosowanie |
|---:|---|---|---|
| 1 | [expo](https://github.com/mojealterego/expo) | MOBILE | React Native framework i moduły |
| 2 | [awesome-phone-call-agents](https://github.com/mojealterego/awesome-phone-call-agents) | VOICE | Skills i schematy rozmów przez telefon |
| 3 | [Agents-for-Humans-Hackathon](https://github.com/mojealterego/Agents-for-Humans-Hackathon) | GOVERNANCE | CogniSync: przygotowanie, aprobata, audyt |
| 4 | [Nowe-projekty](https://github.com/mojealterego/Nowe-projekty) | PORTFOLIO | Katalog projektów i rankingów |
| 5 | [firecrawl](https://github.com/mojealterego/firecrawl) | WEB | Web search, scrape, crawl, extract |
| 6 | [public-apis](https://github.com/mojealterego/public-apis) | CATALOG | Katalog API, nie aktywny provider |
| 7 | [PLANY-I-POST-PY--W-REPOZYTORIACH](https://github.com/mojealterego/PLANY-I-POST-PY--W-REPOZYTORIACH) | PORTFOLIO | Centralny archiwalny audyt repozytoriów |
| 8 | [tauri](https://github.com/mojealterego/tauri) | DESKTOP | Wieloplatformowy desktop Rust |
| 9 | [twine](https://github.com/mojealterego/twine) | READER | RSS reader Kotlin Multiplatform |
| 10 | [YuE](https://github.com/mojealterego/YuE) | AUDIO | YuE2: muzyka symboliczna i synteza |
| 11 | [nocodb](https://github.com/mojealterego/nocodb) | DATA | No-code baza danych |
| 12 | [langgraph](https://github.com/mojealterego/langgraph) | AGENT RUNTIME | Stateful graph i checkpointy |
| 13 | [unsloth](https://github.com/mojealterego/unsloth) | TRAINING | Fine-tuning i lokalne modele |
| 14 | [Googleskills](https://github.com/mojealterego/Googleskills) | SKILLS | Repozytorium Google Agent Skills |
| 15 | [webstudio](https://github.com/mojealterego/webstudio) | WEB BUILDER | Wizualne budowanie stron |
| 16 | [agnes-ai-video-suite](https://github.com/mojealterego/agnes-ai-video-suite) | VIDEO | EchoSync prototypy storyboard |
| 17 | [ollama](https://github.com/mojealterego/ollama) | LOCAL LLM | Serwer lokalnej inferencji |
| 18 | [OpenConstructionERP](https://github.com/mojealterego/OpenConstructionERP) | ERP | BOQ, BIM i workflow budowlane |
| 19 | [appsmith](https://github.com/mojealterego/appsmith) | LOW CODE | Panele operacyjne i aplikacje wewnętrzne |
| 20 | [awesome-seedance-prompts](https://github.com/mojealterego/awesome-seedance-prompts) | REFERENCE | Biblioteka promptów do wideo |
| 21 | [termux-app](https://github.com/mojealterego/termux-app) | ANDROID SHELL | Terminal Android i Linux env |
| 22 | [n8n](https://github.com/mojealterego/n8n) | WORKFLOWS | Automation platform z approval |
| 23 | [renpy](https://github.com/mojealterego/renpy) | GAME | Silnik narracyjnych visual novel |
| 24 | [langflow](https://github.com/mojealterego/langflow) | WORKFLOWS | Builder agentów z API/MCP |
| 25 | [rust-sdk](https://github.com/mojealterego/rust-sdk) | MCP SDK | Rust SDK MCP rmcp |
| 26 | [OGAM](https://github.com/mojealterego/OGAM) | MOBILE AI | Offline-first multimodal AI |
| 27 | [OpenHands](https://github.com/mojealterego/OpenHands) | CODING AGENT | Agent Canvas i coding backend |
| 28 | [datadog-agent](https://github.com/mojealterego/datadog-agent) | OBSERVABILITY | Monitoring metryk, logów i SLO |
| 29 | [google-cloud-node](https://github.com/mojealterego/google-cloud-node) | CLOUD SDK | Google Cloud Node.js clients |
| 30 | [dify](https://github.com/mojealterego/dify) | AGENT PLATFORM | Agent/RAG i workflow platform |
| 31 | [flutter](https://github.com/mojealterego/flutter) | MOBILE SDK | Flutter Dart SDK |
| 32 | [maui](https://github.com/mojealterego/maui) | MOBILE SDK | NET MAUI CSharp i XAML |
| 33 | [WAO-AI](https://github.com/mojealterego/WAO-AI) | AGENT RUNTIME | Virtual Operational Architect |
| 34 | [AllHackingTools](https://github.com/mojealterego/AllHackingTools) | SENSITIVE | Narzędzia pentest, brak autorun |
| 35 | [pegasus_spyware](https://github.com/mojealterego/pegasus_spyware) | RESTRICTED | Decompiled spyware - kwarantanna |
| 36 | [ArchGen](https://github.com/mojealterego/ArchGen) | ARCHITECTURE | Wizualne diagramy i estymacja kosztów |
| 37 | [awesome-agent-skills](https://github.com/mojealterego/awesome-agent-skills) | CATALOG | Katalog zewnętrznych umiejętności |
| 38 | [Awesome-LLMs-meet-Multimodal-Generation](https://github.com/mojealterego/Awesome-LLMs-meet-Multimodal-Generation) | RESEARCH | Literatura o generacji multimodalnej |
| 39 | [MaxVideoAi](https://github.com/mojealterego/MaxVideoAi) | VIDEO PLATFORM | Wielo-modelowe tworzenie wideo |
| 40 | [stable-diffusion-webui](https://github.com/mojealterego/stable-diffusion-webui) | LOCAL IMAGE | SD WebUI Python i Gradio |

Każdy zapis katalogu zawiera: `repository`, `branch`, `revision` (SHA **obiektu Git tree**, a nie commit SHA), `kind`, `risk`, `capability` i `activated=false`. Dokładne zweryfikowane referencje są w `ThorPacketThreeCatalog.kt`. Wszystkie linki to adresy źródłowych repozytoriów użytkownika, nie instancje MCP.

## Architektura integracyjna E2E

| Płaszczyzna | Moduły referencyjne | Strategia integracji | Wymagany gate |
|---|---|---|---|
| Android / Edge | Expo, Flutter, MAUI, OGAM, Termux | Osobny runtime lub bridge HTTP/MCP; THOR Android zachowuje Kotlin Compose | SDK, podpis, uprawnienia, test urządzenia |
| Control Plane | LangGraph, OpenHands, WAO-AI | Trwały graf z checkpointem, stanem wykonania, autoryzacją i audytem | Potwierdzone API, izolacja workspace, narzędzia default-deny |
| Workflow | n8n, Dify, Langflow | Adapter API wyłącznie przez allowlist i identyfikację użytkownika | Policy gate, idempotencja, logi zdarzeń |
| Model Plane | Ollama, Unsloth | Lokalna inferencja oraz oddzielny proces treningu | Model hash, wersja, licencja, pamięć/VRAM, benchmark |
| Media | YuE2, SD WebUI, MaxVideoAi, EchoSync, Seedance | Worker API z kolejką, statusami, kontrolą kosztów i plików | Zgoda na płatność, prawidłowość wyników, prawa do assetów |
| Knowledge | Twine RSS, Googleskills, awesome-agent-skills, badania | Indeksowanie z provenance, bez wykonywania poleceń znalezionych w treści | Kontrola licencji, brak prompt injection |
| Data & Dashboard | NocoDB, Appsmith, OpenConstructionERP, Webstudio | Dedykowane backendy i kontrolowane konektory, bez osadzania całych aplikacji w THOR | Migracje, RBAC, szyfrowanie, backup, zgodność AGPL |
| Infrastructure | Datadog Agent, Google Cloud Node, Rust MCP SDK | Zewnętrzne procesy lub serwery za policy gateway | OIDC/WIF, scope, telemetria, limit kosztów |
| Security | AllHackingTools, Pegasus | Brak adapterów wykonawczych; Pegasus tylko opis referencyjny i analiza defensywna | Kwarantanna; brak automatycznego uruchomienia |

**Szczególne rozstrzygnięcia merytoryczne:** `twine` to czytnik **RSS** w Kotlin Multiplatform, nie narzędzie Twine do interaktywnej fikcji. `YuE` na wskazanej gałęzi dokumentuje **YuE2**. `rust-sdk` to projekt implementujący SDK **MCP Rust**, nie generyczny framework Rust. `PLANY-I-POST-PY--W-REPOZYTORIACH` zawiera historyczną ewidencję 414 repozytoriów, ale nie jest dowodem zaktualizowanej bieżącej liczby ani refaktoryzacji wszystkich z nich.

## Kod zapisany w THOR

- `ThorPacketThreeCatalog.kt`: 40 wpisów ze SHA Git tree i 24 profile; automatyczny fail-fast przy duplikatach, braku źródeł lub nadaniu profilu źródła `RESTRICTED`.
- `ThorPacketOneCatalog.kt`: rozszerzenie typu ryzyka o `RESTRICTED`.
- `ThorPacketOnePanel.kt`: współdzielona karta dla trzech paczek, osobna etykieta **ZABLOKOWANE** przy wpisie z ryzykiem `RESTRICTED`.
- `NewGptStudioScreen.kt`: karta **ŹRÓDŁA 03** z bezpiecznym importem do AGENTS. Role nie są uruchamiane przez sam import, a `tools` oraz `handoffs` pozostają puste.
- `ThorPacketThreeCatalogTest.kt`: unikalność 40 źródeł, 24 profile, integralność 88 referencji i 48 profili w trzech paczkach, domyślne `activated=false`, ograniczenia dla Pegasus i uprzywilejowanych narzędzi.

## Dowody i pozostałe warunki

- **Potwierdzone:** istnienie repozytoriów i ich katalogów, przegląd plików README, zapis zmian na wskazanej gałęzi, sprawdzalny kontrakt statyczny katalogów.
- **Niepotwierdzone:** kompilacja Android, runtime łączący model, wykonanie wszystkich JUnit, APK i UI E2E, handshake poszczególnych MCP, uruchomienie lokalnych modeli, dostawców chmurowych, licencje do embedowania.
- Dla dużych upstreamów (Flutter, Expo, Tauri, OpenHands) preferowana integracja przez stabilne API i dedykowane adaptery zamiast kopiowania monorepo.
- Przy projektach objętych AGPL lub odmiennymi zasadami dystrybucji wymagany jest audyt zgodności przed kopiowaniem lub bezpośrednim wykorzystaniem źródeł.
- Nie wolno uznać reklamowych twierdzeń README za niezależnie przeprowadzone testy.

**Przejście do merge:** pełne Gradle test + APK, audyt licencji, test stanu aplikacji i ocena kolizji z niezależnym PR #2.
