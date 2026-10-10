package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketThreeCatalogTest {
    @Test fun containsFortyDistinctVerifiedSourceTrees() {
        val sources = ThorPacketThreeCatalog.sources
        assertEquals(40, sources.size)
        assertEquals(sources.size, sources.map { it.repository }.toSet().size)
        assertTrue(sources.all { it.branch.isNotBlank() })
        assertTrue(sources.all { it.revision.matches(Regex("[0-9a-f]{40}")) })
    }

    @Test fun offersTwentyFourDistinctProfilesWithKnownSafeReferences() {
        val roles = ThorPacketThreeCatalog.roles
        val sources = ThorPacketThreeCatalog.sources.associateBy { it.repository }
        assertEquals(24, roles.size)
        assertEquals(24, roles.map { it.id }.toSet().size)
        assertTrue(roles.all { it.id.startsWith("packet03.") })
        assertTrue(roles.all { it.prompt.length in 75..2500 })
        assertTrue(roles.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all {
                    sources[it] != null && sources[it]?.risk != ThorSourceRisk.RESTRICTED
                }
        })
    }

    @Test fun pegasusIsRestrictedAndUnavailableToAgents() {
        val source = ThorPacketThreeCatalog.sources.single {
            it.repository == "mojealterego/pegasus_spyware"
        }
        assertEquals(ThorSourceRisk.RESTRICTED, source.risk)
        assertFalse(source.activated)
        assertTrue(ThorPacketThreeCatalog.roles.none {
            it.sourceRepositories.contains(source.repository)
        })
    }

    @Test fun flagsPrivilegedExecutionSourcesAsInactive() {
        val names = listOf(
            "mojealterego/AllHackingTools",
            "mojealterego/termux-app",
            "mojealterego/OpenHands",
            "mojealterego/stable-diffusion-webui",
        )
        val sources = ThorPacketThreeCatalog.sources.associateBy { it.repository }
        assertTrue(names.all { sources[it]?.risk == ThorSourceRisk.PRIVILEGED_EXECUTION })
        assertTrue(names.all { sources[it]?.activated == false })
        assertTrue(ThorPacketThreeCatalog.sources.none { it.activated })
    }

    @Test fun allThreePacksRemainUniqueAndMutuallyNonoverlapping() {
        val allSources = ThorPacketOneCatalog.sources +
            ThorPacketTwoCatalog.sources +
            ThorPacketThreeCatalog.sources
        val allRoles = ThorPacketOneCatalog.roles +
            ThorPacketTwoCatalog.roles +
            ThorPacketThreeCatalog.roles

        assertEquals(88, allSources.size)
        assertEquals(allSources.size, allSources.map { it.repository }.toSet().size)
        assertEquals(48, allRoles.size)
        assertEquals(allRoles.size, allRoles.map { it.id }.toSet().size)
    }

    @Test fun resolvesAmbiguousTwineAsFeedReaderInsteadOfStoryTool() {
        val source = ThorPacketThreeCatalog.sources.single {
            it.repository == "mojealterego/twine"
        }
        assertTrue(source.capability.contains("RSS"))
    }
}
