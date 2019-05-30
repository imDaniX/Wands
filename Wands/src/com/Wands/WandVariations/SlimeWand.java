package com.Wands.WandVariations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.Wands.LocationHelper;
import com.Wands.Main;
import com.Wands.ParticleEmitter;
import com.Wands.Wand;

public class SlimeWand extends Wand {

	public int duration = 30;		// duration of slime effect in seconds
	
	public SlimeWand(Main main, String name, String rarity, int cooldown) {
		super(main, name, rarity, cooldown);
	}

	@Override
	public void runAction(Player player) {
		
		// Spawn the projectile
		Slime projectile = (Slime) player.getWorld().spawnEntity(player.getLocation(), EntityType.SLIME);
	
		// Give the projectile a name
		projectile.setCustomName(ChatColor.GREEN + "Voluntary Projectile");
		
		// Set the slime size to very small
		projectile.setSize(1);
		
		// Get the player location
		Location playerLocation = player.getLocation();

		// Set which blocks to ignore in following checks
		Set<Material> ignoredBlocks = new HashSet<>(Arrays.asList(Material.AIR, Material.CAVE_AIR));

		// Get blocks in line of sight of player
		List<Block> lineOfSightBlocks = player.getLineOfSight(ignoredBlocks, 10);

		// Only spawn fireball if there is some room infront of player
		if (lineOfSightBlocks.size() >= 1) {

			// Get a block in line of sight to know in which direction to launch the player
			Location directionLocation = lineOfSightBlocks.get(lineOfSightBlocks.size() - 1).getLocation();

			// Calculate the direction
			Vector velocity = new Vector(
					directionLocation.getX() - playerLocation.getX(),
					directionLocation.getY() - playerLocation.getY(),
					directionLocation.getZ() - playerLocation.getZ());
			
			// Apply velocity to player
			projectile.setVelocity(velocity.multiply(0.25f));
			
			// Emit particles at the slime position
			ParticleEmitter.emitParticlesContinuously(projectile, Particle.SLIME, 2, 0.1, new Vector(0.5, 0.5, 0.5), main, 0, 2, 20 * duration);
			
			// Play initial sound effect
			projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1);
			
			/*
			 * This runnable will keep track of the projectiles state
			 * It will change its velocity when touching a block
			 */
			BukkitRunnable runnable = new BukkitRunnable() {
				
				// Keep track of how many times the runnable was run
				int c = 0;
				
				// Keep track of wether or not the projectile has bounced
				boolean bouncedOnce = false;
				
				@Override
				public void run() {

					// Note that the runnable was run
					c += 2;
					
					// Get nearby entities
					for (Entity entity : projectile.getNearbyEntities(1, 1, 1)) {
						if (entity instanceof LivingEntity
								&& entity != projectile
								&& entity != player) {
							
							// Convert the entity into a living entity
							LivingEntity livingEntity = (LivingEntity) entity;
							
							// Get the entity location
							Location entityLocation = entity.getLocation();
							
							// Calculate the direction
							Vector velocity = new Vector(
									entityLocation.getX() - projectile.getLocation().getX(),
									0.5,
									entityLocation.getZ() - projectile.getLocation().getZ());
						
							// Apply velocity to entity
							livingEntity.setVelocity(velocity.multiply(2));
						}
					}
					
					// The bounce strength
					double bounce = 0.5;
					
					if (bouncedOnce) {
						if (projectile.getVelocity().getX() > 0) {
							projectile.setVelocity(new Vector(
									bounce,
									projectile.getVelocity().getY(),
									projectile.getVelocity().getZ()));
						}
						
						if (projectile.getVelocity().getX() < 0) {
							projectile.setVelocity(new Vector(
									-bounce,
									projectile.getVelocity().getY(),
									projectile.getVelocity().getZ()));
						}
						
						if (projectile.getVelocity().getZ() > 0) {
							projectile.setVelocity(new Vector(
									projectile.getVelocity().getX(),
									projectile.getVelocity().getY(),
									bounce));
						}
						
						if (projectile.getVelocity().getZ() > 0) {
							projectile.setVelocity(new Vector(
									projectile.getVelocity().getX(),
									projectile.getVelocity().getY(),
									-bounce));
						}
					}
					
					ParticleEmitter.emitParticles(projectile.getLocation(), Particle.CRIT_MAGIC, 1, 0.01, new Vector(0, 0, 0));
					
					Location projectileLocation = LocationHelper.offsetLocation(projectile.getLocation(), new Vector(0, 0, 0));
					
					/*
					 * X Axis bouncing
					 */
					if (LocationHelper.offsetLocation(projectileLocation, new Vector(1, 0, 0)).getBlock().getType().isSolid()
							&& projectile.getVelocity().getX() > 0) {
						projectile.setVelocity(new Vector(
								-1,
								projectile.getVelocity().getY(),
								projectile.getVelocity().getZ()));
						
						bouncedOnce = true;
						projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1);
					}
					
					if (LocationHelper.offsetLocation(projectileLocation, new Vector(-1, 0, 0)).getBlock().getType().isSolid()
							&& projectile.getVelocity().getX() < 0) {
						projectile.setVelocity(new Vector(
								1,
								projectile.getVelocity().getY(),
								projectile.getVelocity().getZ()));
						
						bouncedOnce = true;
						projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1);
					}
					
					/*
					 * Z Axis bouncing
					 */
					if (LocationHelper.offsetLocation(projectileLocation, new Vector(0, 0, 1)).getBlock().getType().isSolid()
							&& projectile.getVelocity().getZ() > 0) {
						projectile.setVelocity(new Vector(
								projectile.getVelocity().getX(),
								projectile.getVelocity().getY(),
								-1));
						
						bouncedOnce = true;
						projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1);
					}
					
					if (LocationHelper.offsetLocation(projectileLocation, new Vector(0, 0, -1)).getBlock().getType().isSolid()
							&& projectile.getVelocity().getZ() < 0) {
						projectile.setVelocity(new Vector(
								projectile.getVelocity().getX(),
								projectile.getVelocity().getY(),
								1));
						
						bouncedOnce = true;
						projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1);
					}
					
					/*
					 * Y Axis bouncing
					 */
					if (LocationHelper.offsetLocation(projectileLocation, new Vector(0, 1, 0)).getBlock().getType().isSolid()
							&& projectile.getVelocity().getY() > 0) {
						projectile.setVelocity(new Vector(
								projectile.getVelocity().getX(),
								-1,
								projectile.getVelocity().getZ()));
						
						bouncedOnce = true;
						projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1);
					}
					
					if (LocationHelper.offsetLocation(projectileLocation, new Vector(0, -1, 0)).getBlock().getType().isSolid()
							&& projectile.getVelocity().getY() < 0) {
						projectile.setVelocity(new Vector(
								projectile.getVelocity().getX(),
								1,
								projectile.getVelocity().getZ()));
						
						bouncedOnce = true;
						projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1);
					}
					
					// If the runnable has run the amount of times we need it to run
					if (c >= 20 * duration) {
						
						// If projectile is still alive
						if (projectile.isValid()
								&& projectile.getHealth() > 0) {
							
							// Remove projectile
							projectile.remove();
						}
						
						// Cancel the runnable
						this.cancel();
					}
				}
			};
			runnable.runTaskTimer(main, 0, 2);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity().getName().equals(ChatColor.GREEN + "Voluntary Projectile")) {
			event.setCancelled(true);
		}
	}

}
