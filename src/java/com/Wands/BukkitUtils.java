package com.Wands;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A bunch of methods from the original PluginBase https://github.com/ArianDannemann/PluginBase
 */
public class BukkitUtils {

    /**
     * Gets a random nearby validated location. NOTE: It might return a location
     * that isn't optimal if it can't find one the first 100 times
     *
     * @param startLocation
     *            The location that will be used as the center of possible nearby
     *            positions
     * @param range
     *            The range in which random locations will be searched for
     * @return Random nearby validated location
     */
    public static Location getRandomNearbyPosition(Location startLocation, int range) {

        // Create a random number generator
        Random rdm = ThreadLocalRandom.current();

        // Create a new location to return later
        Location randomLocation = startLocation;
        Location validatedRandomLocation = null;

        // Keep track of how many times we tried to find a location
        int tries = 0;
        int maximumTries = 100;

        /*
         * Check if the validated random location doesn't exist Check if the validated
         * random location is to far from the start location Check if the validated
         * random location is inside a block (which shouldn't happen TODO: check if this
         * check even needs to exist) Check if there is water beneath the random
         * location Check if there is lava beneath the random location
         */
        while (tries <= maximumTries
                && (validatedRandomLocation == null || validatedRandomLocation.distance(startLocation) > range * 2
                || validatedRandomLocation.getBlock().getType().isSolid()
                || validatedRandomLocation.getBlock().getType().toString().toLowerCase().contains("water")
                || validatedRandomLocation.getBlock().getType().toString().toLowerCase().contains("lava"))) {

            // Generate a random offset
            Vector offset = new Vector(rdm.nextInt(range * 2) - range, rdm.nextInt(range * 2) - range,
                    rdm.nextInt(range * 2) - range);

            // Add offset to random location
            randomLocation = startLocation.clone().add(offset);

            // Validate random location
            validatedRandomLocation = validateLocation(randomLocation);

            // Note the try
            tries++;
        }

        if (tries >= maximumTries - 1) {
            JavaPlugin.getPlugin(Main.class).getLogger().warning(
                    "Exceeded maximum amount of tries while trying to find a random nearby location. "
                            + "The resulting location may not be suitable for entities");
        }

        // Return random validated location
        return validatedRandomLocation;
    }

    public static Location validateLocation(Location location) {

        // Create a new location to return later
        Location validatedLocation = location;

        // While block at location is solid
        while (validatedLocation.getBlock().getType().isSolid()) {

            // Move location up one
            validatedLocation.setY(validatedLocation.getY() + 1);
        }

        // While block under location is not solid
        while (!validatedLocation.clone().add(0, -1, 0).getBlock().getType().isSolid()
                && validatedLocation.getBlockY() > 0) {

            // Move location down one
            validatedLocation.setY(validatedLocation.getY() - 1);
        }

        // Return validated location
        return validatedLocation;
    }

    public static final Particle[] particleTypesThatNeedData = new Particle[] { Particle.REDSTONE, Particle.ITEM_CRACK,
            Particle.BLOCK_CRACK, Particle.BLOCK_DUST };

    /**
     * Emit particles for a certain amount of time at the location of an entity
     *
     * @param entity
     *            The entity at which the particles will be emitted
     * @param particle
     *            The particle type to be emitted
     * @param amount
     *            How many particles should be emitted
     * @param speed
     *            How quickly the particles should play their animation
     * @param spread
     *            The spread of the particles (using the entity location as a base)
     * @param plugin
     *            The plugin
     * @param delay
     *            Delay in minecraft ticks until the first particle emits (20 ticks
     *            = 1 second)
     * @param period
     *            Delay in minecraft ticks between particle emissions (20 ticks = 1
     *            second)
     * @param duration
     *            Duration in minecraft ticks of how long particles will be emitted
     *            (20 ticks = 1 second)
     */
    public static void emitParticlesContinuously(Entity entity, Particle particle, int amount, double speed, Vector spread,
                                                 Plugin plugin, int delay, int period, int duration) {

        // Create a synchronous task
        BukkitRunnable runnable = new BukkitRunnable() {

            // Keep track of how many times runnable was run
            int counter = 0;

            @Override
            public void run() {

                // Emit particles
                emitParticles(entity.getWorld(), entity.getLocation(), particle, amount, speed, spread);

                // Add the time between runs to timer count
                this.counter += period;

                // If counter has run for the needed amount of time
                if (this.counter >= duration) {

                    // Cancel this runnable
                    this.cancel();
                }

                if (entity.isDead()) {
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin, delay, period);
    }

    /**
     * Emit particles for a certain amount of time at a specific location
     *
     * @param location
     *            The location at which the particles will be emitted
     * @param particle
     *            The particle type to be emitted
     * @param amount
     *            How many particles should be emitted
     * @param speed
     *            How quickly the particles should play their animation
     * @param spread
     *            The spread of the particles (using the entity location as a base)
     * @param plugin
     *            The plugin
     * @param delay
     *            Delay in minecraft ticks until the first particle emits (20 ticks
     *            = 1 second)
     * @param period
     *            Delay in minecraft ticks between particle emissions (20 ticks = 1
     *            second)
     * @param duration
     *            Duration in minecraft ticks of how long particles will be emitted
     *            (20 ticks = 1 second)
     */
    public static void emitParticlesContinuously(Location location, Particle particle, int amount, double speed, Vector spread,
                                          Plugin plugin, int delay, int period, int duration) {

        // Create a synchronous task
        BukkitRunnable runnable = new BukkitRunnable() {

            // Keep track of how many times runnable was run
            int counter = 0;

            @Override
            public void run() {

                // EmparticleTypesThatNeedDatait particles
                emitParticles(location.getWorld(), location, particle, amount, speed, spread);

                // Add the time between runs to timer count
                this.counter += period;

                // If counter has run for the needed amount of time
                if (this.counter >= duration) {

                    // Cancel this runnable
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin, delay, period);
    }

    /**
     * Emit particles once at a specific location
     *
     * @param world
     *            The world in which the particles will be emitted
     * @param location
     *            The location at which the particles will be emitted
     * @param particle
     *            The particle type to be emitted
     * @param amount
     *            How many particles should be emitted
     * @param speed
     *            How quickly the particles should play their animation
     * @param spread
     *            The spread of the particles (using the entity location as a base)
     */
    public static void emitParticles(World world, Location location, Particle particle, int amount, double speed,
                              Vector spread) {

        // Loop through all particle types that minecraft expects some data for
        for (Particle particleThatNeedsData : particleTypesThatNeedData) {

            // Check if the particle type given needs data
            if (particle == particleThatNeedsData) {

                // Log the error in the console
                JavaPlugin.getPlugin(Main.class).getLogger().warning("Tried to emit particle without data. " +
                        "Particle." + particle + " may need either dust options or block data to be spawned");

                // Do not actually spawn the particle
                return;
            }
        }

        world.spawnParticle(particle, // Particle type
                location.getX(), // Location X
                location.getY(), // Location Y
                location.getZ(), // Location Z
                amount, // Particle amount
                spread.getX(), // Particle spread X
                spread.getY(), // Particle spread Y
                spread.getZ(), // Particle spread Z
                speed // Particle speed
        );
    }
}
