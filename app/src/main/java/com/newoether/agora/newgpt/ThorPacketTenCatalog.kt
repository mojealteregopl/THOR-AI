package com.newoether.agora.newgpt

/**
 * Pakiet 10: 26 obiektów Git tree i 18 nieaktywnych profili.
 * CzatBotSingularity jest pusty i nie ma drzewa Git: tylko w dokumentacji.
 * Zewnętrzne repozytoria nie są instalowane ani uruchamiane przez import profilu.
 */
object ThorPacketTenCatalog {
    private val sourceData = """
mojealterego/Bug-Bounty-Agents|main|5f8b8301b1bfbbe3aece4f38337cef69d52af0dc|KNOWLEDGE|READ_ONLY|Bug Bounty 43 prompty agentów bez skanerów i kodu wykonawczego
mojealterego/n8n-nodes-futureagi|main|137552e2db78597528ac592b2d98be4d797f1c35|ORCHESTRATION|EXTERNAL_MUTATION|Future AGI integracja n8n logi i ocena odpowiedzi
mojealterego/Duix-Avatar|main|1328feb5871448c8fa3d0e45b3bbc87e7c1d458a|CREATIVE|PRIVILEGED_EXECUTION|Duix Avatar offline Windows klonowanie wyglądu i głosu
mojealterego/Low-Code-No-Code-Platforms|main|756e3d7f88abde1d9367e4bd64d009611c51221e|CATALOG|READ_ONLY|Katalog platform low code oraz no code
mojealterego/autogen|main|027ecf0a379bcc1d09956d46d12d44a3ad9cee14|ORCHESTRATION|READ_ONLY|Microsoft AutoGen maintenance mode następca Agent Framework
mojealterego/HunyuanVideo-I2V|main|c8bba70b9517f08d770a9a2a3d1e93cc6d5b7949|CREATIVE|PRIVILEGED_EXECUTION|Tencent HunyuanVideo I2V generacja wideo i LoRA
mojealterego/ai-novel-generator|main|ed4a89421bfeacb49ab86fce6ebbe052ec089dd4|CREATIVE|EXTERNAL_MUTATION|AI novel generator lokalny Gradio i Vue FastAPI multi tenant
mojealterego/openai-chatkit-starter-app|main|9cf32e6b264095591523fb026d6fa5e97f4bb20e|CLIENT|REMOTE_ACCESS|OpenAI ChatKit starter self hosted i managed examples
mojealterego/sannabotapp|main|7d84aa708fe3c56aa7202635abdaa442cc4781f3|ANDROID|PRIVILEGED_EXECUTION|Sanna Android voice agent z kontrolą Accessibility i powiadomień
mojealterego/graph-of-thoughts|main|3d9d9dbd8937d47a4441f681b8b40e3c5b054f16|ORCHESTRATION|REMOTE_ACCESS|Graph of Thoughts LLM Graph of Operations planowania
mojealterego/desktop-tutorial|main|48ef8f1d4201bafdba969192acad0244e5c2210b|KNOWLEDGE|READ_ONLY|GitHub Desktop tutorial jedyny README bez projektu
fotografaandrzej333/awesome-voip|master|43f67aa8fb8a5a961c382ceffcdbcfc09d02430b|CATALOG|READ_ONLY|Awesome VoIP zbiór materiałów PJSIP i Linphone
mojealterego/visionclaw|main|59269612412acac34c4f918a264ab68ebeb7e7f0|CLIENT|REMOTE_ACCESS|VisionClaw Apple Vision Pro VR asystent OpenClaw przez Mac
mojealterego/Stable-Diffusion-3.5-Web-UI|main|577d3d309001287114cf6f081e5e04f005c71471|CREATIVE|RESTRICTED|Stable Diffusion 3.5 Web UI README instalator EXE z chronionego hasłem ZIP bez audytu
mojealterego/HunyuanPortraitLCM|main|a3650b8323fcd6c2aa550220619b5304184e4643|CREATIVE|PRIVILEGED_EXECUTION|Hunyuan Portrait animacja obrazu referencyjnego GPU 24GB
mojealterego/open-agent-platform|main|6a2ea0a3d019c20550b8d88ca0ed4d57df774ac8|ORCHESTRATION|READ_ONLY|Open Agent Platform deprecated zgodnie z README
mojealterego/kernel-browser-automation-starter|main|57b0354bf15282622a25a8f35f16271d3af2043a|MCP|EXTERNAL_MUTATION|Kernel Browser Automation Next.js Vercel zdalna przeglądarka
mojealterego/autopentest-ai|main|5dee23126a45990292322aa8d073aabd46a8101b|MCP|RESTRICTED|AutoPentest autonomiczny ofensywny MCP z exploit chaining
mojealterego/santamonica|main|65364498f8a7b9543e4f666275560caf77b2b67e|GAME|READ_ONLY|Santamonica dekompilacja gry Fahrenheit PS2 prototype
mojealterego/perplexicapp|main|864395cc8d1bd1e8c300839e9d45776dee4bd318|ANDROID|REMOTE_ACCESS|Perplexicapp Expo mobilny klient serwera Perplexica
NJU-LINK/OmniVideoBench|main|fb7a5603bad0c08e28754a670001f04b45983ce3|KNOWLEDGE|READ_ONLY|OmniVideoBench benchmark 1000 par QA i 628 wideo
mojealterego/babyagi|main|fa8930ebe72a82e5ad57b356e7cbec96290e5bb2|ORCHESTRATION|PRIVILEGED_EXECUTION|BabyAGI eksperymentalny framework functionz bez produkcyjnej gotowości
mojealterego/lonlybot|main|f6adc986e4025fddada43f89a5a02a4115463747|CLIENT|REMOTE_ACCESS|Lonlybot frontend chat persona towarzyszący i API modeli
mojealterego/phisher98|main|eca235305f0f4294768aab98e75e91710288bfb6|KNOWLEDGE|READ_ONLY|Phisher98 repo profil autora bez kodu phishing
mojealterego/MetaGPT|main|11cdf466d042aece04fc6cfd13b28e1a70341b1f|ORCHESTRATION|REMOTE_ACCESS|MetaGPT programowanie zespołowe SOP i wieloagentowe role
mojealterego/OnlineSimBot|main|b251804a70b79f60c9e083485941516786c0097a|CLIENT|RESTRICTED|OnlineSimBot Telegram tymczasowe numery SMS i weryfikacja
    """.trimIndent()

