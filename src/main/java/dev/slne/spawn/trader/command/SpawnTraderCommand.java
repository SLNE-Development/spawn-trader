package dev.slne.spawn.trader.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.slne.spawn.trader.command.subcommand.SpawnTraderClearCommand;
import dev.slne.spawn.trader.command.subcommand.SpawnTraderReloadCommand;
import dev.slne.spawn.trader.command.subcommand.SpawnTraderSpawnCommand;

public class SpawnTraderCommand extends CommandAPICommand {

    public SpawnTraderCommand(String name) {
        super(name);

        withPermission("spawn-trader.spawntrader.command");

        withSubcommand(new SpawnTraderSpawnCommand("spawn"));
        withSubcommand(new SpawnTraderReloadCommand("reload"));
        withSubcommand(new SpawnTraderClearCommand("clear"));
    }
}
