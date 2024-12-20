package net.midget807.jewellery_box.screen.jewellery_box;

import net.midget807.jewellery_box.block.entity.jewellery_box.JewelleryBoxBlockEntity;
import net.midget807.jewellery_box.screen.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class JewelleryBoxScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final int columns;
    public JewelleryBoxScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory, int columns) {
        this(type, syncId, inventory, new SimpleInventory(columns), columns);
    }
    public static JewelleryBoxScreenHandler createFull(int syncId, PlayerInventory playerInventory) {
        return new JewelleryBoxScreenHandler(ModScreenHandlers.JEWELLERY_BOX_8, syncId, playerInventory, 8);
    }
    public static JewelleryBoxScreenHandler createHalf(int syncId, PlayerInventory playerInventory) {
        return new JewelleryBoxScreenHandler(ModScreenHandlers.JEWELLERY_BOX_4, syncId, playerInventory, 4);
    }
    public static JewelleryBoxScreenHandler createQuarter(int syncId, PlayerInventory playerInventory) {
        return new JewelleryBoxScreenHandler(ModScreenHandlers.JEWELLERY_BOX_2, syncId, playerInventory, 2);
    }
    public static JewelleryBoxScreenHandler createFull(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new JewelleryBoxScreenHandler(ModScreenHandlers.JEWELLERY_BOX_8, syncId, playerInventory, inventory, 8);
    }
    public static JewelleryBoxScreenHandler createHalf(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new JewelleryBoxScreenHandler(ModScreenHandlers.JEWELLERY_BOX_4, syncId, playerInventory, inventory, 4);
    }
    public static JewelleryBoxScreenHandler createQuarter(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new JewelleryBoxScreenHandler(ModScreenHandlers.JEWELLERY_BOX_2, syncId, playerInventory, inventory, 2);
    }
    public JewelleryBoxScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int columns) {
        super(type, syncId);
        this.inventory = inventory;
        this.columns = columns;
        checkSize(inventory, columns);
        inventory.onOpen(playerInventory.player);
        int i = 51;
        int maxColumns = 8;
        //adds container columns

        for (int j = 0; j < columns; j++) {
            this.addSlot(new Slot(inventory, j, 17 + ((maxColumns - columns) * 9) + j * 18, 20));
        }

        //adds player inventory slots
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, j * 18 + i));
            }
        }
        //adds slots for hotbar
        for (int j = 0; j < 9; j++) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 109));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < this.inventory.size()) {
                if (!this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getColumns() {
        return this.columns;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
