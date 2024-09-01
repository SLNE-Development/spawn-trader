package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.UserManager;
import dev.slne.spawn.trader.user.User;

/**
 * The type Spawn trader reload command.
 */
public class SpawnTraderReloadCommand extends CommandAPICommand {

  private final SpawnTrader instance = SpawnTrader.instance();

  /**
   * Instantiates a new Spawn trader reload command.
   *
   * @param name the name
   */
  public SpawnTraderReloadCommand(String name) {
    super(name);

    executesPlayer((player, args) -> {
      final User user = UserManager.instance().getUser(player.getUniqueId());

      instance.citizens(SpawnTrader.instance().getConfig().getBoolean("citizens"));
      instance.tradeCooldown(SpawnTrader.instance().getConfig().getLong("trade-cooldown"));

      instance.saveStorage();

      user.sendMessage("Reload erfolgreich.");
    });

    withAliases("rl");
  }
}
