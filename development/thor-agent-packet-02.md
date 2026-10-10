# THOR AI — paczka 02: Unity, GameDev, Android, MCP, agenci

**Data inwentaryzacji:** 2026-10-10  
**Stan:** 25/25 repozytoriów dostępnych przez autoryzowany odczyt GitHub. Przejrzano główne drzewa Git i README.  
**Cel:** `mojealterego/THOR-AI`, gałąź prac `omega/thor-agent-swarm`.

## Interpretacja

Repozytorium to źródło kodu lub wiedzy, nie uruchomiony agent. Zgłoszona paczka zawiera **25 różnych źródeł**, w tym 10 wyspecjalizowanych rozszerzeń Unity AI oraz szablon jedenastego rozszerzenia. Duża część rozszerzeń wykorzystuje wspólną bazę **Unity-MCP2** z paczki 01, a serwer `GameDev-MCP-Server` jest niezależnym procesem pomiędzy klientem MCP a edytorem silnika.

Do aplikacji Android THOR nie kopiujemy repozytoriów Unity, desktop Linux, IDA ani infrastruktury backendowej: działają one w **osobnych procesach** po autoryzowanym uruchomieniu i weryfikacji połączenia. Aplikacja staje się centrum sterowania/planowania, nie emulatorem wszystkich silników naraz.

## Rejestr źródeł

