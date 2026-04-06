package dev.slne.spawn.trader.gui

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.displayName
import me.devnatan.inventoryframework.View
import me.devnatan.inventoryframework.context.Context
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.inventory.ItemStack

val View.outlineItem: ItemStack
    get() = buildItem(Material.GRAY_STAINED_GLASS_PANE) {
        displayName {
            spacer("")
        }
    }

fun Context.playGeneralClickSound() {
    player.playSound(true) {
        type(Sound.UI_BUTTON_CLICK)
    }
}

fun Context.playNewPageSound() {
    player.playSound(true) {
        type(Sound.ENTITY_CHICKEN_EGG)
    }
}

val previousItem = MenuHeads.ARROW_LEFT.clone().apply {
    displayName {
        variableValue("Vorherige Seite")
    }
}

val nextItem = MenuHeads.ARROW_RIGHT.clone().apply {
    displayName {
        variableValue("Nächste Seite")
    }
}

val backItem = MenuHeads.CROSS.apply {
    displayName {
        primary("Zurück".toSmallCaps(), TextDecoration.BOLD)
    }
}

val closeItem = MenuHeads.CROSS.apply {
    displayName {
        primary("Schließen".toSmallCaps(), TextDecoration.BOLD)
    }
}