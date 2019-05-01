package com.Wands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.Wands.WandVariations.*;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		// Initiate wand dropper
		new WandDropper(this);
		
		// Initiate all the wand variations
		initiateWands();
	}
	
	void initiateWands() {
		new FireballWand(this, ChatColor.RED + "Fireball Wand", 1);
		new IceWand(this, ChatColor.BLUE + "Ice Wand", 5);
		new EarthWand(this, ChatColor.GRAY + "Earth Wand", 10);
		new TeleportWand(this, ChatColor.LIGHT_PURPLE + "Teleport Wand", 15);
		new SummonersWand(this, ChatColor.RESET + "Summoners Wand", 30);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Send command information to command handler
		CommandHandler.handleCommand(sender, cmd, label, args);
		return true;
	}
	
}
