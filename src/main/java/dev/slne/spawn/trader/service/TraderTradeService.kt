package dev.slne.spawn.trader.service

import dev.slne.spawn.trader.trades.Trades
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.transaction.api.currency.Currency
import dev.slne.surf.transaction.api.transaction.TransactionResult
import dev.slne.surf.transaction.api.user.TransactionUser
import org.bukkit.entity.Player
import java.util.*

val traderTradeService = TraderTradeService()

class TraderTradeService {
    private val cooldowns = mutableMapOf<UUID, Long>()

    suspend fun buy(trade: Trades, player: Player) {
        val now = System.currentTimeMillis()
        val last = cooldowns[player.uniqueId]

        if (last != null && now - last < 1000) {
            player.sendText {
                appendErrorPrefix()
                error("Bitte warte einen Moment, bevor du erneut versuchst, etwas zu kaufen.")
            }
            return
        }

        cooldowns[player.uniqueId] = now
        val price = trade.price

        val result =
            TransactionUser[player.uniqueId].withdraw(price.toBigDecimal(), Currency.default())

        when (result) {
            is TransactionResult.DatabaseError -> {
                player.sendText {
                    appendErrorPrefix()
                    error("Ein Fehler ist aufgetreten. Bitte versuche es später erneut.")
                }
            }

            TransactionResult.SenderInsufficientFunds -> {
                player.sendText {
                    appendErrorPrefix()
                    error("Der Händler hat nicht genug Geld, um den Kauf abzuschließen. Bitte versuche es später erneut.")
                }
            }

            TransactionResult.ReceiverInsufficientFunds -> {
                player.sendText {
                    appendErrorPrefix()
                    error("Du hast nicht genug Geld, um diesen Kauf abzuschließen.")
                }
            }

            else -> {
                player.inventory.addItem(trade.singleItem).forEach { (_, stack) ->
                    player.world.dropItem(player.location, stack).owner = player.uniqueId
                }

                player.sendText {
                    appendSuccessPrefix()
                    success("Du hast ")
                    variableValue("1x ")
                    append(trade.displayName)
                    success("für ")
                    variableValue("${price}CC")
                    success(" gekauft!")
                }
            }
        }
    }
}