| Lp. | Repozytorium | Moduł domenowy | Minimalna zależność / granica | Klasa działań |
|---:|---|---|---|---|
| 1 | [Unity-AI-Animation](https://github.com/mojealterego/Unity-AI-Animation) | Animacja/Animator | Unity Editor + Unity-MCP | `MODIFY` |
| 2 | [Unity-AI-Timeline](https://github.com/mojealterego/Unity-AI-Timeline) | Sekwencje Timeline | Unity Editor + Timeline | `MODIFY` |
| 3 | [Unity-AI-Tilemap](https://github.com/IvanMurzak/Unity-AI-Tilemap) | Tilemap 2D/RuleTile | Unity Editor + 2D Tilemap Extras | `MODIFY` |
| 4 | [Unity-AI-Terrain](https://github.com/IvanMurzak/Unity-AI-Terrain) | Teren i mapy wysokości | Unity Editor + Terrain | `MODIFY` |
| 5 | [Unity-AI-Splines](https://github.com/mojealterego/Unity-AI-Splines) | Krzywe i splajny | Unity Editor + Splines | `MODIFY` |
| 6 | [Unity-AI-ProBuilder](https://github.com/mojealterego/Unity-AI-ProBuilder) | Modelowanie 3D | Unity Editor + ProBuilder | `MODIFY` |
| 7 | [Unity-AI-InputSystem](https://github.com/mojealterego/Unity-AI-InputSystem) | Sterowanie | Unity Editor + Input System | `MODIFY` |
| 8 | [Unity-AI-ParticleSystem](https://github.com/IvanMurzak/Unity-AI-ParticleSystem) | Particle VFX | Unity Editor + Particle System | `MODIFY` |
| 9 | [Unity-AI-Navigation](https://github.com/mojealterego/Unity-AI-Navigation) | NavMesh i AI | Unity Editor + AI Navigation | `MODIFY` |
| 10 | [Unity-AI-Cinemachine](https://github.com/mojealterego/Unity-AI-Cinemachine) | Kamery i kadrowanie | Unity Editor + Cinemachine | `MODIFY` |
| 11 | [Ai-game-factory](https://github.com/mojealterego/Ai-game-factory) | Fabryka gier E2E | Odrębne workery, game engine | `PLANNING` |
| 12 | [computer-use-linux](https://github.com/mojealterego/computer-use-linux) | Kontrola desktopu | Linux + MCP + sesja graficzna | `PRIVILEGED` |
| 13 | [Unity-AI-Tools-Template](https://github.com/mojealterego/Unity-AI-Tools-Template) | Szablon rozszerzeń | Unity-MCP / pakiet Unity | `REFERENCE` |
| 14 | [GameDev-MCP-Server](https://github.com/mojealterego/GameDev-MCP-Server) | Bramka MCP SignalR | Serwer .NET + Unity/Unreal bridge | `PRIVILEGED` |
| 15 | [ida-pro-mcp](https://github.com/mojealterego/ida-pro-mcp) | Inżynieria odwrotna | Licencjonowane IDA Pro + MCP | `PRIVILEGED` |
| 16 | [Android-AI-Studio](https://github.com/mojealterego/Android-AI-Studio) | Android + kreatywna infrastruktura | FastAPI + ComfyUI + GPU | `MODIFY` |
| 17 | [stable-diffusion.cpp](https://github.com/mojealterego/stable-diffusion.cpp) | Generowanie obrazu/wideo | C++ i kompatybilny model/GPU | `EXECUTE` |
| 18 | [AI-Rental-Hunter](https://github.com/mojealterego/AI-Rental-Hunter) | Oferty najmu | Źródła ogłoszeń + MCP | `READ_REMOTE` |
| 19 | [hackbot](https://github.com/mojealterego/hackbot) | Pentest i audyt | Zgoda na testy + izolowane środowisko | `PRIVILEGED` |
| 20 | [Ageni-AI-mojealterego](https://github.com/mojealterego/Ageni-AI-mojealterego) | Portfolio agentów | Provider OpenAI + runtime | `EXECUTE` |
| 21 | [ride-voice-agent](https://github.com/mojealterego/ride-voice-agent) | Transakcyjny agent głosowy | LiveKit + Deepgram + Gemini + Postgres | `HIGH_IMPACT` |
| 22 | [mcp-coding-agent](https://github.com/mojealterego/mcp-coding-agent) | Agent programujący MCP | Uprawnienia filesystem + process execution | `PRIVILEGED` |
| 23 | [AgentGPT](https://github.com/mojealterego/AgentGPT) | Framework agentowy web | Frontend + API + baza | `EXECUTE` |
| 24 | [Rap-Agent](https://github.com/mojealterego/Rap-Agent) | Kreator muzyki i tekstu | Modele muzyki/voice + pamięć | `CREATIVE` |
| 25 | [ai-game-builder](https://github.com/mojealterego/ai-game-builder) | Szkielet gry Unity | Unity Project + build | `REFERENCE` |

## Implementacja THOR w tym PR

- `ThorPacketTwoCatalog.kt`: 25 unikalnych źródeł z identyfikacją Git, gałęzią, kategorią, klasą ryzyka, opisem i `activated = false`.
- `ThorPacketTwoCatalogTest.kt`: spójność 25 źródeł, 15 ról, 11 projektów `Unity-AI-*`, wymagania blokady narzędzi uprzywilejowanych oraz brak konfliktów z paczką 01.
- `ThorPacketOnePanel.kt`: jeden współdzielony komponent Compose dla obu paczek bez duplikacji widoków.
- `NewGptStudioScreen.kt`: nowa karta **ŹRÓDŁA 02** z importem specjalistów do AGENTS.
- **15 nowych profili**: Unity Animation, Cinematics, World, 2D Tilemap, Gameplay, VFX, Game Factory, Linux Desktop, Android Creative, Diffusion, Rental Scout, Defensive Security, Agent Builder, Voice, Rap. Wszystkie nowe role mają puste `tools` i `handoffs` i nie przejmują uprawnień repozytoriów.

Łącznie z paczką 01 katalog THOR prezentuje **48 unikalnych repozytoriów i 24 profili**, bez pomylenia katalogowania z instalacją. Profile są importowane wyłącznie na żądanie użytkownika i nie nadpisują wcześniejszych wpisów o tych samych identyfikatorach.

## Rozdział architektury E2E

| Moduł | Wykonawca docelowy | Dopuszczalne rozpoczęcie |
|---|---|---|
| Unity narzędzia `Unity-AI-*` | Unity Editor + wtyczki + GameDev MCP + serwer .NET | Po zgodności wersji, instalacji i handshake MCP |
| Factory / Builder | Izolowany kontener/worker silnika gry | Po wskazaniu projektu i testach na assetach |
| Desktop GUI | Linux MCP, AT-SPI, portal Wayland | Po przydzieleniu ograniczeń sesji i jawnej zgody |
| Reverse engineering | IDA Pro MCP | Tylko uprawnione pliki i systemy |
| Kreatywne generowanie | `stable-diffusion.cpp` albo prywatny ComfyUI | Po sprawdzeniu modelu, zasobów GPU i limitów kosztów |
| Android Creative | Android -> HTTPS FastAPI -> prywatny ComfyUI | Po uruchomieniu API, tokenów i testu kolejki |
| Coding Agents | Odrębny runtime + MCP + izolowana przestrzeń robocza | Po konfiguracji policy gate i testów code execution |
| Voice Dispatcher | LiveKit + Deepgram + Gemini + własny backend transakcji | Po jawnym upoważnieniu do każdej operacji zamówienia |
| Rental Hunter | MCP i zaakceptowane publiczne źródła ofert | Po weryfikacji uprawnień i limitów portali |
| SecOps | Tylko audyt defensywny i upoważnione cele | Bez domyślnego skanowania zewnętrznych systemów |

## Jawne ograniczenia

1. **Brak potwierdzenia pracy serwerów.** Repozytoria, dokumentacja i profile to nie aktywny runtime.
2. **Nie skopiowano binariów ani bibliotek zewnętrznych.** Obowiązuje audyt licencji i integralności dla każdej zależności.
3. **Brak testów end-to-end silnika Unity**, urządzenia Android, sesji Wayland, IDA Pro lub serwera ComfyUI; wymagane środowiska nie są dostępne w hostowanym narzędziu GitHub.
4. **Niezweryfikowany build całej aplikacji THOR** przed przejściem CI.
5. README jest dokumentacją projektów źródłowych; informacje marketingowe nie są dowodem wykonanych testów.
6. Podczas uruchamiania agentów należy uzyskiwać odrębne potwierdzenie narzędzia/adaptera oraz wynik wraz z postcondition, zamiast traktować odpowiedź modelu jako dowód działania.
