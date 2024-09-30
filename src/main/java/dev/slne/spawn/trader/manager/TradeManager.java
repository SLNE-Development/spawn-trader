package dev.slne.spawn.trader.manager;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.object.CooldownPair;
import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.GlobeTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The type Trade manager.
 */
@Getter
@Accessors(fluent = true)
public class TradeManager {

  private final FileConfiguration storage = SpawnTrader.instance().storage();
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy-HH:mm:ss");
  private final Object2ObjectMap<UUID, CooldownPair> cooldownStorage = new Object2ObjectOpenHashMap<>();

  /**
   * Sets cooldown.
   *
   * @param player the player
   * @param trade  the trade
   */
  public void setCooldown(Player player, Trade trade) {
    final UUID uuid = player.getUniqueId();
    CooldownPair cooldownPair = this.cooldownStorage.getOrDefault(uuid,
        new CooldownPair(0L, 0L, 0L));

    final long currentTime = System.currentTimeMillis();
    final long cooldownEndTime = currentTime + SpawnTrader.instance().tradeCooldown();

    if (trade instanceof FrameTrade) {
      cooldownPair = new CooldownPair(cooldownEndTime, cooldownPair.getTrade1(), cooldownPair.getTrade2());
    } else if (trade instanceof LightTrade) {
      cooldownPair = new CooldownPair(cooldownPair.getTrade0(), cooldownEndTime, cooldownPair.getTrade2());
    } else if (trade instanceof GlobeTrade) {
      cooldownPair = new CooldownPair(cooldownPair.getTrade0(), cooldownPair.getTrade1(), cooldownEndTime);
    }

    this.cooldownStorage.remove(uuid);
    this.cooldownStorage.put(uuid, cooldownPair);
  }

  /**
   * Is on cooldown boolean.
   *
   * @param player the player
   * @param trade  the trade
   * @return the boolean
   */
  public boolean isOnCooldown(Player player, Trade trade) {
    final UUID playerId = player.getUniqueId();
    final CooldownPair cooldownPair = cooldownStorage.get(playerId);

    final long currentTime = System.currentTimeMillis();

    if (cooldownPair == null) {
      return false;
    }

    if (trade instanceof FrameTrade) {
      return cooldownPair.getTrade0() >= currentTime;
    } else if (trade instanceof LightTrade) {
      return cooldownPair.getTrade1() >= currentTime;
    } else if (trade instanceof GlobeTrade) {
      return cooldownPair.getTrade2() >= currentTime;
    }

    return false;
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
      player.sendMessage(SpawnTrader.prefix()
          .append(Component.text("Du benötigst weitere Materialien!").color(NamedTextColor.RED)));
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
    if (trade instanceof FrameTrade) {
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() +
          " item_frame[entity_data={id:\"minecraft:item_frame\",Invisible:1b}] 20");
    }

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
    for (final ItemStack stack : player.getInventory().getContents()) {
      if (stack != null) {
        if (stack.isSimilar(item)) {
          int stackAmount = stack.getAmount();

          if (stackAmount > item.getAmount()) {
            stack.setAmount(stackAmount - item.getAmount());

            break;
          } else {
            stack.setAmount(0);
          }
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
      player.sendMessage(SpawnTrader.prefix()
          .append(Component.text("Bitte komm später wieder, aktuell habe ich nichts für dich.")
              .color(NamedTextColor.RED)));
      return;
    }

    if (this.hasEnoughRequirements(player, trade)) {
      this.giveReward(player, trade);
      this.removeRequirements(player, trade);
      this.setCooldown(player, trade);
    } else {
      player.sendMessage(SpawnTrader.prefix().append(
          Component.text("Du benötigst weitere Materialien!").color(NamedTextColor.RED)));
    }
  }
}
