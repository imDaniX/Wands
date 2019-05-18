package com.Wands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.Wands.WandVariations.*;

public class Main extends JavaPlugin {
	
	public static boolean costEnabled = true;
	public static List<Wand> wandVariations = new ArrayList<>();
	
	public void onEnable() {
		// Initiate wand dropper
		new WandDropper(this);
		
		// Initiate all the wand variations
		initiateWands();
	}
	
	void initiateWands() {
		
		// Common
		wandVariations.add(new CraftingWand(this, 	ChatColor.RESET + "Craftsman Wand",			ChatColor.RESET + "commond",		0));
		
		// Uncommon
		wandVariations.add(new FireballWand(this, 	ChatColor.AQUA + "Fireball Wand", 			ChatColor.AQUA + "uncommon",		5));
		wandVariations.add(new TeleportWand(this, 	ChatColor.AQUA + "Teleport Wand", 			ChatColor.AQUA + "uncommon",		5));
		wandVariations.add(new RocketWand(this,		ChatColor.AQUA + "Rocket Wand", 			ChatColor.AQUA + "uncommon",		5));
		
		// Rare
		wandVariations.add(new TrickeryWand(this, 	ChatColor.BLUE + "Trickery Wand", 			ChatColor.BLUE + "rare",			20));
		wandVariations.add(new IceWand(this, 		ChatColor.BLUE + "Ice Wand", 				ChatColor.BLUE + "rare",			20));
		
		// Legendary
		wandVariations.add(new WoolWand(this, 		ChatColor.GOLD + "Cloud Wand", 				ChatColor.GOLD + "legendary",	30));
		wandVariations.add(new PumpkinWand(this, 	ChatColor.GOLD + "Pumpkin Wand",			ChatColor.GOLD + "legendary",	30));
		wandVariations.add(new EarthWand(this, 		ChatColor.GOLD + "Earth Wand", 				ChatColor.GOLD + "legendary",	30));
		
		// Ancient
		wandVariations.add(new SummonersWand(this, 	ChatColor.LIGHT_PURPLE + "Summoners Wand", 	ChatColor.LIGHT_PURPLE + "ancient",		60));
		wandVariations.add(new LightningWand(this, 	ChatColor.LIGHT_PURPLE + "Lightning Wand", 	ChatColor.LIGHT_PURPLE + "ancient",		60));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Send command information to command handler
		CommandHandler.handleCommand(sender, cmd, label, args);
		return true;
	}
	
}
