package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import net.kyori.adventure.text.Component;

/**
 * The type Spawn trader spawn command.
 */
public class SpawnTraderSpawnCommand extends CommandAPICommand {

  private final SpawnTrader instance = SpawnTrader.instance();
  private final TraderNPC traderNPC = this.instance.traderNPC();
  private final TraderBukkitEntity traderBukkitEntity = this.instance.traderBukkitEntity();

  /**
   * Instantiates a new Spawn trader spawn command.
   *
   * @param name the name
   */
  public SpawnTraderSpawnCommand(String name) {
    super(name);

    executesPlayer((player, args) -> {
      if (instance.citizens()) {
        traderNPC.spawn(player.getX(), player.getY(), player.getZ());

        player.sendMessage(SpawnTrader.prefix().append(Component.text("Die Citizens-Trade-NPCs wurden gespawnt.")));

      } else {
        traderBukkitEntity.spawn(player.getX(), player.getY(), player.getZ());

        player.sendMessage(SpawnTrader.prefix().append(Component.text("Die Bukkit-Trade-NPCs wurden gespawnt.")));
      }
    });
  }
}
