package com.Wands.WandVariations;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.Wands.Main;
import com.Wands.Wand;

public class CraftingWand extends Wand {

	public CraftingWand(Main main, String name, String rarity, int cost) {
		super(main, name, rarity, cost);
	}

	@Override
	public void runAction(Player player) {
		// Play a sound
		player.getWorld().playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
		
		// Open workbench
		player.openWorkbench(player.getLocation(), true);
	}

}
