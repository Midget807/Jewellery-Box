package net.midget807.jewellery_box.client;

import net.fabricmc.api.ClientModInitializer;
import net.midget807.jewellery_box.block.entity.ModBlockEntities;
import net.midget807.jewellery_box.client.renderer.FullJewelleryBoxEntityRenderer;
import net.midget807.jewellery_box.client.screen.JewelleryBoxScreen;
import net.midget807.jewellery_box.network.ModMessages;
import net.midget807.jewellery_box.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class JewelleryBoxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.JEWELLERY_BOX_8, JewelleryBoxScreen::new);
        HandledScreens.register(ModScreenHandlers.JEWELLERY_BOX_4, JewelleryBoxScreen::new);
        HandledScreens.register(ModScreenHandlers.JEWELLERY_BOX_2, JewelleryBoxScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.JEWELLERY_BOX_BLOCK_ENTITY, FullJewelleryBoxEntityRenderer::new);
        ModMessages.registerS2CPackets();
    }
}
