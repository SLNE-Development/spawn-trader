package dev.slne.spawn.trader.manager.object;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Trade {
    List<ItemStack> requirements();
    List<ItemStack> rewards();

    Integer id();
    String rewardMessage();
}
