package com.Wands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHandler {
	
	public static void handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Check if command sender is player
		if (sender instanceof Player == false) {
			return;
		}
		
		// Safe command player as sender
		Player player = (Player) sender;
		
		if (label.equals("wands")) {
			
			// Check if the player has operator rights
			if (!player.isOp()) {
				player.sendMessage("Only operators can use wand commands");
				return;
			}
			
			if (args.length == 2 && args[0].equals("give")) {		
				
				// Loop through all possible 
				for (Wand wand : Main.wandVariations) {
					
					// Check if wand is the one requested
					if (wand.name.toLowerCase().contains(args[1].toLowerCase())) {
						
						// Create wand item
						ItemStack wandItem = wand.createWandItem();
						
						// Add wand to players inventory
						player.getInventory().addItem(wandItem);
						
						// Notify the player
						player.sendMessage("You have recieved a " + wand.name);
						
						// Return the function
						return;
					}
				}
			}
		}
	}
	
}
