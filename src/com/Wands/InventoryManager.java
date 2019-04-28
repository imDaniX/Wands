package com.Wands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class InventoryManager {
	public static void giveWandToPlayer(Player player, WandTypes type) {
		// Create stick
		ItemStack wand = new ItemStack(Material.STICK, 1);
		
		// Get sticks meta data
		ItemMeta wandMeta = wand.getItemMeta();
		
		// Set stick name based on input
		if (type == WandTypes.FIRE_BALL) {
			wandMeta.setDisplayName(ChatColor.RED + "Fireball Wand");
		}
		
		if (type == WandTypes.ICE) {
			wandMeta.setDisplayName(ChatColor.BLUE + "Ice Wand");
		}
		
		if (type == WandTypes.GROUND) {
			wandMeta.setDisplayName(ChatColor.GRAY + "Earth Wand");
		}
		
		// Add stick enchantment
		wandMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		
		// Set stick lore
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Left click to use wand");
		lore.add(ChatColor.GRAY + "Uses Gunpower as ammunition");
		wandMeta.setLore(lore);
		wand.setItemMeta(wandMeta);
		
		// Give stick to player
		player.getInventory().addItem(wand);
	}
}
