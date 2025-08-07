package dev.slne.spawn.trader.manager

import dev.slne.spawn.trader.manager.trade.Trade
import dev.slne.spawn.trader.plugin
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

object TradeManager {
    fun setCooldown(player: Player, trade: Trade) {
        val currentTime = System.currentTimeMillis()
        val cooldownEndTime: Long = currentTime + trade.cooldown()

        this.saveCooldown(player, trade, cooldownEndTime)
    }

    fun isOnCooldown(player: Player, trade: Trade): Boolean {
        return this.getCooldown(player, trade) >= System.currentTimeMillis()
    }

    fun removeRequirements(player: Player, trade: Trade) {
        if (this.hasEnoughRequirements(player, trade)) {
            trade.requirements()
                .forEach(Consumer { item: ItemStack? -> this.removeItem(player, item!!) })
        } else {
            player.sendText {
                appendPrefix()
                error("Du hast nicht genügend Rohstoffe für diesen Handel!")
            }
        }
    }

    fun hasEnoughRequirements(player: Player, trade: Trade): Boolean {
        for (requirement in trade.requirements()) {
            if (!this.hasItem(player, requirement)) {
                return false
            }
        }

        return true
    }

    fun giveReward(player: Player, trade: Trade) {
        player.sendText {
            appendPrefix()
            success(trade.rewardMessage())
        }

        for (reward in trade.rewards()) {
            val remainingItems: HashMap<Int, ItemStack> = player.inventory.addItem(reward)

            for (stack in remainingItems.values) {
                player.world.dropItem(player.location, stack).owner = player.uniqueId
            }
        }
    }

    private fun hasItem(player: Player, item: ItemStack): Boolean {
        var found = 0

        for (stack in player.inventory.contents) {
            if (stack != null) {
                if (stack.isSimilar(item)) {
                    found += stack.amount

                    if (found >= item.amount) {
                        return true
                    }
                }
            }
        }

        return false
    }

    private fun removeItem(player: Player, item: ItemStack) {
        var amountToRemove = item.amount

        for (stack in player.inventory.contents) {
            if (stack != null && stack.isSimilar(item)) {
                val stackAmount: Int = stack.amount

                if (stackAmount > amountToRemove) {
                    stack.amount = stackAmount - amountToRemove
                    break
                } else {
                    amountToRemove -= stackAmount
                    stack.amount = 0
                }

                if (amountToRemove <= 0) {
                    break
                }
            }
        }
    }

    fun buy(player: Player, trade: Trade) {
        if (this.isOnCooldown(player, trade)) {
            player.sendActionBar(
                Component.text("ʙɪᴛᴛᴇ ᴋᴏᴍᴍᴇ ɪɴ ", NamedTextColor.RED)
                    .append(
                        Component.text(
                            plugin.getFormattedCooldown(player, trade),
                            NamedTextColor.GOLD
                        )
                    )
                    .append(
                        Component.text(
                            " ᴡɪᴇᴅᴇʀ, ᴀᴋᴛᴜᴇʟʟ ʜᴀʙᴇ ɪᴄʜ ɴɪᴄʜᴛѕ ꜰüʀ ᴅɪᴄʜ.",
                            NamedTextColor.RED
                        )
                    ).decorate(TextDecoration.BOLD)
            )
            player.playSound(
                Sound.sound(
                    org.bukkit.Sound.ENTITY_VILLAGER_NO,
                    Sound.Source.PLAYER,
                    1f,
                    1f
                ), Sound.Emitter.self()
            )
            player.sendText {
                appendPrefix()
                error("Bitte komme in ")
                variableValue(plugin.getFormattedCooldown(player, trade))
                error(" wieder, aktuell habe ich nichts für dich.")
            }
            return
        }

        if (this.hasEnoughRequirements(player, trade)) {
            this.giveReward(player, trade)
            this.removeRequirements(player, trade)
            this.setCooldown(player, trade)

            player.playSound(
                Sound.sound(
                    org.bukkit.Sound.ENTITY_WANDERING_TRADER_YES,
                    Sound.Source.PLAYER,
                    1f,
                    1f
                ), Sound.Emitter.self()
            )
        } else {
            player.sendActionBar(
                Component.text(
                    "ᴅᴜ ʜᴀѕᴛ ɴɪᴄʜᴛ ɢᴇɴüɢᴇɴᴅ ʀᴏʜѕᴛᴏꜰꜰᴇ.",
                    NamedTextColor.RED
                ).decorate(TextDecoration.BOLD)
            )
            player.playSound(
                Sound.sound(
                    org.bukkit.Sound.BLOCK_ANVIL_DESTROY,
                    Sound.Source.PLAYER,
                    1f,
                    1f
                ), Sound.Emitter.self()
            )
            player.sendText {
                appendPrefix()
                error("Du hast nicht genügend Rohstoffe.")
            }
        }
    }

    fun saveCooldown(player: Player, trade: Trade, time: Long) {
        plugin.saveCooldown(player, trade, time)
    }

    fun getCooldown(player: Player, trade: Trade): Long {
        return plugin.getCooldown(player, trade)
    }
}
