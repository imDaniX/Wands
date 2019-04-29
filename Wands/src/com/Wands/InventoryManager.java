package com.Wands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class InventoryManager {
	
	public static ItemStack createWandItem(Player player, String name) {
		// Create stick
		ItemStack wandItem = new ItemStack(Material.STICK, 1);
		
		// Get sticks meta data
		ItemMeta wandMeta = wandItem.getItemMeta();
		
		// Set stick name based on input
		wandMeta.setDisplayName(name);
		
		// Add stick enchantment
		wandMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		
		// Set stick lore
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Left click to use wand");
		lore.add(ChatColor.GRAY + "Uses Gunpower as ammunition");
		wandMeta.setLore(lore);
		wandItem.setItemMeta(wandMeta);
		
		// Return the finished want item
		return wandItem;
	}
	
	public static boolean removeGunpowderFromPlayerInventory(Player player, int amount) {
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
