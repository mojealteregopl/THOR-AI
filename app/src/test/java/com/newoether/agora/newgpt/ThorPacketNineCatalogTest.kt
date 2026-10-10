package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketNineCatalogTest {
    @Test fun tracksAllTwentyNineGitTreesWithoutAutomaticActivation() {
        val entries = ThorPacketNineCatalog.sources
        assertEquals(29, entries.size)
        assertEquals(29, entries.map { it.repository }.toSet().size)
        assertTrue(entries.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
        assertTrue(entries.all { it.branch.isNotBlank() })
        assertFalse(entries.any { it.activated })
    }

    @Test fun supportsTwentyRestrictedByDesignSpecialistProfiles() {
        val entries = ThorPacketNineCatalog.roles
        val risk = ThorPacketNineCatalog.sources.associate { it.repository to it.risk }
        assertEquals(20, entries.size)
        assertEquals(20, entries.map { it.id }.toSet().size)
        assertTrue(entries.all { it.id.startsWith("packet09.") })
        assertTrue(entries.all { it.prompt.length >= 75 })
        assertTrue(entries.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { source ->
                    risk[source] != null && risk[source] != ThorSourceRisk.RESTRICTED
                }
        })
    }

    @Test fun quarantinesDangerousSoftwareAndAccountVerificationProviders() {
        val risks = ThorPacketNineCatalog.sources.associate { it.repository to it.risk }
        val denied = setOf(
            "mojealterego/hexstrike-ai",
            "mojealterego/OnlineSimBot2",
            "mojealterego/SNK-RAT",
            "mojealterego/TempMailBot",
            "mojealterego/JieMa_2025",
            "mojealterego/L3MON",
            "mojealterego/VoIP-Spoofing-Research",
            "mojealterego/bulk-email-scraper",
        )
        assertEquals(8, denied.size)
        assertTrue(denied.all { risks[it] == ThorSourceRisk.RESTRICTED })
        assertFalse(ThorPacketNineCatalog.roles.any { role ->
            role.sourceRepositories.any(denied::contains)
        })
    }

    @Test fun keepsTheElevenLabsOfficialRepositoryAndMisleadingUnityNameCorrect() {
        val byName = ThorPacketNineCatalog.sources.associateBy { it.repository }
        assertTrue(byName.containsKey("elevenlabs/elevenlabs-n8n"))
        assertFalse(byName.containsKey("mojealterego/elevenlabs-n8n"))
        assertTrue(byName["mojealterego/unity"]!!.capability.contains("ElevenAgents"))
        assertTrue(byName["mojealterego/OpenDevin"]!!.capability.contains("historyczny"))
        assertTrue(byName["mojealterego/Text-To-Video-API"]!!.capability.contains("README"))
        assertTrue(byName["mojealterego/agent-starter-pack"]!!.capability.contains("maintenance"))
    }

    @Test fun aggregatesNinePacketsWithoutOverlappingSourceOrRoleIds() {
        val sources = ThorPacketRegistry.sources
        val roles = ThorPacketRegistry.roles
        assertEquals(256, sources.size)
        assertEquals(256, sources.map { it.repository }.toSet().size)
        assertEquals(160, roles.size)
        assertEquals(160, roles.map { it.id }.toSet().size)
        assertEquals(18, sources.count { it.risk == ThorSourceRisk.RESTRICTED })
    }
}
