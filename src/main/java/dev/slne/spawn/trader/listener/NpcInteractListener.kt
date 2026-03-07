package dev.slne.spawn.trader.listener

import dev.slne.spawn.trader.gui.SpawnTraderView
import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.surf.npc.api.event.NpcInteractEvent
import dev.slne.surf.surfapi.bukkit.api.inventory.framework.viewFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object NpcInteractListener : Listener {
    @EventHandler
    fun onNpcInteract(event: NpcInteractEvent) {
        if (traderVisibilityService.visible) {
            viewFrame.open(SpawnTraderView::class.java, event.player)
        }
    }
}