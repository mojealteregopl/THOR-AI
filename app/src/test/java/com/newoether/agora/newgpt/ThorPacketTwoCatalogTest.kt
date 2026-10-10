package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketTwoCatalogTest {
    @Test
    fun registers25UniquePinnedGitTreesWithoutActivation() {
        val sources = ThorPacketTwoCatalog.sources
        assertEquals(25, sources.size)
        assertEquals(25, sources.map { it.repository }.toSet().size)
        assertTrue(sources.all { it.branch.isNotBlank() })
        assertTrue(sources.all { it.revision.matches(Regex("[0-9a-f]{40}")) })
        assertFalse(sources.any { it.activated })
    }

    @Test
    fun registers15UniqueRoleProfilesWithKnownSources() {
        val roles = ThorPacketTwoCatalog.roles
        val ids = roles.map { it.id }
        val sourceNames = ThorPacketTwoCatalog.sources.map { it.repository }.toSet()
        assertEquals(15, roles.size)
        assertEquals(15, ids.toSet().size)
        assertTrue(ids.all { it.startsWith("packet02.") })
        assertTrue(roles.all { role ->
            role.prompt.length >= 60 &&
                role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all(sourceNames::contains)
        })
    }

    @Test
    fun includesAll11UnityExtensionsAndTemplate() {
        val unity = ThorPacketTwoCatalog.sources.filter {
            it.repository.substringAfter('/').startsWith("Unity-AI-")
        }
        assertEquals(11, unity.size)
        assertTrue(unity.any { it.repository == "IvanMurzak/Unity-AI-Terrain" })
        assertTrue(unity.any { it.repository == "IvanMurzak/Unity-AI-ParticleSystem" })
        assertEquals(
            ThorSourceRisk.READ_ONLY,
            unity.single { it.repository.endsWith("/Unity-AI-Tools-Template") }.risk,
        )
    }

    @Test
    fun protectsPrivilegedDesktopAndSecurityTools() {
        val protected = listOf(
            "mojealterego/computer-use-linux",
            "mojealterego/GameDev-MCP-Server",
            "mojealterego/ida-pro-mcp",
            "mojealterego/hackbot",
            "mojealterego/mcp-coding-agent",
        )
        val sources = ThorPacketTwoCatalog.sources.associateBy { it.repository }
        assertTrue(protected.all { sources[it]?.risk == ThorSourceRisk.PRIVILEGED_EXECUTION })
        assertTrue(protected.all { sources[it]?.activated == false })
    }

    @Test
    fun packetTwoDoesNotReplaceExistingPacketOneRegistrations() {
        val previous = ThorPacketOneCatalog.sources
        val current = ThorPacketTwoCatalog.sources
        val previousRoles = ThorPacketOneCatalog.roles
        val currentRoles = ThorPacketTwoCatalog.roles
        assertEquals(48, (previous + current).map { it.repository }.toSet().size)
        assertEquals(24, (previousRoles + currentRoles).map { it.id }.toSet().size)
    }
}
