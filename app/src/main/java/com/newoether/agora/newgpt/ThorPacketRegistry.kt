package com.newoether.agora.newgpt

data class ThorSourcePacket(
    val number: String,
    val sources: List<ThorSource>,
    val roles: List<ThorRolePreset>,
)

object ThorPacketRegistry {
    val packets: List<ThorSourcePacket> = listOf(
        ThorSourcePacket("01", ThorPacketOneCatalog.sources, ThorPacketOneCatalog.roles),
        ThorSourcePacket("02", ThorPacketTwoCatalog.sources, ThorPacketTwoCatalog.roles),
        ThorSourcePacket("03", ThorPacketThreeCatalog.sources, ThorPacketThreeCatalog.roles),
        ThorSourcePacket("04", ThorPacketFourCatalog.sources, ThorPacketFourCatalog.roles),
        ThorSourcePacket("05", ThorPacketFiveCatalog.sources, ThorPacketFiveCatalog.roles),
        ThorSourcePacket("06", ThorPacketSixCatalog.sources, ThorPacketSixCatalog.roles),
        ThorSourcePacket("07", ThorPacketSevenCatalog.sources, ThorPacketSevenCatalog.roles),
    ).also { packets ->
        require(packets.map(ThorSourcePacket::number).distinct().size == packets.size)
        val sources = packets.flatMap(ThorSourcePacket::sources)
        val roles = packets.flatMap(ThorSourcePacket::roles)
        require(sources.map(ThorSource::repository).distinct().size == sources.size)
        require(roles.map(ThorRolePreset::id).distinct().size == roles.size)
        require(sources.none(ThorSource::activated))
        val sourceRisks = sources.associate { it.repository to it.risk }
        require(roles.all { role ->
            role.sourceRepositories.isNotEmpty() &&
                role.sourceRepositories.all { repository ->
                    val risk = sourceRisks[repository]
                    risk != null && risk != ThorSourceRisk.RESTRICTED
                }
        })
    }

    val sources: List<ThorSource> = packets.flatMap(ThorSourcePacket::sources)

    val roles: List<ThorRolePreset> = packets.flatMap(ThorSourcePacket::roles)

    fun packetByTabIndex(tab: Int, firstTab: Int = 5): ThorSourcePacket? {
        if (tab < firstTab) return null
        return packets.getOrNull(tab - firstTab)
    }
}
