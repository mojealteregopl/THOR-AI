package com.newoether.agora.newgpt

/**
 * Paczka 09: 29 zweryfikowanych źródeł, 20 nieaktywnych profili.
 * SHA revision identyfikuje Git tree, nie commit ani podpisaną wersję programu.
 * Żadne źródło nie uzyskuje uprawnień do wykonywania działań po imporcie profilu.
 * Źródła RESTRICTED nie mogą być zależnością importowanego profilu.
 */
object ThorPacketNineCatalog {
    private val sourceData = """
mojealterego/railway-design|main|74fa9f890ef713f81ef5a76ffcb9ec8b8222bff3|CLIENT|READ_ONLY|Railway Design szablon Next.js i wytyczne systemu UI
mojealterego/hexstrike-ai|master|d689933ff579d839c676c82b231f8e98326c5f04|MCP|RESTRICTED|HexStrike autonomiczny ofensywny pentest i exploit chain
mojealterego/OnlineSimBot2|main|d21531c113bbc51a923b3f5c252fb19d397915a4|CLIENT|RESTRICTED|OnlineSim Telegram bot udostępniający numery i SMS OTP
mojealterego/alexandria-audiobook|main|be6343d95d94db5f88973aa25575a390f3ce46aa|CREATIVE|PRIVILEGED_EXECUTION|Alexandria audiobook Qwen3 TTS i lokalne narracje
mojealterego/Text-To-Video-API|main|762887897f0e0dac50ced39e3e6eacf26ceaf97a|KNOWLEDGE|READ_ONLY|Text-To-Video-API dokumentacyjne README o zewnętrznym Vadoo API
mojealterego/openai-chatkit-advanced-samples|main|9729a1c05411f10bd02c2a0780b23c443d5415d6|CLIENT|REMOTE_ACCESS|OpenAI ChatKit przykłady React Vite FastAPI i narzędzia
mojealterego/ShipinKit|main|b66473f114fe15adc2e191f054aa6a3a0010f494|CLIENT|REMOTE_ACCESS|ShipinKit Swift SDK Runway Luma polling timeout i credential safety
mojealterego/hmdm-docker|master|9535466ed13c4358bd63df9582555155ea349924|INFRASTRUCTURE|PRIVILEGED_EXECUTION|Headwind MDM serwer zarządzania urządzeniami Android w Docker
mojealterego/claude-code-android|main|cf3040241a437356ad1a97b4bb7d1ba3ef3250b2|ANDROID|PRIVILEGED_EXECUTION|Claude Code Android Termux i tryb eksperymentalnej VM
mojealterego/500-AI-Agents-Projects|main|9beeb721c2af551bacaab827a76bddaecaa0ca5e|CATALOG|READ_ONLY|Katalog 500 projektów i zastosowań agentów AI
mojealterego/SNK-RAT|main|e54cdef8057fa0052ff83c0aa16eba5797394d9e|INFRASTRUCTURE|RESTRICTED|Snaky RAT i C2 z technikami ukrywania działania
mojealterego/Awesome-Hacking|master|b2bb13aa69b4f568a1bab0cbb17b7a00a350dc36|CATALOG|READ_ONLY|Awesome Hacking katalog zasobów bezpieczeństwa
mojealterego/Resolver-Stable-Diffusion-Client-for-android|dev|54f9b964c30b1efad797a5348ca6304ac2078b0c|ANDROID|REMOTE_ACCESS|Resolver mobilny interfejs Forge i ComfyUI z WebSocket
mojealterego/ChatDev|main|4fb2db0ea90375ce1059f44fe03ffbd191a7a169|ORCHESTRATION|REMOTE_ACCESS|ChatDev 2.0 DevAll platforma konfiguracji multiagentowej
mojealterego/agent-starter-pack|main|659f047742457bd55e5db0edd088cf678b6f0669|KNOWLEDGE|READ_ONLY|Google Agent Starter Pack maintenance mode migracja do agents-cli
mojealterego/OpenDevin|main|da114eb485a4813c170f3af8a1f834a0ea4bfb65|ORCHESTRATION|PRIVILEGED_EXECUTION|OpenDevin historyczny autonomiczny agent w kontenerze Docker
mojealterego/suno-cli|main|f0dea4d4e0ef998a508e73f05749eb2b138a253d|CREATIVE|EXTERNAL_MUTATION|Nieoficjalny Suno CLI Rust wykorzystujący dane sesji przeglądarki
mojealterego/TempMailBot|main|3723c6bf22c7eff5a0f12b6395943e645219febd|CLIENT|RESTRICTED|TempMailBot masowe tymczasowe skrzynki i weryfikacja OTP
mojealterego/JieMa_2025|main|a04f40da00a8d03660d34f3bb5025290dccf6cbf|CATALOG|RESTRICTED|JieMa katalog usług numerów SMS do weryfikacji rejestracji
elevenlabs/elevenlabs-n8n|main|769e6df3672a31652295fb918a09fa8a142db56a|MCP|EXTERNAL_MUTATION|ElevenLabs oficjalne integracje społecznościowe n8n do TTS STT
mojealterego/G0DM0D3|main|f6301765fb90eb7b336bdf365319cd2fe44b1187|CLIENT|REMOTE_ACCESS|G0DM0D3 interfejs wielomodelowy i red-team promptów z telemetrią
mojealterego/L3MON|main|9a657c58e1a41cabbddd33c1387dacfae802c318|ANDROID|RESTRICTED|L3MON Android RAT monitoring GPS SMS mikrofonu i plików
mojealterego/futureagi-sdk|main|eef8809791d7570a1398aa6a60407dc8210f590e|KNOWLEDGE|REMOTE_ACCESS|Future AGI SDK Python TypeScript i metryki ewaluacji
mojealterego/OmniVideo-100K|main|2a57e0b5189eb34674315bdc4c7bff5410efaea5|KNOWLEDGE|READ_ONLY|OmniVideo-100K multimodalny dataset i łańcuchy dowodowe
mojealterego/VoIP-Spoofing-Research|main|12256a2a739f1773d2932f63ff9f72d159c2d1e9|KNOWLEDGE|RESTRICTED|VoIP Spoofing Research koncepcje caller ID spoofingu i botów
mojealterego/unity|main|375e152dda6f7c29cb291525d7faa2872dad6e60|GAME|REMOTE_ACCESS|ElevenAgents Unity SDK 6.3 LTS głosowe postacie w grach
mojealterego/actions|main|2c9cb4f4db3d54f0fa17d3232ec93c0b56facb1c|INFRASTRUCTURE|EXTERNAL_MUTATION|MCP GitHub Actions preview PR review i slash commands
mojealterego/sipdroid|master|afc931e8c83ef687de131f8fe5f853e4905e7ebf|ANDROID|REMOTE_ACCESS|Sipdroid historyczny klient SIP Android z GPL
mojealterego/bulk-email-scraper|main|cca0058cb25034138b007649924dfe77815dc0a0|CATALOG|RESTRICTED|Bulk Email Scraper masowe pozyskiwanie adresów e-mail
    """.trimIndent()

