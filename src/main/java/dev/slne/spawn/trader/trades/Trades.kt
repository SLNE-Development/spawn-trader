package dev.slne.spawn.trader.trades

import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

@Suppress("UnstableApiUsage")
enum class Trades(val displayName: TextComponent, val singleItem: ItemStack, val price: Int) {
    INVISIBLE_ITEM_FRAME(
        buildText { variableValue("Unsichtbarer Itemrahmen") },
        Bukkit.getItemFactory()
            .createItemStack("item_frame[entity_data={id:\"minecraft:item_frame\",Invisible:1b}]"),
        500
    ),
    LIGHT_BLOCK(
        buildText { variableValue("Lichtblock") },
        ItemType.LIGHT.createItemStack(),
        250
    ),
    GLOBE_BANNER_PATTERN(
        buildText { variableValue("Globus Banner-Muster") },
        ItemType.GLOBE_BANNER_PATTERN.createItemStack(),
        1000
    ),
    FIREFLY_BUSH(
        buildText { variableValue("Glühwürmchen-Busch") },
        ItemType.FIREFLY_BUSH.createItemStack(),
        500
    )
}