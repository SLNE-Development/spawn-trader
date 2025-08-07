package dev.slne.spawn.trader.command.subcommand

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.spawn.trader.entity.impl.TraderNPC
import dev.slne.spawn.trader.util.TraderPermissionRegistry
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

class SpawnTraderSpawnCommand(commandName: String) : CommandAPICommand(commandName) {
    init {
        withPermission(TraderPermissionRegistry.COMMAND_SPAWN)
        playerExecutor { player, _ ->
            if (TraderNPC.spawn(player.location, "spawn-trader")) {
                player.sendText {
                    appendPrefix()
                    success("Der Trader wurde gespawned.")
                }
            } else {
                player.sendText {
                    appendPrefix()
                    error("Der Trader existiert bereits, oder ein surf-npc-Fehler ist aufgetreten.")
                }
            }
        }
    }
}
