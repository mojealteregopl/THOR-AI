package com.newoether.agora.newgpt

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class StudioSwarmRecoveryTest {
    private val lead = SwarmAgent("lead", "Lead", "plan", listOf("qa"))
    private val qa = SwarmAgent("qa", "QA", "check", emptyList())
    private class MemoryCheckpoint : SwarmCheckpointStore {
        var identity: List<Byte>? = null
        var stored = emptyList<SwarmRecord>()
        var removed = 0
        override suspend fun read(fingerprint: ByteArray): List<SwarmRecord> =
            if (identity == fingerprint.toList()) stored else emptyList()
        override suspend fun write(fingerprint: ByteArray, records: List<SwarmRecord>) {
            identity = fingerprint.toList()
            stored = records.toList()
        }
        override suspend fun delete() {
            stored = emptyList()
            identity = null
            removed++
        }
    }

    @Test fun resumesWithoutRepeatingCompletedSteps() = runBlocking {
        val journal = MemoryCheckpoint()
        var calls = 0
        val first = StudioSwarmRuntime(SwarmInference { _, _, _ ->
            calls++
            if (calls == 2) error("second inference unavailable")
            "first answer"
        }, checkpointStore = journal)
        try {
            first.execute(listOf(lead, qa), "lead", "Build")
            fail("Expected an interrupted execution")
        } catch (_: IllegalStateException) { }
        assertEquals(1, journal.stored.size)

        val slots = mutableListOf<Int>()
        val events = mutableListOf<SwarmExecutionEvent>()
        val second = StudioSwarmRuntime(SwarmInference { slot, _, input ->
            slots += slot
            assertTrue(input.contains("first answer"))
            "second answer"
        }, checkpointStore = journal)
        val results = second.execute(listOf(lead, qa), "lead", "Build") { events += it }
        assertEquals(listOf(1), slots)
        assertEquals(listOf("first answer", "second answer"), results.map { it.output })
        assertTrue(events.first() is SwarmExecutionEvent.Restored)
        assertEquals(1, journal.removed)
    }

    @Test fun refusesInvalidRestoredPrefixBeforeStartingInference() = runBlocking {
        val journal = MemoryCheckpoint()
        val steps = SwarmPlanCompiler().compile(listOf(lead, qa), "lead")
        journal.identity = SwarmExecutionFingerprint.digest("Build", steps).toList()
        journal.stored = listOf(SwarmRecord("qa", "QA", 1, "invalid order"))
        var executed = false
        val runner = StudioSwarmRuntime(SwarmInference { _, _, _ ->
            executed = true
            "output"
        }, checkpointStore = journal)
        try {
            runner.execute(listOf(lead, qa), "lead", "Build")
            fail("Expected malformed checkpoint rejection")
        } catch (_: IllegalArgumentException) { }
        assertFalse(executed)
    }

    @Test fun fingerprintChangesWithTaskAndSystemPrompt() {
        val plan = SwarmPlanCompiler().compile(listOf(lead, qa), "lead")
        val revised = SwarmPlanCompiler().compile(listOf(lead.copy(systemPrompt = "new plan"), qa), "lead")
        val initial = SwarmExecutionFingerprint.digest("Build", plan).toList()
        assertNotEquals(initial, SwarmExecutionFingerprint.digest("Other task", plan).toList())
        assertNotEquals(initial, SwarmExecutionFingerprint.digest("Build", revised).toList())
    }
}
