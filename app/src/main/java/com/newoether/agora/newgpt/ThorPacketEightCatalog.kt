package com.newoether.agora.newgpt

/**
 * Paczka 08 — 28 zweryfikowanych repozytoriów i 22 nieaktywne profile.
 * Identyfikator revision to SHA korzenia drzewa Git (nie commit i nie atestacja).
 * Katalog nie pobiera modeli ani nie uruchamia edytorów, daemonów lub MCP.
 */
object ThorPacketEightCatalog {
    private val sourceData = """
mojealterego/ComfyUI-LTXVideo|master|15d09abb5a187a8dcaea2fc31fe51ee96e6c9d0d|CREATIVE|PRIVILEGED_EXECUTION|Węzły LTX-2.3 ComfyUI i workflow generowania wideo GPU
mojealterego/homebrew-engram|main|81fdc21f5a734608dfd56f29ad527655bfa4d16c|KNOWLEDGE|REMOTE_ACCESS|Instalator Engram Homebrew daemon synchronizacji transkryptów
mojealterego/sanna|main|b9cfdabbb1214b36413395957a117299ebe6f665|INFRASTRUCTURE|REMOTE_ACCESS|Sanna policy gateway przed wykonaniem i attestation po wykonaniu
mojealterego/NekokoLPA|master|e3202fb233b006f197ca77c0c314c6d9538d91d5|ANDROID|PRIVILEGED_EXECUTION|NekokoLPA 1 obsługa eSIM i czytników kart na Android
mojealterego/cookbooks|main|8b9f918dd41f7a73d070c7542fb8c3bf9b728a14|KNOWLEDGE|READ_ONLY|Future AGI TraceAI przykłady ewaluacji i agentów
mojealterego/stitch-skills|main|0337446dadde6f8c94210444e2aa9d546126480f|CATALOG|READ_ONLY|Google Stitch Skills projektowanie interfejsów i MCP
mojealterego/pentagram|main|f5b016693ff8dd731f6b86b38d3cc2a850f558c9|KNOWLEDGE|PRIVILEGED_EXECUTION|Pentagram pamięć S-expr z replay wykonywania kodu
mojealterego/Unreal-AI-Template|main|e746f7c8e1c8ac1e132b3eb569e059eff504b738|GAME|READ_ONLY|Unreal-MCP C++ szablon rozszerzeń edytora
mojealterego/Unreal-AI-PCG|main|2b47f328cdda63ef0f4cdf76f34ed89b4afcb921|GAME|EXTERNAL_MUTATION|Unreal-MCP PCG grafy i generowanie proceduralne
mojealterego/Unreal-AI-Niagara|main|09f0afe7401bd606a3920fb50136f9159ba800ba|GAME|READ_ONLY|Unreal AI Niagara szablon modułu i testy a nie potwierdzony efekt VFX
mojealterego/Unreal-AI-Landscape|main|aee91396f880cf8fc23d29a204880b67df598c71|GAME|READ_ONLY|Unreal-MCP inspekcja Landscape i Water read-only
mojealterego/Unreal-AI-GAS|main|9fd970b1a30a4a6d43dd0a196ea02a791dae7064|GAME|EXTERNAL_MUTATION|Unreal-MCP GameplayAbilities GAS inspekcja i grant zdolności
mojealterego/Unreal-AI-EnhancedInput|main|bb4e411b56b98de3e43f21cde75df3095a22e2d1|GAME|EXTERNAL_MUTATION|Unreal-MCP Enhanced Input tworzenie i mapowanie Input Action
mojealterego/Godot-AI-Tools-Template|main|b4a884dec00e6ea3c7ef09969b82fda9d21907cb|GAME|READ_ONLY|Godot MCP szablon narzędzi C# i NuGet source-only
mojealterego/prim-ftpd|master|fc83f19a2f3f3bec912eaa606fe77b8ff464fb4d|ANDROID|PRIVILEGED_EXECUTION|Primitive FTPd Android serwer plików FTP i SFTP
mojealterego/MumbleLink|develop|d8f6cf2b854723a0348a30a15270b9c16101155d|GAME|REMOTE_ACCESS|MumbleLink Minecraft Forge pozycyjne audio Mumble
mojealterego/duix-skills|main|e9cd894bf4ec1e91178d0af795d6ef3711e1a38f|CREATIVE|EXTERNAL_MUTATION|Duix cyfrowy avatar rozmowa i lipsync z opłatami API
mojealterego/EchoPBX|main|b57af463dee1d08837988b641c5bb740ee6a9772|INFRASTRUCTURE|EXTERNAL_MUTATION|EchoPBX beta centrala Asterisk SIP i kolejki
mojealterego/skills-phone|main|be674fda44d465df53898cafcf8e905f9b6ab210|MCP|RESTRICTED|SV Number skill zakup numerów OTP i automatyzacja rejestracji kont
mojealterego/ChatGPT-CodeReview|main|8bd66227f057f21e847a8c27c9b356c25271d292|INFRASTRUCTURE|EXTERNAL_MUTATION|ChatGPT CodeReview bot automatyczna ocena Pull Request
mojealterego/agentskills|main|69ef37e9424c0a7ea9dd2293b559e43ec8176379|KNOWLEDGE|READ_ONLY|Agent Skills otwarty format SKILL.md i progressive disclosure
mojealterego/Meta3D|master|77683f4be53c7e98efa3ce3bf2e7606d6877a1af|GAME|EXTERNAL_MUTATION|Meta3D webowa platforma no-code edytora 3D
mojealterego/TTS-Latency-Benchmark|main|fef92100816758b53db0946413dfc31b066f47eb|KNOWLEDGE|READ_ONLY|TTS benchmark TTFB TTFA i ciszy startowej
mojealterego/PhoneClaw|main|31bf61d3219f597f62b7672f94475148bca5de19|CLIENT|PRIVILEGED_EXECUTION|PhoneClaw iOS lokalny agent z natywnymi skills telefonu
mojealterego/duix-doc|main|ef44fd919c99af2faba0eb1e039320031a384ec8|KNOWLEDGE|READ_ONLY|DUIX Mintlify dokumentacja narzędzi cyfrowego avatara
mojealterego/TTS-Voice-Direction-Benchmark|main|c6db43648cb8968f99319207becc2090c61ac4d4|KNOWLEDGE|READ_ONLY|TTS benchmark Voice Direction Score i speaker similarity
mojealterego/TTS-Voice-Design-Benchmark|main|bbe46215cf0da646d43027a2548c970279451000|KNOWLEDGE|READ_ONLY|TTS benchmark projektowania głosu i różnorodności
mojealterego/pegasus-skills|main|ad14bf4601ff1f0dd578fc0bf621b658d8a4055a|KNOWLEDGE|READ_ONLY|SaaS Pegasus Django umiejętności aktualizacji i konfliktów merge
    """.trimIndent()

