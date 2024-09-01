package dev.slne.spawn.trader.manager.object.impl;

import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.util.ItemBuilder;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * The type Light trade.
 */
public class LightTrade implements Trade {

  @Override
  public List<ItemStack> requirements() {
    return List.of(new ItemBuilder(Material.EMERALD, 5).build(),
        new ItemBuilder(Material.REDSTONE_LAMP, 20).build());
  }

  @Override
  public List<ItemStack> rewards() {
    return List.of(new ItemBuilder(Material.LIGHT, 20).build());
  }

  @Override
  public Integer id() {
    return 1;
  }

  @Override
  public String rewardMessage() {
    return "Danke für den Einkauf von 20x Licht-Blöcken!";
  }
}
