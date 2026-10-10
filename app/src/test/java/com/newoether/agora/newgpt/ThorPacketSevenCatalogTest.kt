package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketSevenCatalogTest {
    @Test fun registersThirtyDistinctGitTreeRevisions() {
        val sources = ThorPacketSevenCatalog.sources
        assertEquals(30, sources.size)
        assertEquals(30, sources.map { it.repository }.toSet().size)
        assertTrue(sources.all { it.branch.isNotBlank() })
        assertTrue(sources.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
        assertFalse(sources.any { it.activated })
    }

    @Test fun offersEighteenBoundedSpecialistProfiles() {
        val sourceRisks = ThorPacketSevenCatalog.sources.associate { it.repository to it.risk }
        val roles = ThorPacketSevenCatalog.roles
        assertEquals(18, roles.size)
        assertEquals(18, roles.map { it.id }.toSet().size)
        assertTrue(roles.all { it.id.startsWith("packet07.") })
        assertTrue(roles.all { it.prompt.length >= 75 })
        assertTrue(roles.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { repo ->
                    sourceRisks[repo] != null && sourceRisks[repo] != ThorSourceRisk.RESTRICTED
                }
        })
    }

    @Test fun restrictsAutonomousAttackAndFinancialTransactionSources() {
        val sources = ThorPacketSevenCatalog.sources.associateBy { it.repository }
        val restricted = setOf(
            "mojealterego/Decepticon",
            "mojealterego/sugar-mcp",
        )
        assertTrue(restricted.all { sources[it]?.risk == ThorSourceRisk.RESTRICTED })
        assertFalse(ThorPacketSevenCatalog.roles.any { role ->
            role.sourceRepositories.any(restricted::contains)
        })
    }

    @Test fun distinguishesConfusingRepositoryNamesAndDeprecatedServers() {
        val sources = ThorPacketSevenCatalog.sources.associateBy { it.repository }
        assertTrue(sources["mojealterego/sugar"]!!.capability.contains("pamięć"))
        assertTrue(sources["mojealterego/sugar-mcp"]!!.capability.contains("swap"))
        assertTrue(sources["mojealterego/Context-Engine"]!!.capability.contains("gry"))
        assertTrue(sources["mojealterego/ui"]!!.capability.contains("ElevenLabs"))
        assertTrue(sources["mojealterego/krypton-byte"]!!.capability.contains("profil"))
        assertTrue(sources["mojealterego/elevenlabs-mcp"]!!.capability.contains("przestarzały"))
        assertTrue(sources["mojealterego/elevenlabs-mcp-player"]!!.capability.contains("przestarzały"))
    }

    @Test fun expandsTheCompleteRegistryWithoutDuplicateRepos() {
        val allSources = ThorPacketOneCatalog.sources + ThorPacketTwoCatalog.sources +
            ThorPacketThreeCatalog.sources + ThorPacketFourCatalog.sources +
            ThorPacketFiveCatalog.sources + ThorPacketSixCatalog.sources +
            ThorPacketSevenCatalog.sources
        val allRoles = ThorPacketOneCatalog.roles + ThorPacketTwoCatalog.roles +
            ThorPacketThreeCatalog.roles + ThorPacketFourCatalog.roles +
            ThorPacketFiveCatalog.roles + ThorPacketSixCatalog.roles +
            ThorPacketSevenCatalog.roles
        assertEquals(199, allSources.size)
        assertEquals(199, allSources.map { it.repository }.toSet().size)
        assertEquals(118, allRoles.size)
        assertEquals(118, allRoles.map { it.id }.toSet().size)
    }
}
