package dev.slne.spawn.trader.entity.impl;

import dev.slne.spawn.trader.entity.CustomTrader;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;


public class TraderNPC implements CustomTrader {

  @Override
  public String entityTag() {
    return "97067989679834453656";
  }

  @Override
  public World world() {
    return Bukkit.createWorld(new WorldCreator("world"));
  }

  @Override
  public void spawn(double x, double y, double z) {
    final NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Trader");
    npc.spawn(new Location(this.world(), x, y, z));
    npc.shouldRemoveFromTabList();
    npc.getEntity().addScoreboardTag(entityTag());
    npc.setProtected(true);
    npc.setUseMinecraftAI(false);
  }

  @Override
  public void clear() {
    for (final Entity entity : this.world().getEntities()) {
      if (entity.getScoreboardTags().contains(this.entityTag())) {
        entity.remove();
      }
    }
  }
}
