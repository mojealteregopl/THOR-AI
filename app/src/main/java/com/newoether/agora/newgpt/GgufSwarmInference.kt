package com.newoether.agora.newgpt

import com.newoether.agora.api.ChatTemplateMessage
import com.newoether.agora.api.LlamaGenerationEvent
import com.newoether.agora.api.LlamaGenerationStopReason
import kotlinx.coroutines.flow.collect

/**
 * Uses the two actually loaded native llama.cpp engines.
 * Tool calls are deliberately refused: tool names in StudioAgent are not permissions.
 */
class GgufSwarmInference(
    private val runtime: DualGgufRuntime,
) : SwarmInference {
    override suspend fun generate(
        modelSlot: Int,
        systemPrompt: String,
        userPrompt: String,
    ): String {
        require(modelSlot in 0..1) { "Unknown GGUF model slot" }
        val engine = runtime.engine(modelSlot)
            ?: error("GGUF model ${modelSlot + 1} is not loaded")
        check(engine.isLoaded()) { "GGUF model ${modelSlot + 1} is not ready" }

        val template = engine.applyTemplate(
            messages = listOf(
                ChatTemplateMessage(role = "system", content = systemPrompt),
                ChatTemplateMessage(role = "user", content = userPrompt),
            ),
            tools = emptyList(),
            addAss = true,
            enableThinking = false,
        ) ?: error("Cannot render GGUF chat template")

        val output = StringBuilder()
        var reason: LlamaGenerationStopReason? = null
        var toolCallObserved = false

        engine.generate(template, maxTokens = 1024).collect { event ->
            when (event) {
                is LlamaGenerationEvent.Text -> {
                    check(output.length + event.value.length <= 12_000) {
                        "Agent output exceeds limit"
                    }
                    output.append(event.value)
                }
                is LlamaGenerationEvent.ToolCallUpdate,
                is LlamaGenerationEvent.ToolCallsCompleted -> toolCallObserved = true
                is LlamaGenerationEvent.Completed -> reason = event.reason
                is LlamaGenerationEvent.Failed -> error("GGUF inference failed: ${event.message}")
                is LlamaGenerationEvent.Thought -> Unit
            }
        }

        check(!toolCallObserved) { "Tool calls are disabled for GGUF swarm execution" }
        check(reason == LlamaGenerationStopReason.EOG) {
            "GGUF generation did not finish normally: ${reason ?: "no terminal event"}"
        }
        return output.toString()
    }
}
