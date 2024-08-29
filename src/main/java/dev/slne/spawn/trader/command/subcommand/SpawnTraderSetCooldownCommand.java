package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.LongArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.UserManager;
import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;
import dev.slne.spawn.trader.user.User;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SpawnTraderSetCooldownCommand extends CommandAPICommand {
    private final LocalDateTime time = LocalDateTime.now();
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

            switch (args.getUnchecked("trade").toString()){
                case "invisible-item-frame" -> {
                    trade = new FrameTrade();
                }

                case "light-block" -> {
                    trade = new LightTrade();
                }

                default -> {
                    trade = null;
                }
            }

            if(target == null){
                user.sendMessage("<red>Der Spieler wurde nicht gefunden.");
                return;
            }

            if(trade == null){
                user.sendMessage("<red>Der Trade wurde nicht gefunden.");
                return;
            }

            storage.set(target.getUniqueId() + "." + trade.id() + ".cooldown", System.currentTimeMillis() + amount);
            storage.set(target.getUniqueId() + "." + trade.id() + ".last-updated", time.format(formatter));

            SpawnTrader.instance().saveStorage();
        });
    }
}
