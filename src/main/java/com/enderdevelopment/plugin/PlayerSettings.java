package com.enderdevelopment.plugin;

import java.util.EnumMap;

public final class PlayerSettings {
    private final EnumMap<BlockCategory, Boolean> enabled = new EnumMap<>(BlockCategory.class);
    private final EnumMap<BlockCategory, Integer> decay = new EnumMap<>(BlockCategory.class);

    public PlayerSettings() {
        for (BlockCategory category : BlockCategory.values()) {
            enabled.put(category, category == BlockCategory.ORES || category == BlockCategory.TREES);
            decay.put(category, 0);
        }
    }

    public boolean enabled(BlockCategory category) { return enabled.get(category); }
    public void setEnabled(BlockCategory category, boolean value) { enabled.put(category, value); }
    public int decay(BlockCategory category) { return decay.get(category); }
    public void setDecay(BlockCategory category, int value) { decay.put(category, Math.max(0, Math.min(100, value))); }
}
