package com.newoether.agora.newgpt

/**
 * Verified repository metadata from the second user-supplied packet.
 *
 * Revision identifiers are Git tree object IDs observed on the named default
 * branches. These sources are not installed or connected by this catalogue.
 */
object ThorPacketTwoCatalog {
    private val sourceData = """
mojealterego/Unity-AI-Animation|main|b1f38ae6a618c851f9136f088cf2eb44099879dc|GAME|EXTERNAL_MUTATION|Animacja, klipy i kontrolery Animator
mojealterego/Unity-AI-Timeline|main|a9b35a2abb5b13be24f046410db7774f5be9b80b|GAME|EXTERNAL_MUTATION|Timeline i sekwencje filmowe Unity
IvanMurzak/Unity-AI-Tilemap|main|93a141ab3d13f1cbbbc38dcf44ec3d2d599dc8fd|GAME|EXTERNAL_MUTATION|Tilemap i poziomy 2D Unity
IvanMurzak/Unity-AI-Terrain|main|9593cd81379c41feac3a204da3de34614935d555|GAME|EXTERNAL_MUTATION|Terrain i krajobrazy Unity
mojealterego/Unity-AI-Splines|main|ae6a01e7e83f8fc62967bdda60480264704858d8|GAME|EXTERNAL_MUTATION|Splines i krzywe Unity
mojealterego/Unity-AI-ProBuilder|main|551dcba41aa9915524a2eba9e371ad1d08411b36|GAME|EXTERNAL_MUTATION|Geometria ProBuilder Unity
mojealterego/Unity-AI-InputSystem|main|feb72ccfd3b8403c1ad6eaaaf4b52666f61d1058|GAME|EXTERNAL_MUTATION|Input Actions i sterowanie Unity
IvanMurzak/Unity-AI-ParticleSystem|main|de6b6f9216fb7a0eb686d2ddce4b50eb8a1f95f8|GAME|EXTERNAL_MUTATION|Efekty cząsteczkowe Unity
mojealterego/Unity-AI-Navigation|main|df563ba87d2218f321352a29024f819ddcd80343|GAME|EXTERNAL_MUTATION|NavMesh i nawigacja Unity
mojealterego/Unity-AI-Cinemachine|main|948273c3c68300f079f60140a46c86dd75909c7e|GAME|EXTERNAL_MUTATION|Kamery Cinemachine Unity
mojealterego/Ai-game-factory|main|d1a58a418f5574064a146503800150c84640fed7|GAME|REMOTE_ACCESS|System produkcji gier i pipeline agentowy
mojealterego/computer-use-linux|main|3e5f0c5a787560ff424c5658c37b8f0246d846cd|MCP|PRIVILEGED_EXECUTION|MCP sterowania pulpitem Linux
mojealterego/Unity-AI-Tools-Template|main|e24d2a9574d7982ab8d5f01098f25e5ab2849ed8|GAME|READ_ONLY|Szablon narzędzi Unity MCP
mojealterego/GameDev-MCP-Server|main|3879e2ea341e20c0d8c933fdb9131c49b9db41bf|MCP|PRIVILEGED_EXECUTION|Brama MCP do edytorów Unity i Unreal
mojealterego/ida-pro-mcp|main|c133c3853faa111a9b00ee615c013b720d0c4acd|MCP|PRIVILEGED_EXECUTION|Integracja analizy kodu binarnego IDA Pro
mojealterego/Android-AI-Studio|main|11db8dd28fecaba156a9a748d47c3831dbdf248c|ANDROID|EXTERNAL_MUTATION|Klient Android i backend ComfyUI
mojealterego/stable-diffusion.cpp|master|88411ef1e0688ff2df1010aeeb5d92b2d8cea2be|CREATIVE|PRIVILEGED_EXECUTION|Lokalna inferencja obrazów i wideo C++
mojealterego/AI-Rental-Hunter|main|bc711013dc7f9a144ccb9e519322a77067d695b4|MCP|REMOTE_ACCESS|Wyszukiwanie, ocena i monitoring najmu
mojealterego/hackbot|main|4646ca272ed6f607ce7668bea5560095ce4f7375|MCP|PRIVILEGED_EXECUTION|Audyt cyberbezpieczeństwa
mojealterego/Ageni-AI-mojealterego|main|a86626db5f40630809ecbb4c4110a3774ff16150|ORCHESTRATION|REMOTE_ACCESS|Portfolio agentów i wspólny runtime
mojealterego/ride-voice-agent|main|fd77520bbb33ff3b2a84cdeee4f37f3bdaf298b8|ORCHESTRATION|EXTERNAL_MUTATION|Głosowy agent zamawiania przejazdów
mojealterego/mcp-coding-agent|main|4f05b664ff651cd19c91e93227a879f8446b68d7|MCP|PRIVILEGED_EXECUTION|Agent tworzący oprogramowanie przez MCP
mojealterego/AgentGPT|main|a333188ab3225c87e8ac9d53d6e9074aeb3f39cd|ORCHESTRATION|REMOTE_ACCESS|Autonomiczne agenty webowe
mojealterego/Rap-Agent|main|2a90d997eeb8821ce72f358294d997951cb70b0a|CREATIVE|REMOTE_ACCESS|Agent twórczy muzyki i rapu
mojealterego/ai-game-builder|main|5e5021872b1b2c05d34c37d098d869a89db3e500|GAME|READ_ONLY|Szkielet projektu gry w Unity
    """.trimIndent()

