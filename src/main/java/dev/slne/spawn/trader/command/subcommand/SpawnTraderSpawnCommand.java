package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * The type Spawn trader spawn command.
 */
public class SpawnTraderSpawnCommand extends CommandAPICommand {
  private final SpawnTrader instance = SpawnTrader.instance();
  private final TraderNPC traderNPC = this.instance.traderNPC();

  public SpawnTraderSpawnCommand(String commandName) {
    super(commandName);

    executesPlayer((player, args) -> {
      if(traderNPC.spawn(player.getLocation(), "spawn-trader")) {
        player.sendMessage(SpawnTrader.prefix().append(Component.text("Der Trader wurde gespawned.").color(NamedTextColor.GREEN)));
      } else  {
        player.sendMessage(SpawnTrader.prefix().append(Component.text("Der Trader existiert bereits, oder ein ZNPCsPlus-Fehler ist aufgetreten.").color(NamedTextColor.RED)));
      }
    });
  }
}
