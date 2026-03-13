package dev.slne.spawn.trader.gui

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.spawn.trader.plugin
import dev.slne.spawn.trader.service.traderTradeService
import dev.slne.spawn.trader.trades.Trades
import dev.slne.spawn.trader.util.formatPrice
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.inventory.framework.titleBuilder
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import me.devnatan.inventoryframework.View
import me.devnatan.inventoryframework.ViewConfigBuilder
import me.devnatan.inventoryframework.context.RenderContext
import net.kyori.adventure.text.format.TextDecoration

object SpawnTraderView : View() {
    override fun onInit(config: ViewConfigBuilder) {
        config
            .titleBuilder {
                variableValue("Fahrender Händler".toSmallCaps(), TextDecoration.BOLD)
            }
            .size(6)
            .layout(
                "OOOOOOOOO",
                "O       O",
                "O  A B  O",
                "O  C D  O",
                "O   L   O",
                "OOOO-OOOO"
            )
            .cancelInteractions()
    }

    override fun onFirstRender(render: RenderContext) {
        render.layoutSlot('O', outlineItem)
        render.layoutSlot('-', closeItem).onClick { click ->
            click.playGeneralClickSound()
            click.closeForPlayer()
        }
        render.layoutSlot('A', frameItem).onClick { onClick ->
            onClick.playGeneralClickSound()
            plugin.launch {
                traderTradeService.buy(Trades.INVISIBLE_ITEM_FRAME, onClick.player)
            }
        }
        render.layoutSlot('B', lightBlockItem).onClick { onClick ->
            onClick.playGeneralClickSound()
            plugin.launch {
                traderTradeService.buy(Trades.LIGHT_BLOCK, onClick.player)
            }
        }
        render.layoutSlot('C', globeBannerPatternItem).onClick { onClick ->
            onClick.playGeneralClickSound()
            plugin.launch {
                traderTradeService.buy(Trades.GLOBE_BANNER_PATTERN, onClick.player)
            }
        }
        render.layoutSlot('D', fireflyBushItem).onClick { onClick ->
            onClick.playGeneralClickSound()
            plugin.launch {
                traderTradeService.buy(Trades.FIREFLY_BUSH, onClick.player)
            }
        }
        render.layoutSlot('L', clockItem).onClick { click ->
            click.playGeneralClickSound()
            plugin.launch {
                traderTradeService.buy(Trades.TRADER_CLOCK, click.player)
            }
        }
    }

    private val clockItem = plugin.spawnTradersClockItem.clone().apply {
        displayName {
            append(Trades.TRADER_CLOCK.displayName)
        }

        buildLore {
            line {
                spacer("Eine uralte Uhr...".toSmallCaps())
            }
            emptyLine()
            line {
                darkSpacer("▪")
                appendSpace()
                white("Preis: ".toSmallCaps())
                variableValue(formatPrice(Trades.TRADER_CLOCK.price))
            }
            emptyLine()
            line {
                variableValue("Klicke, um das Item zu kaufen.".toSmallCaps())
            }
        }
    }

    private val frameItem = Trades.INVISIBLE_ITEM_FRAME.singleItem.clone().apply {
        displayName {
            append(Trades.INVISIBLE_ITEM_FRAME.displayName)
        }

        buildLore {
            line {
                spacer("Unsichtbar".toSmallCaps())
            }
            emptyLine()
            line {
                darkSpacer("▪")
                appendSpace()
                white("Preis: ".toSmallCaps())
                variableValue(formatPrice(Trades.INVISIBLE_ITEM_FRAME.price))
            }
            emptyLine()
            line {
                variableValue("Klicke, um das Item zu kaufen.".toSmallCaps())
            }
        }
    }

    private val lightBlockItem = Trades.LIGHT_BLOCK.singleItem.clone().apply {
        displayName {
            append(Trades.LIGHT_BLOCK.displayName)
        }

        buildLore {
            line {
                spacer("Licht-Level: 15".toSmallCaps())
            }
            emptyLine()
            line {
                darkSpacer("▪")
                appendSpace()
                white("Preis: ".toSmallCaps())
                variableValue(formatPrice(Trades.LIGHT_BLOCK.price))
            }
            emptyLine()
            line {
                variableValue("Klicke, um das Item zu kaufen.".toSmallCaps())
            }
        }
    }

    private val globeBannerPatternItem = Trades.GLOBE_BANNER_PATTERN.singleItem.clone().apply {
        displayName {
            append(Trades.GLOBE_BANNER_PATTERN.displayName)
        }

        buildLore {
            line {
                spacer("Globus Banner-Muster".toSmallCaps())
            }
            emptyLine()
            line {
                darkSpacer("▪")
                appendSpace()
                white("Preis: ".toSmallCaps())
                variableValue(formatPrice(Trades.GLOBE_BANNER_PATTERN.price))
            }
            emptyLine()
            line {
                variableValue("Klicke, um das Item zu kaufen.".toSmallCaps())
            }
        }
    }

    private val fireflyBushItem = Trades.FIREFLY_BUSH.singleItem.clone().apply {
        displayName {
            append(Trades.FIREFLY_BUSH.displayName)
        }

        buildLore {
            line {
                spacer("Glühwürmchen-Busch".toSmallCaps())
            }
            emptyLine()
            line {
                darkSpacer("▪")
                appendSpace()
                white("Preis: ".toSmallCaps())
                variableValue(formatPrice(Trades.FIREFLY_BUSH.price))
            }
            emptyLine()
            line {
                variableValue("Klicke, um das Item zu kaufen.".toSmallCaps())
            }
        }
    }
}