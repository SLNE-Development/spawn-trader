package dev.slne.spawn.trader.manager.object.impl;

import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.util.ItemBuilder;

import it.unimi.dsi.fastutil.objects.ObjectList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * The type Light trade.
 */
public class LightTrade implements Trade {

  @Override
  public ObjectList<ItemStack> requirements() {
    return ObjectList.of(new ItemBuilder(Material.EMERALD, 5).build(),
        new ItemBuilder(Material.REDSTONE_LAMP, 20).build());
  }

  @Override
  public ObjectList<ItemStack> rewards() {
    return ObjectList.of(new ItemBuilder(Material.LIGHT, 20).build());
  }

  @Override
  public Integer id() {
    return 1;
  }

  @Override
  public String name() {
    return "light-block";
  }

  @Override
  public String rewardMessage() {
    return "Danke f\u00FCr den Einkauf von 20x Licht-Bl\u00F6cken!";
  }

  @Override
  public Long cooldown() {
    return 75600000L;
  }
}
