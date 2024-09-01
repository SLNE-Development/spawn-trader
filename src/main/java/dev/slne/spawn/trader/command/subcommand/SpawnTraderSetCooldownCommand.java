package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.LongArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.UserManager;
import dev.slne.spawn.trader.manager.object.CooldownPair;
import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;
import dev.slne.spawn.trader.user.User;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The type Spawn trader set cooldown command.
 */
public class SpawnTraderSetCooldownCommand extends CommandAPICommand {

  private final TradeManager tradeManager = SpawnTrader.instance().tradeManager();

  /**
   * Instantiates a new Spawn trader set cooldown command.
   *
   * @param name the name
   */
  public SpawnTraderSetCooldownCommand(String name) {
    super(name);

    withArguments(new StringArgument("target"));
    withArguments(new StringArgument("trade"));
    withArguments(new LongArgument("amount"));

    executesPlayer((player, args) -> {
      final Player target = Bukkit.getPlayer(
          args.getOrDefaultUnchecked("target", player.getName()));
      final User user = UserManager.instance().getUser(player.getUniqueId());
      final long amount = args.getOrDefaultUnchecked("amount",
          SpawnTrader.instance().tradeCooldown());

      Trade trade;
      final String tradeName = args.getUnchecked("trade");

      if (tradeName == null) {
        user.sendMessage("<red>Der Trade wurde nicht gefunden.");
        return;
      }

      switch (tradeName) {
        case "invisible-item-frame" -> trade = new FrameTrade();
        case "light-block" -> trade = new LightTrade();
        default -> trade = null;
      }

      if (target == null) {
        user.sendMessage("<red>Der Spieler wurde nicht gefunden.");
        return;
      }

      if (trade == null) {
        user.sendMessage("<red>Der Trade wurde nicht gefunden.");
        return;
      }

      final UUID uuid = target.getUniqueId();
      CooldownPair cooldownPair = tradeManager.cooldownStorage()
          .getOrDefault(uuid, new CooldownPair(0L, 0L));

      final long currentTime = System.currentTimeMillis();
      final long cooldownEndTime = currentTime + amount;

      if (trade.id() == 0) {
        cooldownPair = new CooldownPair(cooldownEndTime, cooldownPair.getTrade1());
      } else if (trade.id() == 1) {
        cooldownPair = new CooldownPair(cooldownPair.getTrade0(), cooldownEndTime);
      }

      tradeManager.cooldownStorage().put(uuid, cooldownPair);
      user.sendMessage("Der Cooldown für den Trade wurde erfolgreich neu gesetzt.");
    });
  }
}
