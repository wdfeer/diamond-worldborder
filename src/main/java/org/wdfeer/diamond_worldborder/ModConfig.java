package org.wdfeer.diamond_worldborder;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public record ModConfig(String diamondId, double widthPerDiamond, double timePerDiamondSeconds) {
    public static final ModConfig DEFAULT = new ModConfig("minecraft:diamond", 1, 10);

    private static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("diamond_worldborder.json");

    public static ModConfig init() {
        if (configPath.toFile().exists()) {
            try {
                return loadConfig();
            } catch (JsonSyntaxException e) {
                DiamondWorldBorder.LOGGER.error("Invalid config JSON syntax! Using default config...");
                return DEFAULT;
            }
        } else {
            try {
                saveConfig(DEFAULT);
            } catch (IOException e) {
                throw new RuntimeException("Failed saving default config! " + e);
            }
            return DEFAULT;
        }
    }

    public static ModConfig loadConfig() throws JsonSyntaxException {
        var file = configPath.toFile();
        try (var reader = new FileReader(file)) {
            var gson = new Gson();
            return gson.fromJson(reader, ModConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading config! " + e);
        }
    }

    public static void saveConfig(ModConfig config) throws IOException {
        var file = configPath.toFile();
        try (var writer = new FileWriter(file)) {
            var gson = new Gson();
            writer.write(gson.toJson(config));
        }
    }
}
