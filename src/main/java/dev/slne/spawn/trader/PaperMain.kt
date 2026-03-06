package dev.slne.spawn.trader

import dev.slne.spawn.trader.command.SpawnTraderCommand
import org.bukkit.plugin.java.JavaPlugin

val plugin = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : JavaPlugin() {

    override fun onEnable() {
        SpawnTraderCommand("spawntrader").register()
    }
}