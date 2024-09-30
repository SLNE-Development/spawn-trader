package dev.slne.spawn.trader.manager.object.impl;

import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.util.ItemBuilder;
import it.unimi.dsi.fastutil.objects.ObjectList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GlobeTrade implements Trade {
    @Override
    public ObjectList<ItemStack> requirements() {
        return ObjectList.of(new ItemBuilder(Material.EMERALD, 5).build(), new ItemBuilder(Material.PAPER, 20).build());
    }

    @Override
    public ObjectList<ItemStack> rewards() {
        return ObjectList.of(new ItemBuilder(Material.GLOBE_BANNER_PATTERN).build());
    }

    @Override
    public Integer id() {
        return 2;
    }

    @Override
    public String name() {
        return "globe-banner-pattern";
    }

    @Override
    public String rewardMessage() {
        return "Danke f\u00FCr den Einkauf von einer Globe Banner Vorlage!";
    }
}
