package dev.slne.spawn.trader.manager.trade.impl

import dev.slne.spawn.trader.manager.trade.Trade
import dev.slne.surf.surfapi.bukkit.api.builder.buildItem
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import org.bukkit.Material

class LightTrade : Trade {
    override fun requirements() = mutableObjectListOf(
        buildItem(Material.EMERALD, 5) {},
        buildItem(Material.REDSTONE_LAMP, 20) {}
    )

    override fun rewards() = mutableObjectListOf(buildItem(Material.LIGHT, 20) {})

    override fun id(): Int {
        return 1
    }

    override fun name(): String {
        return "light-block"
    }

    override fun rewardMessage(): String {
        return "Danke für den Einkauf von 20x Licht-Blöcken!"
    }

    override fun cooldown(): Long {
        return 75600000L
    }
}