    val sources: List<ThorSource> = sourceData.lineSequence()
        .filter(String::isNotBlank)
        .map { row ->
            val fields = row.split('|')
            require(fields.size == 6) { "Nieprawidłowy wpis repozytorium w paczce 08." }
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
            require(entries.size == 28)
            require(entries.map(ThorSource::repository).toSet().size == entries.size)
            require(entries.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
            require(entries.none(ThorSource::activated))
        }

    val roles: List<ThorRolePreset> = listOf(
        ThorRolePreset(
            id = "packet08.governance",
            name = "SECURITY · Sanna Policy Gateway",
            description = "Weryfikowalna polityka agentów",
            prompt = "Projektuj pre-execution policy gateway i podpisywane receipts, oddzielając obserwację post-execution od blokowania przed wykonaniem. Nie przedstawiaj dekoratora @sanna_observe jako zapory powstrzymującej skutki już wykonanych funkcji.",
            sourceRepositories = listOf("mojealterego/sanna"),
        ),
        ThorRolePreset(
            id = "packet08.memory",
            name = "MEMORY · Engram Pentagram Architect",
            description = "Warstwy pamięci agentów",
            prompt = "Projektuj trwałą pamięć z historią i kontrolą czasu ważności. Uwzględnij, że pentagram przechowuje wykonywalne wyrażenia replay; zabroń ich automatycznego wykonania i zapobiegaj eksportowi transkryptów bez zgody.",
            sourceRepositories = listOf("mojealterego/pentagram", "mojealterego/homebrew-engram"),
        ),
        ThorRolePreset(
            id = "packet08.video",
            name = "MEDIA · LTX 2.3 Engineer",
            description = "Generacja wideo ComfyUI",
            prompt = "Opracuj kolejkowanie scen i konfigurację węzłów ComfyUI LTX-2.3, testy zgodności wejść i wyjść oraz pomiar GPU VRAM. Nie deklaruj gotowego renderu bez odebranego artefaktu wideo.",
            sourceRepositories = listOf("mojealterego/ComfyUI-LTXVideo"),
        ),
        ThorRolePreset(
            id = "packet08.esim",
            name = "ANDROID · eSIM Provisioning Analyst",
            description = "Czytniki i eSIM",
            prompt = "Projektuj audyt zgodności eSIM, OMAPI i czytników CCID, z weryfikacją uprawnień, prywatności profili i ograniczeń urządzeń. Nie przeprowadzaj provisioning ani transferów profili bez wyraźnej zgody właściciela.",
            sourceRepositories = listOf("mojealterego/NekokoLPA"),
        ),
        ThorRolePreset(
            id = "packet08.evaluation",
            name = "QA · TraceAI Cookbook Engineer",
            description = "Obserwowalność agentów",
            prompt = "Projektuj eksperymenty i odtwarzalne przypadki testowe TraceAI oraz Future AGI, sprawdzaj źródła i PII przed przekazaniem do evaluatorów. Odróżniaj kod demonstracyjny od rzeczywistych wyników CI.",
            sourceRepositories = listOf("mojealterego/cookbooks"),
        ),
        ThorRolePreset(
            id = "packet08.stitch",
            name = "UI · Google Stitch Designer",
            description = "Skills do projektowania UI",
            prompt = "Opracuj plan ekranów, tokeny stylu, dostępność i eksport komponentów z Google Stitch Skills. Wymagaj sprawdzonego dostępu MCP i licencji przed zewnętrznym uruchomieniem lub publikacją projektu.",
            sourceRepositories = listOf("mojealterego/stitch-skills"),
        ),
        ThorRolePreset(
            id = "packet08.unreal-pcg",
            name = "UNREAL · Procedural World Builder",
            description = "Rozszerzenie PCG",
            prompt = "Projektuj testy procedur PCG Unreal, inspekcję grafów i warunki uruchomienia generacji. Rozdziel read-only od operacji mutujących obiekty UE, wymagających zatwierdzenia i planu rollback.",
            sourceRepositories = listOf("mojealterego/Unreal-AI-PCG"),
        ),
        ThorRolePreset(
            id = "packet08.unreal-vfx",
            name = "UNREAL · Niagara Extension Reviewer",
            description = "Szablon edytora Niagara",
            prompt = "Projektuj implementację C++ providerów Unreal-MCP na podstawie szablonu i plików Niagara, lecz nie deklaruj gotowych narzędzi VFX bez analizy implementacji oraz testów w Unreal Editor.",
            sourceRepositories = listOf("mojealterego/Unreal-AI-Niagara", "mojealterego/Unreal-AI-Template"),
        ),
        ThorRolePreset(
            id = "packet08.unreal-landscape",
            name = "UNREAL · Terrain Inspector",
            description = "Read-only Landscape i Water",
            prompt = "Opracuj plan analizy aktorów Landscape i obiektów Water w Unreal, uwzględniając zależności pluginów i dokumentowane API. Nie modyfikuj terenu w profilu przeznaczonym do inspekcji.",
            sourceRepositories = listOf("mojealterego/Unreal-AI-Landscape"),
        ),
        ThorRolePreset(
            id = "packet08.unreal-gas",
            name = "UNREAL · Gameplay Ability Architect",
            description = "Gameplay Ability System",
            prompt = "Analizuj zdolności, efekty i Attribute Set systemu GAS oraz separuj inspekcję od przydzielania zdolności aktorom. Zmiany w projekcie Unreal wymagają aprobaty i testów silnika.",
            sourceRepositories = listOf("mojealterego/Unreal-AI-GAS"),
        ),
        ThorRolePreset(
            id = "packet08.unreal-input",
            name = "UNREAL · Enhanced Input Engineer",
            description = "Mapowanie sterowania",
            prompt = "Projektuj konfigurację Input Action i Mapping Context z walidacją klawiszy i konfliktów mapowań. Operacje tworzenia zasobów lub zmian projektu Unreal są mutacją i wymagają zatwierdzenia.",
            sourceRepositories = listOf("mojealterego/Unreal-AI-EnhancedInput"),
        ),
        ThorRolePreset(
            id = "packet08.godot",
            name = "GODOT · MCP Tools Engineer",
            description = "Narzędzia C# do Godot",
            prompt = "Projektuj źródłowe rozszerzenia Godot MCP w C# z atrybutami AiToolType, kontraktami NuGet i testami headless. Sam szablon nie zapewnia podłączonego Godot Editor ani gotowych adapterów.",
            sourceRepositories = listOf("mojealterego/Godot-AI-Tools-Template"),
        ),
        ThorRolePreset(
            id = "packet08.android-files",
            name = "ANDROID · SFTP Filesystem Engineer",
            description = "Transfer plików mobilnych",
            prompt = "Projektuj bezpieczny transfer plików do własnego urządzenia Android przez SFTP z autoryzacją klucza publicznego, ograniczeniem katalogów i audytem. Wyklucz anonymous login w konfiguracji produkcyjnej.",
            sourceRepositories = listOf("mojealterego/prim-ftpd"),
        ),
        ThorRolePreset(
            id = "packet08.positional-audio",
            name = "GAME · Positional Audio Engineer",
            description = "Minecraft Mumble positional audio",
            prompt = "Projektuj integrację pozycyjnego VoIP pomiędzy Minecraft Forge i Mumble, ze stanem sesji, ochroną danych pozycji i testami opóźnień. MumbleLink jest modem gry a nie samodzielną centralą SIP.",
            sourceRepositories = listOf("mojealterego/MumbleLink"),
        ),
        ThorRolePreset(
            id = "packet08.pbx",
            name = "VOICE · PBX Architect",
            description = "EchoPBX oraz Asterisk",
            prompt = "Projektuj własną centralę SIP na bazie Asterisk i EchoPBX z TLS SRTP, lokalnymi numerami testowymi, kolejkami i kontrolą dostępu. Nie realizuj połączeń zewnętrznych bez autoryzacji i kosztorysu.",
            sourceRepositories = listOf("mojealterego/EchoPBX"),
        ),
        ThorRolePreset(
            id = "packet08.avatar",
            name = "VIDEO · Digital Human Specialist",
            description = "DUIX postacie i synchronizacja ust",
            prompt = "Projektuj zgodne z prawami wizerunku tworzenie avatarów i lipsync poprzez duix-skills. Każde użycie portretu, głosu i płatnych kredytów wymaga uprawnień, zgody i zweryfikowanego wyniku.",
            sourceRepositories = listOf("mojealterego/duix-skills", "mojealterego/duix-doc"),
        ),
        ThorRolePreset(
            id = "packet08.codereview",
            name = "CODE · Review Gate Engineer",
            description = "GitHub CodeReview",
            prompt = "Projektuj proces niezależnej oceny PR z zasadami minimalnych uprawnień, redakcją sekretów i testami regresji. Review nie jest dowodem zielonej kompilacji i nie może samodzielnie scalać gałęzi.",
            sourceRepositories = listOf("mojealterego/ChatGPT-CodeReview"),
        ),
        ThorRolePreset(
            id = "packet08.skills",
            name = "AGENTS · Skills Standard Architect",
            description = "Otwarta specyfikacja Agent Skills",
            prompt = "Projektuj indeksowanie i ładowanie umiejętności SKILL.md z progressive disclosure, kontrolą metadanych, licencji i ścieżek. Nie uruchamiaj skryptów niezweryfikowanych skills z repozytoriów.",
            sourceRepositories = listOf("mojealterego/agentskills"),
        ),
        ThorRolePreset(
            id = "packet08.web3d",
            name = "3D · Visual World Builder",
            description = "Meta3D low-code",
            prompt = "Projektuj komponentowe interfejsy i silniki Web3D z rozdziałem modelu sceny i renderera. Weryfikuj licencje, konfigurację browser sandbox oraz bezpieczeństwo ładowanych pakietów.",
            sourceRepositories = listOf("mojealterego/Meta3D"),
        ),
        ThorRolePreset(
            id = "packet08.tts-evals",
            name = "QA · Speech Benchmark Lead",
            description = "TTFA VDS i Voice Design",
            prompt = "Projektuj porównywalne benchmarki TTS mierzące czas do pierwszego słyszalnego dźwięku, podobieństwo i zgodność głosu z opisem. Nie twórz fikcyjnych ocen i uwzględnij koszty modeli oceniających.",
            sourceRepositories = listOf("mojealterego/TTS-Latency-Benchmark", "mojealterego/TTS-Voice-Direction-Benchmark", "mojealterego/TTS-Voice-Design-Benchmark"),
        ),
        ThorRolePreset(
            id = "packet08.ios",
            name = "IOS · PhoneClaw Capability Architect",
            description = "Lokalne agents i iOS",
            prompt = "Analizuj lokalny runtime PhoneClaw na iOS i model autoryzacji dostępu do kontaktów, kalendarza, zdjęć i danych zdrowotnych. Nie zakładaj zgodności funkcji iOS z APK na Android bez osobnego portu.",
            sourceRepositories = listOf("mojealterego/PhoneClaw"),
        ),
        ThorRolePreset(
            id = "packet08.saas",
            name = "WEB · SaaS Pegasus Upgrade Engineer",
            description = "Django i SaaS Pegasus",
            prompt = "Projektuj bezpieczne aktualizacje frameworka SaaS Pegasus w Django, testy migracji oraz kontrolowane rozwiązywanie konfliktów merge. Nie myl SaaS Pegasus ze spyware Pegasus i nie uruchamiaj instalatorów bez przeglądu.",
            sourceRepositories = listOf("mojealterego/pegasus-skills"),
        ),
    ).also { entries ->
        require(entries.size == 22)
        require(entries.map(ThorRolePreset::id).toSet().size == entries.size)
        require(entries.all { role ->
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
