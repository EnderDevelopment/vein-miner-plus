package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class DeathChestListener implements Listener {
    private final Main plugin;
    public DeathChestListener(Main plugin) { this.plugin = plugin; }

    @EventHandler public void onDeath(PlayerDeathEvent event) {
        if (event.getKeepInventory() || event.getDrops().isEmpty()) return;
        var location = event.getEntity().getLocation().getBlock().getLocation();
        Block chestBlock = location.getBlock();
        if (!chestBlock.getType().isAir()) chestBlock = location.clone().add(0, 1, 0).getBlock();
        if (!chestBlock.getType().isAir()) return;
        chestBlock.setType(Material.CHEST, false);
        Chest chest = (Chest) chestBlock.getState();
        Inventory inventory = chest.getInventory();
        for (ItemStack drop : event.getDrops()) inventory.addItem(drop);
        event.getDrops().clear();
        chest.customName(Component.text(event.getEntity().getName() + "'s death chest", NamedTextColor.GOLD));
        chest.update(true, false);
        event.getEntity().sendMessage(Component.text("Your items are in a chest at your death location.", NamedTextColor.YELLOW));
    }
}
