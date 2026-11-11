package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public final class SelectionMenu implements Listener {
    private final Main plugin;
    private static final String TITLE = "Vein Miner Blocks";
    public SelectionMenu(Main plugin) { this.plugin = plugin; }

    @EventHandler public void onBookUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta() || item.getItemMeta().getPersistentDataContainer().get(plugin.bookKey(), PersistentDataType.INTEGER) == null) return;
        event.setCancelled(true);
        open(event.getPlayer());
    }

    private void open(org.bukkit.entity.Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, Component.text(TITLE));
        PlayerSettings settings = plugin.settings().get(player);
        for (BlockCategory category : BlockCategory.values()) {
            ItemStack item = new ItemStack(category.icon());
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text((settings.enabled(category) ? "Enabled: " : "Disabled: ") + category.displayName(), settings.enabled(category) ? NamedTextColor.GREEN : NamedTextColor.RED));
            meta.lore(new ArrayList<>(java.util.List.of(Component.text("Click to toggle", NamedTextColor.GRAY), Component.text("Debris chance: " + settings.decay(category) + "%", NamedTextColor.GRAY))));
            item.setItemMeta(meta);
            inventory.setItem(category.ordinal(), item);
        }
        player.openInventory(inventory);
    }

    @EventHandler public void onClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(Component.text(TITLE)) || !(event.getWhoClicked() instanceof org.bukkit.entity.Player player)) return;
        event.setCancelled(true);
        if (event.getRawSlot() < 0 || event.getRawSlot() >= BlockCategory.values().length) return;
        BlockCategory category = BlockCategory.values()[event.getRawSlot()];
        PlayerSettings settings = plugin.settings().get(player);
        settings.setEnabled(category, !settings.enabled(category));
        player.closeInventory();
        open(player);
    }
}
