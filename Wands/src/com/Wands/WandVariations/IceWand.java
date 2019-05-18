package com.Wands.WandVariations;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.Wands.LocationHelper;
import com.Wands.Main;
import com.Wands.ParticleEmitter;
import com.Wands.Wand;

public class IceWand extends Wand {

	int range = 8,			// the range of the ice effect
		duration = 10;		// the duration of the effect in seconds
	
	public IceWand(Main main, String name, String rarity, int cost) {
		super(main, name, rarity, cost);
	}


	@Override
	public void runAction(Player player) {
		// Get player location
		Location playerLocation = player.getLocation();

		// Create random number generator to determine how long snow should stay
		Random rdm = new Random();

		// Loop through blocks near player
		for (int x = -range; x < range; x++) {
			for (int y = -range; y < range; y++) {
				for (int z = -range; z < range; z++) {

					// Get block position
					Location blockLocation = LocationHelper.offsetLocation(playerLocation, new Vector(x, y, z));

					// Get position under block
					Location blockGroundLocation = LocationHelper.offsetLocation(blockLocation, new Vector(0, -1, 0));

					// Check if block is of type air above a block of type ground
					// aswell as if the block is in range
					if (blockLocation.getBlock().getType().toString().contains("AIR")
							&& blockGroundLocation.getBlock().getType().isSolid()
							&& blockLocation.distance(playerLocation) <= range) {

						// Place snow at that block position
						blockLocation.getBlock().setType(Material.SNOW);

						// Place snow effect over block
						ParticleEmitter.emitParticlesContinuously(blockLocation, Particle.CLOUD, 1, 0.05, new Vector(1, 1, 1), main, rdm.nextInt(100), 100, 200);
						
						// Schedule task to turn snow blocks back to normal blocks
						BukkitRunnable runnable = new BukkitRunnable() {
							@Override
							public void run() {
								if (blockLocation.getBlock().getType() == Material.SNOW) {
									blockLocation.getBlock().setType(Material.AIR);
								}
							}
						};
						runnable.runTaskLater(main, rdm.nextInt(40) + 20 * duration);
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
				PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 20 * duration, 10);

				// Add potion effects to entity
				livingEntity.addPotionEffect(slowness);
			}
		}
		
		// Play a sound effect
		player.getWorld().playSound(playerLocation, Sound.BLOCK_SNOW_PLACE, 1, 1);
	}

}
