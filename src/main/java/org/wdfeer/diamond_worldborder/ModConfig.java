package org.wdfeer.diamond_worldborder;

public record ModConfig(String diamondId, double widthPerDiamond, double timePerDiamondSeconds) {
    public static final ModConfig DEFAULT = new ModConfig("minecraft:diamond", 1, 10);

    public static ModConfig loadConfig() {
        // TODO: implement
        return DEFAULT;
    }
}
