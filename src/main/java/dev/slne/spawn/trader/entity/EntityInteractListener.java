package dev.slne.spawn.trader.entity;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.gui.SpawnTraderGUI;
import lol.pyr.znpcsplus.api.event.NpcInteractEvent;
import lol.pyr.znpcsplus.api.npc.NpcEntry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityInteractListener implements Listener {

  @EventHandler
  public void onNpcInteract(NpcInteractEvent event) {
    NpcEntry entry = event.getEntry();
    Player player = event.getPlayer();

    if(entry.getId().equalsIgnoreCase("spawn-trader")) {
      new BukkitRunnable() {
        @Override
        public void run() {
          new SpawnTraderGUI(player).show(player);
        }
      }.runTaskLater(SpawnTrader.instance(), 0L);
    }
  }
}
