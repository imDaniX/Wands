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
import org.bukkit.util.Vector;

import com.Wands.Main;
import com.Wands.ParticleEmitter;
import com.Wands.Wand;

public class RocketWand extends Wand {

	public RocketWand(Main main, String name, String rarity, int cost) {
		super(main, name, rarity, cost);
	}

	@Override
	public void runAction(Player player) {
		// Get the player location
		Location playerLocation = player.getLocation();
		
		// Set which blocks to ignore in following checks
		Set<Material> ignoredBlocks = new HashSet<>(Arrays.asList(Material.AIR, Material.CAVE_AIR));

		// Get blocks in line of sight of player
		List<Block> lineOfSightBlocks = player.getLineOfSight(ignoredBlocks, 10);

		// Only spawn fireball if there is some room infront of player
		if (lineOfSightBlocks.size() >= 10) {
			
			// Get a block in line of sight to know in which direction to launch the player
			Location directionLocation = lineOfSightBlocks.get(lineOfSightBlocks.size() - 1).getLocation();
			
			// Calculate the direction
			Vector velocity = new Vector(
					directionLocation.getX() - playerLocation.getX(),
					directionLocation.getY() - playerLocation.getY(),
					directionLocation.getZ() - playerLocation.getZ());
		
			// Apply velocity to player
			player.setVelocity(velocity.multiply(0.25f));
			
			// Play a sound
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
			
			// Play some particles under player
			ParticleEmitter.emitParticlesContinuously(player, Particle.FLAME, 1, 0.01, new Vector(0, 0, 0), main, 0, 1, 70);
		}
	}

}
