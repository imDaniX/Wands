package com.Wands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Wands.InventoryManager;

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
				player.sendMessage("Only operators can recieve wands over commands");
				return;
			}
			
			if (args.length == 2 && args[0].equals("give")) {				
				if (args[1].equals("fireball")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.RED + "Fireball Wand");
				}
				
				if (args[1].equals("ice")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.BLUE + "Ice Wand");
				}
				
				if (args[1].equals("earth")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.GRAY + "Earth Wand");
				}
				
				if (args[1].equals("teleport")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.LIGHT_PURPLE + "Teleport Wand");
				}
				
				if (args[1].equals("summoners")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.AQUA + "Summoners Wand");
				}
				
				if (args[1].equals("lightning")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.DARK_BLUE + "Lightning Wand");
				}
				
				if (args[1].equals("rocket")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.YELLOW + "Rocket Wand");
				}
				
				if (args[1].equals("craftsman")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.DARK_AQUA + "Craftsman Wand");
				}
				
				if (args[1].equals("cloud")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.RESET + "Cloud Wand");
				}
				
				if (args[1].equals("trickery")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.DARK_GRAY + "Trickery Wand");
				}
				
				if (args[1].equals("pumpkin")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.GOLD + "Pumpkin Wand");
				}
			}
			
			if (args.length == 1 && args[0].equals("free")) {
				Main.costEnabled = !Main.costEnabled;
				
				if (Main.costEnabled) {
					player.sendMessage("Wands will now cost gunpowder again");
				}
				else {
					player.sendMessage("All wands are now free to use");
				}
			}
		}
	}
	
}
