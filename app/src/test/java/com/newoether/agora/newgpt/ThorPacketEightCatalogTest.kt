package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketEightCatalogTest {
    @Test fun registersTwentyEightVerifiedGitTrees() {
        val sources = ThorPacketEightCatalog.sources
        assertEquals(28, sources.size)
        assertEquals(28, sources.map { it.repository }.toSet().size)
        assertTrue(sources.all { it.branch.isNotBlank() })
        assertTrue(sources.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
        assertFalse(sources.any { it.activated })
    }

    @Test fun offersTwentyTwoProfilesWithOnlyRegisteredDependencies() {
        val risks = ThorPacketEightCatalog.sources.associate { it.repository to it.risk }
        val roles = ThorPacketEightCatalog.roles
        assertEquals(22, roles.size)
        assertEquals(22, roles.map { it.id }.toSet().size)
        assertTrue(roles.all { it.id.startsWith("packet08.") })
        assertTrue(roles.all { it.prompt.length >= 75 })
        assertTrue(roles.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { repo ->
                    risks[repo] != null && risks[repo] != ThorSourceRisk.RESTRICTED
                }
        })
    }

    @Test fun restrictsSmsAccountVerificationSkillsButNotSaaSPegasusSkills() {
        val sources = ThorPacketEightCatalog.sources.associateBy { it.repository }
        assertEquals(
            ThorSourceRisk.RESTRICTED,
            sources["mojealterego/skills-phone"]?.risk,
        )
        assertEquals(
            ThorSourceRisk.READ_ONLY,
            sources["mojealterego/pegasus-skills"]?.risk,
        )
        assertFalse(ThorPacketEightCatalog.roles.any {
            it.sourceRepositories.contains("mojealterego/skills-phone")
        })
        assertTrue(ThorPacketEightCatalog.roles.any {
            it.sourceRepositories.contains("mojealterego/pegasus-skills")
        })
    }

    @Test fun distinguishesNonExecutingTemplatesFromConnectedGameEditors() {
        val sources = ThorPacketEightCatalog.sources.associateBy { it.repository }
        assertTrue(
            sources["mojealterego/Unreal-AI-Niagara"]!!.capability.contains("szablon")
        )
        assertTrue(
            sources["mojealterego/Godot-AI-Tools-Template"]!!.capability.contains("szablon")
        )
        assertTrue(sources["mojealterego/MumbleLink"]!!.capability.contains("Minecraft"))
        assertTrue(sources["mojealterego/PhoneClaw"]!!.capability.contains("iOS"))
        assertTrue(sources["mojealterego/sanna"]!!.capability.contains("przed"))
    }

    @Test fun integratesWithAllPreviousPacketsWithoutOverlaps() {
        val sources = ThorPacketRegistry.sources
        val roles = ThorPacketRegistry.roles
        assertEquals(227, sources.size)
        assertEquals(227, sources.map { it.repository }.toSet().size)
        assertEquals(140, roles.size)
        assertEquals(140, roles.map { it.id }.toSet().size)
        assertEquals(10, sources.count { it.risk == ThorSourceRisk.RESTRICTED })
    }
}
