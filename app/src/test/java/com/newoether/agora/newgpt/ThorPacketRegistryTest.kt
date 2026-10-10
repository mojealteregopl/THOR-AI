package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketRegistryTest {
    @Test fun mapsAllNinePacketsToDeterministicAndroidTabIndices() {
        assertEquals(listOf("01", "02", "03", "04", "05", "06", "07", "08", "09"), ThorPacketRegistry.packets.map { it.number })
        assertEquals("01", ThorPacketRegistry.packetByTabIndex(5)?.number)
        assertEquals("02", ThorPacketRegistry.packetByTabIndex(6)?.number)
        assertEquals("03", ThorPacketRegistry.packetByTabIndex(7)?.number)
        assertEquals("04", ThorPacketRegistry.packetByTabIndex(8)?.number)
        assertEquals("05", ThorPacketRegistry.packetByTabIndex(9)?.number)
        assertEquals("06", ThorPacketRegistry.packetByTabIndex(10)?.number)
        assertEquals("07", ThorPacketRegistry.packetByTabIndex(11)?.number)
        assertEquals("08", ThorPacketRegistry.packetByTabIndex(12)?.number)
        assertEquals("09", ThorPacketRegistry.packetByTabIndex(13)?.number)
        assertNull(ThorPacketRegistry.packetByTabIndex(4))
        assertNull(ThorPacketRegistry.packetByTabIndex(14))
        assertNull(ThorPacketRegistry.packetByTabIndex(-1))
    }

    @Test fun exposesNonoverlappingSourceAndAgentIds() {
        assertEquals(256, ThorPacketRegistry.sources.size)
        assertEquals(160, ThorPacketRegistry.roles.size)
        assertEquals(256, ThorPacketRegistry.sources.map { it.repository }.toSet().size)
        assertEquals(160, ThorPacketRegistry.roles.map { it.id }.toSet().size)
    }

    @Test fun allSourcesAreInactiveAndRestrictedSourcesHaveNoAgentAccess() {
        val risks = ThorPacketRegistry.sources.associate { it.repository to it.risk }
        assertFalse(ThorPacketRegistry.sources.any { it.activated })
        val restricted = risks.filterValues { it == ThorSourceRisk.RESTRICTED }.keys
        assertEquals(18, restricted.size)
        assertTrue(restricted.contains("mojealterego/pegasus_spyware"))
        assertTrue(restricted.contains("mojealterego/L3MON-1"))
        assertTrue(restricted.contains("mojealterego/chappie-bot"))
        assertTrue(restricted.contains("mojealterego/mailtm-client"))
        assertTrue(restricted.contains("mojealterego/mcp-server"))
        assertTrue(restricted.contains("mojealterego/mcp-server-phone"))
        assertTrue(restricted.contains("mojealterego/ai"))
        assertTrue(restricted.contains("mojealterego/Decepticon"))
        assertTrue(restricted.contains("mojealterego/sugar-mcp"))
        assertTrue(restricted.contains("mojealterego/skills-phone"))
        assertTrue(restricted.contains("mojealterego/hexstrike-ai"))
        assertTrue(restricted.contains("mojealterego/OnlineSimBot2"))
        assertTrue(restricted.contains("mojealterego/SNK-RAT"))
        assertTrue(restricted.contains("mojealterego/TempMailBot"))
        assertTrue(restricted.contains("mojealterego/JieMa_2025"))
        assertTrue(restricted.contains("mojealterego/L3MON"))
        assertTrue(restricted.contains("mojealterego/VoIP-Spoofing-Research"))
        assertTrue(restricted.contains("mojealterego/bulk-email-scraper"))
        assertFalse(ThorPacketRegistry.roles.any { role ->
            role.sourceRepositories.any(restricted::contains)
        })
    }
}
