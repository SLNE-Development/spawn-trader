package dev.slne.spawn.trader.entity;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.entity.impl.TraderBukkitEntity;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.user.User;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityInteractListener implements Listener {
    @EventHandler
    public void onEvent(PlayerInteractAtEntityEvent event){
        Entity entity = event.getRightClicked();
        User user = User.user(event.getPlayer());

        if(entity.getScoreboardTags().contains(new TraderBukkitEntity().ENTITY_TAG())){
            SpawnTrader.instance().spawnTraderInterface().open(user.player());
            return;
        }

        if(entity.getScoreboardTags().contains(new TraderNPC().ENTITY_TAG())){
            SpawnTrader.instance().spawnTraderInterface().open(user.player());
            return;
        }
    }
}
