package com.Wands.WandVariations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.Wands.Main;
import com.Wands.Wand;

public class FireballWand extends Wand {

	public FireballWand(Main main, String name, int cost) {
		super(main, name, cost);
	}

	@Override
	public void runAction(Player player) {
		// Set which blocks to ignore in following checks
		Set<Material> ignoredBlocks = new HashSet<>(Arrays.asList(Material.AIR));
					
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
			player.getWorld().spawnEntity(fireballLocation, EntityType.FIREBALL);
		}
	}
	
}
