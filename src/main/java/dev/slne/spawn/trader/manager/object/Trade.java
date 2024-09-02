package dev.slne.spawn.trader.manager.object;

import java.util.List;

import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
  List<ItemStack> requirements();

  /**
   * Rewards list.
   *
   * @return the list
   */
  List<ItemStack> rewards();

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


  static Trade getTrade(String name){
    switch (name){
      case "invisible-item-frame" -> {
        return new FrameTrade();
      }
      case "light-block" -> {
        return new LightTrade();
      }
      default -> {
        return null;
      }
    }
  }
}
