package org.wdfeer.diamond_worldborder;

public record ConfigEntry(String diamondId, double widthPerDiamond, double timePerDiamondSeconds) {
    public static ConfigEntry DEFAULT = new ConfigEntry("minecraft:diamond", 1, 10);
}
