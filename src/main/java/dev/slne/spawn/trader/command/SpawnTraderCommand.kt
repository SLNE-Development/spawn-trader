package dev.slne.spawn.trader.command

import dev.jorel.commandapi.CommandAPICommand
import dev.slne.spawn.trader.command.subcommand.SpawnTraderClearCommand
import dev.slne.spawn.trader.command.subcommand.SpawnTraderSetCooldownCommand
import dev.slne.spawn.trader.command.subcommand.SpawnTraderSpawnCommand
import dev.slne.spawn.trader.util.TraderPermissionRegistry

class SpawnTraderCommand(name: String) : CommandAPICommand(name) {
    init {
        withPermission(TraderPermissionRegistry.COMMAND)
        withSubcommand(SpawnTraderSpawnCommand("spawn"))
        withSubcommand(SpawnTraderClearCommand("clear"))
        withSubcommand(SpawnTraderSetCooldownCommand("updateCooldown"))
    }
}
