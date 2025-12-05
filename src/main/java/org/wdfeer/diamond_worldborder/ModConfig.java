package org.wdfeer.diamond_worldborder;

public record ModConfig(String diamondId, double widthPerDiamond, double timePerDiamondSeconds) {
    public static final ModConfig DEFAULT = new ModConfig("minecraft:diamond", 1, 10);

    public static ModConfig init() {
        // TODO: save default config if necessary
        return loadConfig();
    }

    public static ModConfig loadConfig() {
        // TODO: implement loading config from file
        return DEFAULT;
    }

    public static void saveConfig(ModConfig config) {
        // TODO: implement saving `config` to file
    }
}
