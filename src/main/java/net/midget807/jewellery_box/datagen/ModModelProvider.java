package net.midget807.jewellery_box.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.midget807.jewellery_box.block.ModBlocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.FULL_JEWELLERY_BOX);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.HALF_JEWELLERY_BOX);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.QUARTER_JEWELLERY_BOX);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