    val sources: List<ThorSource> = sourceData.lineSequence()
        .filter(String::isNotBlank)
        .map { row ->
            val fields = row.split('|')
            require(fields.size == 6) { "Niepoprawny rekord paczki 10." }
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
            require(entries.size == 26)
            require(entries.map(ThorSource::repository).toSet().size == entries.size)
            require(entries.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
            require(entries.none(ThorSource::activated))
        }

    val roles: List<ThorRolePreset> = listOf(
        ThorRolePreset(
            id = "packet10.security-review",
            name = "SECURITY · Bug Bounty Reader",
            description = "Kontrola dokumentacji defensywnej",
            prompt = "Przygotowuj wyłącznie analizy bezpiecznych wzorców testów autoryzowanych aplikacji oraz rekomendacje obronne. Repozytorium zawiera prompty, nie skanery; żadna akcja ofensywna nie jest automatycznie wykonywana.",
            sourceRepositories = listOf("mojealterego/Bug-Bounty-Agents"),
        ),
        ThorRolePreset(
            id = "packet10.eval-n8n",
            name = "QA · Future AGI Workflow Designer",
            description = "Oceny i obserwowalność n8n",
            prompt = "Projektuj odtwarzalne testy jakości odpowiedzi w n8n i telemetry przez Future AGI, z minimalizacją danych i kontrolą kosztów. Każdy przepływ sieciowy wymaga identyfikowalnego właściciela i zgody.",
            sourceRepositories = listOf("mojealterego/n8n-nodes-futureagi"),
        ),
        ThorRolePreset(
            id = "packet10.avatar",
            name = "VIDEO · Duix Avatar Architect",
            description = "Lokalny awatar i ochrona wizerunku",
            prompt = "Projektuj offline pipeline cyfrowego awatara Duix z dokumentacją pochodzenia próbek i zgody na klonowanie głosu i wyglądu. Nie deklaruj działania bez uruchomienia Windows worker i faktycznego pliku.",
            sourceRepositories = listOf("mojealterego/Duix-Avatar"),
        ),
        ThorRolePreset(
            id = "packet10.nocode",
            name = "PLATFORM · Low-code Evaluator",
            description = "Przegląd platform no-code",
            prompt = "Porównuj architekturę i bezpieczeństwo platform low code na podstawie ich faktycznych możliwości. Nie traktuj katalogu linków jak gotowego do wdrożenia kreatora aplikacji.",
            sourceRepositories = listOf("mojealterego/Low-Code-No-Code-Platforms"),
        ),
        ThorRolePreset(
            id = "packet10.autogen",
            name = "AGENTS · AutoGen Migration Reviewer",
            description = "Migracja do Microsoft Agent Framework",
            prompt = "Projektuj migrację zespołów AutoGen do Microsoft Agent Framework na podstawie oficjalnych zmian API. AutoGen jest w maintenance mode; nigdy nie oznaczaj go jako aktualnego silnika produkcyjnego.",
            sourceRepositories = listOf("mojealterego/autogen"),
        ),
        ThorRolePreset(
            id = "packet10.i2v",
            name = "VIDEO · Hunyuan Image-to-Video Engineer",
            description = "Hunyuan generowanie obrazu do wideo",
            prompt = "Projektuj kolejki generowania image-to-video, kontrolę zgodności pierwszej klatki i konfiguracji LoRA. Wymagaj audytu licencji modeli, GPU i rzeczywiście wygenerowanego artefaktu przed zaliczeniem etapu.",
            sourceRepositories = listOf("mojealterego/HunyuanVideo-I2V"),
        ),
        ThorRolePreset(
            id = "packet10.novel",
            name = "WRITING · Narrative Production Engineer",
            description = "Produkcja opowieści i wielodzierżawność",
            prompt = "Projektuj bezpieczny workflow rozdziałów, postaci i spójności fabuły na podstawie ai novel generator. W wersji multi tenant sprawdzaj RBAC, izolację projektów i prawne pochodzenie materiałów.",
            sourceRepositories = listOf("mojealterego/ai-novel-generator"),
        ),
        ThorRolePreset(
            id = "packet10.chatkit",
            name = "WEB · ChatKit Integration Engineer",
            description = "Managed i self-hosted ChatKit",
            prompt = "Porównuj zarządzany i własny backend ChatKit, weryfikuj rozdział odpowiedzialności klienta i serwera oraz autoryzację sesji. Nie osadzaj sekretów dostawcy w aplikacji mobilnej.",
            sourceRepositories = listOf("mojealterego/openai-chatkit-starter-app"),
        ),
        ThorRolePreset(
            id = "packet10.android",
            name = "ANDROID · Sanna Voice Automation Engineer",
            description = "Accessibility Android i uprawnienia",
            prompt = "Projektuj voice-first obsługę własnego Androida z Accessibility, stanem zadań, scheduler i kontrolą zgody. Każde wysłanie SMS, e-maila, zmiana ustawień lub kliknięcie wymaga pre-execution policy gate.",
            sourceRepositories = listOf("mojealterego/sannabotapp"),
        ),
        ThorRolePreset(
            id = "packet10.got",
            name = "AGENTS · Graph of Thoughts Planner",
            description = "Graf operacji i ocena planów",
            prompt = "Projektuj testowalne grafy generowania, punktowania i oceny stanów modelu na podstawie Graph of Thoughts. Unikaj cykli bez limitów oraz pozornych wyników obliczeń bez testów wykonania.",
            sourceRepositories = listOf("mojealterego/graph-of-thoughts"),
        ),
        ThorRolePreset(
            id = "packet10.voip",
            name = "VOICE · VoIP Standards Researcher",
            description = "PJSIP Linphone i standardy SIP",
            prompt = "Opracowuj audytowalny projekt legalnego klienta SIP z TLS, SRTP, jitter buffer i testami laboratoryjnymi. Katalog awesome voip zawiera historyczne zasoby, które wymagają aktualnej weryfikacji.",
            sourceRepositories = listOf("fotografaandrzej333/awesome-voip"),
        ),
        ThorRolePreset(
            id = "packet10.spatial",
            name = "SPATIAL · Vision Pro Companion Engineer",
            description = "Apple Vision Pro i OpenClaw",
            prompt = "Projektuj spatial UI i komunikację Apple Vision Pro przez jawnie autoryzowany Mac bridge, z kontrolą mikrofonu, stanem sesji i limitami żądań. VisionClaw nie jest aplikacją Android.",
            sourceRepositories = listOf("mojealterego/visionclaw"),
        ),
        ThorRolePreset(
            id = "packet10.portrait",
            name = "VIDEO · Controlled Portrait Animator",
            description = "Hunyuan animacja portretów",
            prompt = "Projektuj rendering referencyjnych portretów z HunyuanPortrait, kontrolą zachowania tożsamości oraz zabezpieczeniami wizerunku. Pomiary na 24GB GPU i prawa do materiału muszą zostać potwierdzone.",
            sourceRepositories = listOf("mojealterego/HunyuanPortraitLCM"),
        ),
        ThorRolePreset(
            id = "packet10.builder",
            name = "AGENTS · Deprecated Builder Analyst",
            description = "Open Agent Platform i architektura",
            prompt = "Opracuj migrację rozwiązań Open Agent Platform z uwzględnieniem jego statusu deprecated i aktualnych kontraktów. Nie podłączaj starego narzędzia do danych produkcyjnych bez audytu.",
            sourceRepositories = listOf("mojealterego/open-agent-platform"),
        ),
        ThorRolePreset(
            id = "packet10.browser",
            name = "WEB · Browser Automation Engineer",
            description = "Kernel sandbox i bezpieczne wykonywanie",
            prompt = "Projektuj kontrolowaną automatyzację w chmurowej przeglądarce Kernel z autoryzacją domen, blokadą SSRF i poświadczeniem zakończenia. Brak zgody oznacza brak sesji wykonawczej.",
            sourceRepositories = listOf("mojealterego/kernel-browser-automation-starter"),
        ),
        ThorRolePreset(
            id = "packet10.mobile-search",
            name = "ANDROID · Private Search Engineer",
            description = "Expo i Perplexica",
            prompt = "Projektuj mobilny klient wyszukiwania z bezpieczną konfiguracją endpointów, reconnect i wyjaśnieniem źródeł. Nie uznawaj nieaktywowanych funkcji Perplexicapp za zaimplementowane.",
            sourceRepositories = listOf("mojealterego/perplexicapp"),
        ),
        ThorRolePreset(
            id = "packet10.avbench",
            name = "QA · OmniVideo Evaluation Engineer",
            description = "Audio wizualne testy rozumowania",
            prompt = "Projektuj benchmarki łączące dźwięk i obraz według OmniVideoBench z wersjonowaniem zestawów danych i kontrolą przecieku odpowiedzi. Nie twierdź że THOR osiągnął wynik bez ewaluacji.",
            sourceRepositories = listOf("NJU-LINK/OmniVideoBench"),
        ),
        ThorRolePreset(
            id = "packet10.software-team",
            name = "AGENTS · MetaGPT SOP Architect",
            description = "Proces wieloagentowego developmentu",
            prompt = "Projektuj wymienialne role PM architekta developera i testera z jawnymi artefaktami wejść i wyjść, walidacją schematów i review. Nie utożsamiaj wzorców MetaGPT z realnie uruchomionym zespołem.",
            sourceRepositories = listOf("mojealterego/MetaGPT"),
        ),
    ).also { entries ->
        require(entries.size == 18)
        require(entries.map(ThorRolePreset::id).toSet().size == entries.size)
        require(entries.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { reference ->
                    sources.any {
                        it.repository == reference &&
                            it.risk != ThorSourceRisk.RESTRICTED
                    }
                }
        })
    }
}
