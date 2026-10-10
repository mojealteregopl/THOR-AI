package com.newoether.agora.newgpt

/**
 * Pakiet 05 — 29 nowych źródeł po odfiltrowaniu gonnect z paczki 04.
 * Rewizje są identyfikatorami Git tree, nie podpisanymi artefaktami.
 * Źródła RESTRICTED nie mogą być użyte jako zaplecze żadnego profilu.
 */
object ThorPacketFiveCatalog {
    private val sourceData = """
mojealterego/hhvm|master|a57533996059e4dd638c66b9483ea3bb8151732f|INFRASTRUCTURE|PRIVILEGED_EXECUTION|HHVM maszynowa wirtualna Hacklang JIT
mojealterego/skillsOPENai|main|49f948faa9258a0c61caceaf225e179651397431|KNOWLEDGE|READ_ONLY|Katalog przestarzałych Codex Agent Skills
mojealterego/dcache|master|932dc9d508c6a4c822f744de1cf3bfe7c0eed356|INFRASTRUCTURE|REMOTE_ACCESS|dCache rozproszony system przechowywania plików
mojealterego/Omni-mobile|main|3f5f3a6373b9396138624aa8a6c3122eb2d1ec24|ANDROID|READ_ONLY|Omni Mobile szkic Kotlin Compose i WebSocket
mojealterego/WAO-AI-2|main|e8b558e889fb283ab8a09cbec151a0991052a3f5|ORCHESTRATION|REMOTE_ACCESS|WAO Agentic Visual Intelligence i kontrola referencji
mojealterego/llama.cpp|master|5d806aa2575e01e126651fd69ab1ab6cefff861d|INFRASTRUCTURE|PRIVILEGED_EXECUTION|llama.cpp natywna inferencja LLM i GGUF
mojealterego/ai|main|c2513ce9a3b58bfecf848f32b962ee8de228351b|CREATIVE|RESTRICTED|Repozytorium AI promujące niezweryfikowane NSFW generowanie i instalator
mojealterego/SYSTEM-EKSPERCKI-GRANT-BUSINESS-ARCHITECT|main|0b15b2ede2fc709f0cca0b392edad2514bff6ca7|ORCHESTRATION|READ_ONLY|Agent grantowy z dowodami i weryfikacją finansową
mojealterego/Cinematic-Agents|main|f5e815d82e71df372d2755e991a3d8c03ccbbee9|CREATIVE|REMOTE_ACCESS|Cinematic Agents wieloagentowa produkcja filmowa
mojealterego/engram|main|8525cdf75d38cbda7453f947d3aad143413f9b3b|MCP|REMOTE_ACCESS|Engram MCP zewnętrzna pamięć rozmów i OAuth
mojealterego/Build-Low-Code-No-Code-Machine-Learning-Web-App.|main|2a3ec8bd1369be49e2af32f6e87c217cc4c8910b|INFRASTRUCTURE|EXTERNAL_MUTATION|AURELIS ML Studio Streamlit i PyCaret AutoML
mojealterego/godot-orchestrator|main|1d4c2271df2dcd10b269f7400a26038f8019babd|GAME|EXTERNAL_MUTATION|Godot Orchestrator wtyczka skryptowania wizualnego
mojealterego/con-terminal|main|f4170e163b5fc929e5400dd1cc0dcfe71e113f31|CLIENT|PRIVILEGED_EXECUTION|Con Terminal z obsługą SSH i agentów
mojealterego/OmniVoice|master|08be0b4ccbac3e13e374e86fbfead4b4cac343e2|CREATIVE|PRIVILEGED_EXECUTION|OmniVoice wielojęzyczna synteza mowy i klonowanie
mojealterego/langchain|master|e670c7a03ba36fd1516f0185f7ec1186c89aa471|ORCHESTRATION|REMOTE_ACCESS|LangChain SDK agentów i narzędzi LLM
mojealterego/Llamatik|main|12fb1ed418166ce226e6633a2e4e9c7c49cdda9f|ANDROID|PRIVILEGED_EXECUTION|Llamatik Kotlin MPP lokalne LLM STT i generowanie obrazu
mojealterego/SillyTavern|release|8172dcd0ee672d3cd9a5e5f7af134f91a45cd2b8|CLIENT|READ_ONLY|SillyTavern elastyczny frontend czatów LLM
mojealterego/agent|main|3dc5aca4fa77985a518b92d6f3fa1dd62e70508f|MCP|PRIVILEGED_EXECUTION|1MCP agregator serwerów i narzędzi MCP
mojealterego/activepieces|main|5ec8114c9a815ceea283196f5ab9e2c0155cb434|ORCHESTRATION|EXTERNAL_MUTATION|Activepieces automatyzacja z bramami akceptacji
mojealterego/graphify|v8|67f99bd0059dd1bac9e44382907ef9f10098b39f|KNOWLEDGE|READ_ONLY|Graphify graf kodu AST i dokumentów
mojealterego/agent-framework|main|fdf7e80ad6c8f6dbdd9b779d5d6923c74d8b583e|ORCHESTRATION|REMOTE_ACCESS|Microsoft Agent Framework Python i .NET
mojealterego/mcp-server-phone|main|174fb9ed1dfd44bd56893c2be00d35e3d7540b39|MCP|RESTRICTED|VirtualSMS numery OTP i serwer MCP bez automatycznej aktywacji
mojealterego/TTS-WebUI|main|2e5701387c423307d73972a45496bfdcb6a7d8e1|CREATIVE|PRIVILEGED_EXECUTION|TTS WebUI platforma modeli głosowych
mojealterego/VoxCPM2_portable|main|5b06f0e8ed3714be8ff03b457c1ebbf2794779c8|CREATIVE|PRIVILEGED_EXECUTION|VoxCPM2 lokalny wielojęzyczny TTS i LoRA
mojealterego/IBM-Cloud-automation|main|49b2cd81f4762921ed455be5c8f589ee369f7cfb|INFRASTRUCTURE|EXTERNAL_MUTATION|IBM Cloud Automation policy-gated agent hierarchy
mojealterego/graphiti|main|b943c9e8486cdc7fe6cb2f4cfe151ae53f0a884d|KNOWLEDGE|REMOTE_ACCESS|Graphiti temporalne grafy wiedzy i pamięci
mojealterego/aihub|main|fd49739118ba6a146759f612a4af76e5df53eb50|ANDROID|READ_ONLY|AI Hub Android agregator UI asystentów
mojealterego/OpenMontage|main|08e2151fa02de28a5d6a312b3d575692bf147ad7|CREATIVE|EXTERNAL_MUTATION|OpenMontage montaż sceny i produkcja wideo
mojealterego/unity-mcp|beta|2fcc17957823f2494b7b1f7ade92c0fb56f4adb1|GAME|PRIVILEGED_EXECUTION|CoplayDev Unity MCP sterowanie edytorem i testami
    """.trimIndent()

