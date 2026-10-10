# THOR AI: paczka 09 — audyt źródeł

Data: 2026-10-10. Gałąź: `omega/thor-agent-swarm`. Sprawdzono 29 repozytoriów przez GitHub API, ich gałęzie, drzewa Git i dokumentację (28 plików README.md oraz README.txt w sipdroid). Poniższe wpisy pozostają **nieaktywne**.

| Lp. | Repozytorium | Faktyczna funkcja |
|---:|---|---|
| 1 | `railway-design` | Next.js design system |
| 2 | `hexstrike-ai` | Kwarantanna: narzędzia bezpieczeństwa ofensywnego |
| 3 | `OnlineSimBot2` | Kwarantanna: tymczasowe numery telefonów |
| 4 | `alexandria-audiobook` | Generowanie audiobooków i narracji |
| 5 | `Text-To-Video-API` | Dokumentacja zewnętrznego API wideo |
| 6 | `openai-chatkit-advanced-samples` | React i FastAPI ChatKit |
| 7 | `ShipinKit` | Swift API do generowania wideo |
| 8 | `hmdm-docker` | MDM dla własnych urządzeń Android |
| 9 | `claude-code-android` | Claude Code na Android/Termux |
| 10 | `500-AI-Agents-Projects` | Przykłady systemów wieloagentowych |
| 11 | `SNK-RAT` | Kwarantanna: oprogramowanie wysokiego ryzyka |
| 12 | `Awesome-Hacking` | Katalog referencji do bezpieczeństwa |
| 13 | `Resolver-Stable-Diffusion-Client-for-android` | Android klient Forge i ComfyUI |
| 14 | `ChatDev` | Orkiestracja ChatDev 2.0 |
| 15 | `agent-starter-pack` | Google Cloud starter w maintenance mode |
| 16 | `OpenDevin` | Historyczny coding agent |
| 17 | `suno-cli` | Nieoficjalny klient Suno |
| 18 | `TempMailBot` | Kwarantanna: skrzynki tymczasowe |
| 19 | `JieMa_2025` | Kwarantanna: lista usług kodów SMS |
| 20 | `elevenlabs/elevenlabs-n8n` | Oficjalna integracja ElevenLabs n8n |
| 21 | `G0DM0D3` | Interfejs wielu modeli LLM |
| 22 | `L3MON` | Kwarantanna: oprogramowanie wysokiego ryzyka |
| 23 | `futureagi-sdk` | Ewaluacja i observability LLM |
| 24 | `OmniVideo-100K` | Zbiór danych i benchmark AV |
| 25 | `VoIP-Spoofing-Research` | Kwarantanna: badania nadużyć VoIP |
| 26 | `unity` | ElevenAgents Unity SDK |
| 27 | `actions` | GitHub Actions dla MCP |
| 28 | `sipdroid` | Klient SIP dla Androida |
| 29 | `bulk-email-scraper` | Kwarantanna: masowe pozyskiwanie e-maili |

## Implementacja

`ThorPacketNineCatalog.kt` zawiera 29 rekordów źródeł, 20 profili oraz blokadę wskazywania pozycji RESTRICTED w profilach. `ThorPacketNineCatalogTest.kt` definiuje testy 29 źródeł, 20 ról, poprawności SHA i 8 ograniczeń. `ThorPacketRegistry.kt` obejmuje teraz paczkę 09. Łączne wielkości: 256 unikalnych repozytoriów, 160 profili konfiguracyjnych oraz 18 pozycji RESTRICTED.

## Ustalenia architektoniczne

- `unity` to ElevenAgents Unity SDK, a nie silnik Unity.
- `elevenlabs/elevenlabs-n8n` należy do organizacji ElevenLabs, więc zachowano jego rzeczywistą nazwę.
- `Text-To-Video-API` jest repozytorium dokumentacyjnym — nie gotowym backendem.
- `agent-starter-pack` działa według własnego README w maintenance mode.
- `OpenDevin` jest historycznym systemem, który należy odróżnić od nowszego OpenHands.
- `suno-cli` jest nieoficjalną integracją, której nie wolno automatycznie autoryzować.
- `sipdroid` to historyczny kod Android SIP z licencją GPL.
- `G0DM0D3` wymaga przeglądu prywatności ze względu na deklarowaną telemetrię.
- Wartości `revision` są SHA drzew Git, a nie wynikami testów.
- Osobne runtime Android, n8n, ChatDev, serwer MDM i systemy multimedialne nie zostały uruchomione.

## Priorytety wykonawcze

1. Adapter wykonawczy MCP z weryfikacją hosta, autoryzacją, limitami i kontrolą operacji przed wykonaniem.
2. Walidacja lokalnej orkiestracji agentów i odtwarzalne testy Gradle.
3. Rozdzielenie runtime Android od usług po stronie serwera i zewnętrznych dostawców.
4. Sprawdzenie zgodności licencyjnej, modelu kosztów i dostępu do zasobów.
5. Budowa i podpisanie APK, testy urządzenia oraz rzeczywiste wyniki CI.

**Granica faktów:** statyczny rejestr repozytoriów i profili nie oznacza uruchomienia 160 agentów; PR musi pozostać draft do uzyskania wiarygodnych testów.