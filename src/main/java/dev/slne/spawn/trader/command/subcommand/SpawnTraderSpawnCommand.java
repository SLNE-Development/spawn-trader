package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.entity.impl.TraderBukkitEntity;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.manager.UserManager;
import dev.slne.spawn.trader.user.User;

public class SpawnTraderSpawnCommand extends CommandAPICommand {
    private final SpawnTrader instance = SpawnTrader.instance();
    private final TraderNPC traderNPC = this.instance.traderNPC();
    private final TraderBukkitEntity traderBukkitEntity = this.instance.traderBukkitEntity();

    public SpawnTraderSpawnCommand(String name) {
        super(name);

        executesPlayer((player, args) -> {
            User user = UserManager.instance().getUser(player.getUniqueId());

            if (instance.citizens()) {
                traderNPC.spawn(player.getX(), player.getY(), player.getZ());

                user.sendMessage("Der Citizens-Trade-NPC wurde gespawnt.");
            } else {
                traderBukkitEntity.spawn(player.getX(), player.getY(), player.getZ());

                user.sendMessage("Der Bukkit-Trade-NPC wurde gespawnt.");
            }
        });
    }
}
