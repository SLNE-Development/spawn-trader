package dev.slne.spawn.trader.manager.object;

import java.util.List;
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
   * Reward message string.
   *
   * @return the string
   */
  String rewardMessage();
}
