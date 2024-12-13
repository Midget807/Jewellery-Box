package net.midget807.jewellery_box.stat;

import net.midget807.jewellery_box.JewelleryBoxMain;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class ModStats {
    public static final Identifier OPEN_JEWELLERY_BOX = registerCustomStat("open_jewellery_box", StatFormatter.DEFAULT);
    private static Identifier registerCustomStat(String name, StatFormatter formatter) {
        Identifier identifier = JewelleryBoxMain.id(name);
        Registry.register(Registries.CUSTOM_STAT, name, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }
    public static void registerModStats() {
        JewelleryBoxMain.LOGGER.info("Registering Mod Stats");
    }
}
