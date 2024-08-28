package dev.slne.spawn.trader.manager;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.user.User;
import dev.slne.spawn.trader.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TradeManager {
    private final ItemStack itemFramePrice1 = new ItemBuilder(Material.ITEM_FRAME, 20).build();
    private final ItemStack itemFramePrice2 = new ItemBuilder(Material.EMERALD, 5).build();
    private final ItemStack itemFrameReward = new ItemBuilder(Material.ITEM_FRAME, 20).build();

    private final ItemStack lightBlockPrice1 = new ItemBuilder(Material.REDSTONE_LAMP, 20).build();
    private final ItemStack lightBlockPrice2 = new ItemBuilder(Material.EMERALD, 5).build();
    private final ItemStack lightBlockReward = new ItemBuilder(Material.LIGHT, 20).build();


    public void setCooldown(Player player, Trade trade){
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy-HH:mm:ss");


        SpawnTrader.instance().storage().set(player.getUniqueId() + "." + trade + ".cooldown", System.currentTimeMillis() + SpawnTrader.instance().tradeCooldown());
        SpawnTrader.instance().storage().set(player.getUniqueId() + "." + trade + ".last-updated", time.format(formatter));

        try {
            SpawnTrader.instance().storage().save(SpawnTrader.instance().storageFile());
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }

    public boolean isOnCooldown(Player player, Trade trade){
        long configValue = SpawnTrader.instance().storage().getLong(player.getUniqueId() + "." + trade.toString() + ".cooldown");
        long current = System.currentTimeMillis();

        if(configValue <= current){
            return false;
        }

        return true;
    }


    public void removeRequirements(Player player, Trade trade){
        if(this.hasEnoughRequirements(player, trade)){
            switch (trade){
                case ITEM_FRAME -> {
                    this.removeItem(player, this.itemFramePrice1);
                    this.removeItem(player, this.itemFramePrice2);
                }

                case LIGHT_BLOCK -> {
                    this.removeItem(player, this.lightBlockPrice1);
                    this.removeItem(player, this.lightBlockPrice2);
                }
            }
        }else{
            User.user(player).sendMessage("<red>Du hast nicht ausreichend Materialien dabei.");
        }
    }

    public boolean hasEnoughRequirements(Player player, Trade trade){
        switch (trade){
            case ITEM_FRAME -> {
                if(this.hasItem(player, itemFramePrice1) && this.hasItem(player, itemFramePrice2)){
                    return true;
                }
            }

            case LIGHT_BLOCK -> {
                if(this.hasItem(player, lightBlockPrice1) && this.hasItem(player, lightBlockPrice2)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean giveReward(Player player, Trade trade){
        List<ItemStack> items = new ArrayList<>();

        for (ItemStack itemStack : player.getInventory().getStorageContents()) {
            if(itemStack != null && itemStack.getType() != Material.AIR){
                items.add(itemStack);
            }
        }


        if(items.size() >= 36){
            User.user(player).sendMessage("<red>Du hast nicht ausreichend Platz im Inventar.");
            return false;
        }else{
            switch (trade){
                case ITEM_FRAME -> {
                    player.getInventory().addItem(itemFrameReward);
                    User.user(player).sendMessage("Danke fuer den Einkauf von 20x Item-Rahmen!");
                }
                case LIGHT_BLOCK -> {
                    player.getInventory().addItem(lightBlockReward);
                    User.user(player).sendMessage("Danke fuer den Einkauf von 20x Licht-Bloecken!");
                }
            }
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
}
