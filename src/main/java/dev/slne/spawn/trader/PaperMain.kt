package dev.slne.spawn.trader

import dev.slne.spawn.trader.command.SpawnTraderCommand
import dev.slne.spawn.trader.papi.PapiExpansion
import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.surf.surfapi.bukkit.api.hook.papi.papiHook
import org.bukkit.plugin.java.JavaPlugin

val plugin = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : JavaPlugin() {
    override fun onEnable() {
        SpawnTraderCommand("spawntrader").register()

        traderVisibilityService.create()

        papiHook.register(PapiExpansion)
    }
}