package dev.slne.spawn.trader.util

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {
    const val PERMISSION_PREFIX = "spawn.trader"
    const val PERMISSION_PREFIX_COMMAND = "$PERMISSION_PREFIX.command"

    val COMMAND = create(PERMISSION_PREFIX_COMMAND)
}