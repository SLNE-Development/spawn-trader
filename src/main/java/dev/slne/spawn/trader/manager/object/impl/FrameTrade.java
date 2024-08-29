package dev.slne.spawn.trader.manager.object.impl;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.object.Trade;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FrameTrade implements Trade {
    @Override
    public List<ItemStack> requirements() {
        return List.of();
    }

    @Override
    public List<ItemStack> rewards() {
        return List.of();
    }

    @Override
    public String id() {
        return "invisible_item_frame";
    }

    @Override
    public Component rewardMessage() {
        return SpawnTrader.deserialize("");
    }
}
