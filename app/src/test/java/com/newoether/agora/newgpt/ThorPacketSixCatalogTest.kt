package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketSixCatalogTest {
    @Test fun hasNineteenSourcesWithVerifiedTreeShaFormat() {
        val sources = ThorPacketSixCatalog.sources
        assertEquals(19, sources.size)
        assertEquals(19, sources.map { it.repository }.toSet().size)
        assertTrue(sources.all { it.revision.matches(Regex("[a-f0-9]{40}")) })
        assertTrue(sources.all { it.branch.isNotBlank() })
        assertFalse(sources.any { it.activated })
    }

    @Test fun keepsOnlyOneCopyOfRepeatedRailwaySkills() {
        val railway = ThorPacketSixCatalog.sources.filter {
            it.repository == "mojealterego/railway-skills"
        }
        assertEquals(1, railway.size)
    }

    @Test fun hasFourteenSpecialistsWithDeclaredSafeReferences() {
        val sources = ThorPacketSixCatalog.sources.associateBy { it.repository }
        val roles = ThorPacketSixCatalog.roles
        assertEquals(14, roles.size)
        assertEquals(14, roles.map { it.id }.toSet().size)
        assertTrue(roles.all { it.id.startsWith("packet06.") })
        assertTrue(roles.all { it.prompt.length >= 75 })
        assertTrue(roles.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { ref ->
                    sources[ref] != null && sources[ref]?.risk != ThorSourceRisk.RESTRICTED
                }
        })
    }

    @Test fun accuratelyDisambiguatesGitHubSources() {
        val sources = ThorPacketSixCatalog.sources.associateBy { it.repository }
        assertTrue(sources["mojealterego/docs"]!!.capability.contains("Future AGI"))
        assertTrue(sources["mojealterego/cli"]!!.capability.contains("ElevenLabs"))
        assertTrue(sources["mojealterego/skills"]!!.capability.contains("ElevenLabs"))
        assertTrue(sources["mojealterego/Stable-Diffusion"]!!.capability.contains("poradnik"))
        assertTrue(sources["mojealterego/TorBot"]!!.capability.contains("Tor"))
    }

    @Test fun neverTreatsExternalReconOrVoiceAsAlreadyRunning() {
        val names = setOf(
            "mojealterego/Shadowbroker",
            "mojealterego/TorBot",
            "mojealterego/cli",
            "mojealterego/elevenlabs-js",
            "mojealterego/elevenlabs-swift-sdk",
        )
        assertTrue(ThorPacketSixCatalog.sources.filter { it.repository in names }.all {
            it.activated == false
        })
        assertTrue(ThorPacketSixCatalog.sources.single {
            it.repository == "mojealterego/TorBot"
        }.risk == ThorSourceRisk.PRIVILEGED_EXECUTION)
    }

    @Test fun fifthAndSixthPacketsDoNotOverlapOrDuplicateExistingCatalog() {
        val sourceList = ThorPacketOneCatalog.sources + ThorPacketTwoCatalog.sources +
            ThorPacketThreeCatalog.sources + ThorPacketFourCatalog.sources +
            ThorPacketFiveCatalog.sources + ThorPacketSixCatalog.sources
        val roles = ThorPacketOneCatalog.roles + ThorPacketTwoCatalog.roles +
            ThorPacketThreeCatalog.roles + ThorPacketFourCatalog.roles +
            ThorPacketFiveCatalog.roles + ThorPacketSixCatalog.roles
        assertEquals(169, sourceList.size)
        assertEquals(169, sourceList.map { it.repository }.toSet().size)
        assertEquals(100, roles.size)
        assertEquals(100, roles.map { it.id }.toSet().size)
    }
}
