package com.Wands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHandler {

    @SuppressWarnings("unused")
    public static void handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Check if command sender is player
        if (sender instanceof Player == false) {
            return;
        }

        // Safe command player as sender
        Player player = (Player) sender;

        if (label.equals("wands")) {

            if (args.length == 0) {
                player.sendMessage("Wands v" + Main.version + " by 'pyzn'. Use /wands help to get a list of commands");

                // Return so later commands aren't executed
                return;
            }

            if (args.length == 1 && args[0].equals("help")) {

                // Display a list of all commands for the player
                player.sendMessage("Here is a list of all valid wands commands:\n"
                        + "/wands help\n"
                        + "/wands give <name>");

                // Return so later commands aren't executed
                return;
            }

            if (args.length == 2 && args[0].equals("give")) {

                if (!player.hasPermission("wands.give")
                        && !player.isOp()) {
                    player.sendMessage("You do not seem to have the right permissions to perform this command");

                    // Return so later commands aren't executed
                    return;
                }

                // Loop through all possible
                for (Wand wand : Main.wandVariations) {

                    // Check if wand is the one requested
                    if (wand.name.toLowerCase().contains(args[1].toLowerCase())) {

                        // Create wand item
                        ItemStack wandItem = wand.createWandItem();

                        // Add wand to players inventory
                        player.getInventory().addItem(wandItem);

                        // Notify the player
                        player.sendMessage("You have recieved a " + wand.name);

                        // Return so later commands aren't executed
                        return;
                    }
                }
            }

            player.sendMessage("Invalid command usage. Use /wands help to get a list of commands");
        }
    }

}
