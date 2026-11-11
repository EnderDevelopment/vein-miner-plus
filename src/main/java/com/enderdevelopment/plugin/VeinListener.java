package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public final class VeinListener implements Listener {
    private final Main plugin;
    private final Set<Player> processing = new HashSet<>();
    public VeinListener(Main plugin) { this.plugin = plugin; }

    @EventHandler public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (processing.contains(player)) return;
        BlockCategory category = categoryOf(event.getBlock().getType());
        if (category == null || !plugin.settings().get(player).enabled(category)) return;
        processing.add(player);
        try {
            Set<Block> vein = findVein(event.getBlock(), category, 128);
            vein.remove(event.getBlock());
            for (Block block : vein) {
                if (Math.random() * 100 < plugin.settings().get(player).decay(category)) {
                    block.setType(Material.AIR, false);
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GRAVEL));
                } else {
                    block.breakNaturally(player.getInventory().getItemInMainHand());
                }
            }
            if (!vein.isEmpty()) player.sendActionBar(Component.text("Vein mined: " + (vein.size() + 1) + " blocks", NamedTextColor.GOLD));
        } finally { processing.remove(player); }
    }

    private Set<Block> findVein(Block origin, BlockCategory category, int limit) {
        Set<Block> found = new HashSet<>();
        ArrayDeque<Block> queue = new ArrayDeque<>(); queue.add(origin); found.add(origin);
        while (!queue.isEmpty() && found.size() < limit) {
            Block current = queue.remove();
            for (org.bukkit.block.BlockFace face : org.bukkit.block.BlockFace.values()) {
                if (face == org.bukkit.block.BlockFace.SELF) continue;
                Block next = current.getRelative(face);
                if (!found.contains(next) && category.matches(next.getType())) { found.add(next); queue.add(next); }
            }
        }
        return found;
    }

    private BlockCategory categoryOf(Material material) {
        for (BlockCategory category : BlockCategory.values()) if (category.matches(material)) return category;
        return null;
    }
}
