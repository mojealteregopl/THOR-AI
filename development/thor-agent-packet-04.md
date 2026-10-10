# THOR AI — Paczka 04: 33 źródła, 18 profili

**Data:** 2026-10-10  
**Repozytorium:** `mojealterego/THOR-AI`  
**Gałąź prac:** `omega/thor-agent-swarm`

## Metodologia weryfikacji

Dla każdego z 33 repozytoriów odczytano stan GitHub, nazwę aktualnej gałęzi domyślnej i SHA jej głównego **drzewa Git** (nie commit SHA). Sprawdzono główne pliki i README. Dla źródeł o szczególnych możliwościach wykonawczych określono najniższy sensowny poziom uprawnień; w katalogu `activated=false`.

Katalog nie dowodzi działania serwera, dostępności urządzenia, zgodności bibliotek lub prawa do włączenia całych źródeł do APK. **W repozytorium THOR nie zainstalowano, nie uruchomiono i nie podłączono tych 33 projektów.**

## Inwentarz

| Lp. | Repozytorium | Typ rzeczywisty | Ryzyko | Decyzja |
|---:|---|---|---|---|
| 1 | [L3MON-1](https://github.com/mojealterego/L3MON-1) | Android RAT | RESTRICTED | Dostęp do SMS, mikrofonu, plików; brak ścieżki wykonywania |
| 2 | [chappie-bot](https://github.com/mojealterego/chappie-bot) | Bot komunikacyjny | Wysokie | Historyczny Python/Docker; widoczne w root nazwy plików mogące zawierać sekrety |
| 3 | [lore](https://github.com/mojealterego/lore) | VCS | Wysokie | Kontrola wersji dużych artefaktów; status pre-1.0 |
| 4 | [Flowise](https://github.com/mojealterego/Flowise) | Agenci/workflow | Średnie | Flow builder; README wskazuje archiwalny stan upstream |
| 5 | [xalgorix](https://github.com/mojealterego/xalgorix) | Pentest | Wysokie | Tylko autoryzowany audyt i izolowany zakres |
| 6 | [mailtm-client](https://github.com/mojealterego/mailtm-client) | Disposable email | Wysokie | Nie łączyć z automatyczną rejestracją kont |
| 7 | [mcp-server](https://github.com/mojealterego/mcp-server) | Bramka SMS/OTP | RESTRICTED | Broker weryfikacji kont i zakup numerów; brak profilu wykonawczego |
| 8 | [railpack](https://github.com/mojealterego/railpack) | DevOps | Wysokie | Budowanie obrazów OCI z kodu |
| 9 | [milvus](https://github.com/mojealterego/milvus) | Baza danych | Średnie | Wektorowa wyszukiwarka i RAG |
| 10 | [docs2](https://github.com/mojealterego/docs2) | Dokumentacja | Niskie | Dokumentacja Railway, nie osobny agent |
| 11 | [phidata](https://github.com/mojealterego/phidata) | Agno/AgentOS | Średnie | Runtime i usługi agentów Agno |
| 12 | [react-native-windows](https://github.com/mojealterego/react-native-windows) | Mobile/Desktop | Niskie | React Native dla Windows |
| 13 | [llama_index](https://github.com/mojealterego/llama_index) | RAG | Średnie | Parsowanie i wyszukiwanie dokumentów |
| 14 | [ToolJet](https://github.com/mojealterego/ToolJet) | Low-code | Wysokie | Budowa aplikacji operacyjnych |
| 15 | [compose-multiplatform](https://github.com/mojealterego/compose-multiplatform) | UI framework | Niskie | Współdzielony interfejs Kotlin |
| 16 | [crewAI](https://github.com/mojealterego/crewAI) | Agenci | Średnie | Crews i Flows; specjalistyczna orkiestracja |
| 17 | [Awesome-Quantum-Computing-Platform](https://github.com/mojealterego/Awesome-Quantum-Computing-Platform) | Katalog | Niskie | Katalog obliczeń kwantowych, nie procesor QPU |
| 18 | [baresip-studio](https://github.com/mojealterego/baresip-studio) | Telefonia Android | Wysokie | SIP i kodeki, prywatność połączeń |
| 19 | [brave-browser](https://github.com/mojealterego/brave-browser) | Dokumentacja | Niskie | Repo Issues/releases; źródła Brave w brave-core |
| 20 | [GDevelop](https://github.com/mojealterego/GDevelop) | Game Engine | Wysokie | Edytor no-code gier |
| 21 | [Open-Generative-AI](https://github.com/mojealterego/Open-Generative-AI) | Media | Wysokie | Generowanie przez platformę i dostawców zewnętrznych |
| 22 | [local-dream](https://github.com/mojealterego/local-dream) | Android/Media | Wysokie | Dyfuzja lokalna na Snapdragon NPU |
| 23 | [ragflow](https://github.com/mojealterego/ragflow) | RAG | Średnie | Parsing, context engine i agent templates |
| 24 | [bisheng](https://github.com/mojealterego/bisheng) | Enterprise AI | Wysokie | Workflow z human in the loop |
| 25 | [saltcorn](https://github.com/mojealterego/saltcorn) | No-code | Wysokie | Aplikacje na bazie danych |
| 26 | [gonnect](https://github.com/mojealterego/gonnect) | Telefonia desktop | Wysokie | SIP, kalendarze i kontakty |
| 27 | [MaxKB](https://github.com/mojealterego/MaxKB) | Knowledge | Wysokie | RAG/agent platform, wymagana zmiana haseł startowych |
| 28 | [budibase](https://github.com/mojealterego/budibase) | Low-code | Wysokie | Biznesowe aplikacje i automatyzacje |
| 29 | [stryker-js](https://github.com/mojealterego/stryker-js) | QA | Niskie | Testy mutacyjne JS/TS |
| 30 | [FastGPT](https://github.com/mojealterego/FastGPT) | RAG/Agents | Wysokie | Workflow i RAG, wymagane bezpieczne hasła |
| 31 | [artemis](https://github.com/mojealterego/artemis) | Android Automation | Wysokie | Real device automation w izolowanym profilu |
| 32 | [mobile-use](https://github.com/mojealterego/mobile-use) | Android/iOS Automation | Wysokie | Autoryzowana kontrola UI urządzeń |
| 33 | [material-design-icons](https://github.com/mojealterego/material-design-icons) | UI assets | Niskie | Material Symbols, nie pełny silnik aplikacji |

## Istotne rozstrzygnięcia i ograniczenia

1. `L3MON-1` jest oparty na projekcie Android RAT, którego README deklaruje zbieranie informacji z mikrofonu, GPS, SMS, schowka oraz plików. Repozytorium służy najwyżej defensywnemu rozpoznaniu zagrożeń. **Nie dostaje profilu agenta, połączenia ani funkcji uruchomienia.**
2. `mcp-server` okazuje się klientem brokera **płatnych numerów SMS/OTP**, nie uniwersalnym serwerem MCP. Został sklasyfikowany jako `RESTRICTED` bez połączenia. `mailtm-client` to klient jednorazowej poczty; żadna z obu technologii nie trafia do profili automatyzującej rejestracji kont.
3. W drzewie `chappie-bot` są pliki nazwane `client_secrets.json` i `apikrypton.txt`. **Nie odczytywano ani nie kopiowano ich treści.** Same nazwy uzasadniają ręczny audyt ekspozycji sekretów w tym repozytorium. Nie przypisano repozytorium do żadnego profilu.
4. `Flowise`: README wskazuje na archiwizację projektu **upstream**; samo repozytorium `mojealterego/Flowise` nie było oznaczone przez API GitHub jako zarchiwizowane. Należy osobno ocenić dalsze utrzymanie i podatności.
5. `lore` oznacza system kontroli wersji dużych plików rozwijany przez Epic Games, nie bazę lore wirtualnych postaci. Dokumentacja określa go jako **pre-1.0**.
6. `docs2` to dokumentacja **Railway**. `phidata` na tej gałęzi przedstawia **Agno AgentOS**. `brave-browser` to przede wszystkim repozytorium zgłoszeń, wydań i Wiki; do budowania przeglądarki konieczny jest odrębny `brave-core`.
7. `MaxKB` i `FastGPT` dokumentują domyślne dane administracyjne. Te wdrożenia wymagają twardej polityki zmiany haseł, ograniczenia dostępu sieciowego oraz ścieżki audytu; nie można wystawiać ich produkcyjnie bez hardeningu.
8. `xalgorix`, `mobile-use`, `artemis`, `baresip-studio`, `gonnect` i `railpack` wymagają rzeczywistego środowiska, indywidualnych uprawnień oraz mierzalnego potwierdzenia działania.

## Implementacja THOR

| Plik | Zakres |
|---|---|
| `app/src/main/java/com/newoether/agora/newgpt/ThorPacketFourCatalog.kt` | 33 źródła + 18 profilów, weryfikacja SHA, wykluczenie `RESTRICTED` |
| `app/src/test/java/com/newoether/agora/newgpt/ThorPacketFourCatalogTest.kt` | Testy kontraktowe kwarantanny i braku konfliktów między paczkami |
| `app/src/main/java/com/newoether/agora/newgpt/ThorPacketRegistry.kt` | Skalowalny spis wszystkich paczek, walidacja globalnych duplikatów i pochodzenia |
| `app/src/main/java/com/newoether/agora/newgpt/ThorPacketOnePanel.kt` | Jeden wspólny ekran Compose dla dowolnej paczki |
| `app/src/main/java/com/newoether/agora/newgpt/NewGptStudioScreen.kt` | Zakładki `ŹRÓDŁA nn` tworzone dynamicznie z rejestru |

**Bilans po paczce 04:** 23 + 25 + 40 + 33 = **121 unikalnych źródeł**, 9 + 15 + 24 + 18 = **66 profilów ról**.

Profile mogą być importowane ręcznie do AGENTS i wykorzystywane w już istniejącym lokalnym THOR SWARM, jeśli działa dwumodelowa inferencja GGUF. Import nie przyznaje automatycznie `tools` ani `handoffs`, a zarządzanie zewnętrznymi usługami nie ma jeszcze adaptera wykonawczego.

## Bramy weryfikacji i wdrożenia

- Kompilacja i testy pełnego projektu Android/Gradle, w tym `ThorPacketFourCatalogTest` i `verifyKotlinFileSize` — **niepotwierdzone**.
- Kompilacja APK, instalacja, test GGUF i stan zapisany w Android Preferences — **niepotwierdzone**.
- Połączenia MCP, workflow z prawdziwymi serwerami, automatyzacja telefonu i streaming SIP — **nieuruchamiane**.
- Import źródeł obcych frameworków do monorepo THOR — **niedozwolony bez indywidualnego przeglądu licencji i bezpieczeństwa**.
- Kod katalogu i rejestru sprawdzany statycznie przez odczyt GitHub; statyczna kontrola nie zastępuje testów wykonaniowych.
