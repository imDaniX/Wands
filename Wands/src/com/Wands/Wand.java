package com.Wands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Wand implements Listener {
	
	protected Main main;
	protected String name;
	protected int cooldown;
	
	protected List<String> playersOnCooldown = new ArrayList<>();
	
	public Wand(Main main, String name, int cooldown) {
		// Register events as listener
		main.getServer().getPluginManager().registerEvents(this, main);
		
		// Set local variables to the given variables
		this.main = main;
		this.name = name;
		this.cooldown = cooldown;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		// Check if player has left clicked the air
		if (event.getAction() == Action.LEFT_CLICK_AIR) {
			
			// Get player
			Player player = event.getPlayer();
			
			// Get players item
			ItemStack item = player.getInventory().getItemInMainHand();
			
			// Check if player is holding an item
			if (item != null
					&& item.getItemMeta() != null
					&& item.getItemMeta().getDisplayName() != null) {
				
				// Check if player is holding the correct wand
				if (item.getItemMeta().getDisplayName().equals(name)) {
					
					// Don't execute any action if the player is on cooldown
					if (playersOnCooldown.contains(player.getName())) {
						
						// Play a sound to let the player know theres still a cooldown
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
						
						// Return the function
						return;
					}
					
					// Run the wand action
					runAction(player);

					// Add player to cooldown list
					playersOnCooldown.add(player.getName());
					
					// This runnable will remove the player from cooldown list after a given time
					BukkitRunnable runnable = new BukkitRunnable() {	
						@Override
						public void run() {
							// Remove player from cooldown list
							playersOnCooldown.remove(player.getName());
						}
					};
					runnable.runTaskLater(main, 20 * cooldown);
				}
			}
		}
	}
	
	public abstract void runAction(Player player);
	
}
