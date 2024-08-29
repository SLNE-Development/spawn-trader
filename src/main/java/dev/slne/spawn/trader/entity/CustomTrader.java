package dev.slne.spawn.trader.entity;

import org.bukkit.World;

public interface CustomTrader {
    String entityTag();
    World world();

    void spawn(double x, double y, double z);
    void clear();
}
