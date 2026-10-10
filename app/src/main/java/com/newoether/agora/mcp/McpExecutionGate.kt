package com.newoether.agora.mcp

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.JsonObject

/**
 * One approval is valid for one invocation only; there is no server-wide trust shortcut.
 * The originating MCP server is not trusted to classify its own tools as read-only.
 */
data class McpApprovalRequest(
    val server: String,
    val tool: String,
    val argumentKeys: List<String>,
) {
    val summary: String = buildString {
        append("MCP: ")
        append(tool)
        append(" (argumenty: ")
        append(argumentKeys.joinToString(", ").ifEmpty { "brak" })
        append("). Zatwierdź to jedno wywołanie; wartości parametrów są ukryte.")
    }
}

fun interface McpApprovalPrompt {
    suspend fun confirm(request: McpApprovalRequest): Boolean
}

internal enum class McpGateDecision {
    ALLOWED,
    DENIED,
    INVALID,
    STALE,
}

/**
 * Fail-closed pre-execution gate. Approval happens before any tools/call RPC is issued.
 * MCP annotations cannot turn off this mandatory check.
 */
internal class McpExecutionGate(
    private val prompt: McpApprovalPrompt?,
) {
    companion object {
        private const val MAX_ARGUMENT_BYTES = 64 * 1024
        private const val MAX_TOOL_NAME_LENGTH = 128
        private const val MAX_ARGUMENT_KEYS = 64
    }

    suspend fun authorize(
        descriptor: McpToolDescriptor,
        arguments: JsonObject,
        isCurrent: () -> Boolean,
    ): McpGateDecision {
        val toolName = descriptor.remote.name
        val serverName = descriptor.serverName
        if (
            !descriptor.enabled ||
            toolName.isBlank() || toolName.length > MAX_TOOL_NAME_LENGTH ||
            serverName.isBlank() || serverName.length > 256 ||
            arguments.toString().toByteArray(Charsets.UTF_8).size > MAX_ARGUMENT_BYTES ||
            arguments.size > MAX_ARGUMENT_KEYS
        ) {
            return McpGateDecision.INVALID
        }

        val activePrompt = prompt ?: return McpGateDecision.DENIED
        if (!isCurrent()) return McpGateDecision.STALE

        // Data values are never surfaced in the confirmation card or audit text.
        val request = McpApprovalRequest(
            server = serverName.replace("\n", " ").replace("\r", " ").take(120),
            tool = toolName.replace("\n", " ").replace("\r", " "),
            argumentKeys = arguments.keys
                .map { key ->
                    key.filter { it.isLetterOrDigit() || it == '_' || it == '-' }.take(48)
                }
                .sorted(),
        )
        val approved = try {
            activePrompt.confirm(request)
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (_: Exception) {
            false
        }
        if (!approved) return McpGateDecision.DENIED
        return if (isCurrent()) McpGateDecision.ALLOWED else McpGateDecision.STALE
    }
}
