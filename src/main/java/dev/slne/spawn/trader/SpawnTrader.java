package dev.slne.spawn.trader;

import dev.slne.spawn.trader.command.SpawnTraderCommand;
import dev.slne.spawn.trader.entity.EntityInteractListener;
import dev.slne.spawn.trader.entity.impl.TraderBukkitEntity;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.object.CooldownPair;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * The Spawn trader.
 */

@Getter
@Setter
@Accessors(fluent = true)
public class SpawnTrader extends JavaPlugin {
	@Getter
	private static SpawnTrader instance;

	private boolean citizens = this.getConfig().getBoolean("citizens");
	private long tradeCooldown = this.getConfig().getLong("trade-cooldown");
	private String prefix = "<gray>>> <gold>Trader <dark_gray>| <white>";

	private File storageFile = new File(this.getDataFolder() + "/storage.yml");
	private FileConfiguration storage = YamlConfiguration.loadConfiguration(storageFile);

	private TraderNPC traderNPC;
	private TradeManager tradeManager;
	private TraderBukkitEntity traderBukkitEntity;

	@Override
	public void onLoad() {
		instance = this;
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

	public static Component deserialize(String message){
		return MiniMessage.miniMessage().deserialize(message);
	}

	public void saveDefaultStorage(){
		if(!this.storageFile().exists()){
			this.saveResource("storage.yml", false);
		}
	}

	public void saveStorage() {
		for (Map.Entry<UUID, CooldownPair> entry : tradeManager.cooldownStorage().entrySet()) {
			String uuid = entry.getKey().toString();
			CooldownPair cooldownPair = entry.getValue();

			storage.set("cooldowns." + uuid + ".trade0", cooldownPair.getTrade0());
			storage.set("cooldowns." + uuid + ".trade1", cooldownPair.getTrade1());
		}

		try {
			storage.save(storageFile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(e.getMessage());
		}
	}

	public void loadStorage() {
		if (!storageFile.exists()) {
			this.saveDefaultStorage();
			this.loadStorage();
			return;
		}

		if (storage.contains("cooldowns")) {
			for (String uuidString : storage.getConfigurationSection("cooldowns").getKeys(false)) {
				UUID uuid = UUID.fromString(uuidString);
				long trade0 = storage.getLong("cooldowns." + uuidString + ".trade0");
				long trade1 = storage.getLong("cooldowns." + uuidString + ".trade1");

				tradeManager.cooldownStorage().put(uuid, new CooldownPair(trade0, trade1));
			}
		}
	}//TODO: Fix reload
}