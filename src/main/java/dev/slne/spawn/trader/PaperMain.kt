package dev.slne.spawn.trader

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.spawn.trader.command.spawnTraderCommand
import dev.slne.spawn.trader.gui.SpawnTraderView
import dev.slne.spawn.trader.listener.NpcInteractListener
import dev.slne.spawn.trader.listener.TraderClockInteractListener
import dev.slne.spawn.trader.papi.PapiExpansion
import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.spawn.trader.task.TimeTask
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.event.register
import dev.slne.surf.api.paper.hook.papi.SurfPaperPAPIHook
import dev.slne.surf.api.paper.inventory.framework.viewFrame
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin

val plugin = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onLoadAsync() {
        viewFrame.with(SpawnTraderView)
    }

    override suspend fun onEnableAsync() {
        traderVisibilityService.create()
        SurfPaperPAPIHook.register(PapiExpansion)

        spawnTraderCommand()

        NpcInteractListener.register()
        TraderClockInteractListener.register()
        TimeTask.create()
    }

    override suspend fun onDisableAsync() {
        TimeTask.dismount()
    }

    val spawnTradersClockItem = buildItem(Material.CLOCK) {
        editMeta {
            it.setEnchantmentGlintOverride(true)
        }

        displayName {
            variableValue("Händlers Uhr")
        }

        buildLore {
            line {
                spacer("Eine uralte Uhr...")
            }
        }
    }
}