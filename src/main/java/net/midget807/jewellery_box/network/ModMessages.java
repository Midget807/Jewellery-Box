package net.midget807.jewellery_box.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.jewellery_box.JewelleryBoxMain;
import net.midget807.jewellery_box.network.packet.S2C.ItemStackSyncPacket;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ITEM_SYNC = JewelleryBoxMain.id("item_sync");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncPacket::receive);
    }
}
