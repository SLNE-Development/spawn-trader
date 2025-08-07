package dev.slne.spawn.trader.entity.impl

import dev.slne.spawn.trader.entity.CustomTrader
import dev.slne.spawn.trader.plugin
import dev.slne.surf.npc.api.dsl.npc
import dev.slne.surf.npc.api.npc.Npc
import dev.slne.surf.npc.api.result.NpcCreationResult
import dev.slne.surf.npc.api.surfNpcApi
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Location

object TraderNPC : CustomTrader {
    private const val NPC_UNIQUE_NAME = "spawn-trader"
    lateinit var npc: Npc

    fun load() {
        surfNpcApi.getNpc(NPC_UNIQUE_NAME)?.let {
            npc = it
        }
    }

    override suspend fun spawn(location: Location, name: String): Boolean {
        val requestedSkin = surfNpcApi.getSkin("Trader")
        val result = npc(plugin) {
            displayName = {
                append(COMPONENT_NAME)
            }
            uniqueName = NPC_UNIQUE_NAME
            skin = requestedSkin
            location {
                world = location.world.name
                x = location.x
                y = location.y
                z = location.z
            }
            persistent = true
        }

        if (result.isSuccess()) {
            npc = (result as NpcCreationResult.Success).npc
        }

        return result.isSuccess()
    }

    override fun clear(name: String): Boolean {
        val spawnTraderNpc = surfNpcApi.getNpc(NPC_UNIQUE_NAME) ?: return false
        spawnTraderNpc.delete()

        return true
    }

    const val STRING_NAME: String = "<b><gradient:#9F0B00:#4C504F>ѕᴘᴀᴡɴ ᴛʀᴀᴅᴇʀ"
    val COMPONENT_NAME = MiniMessage.miniMessage().deserialize(STRING_NAME)
}
