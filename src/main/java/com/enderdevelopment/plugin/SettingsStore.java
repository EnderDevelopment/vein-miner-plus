package com.enderdevelopment.plugin;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public final class SettingsStore {
    private final Main plugin;
    private final File file;
    private final Map<UUID, PlayerSettings> loaded = new java.util.HashMap<>();

    public SettingsStore(Main plugin) { this.plugin = plugin; this.file = new File(plugin.getDataFolder(), "players.yml"); }

    public PlayerSettings get(Player player) {
        return loaded.computeIfAbsent(player.getUniqueId(), id -> read(id));
    }

    private PlayerSettings read(UUID id) {
        PlayerSettings result = new PlayerSettings();
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = data.getConfigurationSection(id.toString());
        if (section == null) return result;
        for (BlockCategory category : BlockCategory.values()) {
            result.setEnabled(category, section.getBoolean(category.name() + ".enabled", result.enabled(category)));
            result.setDecay(category, section.getInt(category.name() + ".decay", 0));
        }
        return result;
    }

    public void save() {
        FileConfiguration data = new YamlConfiguration();
        for (Map.Entry<UUID, PlayerSettings> entry : loaded.entrySet()) for (BlockCategory category : BlockCategory.values()) {
            String path = entry.getKey() + "." + category.name();
            data.set(path + ".enabled", entry.getValue().enabled(category));
            data.set(path + ".decay", entry.getValue().decay(category));
        }
        try { data.save(file); } catch (IOException exception) { plugin.getLogger().warning("Could not save player settings: " + exception.getMessage()); }
    }
}
