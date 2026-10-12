package com.newoether.agora.model

/**
 * Hardened runtime identity contract for one conversation execution slot.
 *
 * The reducer is the single source of truth for transition acceptance. Every async effect and
 * completion must carry an identity that is both unique and still current. This small invariant is
 * the foundation of THOR-grade agentic execution: stale tool rounds, late provider completions, and
 * duplicated finalization attempts cannot mutate a newer run.
 */
internal data class RuntimeIdentityGuard(
    val conversationId: String,
    val ownerToken: Long,
    val runId: String,
    val pass: Int,
    val effectId: String,
) {
    init {
        require(conversationId.isNotBlank())
        require(ownerToken > 0L)
        require(runId.isNotBlank())
        require(pass >= 0)
        require(effectId.isNotBlank())
    }

    fun matches(state: RunState): Boolean = when (state) {
        is RunState.Idle -> false
        is RunState.Recovering -> state.effectIdentity.conversationId == conversationId &&
            state.effectIdentity.runId == runId &&
            state.effectIdentity.pass == pass &&
            state.effectIdentity.effectId == effectId
        is RunState.Preparing -> state.inputEffectIdentity.conversationId == conversationId &&
            state.inputEffectIdentity.ownerToken == ownerToken &&
            state.inputEffectIdentity.runId == runId &&
            state.inputEffectIdentity.pass == pass &&
            state.inputEffectIdentity.effectId == effectId
        is RunState.Active -> state.identity.conversationId == conversationId &&
            state.identity.ownerToken == ownerToken &&
            state.identity.runId == runId &&
            state.identity.pass == pass
        is RunState.Stopping -> state.identity.conversationId == conversationId &&
            state.identity.ownerToken == ownerToken &&
            state.identity.runId == runId &&
            state.identity.pass == pass
        is RunState.Finalizing -> state.identity.conversationId == conversationId &&
            state.identity.ownerToken == ownerToken &&
            state.identity.runId == runId &&
            state.identity.pass == pass &&
            state.effectIdentity.effectId == effectId
    }

    companion object {
        fun forEffect(identity: RunEffectIdentity): RuntimeIdentityGuard = RuntimeIdentityGuard(
            conversationId = identity.conversationId,
            ownerToken = identity.ownerToken,
            runId = identity.runId,
            pass = identity.pass,
            effectId = identity.effectId,
        )

        fun forRun(identity: RuntimeRunIdentity): RuntimeIdentityGuard = RuntimeIdentityGuard(
            conversationId = identity.conversationId,
            ownerToken = identity.ownerToken,
            runId = identity.runId ?: error("runtime identity requires a bound runId"),
            pass = identity.pass,
            effectId = "slot",
        )
    }
}
