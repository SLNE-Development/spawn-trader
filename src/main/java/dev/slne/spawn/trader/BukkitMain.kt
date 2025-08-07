package dev.slne.spawn.trader

import dev.slne.spawn.trader.command.SpawnTraderCommand
import dev.slne.spawn.trader.entity.EntityInteractListener
import dev.slne.spawn.trader.entity.impl.TraderNPC
import dev.slne.spawn.trader.manager.trade.Trade
import dev.slne.spawn.trader.manager.trade.impl.FrameTrade
import dev.slne.spawn.trader.manager.trade.impl.GlobeTrade
import dev.slne.spawn.trader.manager.trade.impl.LightTrade
import dev.slne.surf.surfapi.bukkit.api.event.register
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

val plugin = JavaPlugin.getPlugin(BukkitMain::class.java)

class BukkitMain : JavaPlugin() {
    private val cooldownKeyTradeFrame = NamespacedKey(this, "trader-cooldown-item-frame")
    private val cooldownKeyTradeLight = NamespacedKey(this, "trader-cooldown-light-block")
    private val cooldownKeyTradeGlobe = NamespacedKey(this, "trader-cooldown-globe")

    override fun onEnable() {
        SpawnTraderCommand("spawntrader").register()
        EntityInteractListener().register()

        TraderNPC.load()
    }


    fun saveCooldown(player: Player, trade: Trade, time: Long) {
        val pdc = player.persistentDataContainer

        when (trade) {
            is FrameTrade -> {
                pdc.set(cooldownKeyTradeFrame, PersistentDataType.LONG, time)
            }

            is LightTrade -> {
                pdc.set(cooldownKeyTradeLight, PersistentDataType.LONG, time)
            }

            is GlobeTrade -> {
                pdc.set(cooldownKeyTradeGlobe, PersistentDataType.LONG, time)
            }
        }
    }

    fun getCooldown(player: Player, trade: Trade?): Long {
        val pdc = player.persistentDataContainer

        when (trade) {
            is FrameTrade -> pdc.get<Long, Long>(cooldownKeyTradeFrame, PersistentDataType.LONG)
                ?: return Long.MIN_VALUE

            is LightTrade -> pdc.get<Long, Long>(cooldownKeyTradeLight, PersistentDataType.LONG)
                ?: return Long.MIN_VALUE

            is GlobeTrade -> pdc.get<Long, Long>(cooldownKeyTradeGlobe, PersistentDataType.LONG)
                ?: return Long.MIN_VALUE

            else -> return Long.MIN_VALUE
        }

        return Long.MIN_VALUE
    }

    fun getFormattedCooldown(player: Player, trade: Trade): String {
        val remainingMillis = this.getCooldown(player, trade) - System.currentTimeMillis()

        if (remainingMillis <= 0) {
            return "N/A"
        }

        val seconds = (remainingMillis / 1000) % 60
        val minutes = (remainingMillis / (1000 * 60)) % 60
        val hours = (remainingMillis / (1000 * 60 * 60)) % 24
        val days = remainingMillis / (1000 * 60 * 60 * 24)

        val formattedTime = StringBuilder()

        if (days > 0) {
            formattedTime.append(days).append(" Tage, ")
        }
        if (hours > 0) {
            formattedTime.append(hours).append(" Stunden, ")
        }
        if (minutes > 0) {
            formattedTime.append(minutes).append(" Minuten, ")
        }
        if (seconds > 0) {
            formattedTime.append(seconds).append(" Sekunden")
        }

        if (formattedTime.toString().endsWith(", ")) {
            formattedTime.setLength(formattedTime.length - 2)
        }

        return formattedTime.toString()
    }
}