package dev.slne.spawn.trader;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.slne.spawn.trader.command.SpawnTraderCommand;
import dev.slne.spawn.trader.entity.EntityInteractListener;
import dev.slne.spawn.trader.entity.impl.TraderBukkitEntity;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.manager.TradeManager;
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

		CommandAPI.onLoad(new CommandAPIBukkitConfig(this).usePluginNamespace().silentLogs(true));
	}

	@Override
	public void onEnable() {
		CommandAPI.onEnable();

		this.saveDefaultConfig();
		this.saveDefaultStorage();

		this.tradeManager = new TradeManager();
		this.traderNPC = new TraderNPC();
		this.traderBukkitEntity = new TraderBukkitEntity();

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

	public void saveStorage(){
		try {
			this.storage.save(this.storageFile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(e.getMessage());
		}
	}

	//TODO: Fix reload
}