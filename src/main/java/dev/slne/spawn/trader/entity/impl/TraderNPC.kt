package dev.slne.spawn.trader.entity.impl

import dev.slne.spawn.trader.entity.CustomTrader
import dev.slne.surf.npc.api.dsl.npc
import dev.slne.surf.npc.api.npc.Npc
import dev.slne.surf.npc.api.result.NpcCreationResult
import dev.slne.surf.npc.api.surfNpcApi
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Location

object TraderNPC : CustomTrader {
    private val signature =
        "G/JtpyiQxNFn2ucGK1sHz7NZlxJhFeLzXNlCFCokL26N4gOg53Cwq2jowVSIUcDwxDxl43+ehMJzPjU5+DCVsoO5xJh5Zsh/98wpkWCE9PcOw/iAWTrcghEjusV5prlqS8QLAH82+ndP746PkNIyVhJlc8l5BACwRFbI3Nd2X2KwqyRJDWSbhPMl/V1Sg/mwGAl+IMzPjdvriN0CJDwqKv9gVugeLSmqFvlNIerwlkgyZqaaKtIYv/zRkaVmbFW0Dv8KxxAlH2zTpicY/F8aof4NjrvwajeuKd6z0jopnS+nNv+b6bqNcAnhbQWlTKz2V3mgzNZT2NjoTNopHa5FzJ+uEzSV9xr3XCrmFIM8PRoDLJEYOSFR2btA6V4PO0Hksdr5sags3lGGHihY+DTYKwNnQsJeL8jKBi12IHtmZ6HJvPkcPElNIzKVaIqgi5w3WEUv6QW3Dfp6U1Q77SrEzBg3d4I3ZSgXfN5L+B6qrE4EKyLCb9jZQD567CxVnOyPEz3k5bo0D/YOd9X75wWKFEnXmVO2UCxFlrueIAJZX+tEjbQM6jnfbkTctURl7qT5tvGhjMowE5arwN0PrZQ8Y1DzLv0FPVS+wkYvpyMURr3pd/pzKcy+60QjUVowzZKWCFtfGkV8Q/KkmYhmkHfSa+njPWMGhJEKkq3ecY8DQ0g=="
    private val texture =
        "ewogICJ0aW1lc3RhbXAiIDogMTYxMzA5NDI3OTAzNSwKICAicHJvZmlsZUlkIiA6ICJhOGExZGY5MGE3YjU0NTdjYTEwYjM5ZGQ0N2NmMjM5MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJrZW5ueWtjb21haW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2NmOTU4NjQyMmM4NmI5ODk5N2QxMTYyNzc2OTYzNDcyZTQ1YzE4MzE0Y2Q3ODY1ZmY5NzYxOGE5MzdkYmQ2OCIKICAgIH0KICB9Cn0="

    private val NPC_UNIQUE_NAME = "spawn-trader"
    lateinit var npc: Npc

    override fun spawn(location: Location, name: String): Boolean {
        val result = npc {
            displayName = {
                append(COMPONENT_NAME)
            }
            uniqueName = NPC_UNIQUE_NAME
            skin {
                ownerName = "idk"
                value = texture
                signature = this@TraderNPC.signature
            }
            location {
                world = location.world.name
                x = location.x
                y = location.y
                z = location.z
            }
        }

        if (result.isSuccess()) {
            npc = (result as NpcCreationResult.Success).npc
        }

        return true
    }

    override fun clear(name: String): Boolean {
        val spawnTraderNpc = surfNpcApi.getNpc(NPC_UNIQUE_NAME) ?: return false
        spawnTraderNpc.delete()

        return false
    }

    const val STRING_NAME: String = "<b><gradient:#9F0B00:#4C504F>ѕᴘᴀᴡɴ ᴛʀᴀᴅᴇʀ"
    val COMPONENT_NAME = MiniMessage.miniMessage().deserialize(STRING_NAME)
}
