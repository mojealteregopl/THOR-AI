package com.newoether.agora.newgpt

/**
 * Paczka 07: 30 zweryfikowanych repozytoriów i 18 nieaktywnych profili.
 * Revision oznacza obiekt Git tree, nie commit SHA ani potwierdzenie uruchomienia.
 * Rekord źródłowy i import profilu nie uruchamiają zewnętrznego narzędzia.
 */
object ThorPacketSevenCatalog {
    private val sourceData = """
mojealterego/elevenlabs-python|main|2a3eb7171eb1af6291f5aef9ac7cd9d409ef8756|CLIENT|REMOTE_ACCESS|ElevenLabs Python SDK oficjalna biblioteka API
mojealterego/softphone|inbound|08cd3a872d435f1c875f8ef014c959906efe8528|CLIENT|EXTERNAL_MUTATION|SpechPhone PHP Swoole SIP i RTP bez WebRTC
mojealterego/ACE-Step-1.5|main|ca1e85fe9430179831e6bc6be790c332190a3866|CREATIVE|PRIVILEGED_EXECUTION|ACE-Step 1.5 lokalna generacja i edycja muzyki
mojealterego/openai-cookbook|main|a78f3f37bd23637aac2b3f1e8b1251cf5bb9e1a7|KNOWLEDGE|READ_ONLY|OpenAI Cookbook przykłady używania OpenAI API
mojealterego/Context-Engine|main|974908f29b1928b38bfdb53031d643e6a64a3d9a|GAME|PRIVILEGED_EXECUTION|Context Engine silnik gry z RPC CLI MCP
mojealterego/elevenlabs-android|main|b7cb44bf65b032e5a1de229ac713d6fa1a66d96f|ANDROID|REMOTE_ACCESS|ElevenAgents Android SDK LiveKit i WebSocket
mojealterego/VoxCPM|main|f772e498a45fbb5fb8e13fbf9b9c48be9fe33e69|CREATIVE|PRIVILEGED_EXECUTION|VoxCPM2 lokalny TTS i klonowanie głosu
mojealterego/mobile-ai-agents|main|bd9311c37e834465a70f179b3bbabea6b160db37|CATALOG|READ_ONLY|Mobile AI Agents katalog 19 ról 53 skills 16 workflow
mojealterego/git-lfs|main|f0bffc4fe998fe5cb004dbca9e8951ea662ff66b|INFRASTRUCTURE|READ_ONLY|Git LFS wersjonowanie dużych plików i modeli
mojealterego/RayCodes_Breeze_TTS_2|master|7676cd815963cac448dcf68bf54ffb465e228c91|CREATIVE|REMOTE_ACCESS|Breeze TTS 2 prototyp Gradio z Ollama
mojealterego/saient|main|73073fd7fe17dc565b33a148e42282bcc1eb926c|ANDROID|PRIVILEGED_EXECUTION|Saient Android lokalne modele i generowanie Vulkan
mojealterego/dialogic|main|701b67bbe60114e010476d1685f7de1261091c29|GAME|EXTERNAL_MUTATION|Dialogic 2 narzędzie dialogów do Godot 4.5
mojealterego/Decepticon|main|31e1c8e786c83bb20f5c3d9ebc482cf9fb8ffa06|INFRASTRUCTURE|RESTRICTED|Decepticon autonomiczny red team z C2 bez autowykonania
mojealterego/plugin|main|c23c3850dd9ba19422b66824e125386cba522dc1|MCP|REMOTE_ACCESS|ElevenLabs Plugin umiejętności i hosted OAuth MCP
mojealterego/Awesome-AI-Hacking-Agents|main|98a1ec10b5bf0bf15ef4d82e4bbf519e0f849a2e|CATALOG|READ_ONLY|Katalog agentów bezpieczeństwa do analizy defensywnej
mojealterego/examples|main|9680624d9c7ab58d3f8c427cd11608b2a6027228|KNOWLEDGE|READ_ONLY|ElevenLabs przykłady audio i szablony aplikacji
mojealterego/homebrew-tap|main|ba72896d92647dcd758f86b33b269010d995dae6|KNOWLEDGE|READ_ONLY|ElevenLabs Homebrew tap pliki dystrybucji CLI
mojealterego/scoop-bucket|main|21e8fb23fe89e4d903c6e8dc3aff2dcdcf700066|KNOWLEDGE|READ_ONLY|ElevenLabs Scoop bucket manifesty dystrybucji CLI
mojealterego/sugar-mcp|main|cbef04afb703374513f149d1601da1480cbd38dc|MCP|RESTRICTED|Sugar MCP swap tokenów DeFi i transakcje blockchain
mojealterego/grapesjs|dev|2bdeda85b82b8b9ceae42fd6558cbbcae5ec2d21|CLIENT|READ_ONLY|GrapesJS monorepo wizualnego edytora WWW
mojealterego/com.vadhod.apkextractor|main|3384e12869ce4d7224ba6840aa750de5595bb0b3|ANDROID|READ_ONLY|Vadhod APK Extractor kopia APK i split APKS offline
mojealterego/railway-go-sdk|main|fc1c6d356d9480b2cd24af96d9ddb65592c2dfb4|INFRASTRUCTURE|EXTERNAL_MUTATION|Railway Go SDK deklaratywna konfiguracja infrastruktury
mojealterego/ui|main|88a5342ee74632a3f66f3cf9a75cbe87f97007f9|CLIENT|READ_ONLY|ElevenLabs UI komponenty React i shadcn audio
mojealterego/krypton-byte|master|a8b0700ff2764a69b918e675513c95370679aa35|CATALOG|READ_ONLY|Krypton Byte profil autora a nie framework agentów
mojealterego/sugar|main|f6aa23225279e3f45438e8c653fe9cddbcf1c49e|MCP|PRIVILEGED_EXECUTION|Sugar lokalna pamięć agentów SQLite i MCP
mojealterego/hermesclaw|main|03263b0065e05369e8f685c8bcc7531d947de1f1|ORCHESTRATION|PRIVILEGED_EXECUTION|HermesClaw most Hermes OpenClaw OpenCode WeChat
mojealterego/DorkAgent|main|8a1bbbefbacff1b9726f9c55d1234d1c4e79399a|INFRASTRUCTURE|PRIVILEGED_EXECUTION|DorkAgent automatyczny recon wymaga autoryzacji
mojealterego/hackingtool|master|ef5334f8d37e5be2eecbf56e334f5e3f0f6817ec|INFRASTRUCTURE|PRIVILEGED_EXECUTION|Hackingtool pentest tylko na autoryzowanych systemach
mojealterego/elevenlabs-mcp|main|c6dc1688ead5e0e67cdcaa5e336bed647019340c|MCP|READ_ONLY|ElevenLabs MCP lokalny serwer przestarzały
mojealterego/elevenlabs-mcp-player|main|0e130f7be161290c9febc57ef9aca6cadb8a81e0|MCP|READ_ONLY|ElevenLabs MCP player przestarzały
    """.trimIndent()

