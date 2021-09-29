package com.Wands.WandVariations;

import com.Wands.BukkitUtils;
import com.Wands.Main;
import com.Wands.Wand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeleportWand extends Wand {

    int teleportMaximumRange = 30;

    public TeleportWand(Main main, String name, String rarity, int cost) {
        super(main, name, rarity, cost);
    }

    @Override
    public void runAction(Player player) {
        // Set which blocks to ignore in following checks
        Set<Material> ignoredBlocks = new HashSet<>(Arrays.asList(Material.AIR, Material.CAVE_AIR));

        // Get blocks in line of sight of player
        List<Block> lineOfSightBlocks = player.getLineOfSight(ignoredBlocks, this.teleportMaximumRange);

        // Get target block (last block in line of sight)
        Location targetLocation = lineOfSightBlocks.get(lineOfSightBlocks.size() - 1).getLocation();

        // Use the target location but player rotation
        Location teleportLocation = new Location(
                targetLocation.getWorld(),
                targetLocation.getX() + 0.5f,
                targetLocation.getY() + 1,
                targetLocation.getZ() + 0.5f,
                player.getLocation().getYaw(),
                player.getLocation().getPitch());

        // Play some particles where the player was
        BukkitUtils.emitParticles(teleportLocation.getWorld(),
                player.getLocation().add(0, 1, 0),
                Particle.DRAGON_BREATH, 100, 0.01, new Vector(0.25, 0.5, 0.25));
        // Play some particles where the player went to
        BukkitUtils.emitParticles(teleportLocation.getWorld(),
                teleportLocation.clone().add(0, 1, 0),
                Particle.DRAGON_BREATH, 100, 0.01, new Vector(0.25, 0.5, 0.25));

        // Teleport player to the block thats farthest away from the player
        player.teleport(teleportLocation);

        // Play a sound effect
        player.getWorld().playSound(teleportLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    }

}
