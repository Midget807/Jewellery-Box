package net.midget807.jewellery_box.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.midget807.jewellery_box.JewelleryBoxMain;
import net.midget807.jewellery_box.block.jewellery_box.JewelleryBoxBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {
    public static final Block FULL_JEWELLERY_BOX = registerBlock("full_jewellery_box", new JewelleryBoxBlock(FabricBlockSettings.create().nonOpaque(), 8));
    public static final Block HALF_JEWELLERY_BOX = registerBlock("half_jewellery_box", new JewelleryBoxBlock(FabricBlockSettings.create().nonOpaque(), 4));
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, JewelleryBoxMain.id(name), block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, JewelleryBoxMain.id(name), new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks() {
        JewelleryBoxMain.LOGGER.info("Registering Mod Blocks");
    }
}
