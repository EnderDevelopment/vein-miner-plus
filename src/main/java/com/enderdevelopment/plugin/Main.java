package com.enderdevelopment.plugin;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private SettingsStore settings;
    private NamespacedKey bookKey;

    @Override public void onEnable() {
        settings = new SettingsStore(this);
        bookKey = new NamespacedKey(this, "vein_book");
        getServer().getPluginManager().registerEvents(new VeinListener(this), this);
        getServer().getPluginManager().registerEvents(new SelectionMenu(this), this);
        getServer().getPluginManager().registerEvents(new DeathChestListener(this), this);
        getCommand("veinbook").setExecutor(new VeinCommand(this));
        getCommand("veindecay").setExecutor(new VeinCommand(this));
    }

    @Override public void onDisable() { settings.save(); }
    public SettingsStore settings() { return settings; }
    public NamespacedKey bookKey() { return bookKey; }
}
