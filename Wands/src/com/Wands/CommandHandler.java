package com.Wands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandHandler {
	
	public static void handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Check if command sender is player
		if (sender instanceof Player == false) {
			return;
		}
		
		// Safe command player as sender
		Player player = (Player) sender;
		
		
		if (label.equals("wands")) {
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
					InventoryManager.giveWandToPlayer(player, ChatColor.LIGHT_PURPLE + "Earth Wand");
				}
				
				if (args[1].equals("summoners")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.RESET + "Summoners Wand");
				}
				
				if (args[1].equals("lightning")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.RESET + "Lightning Wand");
				}
				
				if (args[1].equals("rocket")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.YELLOW + "Rocket Wand");
				}
				
				if (args[1].equals("craftsman")) {
					InventoryManager.giveWandToPlayer(player, ChatColor.DARK_BLUE + "Craftsman Wand");
				}
			}
		}
	}
	
}
