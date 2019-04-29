package com.Wands.WandVariations;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.Wands.Main;
import com.Wands.Wand;

public class EarthWand extends Wand {

	public EarthWand(Main main, String name, int cost) {
		super(main, name, cost);
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

				// Safe all the blocks that need to be launched into a list
				List<Location> blocksToLaunch = new ArrayList<Location>();

				for (int x = -earthquakeRange; x < earthquakeRange; x++) {
					for (int y = -earthquakeRange; y < earthquakeRange; y++) {
						for (int z = -earthquakeRange; z < earthquakeRange; z++) {

							// Get block position
							Location blockLocation = new Location(
									playerLocation.getWorld(),
									playerLocation.getX() + x,
									playerLocation.getY() + y,
									playerLocation.getZ() + z);

							// Get position under block
							Location blockCeilingLocation = new Location(
									playerLocation.getWorld(),
									playerLocation.getX() + x,
									playerLocation.getY() + y + 1,
									playerLocation.getZ() + z);

							// Check if block is in range and above the block there is air
							if (blockLocation.distance(playerLocation) <= earthquakeRange + 0.5f
									&& blockLocation.distance(playerLocation) >= earthquakeRange - 1
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
				if (earthquakeRange <= 6) {

					// Increase range of earthquake for next go
					earthquakeRange++;
				}
				// Cancel earthquake if its still needs to go
				else {
					earthquakeRange = 0;
					this.cancel();
				}
			}
		};
		runnable.runTaskTimer(main, 0, 2);
	}

}
