package org.wdfeer.diamond_worldborder;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.border.WorldBorderStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wdfeer.diamond_worldborder.config.ModConfig;

public class DiamondWorldBorder implements ModInitializer {
	public static final String MOD_ID = "diamond_worldborder";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerTickEvents.END_WORLD_TICK.register(this::postWorldTick);

		MidnightConfig.init(MOD_ID, ModConfig.class);

		LOGGER.info("Diamond World Border initialized!");
	}

	private void postWorldTick(ServerWorld world) {
		if (world.getTime() % 10 != 0) // Only run every 10 ticks
			return;

		// world.getWorldBorder() doesn't function properly in nether, end
		WorldBorder border = world.getServer().getOverworld().getWorldBorder();
		if (border.getStage() == WorldBorderStage.GROWING)
			return;

		for (Entity entity : world.iterateEntities()){
			if (!(entity instanceof ItemEntity itemEntity)
					|| !matchItemId(itemEntity, ModConfig.diamondId)
					|| !border.canCollide(entity, entity.getBoundingBox()))
				continue;

			int diamonds = itemEntity.getStack().getCount();
			itemEntity.getStack().decrement(diamonds);

			double size = border.getSize();
			double newSize = size + diamonds * ModConfig.widthPerDiamond;
			long timePerDiamond = (long)(ModConfig.timePerDiamondSeconds * 1e3);
			border.interpolateSize(size, newSize,  diamonds * timePerDiamond);
		}
	}

	private Boolean matchItemId(ItemEntity itemEntity, Identifier id) {
		ItemStack itemStack = itemEntity.getStack();
		Identifier itemId = Registries.ITEM.getId(itemStack.getItem());
		return id.equals(itemId);
	}
}