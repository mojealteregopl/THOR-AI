package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketRegistryTest {
    @Test fun mapsAllFourPacketsToDeterministicAndroidTabIndices() {
        assertEquals(listOf("01", "02", "03", "04"), ThorPacketRegistry.packets.map { it.number })
        assertEquals("01", ThorPacketRegistry.packetByTabIndex(5)?.number)
        assertEquals("02", ThorPacketRegistry.packetByTabIndex(6)?.number)
        assertEquals("03", ThorPacketRegistry.packetByTabIndex(7)?.number)
        assertEquals("04", ThorPacketRegistry.packetByTabIndex(8)?.number)
        assertNull(ThorPacketRegistry.packetByTabIndex(4))
        assertNull(ThorPacketRegistry.packetByTabIndex(9))
        assertNull(ThorPacketRegistry.packetByTabIndex(-1))
    }

    @Test fun exposesNonoverlappingSourceAndAgentIds() {
        assertEquals(121, ThorPacketRegistry.sources.size)
        assertEquals(66, ThorPacketRegistry.roles.size)
        assertEquals(121, ThorPacketRegistry.sources.map { it.repository }.toSet().size)
        assertEquals(66, ThorPacketRegistry.roles.map { it.id }.toSet().size)
    }

    @Test fun allSourcesAreInactiveAndRestrictedSourcesHaveNoAgentAccess() {
        val risks = ThorPacketRegistry.sources.associate { it.repository to it.risk }
        assertFalse(ThorPacketRegistry.sources.any { it.activated })
        val restricted = risks.filterValues { it == ThorSourceRisk.RESTRICTED }.keys
        assertEquals(5, restricted.size)
        assertTrue(restricted.contains("mojealterego/pegasus_spyware"))
        assertTrue(restricted.contains("mojealterego/L3MON-1"))
        assertTrue(restricted.contains("mojealterego/chappie-bot"))
        assertTrue(restricted.contains("mojealterego/mailtm-client"))
        assertTrue(restricted.contains("mojealterego/mcp-server"))
        assertFalse(ThorPacketRegistry.roles.any { role ->
            role.sourceRepositories.any(restricted::contains)
        })
    }
}
