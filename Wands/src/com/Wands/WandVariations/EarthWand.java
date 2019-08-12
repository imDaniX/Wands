package com.Wands.WandVariations;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.PluginBase.LocationHelper;
import com.Wands.Main;
import com.Wands.Wand;

public class EarthWand extends Wand {

	public EarthWand(Main main, String name, String rarity, int cost) {
		super(main, name, rarity, cost);
	}

	@Override
	public void runAction(Player player) {
		/*
		 * Create a bukkit runnable
		 * This will run repeatedly with different delays in order
		 * to start launching blocks up in a nice pattern
		 */
		BukkitRunnable runnable = new BukkitRunnable() {

			// Current range for earthquake
			int earthquakeRange = 3;

			// Get start location for earthquake
			Location playerLocation = player.getLocation();

			@Override
			public void run() {

				// Play some sound effect for every row of blocks being launched
				player.getWorld().playSound(this.playerLocation, Sound.ENTITY_ARROW_SHOOT, 1, 1);
				
				// Safe all the blocks that need to be launched into a list
				List<Location> blocksToLaunch = new ArrayList<>();

				for (int x = -this.earthquakeRange; x < this.earthquakeRange; x++) {
					for (int y = -this.earthquakeRange; y < this.earthquakeRange; y++) {
						for (int z = -this.earthquakeRange; z < this.earthquakeRange; z++) {

							// Get block position
							Location blockLocation = LocationHelper.getInstance().offsetLocation(this.playerLocation, new Vector(x, y, z));
							
							// Get position under block
							Location blockCeilingLocation = LocationHelper.getInstance().offsetLocation(blockLocation, new Vector(0, 1, 0));
							
							// Check if block is in range and above the block there is air
							if (blockLocation.distance(this.playerLocation) <= this.earthquakeRange + 0.5f
									&& blockLocation.distance(this.playerLocation) >= this.earthquakeRange - 1
									&& !blockCeilingLocation.getBlock().getType().isSolid()) {
								blocksToLaunch.add(blockLocation);
							}
						}
					}
				}

				// Launch all blocks
				for (Location blockLocation : blocksToLaunch) {

					// Spawn a falling block at block location
					FallingBlock fallingBlock = blockLocation.getWorld().spawnFallingBlock(
							blockLocation,
							blockLocation.getBlock().getBlockData());

					// Add velocity to falling block
					fallingBlock.setVelocity(new Vector(0, 0.5f, 0));

					// Add velocity to entities near block
					for (Entity entity : fallingBlock.getNearbyEntities(1, 1, 1)) {

						// Check if entity is alive and not the player
						if (entity instanceof LivingEntity
								&& entity != player) {

							// Get living entity
							LivingEntity livingEntity = (LivingEntity) entity;

							// Apply velocity to living entity
							livingEntity.setVelocity(new Vector(0, 1, 0));
						}
					}

					// Remove block from world
					blockLocation.getBlock().setType(Material.AIR);
				}

				// Clear list of blocks to launch
				blocksToLaunch.clear();

				// Check if earthquake still needs to go
				if (this.earthquakeRange <= 6) {

					// Increase range of earthquake for next go
					this.earthquakeRange++;
				}
				// Cancel earthquake if its still needs to go
				else {
					this.earthquakeRange = 0;
					this.cancel();
				}
			}
		};
		runnable.runTaskTimer(this.main, 0, 2);
	}

}
