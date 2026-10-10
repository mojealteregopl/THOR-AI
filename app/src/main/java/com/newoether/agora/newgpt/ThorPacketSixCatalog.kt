package com.newoether.agora.newgpt

/**
 * Paczka 06: źródła zweryfikowane na GitHub. Powtórzenie railway-skills pominięte.
 * Rewizja to SHA drzewa Git, nie atestacja artefaktu.
 * Import profilu nie instaluje kodu, nie wywołuje sieci i nie nadaje uprawnień.
 */
object ThorPacketSixCatalog {
    private val sourceData = """
mojealterego/railway-skills|main|950acebcef3445f3915ea0709876c3ba9c237a7f|KNOWLEDGE|REMOTE_ACCESS|Railway Agent Skills i deklaracje hostowanego MCP
mojealterego/Rivo-Agent-Application|main|bb46f18579709dc0814397119711a519c254b6b1|ANDROID|READ_ONLY|Rivo Agent mobilny chat LLM offline po pobraniu modeli
mojealterego/Shadowbroker|main|cd6395f5ee7d5232fc7b553bf74f5e514deaafdb|INFRASTRUCTURE|PRIVILEGED_EXECUTION|Shadowbroker geograficzny OSINT i narzędzia rekonesansu
mojealterego/gpt_mobile|main|c79f502e1db67141f8b85f69fa1e30668335950f|ANDROID|REMOTE_ACCESS|GPT Mobile Kotlin Compose z MCP OAuth i Android Keystore
mojealterego/agent-learning-kit|main|a13cd0d99e589ab4f6bd7daaf82564e087714c24|KNOWLEDGE|READ_ONLY|Future AGI Python i TypeScript SDK ocen LLM
mojealterego/UserLAnd|master|ba4cb39e2233e196f7aba9766dc4dc700481a8ed|ANDROID|PRIVILEGED_EXECUTION|UserLAnd lokalne środowisko Linux Android bez root
mojealterego/future-agi|main|2d244e6b0566f265337c42ea50299dc5f0dbf96e|ORCHESTRATION|EXTERNAL_MUTATION|Future AGI ocena agentów observability guardrails i ewaluacje
mojealterego/TorBot|dev|1cc500ff2bae6c2cce3a3accd3e55a9c69f51034|INFRASTRUCTURE|PRIVILEGED_EXECUTION|TorBot crawler sieci Tor i onion OSINT bez autorun
mojealterego/docs|main|120d9afc6f9a8fcbe8371a1f5317d546a7dcf3be|KNOWLEDGE|READ_ONLY|Future AGI dokumentacja Astro i SDK
mojealterego/Stable-Diffusion-KMP|master|4b060c7e4e55e5bc94a38f239649f59a5e2183bb|ANDROID|REMOTE_ACCESS|SDAI mobilny klient Stable Diffusion i dostawców API
mojealterego/elevenlabs-swift-sdk|main|2d61c1fc931e130318f6d6b4772b9d84f596d65a|CLIENT|REMOTE_ACCESS|ElevenAgents Swift SDK LiveKit WebRTC
mojealterego/cli|main|020345cb1e7d229ea3f5693945b4645f3ef03b7d|MCP|EXTERNAL_MUTATION|ElevenLabs CLI agenci jako kod i wywołania API
mojealterego/Stable-Diffusion|main|bde3455321023bdf6dc25f8ba3159e884960ac3e|KNOWLEDGE|READ_ONLY|Stable Diffusion poradnik i baza tutoriali zamiast silnika
mojealterego/create-railway|main|20de10ba47ae2cf6be01b55f71371b4a7b56d7e9|INFRASTRUCTURE|EXTERNAL_MUTATION|Railway scaffolding TypeScript i sandbox API
mojealterego/Box|main|07ab1933d14bc390dc2a0fea77a572ca1f278e26|ANDROID|READ_ONLY|Box Android hybrid LiteRT llama.cpp GGUF i STT
mojealterego/element-x-android|develop|67f287af39a467aa47a731ad0774bf101e91fa74|ANDROID|REMOTE_ACCESS|Element X Android komunikator Matrix i Rust SDK
mojealterego/skills|main|fd7b7f593b86861758bb1804234f758da3989f14|KNOWLEDGE|READ_ONLY|ElevenLabs Agent Skills do audio i voice
mojealterego/base44-platform-starter|main|443ecaf6f13b02fbf66e941a105afa974b084758|CLIENT|EXTERNAL_MUTATION|Base44 platform starter white label proxy i RBAC
mojealterego/elevenlabs-js|main|aa6976916c3c4a7dec5db572c03eed0b56d6b8fc|CLIENT|REMOTE_ACCESS|ElevenLabs JS SDK dostęp do API syntezy mowy
    """.trimIndent()

