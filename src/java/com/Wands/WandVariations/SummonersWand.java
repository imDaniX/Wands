package com.Wands.WandVariations;

import com.Wands.BukkitUtils;
import com.Wands.Main;
import com.Wands.Wand;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SummonersWand extends Wand {

    int range = 5,				// range that the wolfs spawn (from the player)
            maximumWolfCount = 8,	// how many wolfs spawn at max
            minimumWolfCount = 3,	// how many wolfs spawn at least
            wolfRange = 10,			// how far away wolfs target enemies
            duration = 30;			// time of the wolfs existence in seconds

    public SummonersWand(Main main, String name, String rarity, int cost) {
        super(main, name, rarity, cost);
    }

    @Override
    public void runAction(Player player) {
        // Play a sound
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WOLF_GROWL, 1, 1);

        // Create a random number generator
        Random rdm = new Random();

        // Keep track of all spawned wolfs
        List<Wolf> wolfs = new ArrayList<>();

        // Determine how many wolfs should spawn
        int wolfCount = rdm.nextInt(this.maximumWolfCount - this.minimumWolfCount + 1) + this.minimumWolfCount;

        // Spawn the wolfs
        for (int i = 0; i < wolfCount; i++) {

            // Generate a random location for the wolf
            Location spawnLocation = BukkitUtils.getRandomNearbyPosition(player.getLocation(), this.range);

            // Spawn a wolf and safe it as a variable
            Wolf wolf = (Wolf) player.getWorld().spawnEntity(spawnLocation, EntityType.WOLF);

            // Spawn cloud particles where the wolf spawns
            BukkitUtils.emitParticles(player.getWorld(), spawnLocation, Particle.CLOUD, 30, 0.01, new Vector(0.5, 0.5, 0.5));

            // Play particles at the position of the wolf
            BukkitUtils.emitParticlesContinuously(wolf, Particle.VILLAGER_ANGRY, 1, 1, new Vector(0, 0, 0), this.main, 0, 10, this.duration * 20);

            // Add wolf to list
            wolfs.add(wolf);
        }

        // This runnable will make sure wolfs allways have a target
        BukkitRunnable runnable = new BukkitRunnable() {

            int counter = 0;

            @Override
            public void run() {
                // Loop through all the wolfs
                for (Wolf wolf : wolfs) {

                    // Check if wolf exists, is alive and has no target
                    if (wolf != null
                            && !wolf.isDead()
                            && (wolf.getTarget() == null
                            || wolf.getTarget().isDead())) {

                        // Get a list of entities near wolf
                        List<Entity> nearbyEntities = wolf.getNearbyEntities(SummonersWand.this.wolfRange, SummonersWand.this.wolfRange, SummonersWand.this.wolfRange);

                        // Only try to find a target if entities are around
                        if (nearbyEntities.size() >= 1) {

                            // Create a list of all possible targets
                            List<LivingEntity> possibleTargets = new ArrayList<>();

                            // Loop through all nearby entities
                            for (Entity entity : nearbyEntities) {

                                // Check if an entity could be a target
                                if (entity != null
                                        && entity instanceof LivingEntity
                                        && entity.getType() != EntityType.WOLF
                                        && entity != player
                                        && wolf.hasLineOfSight(entity)) {

                                    // Turn the entity into a living entity
                                    LivingEntity livingEntity = (LivingEntity) entity;

                                    // Add the entity to the target list
                                    possibleTargets.add(livingEntity);
                                }
                            }

                            // Check if there are possible targets
                            if (possibleTargets.size() >= 1) {

                                // Select a target from all possible targets
                                LivingEntity target = possibleTargets.get(rdm.nextInt(possibleTargets.size()));

                                // Make wolf attack the target
                                wolf.setTarget(target);
                            }
                        }
                    }
                }

                // Keep track of how many times the wolfs target was updated
                this.counter++;

                // Check if effect is over
                if (this.counter >= SummonersWand.this.duration) {

                    // Cancel this runnable
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(this.main, 0, 20);

        // This runnable will remove wolfs after a given duration
        BukkitRunnable runnable1 = new BukkitRunnable() {
            @Override
            public void run() {
                // Loop through all the wolfs
                for (Wolf wolf : wolfs) {

                    // If wolf exists and isn't dead
                    if (wolf != null
                            && !wolf.isDead()) {

                        // Remove wolf
                        wolf.setHealth(0);
                    }
                }

                // Cancel this runnable
                this.cancel();
            }
        };
        runnable1.runTaskLater(this.main, 20 * this.duration);
    }

}
