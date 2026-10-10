# THOR AI — paczka źródeł 01

**Data przeglądu:** 2026-10-10  
**Repozytorium docelowe:** `mojealterego/THOR-AI` (`master`)  
**Gałąź prac:** `omega/thor-agent-swarm`

## Zakres i zasady

Użytkownik przekazał 24 odsyłacze GitHub; po usunięciu powtórzenia `mojealterego/OmniMAS-Advanced` pozostają **23 unikalne repozytoria**. Dla wszystkich 23 sprawdzono dostępność i główną strukturę Git, a także podstawową treść README tam, gdzie była dostępna.

**Repozytorium nie jest automatycznie agentem.** Lista zawiera źródła, katalogi, klientów, infrastrukturę, protokoły i usługi zewnętrzne. Stan `activated = false` w katalogu aplikacji jest świadomym kontraktem: żadna nowa integracja zewnętrzna nie jest automatycznie uruchamiana przez import.

Nie kopiujemy źródeł ani zależności do aplikacji bez odrębnego audytu licencji, pochodzenia, wersji, bezpieczeństwa i rzeczywistych potrzeb. Pozwala to utrzymać istniejące moduły Kotlin i uniknąć niekontrolowanego monolitu.

## Ewidencja

| Lp. | Źródło | Funkcja | Status wdrożeniowy |
|---:|---|---|---|
| 1 | [Agent-God-Level-Omega](https://github.com/mojealterego/Agent-God-Level-Omega) | Orkiestracja i zapewnianie jakości | profil architekta i QA |
| 2 | [awesome-free-llm-apis](https://github.com/mojealterego/awesome-free-llm-apis) | Katalog dostawców LLM | referencja, bez automatycznej rejestracji API |
| 3 | [tinyfish-cookbook](https://github.com/mojealterego/tinyfish-cookbook) | Wyszukiwanie i automatyzacja przeglądarki | profil badacza, adapter do kwalifikacji |
| 4 | [mcp-context-forge](https://github.com/mojealterego/mcp-context-forge) | Bramka/federacja MCP oraz A2A | profil integratora, osobny serwer wymagany |
| 5 | [firecrawl-mcp-server](https://github.com/firecrawl/firecrawl-mcp-server) | Ekstrakcja treści i web MCP | profil badacza, połączenie nieaktywne |
| 6 | [awesome-mcp-servers](https://github.com/mojealterego/awesome-mcp-servers) | Katalog serwerów MCP | referencja |
| 7 | [DesktopCommanderMCP](https://github.com/mojealterego/DesktopCommanderMCP) | Dostęp do plików i procesów | uprzywilejowany adapter, wstrzymany |
| 8 | [mobile-mcp](https://github.com/mojealterego/mobile-mcp) | Automatyzacja Android/iOS | uprzywilejowany adapter, wstrzymany |
| 9 | [anx-reader](https://github.com/mojealterego/anx-reader) | Czytanie dokumentów | profil wiedzy; nie importujemy całej aplikacji |
| 10 | [pangolin](https://github.com/mojealterego/pangolin) | Zero-trust / SASE | wzorzec infrastruktury, nie komponent mobilny |
| 11 | [github-mcp-server](https://github.com/mojealterego/github-mcp-server) | GitHub przez MCP | profil integratora, w host-czasie przez autoryzowane złącze |
| 12 | [gemini-notebook-mcp-cli](https://github.com/mojealterego/gemini-notebook-mcp-cli) | Notebook / źródła wiedzy | wymaga odrębnego logowania i ochrony sesji |
| 13 | [book-to-skill](https://github.com/virgiliojr94/book-to-skill) | Przetwarzanie źródeł do Skill | profil wiedzy, bez automatycznego wykonywania treści |
| 14 | [OmniMAS-Advanced](https://github.com/mojealterego/OmniMAS-Advanced) | Agent Android Accessibility | profil Android, zewnętrzne sterowanie wyłączone |
| 15 | [wda-photo-agent](https://github.com/mojealterego/wda-photo-agent) | Fotografia i Firefly | profil mediów, bez aktywnych zewnętrznych API |
| 16 | [Agentic-Cinema-The-Blockbuster-Hackathon](https://github.com/mojealterego/Agentic-Cinema-The-Blockbuster-Hackathon) | Wieloagentowa produkcja filmowa | profil mediów |
| 17 | [Codex-Android](https://github.com/mojealterego/Codex-Android) | Rozwój kodu z telefonu | profil Android, bez deklaracji działania Codex |
| 18 | [Unity-MCP2](https://github.com/mojealterego/Unity-MCP2) | Automatyzacja Unity | profil gier, edytor niepodłączony |
| 19 | [Unreal-MCP](https://github.com/mojealterego/Unreal-MCP) | Automatyzacja Unreal | profil gier, edytor niepodłączony |
| 20 | [AI-Game-Dev-CLI-Core](https://github.com/mojealterego/AI-Game-Dev-CLI-Core) | Wspólny rdzeń CLI gier | profil gier, osobne uruchomienie konieczne |
| 21 | [honcho](https://github.com/mojealterego/honcho) | Pamięć stanowych agentów | profil wiedzy, backend niepodłączony |
| 22 | [chat-ui](https://github.com/mojealterego/chat-ui) | Web frontend LLM | referencja UI; bez przenoszenia całego SvelteKit |
| 23 | [Azure-mcp](https://github.com/mojealterego/Azure-mcp) | Enterprise MCP Registry | profil integratora, katalog |

## Zaimplementowane w aplikacji

- `ThorPacketOneCatalog.kt`: 23 rekordy z identyfikatorem, gałęzią, referencją, klasą możliwości, ryzykiem i wyłączonym stanem aktywacji.
- `ThorPacketOnePanel.kt`: widok katalogu w aplikacji Android i import profili.
- `NewGptStudioScreen.kt`: karta **ŹRÓDŁA 01** z importem do już istniejącego menedżera agentów.
- `ThorPacketOneCatalogTest.kt`: testy unikalności źródeł, powiązania profili, znaczników rewizji i braku automatycznej aktywacji.
- **9 profili lokalnych:** architekt, integrator MCP, badacz, inżynier Android, audytor bezpieczeństwa, kustosz wiedzy, inżynier gier, dyrektor kreatywny i QA. Profile są oryginalnymi, ograniczonymi instrukcjami roli — nie importowanymi promptami wymagającymi szerszych uprawnień.

Import profilu jest idempotentny: istniejący profil o tym samym ID pozostaje bez zmian. Każdy nowo zaimportowany profil ma puste `tools` i `handoffs`. Użytkownik może sam wskazać handoffy i uruchamiać je dopiero, gdy obecny runtime GGUF potwierdzi gotowość.

## Zastrzeżenia weryfikacyjne

- Odczyt repozytoriów i przegląd dokumentacji nie jest testem działającego serwera.
- Nie wykonano instalacji repozytoriów do urządzenia użytkownika.
- Brak deklaracji aktywnego połączenia MCP, działającej przeglądarki, Unity, Unreal, Firefly czy Notebook.
- Pełny Android APK/Gradle, testy instrumentacyjne, zgodność licencji oraz zewnętrzne scenariusze końcowe wymagają oddzielnych wyników.
- Dalsza integracja każdego źródła wymaga konkretnego kontraktu API, autoryzacji, przydziału uprawnień i udokumentowanych testów.

**Scalenie:** wyłącznie po niezależnych testach, przeglądzie bezpieczeństwa i ocenie kompatybilności z PR #2 (utwardzenie GitHub Connector).
