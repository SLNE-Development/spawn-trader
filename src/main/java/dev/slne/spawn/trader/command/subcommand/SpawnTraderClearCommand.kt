package dev.slne.spawn.trader.command.subcommand

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.spawn.trader.plugin
import dev.slne.spawn.trader.util.TraderPermissionRegistry
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

class SpawnTraderClearCommand(name: String) : CommandAPICommand(name) {
    private val traderNPC = plugin.traderNPC

    init {
        withPermission(TraderPermissionRegistry.COMMAND_CLEAR)
        playerExecutor { player, _ ->
            if (traderNPC.clear("spawn-trader")) {
                player.sendText {
                    appendPrefix()
                    success("Der Trader wurde entfernt.")
                }
            } else {
                player.sendText {
                    appendPrefix()
                    error("Der Trader existiert nicht, oder ein surf-npc Fehler ist aufgetreten.")
                }
            }
        }

    }
}
