package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketFiveCatalogTest {
    @Test fun has29UniqueNewGitTrees() {
        val sources = ThorPacketFiveCatalog.sources
        assertEquals(29, sources.size)
        assertEquals(29, sources.map { it.repository }.toSet().size)
        assertTrue(sources.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
        assertTrue(sources.all { it.branch.isNotBlank() })
        assertFalse(sources.any { it.activated })
    }

    @Test fun excludesDuplicateGonnectFromFifthPacket() {
        assertFalse(ThorPacketFiveCatalog.sources.any {
            it.repository == "mojealterego/gonnect"
        })
        assertTrue(ThorPacketFourCatalog.sources.any {
            it.repository == "mojealterego/gonnect"
        })
    }

    @Test fun has20ValidRolesWithNoRestrictedDependencies() {
        val roles = ThorPacketFiveCatalog.roles
        val sourceRisk = ThorPacketFiveCatalog.sources.associate { it.repository to it.risk }
        assertEquals(20, roles.size)
        assertEquals(20, roles.map { it.id }.toSet().size)
        assertTrue(roles.all { it.id.startsWith("packet05.") })
        assertTrue(roles.all { it.prompt.length >= 70 })
        assertTrue(roles.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { repository ->
                    sourceRisk[repository] != null &&
                        sourceRisk[repository] != ThorSourceRisk.RESTRICTED
                }
        })
    }

    @Test fun doesNotActivatePhoneVerificationOrUnreviewedNsfwApplication() {
        val sources = ThorPacketFiveCatalog.sources.associateBy { it.repository }
        val restricted = setOf(
            "mojealterego/mcp-server-phone",
            "mojealterego/ai",
        )
        assertTrue(restricted.all { sources[it]?.risk == ThorSourceRisk.RESTRICTED })
        assertTrue(restricted.all { sources[it]?.activated == false })
        assertTrue(ThorPacketFiveCatalog.roles.none { role ->
            role.sourceRepositories.any(restricted::contains)
        })
    }

    @Test fun identifiesAmbiguousRepositoriesByObservedPurpose() {
        val sources = ThorPacketFiveCatalog.sources.associateBy { it.repository }
        assertTrue(sources["mojealterego/agent"]!!.capability.contains("1MCP"))
        assertTrue(sources["mojealterego/skillsOPENai"]!!.capability.contains("przestarza"))
        assertTrue(sources["mojealterego/dcache"]!!.capability.contains("rozproszo"))
        assertTrue(sources["mojealterego/graphify"]!!.capability.contains("graf"))
        assertTrue(sources["mojealterego/godot-orchestrator"]!!.capability.contains("Godot"))
    }

    @Test fun extendsExistingCatalogWithoutChangingOlderPackets() {
        val sources = ThorPacketOneCatalog.sources + ThorPacketTwoCatalog.sources +
            ThorPacketThreeCatalog.sources + ThorPacketFourCatalog.sources +
            ThorPacketFiveCatalog.sources
        val roles = ThorPacketOneCatalog.roles + ThorPacketTwoCatalog.roles +
            ThorPacketThreeCatalog.roles + ThorPacketFourCatalog.roles +
            ThorPacketFiveCatalog.roles
        assertEquals(150, sources.size)
        assertEquals(150, sources.map { it.repository }.toSet().size)
        assertEquals(86, roles.size)
        assertEquals(86, roles.map { it.id }.toSet().size)
    }
}
