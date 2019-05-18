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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.Wands.Main;
import com.Wands.ParticleEmitter;
import com.Wands.Wand;

public class FireballWand extends Wand {

	public FireballWand(Main main, String name, String rarity, int cost) {
		super(main, name, rarity, cost);
	}

	@Override
	public void runAction(Player player) {
		// Set which blocks to ignore in following checks
		Set<Material> ignoredBlocks = new HashSet<>(Arrays.asList(Material.AIR, Material.CAVE_AIR));
					
		// Get blocks in line of sight of player
		List<Block> lineOfSightBlocks = player.getLineOfSight(ignoredBlocks, 3);
				
		// Only spawn fireball if there is some room infront of player
		if (lineOfSightBlocks.size() >= 3) {
					
			// Get location of block infront of player
			Location blockLocation = lineOfSightBlocks.get(2).getLocation();		
							
			// Calculate fireball position from block position
			// Take the player rotation so it flies in the correct direction
			Location fireballLocation = new Location(
					player.getWorld(),
					blockLocation.getX(),
					blockLocation.getY(),
					blockLocation.getZ(),
					player.getLocation().getYaw(),
					player.getLocation().getPitch());
							
			// Spawn fireball
			Entity fireball = player.getWorld().spawnEntity(fireballLocation, EntityType.FIREBALL);
			
			// Player a sound effect
			player.getWorld().playSound(fireballLocation, Sound.ITEM_FIRECHARGE_USE, 1, 1);
			
			// Play particle effects that follow the flame
			ParticleEmitter.emitParticlesContinuously(fireball, Particle.FLAME, 5, 0.05, new Vector(0.5, 0.5, 0.5),  main, 0, 1, 200);
		}
	}
	
}
