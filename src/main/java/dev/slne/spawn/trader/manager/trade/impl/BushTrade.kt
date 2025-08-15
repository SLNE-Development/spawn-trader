package dev.slne.spawn.trader.manager.trade.impl

import dev.slne.spawn.trader.manager.trade.Trade
import dev.slne.surf.surfapi.bukkit.api.builder.buildItem
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import it.unimi.dsi.fastutil.objects.ObjectList
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class BushTrade : Trade {
    override fun requirements() = mutableObjectListOf(
        buildItem(Material.EMERALD, 20) {},
        buildItem(Material.SHORT_GRASS, 20) {}
    )

    override fun rewards(): ObjectList<ItemStack> {
        return mutableObjectListOf(buildItem(Material.FIREFLY_BUSH) {})
    }

    override fun id(): Int {
        return 3
    }

    override fun name(): String {
        return "firefly-bush"
    }

    override fun rewardMessage(): String {
        return "Danke für den Einkauf von den Glühwürmchenbüschen!"
    }

    override fun cooldown(): Long {
        return 75600000L
    }
}
