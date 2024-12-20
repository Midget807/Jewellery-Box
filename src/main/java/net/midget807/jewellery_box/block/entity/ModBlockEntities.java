package net.midget807.jewellery_box.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.midget807.jewellery_box.JewelleryBoxMain;
import net.midget807.jewellery_box.block.ModBlocks;
import net.midget807.jewellery_box.block.entity.jewellery_box.JewelleryBoxBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;

public class ModBlockEntities {
    public static final BlockEntityType<JewelleryBoxBlockEntity> JEWELLERY_BOX_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, JewelleryBoxMain.id("jewellery_box_entity"),
                    FabricBlockEntityTypeBuilder.create(JewelleryBoxBlockEntity::new, ModBlocks.FULL_JEWELLERY_BOX, ModBlocks.HALF_JEWELLERY_BOX).build()
            );
    public static void registerModBlockEntities() {
        JewelleryBoxMain.LOGGER.info("Registering Mod Block Entities");
    }
}
