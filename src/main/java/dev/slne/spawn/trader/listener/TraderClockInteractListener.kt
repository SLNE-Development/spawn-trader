package dev.slne.spawn.trader.listener

import dev.slne.spawn.trader.plugin
import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.spawn.trader.task.TimeTask
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

object TraderClockInteractListener : Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val heldItem = player.inventory.itemInMainHand

        if (!heldItem.isSimilar(plugin.spawnTradersClockItem)) {
            return
        }

        val isVisible = traderVisibilityService.visible

        if (isVisible) {
            val fullTime = TimeTask.world.fullTime
            val currentDay = (fullTime / 24000).toInt()
            val dayStartTick = currentDay * 24000L
            val dayEndTick = dayStartTick + 24000L

            val remainingTicks = dayEndTick - fullTime
            val remainingSeconds = remainingTicks / 20
            val remainingMinutes = remainingSeconds / 60
            val remainingHours = remainingMinutes / 60
            val minutesPart = remainingMinutes % 60

            player.sendText {
                appendInfoPrefix()
                info("Der alte Händler ist derzeit am Spawn und verschwindet in ${remainingHours}h ${minutesPart}m.")
            }
        } else {
            val fullTime = TimeTask.world.fullTime
            val currentDay = (fullTime / 24000).toInt()
            val nextVisibleDay = ((currentDay / 10) + 1) * 10
            val nextVisibleTick = nextVisibleDay * 24000L

            val remainingTicks = nextVisibleTick - fullTime
            val realSeconds = remainingTicks / 20
            val realMinutes = realSeconds / 60
            val realHours = realMinutes / 60
            val minutesPart = realMinutes % 60

            player.sendText {
                appendInfoPrefix()
                info("Der alte Händler ist derzeit unterwegs und erscheint in ${nextVisibleDay - currentDay} Minecraft-Tagen (${realHours}h ${minutesPart}m Echtzeit), solange es nicht regnet.")
            }
        }
    }
}