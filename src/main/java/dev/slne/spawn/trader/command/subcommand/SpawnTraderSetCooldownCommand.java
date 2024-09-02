package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.*;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.object.CooldownPair;
import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The type Spawn trader set cooldown command.
 */
public class SpawnTraderSetCooldownCommand extends CommandAPICommand {

  private final TradeManager tradeManager = SpawnTrader.instance().tradeManager();
  private final List<String> availableTradeNames = new ArrayList<>();
  private final List<String> availableTrades = new ArrayList<>();

  /**
   * Instantiates a new Spawn trader set cooldown command.
   *
   * @param name the name
   */
  public SpawnTraderSetCooldownCommand(String name) {
    super(name);

    withArguments(new StringArgument("target"));
    withArguments(tradeArgument());
    withArguments(new LongArgument("amount"));

    executesPlayer((player, args) -> {
      final Player target = Bukkit.getPlayer(
              args.getOrDefaultUnchecked("target", player.getName()));
      final long amount = args.getOrDefaultUnchecked("amount",
              SpawnTrader.instance().tradeCooldown());

      Trade trade = args.getUnchecked("trade");

      availableTrades.add(new LightTrade().name());
      availableTrades.add(new FrameTrade().name());
      availableTrades.forEach(someTrade -> availableTradeNames.add(Trade.getTrade(someTrade).name()));

      if (!availableTradeNames.contains(trade.name())) {
        throw CommandAPI.failWithString("Der Trade wurde nicht gefunden!");
      }

      if (!availableTrades.contains(trade.id())) {
        Bukkit.broadcast(Component.text(trade.toString()));
        throw CommandAPI.failWithString("Der Trade wurde nicht gefunden!");
      }

      if (target == null) {
      player.sendMessage(SpawnTrader.prefix().append(Component.text("Der Spieler wurde nicht gefunden.").color(NamedTextColor.RED)));
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
      player.sendMessage(SpawnTrader.prefix().append(Component.text("Der Cooldown für den Trade wurde erfolgreich neu gesetzt.")));
    });
  }

  private Argument<Trade> tradeArgument() {
    return new CustomArgument<>(new StringArgument("trade"), info -> {
      Trade trade = Trade.getTrade(info.input());

      if (trade == null) {
        throw CustomArgument.CustomArgumentException.fromAdventureComponent(Component.text("Der Trade wurde nicht gefunden.").color(NamedTextColor.RED));
      } else {
        return trade;
      }
    });
  }
}
