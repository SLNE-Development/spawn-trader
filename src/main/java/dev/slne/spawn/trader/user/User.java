package dev.slne.spawn.trader.user;


import dev.slne.spawn.trader.SpawnTrader;
import lombok.Data;
import lombok.experimental.Accessors;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.time.Duration;
import java.util.UUID;

@Data
@Accessors(fluent = true)
public class User {
    private UUID uuid;
    private String name;
    private Player player;
    private OfflinePlayer offlinePlayer;


    public void sendMessage(String message){
        this.player().sendMessage(MiniMessage.miniMessage().deserialize(SpawnTrader.instance().prefix() + message));
    }

    public void teleport(Location location){
        this.player().teleport(location);
    }

    public void teleport(Player player){
        this.player().teleport(player);
    }

    public void send(World world){
        this.player().teleport(world.getSpawnLocation());
    }

    public void sendActionbar(String message){
        this.player().sendActionBar(MiniMessage.miniMessage().deserialize(message));
    }

    public void sendTitle(String title, String subTitle, Duration fadeIn, Duration stay, Duration fadeOut){
        this.player().showTitle(Title.title(MiniMessage.miniMessage().deserialize(title), MiniMessage.miniMessage().deserialize(subTitle), Title.Times.times(fadeIn, stay, fadeOut)));
    }

    public void sendMessageRaw(String message){
        this.player().sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    public void playSound(Key key, float volume, float pitch){
        player.playSound(Sound.sound(key, Sound.Source.VOICE, volume, pitch));
    }

    public void openInventory(Inventory inventory){
        player.openInventory(inventory);
    }


    public static User user(String name){
        User user = new User();

        user.player(Bukkit.getPlayer(name));
        user.name(user.player().getName());
        user.uuid(user.player().getUniqueId());
        user.offlinePlayer(user.player());

        return user;
    }

    public static User user(Player player){
        User user = new User();

        user.player(player);
        user.name(user.player().getName());
        user.uuid(user.player().getUniqueId());
        user.offlinePlayer(user.player());

        return user;
    }

    public static User user(UUID uuid){
        User user = new User();

        user.player(Bukkit.getPlayer(uuid));
        user.name(user.player().getName());
        user.uuid(user.player().getUniqueId());
        user.offlinePlayer(user.player());

        return user;
    }
}
