package com.newoether.agora.newgpt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val SwarmGold = Color(0xFFE1B84A)
private val SwarmBackground = Color(0xFF151518)
private val SwarmText = Color(0xFFF4EEDF)

@Composable
fun StudioSwarmPanel(
    agents: List<Pair<String, String>>,
    rootId: String?,
    task: String,
    status: String,
    running: Boolean,
    modelsLoaded: Boolean,
    onRootSelected: (String) -> Unit,
    onTaskChanged: (String) -> Unit,
    onRun: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SwarmBackground),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text("THOR · AGENT SWARM", color = SwarmGold, fontWeight = FontWeight.Bold)
                    Text(
                        "Rzeczywiste wykonanie promptów w dwóch załadowanych modelach GGUF. " +
                            "Agenci są uruchamiani kolejno, a handoff przekazuje wynik rodzica. " +
                            "Narzędzia i polecenia systemowe nie są wykonywane automatycznie.",
                        color = SwarmText,
                    )
                    OutlinedTextField(
                        value = task,
                        onValueChange = onTaskChanged,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Zadanie (maks. 8000 znaków)") },
                        minLines = 4,
                        maxLines = 8,
                        enabled = !running,
                    )
                    Text(
                        if (modelsLoaded) "GGUF: oba modele gotowe" else
                            "Najpierw załaduj dwa modele w zakładce GGUF ×2.",
                        color = SwarmGold,
                    )
                    Button(
                        onClick = onRun,
                        enabled = modelsLoaded && agents.isNotEmpty() &&
                            rootId != null && task.isNotBlank() &&
                            task.length <= 8_000 && !running,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SwarmGold,
                            contentColor = Color.Black,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (running) CircularProgressIndicator()
                        else Text("URUCHOM ZESPÓŁ AGENTÓW")
                    }
                }
            }
        }
        item {
            Text("AGENT GŁÓWNY", color = SwarmGold, fontWeight = FontWeight.Bold)
        }
        items(agents, key = { it.first }) { (id, name) ->
            OutlinedButton(
                onClick = { onRootSelected(id) },
                enabled = !running,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    (if (id == rootId) "● " else "○ ") + name,
                    color = if (id == rootId) SwarmGold else SwarmText,
                )
            }
        }
        if (status.isNotBlank()) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = SwarmBackground)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Text("DZIENNIK WYKONANIA", color = SwarmGold)
                        SelectionContainer {
                            Text(status, color = SwarmText)
                        }
                    }
                }
            }
        }
    }
}
