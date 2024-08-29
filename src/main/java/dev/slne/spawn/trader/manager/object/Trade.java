package dev.slne.spawn.trader.manager.object;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Trade {
    List<ItemStack> requirements();
    List<ItemStack> rewards();

    String id();
    Component rewardMessage();
}
