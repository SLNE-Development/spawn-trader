package dev.slne.spawn.trader.command

import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.spawn.trader.task.TimeTask
import dev.slne.spawn.trader.util.PermissionRegistry
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun spawnTraderCommand() = commandTree("spawnTrader") {
    withPermission(PermissionRegistry.COMMAND)

    literalArgument("status") {
        anyExecutor { sender, _ ->
            val isVisible = traderVisibilityService.visible

            if (isVisible) {
                sender.sendText {
                    appendInfoPrefix()
                    info("Der Spawn-Trader ist derzeit sichtbar.")
                }
            } else {
                val fullTime = TimeTask.world.fullTime

                val currentDay = (fullTime / 24000).toInt()
                val nextVisibleDay = ((currentDay / 10) + 1) * 10
                val nextVisibleTick = nextVisibleDay * 24000L

                val remainingTicks = nextVisibleTick - fullTime
                val remainingDays = nextVisibleDay - currentDay

                val realSeconds = remainingTicks / 20
                val realMinutes = realSeconds / 60
                val realHours = realMinutes / 60
                val minutesPart = realMinutes % 60

                sender.sendText {
                    appendInfoPrefix()
                    info("Der Spawn-Trader erscheint in $remainingDays Minecraft-Tagen (${realHours}h ${minutesPart}m Echtzeit).")
                }
            }
        }
    }
}