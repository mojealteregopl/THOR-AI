package com.newoether.agora.viewmodel

import com.newoether.agora.data.repository.SettingsRepository
import com.newoether.agora.service.AppForegroundTracker
import com.newoether.agora.util.Constants
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Collections

/**
 * Coordinates user confirmation of shell commands issued by remote MCP / shell servers.
 *
 * Owns the pending-confirmation [StateFlow], the per-session trust set, and the
 * suspend/await handshake between the generation pipeline (which asks) and the UI
 * (which answers). Extracted from [ChatViewModel] as a single responsibility so the
 * trust policy lives in one place and is independently testable.
 */
class ShellConfirmationController(
    private val settings: SettingsRepository,
    private val appForeground: StateFlow<Boolean> = AppForegroundTracker.foreground,
) {
    data class PendingShellCommand(
        val server: String,
        val summary: String,
        val deferred: CompletableDeferred<Boolean>,
        val mandatory: Boolean = false,
    )

    private val _pendingShellCommand = MutableStateFlow<PendingShellCommand?>(null)
    val pendingShellCommand: StateFlow<PendingShellCommand?> = _pendingShellCommand.asStateFlow()

    // Servers the user chose to trust for the rest of this app session.
    private val sessionAllowedServers = Collections.synchronizedSet(mutableSetOf<String>())

    // One prompt on screen at a time. Without this, parallel conversations overwrite each
    // other's pending prompt: the loser's dialog never renders and its confirm() silently
    // times out refused.
    private val promptMutex = Mutex()

    /** User-configurable policy for ordinary shell commands. */
    suspend fun confirm(server: String, summary: String): Boolean =
        confirmInternal(server, summary, mandatory = false)

    /**
     * Mandatory per-invocation approval for external MCP calls.
     * Always prompts, even if shell confirmation is disabled or the server was trusted
     * for ordinary shell commands. Background tasks cannot answer and are denied.
     */
    suspend fun confirmRequired(server: String, summary: String): Boolean =
        confirmInternal(server, summary, mandatory = true)

    private suspend fun confirmInternal(
        server: String,
        summary: String,
        mandatory: Boolean,
    ): Boolean {
        if (!mandatory && !settings.shellConfirmEnabled.value) return true
        if (!mandatory && sessionAllowedServers.contains(server)) return true
        if (!appForeground.value) return false

        return promptMutex.withLock {
            if (!mandatory && sessionAllowedServers.contains(server)) return@withLock true
            if (!appForeground.value) return@withLock false
            val deferred = CompletableDeferred<Boolean>()
            _pendingShellCommand.value = PendingShellCommand(
                server = server,
                summary = summary,
                deferred = deferred,
                mandatory = mandatory,
            )
            try {
                withTimeout(Constants.SHELL_CONFIRM_TIMEOUT_MS) {
                    coroutineScope {
                        val backgrounded = async {
                            appForeground.filter { foreground -> !foreground }.first()
                            false
                        }
                        try {
                            select {
                                deferred.onAwait { allowed -> allowed }
                                backgrounded.onAwait { allowed -> allowed }
                            }
                        } finally {
                            backgrounded.cancel()
                        }
                    }
                }
            } catch (_: TimeoutCancellationException) {
                false
            } finally {
                if (_pendingShellCommand.value?.deferred === deferred) {
                    _pendingShellCommand.value = null
                }
            }
        }
    }

    /** Called by the UI to resolve a pending confirmation. */
    fun resolve(allow: Boolean, alwaysAllowServer: Boolean = false) {
        val pending = _pendingShellCommand.value ?: return
        if (allow && alwaysAllowServer && !pending.mandatory) {
            sessionAllowedServers.add(pending.server)
        }
        pending.deferred.complete(allow)
        _pendingShellCommand.value = null
    }

    fun setEnabled(enabled: Boolean) = settings.setShellConfirmEnabled(enabled)
}
