# THOR AI — Paczka 10: 29 linków, 26 zarejestrowanych źródeł

**Data audytu:** 2026-10-10.  
**Gałąź implementacyjna:** `omega/thor-agent-swarm`.  
**Metoda:** GitHub `get_repo`, korzeń drzewa Git, README każdego niepustego źródła; brak automatycznych instalacji i uruchamiania kodu z repozytoriów.

## Deduplikacja i kompletność

- **29 przesłanych adresów**, **27 unikalnych**. `autogen` i `ai-novel-generator` zostały podane po dwa razy.
- **26 źródeł** mają dostępne obiekty Git tree o 40-znakowych SHA, umożliwiające zapis i wersjonowanie.
- `mojealterego/CzatBotSingularity`: repozytorium istnieje, ale GitHub zwrócił **409 Git Repository is empty**. Nie dopisujemy fikcyjnego SHA ani profilu wykonawczego; rekord pozostaje w dokumentacji jako źródło niezdatne do importu.
- `desktop-tutorial` ma README, ale nie ma kodu rzeczywistej aplikacji.
- W paczce 10 dodano **18 profili konfiguracyjnych**. Łącznie po dziewięciu wcześniejszych paczkach: **282 źródła**, **178 profili** i **21 RESTRICTED**.

## Inwentarz

