package dev.slne.spawn.trader.task

import dev.slne.spawn.trader.plugin
import dev.slne.spawn.trader.service.traderVisibilityService
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.Bukkit
import org.bukkit.World
import java.util.concurrent.TimeUnit

object TimeTask {
    private lateinit var task: ScheduledTask
    private var lastDay = -1

    val world: World get() = Bukkit.getWorlds().first()

    fun create() {
        task = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
            val day = (world.fullTime / 24000).toInt()

            if (day != lastDay) {
                lastDay = day

                traderVisibilityService.onNewDay(day)
            }
        }, 0L, 3L, TimeUnit.SECONDS)
    }

    fun dismount() {
        if (::task.isInitialized) {
            task.cancel()
        }
    }
}