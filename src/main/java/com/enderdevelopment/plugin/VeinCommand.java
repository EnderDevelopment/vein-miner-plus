package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class VeinCommand implements CommandExecutor {
    private final Main plugin;
    public VeinCommand(Main plugin) { this.plugin = plugin; }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) { sender.sendMessage("Only players can use this command."); return true; }
        if (command.getName().equalsIgnoreCase("veinbook")) {
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            ItemMeta meta = book.getItemMeta();
            meta.displayName(Component.text("Vein Miner Guide", NamedTextColor.GOLD));
            meta.getPersistentDataContainer().set(plugin.bookKey(), org.bukkit.persistence.PersistentDataType.INTEGER, 1);
            meta.lore(java.util.List.of(Component.text("Right-click to choose breakable blocks", NamedTextColor.GRAY)));
            book.setItemMeta(meta);
            player.getInventory().addItem(book);
            player.sendMessage(Component.text("You received the Vein Miner Guide.", NamedTextColor.GREEN));
            return true;
        }
        if (args.length != 2) { player.sendMessage(Component.text("Usage: /veindecay <category> <percent>", NamedTextColor.RED)); return true; }
        BlockCategory category = BlockCategory.parse(args[0]);
        if (category == null) { player.sendMessage(Component.text("Unknown category. Use ores, trees, grass, gravel, stone, or dark_stone.", NamedTextColor.RED)); return true; }
        try {
            int percent = Integer.parseInt(args[1]);
            if (percent < 0 || percent > 100) throw new NumberFormatException();
            plugin.settings().get(player).setDecay(category, percent);
            player.sendMessage(Component.text(category.displayName() + " debris chance set to " + percent + "%.", NamedTextColor.GREEN));
        } catch (NumberFormatException exception) { player.sendMessage(Component.text("Percent must be a number from 0 to 100.", NamedTextColor.RED)); }
        return true;
    }
}
