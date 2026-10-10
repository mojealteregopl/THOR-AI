package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketTenCatalogTest {
    @Test fun indexesTwentySixVerifiedTreesAndNotAnEmptyRepository() {
        val sources = ThorPacketTenCatalog.sources
        assertEquals(26, sources.size)
        assertEquals(26, sources.map { it.repository }.toSet().size)
        assertTrue(sources.all { it.branch.isNotBlank() })
        assertTrue(sources.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
        assertFalse(sources.any { it.activated })
        assertFalse(sources.any { it.repository == "mojealterego/CzatBotSingularity" })
    }

    @Test fun registersEighteenSafeProfilesWithExplicitDependencies() {
        val roles = ThorPacketTenCatalog.roles
        val byRepository = ThorPacketTenCatalog.sources.associateBy { it.repository }
        assertEquals(18, roles.size)
        assertEquals(18, roles.map { it.id }.toSet().size)
        assertTrue(roles.all { it.id.startsWith("packet10.") })
        assertTrue(roles.all { it.prompt.length >= 75 })
        assertTrue(roles.all { role ->
            role.sourceRepositories.isNotEmpty() && role.sourceRepositories.all { ref ->
                val source = byRepository[ref]
                source != null && source.risk != ThorSourceRisk.RESTRICTED
            }
        })
    }

    @Test fun quarantinesAutonomousPentestSmsBotAndUnverifiedBinary() {
        val byRepository = ThorPacketTenCatalog.sources.associateBy { it.repository }
        val denied = setOf(
            "mojealterego/autopentest-ai",
            "mojealterego/OnlineSimBot",
            "mojealterego/Stable-Diffusion-3.5-Web-UI",
        )
        assertTrue(denied.all { byRepository[it]?.risk == ThorSourceRisk.RESTRICTED })
        assertFalse(ThorPacketTenCatalog.roles.any { role ->
            role.sourceRepositories.any(denied::contains)
        })
    }

    @Test fun disambiguatesProfilesDeprecatedFrameworksAndActualDevices() {
        val byRepository = ThorPacketTenCatalog.sources.associateBy { it.repository }
        assertTrue(byRepository["mojealterego/phisher98"]!!.capability.contains("profil"))
        assertTrue(byRepository["mojealterego/desktop-tutorial"]!!.capability.contains("README"))
        assertTrue(byRepository["mojealterego/autogen"]!!.capability.contains("maintenance"))
        assertTrue(byRepository["mojealterego/open-agent-platform"]!!.capability.contains("deprecated"))
        assertTrue(byRepository["mojealterego/sannabotapp"]!!.capability.contains("Accessibility"))
        assertTrue(byRepository["mojealterego/santamonica"]!!.capability.contains("Fahrenheit"))
        assertTrue(byRepository["mojealterego/visionclaw"]!!.capability.contains("Vision Pro"))
        assertTrue(byRepository.containsKey("fotografaandrzej333/awesome-voip"))
        assertTrue(byRepository.containsKey("NJU-LINK/OmniVideoBench"))
    }

    @Test fun registryTotalsAreUniqueAcrossTenPackets() {
        val sources = ThorPacketRegistry.sources
        val roles = ThorPacketRegistry.roles
        assertEquals(282, sources.size)
        assertEquals(282, sources.map { it.repository }.toSet().size)
        assertEquals(178, roles.size)
        assertEquals(178, roles.map { it.id }.toSet().size)
        assertEquals(21, sources.count { it.risk == ThorSourceRisk.RESTRICTED })
    }
}
