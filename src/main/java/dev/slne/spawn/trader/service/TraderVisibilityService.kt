package dev.slne.spawn.trader.service

import dev.slne.spawn.trader.task.TimeTask
import dev.slne.surf.npc.api.dsl.npc
import dev.slne.surf.npc.api.npc.Npc
import dev.slne.surf.npc.api.npc.skin.NpcSkin
import dev.slne.surf.npc.api.npc.skin.NpcSkinPart
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.util.mutableObject2ObjectMapOf
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Location
import org.bukkit.entity.EntityType

val traderVisibilityService = TraderVisibilityService()

class TraderVisibilityService {
    var visible = false

    val npcs = mutableObject2ObjectMapOf<String, Npc>()

    fun onNewSunnyDay(day: Int) {
        if (isVisible(day)) {
            if (!visible) {
                showNpc()
                visible = true
            }
        } else {
            if (visible) {
                hideNpc()
                visible = false
            }
        }
    }

    fun create() {
        SpawnTraderLocations.entries.forEach {
            val npc = npc {
                displayName {
                    variableValue("Fahrender Händler".toSmallCaps(), TextDecoration.BOLD)
                }
                uniqueName = "spawn_trader_${it.name.lowercase()}"
                location = it.location
                type = EntityType.MANNEQUIN
                skin = NpcSkin(
                    "Trader",
                    "ewogICJ0aW1lc3RhbXAiIDogMTcyOTY0ODAyNzY3NCwKICAicHJvZmlsZUlkIiA6ICIxZGI3ZjUxYWZmOWM0NTcyOGZmNmU5MjMxMmZhZGZkOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJsdXNtZW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBlYjEzZTU4Y2Q5ZmZhYWUxYzExZGVhOTBjYjgxMDAwZTVjZTViZDFiMDk1OWIxNTRmZTUxNzUwM2E4ZjlkIgogICAgfQogIH0KfQ==",
                    "OncYZhOLAQlU7jN5hTMw8U7MQsPS0rpUqlMGD6owDnsxcd4A099LBU1TtHJQTv/YsX+YO/cwduXud7xbZeky2seIiI9S203xCFS/SdvpS7DJH0Znpz3dqGUXndVkC19PRIyaaPDhL6LsTVt7P2ACnbI0LFZkZxd/YK0zqJPFN4XHM7G9bk3R+EXPRoAfQ5otXvW5RwSjetI4bvYL0aJrU5BrWkuIvbdxyUxLtaxEKivqBVjB82/mnf9YiDyX09pVmcmoYGNiu+ggThV2wb4Ru2JBzirA5q0f9v+QTrScIqa0dL28Va9QToQ/OwE16PGJEIPeN0ZBgYjbIjvKED1EzCgoi6xgsWqrAu/8aUesyroTlal8xfsUqAL7lx9crQKbd3A+R8wXrHgaTbYY+qbZIF47lP0/BVyCdNwn1FncoY2iK9pgchp3Qp/2puVgFjw0eVEChET83yFEowAa+aHpNu4ZLs4maJQXwkp4Ai+PBTJxBWHYN0OdgVDBNAGByt7z/zuF8Al8OqANj1oBR7y1Xdsy0K8T63aO02BfrvFWqjoLOXAaRDtS164J5b6siESQ9ZnI26/UEYMy+DpeI9sf2jmwnNliO2FpKVjH+TQHHVmLlvGVeIcNdV+nEu7PAQZyaL9kL2V4OSzj302zpktrPs5Nxe+KxY+qtJLDiVFFM8I=",
                    NpcSkinPart.entries.toObjectSet()
                )
            }

            npcs[it.name] = npc
        }
    }

    private fun isVisible(day: Int): Boolean = day % 10 == 0

    private fun showNpc() {
        npcs.forEach {
            npcs[it.key] = it.value.copy(viewers = null)
        }

        npcs.forEach {
            it.value.refresh()
        }
    }

    private fun hideNpc() {
        npcs.values.forEach {
            it.clearViewers()
        }
    }

    enum class SpawnTraderLocations(
        x: Double,
        y: Double,
        z: Double,
        yaw: Float,
        pitch: Float
    ) {
        CENTRAL(0.0, 100.0, 0.0, 0f, 0f),
        NORTH(0.0, 100.0, -25000.0, 0f, 0f),
        NORTH_EAST(25000.0, 100.0, -25000.0, -45f, 0f),
        NORTH_WEST(-25000.0, 100.0, -25000.0, 45f, 0f),
        SOUTH(0.0, 100.0, 25000.0, 180f, 0f),
        SOUTH_EAST(25000.0, 100.0, 25000.0, -135f, 0f),
        SOUTH_WEST(-25000.0, 100.0, 25000.0, 135f, 0f),
        EAST(25000.0, 100.0, 0.0, -90f, 0f),
        WEST(-25000.0, 100.0, 0.0, 90f, 0f);

        val location = Location(TimeTask.world, x, y, z, yaw, pitch)
    }
}