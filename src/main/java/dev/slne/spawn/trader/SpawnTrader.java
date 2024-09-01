package dev.slne.spawn.trader;

import dev.slne.spawn.trader.command.SpawnTraderCommand;
import dev.slne.spawn.trader.entity.EntityInteractListener;
import dev.slne.spawn.trader.entity.impl.TraderBukkitEntity;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.object.CooldownPair;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Spawn trader.
 */
@Getter
@Setter
@Accessors(fluent = true)
public class SpawnTrader extends JavaPlugin {

  @Getter
  private static SpawnTrader instance;
  private final String prefix = "<gray>>> <gold>Trader <dark_gray>| <white>";

  private boolean citizens;
  private long tradeCooldown;

  private File storageFile;
  private FileConfiguration storage;

  private TraderNPC traderNPC;
  private TradeManager tradeManager;
  private TraderBukkitEntity traderBukkitEntity;

  @Override
  public void onLoad() {
    instance = this;

    citizens = this.getConfig().getBoolean("citizens");
    tradeCooldown = this.getConfig().getLong("trade-cooldown");

    storageFile = new File(this.getDataFolder() + "/storage.yml");
    storage = YamlConfiguration.loadConfiguration(storageFile);
  }

  @Override
  public void onEnable() {
    this.saveDefaultConfig();
    this.saveDefaultStorage();

    this.tradeManager = new TradeManager();
    this.traderNPC = new TraderNPC();
    this.traderBukkitEntity = new TraderBukkitEntity();

    this.loadStorage();

    new SpawnTraderCommand("spawntrader").register();

    Bukkit.getPluginManager().registerEvents(new EntityInteractListener(), this);
  }

  @Override
  public void onDisable() {
    this.saveStorage();
    //Text as placeholder :O
  }

  /**
   * Deserialize component.
   *
   * @param message the message
   * @return the component
   */
  public static Component deserialize(String message) {
    return MiniMessage.miniMessage().deserialize(message);
  }

  /**
   * Save default storage.
   */
  public void saveDefaultStorage() {
    if (!this.storageFile().exists()) {
      this.saveResource("storage.yml", false);
    }
  }

  /**
   * Save storage.
   */
  public void saveStorage() {
    for (final Map.Entry<UUID, CooldownPair> entry : tradeManager.cooldownStorage().entrySet()) {
      final String uuid = entry.getKey().toString();
      final CooldownPair cooldownPair = entry.getValue();

      storage.set("cooldowns." + uuid + ".trade0", cooldownPair.getTrade0());
      storage.set("cooldowns." + uuid + ".trade1", cooldownPair.getTrade1());
    }

    try {
      storage.save(storageFile);
    } catch (IOException e) {
      Bukkit.getConsoleSender().sendMessage(e.getMessage());
    }
  }

  /**
   * Load storage.
   */
  public void loadStorage() {
    if (!storageFile.exists()) {
      this.saveDefaultStorage();
      this.loadStorage();

      return;
    }

    if (storage.contains("cooldowns")) {
      for (final String uuidString : storage.getConfigurationSection("cooldowns").getKeys(false)) {
        UUID uuid = UUID.fromString(uuidString);
        long trade0 = storage.getLong("cooldowns." + uuidString + ".trade0");
        long trade1 = storage.getLong("cooldowns." + uuidString + ".trade1");

        tradeManager.cooldownStorage().put(uuid, new CooldownPair(trade0, trade1));
      }
    }
  }//TODO: Fix reload
}