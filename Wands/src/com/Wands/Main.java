package com.Wands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.Wands.WandVariations.*;

public class Main extends JavaPlugin {
	
	public static boolean costEnabled = true;
	
	public void onEnable() {
		// Initiate wand dropper
		new WandDropper(this);
		
		// Initiate all the wand variations
		initiateWands();
	}
	
	void initiateWands() {
		
		// Common
		new CraftingWand(this, 		ChatColor.RESET + "Craftsman Wand",				"commond",		0);
		
		// Uncommon
		new FireballWand(this, 		ChatColor.AQUA + "Fireball Wand", 				"uncommon",		5);
		new TeleportWand(this, 		ChatColor.AQUA + "Teleport Wand", 				"uncommon",		5);
		new RocketWand(this,		ChatColor.AQUA + "Rocket Wand", 				"uncommon",		5);
		
		// Rare
		new TrickeryWand(this, 		ChatColor.BLUE + "Trickery Wand", 				"rare",			20);
		new IceWand(this, 			ChatColor.BLUE + "Ice Wand", 					"rare",			20);
		
		// Legendary
		new WoolWand(this, 			ChatColor.GOLD + "Cloud Wand", 					"legendary",	30);
		new PumpkinWand(this, 		ChatColor.GOLD + "Pumpkin Wand",				"legendary",	30);
		new EarthWand(this, 		ChatColor.GOLD + "Earth Wand", 					"legendary",	30);
		
		// Ancient
		new SummonersWand(this, 	ChatColor.LIGHT_PURPLE + "Summoners Wand", 		"ancient",		60);
		new LightningWand(this, 	ChatColor.LIGHT_PURPLE + "Lightning Wand", 		"ancient",		60);
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Send command information to command handler
		CommandHandler.handleCommand(sender, cmd, label, args);
		return true;
	}
	
}
