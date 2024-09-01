package dev.slne.spawn.trader.entity;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.gui.SpawnTraderGUI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * The type Entity interact listener.
 */
public class EntityInteractListener implements Listener {

  /**
   * On event.
   *
   * @param event the event
   */
  @EventHandler
  public void onEvent(PlayerInteractAtEntityEvent event) {
    final Entity entity = event.getRightClicked();
    final Player player = event.getPlayer();
    final SpawnTrader instance = SpawnTrader.instance();

    if (entity.getScoreboardTags().contains(instance.traderBukkitEntity().entityTag())) {
      new SpawnTraderGUI(player).show(player);
      
      return;
    }

    if (entity.getScoreboardTags().contains(instance.traderNPC().entityTag())) {
      new SpawnTraderGUI(player).show(player);
    }
  }
}
