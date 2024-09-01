package dev.slne.spawn.trader.entity;

import org.bukkit.World;

/**
 * The interface Custom trader.
 */
public interface CustomTrader {

  /**
   * Entity tag string.
   *
   * @return the string
   */
  String entityTag();

  /**
   * World world.
   *
   * @return the world
   */
  World world();

  /**
   * Spawn.
   *
   * @param x the x
   * @param y the y
   * @param z the z
   */
  void spawn(double x, double y, double z);

  /**
   * Clear.
   */
  void clear();
}
