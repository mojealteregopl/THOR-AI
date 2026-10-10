# THOR AI · PACZKA 06 — Android, ocena agentów, Railway, ElevenLabs i multimedia

**Data audytu źródeł:** 2026-10-10  
**Repozytorium docelowe:** `mojealterego/THOR-AI`, gałąź `omega/thor-agent-swarm`  
**Wejście:** 20 adresów GitHub; dwa wskazują `railway-skills`, dlatego **19 unikalnych repozytoriów**.

## Stan i zakres audytu

Dla 19 repozytoriów zweryfikowano istnienie, główną gałąź, obiekt **Git tree** (40-znakowy SHA) i README. Dokumentacja jest podstawą identyfikacji produktów; deklarowane benchmarki wydajności i bezpieczeństwa nie są traktowane jako wyniki testów THOR. W pliku `ThorPacketSixCatalog.kt` zapisana jest referencja do każdego z 19 źródeł, kategoria, próg ryzyka i opis możliwości.

## Inwentarz

| # | Repozytorium | Typ / ryzyko | Ustalenie na podstawie zawartości |
|---:|---|---|---|
| 1 | [railway-skills](https://github.com/mojealterego/railway-skills) | KNOWLEDGE / REMOTE_ACCESS | Skills i oficjalny plugin Railway; wymaga konta i autoryzacji |
| 2 | [Rivo-Agent-Application](https://github.com/mojealterego/Rivo-Agent-Application) | ANDROID / READ_ONLY | React Native chat offline, pobieranie modelu i początkowy sign-in online |
| 3 | [Shadowbroker](https://github.com/mojealterego/Shadowbroker) | INFRASTRUCTURE / PRIVILEGED_EXECUTION | Publiczne dane OSINT i serwerowe narzędzia rekonesansu; wyłącznie autoryzowane |
| 4 | [gpt_mobile](https://github.com/mojealterego/gpt_mobile) | ANDROID / REMOTE_ACCESS | Kotlin Compose i profile narzędzi, MCP Streamable HTTP, OAuth, Android Keystore |
| 5 | [agent-learning-kit](https://github.com/mojealterego/agent-learning-kit) | KNOWLEDGE / READ_ONLY | SDK Future AGI do ewaluacji Python i TypeScript |
| 6 | [UserLAnd](https://github.com/mojealterego/UserLAnd) | ANDROID / PRIVILEGED_EXECUTION | UserLAnd dystrybucje Linux na Android bez root |
| 7 | [future-agi](https://github.com/mojealterego/future-agi) | ORCHESTRATION / EXTERNAL_MUTATION | Platforma ocen LLM, guardrails, symulacji i obserwowalności |
| 8 | [TorBot](https://github.com/mojealterego/TorBot) | INFRASTRUCTURE / PRIVILEGED_EXECUTION | TorBot publiczny onion crawler, gałąź dev, Tor opcjonalny |
| 9 | [docs](https://github.com/mojealterego/docs) | KNOWLEDGE / READ_ONLY | Repozytorium dokumentacji Future AGI, nie Railway docs2 |
| 10 | [Stable-Diffusion-KMP](https://github.com/mojealterego/Stable-Diffusion-KMP) | ANDROID / REMOTE_ACCESS | SDAI klient Stable Diffusion z wieloma backendami |
| 11 | [elevenlabs-swift-sdk](https://github.com/mojealterego/elevenlabs-swift-sdk) | CLIENT / REMOTE_ACCESS | Swift i ElevenAgents LiveKit/WebRTC |
| 12 | [cli](https://github.com/mojealterego/cli) | MCP / EXTERNAL_MUTATION | ElevenLabs CLI Agents as Code, nie ogólny CLI |
| 13 | [Stable-Diffusion](https://github.com/mojealterego/Stable-Diffusion) | KNOWLEDGE / READ_ONLY | Katalog kursów i tutoriali, nie silnik SD |
| 14 | [create-railway](https://github.com/mojealterego/create-railway) | INFRASTRUCTURE / EXTERNAL_MUTATION | Scaffolding TypeScript sandboxów Railway |
| 15 | [Box](https://github.com/mojealterego/Box) | ANDROID / READ_ONLY | Hybrydowy klient mobilny LiteRT/GGUF/whisper, zgodność wymaga testu |
| 16 | [element-x-android](https://github.com/mojealterego/element-x-android) | ANDROID / REMOTE_ACCESS | Element X Android Matrix + Rust SDK i Compose |
| 17 | [skills](https://github.com/mojealterego/skills) | KNOWLEDGE / READ_ONLY | ElevenLabs Skills, nie OpenAI skillsOPENai |
| 18 | [base44-platform-starter](https://github.com/mojealterego/base44-platform-starter) | CLIENT / EXTERNAL_MUTATION | White label Base44, proxy API i indywidualna tożsamość użytkownika |
| 19 | [elevenlabs-js](https://github.com/mojealterego/elevenlabs-js) | CLIENT / REMOTE_ACCESS | Oficjalne ElevenLabs Node JS SDK |

## Strategia integracji

| Strumień | Źródła | Potencjalna integracja | Bramki jakości i bezpieczeństwa |
|---|---|---|---|
| Android runtime | Box, Rivo-Agent-Application, gpt_mobile | Wydzielić kontrakt modeli i sesji, przejrzeć aktywację MCP, migrację profilów oraz Keystore | Android build; test dwóch modeli GGUF; izolacja narzędzi |
| Android Linux | UserLAnd | Użyć jako referencji alternatywnego środowiska użytkownika bez uprawnień root | Osobny proces, ochrona filesystem, kontrola sandboxu |
| Mobilne obrazy | Stable-Diffusion-KMP | Projekt interfejsu providerów SD, test obsługi local/remote, świadome finansowanie API | Audyt licencji, model i GPU, test pliku wyjściowego |
| Agenci i jakość | agent-learning-kit, future-agi, docs | Ewaluacja, tracing, red-team prompts, testy regresji i raporty jakości | Rzeczywiste wyniki, walidacja metryk, koszt/PII |
| Integracje chmurowe | railway-skills, create-railway, base44-platform-starter | Dwa izolowane adaptery: deploy Railway i tworzenie aplikacji Base44 | SSO/OAuth, server-only credentials, allowlist, budżet i zgody |
| Synteza głosu | elevenlabs-swift-sdk, elevenlabs-js, cli, skills | Voice streaming, konfiguracje agentów, TTS/STT, dubbing, lokalna obudowa API | Zgoda na głos, koszt, klucze backend-only, test latencji |
| Komunikator | element-x-android | Rozpoznanie własności Matrix E2EE, modelu sesji i modułów Compose | Testy szyfrowania, kompatybilności Matrix SDK, licencji |
| OSINT | Shadowbroker, TorBot | Wyłącznie planowanie analizy publicznych źródeł i pochodzenie danych | Jawny zakres uprawnienia; brak automatycznego skanowania i crawlera |
| Materiały edukacyjne | Stable-Diffusion | Katalog tutoriali i benchmarków modeli SD | Bez automatycznego instalowania komend z poradników |

### Wyjątkowe rozstrzygnięcia

- `docs` jest dokumentacją **Future AGI**; `docs2` w paczce 04 to dokumentacja Railway.
- `skills` to umiejętności **ElevenLabs**; `skillsOPENai` w paczce 05 to przestarzałe umiejętności OpenAI/Codex.
- `cli` to **ElevenLabs CLI**; nie jest abstrakcyjną powłoką.
- `Stable-Diffusion` to poradniki i edukacja; silnik rozproszony wymaga osobnego backendu; `Stable-Diffusion-KMP` jest klientem mobilnym.
- `TorBot` wymaga osobnego autoryzowanego środowiska i nie staje się wykonywalnym agentem przez import profilu.
- `Shadowbroker` deklaruje narzędzia rozpoznania sieci, więc oznaczony jest jako `PRIVILEGED_EXECUTION` i pozostaje nieaktywny.

## Realne zmiany w THOR

- Nowy `ThorPacketSixCatalog.kt` — **19 źródeł i 14 profili**, walidacja referencji, unikalności oraz aktywacji `false`.
- Nowy `ThorPacketSixCatalogTest.kt` — scenariusze unikalności, klasyfikacji, referencji i wyników agregacji.
- `ThorPacketRegistry.kt` — szósty katalog numer `06`, wykrywany dynamicznie przez obecny ekran Android.
- `ThorPacketRegistryTest.kt` — poprawna mapa zakładek `5–10` i łączne wartości **169 źródeł oraz 100 profili**.
- Import profilu nadal tworzy prompt i metadane bez domyślnego przypisania narzędzi.

## Weryfikacja i bramy wydania

| Brama | Stan |
|---|---|
| Dostęp do 19 repozytoriów oraz odczyt Git tree i README | Zweryfikowane |
| Brak podwójnego wpisu `railway-skills` | Zweryfikowane w danych katalogu |
| Integralność 6 paczek, referencji profili i ryzyka | Kontrola statyczna kodu |
| Kompilacja Android / JUnit Gradle | Wymaga wykonania |
| Działające APK | Wymaga wykonania |
| Runtime MCP, ElevenLabs, Base44, Railway | Niepodłączony |
| Testy realnego Android / modele GGUF | Wymaga wykonania |
| Wdrożenie na `master` | Zablokowane do przejścia testów |

**Nie przedstawiać wpisu źródła jako instalacji frameworka, aktywnego MCP, faktycznego serwera ani uruchomionego agenta.**