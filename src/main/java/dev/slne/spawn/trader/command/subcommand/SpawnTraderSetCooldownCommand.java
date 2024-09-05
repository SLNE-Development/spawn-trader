package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.LongArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.object.CooldownPair;
import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The type Spawn trader set cooldown command.
 */
public class SpawnTraderSetCooldownCommand extends CommandAPICommand {

  private final TradeManager tradeManager = SpawnTrader.instance().tradeManager();
  private final ObjectList<String> availableTrades = new ObjectArrayList<>();

  /**
   * Instantiates a new Spawn trader set cooldown command.
   *
   * @param name the name
   */
  public SpawnTraderSetCooldownCommand(String name) {
    super(name);

    withArguments(new PlayerArgument("target"));
    withArguments(tradeArgument().replaceSuggestions(
        ArgumentSuggestions.strings("light-block", "invisible-item-frame")));
    withArguments(new LongArgument("amount"));

    executesPlayer((player, args) -> {
      final Player target = Bukkit.getPlayer(
          args.getOrDefaultUnchecked("target", player.getName()));
      final long amount = args.getOrDefaultUnchecked("amount",
          SpawnTrader.instance().tradeCooldown());

      Trade trade = args.getUnchecked("trade");

      availableTrades.add(new LightTrade().name());
      availableTrades.add(new FrameTrade().name());

      if (!availableTrades.contains(trade.name())) {
        throw CommandAPI.failWithString("Der Trade wurde nicht gefunden!");
      }

      if (target == null) {
        player.sendMessage(SpawnTrader.prefix()
            .append(Component.text("Der Spieler wurde nicht gefunden.").color(NamedTextColor.RED)));
        return;
      }

      final UUID uuid = target.getUniqueId();
      CooldownPair cooldownPair = tradeManager.cooldownStorage()
          .getOrDefault(uuid, new CooldownPair(0L, 0L));

      final long currentTime = System.currentTimeMillis();
      final long cooldownEndTime = currentTime + amount;

      if (trade instanceof FrameTrade) {
        cooldownPair = new CooldownPair(cooldownEndTime, cooldownPair.getTrade1());
      } else if (trade instanceof LightTrade) {
        cooldownPair = new CooldownPair(cooldownPair.getTrade0(), cooldownEndTime);
      }

      tradeManager.cooldownStorage().put(uuid, cooldownPair);
      player.sendMessage(SpawnTrader.prefix().append(
          Component.text("Der Cooldown f\u00FCr den Trade wurde erfolgreich neu gesetzt.")));
    });
  }

  private Argument<Trade> tradeArgument() {
    return new CustomArgument<>(new StringArgument("trade"), info -> {
      Trade trade = Trade.getTrade(info.input());

      if (trade == null) {
        throw CustomArgument.CustomArgumentException.fromAdventureComponent(
            Component.text("Der Trade wurde nicht gefunden.").color(NamedTextColor.RED));
      } else {
        return trade;
      }
    });
  }
}
