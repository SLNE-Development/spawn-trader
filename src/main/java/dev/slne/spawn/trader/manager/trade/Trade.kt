package dev.slne.spawn.trader.manager.object;

import java.util.List;

import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.GlobeTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;
import it.unimi.dsi.fastutil.objects.ObjectList;
import org.bukkit.inventory.ItemStack;

/**
 * The interface Trade.
 */
public interface Trade {

  /**
   * Requirements list.
   *
   * @return the list
   */
  ObjectList<ItemStack> requirements();

  /**
   * Rewards list.
   *
   * @return the list
   */
  ObjectList<ItemStack> rewards();

  /**
   * Id integer.
   *
   * @return the integer
   */
  Integer id();

  /**
   * Name String.
   *
   * @return the name
   */
  String name();

  /**
   * Reward message string.
   *
   * @return the string
   */
  String rewardMessage();


  /**
   * Get the cooldown for this trade.
   *
   * @return the cooldown in milliseconds
   */
  Long cooldown();


  static Trade getTrade(String name){
    switch (name){
      case "invisible-item-frame" -> {
        return new FrameTrade();
      }
      case "light-block" -> {
        return new LightTrade();
      }
      case "globe-banner-pattern" -> {
        return new GlobeTrade();
      }
      default -> {
        return null;
      }
    }
  }
}
