package net.midget807.jewellery_box.screen;

import net.midget807.jewellery_box.JewelleryBoxMain;
import net.midget807.jewellery_box.block.jewellery_box.JewelleryBoxBlock;
import net.midget807.jewellery_box.screen.jewellery_box.JewelleryBoxScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;

public class ModScreenHandlers {
    public static final ScreenHandlerType<JewelleryBoxScreenHandler> JEWELLERY_BOX_8 = registerScreenHandler("jewellery_box_full", JewelleryBoxScreenHandler::createFull);
    public static final ScreenHandlerType<JewelleryBoxScreenHandler> JEWELLERY_BOX_4 = registerScreenHandler("jewellery_box_half", JewelleryBoxScreenHandler::createHalf);
    public static final ScreenHandlerType<JewelleryBoxScreenHandler> JEWELLERY_BOX_2 = registerScreenHandler("jewellery_box_quarter", JewelleryBoxScreenHandler::createQuarter);
    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String name, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, JewelleryBoxMain.id(name), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }
    public static void registerModScreenHandlers() {
        JewelleryBoxMain.LOGGER.info("Registering Mod Screen Handlers");
    }
}
