package com.newoether.agora.newgpt

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class StudioSwarmRuntimeTest {
    private val lead = SwarmAgent("lead", "Koordynator", "Planuj.", listOf("qa", "security"))
    private val qa = SwarmAgent("qa", "QA", "Testuj.", emptyList())
    private val security = SwarmAgent("security", "Security", "Weryfikuj.", emptyList())

    @Test fun compilesBreadthFirstGraphWithDeterministicModelSlots() {
        val steps = SwarmPlanCompiler().compile(listOf(lead, qa, security), "lead")
        assertEquals(listOf("lead", "qa", "security"), steps.map { it.agent.id })
        assertEquals(listOf(0, 1, 0), steps.map { it.modelSlot })
        assertEquals(listOf(null, "lead", "lead"), steps.map { it.parentId })
    }

    @Test fun refusesUnknownHandoff() {
        rejects {
            SwarmPlanCompiler().compile(listOf(lead), "lead")
        }
    }

    @Test fun refusesDuplicateAgentIdentifiers() {
        rejects {
            SwarmPlanCompiler().compile(listOf(lead, lead), "lead")
        }
    }

    @Test fun refusesRepeatedHandoffs() {
        val repeated = lead.copy(handoffs = listOf("qa", "qa"))
        rejects {
            SwarmPlanCompiler().compile(listOf(repeated, qa), "lead")
        }
    }

    @Test fun refusesCyclesAndSharedChildMerges() {
        val cycle = qa.copy(handoffs = listOf("lead"))
        rejects {
            SwarmPlanCompiler().compile(listOf(lead, cycle, security), "lead")
        }
        val left = qa.copy(handoffs = listOf("security"))
        val merge = lead.copy(handoffs = listOf("qa", "security"))
        rejects {
            SwarmPlanCompiler().compile(listOf(merge, left, security), "lead")
        }
    }

    @Test fun refusesMoreAgentsThanBudget() {
        rejects {
            SwarmPlanCompiler(maxAgents = 2).compile(listOf(lead, qa, security), "lead")
        }
    }

    @Test fun refusesMissingRoot() {
        rejects {
            SwarmPlanCompiler().compile(listOf(qa), "lead")
        }
    }

    @Test fun handsParentOutputToChildWithoutRunningTools() = runBlocking {
        val prompts = mutableListOf<Pair<Int, String>>()
        val inference = SwarmInference { slot, _, task ->
            prompts += slot to task
            if (slot == 0) "Plan wykonania" else "Wynik QA"
        }
        val results = StudioSwarmRuntime(inference).execute(
            listOf(lead.copy(handoffs = listOf("qa")), qa),
            "lead",
            "Opracuj aplikację Android",
        )
        assertEquals(2, results.size)
        assertEquals("Plan wykonania", results[0].output)
        assertEquals("Wynik QA", results[1].output)
        assertEquals(listOf(0, 1), prompts.map { it.first })
        assertTrue(prompts[1].second.contains("<untrusted_agent_output>"))
        assertTrue(prompts[1].second.contains("Plan wykonania"))
        assertFalse(prompts[0].second.contains("<untrusted_agent_output>"))
    }

    @Test fun refusesBlankTaskAndOversizedOutput() = runBlocking {
        val runner = StudioSwarmRuntime(SwarmInference { _, _, _ -> "x".repeat(12_001) })
        rejectsSuspend {
            runner.execute(listOf(qa), "qa", " ")
        }
        rejectsSuspend {
            runner.execute(listOf(qa), "qa", "test")
        }
    }

    @Test fun refusesEmptyOutput() = runBlocking {
        val runner = StudioSwarmRuntime(SwarmInference { _, _, _ -> "" })
        rejectsSuspend {
            runner.execute(listOf(qa), "qa", "test")
        }
    }

    @Test fun rejectsInvalidAgentMetadata() {
        rejects {
            SwarmPlanCompiler().compile(listOf(qa.copy(systemPrompt = "")), "qa")
        }
        rejects {
            SwarmPlanCompiler().compile(listOf(qa.copy(name = "a".repeat(81))), "qa")
        }
    }

    private fun rejects(block: () -> Unit) {
        try {
            block()
            fail("Expected invalid input rejection")
        } catch (_: IllegalArgumentException) {
            // Required rejection.
        }
    }

    private suspend fun rejectsSuspend(block: suspend () -> Unit) {
        try {
            block()
            fail("Expected invalid inference rejection")
        } catch (_: IllegalArgumentException) {
            // Required rejection.
        }
    }
}
