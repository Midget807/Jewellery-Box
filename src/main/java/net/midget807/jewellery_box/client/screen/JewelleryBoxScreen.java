package net.midget807.jewellery_box.client.screen;

import net.midget807.jewellery_box.JewelleryBoxMain;
import net.midget807.jewellery_box.screen.jewellery_box.JewelleryBoxScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class JewelleryBoxScreen extends HandledScreen<JewelleryBoxScreenHandler> implements ScreenHandlerProvider<JewelleryBoxScreenHandler> {
    private static final Identifier TEXTURE_8 = new Identifier(JewelleryBoxMain.MOD_ID, "textures/gui/container/jewellery_box_8.png");
    private static final Identifier TEXTURE_4 = new Identifier(JewelleryBoxMain.MOD_ID, "textures/gui/container/jewellery_box_4.png");
    private static final Identifier TEXTURE_2 = new Identifier(JewelleryBoxMain.MOD_ID, "textures/gui/container/jewellery_box_2.png");
    private final int columns;
    public JewelleryBoxScreen(JewelleryBoxScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.columns = handler.getColumns();
        this.backgroundHeight = 133;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        switch (columns) {
            case 4:
                context.drawTexture(TEXTURE_4, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
                break;
            case 2:
                context.drawTexture(TEXTURE_2, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
                break;
            default:
                context.drawTexture(TEXTURE_8, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        }
    }
}
