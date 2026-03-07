package dev.slne.spawn.trader.service

import dev.slne.spawn.trader.trades.Trades
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.transaction.api.currency.Currency
import dev.slne.surf.transaction.api.user.TransactionUser
import org.bukkit.entity.Player

val traderTradeService = TraderTradeService()

class TraderTradeService {
    suspend fun buy(trade: Trades, player: Player) {
        val price = trade.price

        val result =
            TransactionUser[player.uniqueId].withdraw(price.toBigDecimal(), Currency.default())

        if (result.success) {
            player.inventory.addItem(trade.singleItem).forEach { (_, stack) ->
                player.world.dropItem(player.location, stack).owner = player.uniqueId
            }

            player.sendText {
                appendSuccessPrefix()
                success("Du hast ")
                variableValue("1x")
                success(" ${trade.displayName} ")
                success("für ")
                variableValue("${price}CC")
                success(" gekauft!")
            }
        }
    }
}