package dev.slne.spawn.trader.command;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.entity.impl.TraderBukkitEntity;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.manager.UserManager;
import dev.slne.spawn.trader.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnTraderCommandOLD implements CommandExecutor, TabCompleter {
    private final SpawnTrader instance = SpawnTrader.instance();
    private final TraderNPC traderNPC = this.instance.traderNPC();
    private final TraderBukkitEntity traderBukkitEntity = this.instance.traderBukkitEntity();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            User user = UserManager.instance().getUser(player.getUniqueId());
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                    instance.citizens(SpawnTrader.instance().getConfig().getBoolean("citizens"));
                    instance.tradeCooldown(SpawnTrader.instance().getConfig().getLong("trade-cooldown"));

                    instance.saveStorage();

                    user.sendMessage("Reload erfolgreich.");
                } else if (args[0].equalsIgnoreCase("spawn")) {
                    if (instance.citizens()) {
                        traderNPC.spawn(player.getX(), player.getY(), player.getZ());

                        user.sendMessage("Der Citizens-Trade-NPC wurde gespawnt.");
                    } else {
                        traderBukkitEntity.spawn(player.getX(), player.getY(), player.getZ());

                        user.sendMessage("Der Bukkit-Trade-NPC wurde gespawnt.");
                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    if (instance.citizens()) {
                        traderNPC.clear();

                        user.sendMessage("Die Citizens-Trade-NPCs wurden entfernt.");
                    } else {
                        traderBukkitEntity.clear();
                        user.sendMessage("Die Bukkit-Trade-NPCs wurden entfernt.");
                    }
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], List.of("reload", "spawn", "clear"), new ArrayList<>());
        }
        Collections.sort(completions);
        return completions;
    }
}
