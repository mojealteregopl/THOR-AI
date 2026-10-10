# THOR AI · PACZKA 05 — agenci, lokalna inferencja, pamięć, głos i DevOps

**Data źródeł:** 2026-10-10.  
**Repozytorium docelowe:** `mojealterego/THOR-AI`, gałąź `omega/thor-agent-swarm`.  
**Wejście:** 30 adresów GitHub, **29 nowych** — `gonnect` już należy do paczki 04.  
**Metoda:** odczyt REST GitHub 30 repozytoriów, identyfikacja gałęzi domyślnych, głównych drzew Git i README. Wartości SHA w pliku `ThorPacketFiveCatalog.kt` to Git **tree SHA**, a nie commity. 

## Klasyfikacja repozytoriów

| Lp. | Źródło | Typ | Ustalenie po odczycie README |
|---:|---|---|---|
| 1 | [gonnect](https://github.com/mojealterego/gonnect) | DUPLIKAT 04 | Klient SIP/UC; zarejestrowany w paczce 04 |
| 2 | [hhvm](https://github.com/mojealterego/hhvm) | INFRASTRUCTURE | Maszyna wirtualna Hacklang/JIT; wymaga osobnego procesu |
| 3 | [skillsOPENai](https://github.com/mojealterego/skillsOPENai) | KNOWLEDGE | Archiwalny katalog skills; README wskazuje deprecjację |
| 4 | [dcache](https://github.com/mojealterego/dcache) | STORAGE | Rozproszona pamięć masowa, NFS/WebDAV, AGPL |
| 5 | [Omni-mobile](https://github.com/mojealterego/Omni-mobile) | ANDROID | Niewielki szkielet Kotlin Compose i WebSocket |
| 6 | [WAO-AI-2](https://github.com/mojealterego/WAO-AI-2) | VISUAL | Agentic Visual Intelligence / walidacja specyfikacji |
| 7 | [llama.cpp](https://github.com/mojealterego/llama.cpp) | LLM | Natywna inferencja GGUF; źródło do audytu kompatybilności |
| 8 | [ai](https://github.com/mojealterego/ai) | RESTRICTED | Projekt NSFW z reklamowanym instalatorem; brak integracji |
| 9 | [SYSTEM-EKSPERCKI-GRANT-BUSINESS-ARCHITECT](https://github.com/mojealterego/SYSTEM-EKSPERCKI-GRANT-BUSINESS-ARCHITECT) | BUSINESS | Grant/business architect z evidence gates; bootstrap |
| 10 | [Cinematic-Agents](https://github.com/mojealterego/Cinematic-Agents) | CINEMA | 18 specjalistów wg README, pipeline filmowy |
| 11 | [engram](https://github.com/mojealterego/engram) | MEMORY | Zewnętrzny serwer pamięci rozmów MCP z OAuth; weryfikacja prywatności |
| 12 | [Build-Low-Code-No-Code-Machine-Learning-Web-App.](https://github.com/mojealterego/Build-Low-Code-No-Code-Machine-Learning-Web-App.) | AUTOML | AURELIS: Streamlit/PyCaret, CSV, eksperymenty |
| 13 | [godot-orchestrator](https://github.com/mojealterego/godot-orchestrator) | GAME | Wtyczka Godot GDExtension do visual scripting, NIE runtime agentów |
| 14 | [con-terminal](https://github.com/mojealterego/con-terminal) | DESKTOP | Terminal z asystentem i SSH; wykonanie uprzywilejowane |
| 15 | [OmniVoice](https://github.com/mojealterego/OmniVoice) | AUDIO | Wielojęzyczne TTS, voice design; zgoda na klonowanie |
| 16 | [langchain](https://github.com/mojealterego/langchain) | AGENTS | Interfejsy LLM, narzędzia i ekosystem agentów |
| 17 | [Llamatik](https://github.com/mojealterego/Llamatik) | KOTLIN AI | Kotlin Multiplatform: llama, whisper i diffusion |
| 18 | [SillyTavern](https://github.com/mojealterego/SillyTavern) | CLIENT | Interfejs czatowy dla zaawansowanych użytkowników |
| 19 | [agent](https://github.com/mojealterego/agent) | MCP | 1MCP agregator serwerów; NIE dowolny autonomous agent |
| 20 | [activepieces](https://github.com/mojealterego/activepieces) | AUTOMATION | No-code workflow, human approvals i MCP pieces |
| 21 | [graphify](https://github.com/mojealterego/graphify) | KNOWLEDGE | Graf AST repozytorium; relacje EXTRACTED/INFERRED |
| 22 | [agent-framework](https://github.com/mojealterego/agent-framework) | AGENTS | Microsoft Agent Framework .NET/Python |
| 23 | [mcp-server-phone](https://github.com/mojealterego/mcp-server-phone) | RESTRICTED | VirtualSMS API kupowania numerów i obsługi OTP; bez adaptera |
| 24 | [TTS-WebUI](https://github.com/mojealterego/TTS-WebUI) | AUDIO | WebUI syntezy mowy i rozszerzeń |
| 25 | [VoxCPM2_portable](https://github.com/mojealterego/VoxCPM2_portable) | AUDIO | Lokalna synteza/klonowanie głosu, LoRA, runtime GPU |
| 26 | [IBM-Cloud-automation](https://github.com/mojealterego/IBM-Cloud-automation) | CLOUD | System referencyjny supervised agents i policy gating |
| 27 | [graphiti](https://github.com/mojealterego/graphiti) | MEMORY | Temporal knowledge graphs z pochodzeniem faktów |
| 28 | [aihub](https://github.com/mojealterego/aihub) | ANDROID | Kotlin UI agregatora AI na Android |
| 29 | [OpenMontage](https://github.com/mojealterego/OpenMontage) | VIDEO | Agentic video editing, timeline, media provenance |
| 30 | [unity-mcp](https://github.com/mojealterego/unity-mcp) | GAME | CoplayDev Unity Editor MCP, gałąź beta, testy i assety |

## Implementacja w THOR

- `ThorPacketFiveCatalog.kt` — 29 wpisów nieaktywnych, 20 profili specjalistycznych. Każdy ma co najmniej jedną dozwoloną referencję; nie uruchamia automatycznie MCP, serwera, GPU ani dostępu do danych.
- `ThorPacketFiveCatalogTest.kt` — kontrakty integralności 29 nowych rekordów, 20 ról, odfiltrowania `gonnect`, dwóch nowych ograniczeń RESTRICTED i spójności globalnych sum.
- `ThorPacketRegistry.kt` — dynamiczny rejestr rozszerzony o numer `05`. Ekran Android automatycznie wyświetla kartę **ŹRÓDŁA 05** z istniejącym komponentem `ThorSourcePacketPanel`; nie wymaga dopisywania twardego `when`.
- `ThorPacketRegistryTest.kt` — 5 paczek, indeks tab `9`, 150 źródeł i 86 profili, 7 źródeł RESTRICTED oraz brak ich referencji we wszystkich rolach.
- Nowe profile obejmują inżynierię GGUF, Android Compose, obraz referencyjny WAO, kinematografię, TTS, LangChain/MAF, 1MCP, Activepieces, Graphiti/Graphify/Engram, granty, AutoML, Godot, Unity MCP, terminal, storage, HHVM, skills, chat, IBM Cloud oraz montaż wideo.

## Architektura: co wymaga rzeczywistego podłączenia

| Warstwa | Kandydaci | Nieodzowne kontrakty i bramy |
|---|---|---|
| Android i lokalna inferencja | `llama.cpp`, `Llamatik`, `Omni-mobile`, `aihub` | NDK/ABI, GGUF, stan sesji, uprawnienia Android, wydajność i zgodność obecnego silnika `LlamaChatEngine` |
| Orkiestracja | `langchain`, `agent-framework`, `IBM-Cloud-automation`, `activepieces` | Separate trusted worker, wersjonowane API, RBAC, idempotencja, human approval |
| Brama MCP | `agent` = **1MCP** | Uwzględnić 1MCP jako agregator z filtrowaniem tool schema, a nie jako samodzielnego agenta |
| Pamięć | `engram`, `graphiti`, `graphify` | Gating eksportu wrażliwych konwersacji, zgoda, retencja, PII, odwracalny zapis, licencja BSL Engram |
| Media | `Cinematic-Agents`, `OpenMontage`, `OmniVoice`, `TTS-WebUI`, `VoxCPM2_portable` | Worker GPU/FFmpeg, kolejki, QA, zgody na wykorzystanie głosu, provenance materiałów i eksportu |
| Game engines | `unity-mcp`, `godot-orchestrator` | Niezależne środowisko i zgodna wersja edytora; Godot Orchestrator jest pluginem **visual scripting** |
| Rozwój i chmura | `con-terminal`, `dcache`, `hhvm`, `skillsOPENai` | Separacja procesów, audyt licencji, brak automatycznego uruchamiania nieznanych skryptów; skillsOPENai: repo archiwalne |
| Biznes i AutoML | `SYSTEM-EKSPERCKI-GRANT-BUSINESS-ARCHITECT`, `Build-Low-Code-No-Code-Machine-Learning-Web-App.` | Provenance danych, wyniki testów, legalne źródła programów grantowych, metryki holdout i trwałe modele |

## Ryzyka i blokady

1. `ai` — repozytorium promuje niezweryfikowany instalator oraz generowanie NSFW. **RESTRICTED**: tylko metadane, bez importu roli, uruchamiania instalatora i generowania.
2. `mcp-server-phone` — źródło VirtualSMS MCP dla wynajmu numerów, proxy i kodów OTP. **RESTRICTED**: brak uprawnień do kupna numerów i obsługi zewnętrznych weryfikacji.
3. Poprzednie ograniczenia (`pegasus_spyware`, `L3MON-1`, `chappie-bot`, `mailtm-client`, `mcp-server`) pozostają bez zmian. **Łącznie 7** źródeł objętych blokadą.
4. `engram`: według README zewnętrzna pamięć pełnych transkryptów. Nie wolno wysyłać danych do tego serwera bez zgody, oceny prywatności oraz retencji.
5. `skillsOPENai`: README opisuje archiwizację/deprecjację na rzecz nowszego repozytorium pluginów. Nie należy kopiować wszystkich skills bez wyboru i weryfikacji.
6. `gonnect`: wpis istnieje w paczce 04; dodanie ponownego wywołałoby błąd spójności, więc poprawnie deduplikowany.

## Bramy wydania i ograniczenia dowodowe

- **Zapisane:** katalog i definicje profili, testy regresji katalogu, rejestr piątej zakładki.
- **Niepotwierdzone:** pełna kompilacja Android, uruchomienie JUnit Gradle, APK, faktyczne podłączenie MCP/LLM/voice/cloud, urządzenie Android, benchmark GPU i zgodność licencji dla embeddowania upstreamów.
- **Bez merge:** `master` pozostaje niezmieniony aż do uzyskania testów z CI, oceny regresji i akceptacji wydania.