    val sources: List<ThorSource> = sourceData.lineSequence()
        .filter(String::isNotBlank)
        .map { row ->
            val fields = row.split('|')
            require(fields.size == 6) { "Nieprawidłowy rekord paczki 09." }
            ThorSource(
                repository = fields[0],
                branch = fields[1],
                revision = fields[2],
                kind = ThorSourceKind.valueOf(fields[3]),
                risk = ThorSourceRisk.valueOf(fields[4]),
                capability = fields[5],
            )
        }
        .toList()
        .also { sources ->
            require(sources.size == 29)
            require(sources.map(ThorSource::repository).toSet().size == sources.size)
            require(sources.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
            require(sources.none(ThorSource::activated))
        }

    val roles: List<ThorRolePreset> = listOf(
        ThorRolePreset(
            id = "packet09.design",
            name = "UI · Railway Design Reviewer",
            description = "System projektowy Next.js",
            prompt = "Projektuj system typografii i komponentów UI na podstawie Railway Design i testuj responsywność. To materiał referencyjny Next.js, nie uprawnienie do wdrażania projektów na Railway.",
            sourceRepositories = listOf("mojealterego/railway-design"),
        ),
        ThorRolePreset(
            id = "packet09.chatkit",
            name = "WEB · ChatKit Application Architect",
            description = "React Vite i FastAPI",
            prompt = "Projektuj bezpieczną architekturę ChatKit z rozdziałem sesji klienta i backendu, walidacją narzędzi i testami typów. Przykłady React FastAPI wymagają dostosowania do istniejącego THOR, bez kopiowania sekretów.",
            sourceRepositories = listOf("mojealterego/openai-chatkit-advanced-samples"),
        ),
        ThorRolePreset(
            id = "packet09.development",
            name = "AGENTS · Software Team Architect",
            description = "ChatDev i historyczny OpenDevin",
            prompt = "Projektuj delegowanie zadań kodowania w grafie agentów, z izolacją workspace i dowodami kompilacji. ChatDev 2.0 jest konfiguracją orkiestracji, a OpenDevin starym runtime, więc nie zakładaj zgodności API.",
            sourceRepositories = listOf("mojealterego/ChatDev", "mojealterego/OpenDevin"),
        ),
        ThorRolePreset(
            id = "packet09.cloud",
            name = "CLOUD · Agent Lifecycle Engineer",
            description = "Google Agent Starter Pack",
            prompt = "Projektuj pełny cykl testów i wdrożenia agentów w Google Cloud z kontrolą kosztów i rollback. Agent Starter Pack jest w maintenance mode; nowe wdrożenia wymagają sprawdzenia następcy agents-cli.",
            sourceRepositories = listOf("mojealterego/agent-starter-pack"),
        ),
        ThorRolePreset(
            id = "packet09.mobile-code",
            name = "ANDROID · Terminal Coding Reviewer",
            description = "Claude Code w Termux",
            prompt = "Projektuj testy i izolację środowisk kodowania na własnym Androidzie z jawnymi uprawnieniami plików i procesów. Instrukcje Termux nie oznaczają działającego Claude Code bez instalacji i uwierzytelnienia.",
            sourceRepositories = listOf("mojealterego/claude-code-android"),
        ),
        ThorRolePreset(
            id = "packet09.mobile-diffusion",
            name = "ANDROID · ComfyUI Client Architect",
            description = "Resolver klient SD",
            prompt = "Projektuj klienta ComfyUI i WebUI Forge z bezpiecznym przesyłaniem workflow, reconnect WebSocket, job IDs i ochroną prywatnych endpointów. Wykonanie modeli pozostaje po stronie serwera GPU.",
            sourceRepositories = listOf("mojealterego/Resolver-Stable-Diffusion-Client-for-android"),
        ),
        ThorRolePreset(
            id = "packet09.mobile-management",
            name = "ANDROID · Managed Fleet Architect",
            description = "Headwind MDM i Docker",
            prompt = "Projektuj zarządzanie własną flotą urządzeń testowych przez MDM, z wymuszonym TLS, RBAC, prawami administratorów i odrębnymi kontami. Nie przejmuj urządzeń cudzych ani nie używaj haseł przykładowych z README.",
            sourceRepositories = listOf("mojealterego/hmdm-docker"),
        ),
        ThorRolePreset(
            id = "packet09.android-sip",
            name = "VOICE · Legacy SIP Android Analyst",
            description = "Sipdroid klient SIP",
            prompt = "Oceń historyczny kod Sipdroid pod kątem współczesnego API Android, bezpieczeństwa SIP i licencji GPL. Przygotuj testy dzwonienia wyłącznie na kontrolowanych numerach laboratoryjnych.",
            sourceRepositories = listOf("mojealterego/sipdroid"),
        ),
        ThorRolePreset(
            id = "packet09.audiobook",
            name = "AUDIO · Audiobook Production Engineer",
            description = "Alexandria Qwen3 TTS",
            prompt = "Projektuj kolejkę konwersji legalnie posiadanych tekstów na audiobook z atrybucją głosów i oznaczaniem dialogów. Klonowanie głosu i użycie cudzych książek wymaga odpowiednich praw oraz odbioru pliku audio.",
            sourceRepositories = listOf("mojealterego/alexandria-audiobook"),
        ),
        ThorRolePreset(
            id = "packet09.video-api",
            name = "VIDEO · Video API Contract Engineer",
            description = "Vadoo Runway i Luma",
            prompt = "Projektuj asynchroniczne zlecenia wideo z walidacją promptów, podpisanymi webhookami i timeout. Text-To-Video-API jest głównie README z Vadoo API, natomiast ShipinKit to nieoficjalny Swift SDK Runway Luma.",
            sourceRepositories = listOf("mojealterego/Text-To-Video-API", "mojealterego/ShipinKit"),
        ),
        ThorRolePreset(
            id = "packet09.music-cli",
            name = "MUSIC · Suno Integration Reviewer",
            description = "Nieoficjalne Suno CLI",
            prompt = "Analizuj Suno CLI jako nieoficjalne narzędzie Rust wykorzystujące sesję przeglądarki; nie pobieraj cookies ani tokenów i nie inicjuj generacji przed akceptacją kosztów oraz zasad usługi.",
            sourceRepositories = listOf("mojealterego/suno-cli"),
        ),
        ThorRolePreset(
            id = "packet09.n8n-voice",
            name = "VOICE · ElevenLabs n8n Engineer",
            description = "Nody workflow ElevenLabs",
            prompt = "Projektuj bezpieczną integrację ElevenLabs n8n do TTS STT i zarządzania głosami. Kontroluj zakres klucza serwisowego, koszty generacji, prywatność materiałów i dowody wykonania workflow.",
            sourceRepositories = listOf("elevenlabs/elevenlabs-n8n"),
        ),
        ThorRolePreset(
            id = "packet09.evaluation",
            name = "QA · Future AGI SDK Engineer",
            description = "Ewaluacja agentów i metryki",
            prompt = "Opracuj odtwarzalne ewaluacje odpowiedzi agentów, walidację datasets, scoring i tracing przez Future AGI SDK. Test musi podawać prawdziwe metryki i jasno rozdzielać koszt zewnętrznego oceniania.",
            sourceRepositories = listOf("mojealterego/futureagi-sdk"),
        ),
        ThorRolePreset(
            id = "packet09.omnivideo",
            name = "VISION · Audiovisual Evidence Analyst",
            description = "OmniVideo multimodalny dataset",
            prompt = "Projektuj analizę wideo i audio przez chronologiczne segmenty i entity anchored scripts. Nie twierdź, że THOR wytrenował modele OmniVideo-100K bez procesu treningowego i testów danych.",
            sourceRepositories = listOf("mojealterego/OmniVideo-100K"),
        ),
        ThorRolePreset(
            id = "packet09.unity-voice",
            name = "GAME · ElevenAgents Unity Integrator",
            description = "SDK głosu dla Unity",
            prompt = "Projektuj postacie mówiące w Unity 6.3 LTS przy użyciu ElevenAgents Unity SDK, steruj sesją przez eventy i testuj spatial audio. To nie jest silnik gry Unity ani zamiennik Unity MCP, tylko wczesny SDK.",
            sourceRepositories = listOf("mojealterego/unity"),
        ),
        ThorRolePreset(
            id = "packet09.github",
            name = "DEVOPS · MCP Repository Actions",
            description = "Actions dla preview i PR",
            prompt = "Projektuj GitHub Actions z najmniejszymi uprawnieniami, preview Cloudflare, kontrolą branch oraz audytem slash commands. Nie włączaj auto-merge ani działań wdrożeniowych bez zatwierdzonego PR i testów CI.",
            sourceRepositories = listOf("mojealterego/actions"),
        ),
        ThorRolePreset(
            id = "packet09.catalog",
            name = "RESEARCH · Multiagent Pattern Curator",
            description = "500 projektów agentów",
            prompt = "Kataloguj wzorce agentów i klasyfikuj przypadki użycia na podstawie 500 AI Agents Projects. Nie deklaruj gotowych integracji tylko dlatego, że projekt został wymieniony w zestawieniu.",
            sourceRepositories = listOf("mojealterego/500-AI-Agents-Projects"),
        ),
        ThorRolePreset(
            id = "packet09.security",
            name = "SECURITY · Defensive Resources Curator",
            description = "Awesome Hacking i obrona",
            prompt = "Weryfikuj publiczne źródła o narzędziach bezpieczeństwa, przygotuj model zagrożeń i listę defensywnych testów. Nie uruchamiaj ofensywnych exploitów ani narzędzi na cudzej infrastrukturze.",
            sourceRepositories = listOf("mojealterego/Awesome-Hacking"),
        ),
        ThorRolePreset(
            id = "packet09.model-ui",
            name = "LLM · Multi-provider UX Architect",
            description = "G0DM0D3 interfejs wielomodelowy",
            prompt = "Analizuj wzorce interfejsu multi-provider, zarządzania sesjami i lokalnych modeli w G0DM0D3. Oddziel prywatność danych od deklaracji dostawcy, weryfikuj telemetry i źródła modelowych odpowiedzi.",
            sourceRepositories = listOf("mojealterego/G0DM0D3"),
        ),
        ThorRolePreset(
            id = "packet09.release",
            name = "RELEASE · Agent Delivery Auditor",
            description = "Wdrażanie agentów i automatyzacje",
            prompt = "Projektuj kontrolowaną ścieżkę PR od planu, przez testy integracyjne i ocenę diff, po manualnie zatwierdzone wdrożenie. Przed scaleniem sprawdzaj maintenance mode startera i uprawnienia skryptów Actions.",
            sourceRepositories = listOf("mojealterego/actions", "mojealterego/agent-starter-pack"),
        ),
    ).also { roles ->
        require(roles.size == 20)
        require(roles.map(ThorRolePreset::id).toSet().size == roles.size)
        require(roles.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { repository ->
                    sources.any {
                        it.repository == repository &&
                            it.risk != ThorSourceRisk.RESTRICTED
                    }
                }
        })
    }
}
