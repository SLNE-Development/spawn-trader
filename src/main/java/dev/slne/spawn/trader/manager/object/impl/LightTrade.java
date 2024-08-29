package dev.slne.spawn.trader.manager.object.impl;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LightTrade implements Trade {
    @Override
    public List<ItemStack> requirements() {
        return List.of(new ItemBuilder(Material.EMERALD, 5).build(), new ItemBuilder(Material.REDSTONE_LAMP, 20).build());
    }

    @Override
    public List<ItemStack> rewards() {
        return List.of(new ItemBuilder(Material.LIGHT, 20).build());
    }

    @Override
    public String id() {
        return "light_block";
    }

    @Override
    public Component rewardMessage() {
        return SpawnTrader.deserialize("");
    }
}
