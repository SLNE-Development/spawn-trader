package dev.slne.spawn.trader.entity;

import org.bukkit.Location;

/**
 * The interface Custom trader.
 */
public interface CustomTrader {
  /**
   * Spawn.
   *
   * @param x the x
   * @param y the y
   * @param z the z
   */
  void spawn(Location location, String name);

  /**
   * Clear.
   */
  void clear(String name);
}
