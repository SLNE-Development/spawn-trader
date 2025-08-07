package dev.slne.spawn.trader.entity

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.spawn.trader.entity.impl.TraderNPC
import dev.slne.spawn.trader.gui.SpawnTraderGUI
import dev.slne.spawn.trader.plugin
import dev.slne.surf.npc.api.event.NpcInteractEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EntityInteractListener : Listener {
    @EventHandler
    fun onNpcInteract(event: NpcInteractEvent) {
        val npc = event.npc

        if (npc == TraderNPC.npc) {
            plugin.launch(plugin.entityDispatcher(event.player)) {
                SpawnTraderGUI(event.player).show(event.player)
            }
        }
    }
}
