package dev.slne.spawn.trader.entity.impl;

import dev.slne.spawn.trader.entity.CustomTrader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Witch;

/**
 * The type Trader bukkit entity.
 */
public class TraderBukkitEntity implements CustomTrader {

  @Override
  public String entityTag() {
    return "9784986896784573945876097684576457864";
  }

  @Override
  public World world() {
    return Bukkit.getWorlds().getFirst();
  }

  @Override
  public void spawn(double x, double y, double z) {
    final Witch witch = (Witch) world().spawnEntity(new Location(world(), x, y, z),
        EntityType.WITCH);

    witch.addScoreboardTag(entityTag());
    witch.setInvulnerable(true);
    witch.setAggressive(false);
    witch.setAI(false);
    witch.setSilent(true);

    if (witch instanceof LivingEntity entity) {
      entity.setRemoveWhenFarAway(false);
    }
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
