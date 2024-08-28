package dev.slne.spawn.trader;

import dev.slne.spawn.trader.command.SpawnTraderCommand;
import dev.slne.spawn.trader.entity.EntityInteractListener;
import dev.slne.spawn.trader.entity.impl.TraderBukkitEntity;
import dev.slne.spawn.trader.entity.impl.TraderNPC;
import dev.slne.spawn.trader.gui.SpawnTraderInterface;
import dev.slne.spawn.trader.manager.TradeManager;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
	private SpawnTraderInterface spawnTraderInterface;

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.saveDefaultStorage();

		this.tradeManager = new TradeManager();
		this.spawnTraderInterface = new SpawnTraderInterface();
		this.traderNPC = new TraderNPC();
		this.traderBukkitEntity = new TraderBukkitEntity();

		this.getCommand("spawntrader").setExecutor(new SpawnTraderCommand());

		Bukkit.getPluginManager().registerEvents(new EntityInteractListener(), this);
	}

	@Override
	public void onDisable() {
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
}