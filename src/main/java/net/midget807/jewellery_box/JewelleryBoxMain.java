package net.midget807.jewellery_box;

import net.fabricmc.api.ModInitializer;

import net.midget807.jewellery_box.block.ModBlocks;
import net.midget807.jewellery_box.block.entity.ModBlockEntities;
import net.midget807.jewellery_box.screen.ModScreenHandlers;
import net.midget807.jewellery_box.stat.ModStats;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JewelleryBoxMain implements ModInitializer {
	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
	public static final String MOD_ID = "jewellery_box";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();
		ModScreenHandlers.registerModScreenHandlers();
		ModStats.registerModStats();

		LOGGER.info("wassup");
	}
}