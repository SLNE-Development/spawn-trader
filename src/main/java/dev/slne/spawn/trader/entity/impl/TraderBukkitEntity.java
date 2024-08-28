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

public class TraderBukkitEntity implements CustomTrader {
    @Override
    public String ENTITY_TAG() {
        return "9784986896784573945876097684576457864";
    }

    @Override
    public World world() {
        return Bukkit.createWorld(new WorldCreator("world"));
    }

    @Override
    public void spawn(double x, double y, double z){
        Witch witch = (Witch) world().spawnEntity(new Location(world(), x, y, z), EntityType.WITCH);

        witch.addScoreboardTag(ENTITY_TAG());
        witch.setInvulnerable(true);
        witch.setAggressive(false);
        witch.setAI(false);
        witch.setSilent(true);

        if(witch instanceof LivingEntity entity){
            entity.setRemoveWhenFarAway(false);
        }
    }

    @Override
    public void clear() {
        for (Entity entity : this.world().getEntities()) {
            if(entity.getScoreboardTags().contains(this.ENTITY_TAG())){
                entity.remove();
            }
        }
    }
}
