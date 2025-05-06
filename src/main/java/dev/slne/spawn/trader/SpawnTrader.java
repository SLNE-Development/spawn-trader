package dev.slne.spawn.trader;

import dev.slne.spawn.trader.command.SpawnTraderCommand;
import dev.slne.spawn.trader.entity.EntityInteractListener;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.object.Trade;

import java.io.File;


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
import org.bukkit.configuration.file.FileConfiguration;
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

  private long tradeCooldown;

  private File storageFile;
  private FileConfiguration storage;

  private TraderNPC traderNPC;
  private TradeManager tradeManager;
  private TraderBukkitEntity traderBukkitEntity;

  @Override
  public void onEnable() {
    this.tradeManager = new TradeManager();
    this.traderNPC = new TraderNPC();
    this.traderBukkitEntity = new TraderBukkitEntity();

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
}