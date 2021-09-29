package com.Wands.WandVariations;

import com.Wands.BukkitUtils;
import com.Wands.Main;
import com.Wands.Wand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class WoolWand extends Wand {

    int 	duration = 10;		// time that the effect lasts
    double	period = 0.25;		// how quickly to replace air blocks
    int		range = 3;			// range in which air will be replaced by wool

    Material[] glassColors = new Material[] {
            Material.BLUE_STAINED_GLASS,
            Material.CYAN_STAINED_GLASS,
            Material.LIGHT_BLUE_STAINED_GLASS,
            Material.LIME_STAINED_GLASS,
            Material.MAGENTA_STAINED_GLASS,
            Material.ORANGE_STAINED_GLASS,
            Material.PINK_STAINED_GLASS,
            Material.PURPLE_STAINED_GLASS,
            Material.RED_STAINED_GLASS,
            Material.WHITE_STAINED_GLASS,
            Material.YELLOW_STAINED_GLASS
    };

    public WoolWand(Main main, String name, String rarity, int cost) {
        super(main, name, rarity, cost);
    }

    @Override
    public void runAction(Player player) {
        // Create a random number generator
        Random rdm = new Random();

        // Create runnable that will spawn all the wool blocks
        BukkitRunnable runnable = new BukkitRunnable() {

            // Keep track of how many times runnable was run
            double counter = 0;

            @Override
            public void run() {

                // Get player location
                Location playerLocation = player.getLocation();

                // Don't place blocks if the player is sneaking
                if (!player.isSneaking()) {

                    // Loop through nearby blocks
                    for (int x = -WoolWand.this.range; x < WoolWand.this.range; x++) {
                        for (int z = -WoolWand.this.range; z < WoolWand.this.range; z++) {

                            // Get location near player
                            Location blockLocation = playerLocation.clone().add(x + 0.5f, -1, z + 0.5f);

                            // Check if block can be placed at location
                            if (blockLocation.getBlock().getType().toString().contains("AIR")
                                    && !blockLocation.getBlock().getType().toString().contains("STAIR")
                                    && blockLocation.distance(playerLocation) <= WoolWand.this.range) {

                                // Pick a random color from the list of available colors
                                Material glassColor = WoolWand.this.glassColors[rdm.nextInt(WoolWand.this.glassColors.length)];

                                // Place block
                                blockLocation.getBlock().setType(glassColor);

                                // Play particle effect at block position
                                BukkitUtils.emitParticles(blockLocation.getWorld(), blockLocation, Particle.HEART, 1, 0.1, new Vector(0.5, 0.5, 0.5));

                                // Create runnable that will remove all the wool blocks
                                BukkitRunnable runnable = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        // Make sure the block we want to remove is still there
                                        if (blockLocation.getBlock().getType() == glassColor
                                                && blockLocation.distance(player.getLocation()) >= 2) {

                                            // Remove block
                                            blockLocation.getBlock().setType(Material.AIR);

                                            // Play particle effect at block position
                                            BukkitUtils.emitParticles(blockLocation.getWorld(), blockLocation, Particle.CLOUD, 1, 0.01, new Vector(0.5, 0.5, 0.5));

                                            // Cancel this runnable
                                            this.cancel();
                                        }
                                    }
                                };
                                runnable.runTaskTimer(WoolWand.this.main, 60, 20);
                            }
                        }
                    }
                }

                // Add the time between runs to timer count
                this.counter += WoolWand.this.period;

                // If counter has run for the needed amount of time
                if (this.counter >= WoolWand.this.duration) {

                    // Cancel this runnable
                    this.cancel();
                }
            }

        };
        runnable.runTaskTimer(this.main, 0, (long) (20 * this.period));
    }

}
