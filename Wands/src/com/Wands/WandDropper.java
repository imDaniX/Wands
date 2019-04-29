package com.Wands;

import java.util.Random;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.md_5.bungee.api.ChatColor;

public class WandDropper implements Listener {
	
	Main main;
	int dropChance = 10;
	
	WandDropper(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		this.main = main;
	}
	
	public String[] wandVariationNames = new String[] 
			{
					ChatColor.RED + "Fireball Wand",
					ChatColor.BLUE + "Ice Wand",
					ChatColor.GRAY + "Earth Wand"
			};
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		// Event damager has to be of type player
		if (event.getDamager() instanceof Player == false) {
			return;
		}
		
		// Get player
		Player player = (Player) event.getDamager();
		
		// Get entity
		Entity entity = event.getEntity();
		
		// Get living entity
		LivingEntity livingEntity = (LivingEntity) entity;
		
		// Check if entity is a witch that died
		if (entity.getType() == EntityType.WITCH
				&& livingEntity.getHealth() <= event.getDamage()) {
			
			// Create a random number generator to check if the player found a wand
			Random rdm = new Random();
			
			// Create chance
			int chance = rdm.nextInt(101);
			
			// There is a certain % chance of a wand dropping after a witch died
			if (chance <= dropChance) {
				
				// Select random wand type
				String type = wandVariationNames[rdm.nextInt(wandVariationNames.length)];
				
				// Give wand to player
				InventoryManager.giveWandToPlayer(player, type);
				
				// Notify player about wand
				event.getDamager().sendMessage(ChatColor.DARK_PURPLE + "You found a wand while looting the witch");
			}
		}
	}
	
}
