package com.Wands.WandVariations;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.Wands.LocationHelper;
import com.Wands.Main;
import com.Wands.ParticleEmitter;
import com.Wands.Wand;

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
	
	public WoolWand(Main main, String name, int cost) {
		super(main, name, cost);
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
					for (int x = (int) -range; x < range; x++) {
						for (int z = (int) -range; z < range; z++) {
							
							// Get location near player
							Location blockLocation = LocationHelper.offsetLocation(playerLocation, new Vector(x + 0.5f, -1, z + 0.5f));
							
							// Check if block can be placed at locationy
							if (blockLocation.getBlock().getType().toString().contains("AIR")
									&& blockLocation.distance(playerLocation) <= range) {
								
								Material glassColor = glassColors[rdm.nextInt(glassColors.length)];
								
								// Place block
								blockLocation.getBlock().setType(glassColor);
								
								// Play particle effect at block position
								ParticleEmitter.emitParticles(blockLocation, Particle.HEART, 1, 0.1, new Vector(0.5, 0.5, 0.5));
								
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
											ParticleEmitter.emitParticles(blockLocation, Particle.CLOUD, 1, 0.01, new Vector(0.5, 0.5, 0.5));
											
											// Cancel this runnable
											this.cancel();
										}
									}
								};
								runnable.runTaskTimer(main, 60, 20);
							}
						}
					}
				}
				
				// Add the time between runs to timer count
				counter += period;
				
				// If counter has run for the needed amount of time
				if (counter >= duration) {
					
					// Cancel this runnable
					this.cancel();
				}
			}
			
		};
		runnable.runTaskTimer(main, 0, (long) (20 * period));
	}

}
