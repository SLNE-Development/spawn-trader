package dev.slne.spawn.trader.gui;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.UserManager;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;
import dev.slne.spawn.trader.user.User;
import dev.slne.spawn.trader.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class SpawnTraderGUI extends ChestGui {
    private final TradeManager tradeManager = SpawnTrader.instance().tradeManager();
    private final ItemStack available = new ItemBuilder(Material.GREEN_CONCRETE_POWDER).setName(SpawnTrader.deserialize("<green>Verfuegbar")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP).build();
    private final ItemStack locked = new ItemBuilder(Material.RED_CONCRETE_POWDER).setName(SpawnTrader.deserialize("<red>Nicht Verfuegbar")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP).build();
    private final OutlinePane lightStatusPane = new OutlinePane(3, 3, 1, 1);
    private final OutlinePane frameStatusPane = new OutlinePane(5, 3, 1, 1);

    private final FrameTrade frameTrade = new FrameTrade();
    private final LightTrade lightTrade = new LightTrade();

    private final Player player;

    public SpawnTraderGUI(Player player) {
        super(6, ComponentHolder.of(SpawnTrader.deserialize("<black>Haendler")));
        this.player = player;

        User user = UserManager.instance().getUser(player.getUniqueId());
        ItemStack lightItem = new ItemBuilder(Material.LIGHT).setName(SpawnTrader.deserialize("<yellow>20x Licht Bloecke")).addLoreLine(SpawnTrader.deserialize("<gray>Preis: <white>20x Redstone-Lampe und 5x Smaragt")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP).build();
        ItemStack frameItem = new ItemBuilder(Material.ITEM_FRAME).setName(SpawnTrader.deserialize("<yellow>20x Unsichtbarer Item Rahmen")).addLoreLine(SpawnTrader.deserialize("<gray>Preis: <white>20x Item-Rahmen und 5x Smaragt")).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP).build();
        OutlinePane lightPane = new OutlinePane(3, 2, 1, 1, Pane.Priority.LOW);
        OutlinePane framePane = new OutlinePane(5, 2, 1, 1, Pane.Priority.LOW);
        OutlinePane background = new OutlinePane(0, 0, 9, 6, Pane.Priority.LOWEST);

        this.setOnGlobalClick(event -> event.setCancelled(true));
        this.setOnGlobalDrag(event -> event.setCancelled(true));

        background.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP).build()));
        background.setRepeat(true);

        lightPane.addItem(new GuiItem(lightItem, event -> {
            tradeManager.buy(user, lightTrade);
            this.setStatusItems();
        }));

        framePane.addItem(new GuiItem(frameItem, event -> {
            tradeManager.buy(user, frameTrade);
            this.setStatusItems();

        }));

        this.addPane(background);
        this.addPane(framePane);
        this.addPane(lightPane);
        this.addPane(frameStatusPane);
        this.addPane(lightStatusPane);

        this.setStatusItems();
    }

    public void setStatusItems(){
        if(tradeManager.isOnCooldown(player, lightTrade)){
            lightStatusPane.addItem(new GuiItem(locked));
        }else{
            lightStatusPane.addItem(new GuiItem(available));
        }

        if(tradeManager.isOnCooldown(player, frameTrade)){
            frameStatusPane.addItem(new GuiItem(locked));
        }else{
            frameStatusPane.addItem(new GuiItem(available));
        }
    }
}
