package org.wdfeer.diamond_worldborder;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

public record ModConfig(String diamondId, double widthPerDiamond, double timePerDiamondSeconds) {
    public static final ModConfig DEFAULT = new ModConfig("minecraft:diamond", 1, 10);

    private static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("diamond_worldborder.json");

    public static ModConfig init() {
        if (configPath.toFile().exists()) {
            try {
                return loadConfig();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (JsonSyntaxException e) {
                DiamondWorldBorder.LOGGER.error("Invalid config JSON syntax! Using default config...");
                return DEFAULT;
            }
        } else {
            saveConfig(DEFAULT);
            return DEFAULT;
        }
    }

    public static ModConfig loadConfig() throws FileNotFoundException, JsonIOException, JsonSyntaxException {
        var file = configPath.toFile();
        var reader = new FileReader(file);
        var gson = new Gson();
        return gson.fromJson(reader, ModConfig.class);
    }

    public static void saveConfig(ModConfig config) {
        // TODO: implement saving `config` to file
    }
}
