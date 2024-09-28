package dev.slne.spawn.trader.gui;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;

import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.LightTrade;
import dev.slne.spawn.trader.util.ItemBuilder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * The type Spawn trader gui.
 */
public class SpawnTraderGUI extends ChestGui {

  public static final ItemStack LIGHT_BLOCK_ITEMSTACK = new ItemBuilder(Material.LIGHT)
          .setName(Component.text("20x Licht Bl\u00F6cke").color(NamedTextColor.YELLOW))
          .addLoreLine(Component.text("Preis: ")
                  .color(NamedTextColor.GRAY)
                  .append(Component.text("20x Redstone-Lampe und 5x Smaragd").color(NamedTextColor.WHITE))
          )
          .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
          .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
          .setCustomModelData(42)
          .build();

  public static final ItemStack FRAME_ITEM_STACK = new ItemBuilder(Material.ITEM_FRAME)
          .setName(Component.text("20x Unsichtbarer Item Rahmen").color(NamedTextColor.YELLOW))
          .addLoreLine(Component.text("Preis: ")
                  .color(NamedTextColor.GRAY)
                  .append(Component.text("20x Item-Rahmen und 5x Smaragd").color(NamedTextColor.WHITE))
          )
          .setCustomModelData(42)
          .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
          .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
          .build();

  private final TradeManager tradeManager = SpawnTrader.instance().tradeManager();
  private final ItemStack available = new ItemBuilder(Material.LIME_DYE)
          .setName(Component.text("Verf\u00FCgbar").color(NamedTextColor.GREEN))
          .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
          .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
          .setCustomModelData(42)
          .build();

  private final ItemStack locked = new ItemBuilder(Material.RED_DYE)
          .setName(Component.text("Nicht Verf\u00FCgbar").color(NamedTextColor.RED))
          .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
          .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
          .setCustomModelData(42)
          .build();

  private final OutlinePane lightStatusPane = new OutlinePane(2, 3, 1, 1);
  private final OutlinePane frameStatusPane = new OutlinePane(6, 3, 1, 1);

  private final FrameTrade frameTrade = new FrameTrade();
  private final LightTrade lightTrade = new LightTrade();

  private final Player player;

  /**
   * Instantiates a new Spawn trader gui.
   *
   * @param player the player
   */
  public SpawnTraderGUI(Player player) {
    super(6, ComponentHolder.of(Component.text("H\u00E4ndler").color(NamedTextColor.BLACK)));

    this.player = player;

    final OutlinePane lightPane = new OutlinePane(2, 2, 1, 1, Pane.Priority.LOW);
    final OutlinePane framePane = new OutlinePane(6, 2, 1, 1, Pane.Priority.LOW);
    final OutlinePane footer = new OutlinePane(0, 5, 9, 1, Pane.Priority.LOW);
    final OutlinePane header = new OutlinePane(0, 0, 9, 1, Pane.Priority.LOW);

    this.setOnGlobalClick(event -> event.setCancelled(true));
    this.setOnGlobalDrag(event -> event.setCancelled(true));

    header.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("").build()));
    header.setRepeat(true);

    footer.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("").build()));
    footer.setRepeat(true);

    lightPane.addItem(new GuiItem(LIGHT_BLOCK_ITEMSTACK, event -> {
      tradeManager.buy(player, lightTrade);
      this.setStatusItems();
    }));

    framePane.addItem(new GuiItem(FRAME_ITEM_STACK, event -> {
      tradeManager.buy(player, frameTrade);
      this.setStatusItems();
    }));

    this.addPane(framePane);
    this.addPane(lightPane);
    this.addPane(frameStatusPane);
    this.addPane(lightStatusPane);
    this.addPane(header);
    this.addPane(footer);

    this.setStatusItems();
  }

  /**
   * Sets status items.
   */
  private void setStatusItems() {
    lightStatusPane.clear();
    frameStatusPane.clear();

    if (tradeManager.isOnCooldown(player, lightTrade)) {
      lightStatusPane.addItem(new GuiItem(locked));
    } else {
      lightStatusPane.addItem(new GuiItem(available));
    }

    if (tradeManager.isOnCooldown(player, frameTrade)) {
      frameStatusPane.addItem(new GuiItem(locked));
    } else {
      frameStatusPane.addItem(new GuiItem(available));
    }
  }
}
