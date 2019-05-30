package com.Wands;

import java.util.Random;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class WandDropper implements Listener {
	
	Main main;
	int dropChance = 10;
	
	WandDropper(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
		this.main = main;
	}
	
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
		
		// Entity has to be of type living entity
		if (entity instanceof LivingEntity == false) {
			return;
		}
		
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
				
				// Randomly select a wand
				int randomWandIndex = rdm.nextInt(Main.wandVariations.size());
				
				// Select random wand type
				Wand randomWand = Main.wandVariations.get(randomWandIndex);
				
				// Create wand item
				ItemStack wandItem = randomWand.createWandItem();
				
				// Drop wand item at witches death position
				player.getWorld().dropItem(entity.getLocation(), wandItem);
			}
		}
	}
	
}
