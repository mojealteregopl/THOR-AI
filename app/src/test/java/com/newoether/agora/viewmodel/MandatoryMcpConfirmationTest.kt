package com.newoether.agora.viewmodel

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import io.mockk.mockk
import com.newoether.agora.data.repository.SettingsRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MandatoryMcpConfirmationTest {
    @Test
    fun mandatoryApprovalNeverTrustsWholeServer() = runBlocking {
        val foreground = MutableStateFlow(true)
        val controller = ShellConfirmationController(
            settings = mockk<SettingsRepository>(relaxed = true),
            appForeground = foreground,
        )
        val first = async { controller.confirmRequired("MCP · Trusted", "MCP: delete") }
        val firstPrompt = withTimeout(2_000L) {
            controller.pendingShellCommand.filterNotNull().first()
        }
        assertTrue(firstPrompt.mandatory)
        controller.resolve(allow = true, alwaysAllowServer = true)
        assertTrue(first.await())

        val second = async { controller.confirmRequired("MCP · Trusted", "MCP: delete again") }
        val secondPrompt = withTimeout(2_000L) {
            controller.pendingShellCommand.filterNotNull().first()
        }
        assertTrue(secondPrompt.mandatory)
        controller.resolve(allow = false, alwaysAllowServer = true)
        assertFalse(second.await())
    }

    @Test
    fun mandatoryApprovalFailsClosedWhenNoForegroundUi() = runBlocking {
        val foreground = MutableStateFlow(false)
        val controller = ShellConfirmationController(
            settings = mockk<SettingsRepository>(relaxed = true),
            appForeground = foreground,
        )
        assertFalse(controller.confirmRequired("MCP · Worker", "MCP: search"))
        assertNull(controller.pendingShellCommand.value)
    }

    @Test
    fun mandatoryApprovalIsRevokedWhenAppMovesToBackground() = runBlocking {
        val foreground = MutableStateFlow(true)
        val controller = ShellConfirmationController(
            settings = mockk<SettingsRepository>(relaxed = true),
            appForeground = foreground,
        )
        val attempt = async { controller.confirmRequired("MCP · Worker", "MCP: inspect") }
        withTimeout(2_000L) { controller.pendingShellCommand.filterNotNull().first() }
        foreground.value = false
        assertFalse(withTimeout(2_000L) { attempt.await() })
        assertNull(controller.pendingShellCommand.value)
    }
}
