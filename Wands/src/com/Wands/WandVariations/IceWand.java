package com.Wands.WandVariations;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.Wands.Main;
import com.Wands.Wand;

public class IceWand extends Wand {

	public IceWand(Main main, String name, int cost) {
		super(main, name, cost);
	}

	@Override
	public void runAction(Player player) {
		// Get player location
		Location playerLocation = player.getLocation();

		// Create random number generator to determine how long snow should stay
		Random rdm = new Random();

		// Range of the ice effect
		int range = 8;

		// Loop through blocks near player
		for (int x = -range; x < range; x++) {
			for (int y = -range; y < range; y++) {
				for (int z = -range; z < range; z++) {

					// Get block position
					Location blockLocation = new Location(
							playerLocation.getWorld(),
							playerLocation.getX() + x,
							playerLocation.getY() + y,
							playerLocation.getZ() + z);

					// Get position under block
					Location blockGroundLocation = new Location(
							playerLocation.getWorld(),
							playerLocation.getX() + x,
							playerLocation.getY() + y - 1,
							playerLocation.getZ() + z);

					// Check if block is of type air above a block of type ground
					// aswell as if the block is in range
					if (blockLocation.getBlock().getType() == Material.AIR
							&& blockGroundLocation.getBlock().getType().isSolid()
							&& blockLocation.distance(playerLocation) <= range) {

						// Place snow at that block position
						blockLocation.getBlock().setType(Material.SNOW);

						// Schedule task to turn snow blocks back to normal blocks
						BukkitRunnable runnable = new BukkitRunnable() {
							@Override
							public void run() {
								if (blockLocation.getBlock().getType() == Material.SNOW) {
									blockLocation.getBlock().setType(Material.AIR);
								}
							}
						};
						runnable.runTaskLater(main, rdm.nextInt(40) + 200);
					}
				}
			}
		}

		// Loop through all entities near player
		for (Entity entity : player.getNearbyEntities(range, range, range)) {

			// Check if the entity is not the player and also alive
			if (entity != player
					&& entity instanceof LivingEntity) {

				// Get living entity
				LivingEntity livingEntity = (LivingEntity) entity;

				// Prepare potion effect
				PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 200, 10);

				// Add potion effects to entity
				livingEntity.addPotionEffect(slowness);
			}
		}
		
		// Play a sound effect
		player.getWorld().playSound(playerLocation, Sound.BLOCK_SNOW_PLACE, 1, 1);
	}

}
