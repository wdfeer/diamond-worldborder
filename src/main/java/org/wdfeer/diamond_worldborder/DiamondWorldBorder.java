package org.wdfeer.diamond_worldborder;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.border.WorldBorderStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiamondWorldBorder implements ModInitializer {
	public static final String MOD_ID = "diamond_worldborder";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerTickEvents.END_WORLD_TICK.register(this::postWorldTick);
		LOGGER.info("Diamond World Border initialized!");
	}

	private void postWorldTick(ServerWorld world) {
		if (world.getTime() % 10 != 0) // Only run every 10 ticks
			return;

		WorldBorder border = world.getWorldBorder();
		if (border.getStage() == WorldBorderStage.GROWING)
			return;

		for (Entity entity : world.iterateEntities()){
			if (!(entity instanceof ItemEntity item)
					|| !item.getStack().isOf(Items.DIAMOND)
					|| !border.canCollide(entity, entity.getBoundingBox()))
				continue;

			item.getStack().decrement(1);

			double size = border.getSize();
			border.interpolateSize(size, size + 1.0, 10 * 1000);
		}
	}
}