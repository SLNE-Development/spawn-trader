package dev.slne.spawn.trader.entity

import org.bukkit.Location

/**
 * The interface Custom trader.
 */
interface CustomTrader {
    /**
     * Spawn.
     *
     * @param location the location
     */
    fun spawn(location: Location, name: String): Boolean

    /**
     * Clear.
     */
    fun clear(name: String): Boolean
}
