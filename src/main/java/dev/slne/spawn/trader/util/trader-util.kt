package dev.slne.spawn.trader.util

import dev.slne.surf.surfapi.core.api.messages.builder.SurfComponentBuilder
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

fun SurfComponentBuilder.traderColored(text: Any, vararg decoration: TextDecoration) =
    coloredComponent(text.toString(), TextColor.color(167, 245, 66), *decoration)

fun formatPrice(price: Int) = "%,d".format(price).replace(',', '.') + "CC"