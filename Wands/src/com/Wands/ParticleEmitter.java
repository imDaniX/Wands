package com.Wands;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticleEmitter {
	
	public static void emitParticlesContinuously(Entity entity, Particle particle, int amount, double speed, Vector spread, Main main, int delay, int period, int duration) {
		// Create a synchronous task
		BukkitRunnable runnable = new BukkitRunnable() {
			
			// Keep track of how many times runnable was run
			int counter = 0;
			
			@Override
			public void run() {
				// Emit particles
				emitParticles(entity.getLocation(), particle, amount, speed, spread);
				
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
		runnable.runTaskTimer(main, delay, period);
	}
	
	public static void emitParticlesContinuously(Location location, Particle particle, int amount, double speed, Vector spread, Main main, int delay, int period, int duration) {
		// Create a synchronous task
		BukkitRunnable runnable = new BukkitRunnable() {
			
			// Keep track of how many times runnable was run
			int counter = 0;
			
			@Override
			public void run() {
				// Emit particles
				emitParticles(location, particle, amount, speed, spread);
				
				// Add the time between runs to timer count
				this.counter += period;
				
				// If counter has run for the needed amount of time
				if (this.counter >= duration) {
					
					// Cancel this runnable
					this.cancel();
				}
			}
		};
		runnable.runTaskTimer(main, delay, period);
	}
	
	public static void emitParticles(Location location, Particle particle, int amount, double speed, Vector spread) {
		location.getWorld().spawnParticle(
				particle,					// Particle type
				location.getX(),			// Location X
				location.getY(),			// Location Y
				location.getZ(),			// Location Z
				amount,						// Particle amount
				spread.getX(),				// Particle spread X
				spread.getY(),				// Particle spread Y
				spread.getZ(),				// Particle spread Z
				speed);						// Particle speed
	}
	
}
