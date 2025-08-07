package dev.slne.spawn.trader.command.subcommand

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.CustomArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.kotlindsl.longArgument
import dev.jorel.commandapi.kotlindsl.playerArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.spawn.trader.manager.trade.Trade
import dev.slne.spawn.trader.manager.trade.impl.FrameTrade
import dev.slne.spawn.trader.manager.trade.impl.GlobeTrade
import dev.slne.spawn.trader.manager.trade.impl.LightTrade
import dev.slne.spawn.trader.plugin
import dev.slne.spawn.trader.util.TraderPermissionRegistry
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.util.mutableObjectListOf
import org.bukkit.command.CommandSender

class SpawnTraderSetCooldownCommand(name: String) : CommandAPICommand(name) {
    private val availableTrades = mutableObjectListOf<String>()

    init {
        withPermission(TraderPermissionRegistry.COMMAND_SET_COOLDOWN)
        playerArgument("target")
        withArguments(
            tradeArgument().replaceSuggestions(
                ArgumentSuggestions.strings<CommandSender?>(
                    "light-block",
                    "invisible-item-frame",
                    "globe-banner-pattern"
                )
            )
        )
        longArgument("amount")

        availableTrades.add(LightTrade().name())
        availableTrades.add(FrameTrade().name())
        availableTrades.add(GlobeTrade().name())

        playerExecutor { player, args ->
            val target = args.getOrDefaultUnchecked("target", player)
            val amount = args.getUnchecked<Long>("amount") ?: return@playerExecutor
            val trade = args.getUnchecked<Trade>("trade") ?: return@playerExecutor

            val currentTime = System.currentTimeMillis()
            val cooldownEndTime = currentTime + amount

            plugin.saveCooldown(target, trade, cooldownEndTime)
            player.sendText {
                appendPrefix()
                success("Der Cooldown für den Trade ")
                variableValue(trade.name())
                success(" wurde für den Spieler ")
                variableValue(target.name)
                success(" auf ")
                variableValue(amount.toString())
                success(" Millisekunden gesetzt.")
            }
        }
    }

    private fun tradeArgument(): Argument<Trade> {
        return CustomArgument(
            StringArgument("trade")
        ) { info: CustomArgument.CustomArgumentInfo<String> ->
            val trade = Trade.getTrade(info.input())
                ?: throw CustomArgument.CustomArgumentException.fromAdventureComponent(
                    buildText {
                        appendPrefix()
                        error("Der Trade wurde nicht gefunden.")
                    }
                )

            trade
        }.replaceSuggestions(
            ArgumentSuggestions.strings {
                availableTrades.toTypedArray()
            }
        )
    }
}
