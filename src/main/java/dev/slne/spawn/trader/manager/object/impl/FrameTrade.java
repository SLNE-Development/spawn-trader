package dev.slne.spawn.trader.manager.object.impl;

import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.util.ItemBuilder;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * The type Frame trade.
 */
public class FrameTrade implements Trade {


  @Override
  public List<ItemStack> requirements() {
    return List.of(new ItemBuilder(Material.EMERALD, 5).build(),
        new ItemBuilder(Material.ITEM_FRAME, 20).build());
  }

  @Override
  public List<ItemStack> rewards() {
    // per command: invisible item frame x20
    return List.of();
  }

  @Override
  public Integer id() {
    return 0;
  }

  @Override
  public String name() {
    return "invisible-item-frame";
  }


  @Override
  public String rewardMessage() {
    return "Danke für den Einkauf von 20x Item-Rahmen!";
  }
}
