package dev.slne.spawn.trader.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemBuilder {
    private final ItemStack is;
    /**
     * Create a new ItemBuilder from scratch.
     * @param m The material to create the ItemBuilder with.
     */
    public ItemBuilder(Material m){
        this(m, 1);
    }
    /**
     * Create a new ItemBuilder over an existing itemstack.
     * @param is The itemstack to create the ItemBuilder over.
     */
    public ItemBuilder(ItemStack is){
        this.is=is;
    }
    /**
     * Create a new ItemBuilder from scratch.
     * @param m The material of the item.
     * @param amount The amount of the item.
     */
    public ItemBuilder(Material m, int amount){
        is= new ItemStack(m, amount);
    }

    /**
     * Create a new ItemBuilder from scratch.
     * @param m The material of the item.
     * @param amount The amount of the item.
     * @param durability The durability of the item.
     */
    public ItemBuilder(Material m, int amount, byte durability){
        is = new ItemStack(m, amount, durability);
    }
    /**
     * Clone the ItemBuilder into a new one.
     * @return The cloned instance.
     */
    public ItemBuilder clone(){
        return new ItemBuilder(is);
    }
    /**
     * Change the durability of the item.
     * @param dur The durability to set it to.
     */
    public ItemBuilder setDurability(short dur){
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setAmount(int amount){
        is.setAmount(amount);
        return this;
    }

    public ItemBuilder setCustomModelData(int amount){
        ItemMeta im = is.getItemMeta();
        im.setCustomModelData(amount);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEffect(PotionEffectType type, int duration, int amplifier){
        PotionMeta im = (PotionMeta) is.getItemMeta();
        im.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addDataToPersistenceContainer(NamespacedKey key, PersistentDataType type, Object value){
        ItemMeta im = is.getItemMeta();
        PersistentDataContainer container = im.getPersistentDataContainer();
        container.set(key, type, value);

        is.setItemMeta(im);
        return this;
    }

    /**
     * Set the displayname of the item.
     * @param name The name to change it to.
     */
    public ItemBuilder setName(Component name){
        ItemMeta im = is.getItemMeta();
        im.displayName(name);
        is.setItemMeta(im);
        return this;
    }
    public ItemBuilder setName(String name){
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Add an unsafe enchantment.
     * @param ench The enchantment to add.
     * @param level The level to put the enchant on.
     */
    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level){
        is.addUnsafeEnchantment(ench, level);
        return this;
    }
    /**
     * Remove a certain enchant from the item.
     * @param ench The enchantment to remove
     */
    public ItemBuilder removeEnchantment(Enchantment ench){
        is.removeEnchantment(ench);
        return this;
    }
    /**
     * Set the skull owner for the item. Works on skulls only.
     * @param owner The name of the skull's owner.
     */
    public ItemBuilder setSkullOwner(String owner){
        try{
            SkullMeta im = (SkullMeta)is.getItemMeta();
            im.setOwner(owner);
            is.setItemMeta(im);
        }catch(ClassCastException expected){}
        return this;
    }
    /**
     * Add an enchant to the item.
     * @param enchantment The enchant to add
     * @param level The level
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        ItemMeta im = is.getItemMeta();
        im.addEnchant(enchantment, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean b){
        ItemMeta im = is.getItemMeta();
        im.setUnbreakable(b);
        is.setItemMeta(im);
        return this;
    }
    public ItemBuilder addItemFlag(ItemFlag flag){
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(flag);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Add multiple enchants at once.
     * @param enchantments The enchants to add.
     */
    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments){
        is.addEnchantments(enchantments);
        return this;
    }
    /**
     * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
     */
    public ItemBuilder setInfinityDurability(){
        is.setDurability(Short.MAX_VALUE);
        return this;
    }
    /**
     * Re-sets the lore.
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(Component... lore){
        ItemMeta im = is.getItemMeta();
        im.lore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }
    /**
     * Re-sets the lore.
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(List<Component> lore) {
        ItemMeta im = is.getItemMeta();
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Remove a lore line.
     * @param line The line to remove.
     */
    public ItemBuilder removeLoreLine(Component line){
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>(im.lore());
        if(!lore.contains(line)) return this;
        lore.remove(line);
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Remove a lore line.
     * @param index The index of the lore line to remove.
     */
    public ItemBuilder removeLoreLine(int index){
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>(im.lore());
        if(index<0||index>lore.size())return this;
        lore.remove(index);
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Add a lore line.
     * @param line The lore line to add.
     */
    public ItemBuilder addLoreLine(Component line){
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>();
        if(im.hasLore())lore = new ArrayList<>(im.lore());
        lore.add(line);
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Add a lore line.
     * @param line The lore line to add.
     * @param pos The index of where to put it.
     */
    public ItemBuilder addLoreLine(Component line, int pos){
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>(im.lore());
        lore.set(pos, line);
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }
    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor pieces.
     * @param color The color to set it to.
     */
    public ItemBuilder setLeatherArmorColor(Color color){
        try{
            LeatherArmorMeta im = (LeatherArmorMeta)is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        }catch(ClassCastException expected){}
        return this;
    }
    /**
     * Retrieves the itemstack from the ItemBuilder.
     * @return The itemstack created/modified by the ItemBuilder instance.
     */
    public ItemStack build(){
        return is;
    }
}