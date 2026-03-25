package dev.slne.spawn.trader.trades

import dev.slne.spawn.trader.plugin
import dev.slne.spawn.trader.util.traderColored
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

@Suppress("UnstableApiUsage")
enum class Trades(val displayName: TextComponent, val singleItem: ItemStack, val price: Int) {
    INVISIBLE_ITEM_FRAME(
        buildText { traderColored("Unsichtbarer Itemrahmen".toSmallCaps(), TextDecoration.BOLD) },
        Bukkit.getItemFactory()
            .createItemStack("item_frame[entity_data={id:\"minecraft:item_frame\",Invisible:1b}]")
            .apply {
                buildLore {
                    line {
                        variableValue("Unsichtbar")
                    }
                }
            },
        500
    ),
    LIGHT_BLOCK(
        buildText { traderColored("Lichtblock".toSmallCaps(), TextDecoration.BOLD) },
        ItemType.LIGHT.createItemStack(),
        250
    ),
    GLOBE_BANNER_PATTERN(
        buildText { traderColored("Globus Banner-Muster".toSmallCaps(), TextDecoration.BOLD) },
        ItemType.GLOBE_BANNER_PATTERN.createItemStack(),
        1000
    ),
    TRADER_CLOCK(
        buildText { traderColored("Händlers Uhr".toSmallCaps(), TextDecoration.BOLD) },
        plugin.spawnTradersClockItem,
        50000
    )
}