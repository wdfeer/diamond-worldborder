package org.wdfeer.diamond_worldborder.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.util.Identifier;

public class ModConfig extends MidnightConfig {
    @Entry
    public static Identifier diamondId = Identifier.of("minecraft", "diamond");

    @Entry(min = 0, max = 1000000)
    public static double widthPerDiamond = 1;

    @Entry(min = 0, max = 1000000)
    public static double timePerDiamondSeconds = 10;
}
