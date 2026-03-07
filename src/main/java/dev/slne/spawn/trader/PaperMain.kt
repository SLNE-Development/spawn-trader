package dev.slne.spawn.trader

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.spawn.trader.command.spawnTraderCommand
import dev.slne.spawn.trader.gui.SpawnTraderView
import dev.slne.spawn.trader.listener.NpcInteractListener
import dev.slne.spawn.trader.papi.PapiExpansion
import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.spawn.trader.task.TimeTask
import dev.slne.surf.surfapi.bukkit.api.event.register
import dev.slne.surf.surfapi.bukkit.api.hook.papi.papiHook
import dev.slne.surf.surfapi.bukkit.api.inventory.framework.viewFrame
import org.bukkit.plugin.java.JavaPlugin

val plugin = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onLoadAsync() {
        viewFrame.with(SpawnTraderView)
    }

    override suspend fun onEnableAsync() {
        traderVisibilityService.create()
        papiHook.register(PapiExpansion)

        spawnTraderCommand()

        NpcInteractListener.register()
        TimeTask.create()
    }

    override suspend fun onDisableAsync() {
        TimeTask.dismount()
    }
}