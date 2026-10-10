package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketFourCatalogTest {
    @Test fun registers33UniqueVerifiedGitTrees() {
        val sources = ThorPacketFourCatalog.sources
        assertEquals(33, sources.size)
        assertEquals(33, sources.map { it.repository }.toSet().size)
        assertTrue(sources.all { it.branch.isNotBlank() })
        assertTrue(sources.all { it.revision.matches(Regex("[0-9a-f]{40}")) })
        assertFalse(sources.any { it.activated })
    }

    @Test fun offers18BoundedProfilesWithSafeReferences() {
        val roles = ThorPacketFourCatalog.roles
        val sources = ThorPacketFourCatalog.sources.associateBy { it.repository }
        assertEquals(18, roles.size)
        assertEquals(18, roles.map { it.id }.toSet().size)
        assertTrue(roles.all { it.id.startsWith("packet04.") })
        assertTrue(roles.all { role ->
            role.prompt.length >= 70 &&
                role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { ref ->
                    sources[ref] != null && sources[ref]?.risk != ThorSourceRisk.RESTRICTED
                }
        })
    }

    @Test fun excludesAndroidRatAndVerificationBrokerFromAgentProfiles() {
        val locked = setOf(
            "mojealterego/L3MON-1",
            "mojealterego/mcp-server",
        )
        val sources = ThorPacketFourCatalog.sources.associateBy { it.repository }
        assertTrue(locked.all { sources[it]?.risk == ThorSourceRisk.RESTRICTED })
        assertTrue(locked.all { sources[it]?.activated == false })
        assertTrue(ThorPacketFourCatalog.roles.none { role ->
            role.sourceRepositories.any(locked::contains)
        })
    }

    @Test fun avoidsAutowiringTemporaryMailAndLegacyChatBot() {
        val excluded = setOf(
            "mojealterego/mailtm-client",
            "mojealterego/chappie-bot",
        )
        val sources = ThorPacketFourCatalog.sources.associateBy { it.repository }
        assertTrue(excluded.all { sources[it]?.risk == ThorSourceRisk.RESTRICTED })
        assertTrue(excluded.all { sources[it]?.activated == false })
        assertTrue(ThorPacketFourCatalog.roles.none { role ->
            role.sourceRepositories.any(excluded::contains)
        })
    }

    @Test fun resolvesAmbiguousProjectNamesCorrectly() {
        val sources = ThorPacketFourCatalog.sources.associateBy { it.repository }
        assertTrue(sources["mojealterego/lore"]!!.capability.contains("wersjonowanie"))
        assertTrue(sources["mojealterego/docs2"]!!.capability.contains("Railway"))
        assertTrue(sources["mojealterego/phidata"]!!.capability.contains("Agno"))
        assertTrue(sources["mojealterego/brave-browser"]!!.capability.contains("Issues"))
    }

    @Test fun catalogIsAdditiveAcrossFourPackets() {
        val all = ThorPacketOneCatalog.sources + ThorPacketTwoCatalog.sources +
            ThorPacketThreeCatalog.sources + ThorPacketFourCatalog.sources
        val roles = ThorPacketOneCatalog.roles + ThorPacketTwoCatalog.roles +
            ThorPacketThreeCatalog.roles + ThorPacketFourCatalog.roles
        assertEquals(121, all.size)
        assertEquals(121, all.map { it.repository }.toSet().size)
        assertEquals(66, roles.size)
        assertEquals(66, roles.map { it.id }.toSet().size)
    }
}
