package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import net.kyori.adventure.text.Component;

/**
 * The type Spawn trader clear command.
 */
public class SpawnTraderClearCommand extends CommandAPICommand {

  private final SpawnTrader instance = SpawnTrader.instance();
  private final TraderNPC traderNPC = this.instance.traderNPC();
  private final TraderBukkitEntity traderBukkitEntity = this.instance.traderBukkitEntity();

  /**
   * Instantiates a new Spawn trader clear command.
   *
   * @param name the name
   */
  public SpawnTraderClearCommand(String name) {
    super(name);

    executesPlayer((player, args) -> {

      if (instance.citizens()) {
        traderNPC.clear();

        player.sendMessage(SpawnTrader.prefix().append(Component.text("Die Citizens-Trade-NPCs wurden entfernt.")));
      } else {
        traderBukkitEntity.clear();
        player.sendMessage(SpawnTrader.prefix().append(Component.text("Die Bukkit-Trade-NPCs wurden entfernt.")));
      }
    });
  }
}
