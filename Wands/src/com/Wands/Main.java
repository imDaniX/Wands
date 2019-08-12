package com.Wands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.Wands.WandVariations.*;

public class Main extends JavaPlugin {
	
	public static double version = 1.3;
	public static boolean costEnabled = true;
	public static List<Wand> wandVariations = new ArrayList<>();
	public static FileConfiguration config;
	
	@SuppressWarnings("unused")
	@Override
	public void onEnable() {
		
		// Load the config containing plugin settings
		loadConfig();
		
		// Initiate all the wand variations
		initiateWands();
		
		// Initiate wand dropper
		new WandDropper(this);
	}
	
	void loadConfig() {
		
		// Get the config
		config = this.getConfig();
		
		// Set some default settings
		config.addDefault("Wands.Drop.Enabled", true);
		config.addDefault("Wands.Drop.Chance", 10);
		
		// Save all the settings to the config
		config.options().copyDefaults(true);
		this.saveConfig();
	}
	
	void initiateWands() {
		
		// Common
		wandVariations.add(new CraftingWand(this, 	ChatColor.RESET + "Craftsman Wand",			ChatColor.RESET + "common",				config.getInt("Wands.Craftsman.Cooldown")));
		
		// Uncommon
		wandVariations.add(new FireballWand(this, 	ChatColor.AQUA + "Fireball Wand", 			ChatColor.AQUA + "uncommon",			config.getInt("Wands.Fireball.Cooldown")));
		wandVariations.add(new TeleportWand(this, 	ChatColor.AQUA + "Teleport Wand", 			ChatColor.AQUA + "uncommon",			config.getInt("Wands.Teleport.Cooldown")));
		wandVariations.add(new RocketWand(this,		ChatColor.AQUA + "Rocket Wand", 			ChatColor.AQUA + "uncommon",			config.getInt("Wands.Rocket.Cooldown")));
		wandVariations.add(new SlimeWand(this, 		ChatColor.AQUA + "Slime Wand", 				ChatColor.AQUA + "uncommon", 			config.getInt("Wands.Slime.Cooldown")));
		
		// Rare
		wandVariations.add(new TrickeryWand(this, 	ChatColor.BLUE + "Trickery Wand", 			ChatColor.BLUE + "rare",				config.getInt("Wands.Trickery.Cooldown")));
		wandVariations.add(new IceWand(this, 		ChatColor.BLUE + "Ice Wand", 				ChatColor.BLUE + "rare",				config.getInt("Wands.Ice.Cooldown")));
		
		// Legendary
		wandVariations.add(new WoolWand(this, 		ChatColor.GOLD + "Cloud Wand", 				ChatColor.GOLD + "legendary",			config.getInt("Wands.Cloud.Cooldown")));
		wandVariations.add(new EarthWand(this, 		ChatColor.GOLD + "Earth Wand", 				ChatColor.GOLD + "legendary",			config.getInt("Wands.Earth.Cooldown")));
		
		// Ancient
		wandVariations.add(new SummonersWand(this, 	ChatColor.LIGHT_PURPLE + "Summoners Wand", 	ChatColor.LIGHT_PURPLE + "ancient",		config.getInt("Wands.Summoners.Cooldown")));
		wandVariations.add(new LightningWand(this, 	ChatColor.LIGHT_PURPLE + "Lightning Wand", 	ChatColor.LIGHT_PURPLE + "ancient",		config.getInt("Wands.Lightning.Cooldown")));
		wandVariations.add(new PumpkinWand(this, 	ChatColor.LIGHT_PURPLE + "Pumpkin Wand",	ChatColor.LIGHT_PURPLE + "ancient",		config.getInt("Wands.Pumpkin.Cooldown")));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Send command information to command handler
		CommandHandler.handleCommand(sender, cmd, label, args);
		return true;
	}
	
}
