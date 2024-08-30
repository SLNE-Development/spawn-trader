package dev.slne.spawn.trader.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.slne.spawn.trader.command.subcommand.SpawnTraderClearCommand;
import dev.slne.spawn.trader.command.subcommand.SpawnTraderReloadCommand;
import dev.slne.spawn.trader.command.subcommand.SpawnTraderSetCooldownCommand;
import dev.slne.spawn.trader.command.subcommand.SpawnTraderSpawnCommand;

public class SpawnTraderCommand extends CommandAPICommand {

    public SpawnTraderCommand(String name) {
        super(name);

        this.withPermission("spawn-trader.spawntrader.command");

        this.withSubcommand(new SpawnTraderSpawnCommand("spawn"));
        this.withSubcommand(new SpawnTraderReloadCommand("reload"));
        this.withSubcommand(new SpawnTraderClearCommand("clear"));
        this.withSubcommand(new SpawnTraderSetCooldownCommand("setcooldown"));
    }
}
