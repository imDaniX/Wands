package com.Wands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public abstract class Wand implements Listener {

    protected Main main;
    protected String name, rarity;
    protected int cooldown;

    protected List<String> playersOnCooldown = new ArrayList<>();

    public Wand(Main main, String name, String rarity, int cooldown) {

        // Register events as listener
        main.getServer().getPluginManager().registerEvents(this, main);

        // Set local variables to the given variables
        this.main = main;
        this.name = name;
        this.rarity = rarity;
        this.cooldown = cooldown;
    }

    public ItemStack createWandItem() {

        // Create stick
        ItemStack wandItem = new ItemStack(Material.STICK, 1);

        // Get sticks meta data
        ItemMeta wandMeta = wandItem.getItemMeta();

        // Set stick name based on input
        wandMeta.setDisplayName(this.name);

        // Add stick enchantment
        wandMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);

        // Set stick lore
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Left click to use this " + this.rarity + ChatColor.GRAY + " wand");
        lore.add(ChatColor.GRAY + "This wand has a cooldown of " + ChatColor.GREEN + this.cooldown + ChatColor.GRAY + " seconds");
        wandMeta.setLore(lore);
        wandItem.setItemMeta(wandMeta);

        // Return the finished want item
        return wandItem;
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

                // Check if player is holding the correct wand
                if (item.getItemMeta().getDisplayName().equals(this.name)) {

                    // Don't execute any action if the player is on cooldown
                    if (this.playersOnCooldown.contains(player.getName())) {

                        // Play a sound to let the player know theres still a cooldown
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);

                        // Return the function
                        return;
                    }

                    // Run the wand action
                    runAction(player);

                    // Add player to cooldown list
                    this.playersOnCooldown.add(player.getName());

                    // This runnable will remove the player from cooldown list after a given time
                    BukkitRunnable runnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            // Remove player from cooldown list
                            Wand.this.playersOnCooldown.remove(player.getName());
                        }
                    };
                    runnable.runTaskLater(this.main, 20 * this.cooldown);
                }
            }
        }
    }

    public abstract void runAction(Player player);

}
