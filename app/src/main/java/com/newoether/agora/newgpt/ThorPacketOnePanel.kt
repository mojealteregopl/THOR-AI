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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val CatalogSurface = Color(0xFF151518)
private val CatalogGold = Color(0xFFE1B84A)
private val CatalogText = Color(0xFFF4EEDF)

@Composable
fun ThorPacketOnePanel(
    importedIds: Set<String>,
    status: String,
    onImport: (ThorRolePreset) -> Unit,
) = ThorSourcePacketPanel(
    packetNumber = "01",
    sources = ThorPacketOneCatalog.sources,
    roles = ThorPacketOneCatalog.roles,
    importedIds = importedIds,
    status = status,
    onImport = onImport,
)

@Composable
fun ThorPacketTwoPanel(
    importedIds: Set<String>,
    status: String,
    onImport: (ThorRolePreset) -> Unit,
) = ThorSourcePacketPanel(
    packetNumber = "02",
    sources = ThorPacketTwoCatalog.sources,
    roles = ThorPacketTwoCatalog.roles,
    importedIds = importedIds,
    status = status,
    onImport = onImport,
)

@Composable
fun ThorPacketThreePanel(
    importedIds: Set<String>,
    status: String,
    onImport: (ThorRolePreset) -> Unit,
) = ThorSourcePacketPanel(
    packetNumber = "03",
    sources = ThorPacketThreeCatalog.sources,
    roles = ThorPacketThreeCatalog.roles,
    importedIds = importedIds,
    status = status,
    onImport = onImport,
)

@Composable
fun ThorPacketFourPanel(
    importedIds: Set<String>,
    status: String,
    onImport: (ThorRolePreset) -> Unit,
) = ThorSourcePacketPanel(
    packetNumber = "04",
    sources = ThorPacketFourCatalog.sources,
    roles = ThorPacketFourCatalog.roles,
    importedIds = importedIds,
    status = status,
    onImport = onImport,
)

@Composable
fun ThorSourcePacketPanel(
    packetNumber: String,
    sources: List<ThorSource>,
    roles: List<ThorRolePreset>,
    importedIds: Set<String>,
    status: String,
    onImport: (ThorRolePreset) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item {
            Text("THOR · PACZKA $packetNumber", color = CatalogGold, style = MaterialTheme.typography.titleLarge)
            Text(
                "${sources.size} unikalnych repozytoriów; ${roles.size} lokalnych profili specjalistów. " +
                    "Import profilu tworzy tylko konfigurację promptu. " +
                    "Źródła MCP, CLI, API i automatyzacji pozostają niepodłączone.",
                color = CatalogText,
            )
        }
        if (status.isNotBlank()) {
            item {
                Text(status, color = CatalogGold, style = MaterialTheme.typography.bodyMedium)
            }
        }
        item {
            HorizontalDivider(color = CatalogGold)
            Text(
                "ROLE DO IMPORTU",
                color = CatalogGold,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp),
            )
        }
        items(roles, key = { it.id }) { role ->
            Card(
                colors = CardDefaults.cardColors(containerColor = CatalogSurface),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(role.name, color = CatalogGold, fontWeight = FontWeight.SemiBold)
                    Text(role.description, color = CatalogText)
                    Text("Źródła: ${role.sourceRepositories.size}", color = CatalogText)
                    Button(
                        enabled = !importedIds.contains(role.id),
                        onClick = { onImport(role) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(if (importedIds.contains(role.id)) "ZAIMPORTOWANO" else "DODAJ DO AGENTS")
                    }
                }
            }
        }
        item {
            HorizontalDivider(color = CatalogGold)
            Text(
                "ZWERYFIKOWANE ŹRÓDŁA GITHUB · NIEAKTYWNE",
                color = CatalogGold,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp),
            )
        }
        items(sources, key = { it.repository }) { source ->
            Card(
                colors = CardDefaults.cardColors(containerColor = CatalogSurface),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(source.repository, color = CatalogGold, fontWeight = FontWeight.SemiBold)
                    Text(source.capability, color = CatalogText)
                    Text(
                        "Typ: ${source.kind} · Ryzyko: ${source.risk} · " +
                            "Stan: ${when {
                            source.risk == ThorSourceRisk.RESTRICTED -> "ZABLOKOWANE"
                            source.activated -> "AKTYWNE"
                            else -> "TYLKO ŹRÓDŁO"
                        }}",
                        color = CatalogText,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }
    }
}
