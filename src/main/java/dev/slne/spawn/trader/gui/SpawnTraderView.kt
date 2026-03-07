package dev.slne.spawn.trader.gui

import dev.slne.spawn.trader.trades.Trades
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
                "O       O",
                "OOOO-OOOO"
            )
            .cancelInteractions()
    }

    override fun onFirstRender(render: RenderContext) {
        render.layoutSlot('O', outlineItem)
        render.layoutSlot('-', closeItem)
        render.layoutSlot('A', frameItem)
        render.layoutSlot('B', lightBlockItem)
        render.layoutSlot('C', globeBannerPatternItem)
        render.layoutSlot('D', fireflyBushItem)
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
                variableValue("${Trades.INVISIBLE_ITEM_FRAME.price}CC")
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
                variableValue("${Trades.LIGHT_BLOCK.price}CC")
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
                variableValue("${Trades.GLOBE_BANNER_PATTERN.price}CC")
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
                variableValue("${Trades.FIREFLY_BUSH.price}CC")
            }
            emptyLine()
            line {
                variableValue("Klicke, um das Item zu kaufen.".toSmallCaps())
            }
        }
    }
}