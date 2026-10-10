package com.newoether.agora.mcp

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class McpExecutionGateTest {
    private fun descriptor() = McpToolDescriptor(
        publicName = "mcp_server_send_abc",
        serverId = "server-id",
        serverName = "Laboratory MCP",
        remote = McpRemoteTool(
            name = "send_message",
            description = "Submit a request",
            inputSchema = buildJsonObject { put("type", "object") },
        ),
    )

    @Test fun noConfiguredApprovalDeniesEvenReadOnlyTools() = runBlocking {
        val result = McpExecutionGate(null).authorize(
            descriptor(), buildJsonObject {}, { true },
        )
        assertEquals(McpGateDecision.DENIED, result)
    }

    @Test fun explicitDenialNeverAuthorizesTool() = runBlocking {
        var calls = 0
        val gate = McpExecutionGate(McpApprovalPrompt {
            calls++
            false
        })
        assertEquals(
            McpGateDecision.DENIED,
            gate.authorize(descriptor(), buildJsonObject {}, { true }),
        )
        assertEquals(1, calls)
    }

    @Test fun requiresFreshRegistrationAfterUserApproval() = runBlocking {
        var active = true
        val gate = McpExecutionGate(McpApprovalPrompt {
            active = false
            true
        })
        assertEquals(
            McpGateDecision.STALE,
            gate.authorize(descriptor(), buildJsonObject {}, { active }),
        )
    }

    @Test fun approvedActiveToolMayProceed() = runBlocking {
        val gate = McpExecutionGate(McpApprovalPrompt { true })
        assertEquals(
            McpGateDecision.ALLOWED,
            gate.authorize(descriptor(), buildJsonObject {}, { true }),
        )
    }

    @Test fun promptDoesNotRevealArgumentValuesAndIsSingleActionScoped() = runBlocking {
        var request: McpApprovalRequest? = null
        val gate = McpExecutionGate(McpApprovalPrompt { seen ->
            request = seen
            true
        })
        val payload = buildJsonObject {
            put("token", "sensitive-secret-123")
            put("recipient", "private@example.org")
        }
        assertEquals(McpGateDecision.ALLOWED, gate.authorize(descriptor(), payload, { true }))
        val actual = checkNotNull(request)
        assertTrue(actual.server == "Laboratory MCP")
        assertTrue(actual.tool == "send_message")
        assertEquals(listOf("recipient", "token"), actual.argumentKeys)
        assertFalse(actual.summary.contains("sensitive-secret-123"))
        assertFalse(actual.summary.contains("private@example.org"))
    }

    @Test fun oversizedPayloadIsDeniedWithoutPrompting() = runBlocking {
        var prompted = false
        val gate = McpExecutionGate(McpApprovalPrompt { prompted = true; true })
        val huge = buildJsonObject { put("data", "a".repeat(65_537)) }
        assertEquals(McpGateDecision.INVALID, gate.authorize(descriptor(), huge, { true }))
        assertFalse(prompted)
    }

    @Test fun approvalFailureIsDeniedButCancellationPropagates() = runBlocking {
        val failing = McpExecutionGate(McpApprovalPrompt { throw IllegalStateException("offline") })
        assertEquals(
            McpGateDecision.DENIED,
            failing.authorize(descriptor(), buildJsonObject {}, { true }),
        )
        val cancelling = McpExecutionGate(McpApprovalPrompt { throw CancellationException("cancel") })
        var observedCancellation = false
        try {
            cancelling.authorize(descriptor(), buildJsonObject {}, { true })
        } catch (_: CancellationException) {
            observedCancellation = true
        }
        assertTrue(observedCancellation)
    }
}
