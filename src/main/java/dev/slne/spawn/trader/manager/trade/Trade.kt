package dev.slne.spawn.trader.manager.trade

import dev.slne.spawn.trader.manager.trade.impl.FrameTrade
import dev.slne.spawn.trader.manager.trade.impl.GlobeTrade
import dev.slne.spawn.trader.manager.trade.impl.LightTrade
import it.unimi.dsi.fastutil.objects.ObjectList
import org.bukkit.inventory.ItemStack

/**
 * The interface Trade.
 */
interface Trade {
    /**
     * Requirements list.
     *
     * @return the list
     */
    fun requirements(): ObjectList<ItemStack>

    /**
     * Rewards list.
     *
     * @return the list
     */
    fun rewards(): ObjectList<ItemStack>

    /**
     * Id integer.
     *
     * @return the integer
     */
    fun id(): Int

    /**
     * Name String.
     *
     * @return the name
     */
    fun name(): String

    /**
     * Reward message string.
     *
     * @return the string
     */
    fun rewardMessage(): String

    /**
     * Get the cooldown for this trade.
     *
     * @return the cooldown in milliseconds
     */
    fun cooldown(): Long


    companion object {
        fun getTrade(name: String): Trade? {
            when (name) {
                "invisible-item-frame" -> {
                    return FrameTrade()
                }

                "light-block" -> {
                    return LightTrade()
                }

                "globe-banner-pattern" -> {
                    return GlobeTrade()
                }

                else -> {
                    return null
                }
            }
        }
    }
}
