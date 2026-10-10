package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketRegistryTest {
    @Test fun mapsAllSevenPacketsToDeterministicAndroidTabIndices() {
        assertEquals(listOf("01", "02", "03", "04", "05", "06", "07"), ThorPacketRegistry.packets.map { it.number })
        assertEquals("01", ThorPacketRegistry.packetByTabIndex(5)?.number)
        assertEquals("02", ThorPacketRegistry.packetByTabIndex(6)?.number)
        assertEquals("03", ThorPacketRegistry.packetByTabIndex(7)?.number)
        assertEquals("04", ThorPacketRegistry.packetByTabIndex(8)?.number)
        assertEquals("05", ThorPacketRegistry.packetByTabIndex(9)?.number)
        assertEquals("06", ThorPacketRegistry.packetByTabIndex(10)?.number)
        assertEquals("07", ThorPacketRegistry.packetByTabIndex(11)?.number)
        assertNull(ThorPacketRegistry.packetByTabIndex(4))
        assertNull(ThorPacketRegistry.packetByTabIndex(12))
        assertNull(ThorPacketRegistry.packetByTabIndex(-1))
    }

    @Test fun exposesNonoverlappingSourceAndAgentIds() {
        assertEquals(199, ThorPacketRegistry.sources.size)
        assertEquals(118, ThorPacketRegistry.roles.size)
        assertEquals(199, ThorPacketRegistry.sources.map { it.repository }.toSet().size)
        assertEquals(118, ThorPacketRegistry.roles.map { it.id }.toSet().size)
    }

    @Test fun allSourcesAreInactiveAndRestrictedSourcesHaveNoAgentAccess() {
        val risks = ThorPacketRegistry.sources.associate { it.repository to it.risk }
        assertFalse(ThorPacketRegistry.sources.any { it.activated })
        val restricted = risks.filterValues { it == ThorSourceRisk.RESTRICTED }.keys
        assertEquals(9, restricted.size)
        assertTrue(restricted.contains("mojealterego/pegasus_spyware"))
        assertTrue(restricted.contains("mojealterego/L3MON-1"))
        assertTrue(restricted.contains("mojealterego/chappie-bot"))
        assertTrue(restricted.contains("mojealterego/mailtm-client"))
        assertTrue(restricted.contains("mojealterego/mcp-server"))
        assertTrue(restricted.contains("mojealterego/mcp-server-phone"))
        assertTrue(restricted.contains("mojealterego/ai"))
        assertTrue(restricted.contains("mojealterego/Decepticon"))
        assertTrue(restricted.contains("mojealterego/sugar-mcp"))
        assertFalse(ThorPacketRegistry.roles.any { role ->
            role.sourceRepositories.any(restricted::contains)
        })
    }
}
