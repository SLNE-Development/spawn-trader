package dev.slne.spawn.trader.command

import com.github.shynixn.mccoroutine.folia.globalRegionDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.slne.spawn.trader.plugin
import dev.slne.spawn.trader.service.traderVisibilityService
import dev.slne.spawn.trader.task.TimeTask
import dev.slne.spawn.trader.util.PermissionRegistry
import dev.slne.surf.api.core.messages.adventure.sendText

fun spawnTraderCommand() = commandTree("spawnTrader") {
    withPermission(PermissionRegistry.COMMAND)

    literalArgument("status") {
        anyExecutor { sender, _ ->
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

                sender.sendText {
                    appendInfoPrefix()
                    info("Der Spawn-Trader ist derzeit sichtbar und verschwindet in ${remainingHours}h ${minutesPart}m Echtzeit.")
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

                sender.sendText {
                    appendInfoPrefix()
                    info("Der Spawn-Trader erscheint in ${nextVisibleDay - currentDay} Minecraft-Tagen (${realHours}h ${minutesPart}m Echtzeit), solange es nicht regnet.")
                }
            }
        }
    }

    literalArgument("skip") {
        anyExecutor { sender, _ ->
            val world = TimeTask.world
            val fullTime = world.fullTime
            val currentDay = (fullTime / 24000).toInt()

            if (traderVisibilityService.visible) {
                val nextDayStart = ((currentDay + 1) * 24000L)

                plugin.launch(plugin.globalRegionDispatcher) {
                    world.fullTime = nextDayStart
                }

                sender.sendText {
                    appendSuccessPrefix()
                    success("Der aktuelle Spawn-Trader-Tag wurde übersprungen.")
                }
            } else {
                val nextVisibleDay = ((currentDay / 10) + 1) * 10
                val nextVisibleTick = nextVisibleDay * 24000L

                plugin.launch(plugin.globalRegionDispatcher) {
                    world.fullTime = nextVisibleTick
                }


                sender.sendText {
                    appendSuccessPrefix()
                    success("Die Zeit wurde bis zum nächsten Spawn-Trader-Tag vorgespult.")
                }
            }
        }
    }
}