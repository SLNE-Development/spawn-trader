package dev.slne.spawn.trader.entity;

import org.bukkit.Location;

/**
 * The interface Custom trader.
 */
public interface CustomTrader {
  /**
   * Spawn.
   *
   * @param location the location
   */
  boolean spawn(Location location, String name);

  /**
   * Clear.
   */
  boolean clear(String name);
}
