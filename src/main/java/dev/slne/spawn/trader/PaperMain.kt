package dev.slne.spawn.trader

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.spawn.trader.listener.NpcInteractListener
import dev.slne.spawn.trader.papi.PapiExpansion
import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.surf.surfapi.bukkit.api.event.register
import dev.slne.surf.surfapi.bukkit.api.hook.papi.papiHook
import org.bukkit.plugin.java.JavaPlugin

val plugin = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        traderVisibilityService.create()
        papiHook.register(PapiExpansion)

        NpcInteractListener.register()
    }
}