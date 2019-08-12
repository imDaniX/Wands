package com.Wands.WandVariations;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.PluginBase.ParticleEmitter;
import com.Wands.LocationHelper;
import com.Wands.Main;
import com.Wands.Wand;

public class TrickeryWand extends Wand {

	int duration = 10;		// delay till teleport in seconds
	
	public TrickeryWand(Main main, String name, String rarity, int cost) {
		super(main, name, rarity, cost);
	}

	@Override
	public void runAction(Player player) {
		// Remember location of the player
		Location playerLocation = player.getLocation();
		
		// Play a sound to let the player know the time frame is over
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_AMBIENT, 1, 1);
		
		// Add speed effect to player
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * this.duration, 1));
		
		BukkitRunnable runnable = new BukkitRunnable() {
			
			// Keep track of how many times the runnable was run
			int counter = 0;
			
			@Override
			public void run() {
				// If the player is sneaking
				if (player.isSneaking()) {
					
					// Play some particles where the player was at
					ParticleEmitter.getInstance().emitParticles(playerLocation.getWorld(),
							LocationHelper.offsetLocation(player.getLocation(), new Vector(0, 1, 0)),
							Particle.DRAGON_BREATH, 100, 0.01, new Vector(0.25, 0.5, 0.25));
					
					// Play some particles where the player went to
					ParticleEmitter.getInstance().emitParticles(playerLocation.getWorld(),
							LocationHelper.offsetLocation(playerLocation, new Vector(0, 1, 0)),
							Particle.DRAGON_BREATH, 100, 0.01, new Vector(0.25, 0.5, 0.25));
					
					// Teleport the player back to his original position
					player.teleport(playerLocation);
					
					// Play a sound effect
					player.getWorld().playSound(playerLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
					
					// Cancel this runnable
					this.cancel();
				}
				
				// Add period to counter
				this.counter += 2;
				
				// If the time has run out
				if (this.counter >= 20 * TrickeryWand.this.duration) {
					
					// Play a sound to let the player know the time frame is over
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_HURT, 1, 1);
					
					// Cancel this runnable
					this.cancel();
				}
			}
		};
		runnable.runTaskTimer(this.main, 0, 2);
	}

}
