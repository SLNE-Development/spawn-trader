package dev.slne.spawn.trader.papi.placeholder

import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiPlaceholder
import org.bukkit.OfflinePlayer

object SpawnTraderVisiblePlaceholder : PapiPlaceholder(
    "isVisible",
) {
    override fun parse(player: OfflinePlayer, args: List<String>): String =
        traderVisibilityService.visible.toString()
}