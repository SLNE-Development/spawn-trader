package dev.slne.spawn.trader.listener

import dev.slne.spawn.trader.service.traderVisibilityService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object ConnectionListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        traderVisibilityService.npcs.forEach {
            it.value.addViewer(event.player.uniqueId)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerJoinEvent) {
        traderVisibilityService.npcs.forEach {
            it.value.removeViewer(event.player.uniqueId)
        }
    }
}