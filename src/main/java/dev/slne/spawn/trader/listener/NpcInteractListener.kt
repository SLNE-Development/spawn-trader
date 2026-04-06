package dev.slne.spawn.trader.listener

import dev.slne.spawn.trader.gui.SpawnTraderView
import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.surf.api.paper.inventory.framework.viewFrame
import dev.slne.surf.npc.api.event.NpcInteractEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object NpcInteractListener : Listener {
    @EventHandler
    fun onNpcInteract(event: NpcInteractEvent) {
        if (!event.npc.uniqueName.startsWith("spawn_trader_")) {
            return
        }

        if (traderVisibilityService.visible) {
            viewFrame.open(SpawnTraderView::class.java, event.player)
        }
    }
}