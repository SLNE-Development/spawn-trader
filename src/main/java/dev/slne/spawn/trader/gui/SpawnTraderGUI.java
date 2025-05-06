package dev.slne.spawn.trader.gui;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;

import dev.slne.spawn.trader.SpawnTrader;
import dev.slne.spawn.trader.manager.TradeManager;
import dev.slne.spawn.trader.manager.object.impl.FrameTrade;
import dev.slne.spawn.trader.manager.object.impl.GlobeTrade;
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
      .setName(Component.text("20x Licht Blöcke").color(NamedTextColor.YELLOW))
      .addLoreLine(Component.empty())
      .addLoreLine(Component.text("Preis: ", NamedTextColor.GRAY).append(Component.text("20x Redstone-Lampe und 5x Smaragd", NamedTextColor.WHITE)))
      .addLoreLine(Component.empty())
      .addLoreLine(Component.text("Eine magische und unsichtbare Lampe, mit Lichtstärke 15.", NamedTextColor.GRAY))
      .addLoreLine(Component.text("Hergestellt unter heißen Temperaturen in der tiefe des Vulkans.", NamedTextColor.GRAY))
      .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
      .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
      .build();

  public static final ItemStack FRAME_ITEM_STACK = new ItemBuilder(Material.ITEM_FRAME)
      .setName(Component.text("20x Unsichtbarer Item Rahmen").color(NamedTextColor.YELLOW))
      .addLoreLine(Component.empty())
      .addLoreLine(Component.text("Preis: ", NamedTextColor.GRAY).append(Component.text("20x Item-Rahmen und 5x Smaragd", NamedTextColor.WHITE)))
      .addLoreLine(Component.empty())
      .addLoreLine(Component.text("Ein fast unsichtbarer Rahmen für geheime Präsentationen.", NamedTextColor.GRAY))
      .addLoreLine(Component.text("Wurde mit Illusionsmagie von alten Bibliothekaren verzaubert.", NamedTextColor.GRAY))
      .setCustomModelData(42)
      .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
      .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
      .build();


  public static final ItemStack GLOBE_BANNER_STACK = new ItemBuilder(Material.GLOBE_BANNER_PATTERN)
      .setName(Component.text("1x Globe Banner Vorlage").color(NamedTextColor.YELLOW))
      .addLoreLine(Component.empty())
      .addLoreLine(Component.text("Preis: ").color(NamedTextColor.GRAY).append(Component.text("20x Papier und 20x Smaragd").color(NamedTextColor.WHITE)))
      .addLoreLine(Component.empty())
      .addLoreLine(Component.text("Ein uraltes Symbol, das einst nur Königen vorbehalten war.", NamedTextColor.GRAY))
      .addLoreLine(Component.text("Gefunden in den Ruinen einer vergessenen Zivilisation.", NamedTextColor.GRAY))
      .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
      .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
      .build();


  private final TradeManager tradeManager = SpawnTrader.instance().tradeManager();
  private final ItemStack available = new ItemBuilder(Material.LIME_DYE)
      .setName(Component.text("Verfügbar").color(NamedTextColor.GREEN))
      .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
      .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
      .build();

  private final ItemStack locked = new ItemBuilder(Material.RED_DYE)
      .setName(Component.text("Nicht Verfügbar").color(NamedTextColor.RED))
      .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
      .addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
      .build();

  private final OutlinePane lightStatusPane = new OutlinePane(2, 3, 1, 1);
  private final OutlinePane frameStatusPane = new OutlinePane(6, 3, 1, 1);
  private final OutlinePane globeStatusPane = new OutlinePane(4, 3, 1, 1);

  private final FrameTrade frameTrade = new FrameTrade();
  private final LightTrade lightTrade = new LightTrade();
  private final GlobeTrade globeTrade = new GlobeTrade();

  private final Player player;

  /**
   * Instantiates a new Spawn trader gui.
   *
   * @param player the player
   */
  public SpawnTraderGUI(Player player) {
    super(6, ComponentHolder.of(Component.text("Händler").color(NamedTextColor.BLACK)));

    this.player = player;

    final OutlinePane lightPane = new OutlinePane(2, 2, 1, 1, Pane.Priority.LOW);
    final OutlinePane framePane = new OutlinePane(6, 2, 1, 1, Pane.Priority.LOW);
    final OutlinePane globePane = new OutlinePane(4, 2, 1, 1, Pane.Priority.LOW);
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
      new SpawnTraderGUI(player).show(player);
    }));

    framePane.addItem(new GuiItem(FRAME_ITEM_STACK, event -> {
      tradeManager.buy(player, frameTrade);
      new SpawnTraderGUI(player).show(player);
    }));

    globePane.addItem(new GuiItem(GLOBE_BANNER_STACK, event -> {
      tradeManager.buy(player, globeTrade);
      new SpawnTraderGUI(player).show(player);
    }));

    this.addPane(framePane);
    this.addPane(lightPane);
    this.addPane(globePane);
    this.addPane(frameStatusPane);
    this.addPane(lightStatusPane);
    this.addPane(globeStatusPane);
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

    if (tradeManager.isOnCooldown(player, globeTrade)) {
      globeStatusPane.addItem(new GuiItem(locked));
    } else {
      globeStatusPane.addItem(new GuiItem(available));
    }
  }
}
