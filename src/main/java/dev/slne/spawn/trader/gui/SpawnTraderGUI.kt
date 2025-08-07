package dev.slne.spawn.trader.gui

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder
import com.github.stefvanschie.inventoryframework.gui.GuiItem
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui
import com.github.stefvanschie.inventoryframework.pane.OutlinePane
import dev.slne.spawn.trader.manager.TradeManager
import dev.slne.spawn.trader.manager.trade.impl.FrameTrade
import dev.slne.spawn.trader.manager.trade.impl.GlobeTrade
import dev.slne.spawn.trader.manager.trade.impl.LightTrade
import dev.slne.surf.surfapi.bukkit.api.builder.buildItem
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

class SpawnTraderGUI(private val player: Player) :
    ChestGui(6, ComponentHolder.of(Component.text("Händler").color(NamedTextColor.BLACK))) {
    private val available = buildItem(Material.LIME_DYE) {
        displayName {
            success("Verfügbar")
        }
        addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES)
    }

    private val locked = buildItem(Material.RED_DYE) {
        displayName {
            error("Nicht Verfügbar")
        }
        addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
    }

    private val lightStatusPane = OutlinePane(2, 3, 1, 1)
    private val frameStatusPane = OutlinePane(6, 3, 1, 1)
    private val globeStatusPane = OutlinePane(4, 3, 1, 1)

    private val frameTrade: FrameTrade = FrameTrade()
    private val lightTrade: LightTrade = LightTrade()
    private val globeTrade: GlobeTrade = GlobeTrade()

    init {

        val lightPane = OutlinePane(2, 2, 1, 1, Pane.Priority.LOW)
        val framePane = OutlinePane(6, 2, 1, 1, Pane.Priority.LOW)
        val globePane = OutlinePane(4, 2, 1, 1, Pane.Priority.LOW)
        val footer = OutlinePane(0, 5, 9, 1, Pane.Priority.LOW)
        val header = OutlinePane(0, 0, 9, 1, Pane.Priority.LOW)

        this.setOnGlobalClick { it.isCancelled = true }
        this.setOnGlobalDrag { it.isCancelled = true }

        header.addItem(GuiItem(buildItem(Material.GRAY_STAINED_GLASS_PANE) {
            displayName {
                text("")
            }
        }))
        header.setRepeat(true)

        footer.addItem(GuiItem(buildItem(Material.GRAY_STAINED_GLASS_PANE) {
            displayName {
                text("")
            }
        }))
        footer.setRepeat(true)

        lightPane.addItem(GuiItem(LIGHT_BLOCK_STACK) {
            TradeManager.buy(player, lightTrade)
            SpawnTraderGUI(player).show(player)
        })

        framePane.addItem(GuiItem(FRAME_ITEM_STACK) {
            TradeManager.buy(player, frameTrade)
            SpawnTraderGUI(player).show(player)
        })

        globePane.addItem(GuiItem(GLOBE_BANNER_STACK) {
            TradeManager.buy(player, globeTrade)
            SpawnTraderGUI(player).show(player)
        })

        this.addPane(framePane)
        this.addPane(lightPane)
        this.addPane(globePane)
        this.addPane(frameStatusPane)
        this.addPane(lightStatusPane)
        this.addPane(globeStatusPane)
        this.addPane(header)
        this.addPane(footer)

        this.setStatusItems()
    }

    /**
     * Sets status items.
     */
    private fun setStatusItems() {
        lightStatusPane.clear()
        frameStatusPane.clear()

        if (TradeManager.isOnCooldown(player, lightTrade)) {
            lightStatusPane.addItem(GuiItem(locked))
        } else {
            lightStatusPane.addItem(GuiItem(available))
        }

        if (TradeManager.isOnCooldown(player, frameTrade)) {
            frameStatusPane.addItem(GuiItem(locked))
        } else {
            frameStatusPane.addItem(GuiItem(available))
        }

        if (TradeManager.isOnCooldown(player, globeTrade)) {
            globeStatusPane.addItem(GuiItem(locked))
        } else {
            globeStatusPane.addItem(GuiItem(available))
        }
    }

    companion object {
        val LIGHT_BLOCK_STACK = buildItem(Material.LIGHT) {
            displayName {
                text("20x Licht Blöcke", NamedTextColor.YELLOW)
            }

            buildLore {
                line {
                    append(Component.empty())
                }
                line {
                    text("Preis: ", NamedTextColor.GRAY)
                        .append(text("20x Redstone-Lampe und 5x Smaragd", NamedTextColor.WHITE))
                }
                line {
                    append(Component.empty())
                }
                line {
                    text(
                        "Eine magische und unsichtbare Lampe, mit Lichtstärke 15.",
                        NamedTextColor.GRAY
                    )
                }
                line {
                    text(
                        "Erzeugt von einem alten Zauberer, der in der Tiefe des Vulkans lebte.",
                        NamedTextColor.GRAY
                    )
                }
            }

            addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        }

        val FRAME_ITEM_STACK = buildItem(Material.ITEM_FRAME) {
            displayName {
                text("20x Unsichtbarer Item Rahmen", NamedTextColor.YELLOW)
            }

            buildLore {
                line {
                    append(Component.empty())
                }

                line {
                    text("Preis: ", NamedTextColor.GRAY)
                        .append(text("20x Item-Rahmen und 5x Smaragd", NamedTextColor.WHITE))
                }

                line {
                    append(Component.empty())
                }

                line {
                    text(
                        "Ein fast unsichtbarer Rahmen für geheime Präsentationen.",
                        NamedTextColor.GRAY
                    )
                }

                line {
                    text(
                        "Wurde mit Illusionsmagie von alten Bibliothekaren verzaubert.",
                        NamedTextColor.GRAY
                    )
                }
            }

            addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        }


        val GLOBE_BANNER_STACK = buildItem(Material.GLOBE_BANNER_PATTERN) {
            displayName {
                text("1x Globe Banner Vorlage", NamedTextColor.YELLOW)
            }

            buildLore {
                line {
                    append(Component.empty())
                }

                line {
                    text("Preis: ", NamedTextColor.GRAY)
                        .append(text("20x Papier und 20x Smaragd", NamedTextColor.WHITE))
                }

                line {
                    append(Component.empty())
                }

                line {
                    text(
                        "Ein uraltes Symbol, das einst nur Königen vorbehalten war.",
                        NamedTextColor.GRAY
                    )
                }

                line {
                    text(
                        "Gefunden in den Ruinen einer vergessenen Zivilisation.",
                        NamedTextColor.GRAY
                    )
                }
            }

            addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
        }
    }
}
