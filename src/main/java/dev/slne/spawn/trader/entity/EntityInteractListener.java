package dev.slne.spawn.trader.entity;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.gui.SpawnTraderGUI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityInteractListener implements Listener {
    @EventHandler
    public void onEvent(PlayerInteractAtEntityEvent event){
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
        SpawnTrader instance = SpawnTrader.instance();

        if(entity.getScoreboardTags().contains(instance.traderBukkitEntity().entityTag())){
            new SpawnTraderGUI(player).show(player);
            return;
        }

        if(entity.getScoreboardTags().contains(instance.traderNPC().entityTag())){
            new SpawnTraderGUI(player).show(player);
        }
    }
}
