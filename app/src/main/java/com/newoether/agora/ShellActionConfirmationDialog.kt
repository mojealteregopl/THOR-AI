package com.newoether.agora

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newoether.agora.ui.chat.message.ChatMarkdownCodeBlock
import com.newoether.agora.viewmodel.ChatViewModel

/** Central user decision surface for regular shell commands and mandatory MCP calls. */
@Composable
internal fun ShellActionConfirmationDialog(viewModel: ChatViewModel) {
    val pendingCommand by viewModel.pendingShellCommand.collectAsState()
    val pending = pendingCommand ?: return
    var alwaysAllow by remember(pending) { mutableStateOf(false) }

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        onDismissRequest = { viewModel.resolveShellConfirmation(allow = false) },
        icon = {
            Icon(
                Icons.Default.Terminal,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        title = {
            Text(
                text = if (pending.mandatory) {
                    "Zatwierdź pojedyncze wywołanie MCP: ${pending.server}"
                } else {
                    stringResource(R.string.shell_confirm_title, pending.server)
                },
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .heightIn(max = 240.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    ChatMarkdownCodeBlock(code = pending.summary)
                }
                if (!pending.mandatory) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .pointerInput(Unit) {
                                detectTapGestures { alwaysAllow = !alwaysAllow }
                            },
                    ) {
                        Checkbox(
                            checked = alwaysAllow,
                            onCheckedChange = { alwaysAllow = it },
                        )
                        Text(
                            stringResource(R.string.shell_confirm_always),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.resolveShellConfirmation(
                        allow = true,
                        alwaysAllowServer = alwaysAllow && !pending.mandatory,
                    )
                },
            ) { Text(stringResource(R.string.shell_confirm_allow)) }
        },
        dismissButton = {
            TextButton(
                onClick = { viewModel.resolveShellConfirmation(allow = false) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                ),
            ) { Text(stringResource(R.string.shell_confirm_deny)) }
        },
    )
}
