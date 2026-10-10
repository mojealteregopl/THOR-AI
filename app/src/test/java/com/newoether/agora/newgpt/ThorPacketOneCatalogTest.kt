package com.newoether.agora.newgpt

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThorPacketOneCatalogTest {
    @Test fun registersExactly23UniqueRepositories() {
        val sources = ThorPacketOneCatalog.sources
        assertEquals(23, sources.size)
        assertEquals(23, sources.map { it.repository }.toSet().size)
        assertTrue(sources.any { it.repository == "firecrawl/firecrawl-mcp-server" })
        assertTrue(sources.any { it.repository == "virgiliojr94/book-to-skill" })
        assertTrue(sources.any { it.repository == "mojealterego/Codex-Android" })
    }

    @Test fun sourcesArePinnedAndNeverPretendToBeConnected() {
        val sources = ThorPacketOneCatalog.sources
        assertTrue(sources.all { it.branch.isNotBlank() && it.revision.matches(Regex("[0-9a-f]{40}")) })
        assertTrue(sources.all { it.repository.matches(Regex("[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+")) })
        assertFalse(sources.any { it.activated })
        assertEquals(
            ThorSourceRisk.PRIVILEGED_EXECUTION,
            sources.single { it.repository == "mojealterego/DesktopCommanderMCP" }.risk,
        )
    }

    @Test fun offersNineDistinctProfilesWithExistingSources() {
        val roles = ThorPacketOneCatalog.roles
        val sources = ThorPacketOneCatalog.sources.map { it.repository }.toSet()
        assertEquals(9, roles.size)
        assertEquals(9, roles.map { it.id }.toSet().size)
        assertTrue(roles.all { it.id.startsWith("packet01.") })
        assertTrue(roles.all { it.prompt.length in 40..1600 })
        assertTrue(roles.all { it.sourceRepositories.isNotEmpty() &&
            it.sourceRepositories.all(sources::contains) })
    }

    @Test fun catalogContainsNoImplicitExecutableToolGrant() {
        assertTrue(ThorPacketOneCatalog.roles.all { role ->
            role.prompt.isNotBlank() && role.sourceRepositories.isNotEmpty()
        })
        assertTrue(ThorPacketOneCatalog.sources.none { it.activated })
    }
}