    val sources: List<ThorSource> = sourceData.lineSequence()
        .filter(String::isNotBlank)
        .map { row ->
            val fields = row.split('|')
            require(fields.size == 6) { "Nieprawidłowy rekord źródła paczki 05." }
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
            id = "packet05.gguf",
            name = "LLM · GGUF Runtime Engineer",
            description = "llama.cpp i Kotlin",
            prompt = "Projektuj interfejsy inferencji GGUF i sesji Kotlin Multiplatform. Sprawdzaj wersje ABI, NDK, JNI, kompatybilność tokenizerów oraz budżet RAM. Nie deklaruj integracji bez kompilacji i pomiarów.",
            sourceRepositories = listOf("mojealterego/llama.cpp", "mojealterego/Llamatik"),
        ),
        ThorRolePreset(
            id = "packet05.mobile",
            name = "ANDROID · Omni Platform",
            description = "Kotlin UI i lokalne AI",
            prompt = "Projektuj aplikację Android na bazie Compose z jawną granicą WebSocket i bezpiecznym magazynem sesji. Analizuj Omni-mobile i AI Hub jako referencje UI, bez kopiowania gotowej aplikacji.",
            sourceRepositories = listOf("mojealterego/Omni-mobile", "mojealterego/aihub"),
        ),
        ThorRolePreset(
            id = "packet05.visual",
            name = "VISION · Reference Controller",
            description = "Wierność referencjom",
            prompt = "Projektuj bezpieczny pipeline analizy obrazu, blokady tożsamości, perspektywy i światła oraz kontrolę zgodności z referencją. Wynik ma być specyfikacją, nie nieudokumentowaną edycją obrazu.",
            sourceRepositories = listOf("mojealterego/WAO-AI-2"),
        ),
        ThorRolePreset(
            id = "packet05.cinema",
            name = "CINEMA · Film Production Director",
            description = "Wieloagentowa realizacja filmowa",
            prompt = "Rozdziel reżyserię, storyboard, zdjęcia, światło, montaż, dźwięk i kontrolę ciągłości na odtwarzalne etapy. Weryfikuj pliki i licencje materiałów, koszty oraz akceptację przed publikacją.",
            sourceRepositories = listOf("mojealterego/Cinematic-Agents", "mojealterego/OpenMontage"),
        ),
        ThorRolePreset(
            id = "packet05.voice",
            name = "VOICE · Speech Synthesis Engineer",
            description = "OmniVoice i VoxCPM2",
            prompt = "Projektuj wielojęzyczne TTS i voice design z kontrolą jakości, latency i formatów audio. Klonowanie głosu tylko za zgodą właściciela głosu, z udokumentowanym pochodzeniem próbek.",
            sourceRepositories = listOf("mojealterego/OmniVoice", "mojealterego/VoxCPM2_portable", "mojealterego/TTS-WebUI"),
        ),
        ThorRolePreset(
            id = "packet05.agent",
            name = "AGENTS · Framework Engineer",
            description = "LangChain i Microsoft MAF",
            prompt = "Porównuj biblioteki agentów, interfejsy tool calling, role, middleware, retry i kontrolę stanu. Unikaj nadmiarowych adapterów i nie deleguj akcji bez pozytywnej decyzji policy gateway.",
            sourceRepositories = listOf("mojealterego/langchain", "mojealterego/agent-framework"),
        ),
        ThorRolePreset(
            id = "packet05.mcp",
            name = "MCP · Federation Architect",
            description = "Agregacja 1MCP",
            prompt = "Projektuj bramę agregującą MCP z allowlist, ograniczeniami argumentów, ujawnianiem minimalnego zestawu narzędzi i przeglądem tokenów. Samo dodanie źródła nie uruchamia 1MCP.",
            sourceRepositories = listOf("mojealterego/agent"),
        ),
        ThorRolePreset(
            id = "packet05.workflows",
            name = "AUTOMATION · Event Engineer",
            description = "Activepieces",
            prompt = "Projektuj wyzwalacze automatyzacji i przepływy z retry, kompensacją, idempotencją i human approval. Każda akcja sieciowa i mutacja zewnętrzna musi mieć konkretny zakres uprawnień.",
            sourceRepositories = listOf("mojealterego/activepieces"),
        ),
        ThorRolePreset(
            id = "packet05.memory",
            name = "MEMORY · Temporal Graph Architect",
            description = "Graphiti, Graphify i Engram",
            prompt = "Projektuj wielowarstwową pamięć agentów z wersjonowaniem faktów, ich źródeł i okresów ważności. Oddziel dane lokalne od zewnętrznych usług pamięci; przed transferem konwersacji wymagaj zgody.",
            sourceRepositories = listOf("mojealterego/graphiti", "mojealterego/graphify", "mojealterego/engram"),
        ),
        ThorRolePreset(
            id = "packet05.grants",
            name = "BUSINESS · Grant Evidence Architect",
            description = "Audyt wniosków grantowych",
            prompt = "Przygotowuj audytowalne modele biznesowe i grantowe na podstawie aktualnych źródeł programu, danych liczbowych i dowodów. Każda zmienna bez potwierdzenia ma być oznaczona jako brak danych.",
            sourceRepositories = listOf("mojealterego/SYSTEM-EKSPERCKI-GRANT-BUSINESS-ARCHITECT"),
        ),
        ThorRolePreset(
            id = "packet05.automl",
            name = "DATA · AutoML Engineer",
            description = "AURELIS i PyCaret",
            prompt = "Projektuj walidację CSV, profilowanie danych, cross-validation, metryki holdout i reproducible experiment tracking. Nie uznawaj modeli za wytrenowane bez dzienników oraz artefaktów walidacji.",
            sourceRepositories = listOf("mojealterego/Build-Low-Code-No-Code-Machine-Learning-Web-App."),
        ),
        ThorRolePreset(
            id = "packet05.godot",
            name = "GAME · Godot Visual Engineer",
            description = "Godot Orchestrator",
            prompt = "Projektuj grafy skryptów wizualnych Godot z kontrolą ABI wtyczki GDExtension i wersji silnika. Wyniki zmian muszą pochodzić z faktycznego edytora i testów scen.",
            sourceRepositories = listOf("mojealterego/godot-orchestrator"),
        ),
        ThorRolePreset(
            id = "packet05.unity",
            name = "GAME · Unity MCP Engineer",
            description = "CoplayDev Unity bridge",
            prompt = "Projektuj integrację klienta MCP z Unity Editor, z limitami uprawnień i procedurą wycofania zmian. Każdy skrypt, asset i build wymaga potwierdzenia przez narzędzia edytora.",
            sourceRepositories = listOf("mojealterego/unity-mcp"),
        ),
        ThorRolePreset(
            id = "packet05.terminal",
            name = "DESKTOP · Agent Terminal",
            description = "Con Terminal i SSH",
            prompt = "Projektuj bezpieczne wykonywanie poleceń na jawnie wskazanej maszynie i sesji. Wymagaj allowlist komend, ograniczonych uprawnień oraz audytu logów bez ujawniania sekretów.",
            sourceRepositories = listOf("mojealterego/con-terminal"),
        ),
        ThorRolePreset(
            id = "packet05.storage",
            name = "STORAGE · Distributed Architect",
            description = "dCache storage",
            prompt = "Projektuj duże rozproszone repozytoria danych z NFS i WebDAV, kontrolą retencji i spójności. Uwzględnij prawa AGPL, koszty odtwarzania oraz przegląd dostępu, nie uruchamiaj magazynu bez infrastruktury.",
            sourceRepositories = listOf("mojealterego/dcache"),
        ),
        ThorRolePreset(
            id = "packet05.hackvm",
            name = "INFRA · HHVM Specialist",
            description = "Hacklang i wirtualna maszyna",
            prompt = "Opracuj kompatybilność runtime Hacklang i HHVM, zależności JIT, obrazy kontenerowe i testy regresji. Oddziel szacunki wydajności od benchmarków oraz weryfikuj zasady licencjonowania.",
            sourceRepositories = listOf("mojealterego/hhvm"),
        ),
        ThorRolePreset(
            id = "packet05.skills",
            name = "KNOWLEDGE · Skills Curator",
            description = "Archiwalne Agent Skills OpenAI",
            prompt = "Kataloguj i oceniaj umiejętności Agent Skills względem aktualnych kontraktów. Repozytorium źródłowe jest oznaczone jako przestarzałe; nie uruchamiaj pochodzących z niego skryptów bez audytu.",
            sourceRepositories = listOf("mojealterego/skillsOPENai"),
        ),
        ThorRolePreset(
            id = "packet05.chat",
            name = "CHAT · Conversation Interface",
            description = "SillyTavern frontend",
            prompt = "Projektuj bezpieczną prezentację czatów i stanów modeli, historię sesji, moderację widoku i ochronę danych. SillyTavern pozostaje niezależnym klientem, nie wbudowanym modułem APK.",
            sourceRepositories = listOf("mojealterego/SillyTavern"),
        ),
        ThorRolePreset(
            id = "packet05.cloud",
            name = "CLOUD · Governed Supervisor",
            description = "IBM Cloud automation",
            prompt = "Projektuj rozdział agenta nadzorczego, decyzji policy engine i wykonania w IBM Cloud. Wymagaj potwierdzonych tożsamości, audytu, budżetu i mechanizmu rollback dla każdej zmiany infrastruktury.",
            sourceRepositories = listOf("mojealterego/IBM-Cloud-automation"),
        ),
        ThorRolePreset(
            id = "packet05.edit",
            name = "VIDEO · Editing and Review",
            description = "OpenMontage pipeline",
            prompt = "Projektuj nie tylko prompty wideo, lecz montaż rzeczywistych klipów w chronologii, synchronizację audio, napisy i finalny rendering. Każde wyjście musi być sprawdzone na pliku i licencji assetów.",
            sourceRepositories = listOf("mojealterego/OpenMontage"),
        ),
    ).also { roles ->
        require(roles.size == 20)
        require(roles.map(ThorRolePreset::id).toSet().size == roles.size)
        require(roles.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { repository ->
                    sources.any { source ->
                        source.repository == repository &&
                            source.risk != ThorSourceRisk.RESTRICTED
                    }
                }
        })
    }
}
