package com.Wands.WandVariations;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.Wands.LocationHelper;
import com.Wands.Main;
import com.Wands.Wand;

public class WoolWand extends Wand {

	int 	duration = 10;		// time that the effect lasts
	double	period = 0.25;		// how quickly to replace air blocks
	int		range = 3;			// range in which air will be replaced by wool
	
	Material[] woolColors = new Material[] {
		Material.BLUE_WOOL,
		Material.CYAN_WOOL,
		Material.LIGHT_BLUE_WOOL,
		Material.LIME_WOOL,
		Material.MAGENTA_WOOL,
		Material.ORANGE_WOOL,
		Material.PINK_WOOL,
		Material.PURPLE_WOOL,
		Material.RED_WOOL,
		Material.WHITE_WOOL,
		Material.YELLOW_WOOL
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
							
							// Check if block can be placed at location
							if (blockLocation.getBlock().getType() == Material.AIR
									&& blockLocation.distance(playerLocation) <= range) {
								
								Material woolColor = woolColors[rdm.nextInt(woolColors.length)];
								
								// Place block
								blockLocation.getBlock().setType(woolColor);
								
								// Create runnable that will remove all the wool blocks
								BukkitRunnable runnable = new BukkitRunnable() {
									@Override
									public void run() {
										// Make sure the block we want to remove is still there
										if (blockLocation.getBlock().getType() == woolColor
												&& blockLocation.distance(player.getLocation()) >= 2) {		
											
											// Remove block
											blockLocation.getBlock().setType(Material.AIR);
											
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
