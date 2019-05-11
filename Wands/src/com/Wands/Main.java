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
		new FireballWand(this, ChatColor.RED + "Fireball Wand", 2);
		new IceWand(this, ChatColor.BLUE + "Ice Wand", 5);
		new EarthWand(this, ChatColor.GRAY + "Earth Wand", 10);
		new TeleportWand(this, ChatColor.LIGHT_PURPLE + "Teleport Wand", 15);
		new SummonersWand(this, ChatColor.AQUA + "Summoners Wand", 30);
		new LightningWand(this, ChatColor.DARK_BLUE + "Lightning Wand", 20);
		new RocketWand(this, ChatColor.YELLOW + "Rocket Wand", 10);
		new CraftingWand(this, ChatColor.DARK_AQUA + "Craftsman Wand", 0);
		new WoolWand(this, ChatColor.RESET + "Cloud Wand", 8);
		new TrickeryWand(this, ChatColor.DARK_GRAY + "Trickery Wand", 5);
		new PumpkinWand(this, ChatColor.GOLD + "Pumpkin Wand", 15);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Send command information to command handler
		CommandHandler.handleCommand(sender, cmd, label, args);
		return true;
	}
	
}
