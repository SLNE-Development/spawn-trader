package dev.slne.spawn.trader.listener

import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.surf.npc.api.event.NpcInteractEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object NpcInteractListener : Listener {
    @EventHandler
    fun onNpcInteract(event: NpcInteractEvent) {
        if (traderVisibilityService.visible) {
            // TODO: Open View
        }
    }
}