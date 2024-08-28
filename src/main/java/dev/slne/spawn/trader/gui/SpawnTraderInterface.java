package dev.slne.spawn.trader.gui;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.Trade;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.user.User;
import dev.slne.spawn.trader.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class SpawnTraderInterface {
    private final TradeManager tradeManager = SpawnTrader.instance().tradeManager();

    public void open(Player player){
        ChestGui gui = new ChestGui(6, ComponentHolder.of(SpawnTrader.deserialize("<black>Haendler")));
        User user = User.user(player);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 6, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(SpawnTrader.deserialize("<gray>Haendler")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP).build()));
        background.setRepeat(true);

        gui.addPane(background);

        OutlinePane lightPane = new OutlinePane(3, 2, 1, 1);
        OutlinePane framePane = new OutlinePane(5, 2, 1, 1);
        OutlinePane lightStatusPane = new OutlinePane(3, 3, 1, 1);
        OutlinePane frameStatusPane = new OutlinePane(5, 3, 1, 1);

        ItemStack lightItem = new ItemBuilder(Material.LIGHT)
                .setName(SpawnTrader.deserialize("<yellow>20x Licht Bloecke"))
                .addLoreLine(SpawnTrader.deserialize("<gray>Preis: <white>20x Redstone-Lampe und 5x Smaragt"))
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .build();

        ItemStack frameItem = new ItemBuilder(Material.ITEM_FRAME)
                .setName(SpawnTrader.deserialize("<yellow>20x Unsichtbarer Item Rahmen"))
                .addLoreLine(SpawnTrader.deserialize("<gray>Preis: <white>20x Item-Rahmen und 5x Smaragt"))
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .build();

        ItemStack available = new ItemBuilder(Material.GREEN_CONCRETE_POWDER)
                .setName(SpawnTrader.deserialize("<green>Verfuegbar"))
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .build();

        ItemStack locked = new ItemBuilder(Material.RED_CONCRETE_POWDER)
                .setName(SpawnTrader.deserialize("<red>Nicht Verfuegbar"))
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .build();

        lightPane.addItem(new GuiItem(lightItem, event -> {
            if(tradeManager.isOnCooldown(player, Trade.LIGHT_BLOCK)){
                user.sendMessage("<red>Bitte warte noch.");
                return;
            }

            if(tradeManager.hasEnoughRequirements(player, Trade.LIGHT_BLOCK)) {
                if (tradeManager.giveReward(player, Trade.LIGHT_BLOCK)) {
                    tradeManager.removeRequirements(player, Trade.LIGHT_BLOCK);
                    tradeManager.setCooldown(player, Trade.LIGHT_BLOCK);
                }
            }else{
                user.sendMessage("<red>Du hast nicht ausreichend Materialien!");
            }
        }));

        framePane.addItem(new GuiItem(frameItem, event -> {
            if(tradeManager.isOnCooldown(player, Trade.ITEM_FRAME)){
                user.sendMessage("<red>Bitte warte noch.");
                return;
            }

            if(tradeManager.hasEnoughRequirements(player, Trade.ITEM_FRAME)) {

                if (tradeManager.giveReward(player, Trade.ITEM_FRAME)) {
                    tradeManager.removeRequirements(player, Trade.ITEM_FRAME);
                    tradeManager.setCooldown(player, Trade.ITEM_FRAME);
                }
            }else{
                user.sendMessage("<red>Du hast nicht ausreichend Materialien!");
            }

        }));

        if(tradeManager.isOnCooldown(player, Trade.LIGHT_BLOCK)){
            lightStatusPane.addItem(new GuiItem(locked));
        }else{
            lightStatusPane.addItem(new GuiItem(available));
        }

        if(tradeManager.isOnCooldown(player, Trade.ITEM_FRAME)){
            frameStatusPane.addItem(new GuiItem(locked));
        }else{
            frameStatusPane.addItem(new GuiItem(available));
        }

        gui.addPane(framePane);
        gui.addPane(lightPane);
        gui.addPane(frameStatusPane);
        gui.addPane(lightStatusPane);

        gui.show(player);
    }
}
