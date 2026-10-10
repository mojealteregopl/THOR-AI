# THOR AI · PACZKA 08 — Unreal / Godot MCP, policy gate, pamięć, wideo i telefonia

**Data audytu źródeł:** 2026-10-10  
**Repozytorium THOR:** `mojealterego/THOR-AI`  
**Gałąź robocza:** `omega/thor-agent-swarm`  
**Wynik:** **28 zbadanych źródeł** oraz **22 profile konfiguracyjne**; kumulatywnie **227 unikalnych źródeł i 140 profili**.

## Metoda weryfikacji

Przez połączony dostęp GitHub odczytano dla 28 repozytoriów: metadane, gałąź domyślną, korzeń drzewa Git (obiekt SHA-1) i README. Źródła są identyfikowane przez dane repozytorium `repository + branch + Git tree SHA`. Nie oznacza to atestacji kompilacji, zależności, aktualności API ani rzeczywistego podłączenia MCP. Każdy rekord ma `activated=false`.

## Inwentarz potwierdzony źródłowo

| # | Repozytorium | Obszar | Rzeczywiste zastosowanie według źródła | Ryzyko |
|---:|---|---|---|---|
| 1 | [ComfyUI-LTXVideo](https://github.com/mojealterego/ComfyUI-LTXVideo) | Wideo | Rozszerzenie ComfyUI LTX-2.3, GPU od 32 GB VRAM wg README | `PRIVILEGED_EXECUTION` |
| 2 | [homebrew-engram](https://github.com/mojealterego/homebrew-engram) | Pamięć | Homebrew instalator Engram; daemon synchronizuje transkrypty do konta | `REMOTE_ACCESS` |
| 3 | [sanna](https://github.com/mojealterego/sanna) | Governance | Sanna pre-execution gateway i post-execution receipts | `REMOTE_ACCESS` |
| 4 | [NekokoLPA](https://github.com/mojealterego/NekokoLPA) | Android/eSIM | Aplikacja LPA Android/iOS, czytniki OMAPI i CCID | `PRIVILEGED_EXECUTION` |
| 5 | [cookbooks](https://github.com/mojealterego/cookbooks) | Ewaluacja | Future AGI TraceAI SDK przykłady wielu typów agentów | `READ_ONLY` |
| 6 | [stitch-skills](https://github.com/mojealterego/stitch-skills) | Projektowanie | Google Stitch Skills i pluginy Agent Skills | `READ_ONLY` |
| 7 | [pentagram](https://github.com/mojealterego/pentagram) | Pamięć | S-expression event-log oraz replay wykonywalnych wyrażeń | `PRIVILEGED_EXECUTION` |
| 8 | [Unreal-AI-Template](https://github.com/mojealterego/Unreal-AI-Template) | Unreal MCP | Szablon C++ implementacji pluginów Unreal | `READ_ONLY` |
| 9 | [Unreal-AI-PCG](https://github.com/mojealterego/Unreal-AI-PCG) | Unreal MCP | Procedural Content Generation, read i mutation | `EXTERNAL_MUTATION` |
| 10 | [Unreal-AI-Niagara](https://github.com/mojealterego/Unreal-AI-Niagara) | Unreal MCP | README nadal opisuje szablon; NIE potwierdza pełnego VFX runtime | `READ_ONLY` |
| 11 | [Unreal-AI-Landscape](https://github.com/mojealterego/Unreal-AI-Landscape) | Unreal MCP | Inspekcja Landscape/Water read-only | `READ_ONLY` |
| 12 | [Unreal-AI-GAS](https://github.com/mojealterego/Unreal-AI-GAS) | Unreal MCP | GameplayAbility System, inspekcja oraz mutacja | `EXTERNAL_MUTATION` |
| 13 | [Unreal-AI-EnhancedInput](https://github.com/mojealterego/Unreal-AI-EnhancedInput) | Unreal MCP | Konfiguracja Enhanced Input, tworzenie assetów | `EXTERNAL_MUTATION` |
| 14 | [Godot-AI-Tools-Template](https://github.com/mojealterego/Godot-AI-Tools-Template) | Godot MCP | Szablon C# source-only NuGet i testbed | `READ_ONLY` |
| 15 | [prim-ftpd](https://github.com/mojealterego/prim-ftpd) | Android Filesystem | Serwer FTP/SFTP z dostępem do plików Android | `PRIVILEGED_EXECUTION` |
| 16 | [MumbleLink](https://github.com/mojealterego/MumbleLink) | Game audio | Minecraft Forge mod do przestrzennego dźwięku Mumble | `REMOTE_ACCESS` |
| 17 | [duix-skills](https://github.com/mojealterego/duix-skills) | Avatar | DUIX cyfrowy człowiek: interaktywny avatar i lipsync | `EXTERNAL_MUTATION` |
| 18 | [EchoPBX](https://github.com/mojealterego/EchoPBX) | VoIP | Beta Asterisk/PBX, lokalne numery i kolejki | `EXTERNAL_MUTATION` |
| 19 | [skills-phone](https://github.com/mojealterego/skills-phone) | RESTRICTED | SV Number zamawianie numerów i OTP do tworzenia kont | `RESTRICTED` |
| 20 | [ChatGPT-CodeReview](https://github.com/mojealterego/ChatGPT-CodeReview) | DevSecOps | Bot GitHub PR recenzji kodu, API zewnętrzne | `EXTERNAL_MUTATION` |
| 21 | [agentskills](https://github.com/mojealterego/agentskills) | Agents | Agent Skills SKILL.md standard i progressive disclosure | `READ_ONLY` |
| 22 | [Meta3D](https://github.com/mojealterego/Meta3D) | Web3D | Webowa platforma low-code 3D i edytor | `EXTERNAL_MUTATION` |
| 23 | [TTS-Latency-Benchmark](https://github.com/mojealterego/TTS-Latency-Benchmark) | QA audio | TTFB TTFA i cisza początkowa | `READ_ONLY` |
| 24 | [PhoneClaw](https://github.com/mojealterego/PhoneClaw) | iOS | Natywny runtime AI iOS, lokalne skills telefonu | `PRIVILEGED_EXECUTION` |
| 25 | [duix-doc](https://github.com/mojealterego/duix-doc) | Dokumentacja | DUIX Mintlify dokumentacja techniczna | `READ_ONLY` |
| 26 | [TTS-Voice-Direction-Benchmark](https://github.com/mojealterego/TTS-Voice-Direction-Benchmark) | QA audio | Voice Direction Score i speaker similarity | `READ_ONLY` |
| 27 | [TTS-Voice-Design-Benchmark](https://github.com/mojealterego/TTS-Voice-Design-Benchmark) | QA audio | Role Fit, transcript pass rate, voice diversity | `READ_ONLY` |
| 28 | [pegasus-skills](https://github.com/mojealterego/pegasus-skills) | SaaS/Django | SaaS Pegasus upgrades i merge conflicts, bez związku ze spyware | `READ_ONLY` |

## Kluczowe rozstrzygnięcia

1. **`pegasus-skills` ≠ `pegasus_spyware`**. Pierwsze repozytorium zawiera narzędzia Agent Skills do aktualizacji **SaaS Pegasus/Django** i jest źródłem dokumentacyjnym `READ_ONLY`. Nie ma podstaw do jego utożsamiania ze spyware.
2. **`skills-phone` = SV Number**, narzędzia do nabywania numerów i odczytywania OTP na potrzeby rejestracji usług. Źródło sklasyfikowano `RESTRICTED`; bez profilu importowalnego, automatycznego obchodzenia kontroli tożsamości i zakupów numerów.
3. **`Unreal-AI-Niagara`**: README nadal powtarza opis szablonu `Unreal AI Template`; dopóki nie zostaną przebadane rzeczywiste handlery i kompilacja w UE, nie wolno oznaczać go jako działającego adaptera Niagara.
4. **`sanna`** odróżnia `@sanna_observe` (kontrola i poświadczenie *po* wykonaniu) od gateway/interceptor, które egzekwują politykę *przed* wykonaniem. Sam post-execution receipt nie zapobiega skutkom nieuprawnionych działań.
5. **`pentagram`** pamięta kod jako S-expression i może go odtwarzać przez `replay`. Nie należy uruchamiać treści pobranych z pamięci bez oddzielnego policy gate. Pamięć to dane, nie uprawnienie wykonawcze.
6. **`homebrew-engram`** może automatycznie synchronizować prywatne transkrypty Claude Code do zewnętrznego Engram. Brak automatycznego uruchomienia usługi i transferu danych w THOR.
7. **`ComfyUI-LTXVideo`** wymaga według README ComfyUI, GPU z co najmniej 32 GB VRAM oraz 100 GB miejsca na modele i cache. Wartości te są dokumentacją upstream, nie dokonanym pomiarem THOR.
8. **`PhoneClaw`** to runtime natywny **iOS Swift**, nie APK na Android; mechanizmy dostępu do kontaktów, kalendarza i danych osobowych wymagają niezależnej autoryzacji.
9. **`Unreal-AI-PCG`, `Unreal-AI-GAS`, `Unreal-AI-EnhancedInput`** posiadają operacje modyfikujące zasoby edytora. **`Unreal-AI-Landscape`** dostarcza inspekcję read-only. Profile nie nadają uprawnień do zmian.
10. **`NekokoLPA`** zajmuje się eSIM/LPA (nie klasycznym softphone). **`MumbleLink`** to mod pozycyjnego audio do Minecraft/Mumble, nie centrala VoIP. **`EchoPBX`** to beta serwera Asterisk, nie aplikacja mobilna.
11. Trzy benchmarki TTS definiują odrębne miary: **TTFA/TTFB**, **Voice Direction Score/similarity**, **Voice Design Role Fit/diversity**. Nie wykonano ich ani nie uzyskano wyniku dla THOR.

## Integracja E2E

| Moduł | Kandydaci | Architektura docelowa | Gate do wykonania |
|---|---|---|---|
| Agent policy plane | Sanna | Deterministyczny preflight -> auth -> tool gateway -> receipt -> postcondition | Przed wykonaniem fail-closed, signed receipts, test TOCTOU |
| Pamięć | Pentagram / Engram | Separate memory service, provenance, retencja, brak replay | Zgoda na transfer, kontrola wyrażeń, szyfrowanie |
| Unreal MCP | Template, PCG, Niagara, Landscape, GAS, EnhancedInput | Unreal Editor C++ provider -> izolowana usługa MCP -> THOR | Kompilacja UE/UBT, test narzędzi, możliwość rollback |
| Godot MCP | Godot-AI-Tools-Template | Godot.NET C# source-only package -> Godot headless tests -> MCP | xUnit, Godot consumer build, autoryzacja |
| Video | ComfyUI-LTXVideo | Worker GPU -> kolejka -> model assets -> kontrolowany artifact store | VRAM, koszt, wynik plikowy i test odtwarzania |
| Voice / łączność | EchoPBX, MumbleLink, duix-skills | Kontrolowana brama SIP / avatar / audio events | Zgoda, koszty, tokeny, ochrona wizerunku i głosu |
| Android edge | NekokoLPA, prim-ftpd | Adapter eSIM + ograniczony transfer SFTP do własnych urządzeń | Uprawnienia Android, user consent, sandbox, brak anonymous FTP |
| QA | ChatGPT-CodeReview, TTS benchmarki | PR review + deterministyczne testy jednostkowe + realne raporty audio | Logi CI, próby regresji, źródła wyników |
| Skills/UI/3D | agentskills, stitch-skills, pegasus-skills, Meta3D | Registry SKILL.md i wersjonowane kontrakty renderingu | Przegląd metadanych, licencji i testów wydania |

## Faktycznie zapisane zmiany

- `ThorPacketEightCatalog.kt`: 28 rekordów, 22 profile, sprawdzanie unikalności i zależności.
- `ThorPacketEightCatalogTest.kt`: 28 SHA Git tree, 22 profile, `skills-phone` bez przypisania oraz bezpieczne rozróżnienie `pegasus-skills`.
- `ThorPacketRegistry.kt`: ósma karta **ŹRÓDŁA 08** podłączona do istniejącego dynamicznego systemu kart.
- `ThorPacketRegistryTest.kt`: globalne sumy **227** źródeł, **140** profili, **10 RESTRICTED** oraz mapowanie indeksu zakładki.
- `development/thor-agent-packet-08.md`: ten dokument i granice źródłowej wiarygodności.

## Rejestr jakości i ograniczeń

**Odczyt i zapis:** metadane GitHub / Git tree / README, rejestr i pliki testów.  
**Kontrola statyczna:** spójność sum, unikalność źródeł, format SHA, brak aktywowanych źródeł, profil nie może odwoływać się do `RESTRICTED`, dynamiczna obsługa 8 kart.

**Niepotwierdzone:** uruchomienie Gradle/JUnit, podpisany APK, instalacja na Androidzie, połączenie Sanna, Pentagram/Engram, Godot/Unreal MCP, render ComfyUI, połączenia SIP i testy głosowe. Brak dowodów na kompletne e2e lub wdrożenie.

**Warunek merge:** testy Gradle Android i APK, weryfikacja licencji, kodu natywnego i uprawnień, przegląd bezpieczeństwa, porównanie z innymi otwartymi PR. Pozostawić `master` bez zmian.