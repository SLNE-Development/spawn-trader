package dev.slne.spawn.trader;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The type Spawn trader.
 */
public class SpawnTrader extends JavaPlugin {

	@Override
	public void onLoad() {
		// Currently this check is not needed, as no recipe is using transactions.
//		Plugin plugin = Bukkit.getPluginManager().getPlugin("surf-transaction-bukkit");
//
//		if(plugin == null) {
//			getLogger().severe("Could not find the required plugin 'surf-transaction-bukkit'. Disabling SpawnTrader.");
//
//			// TODO: Comment when testing
//			getServer().getPluginManager().disablePlugin(this);
//		}
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {

	}
}