package com.Wands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class WandLogic implements Listener {
	
	Main main;
	
	WandLogic(Main main) {
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
		
		// Get living entity
		LivingEntity livingEntity = (LivingEntity) entity;
		
		// Check if entity is a witch that died
		if (entity.getType() == EntityType.WITCH
				&& livingEntity.getHealth() <= event.getDamage()) {
			
			// Create a random number generator to check if the player found a wand
			Random rdm = new Random();
			
			// Create chance
			int chance = rdm.nextInt(101);
			
			// There is a 10% chance of a wand dropping after a witch died
			if (chance <= 10) {
				
				// Select random wand type
				WandTypes type = WandTypes.values()[rdm.nextInt(WandTypes.values().length)];
				
				// Give wand to player
				InventoryManager.giveWandToPlayer(player, type);
				
				// Notify player about wand
				event.getDamager().sendMessage(ChatColor.DARK_PURPLE + "You found a wand while looting the witch");
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		// Check if player has left clicked the air
		if (event.getAction() == Action.LEFT_CLICK_AIR) {
			
			// Get player
			Player player = event.getPlayer();
			
			// Get players item
			ItemStack item = player.getInventory().getItemInMainHand();
			
			// Check if player is holding an item
			if (item != null
					&& item.getItemMeta() != null
					&& item.getItemMeta().getDisplayName() != null) {
				
				// Check if player is holding a fire ball wand
				if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Fireball Wand")) {
					if (removeGunpowderFromPlayerInventory(player, 1)) {
						executeFireWandLogic(player);
					}
					else {
						player.sendMessage("You do not have enough gunpowder to use this wand");
					}
				}
				
				// Check if player is holding an ice wand
				if (item.getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Ice Wand")) {
					if (removeGunpowderFromPlayerInventory(player, 5)) {
						executeIceWandLogic(player);
					}
					else {
						player.sendMessage("You do not have enough gunpowder to use this wand");
					}
				}
				
				// Check if player is holding a ground wand
				if (item.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Earth Wand")) {
					if (removeGunpowderFromPlayerInventory(player, 10)) {
						executeGroundWandLogic(player);
					}
					else {
						player.sendMessage("You do not have enough gunpowder to use this wand");
					}
				}
			}
		}
	}
	
	boolean removeGunpowderFromPlayerInventory(Player player, int amount) {
		// Get player inventory
		Inventory inventory = player.getInventory();
		
		// Loop through all items in player inventory
		for (ItemStack item : inventory.getContents()) {
			
			// Check if item is gunpowder
			if (item != null &&
					item.getType() == Material.GUNPOWDER) {
				
				// Remove one if there is more than the needed amount
				if (item.getAmount() > amount) {
					item.setAmount(item.getAmount() - amount);
					return true;
				}
				// Remove the whole stack if its exactly the needed amount
				else if (item.getAmount() == amount) {
					inventory.remove(item);
					return true;
				}
			}
		}
		
		return false;
	}
	
	void executeFireWandLogic(Player player) {
		try {
			// Set which blocks to ignore in following checks
			Set<Material> ignoredBlocks = new HashSet<>(Arrays.asList(Material.AIR));
			
			// Get blocks in line of sight of player
			List<Block> lineOfSightBlocks = player.getLineOfSight(ignoredBlocks, 3);
			
			// Only spawn fireball if there is some room infront of player
			if (lineOfSightBlocks.size() >= 3) {
				
				// Get location of block infront of player
				Location blockLocation = lineOfSightBlocks.get(2).getLocation();		
				
				// Calculate fireball position from block position
				Location fireballLocation = new Location(
						player.getWorld(),
						blockLocation.getX(),
						blockLocation.getY(),
						blockLocation.getZ(),
						player.getLocation().getYaw(),
						player.getLocation().getPitch());
				
				// Spawn fireball
				player.getWorld().spawnEntity(fireballLocation, EntityType.FIREBALL);
			}
		}
		catch (Exception ex) {
			player.sendMessage("Could not use wand");
		}
	}
	
	void executeIceWandLogic(Player player) {
		// Get player location
		Location playerLocation = player.getLocation();
		
		// Create random number generator to determine how long snow should stay
		Random rdm = new Random();
		
		// Range of the ice effect
		int range = 8;
		
		// Loop through blocks near player
		for (int x = -range; x < range; x++)
		for (int y = -range; y < range; y++)
		for (int z = -range; z < range; z++) {
			
			// Get block position
			Location blockLocation = new Location(
					playerLocation.getWorld(),
					playerLocation.getX() + x,
					playerLocation.getY() + y,
					playerLocation.getZ() + z);
			
			// Get position under block
			Location blockGroundLocation = new Location(
					playerLocation.getWorld(),
					playerLocation.getX() + x,
					playerLocation.getY() + y - 1,
					playerLocation.getZ() + z);
			
			// Check if block is of type air above a block of type ground
			// aswell as if the block is in range
			if (blockLocation.getBlock().getType() == Material.AIR
					&& blockGroundLocation.getBlock().getType().isSolid()
					&& blockLocation.distance(playerLocation) <= range) {
				
				// Place snow at that block position
				blockLocation.getBlock().setType(Material.SNOW);
				
				// Schedule task to turn snow blocks back to normal blocks
				BukkitRunnable runnable = new BukkitRunnable() {
					@Override
					public void run() {
						if (blockLocation.getBlock().getType() == Material.SNOW) {
							blockLocation.getBlock().setType(Material.AIR);
						}
					}
				};
				runnable.runTaskLater(main, rdm.nextInt(40) + 200);
			}
		}
		
		// Loop through all entities near player
		for (Entity entity : player.getNearbyEntities(range, range, range)) {
			
			// Check if the entity is not the player and also alive
			if (entity != player
					&& entity instanceof LivingEntity) {
				
				// Get living entity
				LivingEntity livingEntity = (LivingEntity) entity;
				
				// Prepare potion effect
				PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 200, 10);
				
				// Add potion effects to entity
				livingEntity.addPotionEffect(slowness);
			}
		}
	}
	
	// Current range for earthquake
	//public int earthquakeRange = 0;
	
	void executeGroundWandLogic(Player player) {
		BukkitRunnable runnable = new BukkitRunnable() {
			
			// Current range for earthquake
			int earthquakeRange = 3;
			
			// Get start location for earthquake
			Location playerLocation = player.getLocation();
			
			@Override
			public void run() {

				// Safe all the blocks that need to be launched into a list
				List<Location> blocksToLaunch = new ArrayList<Location>();
					
				for (int x = -earthquakeRange; x < earthquakeRange; x++)
				for (int y = -earthquakeRange; y < earthquakeRange; y++)
				for (int z = -earthquakeRange; z < earthquakeRange; z++) {
						
					// Get block position
					Location blockLocation = new Location(
							playerLocation.getWorld(),
							playerLocation.getX() + x,
							playerLocation.getY() + y,
							playerLocation.getZ() + z);
						
					// Get position under block
					Location blockCeilingLocation = new Location(
							playerLocation.getWorld(),
							playerLocation.getX() + x,
							playerLocation.getY() + y + 1,
							playerLocation.getZ() + z);
						
					// Check if block is in range and above the block there is air
					if (blockLocation.distance(playerLocation) <= earthquakeRange + 0.5f
							&& blockLocation.distance(playerLocation) >= earthquakeRange - 1
							&& !blockCeilingLocation.getBlock().getType().isSolid()) {
						blocksToLaunch.add(blockLocation);
					}
				}
					
				// Launch all blocks
				for (Location blockLocation : blocksToLaunch) {
						
					// Spawn a falling block at block location
					FallingBlock fallingBlock = blockLocation.getWorld().spawnFallingBlock(
									blockLocation,
									blockLocation.getBlock().getBlockData());
						
					// Add velocity to falling block
					fallingBlock.setVelocity(new Vector(0, 0.5f, 0));
					
					// Add velocity to entities near block
					for (Entity entity : fallingBlock.getNearbyEntities(1, 1, 1)) {
						
						// Check if entity is alive and not the player
						if (entity instanceof LivingEntity
								&& entity != player) {
							
							// Get living entity
							LivingEntity livingEntity = (LivingEntity) entity;
							
							// Apply velocity to living entity
							livingEntity.setVelocity(new Vector(0, 1, 0));
						}
					}
						
					// Remove block from world
					blockLocation.getBlock().setType(Material.AIR);
				}
					
				// Clear list of blocks to launch
				blocksToLaunch.clear();
				
				// Check if earthquake still needs to go
				if (earthquakeRange <= 6) {
					
					// Increase range of earthquake for next go
					earthquakeRange++;
				}
				// Cancel earthquake if its still needs to go
				else {
					earthquakeRange = 0;
					this.cancel();
				}
			}
		};
		runnable.runTaskTimer(main, 0, 2);
	}
}