| # | Repozytorium | Ustalenie na podstawie README i drzewa | Poziom ryzyka |
|---:|---|---|---|
| 1 | [mojealterego/Bug-Bounty-Agents](https://github.com/mojealterego/Bug-Bounty-Agents) | Prompt-only collection of 43 security personas | `READ_ONLY` |
| 2 | [mojealterego/n8n-nodes-futureagi](https://github.com/mojealterego/n8n-nodes-futureagi) | Future AGI evaluation and prompt-management nodes for n8n | `EXTERNAL_MUTATION` |
| 3 | [mojealterego/Duix-Avatar](https://github.com/mojealterego/Duix-Avatar) | Offline Windows avatar and voice-generation toolkit | `PRIVILEGED_EXECUTION` |
| 4 | [mojealterego/Low-Code-No-Code-Platforms](https://github.com/mojealterego/Low-Code-No-Code-Platforms) | Curated index of builders, not executable builder | `READ_ONLY` |
| 5 | [mojealterego/autogen](https://github.com/mojealterego/autogen) | Microsoft AutoGen in maintenance mode; MAF successor | `READ_ONLY` |
| 6 | [mojealterego/HunyuanVideo-I2V](https://github.com/mojealterego/HunyuanVideo-I2V) | Tencent PyTorch image-to-video and LoRA | `PRIVILEGED_EXECUTION` |
| 7 | [mojealterego/ai-novel-generator](https://github.com/mojealterego/ai-novel-generator) | Local Gradio and Vue/FastAPI multitenant story generator | `EXTERNAL_MUTATION` |
| 8 | [mojealterego/openai-chatkit-starter-app](https://github.com/mojealterego/openai-chatkit-starter-app) | Two ChatKit starter templates: self-hosted and managed | `REMOTE_ACCESS` |
| 9 | [mojealterego/sannabotapp](https://github.com/mojealterego/sannabotapp) | Voice-first Android Accessibility and task scheduler | `PRIVILEGED_EXECUTION` |
| 10 | [mojealterego/graph-of-thoughts](https://github.com/mojealterego/graph-of-thoughts) | Graph of Operations implementation for LLM reasoning | `REMOTE_ACCESS` |
| 11 | [mojealterego/desktop-tutorial](https://github.com/mojealterego/desktop-tutorial) | README-only GitHub Desktop sample | `READ_ONLY` |
| 12 | [fotografaandrzej333/awesome-voip](https://github.com/fotografaandrzej333/awesome-voip) | Historical VoIP, PJSIP and Linphone resources | `READ_ONLY` |
| 13 | [mojealterego/visionclaw](https://github.com/mojealterego/visionclaw) | Apple Vision Pro spatial assistant with Mac/OpenClaw link | `REMOTE_ACCESS` |
| 14 | [mojealterego/Stable-Diffusion-3.5-Web-UI](https://github.com/mojealterego/Stable-Diffusion-3.5-Web-UI) | Unreviewed password-protected executable download | `RESTRICTED` |
| 15 | [mojealterego/HunyuanPortraitLCM](https://github.com/mojealterego/HunyuanPortraitLCM) | Portrait animation; README specifies 24 GB GPU baseline | `PRIVILEGED_EXECUTION` |
| 16 | [mojealterego/open-agent-platform](https://github.com/mojealterego/open-agent-platform) | Deprecated LangGraph agent builder per README | `READ_ONLY` |
| 17 | [mojealterego/kernel-browser-automation-starter](https://github.com/mojealterego/kernel-browser-automation-starter) | Next.js/Vercel and Kernel cloud browser template | `EXTERNAL_MUTATION` |
| 18 | [mojealterego/autopentest-ai](https://github.com/mojealterego/autopentest-ai) | Autonomous offensive pentest MCP, quarantined | `RESTRICTED` |
| 19 | [mojealterego/santamonica](https://github.com/mojealterego/santamonica) | Fahrenheit PS2 game decompilation and research | `READ_ONLY` |
| 20 | [mojealterego/perplexicapp](https://github.com/mojealterego/perplexicapp) | Unofficial Expo mobile frontend for Perplexica | `REMOTE_ACCESS` |
| 21 | [NJU-LINK/OmniVideoBench](https://github.com/NJU-LINK/OmniVideoBench) | Audio-visual reasoning benchmark, 1,000 QA pairs | `READ_ONLY` |
| 22 | [mojealterego/babyagi](https://github.com/mojealterego/babyagi) | Experimental self-building function framework, not production | `PRIVILEGED_EXECUTION` |
| 23 | [mojealterego/lonlybot](https://github.com/mojealterego/lonlybot) | Companion chat UI with multiple model providers | `REMOTE_ACCESS` |
| 24 | [mojealterego/phisher98](https://github.com/mojealterego/phisher98) | README-only author profile, not a phishing tool | `READ_ONLY` |
| 25 | [mojealterego/MetaGPT](https://github.com/mojealterego/MetaGPT) | SOP-based multiagent software company framework | `REMOTE_ACCESS` |
| 26 | [mojealterego/OnlineSimBot](https://github.com/mojealterego/OnlineSimBot) | Temporary numbers and SMS inboxes, quarantined | `RESTRICTED` |

## Kierunki integracji w architekturze THOR AI

| Warstwa | Źródła | Pożądany kontrakt wykonawczy | Warunki dopuszczenia |
|---|---|---|---|
| Voice-first Android | `sannabotapp` | Mikrofon i Android Accessibility → policy gate → narzędzia → udokumentowany wynik | Zgoda użytkownika, zakres uprawnień, blokada wiadomości/operacji bez autoryzacji, test na urządzeniu |
| Agent orchestrator | `graph-of-thoughts`, `MetaGPT`, `autogen`, `babyagi` | Graf z limitami kroków, walidacją wejść/wyjść, monitoringiem i kontrolą handoff | AutoGen w maintenance mode; BabyAGI eksperymentalny; brak importu skryptów z niezweryfikowanych repo |
| Voice/Video backend | `Duix-Avatar`, `HunyuanVideo-I2V`, `HunyuanPortraitLCM` | Kolejka GPU, rozpoznawalny `jobId`, polling/webhook, podpisany rezultat | Licencje modeli, zgody na wizerunek i głos, pomiar VRAM, test wynikowego MP4 |
| Chat/UI | `openai-chatkit-starter-app`, `perplexicapp`, `visionclaw`, `lonlybot` | Kontrakt sesji, socket / realtime transport, dostępność i prywatność | Serwerowe poświadczenia, brak wycieku historii, iOS/visionOS ≠ Android |
| Browser/Low-code | `kernel-browser-automation-starter`, `Low-Code-No-Code-Platforms`, `open-agent-platform` | Przeglądarka izolowana z allowlist domen, osobny webworker | `open-agent-platform` jest deprecated, katalog no-code nie zawiera silnika |
| Agent evaluation | `n8n-nodes-futureagi`, `NJU-LINK/OmniVideoBench` | Reproducible eval artifacts, ślady pochodzenia, metryki audio-wideo | 1 000 par QA w benchmarku to dane upstream, nie wynik THOR; zgoda na przekazanie próbek |
| Source research | `Bug-Bounty-Agents`, `awesome-voip`, `desktop-tutorial`, `phisher98`, `santamonica` | Tylko indeksowanie wiedzy / ostrożna analiza | Żadne z nich nie jest uruchomionym adapterem THOR |

## Ryzyka i rozstrzygnięcia

1. `sannabotapp` (Android voice assistant z Accessibility) **nie jest tym samym** co wcześniejsze `sanna` (kryptograficzne receipts i brama governance). Ich bezpieczeństwo i kontrakty muszą być oceniane oddzielnie.
2. `autogen` — według README rozwój nowych funkcji został przeniesiony do Microsoft Agent Framework. W paczce 05 jest już osobne źródło `agent-framework`; brak podstaw do dublowania runtime.
3. `open-agent-platform` zostało oznaczone przez autorów jako deprecated. Nie tworzyć nowej zależności produkcyjnej wyłącznie na podstawie README.
4. `babyagi` jest eksperymentalne i opisane jako nieprzeznaczone do produkcji; `MetaGPT` opisuje SOP wieloagentowych zespołów.
5. `phisher98` to profil autora, nie kod narzędzia phishingowego. `santamonica` jest projektem dekompilacji gry Fahrenheit na PlayStation 2, nie platformą AI. `visionclaw` dotyczy Vision Pro, nie Android.
6. `Stable-Diffusion-3.5-Web-UI` odsyła z README do niezweryfikowanego, zabezpieczonego hasłem archiwum wykonywalnego Windows. Źródło sklasyfikowano `RESTRICTED`; bez pobrania, uruchomienia i przyznania profilu.
7. `autopentest-ai`: autonomiczne ofensywne testy i łączenie podatności. `OnlineSimBot`: odczyt SMS z numerów tymczasowych. Oba oznaczono `RESTRICTED`, bez integracji wykonawczej.
8. `Bug-Bounty-Agents` to **43 opisowe prompty** bez skanerów. Zarejestrowano jedynie profil do defensywnego przeglądu wiedzy; nie aktywowano narzędzi.
9. `NJU-LINK/OmniVideoBench` znajduje się w innym namespace niż `mojealterego` i różni się od wcześniej zarejestrowanego `OmniVideo-100K`.

## Zmiany wykonane w kodzie

- `ThorPacketTenCatalog.kt`: 26 rekordów z faktycznymi Git tree SHA; 18 profili; walidacja unikalności, zależności i stanu nieaktywnego.
- `ThorPacketTenCatalogTest.kt`: specyfikacja inwariantów, blokad i globalnych sum.
- `ThorPacketRegistry.kt` — dodatkowa paczka `10` w dynamicznej nawigacji Android.
- `ThorPacketRegistryTest.kt` — mapa kart i globalne wartości `282/178/21`.

## Testy i ograniczenia

Kontrola statyczna może potwierdzić format danych, unikalność źródeł, referencje ról i indeks zakładki. Nie jest dowodem, że JUnit lub Android Gradle przeszły. Rzeczywiste podłączenie LLM/MCP, kompilacja APK, integracja głosu i renderów GPU wymagają osobnych środowisk i testów. Do tego czasu `master` pozostaje bez zmian, a PR powinien zachować status draft.