package dev.slne.spawn.trader.command.subcommand;

import dev.jorel.commandapi.CommandAPICommand;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.entity.impl.TraderBukkitEntity;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.manager.UserManager;
import dev.slne.spawn.trader.user.User;

public class SpawnTraderClearCommand extends CommandAPICommand {
    private final SpawnTrader instance = SpawnTrader.instance();
    private final TraderNPC traderNPC = this.instance.traderNPC();
    private final TraderBukkitEntity traderBukkitEntity = this.instance.traderBukkitEntity();

    public SpawnTraderClearCommand(String name) {
        super(name);

        executesPlayer((player, args) -> {
            User user = UserManager.instance().getUser(player.getUniqueId());

            if (instance.citizens()) {
                traderNPC.clear();

                user.sendMessage("Die Citizens-Trade-NPCs wurden entfernt.");
            } else {
                traderBukkitEntity.clear();
                user.sendMessage("Die Bukkit-Trade-NPCs wurden entfernt.");
            }
        });
    }
}
