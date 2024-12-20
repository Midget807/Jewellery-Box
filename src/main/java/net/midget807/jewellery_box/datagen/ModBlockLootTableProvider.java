package net.midget807.jewellery_box.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.midget807.jewellery_box.block.ModBlocks;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.FULL_JEWELLERY_BOX);
        addDrop(ModBlocks.HALF_JEWELLERY_BOX);
        addDrop(ModBlocks.QUARTER_JEWELLERY_BOX);
    }
}
