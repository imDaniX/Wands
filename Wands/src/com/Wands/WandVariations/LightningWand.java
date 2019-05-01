package com.Wands.WandVariations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.Wands.LocationHelper;
import com.Wands.Main;
import com.Wands.Wand;

public class LightningWand extends Wand {

	int lightningMaximumRange = 50;
	
	public LightningWand(Main main, String name, int cost) {
		super(main, name, cost);
	}

	@Override
	public void runAction(Player player) {
		// Set which blocks to ignore in following checks
		Set<Material> ignoredBlocks = new HashSet<>(Arrays.asList(Material.AIR));
					
		// Get blocks in line of sight of player
		List<Block> lineOfSightBlocks = player.getLineOfSight(ignoredBlocks, lightningMaximumRange);
		
		// Get target block (last block in line of sight)
		Location targetLocation = lineOfSightBlocks.get(lineOfSightBlocks.size() - 1).getLocation();
		
		// Use the target location but player rotation
		Location lightningLocation = LocationHelper.offsetLocation(targetLocation, new Vector(0.5f, 1, 0.5f));
		
		// Strike down lightning at target location
		player.getWorld().strikeLightning(lightningLocation);
	}

}
