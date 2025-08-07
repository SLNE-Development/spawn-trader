package dev.slne.spawn.trader.manager;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.object.Trade;

import java.util.HashMap;

import lombok.Getter;
import lombok.experimental.Accessors;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.Sound.Emitter;
import net.kyori.adventure.sound.Sound.Source;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The type Trade manager.
 */
@Getter
@Accessors(fluent = true)
public class TradeManager {
  /**
   * Sets cooldown.
   *
   * @param player the player
   * @param trade  the trade
   */
  public void setCooldown(Player player, Trade trade) {
    final long currentTime = System.currentTimeMillis();
    final long cooldownEndTime = currentTime + trade.cooldown();

    this.saveCooldown(player, trade, cooldownEndTime);
  }

  /**
   * Is on cooldown boolean.
   *
   * @param player the player
   * @param trade  the trade
   * @return the boolean
   */
  public boolean isOnCooldown(Player player, Trade trade) {
      return this.getCooldown(player, trade) >= System.currentTimeMillis();
  }

  /**
   * Remove requirements.
   *
   * @param player the player
   * @param trade  the trade
   */
  public void removeRequirements(Player player, Trade trade) {
    if (this.hasEnoughRequirements(player, trade)) {
      trade.requirements().forEach(item -> this.removeItem(player, item));
    } else {
      player.sendMessage(SpawnTrader.prefix().append(Component.text("Du benötigst weitere Materialien!").color(NamedTextColor.RED)));
    }
  }

  /**
   * Has enough requirements boolean.
   *
   * @param player the player
   * @param trade  the trade
   * @return the boolean
   */
  public boolean hasEnoughRequirements(Player player, Trade trade) {
    for (final ItemStack requirement : trade.requirements()) {
      if (!this.hasItem(player, requirement)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Give reward boolean.
   *
   * @param player the player
   * @param trade  the trade
   */
  public void giveReward(Player player, Trade trade) {
    player.sendMessage(SpawnTrader.prefix().append(Component.text(trade.rewardMessage())));

    for (ItemStack reward : trade.rewards()) {
      HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(reward);

      for (ItemStack stack : remainingItems.values()) {
        player.getWorld().dropItem(player.getLocation(), stack).setOwner(player.getUniqueId());
      }
    }
  }

  /**
   * Has item boolean.
   *
   * @param player the player
   * @param item   the item
   * @return the boolean
   */
  private boolean hasItem(Player player, ItemStack item) {
    int found = 0;

    for (final ItemStack stack : player.getInventory().getContents()) {
      if (stack != null) {
        if (stack.isSimilar(item)) {
          found += stack.getAmount();

          if (found >= item.getAmount()) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /**
   * Remove item.
   *
   * @param player the player
   * @param item   the item
   */
  private void removeItem(Player player, ItemStack item) {
    int amountToRemove = item.getAmount();

    for (ItemStack stack : player.getInventory().getContents()) {
      if (stack != null && stack.isSimilar(item)) {
        int stackAmount = stack.getAmount();

        if (stackAmount > amountToRemove) {
          stack.setAmount(stackAmount - amountToRemove);
          break;
        } else {
          amountToRemove -= stackAmount;
          stack.setAmount(0);
        }

        if (amountToRemove <= 0) {
          break;
        }
      }
    }
  }

  /**
   * Buy.
   *
   * @param player the player
   * @param trade  the trade
   */
  public void buy(Player player, Trade trade) {
    if (this.isOnCooldown(player, trade)) {
      player.sendActionBar(Component.text("ʙɪᴛᴛᴇ ᴋᴏᴍᴍᴇ ɪɴ ", NamedTextColor.RED)
          .append(Component.text(SpawnTrader.instance().getFormattedCooldown(player, trade), NamedTextColor.GOLD))
          .append(Component.text(" ᴡɪᴇᴅᴇʀ, ᴀᴋᴛᴜᴇʟʟ ʜᴀʙᴇ ɪᴄʜ ɴɪᴄʜᴛѕ ꜰüʀ ᴅɪᴄʜ.", NamedTextColor.RED)).decorate(TextDecoration.BOLD)
      );
      player.playSound(Sound.sound(org.bukkit.Sound.ENTITY_VILLAGER_NO, Source.PLAYER, 1f, 1f), Emitter.self());
      player.sendMessage(SpawnTrader.prefix().append(Component.text("Bitte komme in " + SpawnTrader.instance().getFormattedCooldown(player, trade) + " wieder, aktuell habe ich nichts für dich.").color(NamedTextColor.RED)));
      return;
    }

    if (this.hasEnoughRequirements(player, trade)) {
      this.giveReward(player, trade);
      this.removeRequirements(player, trade);
      this.setCooldown(player, trade);

      player.playSound(Sound.sound(org.bukkit.Sound.ENTITY_WANDERING_TRADER_YES, Source.PLAYER, 1f, 1f), Emitter.self());
    } else {
      player.sendActionBar(Component.text("ᴅᴜ ʜᴀѕᴛ ɴɪᴄʜᴛ ɢᴇɴüɢᴇɴᴅ ʀᴏʜѕᴛᴏꜰꜰᴇ.", NamedTextColor.RED).decorate(TextDecoration.BOLD));
      player.playSound(Sound.sound(org.bukkit.Sound.BLOCK_ANVIL_DESTROY, Source.PLAYER, 1f, 1f), Emitter.self());
      player.sendMessage(SpawnTrader.prefix().append(Component.text("Du benötigst weitere Materialien!").color(NamedTextColor.RED)));
    }
  }

  /**
   * Saves a cooldown to a trade.
   *
   * @param player the player
   * @param trade  the trade
   * @param time  the time
   */

  public void saveCooldown(Player player, Trade trade, Long time){
    SpawnTrader.instance().saveCooldown(player, trade, time);
  }

  /**
   * Get a cooldown from a trade by a player.
   *
   * @param player the player
   * @param trade  the trade
   * @return the cooldown from pdc - 0x8000000000000000L if not existing
   */

  public Long getCooldown(Player player, Trade trade) {
    return SpawnTrader.instance().getCooldown(player, trade);
  }
}
