package dev.slne.spawn.trader.papi

import dev.slne.spawn.trader.papi.placeholder.SpawnTraderVisiblePlaceholder
import dev.slne.surf.surfapi.bukkit.api.hook.papi.expansion.PapiExpansion

object PapiExpansion : PapiExpansion(
    identifier = "spawntrader",
    placeholder = listOf(
        SpawnTraderVisiblePlaceholder
    ),
    author = "red"
)