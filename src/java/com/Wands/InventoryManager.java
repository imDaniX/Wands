package com.Wands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
	
	public static ItemStack createWandItem(String name) {
		// Create stick
		ItemStack wandItem = new ItemStack(Material.STICK, 1);
		
		// Get sticks meta data
		ItemMeta wandMeta = wandItem.getItemMeta();
		
		// Set stick name based on input
		wandMeta.setDisplayName(name);
		
		// Add stick enchantment
		wandMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		
		// Set stick lore
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Left click to use wand");
		wandMeta.setLore(lore);
		wandItem.setItemMeta(wandMeta);
		
		// Return the finished want item
		return wandItem;
	}
	
	
	public static void giveWandToPlayer(Player player, String name) {
		// Create stick
		ItemStack wandItem = new ItemStack(Material.STICK, 1);
		
		// Get sticks meta data
		ItemMeta wandMeta = wandItem.getItemMeta();
		
		// Set stick name based on input
		wandMeta.setDisplayName(name);
		
		// Add stick enchantment
		wandMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		
		// Set stick lore
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Left click to use wand");
		lore.add(ChatColor.GRAY + "Uses Gunpower as ammunition");
		wandMeta.setLore(lore);
		wandItem.setItemMeta(wandMeta);
		
		// Give the item to the player
		player.getInventory().addItem(wandItem);
	}
	
	public static boolean removeGunpowderFromPlayerInventory(Player player, int amount) {
		// If the wand doesn't have any action cost or wands are free to use, don't check for gunpowder
		if (amount == 0
				|| !Main.costEnabled) {
			return true;
		}
		
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
	
}
