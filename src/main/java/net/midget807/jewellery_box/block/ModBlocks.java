package net.midget807.jewellery_box.block;

import net.midget807.jewellery_box.JewelleryBoxMain;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks {

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block.asItem());
        return Registry.register(Registries.BLOCK, JewelleryBoxMain.id(name), block);
    }
    private static void registerBlockItem(String name, Item item) {
        Registry.register(Registries.ITEM, JewelleryBoxMain.id(name), item);
    }
    public static void registerModBlocks() {
        JewelleryBoxMain.LOGGER.info("Registering Mod Blocks");
    }
}
