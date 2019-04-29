package com.Wands.WandVariations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.Wands.Main;
import com.Wands.Wand;

public class TeleportWand extends Wand {

	int teleportMaximumRange = 30;
	
	public TeleportWand(Main main, String name, int cost) {
		super(main, name, cost);
	}

	@Override
	public void runAction(Player player) {
		// Set which blocks to ignore in following checks
		Set<Material> ignoredBlocks = new HashSet<>(Arrays.asList(Material.AIR));
					
		// Get blocks in line of sight of player
		List<Block> lineOfSightBlocks = player.getLineOfSight(ignoredBlocks, teleportMaximumRange);
		
		// Get target block (last block in line of sight)
		Location targetLocation = lineOfSightBlocks.get(lineOfSightBlocks.size() - 1).getLocation();
		
		// Use the target location but player rotation
		Location teleportLocation = new Location(
				targetLocation.getWorld(),
				targetLocation.getX() + 0.5f,
				targetLocation.getY() + 1,
				targetLocation.getZ() + 0.5f,
				player.getLocation().getYaw(),
				player.getLocation().getPitch());
		
		// Teleport player to the block thats farthest away from the player
		player.teleport(teleportLocation);
	}

}
