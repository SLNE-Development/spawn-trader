package dev.slne.spawn.trader.manager.trade.impl

import dev.slne.spawn.trader.manager.trade.Trade
import dev.slne.surf.surfapi.bukkit.api.builder.buildItem
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import it.unimi.dsi.fastutil.objects.ObjectList
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * The type Frame trade.
 */
class FrameTrade : Trade {
    override fun requirements() = mutableObjectListOf(
        buildItem(Material.EMERALD, 5) {},
        buildItem(Material.ITEM_FRAME, 20) {}
    )

    override fun rewards(): ObjectList<ItemStack> {
        val itemStack = Bukkit.getItemFactory()
            .createItemStack("item_frame[entity_data={id:\"minecraft:item_frame\",Invisible:1b}]")

        itemStack.amount = 20
        return mutableObjectListOf(itemStack)
    }

    override fun id(): Int {
        return 0
    }

    override fun name(): String {
        return "invisible-item-frame"
    }


    override fun rewardMessage(): String {
        return "Danke für den Einkauf von 20x Item-Rahmen!"
    }

    override fun cooldown(): Long {
        return 75600000L
    }
}
