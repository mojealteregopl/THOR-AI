package com.newoether.agora.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RuntimeIdentityGuardTest {
    @Test
    fun `guard matches the current run and rejects stale run identity`() {
        val state = RunState.Active(
            identity = RuntimeRunIdentity(
                conversationId = "conversation",
                ownerToken = 7L,
                runId = "run-42",
                pass = 1,
            ),
        )

        val current = RuntimeIdentityGuard(
            conversationId = "conversation",
            ownerToken = 7L,
            runId = "run-42",
            pass = 1,
            effectId = "effect",
        )
        val stale = current.copy(runId = "run-99")

        assertTrue(current.matches(state))
        assertFalse(stale.matches(state))
    }

    @Test
    fun `reducer rejects stale effect identities using guard semantics`() {
        val state = RunState.Active(
            identity = RuntimeRunIdentity(
                conversationId = "conversation",
                ownerToken = 9L,
                runId = "run-a",
                pass = 2,
            ),
        )
        val requested = ConversationCommand.ProviderPassRequested(
            identity = RunEffectIdentity(
                conversationId = "conversation",
                ownerToken = 9L,
                runId = "run-a",
                pass = 2,
                effectId = "provider-a",
            ),
        )

        val accepted = ConversationRuntimeReducer.reduce(state, requested)
        assertTrue(accepted.accepted)

        val stale = ConversationCommand.ProviderPassCompleted(
            identity = RunEffectIdentity(
                conversationId = "conversation",
                ownerToken = 9L,
                runId = "run-b",
                pass = 2,
                effectId = "provider-a",
            ),
            result = ProviderPassResult.COMPLETED_TEXT,
        )

        val transition = ConversationRuntimeReducer.reduce(accepted.newState, stale)
        assertEquals(CommandRejection.STALE_IDENTITY, transition.rejection)
    }
}
