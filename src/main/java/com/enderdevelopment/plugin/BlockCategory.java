package com.enderdevelopment.plugin;

import org.bukkit.Material;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

public enum BlockCategory {
    ORES("Ores", Material.DIAMOND_ORE),
    TREES("Trees", Material.OAK_LOG),
    GRASS("Grass", Material.GRASS_BLOCK),
    GRAVEL("Gravel", Material.GRAVEL),
    STONE("Stone", Material.STONE),
    DARK_STONE("Dark Stone", Material.DEEPSLATE);

    private final String displayName;
    private final Material icon;

    BlockCategory(String displayName, Material icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String displayName() { return displayName; }
    public Material icon() { return icon; }

    public boolean matches(Material material) {
        String name = material.name();
        return switch (this) {
            case ORES -> name.endsWith("_ORE") || material == Material.ANCIENT_DEBRIS;
            case TREES -> name.endsWith("_LOG") || name.endsWith("_WOOD") || name.endsWith("_LEAVES") || name.endsWith("_LEAF");
            case GRASS -> material == Material.GRASS_BLOCK || material == Material.DIRT || material == Material.COARSE_DIRT
                    || material == Material.ROOTED_DIRT || material == Material.PODZOL || material == Material.MYCELIUM
                    || material == Material.SHORT_GRASS || material == Material.TALL_GRASS || material == Material.FERN || material == Material.LARGE_FERN;
            case GRAVEL -> material == Material.GRAVEL || material == Material.SUSPICIOUS_GRAVEL;
            case STONE -> EnumSet.of(Material.STONE, Material.COBBLESTONE, Material.ANDESITE, Material.DIORITE, Material.GRANITE,
                    Material.COBBLED_DEEPSLATE).contains(material);
            case DARK_STONE -> name.contains("DEEPSLATE") || material == Material.BLACKSTONE || material == Material.BASALT;
        };
    }

    public static BlockCategory parse(String value) {
        String normalized = value.toUpperCase(Locale.ROOT).replace('-', '_');
        for (BlockCategory category : values()) if (category.name().equals(normalized)) return category;
        return null;
    }
}
