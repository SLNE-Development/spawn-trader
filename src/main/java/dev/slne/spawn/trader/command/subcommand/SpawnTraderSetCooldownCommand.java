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
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class SpawnTraderSetCooldownCommand extends CommandAPICommand {
    private final LocalDateTime time = LocalDateTime.now();
    private final TradeManager tradeManager = SpawnTrader.instance().tradeManager();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy-HH:mm:ss");
    private final FileConfiguration storage = SpawnTrader.instance().storage();

    public SpawnTraderSetCooldownCommand(String name) {
        super(name);

        withArguments(new StringArgument("target"));
        withArguments(new StringArgument("trade"));
        withArguments(new LongArgument("amount"));

        executesPlayer((player, args) -> {
            Player target = Bukkit.getPlayer(args.getOrDefaultUnchecked("target", player.getName()));
            User user = UserManager.instance().getUser(player.getUniqueId());
            Long amount = args.getOrDefaultUnchecked("amount", SpawnTrader.instance().tradeCooldown());
            Trade trade;

            switch (args.getUnchecked("trade").toString()) {
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

            UUID uuid = target.getUniqueId();
            CooldownPair cooldownPair = tradeManager.cooldownStorage().getOrDefault(uuid, new CooldownPair(0L, 0L));

            long currentTime = System.currentTimeMillis();
            long cooldownEndTime = currentTime + amount;

            if (trade.id() == 0) {
                cooldownPair = new CooldownPair(cooldownEndTime, cooldownPair.getTrade1());
            } else if (trade.id() == 1) {
                cooldownPair = new CooldownPair(cooldownPair.getTrade0(), cooldownEndTime);
            }

            tradeManager.cooldownStorage().put(uuid, cooldownPair);
            user.sendMessage("Der Cooldown fuer den Trade wurde erfolgreich neu gesetzt.");
        });
    }
}
