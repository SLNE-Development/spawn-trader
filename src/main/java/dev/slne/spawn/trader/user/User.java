package dev.slne.spawn.trader.user;


import dev.slne.spawn.trader.SpawnTrader;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The type User.
 */
@Data
@Accessors(fluent = true)
public class User {

  private UUID uuid;

  /**
   * Instantiates a new User.
   *
   * @param uuid the uuid
   */
  public User(UUID uuid) {
    this.uuid = uuid;
  }

  /**
   * Send message.
   *
   * @param message the message
   */
  public void sendMessage(String message) {
    this.player().sendMessage(
        MiniMessage.miniMessage().deserialize(SpawnTrader.instance().prefix() + message));
  }

  /**
   * Player player.
   *
   * @return the player
   */
  public Player player() {
    return Bukkit.getPlayer(uuid);
  }
}
