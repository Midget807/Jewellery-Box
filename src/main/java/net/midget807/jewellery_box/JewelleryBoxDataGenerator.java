package net.midget807.jewellery_box;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.midget807.jewellery_box.datagen.ModBlockLootTableProvider;
import net.midget807.jewellery_box.datagen.ModBlockTagProvider;
import net.midget807.jewellery_box.datagen.ModModelProvider;
import net.midget807.jewellery_box.datagen.ModRecipeProvider;

public class JewelleryBoxDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockLootTableProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
