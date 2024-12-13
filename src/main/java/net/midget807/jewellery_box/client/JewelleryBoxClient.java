package net.midget807.jewellery_box.client;

import net.fabricmc.api.ClientModInitializer;
import net.midget807.jewellery_box.client.screen.JewelleryBoxScreen;
import net.midget807.jewellery_box.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class JewelleryBoxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.JEWELLERY_BOX_8, JewelleryBoxScreen::new);
        HandledScreens.register(ModScreenHandlers.JEWELLERY_BOX_4, JewelleryBoxScreen::new);
        HandledScreens.register(ModScreenHandlers.JEWELLERY_BOX_2, JewelleryBoxScreen::new);
    }
}
