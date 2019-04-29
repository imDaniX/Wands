package com.Wands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Wand implements Listener {
	
	protected Main main;
	protected String name;
	protected int cost;
	
	public Wand(Main main, String name, int cost) {
		// Register events as listener
		main.getServer().getPluginManager().registerEvents(this, main);
		
		// Set local variables to the given variables
		this.main = main;
		this.name = name;
		this.cost = cost;
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
				
				// Check if player is holding a fire ball wand
				if (item.getItemMeta().getDisplayName().equals(name)) {
					if (InventoryManager.removeGunpowderFromPlayerInventory(player, cost)) {
						runAction(player);
					}
					else {
						player.sendMessage("You do not have enough gunpowder to use this wand");
					}
				}
			}
		}
	}
	
	public abstract void runAction(Player player);
	
}
