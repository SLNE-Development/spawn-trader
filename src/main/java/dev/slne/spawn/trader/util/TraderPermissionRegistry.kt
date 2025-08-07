package dev.slne.spawn.trader.util

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object TraderPermissionRegistry : PermissionRegistry() {
    const val PERMISSION_PREFIX = "spawn.trader"
    const val PERMISSION_PREFIX_COMMAND = "$PERMISSION_PREFIX.command"

    val COMMAND = create(PERMISSION_PREFIX_COMMAND)
    val COMMAND_SPAWN = create("$PERMISSION_PREFIX_COMMAND.spawn")
    val COMMAND_CLEAR = create("$PERMISSION_PREFIX_COMMAND.clear")
    val COMMAND_SET_COOLDOWN = create("$PERMISSION_PREFIX_COMMAND.setCooldown")
}