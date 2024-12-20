package net.midget807.jewellery_box.block.entity.jewellery_box;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.jewellery_box.block.entity.ImplementedInventory;
import net.midget807.jewellery_box.block.entity.ModBlockEntities;
import net.midget807.jewellery_box.block.jewellery_box.JewelleryBoxBlock;
import net.midget807.jewellery_box.network.ModMessages;
import net.midget807.jewellery_box.screen.jewellery_box.JewelleryBoxScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JewelleryBoxBlockEntity extends LootableContainerBlockEntity implements ImplementedInventory, LidOpenable, Nameable {

    public int size;
    public DefaultedList<ItemStack> inventory;
    private final ViewerCountManager stateManager = new ViewerCountManager() {
        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            JewelleryBoxBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_CHEST_OPEN);
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            JewelleryBoxBlockEntity.playSound(world, pos, state, SoundEvents.BLOCK_CHEST_CLOSE);
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            JewelleryBoxBlockEntity.this.onViewerCountUpdate(world, pos, state, oldViewerCount, newViewerCount);
        }

        @Override
        protected boolean isPlayerViewing(PlayerEntity player) {
            if (!(player.currentScreenHandler instanceof JewelleryBoxScreenHandler)) {
                return false;
            } else {
                Inventory inventory = ((JewelleryBoxScreenHandler) player.currentScreenHandler).getInventory();
                return inventory == JewelleryBoxBlockEntity.this;
            }
        }
    };

    private final ChestLidAnimator lidAnimator = new ChestLidAnimator();

    public JewelleryBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.JEWELLERY_BOX_BLOCK_ENTITY, pos, state);
    }

    public DefaultedList<ItemStack> getRenderStacks() {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        if (defaultedList.isEmpty()) {
            defaultedList.add(ItemStack.EMPTY);
        } else {
            defaultedList.addAll(this.inventory);
        }
        return defaultedList;
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.lidAnimator.setOpen(data > 0);
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }


    private static void playSound(World world, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 0.3f, world.random.nextFloat() * 0.1f + 0.9f);
    }

    private void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        Block block = state.getBlock();
        world.addSyncedBlockEvent(pos, block, 1, newViewerCount);
    }
    public static void clientTick(World world, BlockPos pos, BlockState state, JewelleryBoxBlockEntity blockEntity) {
        blockEntity.lidAnimator.step();
    }


    @Override
    public int size() {
        return 8;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }



    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return switch (size) {
            case 2 -> JewelleryBoxScreenHandler.createQuarter(syncId, playerInventory, this);
            case 4 -> JewelleryBoxScreenHandler.createHalf(syncId, playerInventory, this);
            default -> JewelleryBoxScreenHandler.createFull(syncId, playerInventory, this);
        };
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.jewellery_box.jewellery_box");
    }


    @Override
    public float getAnimationProgress(float tickDelta) {
        return 0;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        super.readNbt(nbt);
        /*
        if (nbt.contains(CUSTOM_NAME_KEY, NbtElement.STRING_TYPE)) {
            this.customName = Text.Serializer.fromJson(nbt.getString(CUSTOM_NAME_KEY));
        }*/
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        /*
        if (this.customName != null) {
            nbt.putString(CUSTOM_NAME_KEY, Text.Serializer.toJson(this.customName));
        }*/
    }

    public void onScheduledTick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public void markDirty() {
        if (!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for (int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.ITEM_SYNC, data);
            }
        }
        super.markDirty();
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

}
