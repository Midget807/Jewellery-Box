package net.midget807.jewellery_box.network.packet.S2C;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.midget807.jewellery_box.block.entity.jewellery_box.JewelleryBoxBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ItemStackSyncPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            list.set(i, buf.readItemStack());
        }
        BlockPos blockPos = buf.readBlockPos();
        if (client.world.getBlockEntity(blockPos) instanceof JewelleryBoxBlockEntity jewelleryBoxBlockEntity) {
            jewelleryBoxBlockEntity.setInventory(list);
        }
    }
}
