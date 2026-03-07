package dev.slne.spawn.trader.gui

import dev.slne.surf.surfapi.bukkit.api.inventory.framework.titleBuilder
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import me.devnatan.inventoryframework.View
import me.devnatan.inventoryframework.ViewConfigBuilder
import me.devnatan.inventoryframework.context.RenderContext
import net.kyori.adventure.text.format.TextDecoration

object SpawnTraderView : View() {
    override fun onInit(config: ViewConfigBuilder) {
        config
            .titleBuilder {
                primary("Fahrender Händler".toSmallCaps(), TextDecoration.BOLD)
            }
            .size(6)
            .layout(
                "OOOOOOOOO",
                "ORRRRRRRO",
                "ORRRRRRRO",
                "ORRRRRRRO",
                "ORRRRRRRO",
                "OOOOBOOOO"
            )
            .cancelInteractions()
    }

    override fun onFirstRender(render: RenderContext) {

    }
}