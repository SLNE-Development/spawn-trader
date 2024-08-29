package dev.slne.spawn.trader.manager;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.object.Trade;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TradeManager {
    private final FileConfiguration storage = SpawnTrader.instance().storage();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy-HH:mm:ss");


    public void setCooldown(Player player, Trade trade){
        LocalDateTime time = LocalDateTime.now();

        storage.set(player.getUniqueId() + "." + trade.id() + ".cooldown", System.currentTimeMillis() + SpawnTrader.instance().tradeCooldown());
        storage.set(player.getUniqueId() + "." + trade.id() + ".last-updated", time.format(formatter));

        SpawnTrader.instance().saveStorage();
    }

    public boolean isOnCooldown(Player player, Trade trade){
        long storageValue = storage.getLong(player.getUniqueId() + "." + trade.id() + ".cooldown");
        long current = System.currentTimeMillis();

        return storageValue >= current;
    }


    public void removeRequirements(Player player, Trade trade){
        if(this.hasEnoughRequirements(player, trade)){
            trade.requirements().forEach(item -> this.removeItem(player, item));
        }else{
            UserManager.instance().getUser(player.getUniqueId()).sendMessage("<red>Du hast nicht ausreichend Materialien dabei.");
        }
    }

    public boolean hasEnoughRequirements(Player player, Trade trade){
        for (ItemStack requirement : trade.requirements()) {
            if(!this.hasItem(player, requirement)){
                return false;
            }
        }
        return true;
    }

    public boolean giveReward(Player player, Trade trade){
        List<ItemStack> items = new ArrayList<>();
        User user = UserManager.instance().getUser(player.getUniqueId());

        for (ItemStack itemStack : player.getInventory().getStorageContents()) {
            if(itemStack != null && itemStack.getType() != Material.AIR){
                items.add(itemStack);
            }
        }


        if(items.size() >= 36){
            user.sendMessage("<red>Du hast nicht ausreichend Platz im Inventar.");
            return false;
        }else{
            trade.rewards().forEach(reward -> player.getInventory().addItem(reward));

            if(trade.id().equals(new FrameTrade().id())){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() +  " item_frame[entity_data={id:\"minecraft:item_frame\",Invisible:1b}] 1");
            }

            user.sendMessage(trade.rewardMessage());
            return true;
        }
    }

    private boolean hasItem(Player player, ItemStack item) {
        int found = 0;

        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null) {
                if(stack.isSimilar(item)) {
                    found += stack.getAmount();

                    if (found >= item.getAmount()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void removeItem(Player player, ItemStack item) {
        int amount = item.getAmount();

        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null) {
                if(stack.isSimilar(item)) {
                    int stackAmount = stack.getAmount();

                    if (stackAmount > item.getAmount()) {
                        stack.setAmount(stackAmount - item.getAmount());
                        break;
                    } else {
                        amount -= stackAmount;
                        stack.setAmount(0);
                    }
                }
            }
        }
    }



    public void buy(User user, Trade trade){
        Player player = user.player();

        if(this.isOnCooldown(player, trade)){
            user.sendMessage("<red>Bitte warte noch.");
            return;
        }

        if(this.hasEnoughRequirements(player, trade)) {
            if (this.giveReward(player, trade)) {
                this.removeRequirements(player, trade);
                this.setCooldown(player, trade);
            }
        }else{
            user.sendMessage("<red>Du hast nicht ausreichend Materialien!");
        }
    }
}
