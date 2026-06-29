package dev.slne.spawn.trader.service

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import dev.slne.spawn.trader.plugin
import dev.slne.spawn.trader.trades.Trades
import dev.slne.spawn.trader.util.formatPrice
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.transaction.api.currency.Currency
import dev.slne.surf.transaction.api.transaction.TransactionResult
import dev.slne.surf.transaction.api.user.TransactionUser
import kotlinx.coroutines.withContext
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

val traderTradeService = TraderTradeService()

class TraderTradeService {
    private val inProcess = ConcurrentHashMap.newKeySet<UUID>()

    suspend fun buy(trade: Trades, player: Player) {
        val uuid = player.uniqueId
        val started = inProcess.add(uuid)

        if (!started) {
            player.sendText {
                appendErrorPrefix()
                error("Bitte warte, bis dein aktueller Kauf abgeschlossen ist.")
            }
            return
        }

        try {
            val price = trade.price

            val result =
                TransactionUser[uuid].withdraw(price.toBigDecimal(), Currency.default())

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
                    withContext(plugin.entityDispatcher(player)) {
                        player.inventory.addItem(trade.singleItem).forEach { (_, stack) ->
                            player.world.dropItem(player.location, stack).owner = uuid
                        }
                    }

                    player.sendText {
                        appendSuccessPrefix()
                        success("Du hast ")
                        variableValue("1x ")
                        append(trade.displayName)
                        success(" für ")
                        variableValue(formatPrice(price))
                        success(" gekauft!")
                    }
                }
            }
        } finally {
            inProcess.remove(uuid)
        }
    }
}