package com.Wands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler {
	
	public static void handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Check if command sender is player
		if (sender instanceof Player == false) {
			return;
		}
		
		// Safe command player as sender
		//Player player = (Player) sender;
		
		/*
		if (label.equals("wands")) {
			if (args.length == 2 && args[0].equals("give")) {
				if (args[1].equals("fire_ball")) {
					InventoryManager.giveWandToPlayer(player, WandTypes.FIRE_BALL);
				}
				
				if (args[1].equals("ice")) {
					InventoryManager.giveWandToPlayer(player, WandTypes.ICE);
				}
				
				if (args[1].equals("ground")) {
					InventoryManager.giveWandToPlayer(player, WandTypes.GROUND);
				}
			}
		}
		*/
	}
	
}
