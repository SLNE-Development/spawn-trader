package dev.slne.spawn.trader.manager.trade.impl

import dev.slne.spawn.trader.manager.trade.Trade
import dev.slne.surf.surfapi.bukkit.api.builder.buildItem
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import it.unimi.dsi.fastutil.objects.ObjectList
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class GlobeTrade : Trade {
    override fun requirements() = mutableObjectListOf(
        buildItem(Material.EMERALD, 20) {},
        buildItem(Material.PAPER, 20) {}
    )

    override fun rewards(): ObjectList<ItemStack> {
        return mutableObjectListOf(buildItem(Material.GLOBE_BANNER_PATTERN) {})
    }

    override fun id(): Int {
        return 2
    }

    override fun name(): String {
        return "globe-banner-pattern"
    }

    override fun rewardMessage(): String {
        return "Danke für den Einkauf von einer Globe Banner Vorlage!"
    }

    override fun cooldown(): Long {
        return 75600000L
    }
}
