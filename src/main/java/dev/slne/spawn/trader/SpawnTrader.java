package dev.slne.spawn.trader;

import dev.slne.spawn.trader.command.SpawnTraderCommand;
import dev.slne.spawn.trader.entity.EntityInteractListener;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.GlobeTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Spawn trader.
 */
@Getter
@Setter
@Accessors(fluent = true)
public class SpawnTrader extends JavaPlugin {
  private final NamespacedKey cooldownKeyTradeFrame = new NamespacedKey(this, "trader-cooldown-item-frame");
  private final NamespacedKey cooldownKeyTradeLight = new NamespacedKey(this, "trader-cooldown-light-block");
  private final NamespacedKey cooldownKeyTradeGlobe = new NamespacedKey(this, "trader-cooldown-globe");

  @Getter
  private static final Component prefix = Component.text(">> ").color(NamedTextColor.GRAY)
          .append(Component.text("Trader").color(NamedTextColor.GOLD))
          .append(Component.text(" |").color(NamedTextColor.DARK_GRAY))
          .append(Component.text(" ").color(NamedTextColor.WHITE));

  private TraderNPC traderNPC;
  private TradeManager tradeManager;

  @Override
  public void onEnable() {
    this.tradeManager = new TradeManager();
    this.traderNPC = new TraderNPC();

    new SpawnTraderCommand("spawntrader").register();

    Bukkit.getPluginManager().registerEvents(new EntityInteractListener(), this);
  }

  /**
   * The Plugin instance.
   *
   * @return the plugin instance
   */

  public static SpawnTrader instance(){
    return getPlugin(SpawnTrader.class);
  }

  /**
   * Saves a cooldown to a trade.
   *
   * @param player the player
   * @param trade  the trade
   * @param time  the time
   */

  public void saveCooldown(Player player, Trade trade, Long time){
    PersistentDataContainer pdc = player.getPersistentDataContainer();

    if(trade instanceof FrameTrade){
      pdc.set(cooldownKeyTradeFrame, PersistentDataType.LONG, time);
    }else if(trade instanceof LightTrade){
      pdc.set(cooldownKeyTradeLight, PersistentDataType.LONG, time);
    }else if(trade instanceof GlobeTrade){
      pdc.set(cooldownKeyTradeGlobe, PersistentDataType.LONG, time);
    }
  }

  /**
   * Get a cooldown from a trade by a player.
   *
   * @param player the player
   * @param trade  the trade
   * @return the cooldown from pdc - 0x8000000000000000L if not existing
   */

  public Long getCooldown(Player player, Trade trade) {
    PersistentDataContainer pdc = player.getPersistentDataContainer();

    if (trade instanceof FrameTrade) {
      if(pdc.get(cooldownKeyTradeFrame, PersistentDataType.LONG) == null){
        return Long.MIN_VALUE;
      }

      return pdc.get(cooldownKeyTradeFrame, PersistentDataType.LONG);
    } else if (trade instanceof LightTrade) {
      if(pdc.get(cooldownKeyTradeLight, PersistentDataType.LONG) == null){
        return Long.MIN_VALUE;
      }

      return pdc.get(cooldownKeyTradeLight, PersistentDataType.LONG);
    } else if (trade instanceof GlobeTrade) {
      if(pdc.get(cooldownKeyTradeGlobe, PersistentDataType.LONG) == null){
        return Long.MIN_VALUE;
      }

      return pdc.get(cooldownKeyTradeGlobe, PersistentDataType.LONG);
    }

    return Long.MIN_VALUE;
  }

  public String getFormattedCooldown(Player player, Trade trade) {
      long remainingMillis = this.getCooldown(player, trade) - System.currentTimeMillis();

      if (remainingMillis <= 0) {
          return "N/A";
      }

      long seconds = (remainingMillis / 1000) % 60;
      long minutes = (remainingMillis / (1000 * 60)) % 60;
      long hours = (remainingMillis / (1000 * 60 * 60)) % 24;
      long days = remainingMillis / (1000 * 60 * 60 * 24);

      StringBuilder formattedTime = new StringBuilder();

      if (days > 0) {
          formattedTime.append(days).append(" Tage, ");
      }
      if (hours > 0) {
          formattedTime.append(hours).append(" Stunden, ");
      }
      if (minutes > 0) {
          formattedTime.append(minutes).append(" Minuten, ");
      }
      if (seconds > 0) {
          formattedTime.append(seconds).append(" Sekunden");
      }

      if (formattedTime.toString().endsWith(", ")) {
          formattedTime.setLength(formattedTime.length() - 2);
      }

      return formattedTime.toString();
  }
}