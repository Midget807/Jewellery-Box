package net.midget807.jewellery_box.block.entity.jewellery_box;

import net.midget807.jewellery_box.block.entity.ImplementedInventory;
import net.midget807.jewellery_box.block.entity.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestLidAnimator;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class JewelleryBoxBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory, LidOpenable, Nameable {
    @Nullable
    private Text customName;
    private static final String CUSTOM_NAME_KEY = "CustomName";
    public int size = 1;
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
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
            if (!(player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                return false;
            } else {
                Inventory inventory = ((GenericContainerScreenHandler) player.currentScreenHandler).getInventory();
                return inventory == JewelleryBoxBlockEntity.this;
            }
        }
    };

    private final ChestLidAnimator lidAnimator = new ChestLidAnimator();

    public JewelleryBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.JEWELLERY_BOX_BLOCK_ENTITY, pos, state);
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

    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }

    @Override
    public Text getName() {
        return this.customName != null ? this.customName : this.getContainerName();
    }

    private Text getContainerName() {
        return Text.translatable("container.jewellery_box.jewellery_box");
    }

    @Override
    public Text getDisplayName() {
        return this.getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

    @Override
    public float getAnimationProgress(float tickDelta) {
        return 0;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        if (nbt.contains(CUSTOM_NAME_KEY, NbtElement.STRING_TYPE)) {
            this.customName = Text.Serializer.fromJson(nbt.getString(CUSTOM_NAME_KEY));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        if (this.customName != null) {
            nbt.putString(CUSTOM_NAME_KEY, Text.Serializer.toJson(this.customName));
        }
    }

    public void setCustomName(Text name) {
        this.customName = name;
    }

    public void onScheduledTick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }
}
