package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * The type Spawn trader clear command.
 */
public class SpawnTraderClearCommand extends CommandAPICommand {

  private final SpawnTrader instance = SpawnTrader.instance();
  private final TraderNPC traderNPC = this.instance.traderNPC();

  /**
   * Instantiates a new Spawn trader clear command.
   *
   * @param name the name
   */
  public SpawnTraderClearCommand(String name) {
    super(name);

    executesPlayer((player, args) -> {

      if(traderNPC.clear("spawn-trader")) {
        player.sendMessage(SpawnTrader.prefix().append(Component.text("Der Trader wurde entfernt.").color(NamedTextColor.GREEN)));
      } else {
        player.sendMessage(SpawnTrader.prefix().append(Component.text("Der Trader existiert nicht, oder ein ZNPCsPlus Fehler ist aufgetreten.").color(NamedTextColor.RED)));
      }
    });
  }
}