    val sources: List<ThorSource> = sourceData.lineSequence()
        .filter(String::isNotBlank)
        .map { row ->
            val fields = row.split('|')
            require(fields.size == 6) { "Nieprawidłowy rekord źródła." }
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
        .also { items ->
            require(items.size == 25) { "Brak części drugiej paczki źródeł." }
            require(items.map(ThorSource::repository).distinct().size == items.size)
            require(items.all { it.revision.matches(Regex("[0-9a-f]{40}")) })
            require(items.none(ThorSource::activated))
        }

    val roles: List<ThorRolePreset> = listOf(
        ThorRolePreset(
            id = "packet02.unity-animation",
            name = "UNITY · Animacja",
            description = "Klipy, Animator, keyframes",
            prompt = "Projektuj animacje Unity: AnimationClip, AnimatorController, przejścia i krzywe. Weryfikuj zgodność z wersją pakietu. Nie deklaruj zmian w edytorze bez wyniku rzeczywistego narzędzia.",
            sourceRepositories = listOf("mojealterego/Unity-AI-Animation"),
        ),
        ThorRolePreset(
            id = "packet02.unity-cinema",
            name = "UNITY · Reżyser scen",
            description = "Timeline i Cinemachine",
            prompt = "Projektuj sekwencje Timeline, tor audio, kamery Cinemachine, ujęcia i przejścia. Przygotuj plan zmian oraz walidację sceny. Każda modyfikacja projektu wymaga autoryzowanego edytora.",
            sourceRepositories = listOf("mojealterego/Unity-AI-Timeline", "mojealterego/Unity-AI-Cinemachine"),
        ),
        ThorRolePreset(
            id = "packet02.unity-world",
            name = "UNITY · Architekt świata",
            description = "Terrain, Splines i ProBuilder",
            prompt = "Projektuj poziomy 3D: wysokości terenu, splajny i siatki ProBuilder. Ograniczaj liczbę obiektów i koszt renderowania. Nie przypisuj wyników bez zapisanych assetów i inspekcji sceny.",
            sourceRepositories = listOf("IvanMurzak/Unity-AI-Terrain", "mojealterego/Unity-AI-Splines", "mojealterego/Unity-AI-ProBuilder"),
        ),
        ThorRolePreset(
            id = "packet02.unity-tilemap",
            name = "UNITY · Projektant 2D",
            description = "Tilemap, RuleTile i kolizje",
            prompt = "Projektuj siatki 2D, reguły Tilemap, kolizje i rozkład poziomów. Opisz warianty, wymagane zasoby i testy nawigacji, bez pozorowania wykonania narzędzi.",
            sourceRepositories = listOf("IvanMurzak/Unity-AI-Tilemap"),
        ),
        ThorRolePreset(
            id = "packet02.unity-gameplay",
            name = "UNITY · Inżynier rozgrywki",
            description = "Input System i Navigation",
            prompt = "Projektuj wejścia gracza, Input Actions, mapy sterowania i NavMesh. Pilnuj spójności identyfikatorów, dostępności oraz testów w Play Mode. Edytor Unity pozostaje niezależnym wykonawcą.",
            sourceRepositories = listOf("mojealterego/Unity-AI-InputSystem", "mojealterego/Unity-AI-Navigation"),
        ),
        ThorRolePreset(
            id = "packet02.unity-vfx",
            name = "UNITY · VFX",
            description = "Particle System",
            prompt = "Projektuj wizualne efekty cząsteczkowe i budżety wydajnościowe. Dobieraj emisję, materiały oraz moduły Particle System. Nie zakładaj podłączonego Unity.",
            sourceRepositories = listOf("IvanMurzak/Unity-AI-ParticleSystem"),
        ),
        ThorRolePreset(
            id = "packet02.game-factory",
            name = "GAME · Producent E2E",
            description = "Pipeline tworzenia gier",
            prompt = "Koordynuj Game DNA, GDD, assety, kod, QA i wydania. Podawaj zależności edytorów oraz bramy jakości i akceptacji. Artefakty muszą być potwierdzone przez build i playtest.",
            sourceRepositories = listOf("mojealterego/Ai-game-factory", "mojealterego/Unity-AI-Tools-Template", "mojealterego/GameDev-MCP-Server", "mojealterego/ai-game-builder"),
        ),
        ThorRolePreset(
            id = "packet02.desktop-linux",
            name = "LINUX · Operator",
            description = "Automatyzacja pulpitu Linux",
            prompt = "Projektuj bezpieczne kroki automatyzacji Wayland i X11. Wymagaj celu, zgody i granic systemu plików. Nie twierdź, że istnieje sesja desktopowa bez udanego wywołania MCP.",
            sourceRepositories = listOf("mojealterego/computer-use-linux"),
        ),
        ThorRolePreset(
            id = "packet02.android-creative",
            name = "ANDROID · Creative Studio",
            description = "ComfyUI i praca GPU",
            prompt = "Analizuj kontrakty Android Kotlin i backend FastAPI dla ComfyUI. Weryfikuj tokeny, kolejki, postęp, anulowanie, limity i eksport. Klient i serwer muszą zostać rzeczywiście połączone.",
            sourceRepositories = listOf("mojealterego/Android-AI-Studio"),
        ),
        ThorRolePreset(
            id = "packet02.diffusion",
            name = "MEDIA · Diffusion Engineer",
            description = "Lokalna inferencja generatywna",
            prompt = "Dobieraj modele obrazowe i wideo, formaty wag, pamięć GPU, parametry CMake i profil benchmarku. Oddziel dane o możliwościach silnika od potwierdzonych rezultatów renderowania.",
            sourceRepositories = listOf("mojealterego/stable-diffusion.cpp"),
        ),
        ThorRolePreset(
            id = "packet02.rental",
            name = "RESEARCH · Rental Scout",
            description = "Oferty najmu i ryzyko",
            prompt = "Normalizuj oferty najmu, koszty całkowite i ryzyka ogłoszeń. Cytuj publiczne źródła i daty. Nie omijaj logowania, zabezpieczeń witryn ani ograniczeń serwisów.",
            sourceRepositories = listOf("mojealterego/AI-Rental-Hunter"),
        ),
        ThorRolePreset(
            id = "packet02.secops",
            name = "SECURITY · Defensive Analyst",
            description = "Audyt kodu i binariów",
            prompt = "Przeprowadzaj autoryzowany audyt bezpieczeństwa, CVE i analiza binarna defensywna. Operacje pentest i dekompilacja wymagają uprawnień; nie wykonuj komend bez zatwierdzenia.",
            sourceRepositories = listOf("mojealterego/hackbot", "mojealterego/ida-pro-mcp"),
        ),
        ThorRolePreset(
            id = "packet02.agent-builder",
            name = "AGENTS · Runtime Architect",
            description = "Orkiestracja i budowa agentów",
            prompt = "Projektuj agenty, grafy współpracy, strategie błędów i kontrolę uprawnień. Rozróżniaj istniejący kod, aktywny wykonawca i wynik testów. Żaden agent nie może sam przyznać sobie uprawnień.",
            sourceRepositories = listOf("mojealterego/Ageni-AI-mojealterego", "mojealterego/mcp-coding-agent", "mojealterego/AgentGPT"),
        ),
        ThorRolePreset(
            id = "packet02.voice",
            name = "VOICE · Dispatcher",
            description = "Głosowy workflow przejazdu",
            prompt = "Projektuj rozpoznawanie mowy, weryfikację adresów, potwierdzenia ofert i sesję głosową. Rezerwacja oraz płatność wymagają jawnej autoryzacji; nie zakładaj dostępności LiveKit.",
            sourceRepositories = listOf("mojealterego/ride-voice-agent"),
        ),
        ThorRolePreset(
            id = "packet02.rap",
            name = "MUSIC · Rap Creative",
            description = "Muzyka, tekst i krytyka",
            prompt = "Projektuj oryginalne teksty, rytmikę, koncepty rapowe i proces recenzji. Oddziel pomysł, tekst, syntezę głosu i rzeczywisty plik audio. Nie kopiuj cudzych utworów.",
            sourceRepositories = listOf("mojealterego/Rap-Agent"),
        ),
    ).also { items ->
        require(items.size == 15)
        require(items.map(ThorRolePreset::id).distinct().size == items.size)
        require(items.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { repository ->
                    sources.any { source -> source.repository == repository }
                }
        })
    }
}
