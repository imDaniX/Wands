package com.Wands.WandVariations;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.PluginBase.ParticleEmitter;
import com.Wands.Main;
import com.Wands.Wand;

public class PumpkinWand extends Wand {

	double 	chance = 10,		// chance of applying random velocity in percent
			strength = 1,		// strength of the random velocity
			duration = 30,		// duration of effect in seconds
			range = 10;			// how far pumpkins can move away from player
	
	public PumpkinWand(Main main, String name, String rarity, int cost) {
		super(main, name, rarity, cost);
	}

	@Override
	public void runAction(Player player) {
		// Get player position
		Location playerLocation = player.getLocation();
		
		// Create a random number generator
		Random rdm = new Random();
		
		for (int i = 0; i < 5; i++) {
			// Spawn a bat
			Bat bat = (Bat) player.getWorld().spawnEntity(playerLocation, EntityType.BAT);

			// Make that bat invisible
			bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2000, 1));

			// Spawn an armorstand
			ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(playerLocation, EntityType.ARMOR_STAND);

			// Make that armorstand invisible
			armorStand.setVisible(false);

			// Give it a jack'o'lantern
			armorStand.getEquipment().setHelmet(new ItemStack(Material.JACK_O_LANTERN));

			// Make the armorstand ride the bat
			bat.addPassenger(armorStand);

			ParticleEmitter.getInstance().emitParticlesContinuously(bat, Particle.FLAME, 1, 0.01, new Vector(0.5f, 0.5f, 0.5f), this.main, 0, 5, 20 * (int) this.duration);
			ParticleEmitter.getInstance().emitParticlesContinuously(bat, Particle.CLOUD, 1, 0.01, new Vector(0.5f, 0.5f, 0.5f), this.main, 0, 5, 20 * (int) this.duration);
			ParticleEmitter.getInstance().emitParticlesContinuously(bat, Particle.SQUID_INK, 1, 0.01, new Vector(0.5f, 0.5f, 0.5f), this.main, 0, 5, 20 * (int) this.duration);
			
			// This runnable will make sure that the bats will stay around the player
			// it will also remove the bats if the time has run out
			BukkitRunnable runnable = new BukkitRunnable() {	

				// Keep track of how many times the runnable was run
				int counter = 0;

				@SuppressWarnings("hiding")
				@Override
				public void run() {

					// Loop through all entities near player
					for (Entity entity : bat.getNearbyEntities(PumpkinWand.this.range, PumpkinWand.this.range, PumpkinWand.this.range)) {
						
						// Entities only have a 10% chance of getting effected
						if (rdm.nextInt(101) <= PumpkinWand.this.chance) {
							
							// Make sure the entity isn't the player or a bat or armor stand
							if (entity != player
									&& entity.getType() != EntityType.BAT
									&& entity.getType() != EntityType.ARMOR_STAND) {

								// Get the entities current velocity
								Vector currentVelocity = entity.getVelocity();

								// Add a random vector to that velocity
								Vector velocity = new Vector(
										currentVelocity.getX() + rdm.nextFloat() * PumpkinWand.this.strength - (PumpkinWand.this.strength / 2),
										currentVelocity.getY() + rdm.nextFloat() * PumpkinWand.this.strength - (PumpkinWand.this.strength / 2),
										currentVelocity.getZ() + rdm.nextFloat() * PumpkinWand.this.strength - (PumpkinWand.this.strength / 2));

								// Play some particles
								ParticleEmitter.getInstance().emitParticles(entity.getWorld(), entity.getLocation(), Particle.CRIT_MAGIC, 5, 0.1, new Vector(0.5, 0.5, 0.5));
								
								// Apply the random velocity
								entity.setVelocity(velocity);

								// If the entity is alive
								if (entity instanceof LivingEntity) {

									// Get a reference to the living entity
									LivingEntity livingEntity = (LivingEntity) entity;

									// Add blindness and slowness effects
									livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
									livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 1));
								}
							}
						}
					}
					
					if (bat.getLocation().distance(player.getLocation()) >= PumpkinWand.this.range) {

						// Get player and bat location
						Location playerLocation = player.getLocation();
						Location batLocation = bat.getLocation();

						// Calculate difference between player and bat location
						Vector velocity = new Vector(
								playerLocation.getX() - batLocation.getX(),
								playerLocation.getY() - batLocation.getY(),
								playerLocation.getZ() - batLocation.getZ());

						// Pull bat towards player
						bat.setVelocity(velocity.multiply(0.1));
					}

					// Add period to counter
					this.counter += 10;

					// If the time has run out
					if (this.counter >= 20 * PumpkinWand.this.duration) {

						// Remove the bat and armorstand
						armorStand.remove();
						bat.remove();

						// Cancel this runnable
						this.cancel();
					}
				}
			};
			runnable.runTaskTimer(this.main, 0, 10);
		}
	}

}
