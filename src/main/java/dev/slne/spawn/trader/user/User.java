package dev.slne.spawn.trader.user;


import dev.slne.spawn.trader.SpawnTrader;
import lombok.Data;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
@Accessors(fluent = true)
public class User {
    private UUID uuid;

    public User(UUID uuid){
        this.uuid = uuid;
    }

    public void sendMessage(String message){
        this.player().sendMessage(MiniMessage.miniMessage().deserialize(SpawnTrader.instance().prefix() + message));
    }

    public void sendMessage(Component message){
        this.player().sendMessage(message);
    }

    public Player player(){
        return Bukkit.getPlayer(uuid);
    }
}
