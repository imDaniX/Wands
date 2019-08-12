package com.Wands.WandVariations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.Wands.LocationHelper;
import com.Wands.Main;
import com.Wands.ParticleEmitter;
import com.Wands.Wand;

public class LightningWand extends Wand {

	int lightningMaximumRange = 50;		// the maximum range of the lightning bolt
	
	public LightningWand(Main main, String name, String rarity, int cost) {
		super(main, name, rarity, cost);
	}

	@Override
	public void runAction(Player player) {
		// Set which blocks to ignore in following checks
		Set<Material> ignoredBlocks = new HashSet<>(Arrays.asList(Material.AIR, Material.CAVE_AIR));
					
		// Get blocks in line of sight of player
		List<Block> lineOfSightBlocks = player.getLineOfSight(ignoredBlocks, this.lightningMaximumRange);
		
		// Get target block (last block in line of sight)
		Location targetLocation = lineOfSightBlocks.get(lineOfSightBlocks.size() - 1).getLocation();
		
		// Use the target location but player rotation
		Location lightningLocation = LocationHelper.offsetLocation(targetLocation, new Vector(0.5f, 1, 0.5f));
		
		// Play some sound effect to let the player know there's a thunder going down
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_AMBIENT, 0.5f, 1);
		
		// Play some sound effect at the position of the thunder to warn players
		player.getWorld().playSound(lightningLocation, Sound.ENTITY_ENDERMAN_AMBIENT, 1, 1);
		
		// Create a synchronous task
		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {
				
				// Strike down lightning at target location
				player.getWorld().strikeLightning(lightningLocation);
			}
		};
		runnable.runTaskLater(this.main, 50);
		
		// Play some particle effect to let players now its coming
		ParticleEmitter.emitParticles(lightningLocation, Particle.PORTAL, 500, 1, new Vector(0, 0, 0));
	}

}