    val sources: List<ThorSource> = sourceData.lineSequence()
        .filter(String::isNotBlank)
        .map { row ->
            val fields = row.split('|')
            require(fields.size == 6) { "Nieprawidłowy rekord źródła paczki 07." }
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
            require(entries.size == 30)
            require(entries.map(ThorSource::repository).toSet().size == entries.size)
            require(entries.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
            require(entries.none(ThorSource::activated))
        }

    val roles: List<ThorRolePreset> = listOf(
        ThorRolePreset(
            id = "packet07.voice-android",
            name = "VOICE · ElevenAgents Android",
            description = "LiveKit oraz WebSocket",
            prompt = "Projektuj niskolatencyjne rozmowy Android przez oficjalny SDK ElevenAgents, z rozdziałem publicznego agentId i prywatnego tokenu sesji. Zapewnij kontrolę uprawnień mikrofonu, odmowę połączeń bez zgody i testy przerwań.",
            sourceRepositories = listOf("mojealterego/elevenlabs-android"),
        ),
        ThorRolePreset(
            id = "packet07.voice-python",
            name = "VOICE · ElevenLabs API Engineer",
            description = "Python SDK i zewnętrzne API",
            prompt = "Projektuj serwerowe wywołania ElevenLabs przez oficjalny SDK Python z walidacją parametrów, kontrolą limitu kosztów, retry i bezpiecznym przechowywaniem sekretów. Nie publikuj tokenów i nie deklaruj renderu bez odebranego pliku.",
            sourceRepositories = listOf("mojealterego/elevenlabs-python"),
        ),
        ThorRolePreset(
            id = "packet07.music",
            name = "MUSIC · Generative Composer",
            description = "ACE-Step 1.5 i muzyka",
            prompt = "Projektuj oryginalną muzykę i warianty aranżacji ACE-Step 1.5, w tym kontrolę długości, głosu i metryk jakości. Generacja wymaga potwierdzonego runtime GPU, praw do próbek i gotowych plików audio.",
            sourceRepositories = listOf("mojealterego/ACE-Step-1.5"),
        ),
        ThorRolePreset(
            id = "packet07.voice-model",
            name = "VOICE · Local TTS Specialist",
            description = "VoxCPM i Breeze TTS",
            prompt = "Porównuj lokalne modele VoxCPM2 i Breeze TTS 2 z uwzględnieniem jakości głosu, pamięci, latencji, zgodności języka polskiego i wymogów serwowania. Klonuj głos wyłącznie za zgodą właściciela próbki.",
            sourceRepositories = listOf("mojealterego/VoxCPM", "mojealterego/RayCodes_Breeze_TTS_2"),
        ),
        ThorRolePreset(
            id = "packet07.sip",
            name = "VOICE · SIP Web Softphone",
            description = "PHP Swoole i strumieniowanie RTP",
            prompt = "Projektuj separację sygnalizacji SIP od kanału RTP i PCM, kontrolę sesji, TLS i izolację połączeń oraz testy ruchu przychodzącego. Nie inicjuj prawdziwych połączeń ani rejestracji bez wyraźnej zgody.",
            sourceRepositories = listOf("mojealterego/softphone"),
        ),
        ThorRolePreset(
            id = "packet07.api",
            name = "LLM · OpenAI API Patterns",
            description = "Cookbook i przykłady",
            prompt = "Analizuj aktualne wzorce OpenAI API na podstawie wersjonowanych przykładów Cookbook. Projektuj obsługę błędów, bezpieczeństwo kluczy, limity tokenów, telemetry i testy integracyjne. Rozróżniaj przykład od działającej usługi.",
            sourceRepositories = listOf("mojealterego/openai-cookbook"),
        ),
        ThorRolePreset(
            id = "packet07.mobile-engineering",
            name = "ANDROID · Mobile Engineering Crew",
            description = "Agenci i skills mobile",
            prompt = "Projektuj prace zespołu agentów nad Android i Kotlin Multiplatform: plan, implementacja, QA, zabezpieczenia i publikacja. 19 ról upstream jest dokumentacją, a nie 19 równolegle uruchomionymi procesami THOR.",
            sourceRepositories = listOf("mojealterego/mobile-ai-agents"),
        ),
        ThorRolePreset(
            id = "packet07.device-media",
            name = "ANDROID · Native Media Engineer",
            description = "Saient i lokalne modele",
            prompt = "Analizuj lokalne generowanie obrazu i wideo Android oraz izolację modelowych assetów i pobrań z kontrolą SHA-256. Dostęp do aparatu, SMS i kontaktów wymaga oddzielnych uprawnień, testów i zgody.",
            sourceRepositories = listOf("mojealterego/saient"),
        ),
        ThorRolePreset(
            id = "packet07.game-core",
            name = "GAME · Context Engine Architect",
            description = "Silnik gry i kontrakt MCP",
            prompt = "Projektuj deterministyczne edytory gier, pakiety i kontrakty CLI RPC MCP na podstawie Context Game Engine. Odseparuj uprawnienia do projektu i testuj symulację w headless przed użyciem GPU.",
            sourceRepositories = listOf("mojealterego/Context-Engine"),
        ),
        ThorRolePreset(
            id = "packet07.game-dialogue",
            name = "GAME · Godot Dialogue Designer",
            description = "Dialogic 2 i scenariusze",
            prompt = "Opracuj strukturę dialogów, stany postaci i testy rozgałęzień w Dialogic 2 przy zachowaniu wymogu Godot co najmniej 4.5. Import scenariuszy musi być odtwarzalny i zgodny z licencją dodatku.",
            sourceRepositories = listOf("mojealterego/dialogic"),
        ),
        ThorRolePreset(
            id = "packet07.model-assets",
            name = "DEVOPS · Large Artifact Curator",
            description = "Git LFS i modele",
            prompt = "Projektuj obsługę dużych modeli GGUF i plików multimedialnych przez Git LFS, kontrolę sum SHA-256, retencję i ograniczenia transferu. Nie kopiuj zbędnych artefaktów do źródeł APK ani historii Git.",
            sourceRepositories = listOf("mojealterego/git-lfs"),
        ),
        ThorRolePreset(
            id = "packet07.web-builder",
            name = "WEB · Visual Editor Architect",
            description = "GrapesJS i builder",
            prompt = "Projektuj komponentowy edytor stron WWW i kontrakty zapisu stron przy użyciu rozwiązań GrapesJS. Przed publikacją waliduj HTML i CSS, odseparuj nieufne skrypty i zweryfikuj licencje zależności.",
            sourceRepositories = listOf("mojealterego/grapesjs"),
        ),
        ThorRolePreset(
            id = "packet07.apk",
            name = "ANDROID · Application Artifact Auditor",
            description = "Kopie APK i split APK",
            prompt = "Projektuj legalny audyt własnych aplikacji Android: eksport base oraz split APK, kontrolę certyfikatu, ABI, wersji i SHA-256. Nie omijaj podpisu ani zabezpieczeń cudzych aplikacji.",
            sourceRepositories = listOf("mojealterego/com.vadhod.apkextractor"),
        ),
        ThorRolePreset(
            id = "packet07.railway",
            name = "CLOUD · Railway IaC Engineer",
            description = "Go SDK i deployment",
            prompt = "Projektuj jednoznaczne pliki konfiguracji Railway w Go z planem zmian, budżetem i rollback. Wywołanie config apply to rzeczywista mutacja infrastruktury wymagająca jawnej akceptacji użytkownika.",
            sourceRepositories = listOf("mojealterego/railway-go-sdk"),
        ),
        ThorRolePreset(
            id = "packet07.memory",
            name = "MEMORY · Local Knowledge Engineer",
            description = "Sugar SQLite i MCP",
            prompt = "Projektuj lokalną pamięć projektową agentów na wzór Sugar z rozdziałem danych osobistych i projektowych, migracjami SQLite i śladem pochodzenia. Zadania autonomiczne i serwery MCP wymagają osobnego zezwolenia.",
            sourceRepositories = listOf("mojealterego/sugar"),
        ),
        ThorRolePreset(
            id = "packet07.hermes",
            name = "AGENTS · WeChat Gateway Architect",
            description = "HermesClaw i ACP",
            prompt = "Projektuj adaptery komunikacji dla Hermes Agent, OpenClaw i OpenCode z arbitrażem jednej sesji, ochroną tokenu i odrębnymi uprawnieniami narzędzi. Nie inicjuj połączeń z kontem WeChat bez autoryzacji.",
            sourceRepositories = listOf("mojealterego/hermesclaw"),
        ),
        ThorRolePreset(
            id = "packet07.defensive",
            name = "SECURITY · Agent Capability Auditor",
            description = "Katalog narzędzi defensywnych",
            prompt = "Kataloguj możliwości narzędzi AI do autoryzowanych testów bezpieczeństwa i porównuj ich zakres i licencje bez uruchamiania exploitów. Wnioski oznaczaj jako źródłowe lub potwierdzone testem w izolacji.",
            sourceRepositories = listOf("mojealterego/Awesome-AI-Hacking-Agents"),
        ),
        ThorRolePreset(
            id = "packet07.voice-studio",
            name = "VOICE · Cross-platform UX Designer",
            description = "ElevenLabs UI plugin i przykłady",
            prompt = "Projektuj nowoczesny interfejs syntezy mowy i agenta głosowego na podstawie ElevenLabs UI i oficjalnych przykładów. Unikaj przestarzałych lokalnych serwerów MCP; hosted OAuth wymaga zgody użytkownika.",
            sourceRepositories = listOf("mojealterego/plugin", "mojealterego/examples", "mojealterego/ui", "mojealterego/homebrew-tap", "mojealterego/scoop-bucket"),
        ),
    ).also { entries ->
        require(entries.size == 18)
        require(entries.map(ThorRolePreset::id).toSet().size == entries.size)
        require(entries.all { role ->
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