    val sources: List<ThorSource> = sourceData.lineSequence()
        .filter(String::isNotBlank)
        .map { row ->
            val fields = row.split('|')
            require(fields.size == 6) { "Niepoprawny rekord źródła paczki 06." }
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
        .also { entries ->
            require(entries.size == 19)
            require(entries.map(ThorSource::repository).toSet().size == entries.size)
            require(entries.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
            require(entries.none(ThorSource::activated))
        }

    val roles: List<ThorRolePreset> = listOf(
        ThorRolePreset(
            id = "packet06.android-edge",
            name = "ANDROID · Edge Runtime Analyst",
            description = "Box Rivo i gpt_mobile",
            prompt = "Projektuj kompatybilną z Androidem architekturę lokalnej inferencji, katalogu GGUF, izolacji sekretów i sesji. Rozdziel wywołania modelu offline od zewnętrznych API. Wyniki wydajności wymagają testu na urządzeniu.",
            sourceRepositories = listOf("mojealterego/Box", "mojealterego/Rivo-Agent-Application", "mojealterego/gpt_mobile"),
        ),
        ThorRolePreset(
            id = "packet06.android-linux",
            name = "ANDROID · UserLAnd Engineer",
            description = "Bezrootowe środowiska Linux",
            prompt = "Projektuj izolowane środowiska Linux na własnym Androidzie bez root; rozpisz wymagania ABI, filesystem, limity procesów i ochronę danych. Instalacja i komendy terminalowe wymagają zgody użytkownika oraz potwierdzonych logów.",
            sourceRepositories = listOf("mojealterego/UserLAnd"),
        ),
        ThorRolePreset(
            id = "packet06.android-integration",
            name = "ANDROID · Secure MCP Architect",
            description = "Kotlin Android MCP i OAuth",
            prompt = "Projektuj profile dostawców AI w Kotlin Compose z szyfrowaniem Keystore i autoryzacją MCP OAuth. Narzędzia pozostają domyślnie wyłączone; odkrywanie serwerów ujawniające tokeny wymaga jawnej akceptacji.",
            sourceRepositories = listOf("mojealterego/gpt_mobile"),
        ),
        ThorRolePreset(
            id = "packet06.android-design",
            name = "ANDROID · Secure Messaging Designer",
            description = "Matrix i Element X",
            prompt = "Projektuj prywatne komunikatory i przepływy Android, wykorzystując architekturę Matrix Rust SDK oraz Compose. Przed integracją wymagaj audytu zgodności API, uprawnień, szyfrowania i licencji.",
            sourceRepositories = listOf("mojealterego/element-x-android"),
        ),
        ThorRolePreset(
            id = "packet06.diffusion",
            name = "IMAGE · Mobile Diffusion Architect",
            description = "SDAI i Stable Diffusion",
            prompt = "Opracuj mobilne interfejsy zdalnego i lokalnego generowania obrazów, parametry seed, steps, CFG, LoRA, routing providerów i kolejki. Odróżniaj repozytorium tutoriali Stable Diffusion od silnika modelu.",
            sourceRepositories = listOf("mojealterego/Stable-Diffusion-KMP", "mojealterego/Stable-Diffusion"),
        ),
        ThorRolePreset(
            id = "packet06.railway",
            name = "CLOUD · Railway Deployment Architect",
            description = "Railway Agent Skills i scaffolding",
            prompt = "Projektuj reproducible deploy na Railway z policy gate, minimalnym zakresem OAuth, pinowaniem wersji i kontrolą kosztów. Uruchomienie CLI oraz budowa sandboxu wymaga własnego konta i zgody.",
            sourceRepositories = listOf("mojealterego/railway-skills", "mojealterego/create-railway"),
        ),
        ThorRolePreset(
            id = "packet06.builder",
            name = "BUILDER · White-label Platform Engineer",
            description = "Base44 platform starter",
            prompt = "Projektuj bezpieczne integracje kreatora aplikacji z tożsamością per user, allowlist API, CORS, egzekwowaniem RBAC i odseparowaniem sekretów od frontend. Nie twórz aplikacji produkcyjnej bez uwierzytelnienia i kontroli kosztów.",
            sourceRepositories = listOf("mojealterego/base44-platform-starter"),
        ),
        ThorRolePreset(
            id = "packet06.voice-realtime",
            name = "VOICE · LiveKit Voice Engineer",
            description = "ElevenAgents Swift i JavaScript",
            prompt = "Projektuj rozmowy audio czasu rzeczywistego, obsługę przerwań, streaming WebRTC i podpisywane sesje. Nie utrwalaj głosu ani nie uruchamiaj płatnych API bez zgody. Zwracaj uwagę na granice Swift i Node.",
            sourceRepositories = listOf("mojealterego/elevenlabs-swift-sdk", "mojealterego/elevenlabs-js"),
        ),
        ThorRolePreset(
            id = "packet06.voice-ops",
            name = "VOICE · Agents as Code Engineer",
            description = "ElevenLabs CLI i Agent Skills",
            prompt = "Projektuj głosowych agentów jako wersjonowane konfiguracje z kontrolą uprawnień i wdrożeń. Przygotuj obsługę TTS STT dubbingu i testy kontraktów, bez przechowywania kluczy w kodzie i wykonywania zmian konta bez potwierdzenia.",
            sourceRepositories = listOf("mojealterego/cli", "mojealterego/skills"),
        ),
        ThorRolePreset(
            id = "packet06.agent-evals",
            name = "QA · AI Evaluation Architect",
            description = "Future AGI i agent-learning-kit",
            prompt = "Definiuj zestawy golden cases, walidację halucynacji, wykrywanie PII i prompt injection, testy regresji, zakresy statystyczne i ślady OTel. Metryki marketingowe z README nie są dowodem uzyskania benchmarku THOR.",
            sourceRepositories = listOf("mojealterego/agent-learning-kit", "mojealterego/future-agi", "mojealterego/docs"),
        ),
        ThorRolePreset(
            id = "packet06.qa-hardening",
            name = "QA · Red Team Evaluator",
            description = "Guardrails i atestacja wykonania",
            prompt = "Projektuj nieinwazyjne scenariusze red-team dla własnych agentów, odporność na wstrzykiwanie instrukcji, niezależny scoring odpowiedzi i falsyfikowalne raporty. Weryfikacja wymaga rzeczywistych danych i aktualnych wyników.",
            sourceRepositories = listOf("mojealterego/agent-learning-kit", "mojealterego/future-agi"),
        ),
        ThorRolePreset(
            id = "packet06.osint",
            name = "OSINT · Public Data Analyst",
            description = "Shadowbroker i TorBot",
            prompt = "Opracowuj analizy jawnych danych geoprzestrzennych i publicznych źródeł z oceną pochodzenia informacji i ograniczeniami prywatności. Nie skanuj cudzych zasobów, nie uruchamiaj rekonesansu ani crawlerów Tor bez odrębnej autoryzacji.",
            sourceRepositories = listOf("mojealterego/Shadowbroker", "mojealterego/TorBot"),
        ),
        ThorRolePreset(
            id = "packet06.railway-integrator",
            name = "CLOUD · Integration Policy Engineer",
            description = "Railway OAuth i sandbox",
            prompt = "Projektuj politykę najmniejszych uprawnień dla umiejętności Railway i środowisk sandbox. Ustal zakres dostępu, ważność tokenów, audyt, koszty, limity czasu i procedurę rollback zanim nastąpi deploy.",
            sourceRepositories = listOf("mojealterego/railway-skills", "mojealterego/create-railway"),
        ),
        ThorRolePreset(
            id = "packet06.mobile-privacy",
            name = "ANDROID · Privacy and UX Auditor",
            description = "Local-first i Matrix",
            prompt = "Porównuj mechanizmy offline-first, szyfrowania sekretów, historii lokalnej i komunikacji Matrix. Przygotuj zagrożenia prywatności i testy danych Android bez deklarowania pomyślnego audytu runtime.",
            sourceRepositories = listOf("mojealterego/Rivo-Agent-Application", "mojealterego/gpt_mobile", "mojealterego/Box", "mojealterego/element-x-android"),
        ),
    ).also { profiles ->
        require(profiles.size == 14)
        require(profiles.map(ThorRolePreset::id).toSet().size == profiles.size)
        require(profiles.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { repository ->
                    sources.any {
                        it.repository == repository && it.risk != ThorSourceRisk.RESTRICTED
                    }
                }
        })
    }
}
