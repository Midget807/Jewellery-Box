package net.midget807.jewellery_box.client.renderer;

import net.midget807.jewellery_box.JewelleryBoxMain;
import net.midget807.jewellery_box.block.ModBlocks;
import net.midget807.jewellery_box.block.entity.jewellery_box.JewelleryBoxBlockEntity;
import net.midget807.jewellery_box.block.jewellery_box.JewelleryBoxBlock;
import net.midget807.jewellery_box.client.entity.ModEntityModelLayers;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class JewelleryBoxEntityRenderer implements BlockEntityRenderer<JewelleryBoxBlockEntity> {
    public static final Identifier FULL_TEXTURE = JewelleryBoxMain.id("textures/entity/jewellery_box/full.png");
    public static final Identifier HALF_TEXTURE = JewelleryBoxMain.id("textures/entity/jewellery_box/half.png");
    public static final Identifier QUARTER_TEXTURE = JewelleryBoxMain.id("textures/entity/jewellery_box/quarter.png");
    public static final float SCALE_MAGNITUDE = 0.25f;
    private static final String BASE = "base";
    private static final String LID = "lid";
    public final ModelPart fullBase;
    public final ModelPart fullLid;
    public final ModelPart halfBase;
    public final ModelPart halfLid;
    public final ModelPart quarterBase;
    public final ModelPart quarterLid;
    public JewelleryBoxEntityRenderer(BlockEntityRendererFactory.Context context) {
        ModelPart fullModelPart = context.getLayerModelPart(ModEntityModelLayers.FULL_JEWELLERY_BOX);
        this.fullBase = fullModelPart.getChild(BASE);
        this.fullLid = fullModelPart.getChild(LID);
        ModelPart halfModelPart = context.getLayerModelPart(ModEntityModelLayers.HALF_JEWELLERY_BOX);
        this.halfBase = halfModelPart.getChild(BASE);
        this.halfLid = halfModelPart.getChild(LID);
        ModelPart quarterModelPart = context.getLayerModelPart(ModEntityModelLayers.QUARTER_JEWELLERY_BOX);
        this.quarterBase = quarterModelPart.getChild(BASE);
        this.quarterLid = quarterModelPart.getChild(LID);
    }
    public static TexturedModelData getFullTextureModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                BASE,
                ModelPartBuilder.create()
                        .uv(48, 14).cuboid(-7.0F, 2.0F, -7.0F, 14.0F, 6.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 30).cuboid(-7.0F, 2.0F, -6.0F, 1.0F, 6.0F, 12.0F, new Dilation(0.0F))
                        .uv(48, 21).cuboid(-7.0F, 2.0F, 6.0F, 14.0F, 6.0F, 1.0F, new Dilation(0.0F))
                        .uv(26, 30).cuboid(6.0F, 2.0F, -6.0F, 1.0F, 6.0F, 12.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-6.0F, 3.0F, -6.0F, 12.0F, 5.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 16.0F, 0.0F)
        );
        modelPartData.addChild(
                LID,
                ModelPartBuilder.create()
                        .uv(26, 48).cuboid(-7.0F, -2.0F, -14.0F, 14.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 48).cuboid(6.0F, -2.0F, -13.0F, 1.0F, 2.0F, 12.0F, new Dilation(0.0F))
                        .uv(26, 51).cuboid(-7.0F, -2.0F, -1.0F, 14.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(48, 0).cuboid(-7.0F, -2.0F, -13.0F, 1.0F, 2.0F, 12.0F, new Dilation(0.0F))
                        .uv(0, 17).cuboid(-6.0F, -2.0F, -13.0F, 12.0F, 1.0F, 12.0F, new Dilation(0.0F))
                        .uv(52, 28).cuboid(-1.0F, -1.0F, -15.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 18.0F, 7.0F)
        );
        return TexturedModelData.of(modelData, 128, 128);
    }
    public static TexturedModelData getHalfTextureModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                BASE,
                ModelPartBuilder.create()
                        .uv(0, 14).cuboid(-7.0F, -4.0F, -3.5F, 14.0F, 4.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 30).cuboid(6.0F, -4.0F, -2.5F, 1.0F, 4.0F, 5.0F, new Dilation(0.0F))
                        .uv(0, 19).cuboid(-7.0F, -4.0F, 2.5F, 14.0F, 4.0F, 1.0F, new Dilation(0.0F))
                        .uv(12, 30).cuboid(-7.0F, -4.0F, -2.5F, 1.0F, 4.0F, 5.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-6.0F, -3.0F, -2.5F, 12.0F, 3.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );
        modelPartData.addChild(
                LID,
                ModelPartBuilder.create()
                        .uv(0, 24).cuboid(-7.0F, -2.0F, -7.0F, 14.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(30, 14).cuboid(6.0F, -2.0F, -6.0F, 1.0F, 2.0F, 5.0F, new Dilation(0.0F))
                        .uv(0, 27).cuboid(-7.0F, -2.0F, -1.0F, 14.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(30, 21).cuboid(-7.0F, -2.0F, -6.0F, 1.0F, 2.0F, 5.0F, new Dilation(0.0F))
                        .uv(0, 8).cuboid(-6.0F, -2.0F, -6.0F, 12.0F, 1.0F, 5.0F, new Dilation(0.0F))
                        .uv(24, 30).cuboid(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 20.0F, 3.5F)
        );
        return TexturedModelData.of(modelData, 64, 64);
    }
    public static TexturedModelData getQuarterTextureModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                BASE,
                ModelPartBuilder.create()
                        .uv(0, 13).cuboid(-3.5F, -3.0F, -3.5F, 7.0F, 3.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 17).cuboid(-3.5F, -3.0F, -2.5F, 1.0F, 3.0F, 5.0F, new Dilation(0.0F))
                        .uv(16, 13).cuboid(-3.5F, -3.0F, 2.5F, 7.0F, 3.0F, 1.0F, new Dilation(0.0F))
                        .uv(12, 17).cuboid(2.5F, -3.0F, -2.5F, 1.0F, 3.0F, 5.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-2.5F, -2.0F, -2.5F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );
        modelPartData.addChild(
                LID,
                ModelPartBuilder.create()
                        .uv(20, 0).cuboid(-3.5F, -2.0F, -7.0F, 7.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(20, 3).cuboid(-3.5F, -2.0F, -1.0F, 7.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(20, 6).cuboid(-3.5F, -2.0F, -6.0F, 1.0F, 2.0F, 5.0F, new Dilation(0.0F))
                        .uv(24, 17).cuboid(2.5F, -2.0F, -6.0F, 1.0F, 2.0F, 5.0F, new Dilation(0.0F))
                        .uv(0, 7).cuboid(-2.5F, -2.0F, -6.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F))
                        .uv(24, 24).cuboid(-0.5F, -1.0F, -8.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 21.0F, 3.5F)
        );
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void render(JewelleryBoxBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        //Local Variables
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        DefaultedList<ItemStack> stacks = entity.getRenderStacks();
        World world = entity.getWorld();
        boolean worldNull = world != null;
        BlockState blockState = worldNull ? entity.getCachedState() : ModBlocks.FULL_JEWELLERY_BOX.getDefaultState().with(JewelleryBoxBlock.FACING, Direction.SOUTH);
        float rotation = ((Direction) blockState.get(JewelleryBoxBlock.FACING)).asRotation();


        //Item Renderer
        matrices.push();
        matrices.translate(0.5f, 0.32f, 0.5f);
        matrices.scale(SCALE_MAGNITUDE, SCALE_MAGNITUDE, SCALE_MAGNITUDE);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
        /*
        switch (entity.size) {
            case 2 -> this.renderItems(Math.min(stacks.size(), 2), entity, stacks, itemRenderer, getLightLevel(entity.getWorld(), entity.getPos()), matrices, vertexConsumers, world, (int) entity.getPos().asLong());
            case 4 -> this.renderItems(Math.min(stacks.size(), 2), entity, stacks, itemRenderer, getLightLevel(entity.getWorld(), entity.getPos()), matrices, vertexConsumers, world, (int) entity.getPos().asLong());
            default -> this.renderItems(Math.min(stacks.size(), 2), entity, stacks, itemRenderer, getLightLevel(entity.getWorld(), entity.getPos()), matrices, vertexConsumers, world, (int) entity.getPos().asLong());
        }
        */
        DefaultedList<ItemStack> availableRenderStacks = this.getRenderStacks(entity, stacks);
        if (!availableRenderStacks.isEmpty()) {
            switch (availableRenderStacks.size()) {
                case 4:
                    this.renderEvenItems(
                            false,
                            2,
                            2,
                            availableRenderStacks.get(0),
                            availableRenderStacks.get(1),
                            availableRenderStacks.get(2),
                            availableRenderStacks.get(3),
                            matrices,
                            itemRenderer,
                            getLightLevel(entity.getWorld(), entity.getPos()),
                            vertexConsumers,
                            world,
                            (int) entity.getPos().asLong()
                    );
                    break;
                case 3:
                    this.renderOddItems(
                            false,
                            2,
                            2,
                            1,
                            availableRenderStacks.get(0),
                            availableRenderStacks.get(1),
                            availableRenderStacks.get(2),
                            matrices,
                            itemRenderer,
                            getLightLevel(entity.getWorld(), entity.getPos()),
                            vertexConsumers,
                            world,
                            (int) entity.getPos().asLong()
                    );
                    break;
                case 2:
                    this.renderEvenItems(
                            entity.size <= 4,
                            2,
                            1,
                            availableRenderStacks.get(0),
                            availableRenderStacks.get(1),
                            ItemStack.EMPTY,
                            ItemStack.EMPTY,
                            matrices,
                            itemRenderer,
                            getLightLevel(entity.getWorld(), entity.getPos()),
                            vertexConsumers,
                            world,
                            (int) entity.getPos().asLong()
                    );
                    break;
                default:
                    this.renderOddItems(
                            entity.size <= 2,
                            1,
                            1,
                            0,
                            availableRenderStacks.get(0),
                            ItemStack.EMPTY,
                            ItemStack.EMPTY,
                            matrices,
                            itemRenderer,
                            getLightLevel(entity.getWorld(), entity.getPos()),
                            vertexConsumers,
                            world,
                            (int) entity.getPos().asLong()
                    );
            }
        }

        matrices.pop();

        //Block Entity Renderer
        if (blockState.getBlock() instanceof JewelleryBoxBlock) {
            matrices.push();
            DoubleBlockProperties.PropertySource<JewelleryBoxBlockEntity> propertySource;
            propertySource = DoubleBlockProperties.PropertyRetriever::getFallback;
            float animationProgress = propertySource.apply(JewelleryBoxBlock.getAnimationProgressRetriever(entity)).get(tickDelta);
            animationProgress = 1.0f - animationProgress;
            animationProgress = 1.0f - animationProgress * animationProgress * animationProgress;
            matrices.translate(0.5f, 1.5f, 0.5f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation));
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(180));
            VertexConsumer vertexConsumer;
            switch (entity.size) {
                case 2:
                    vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(QUARTER_TEXTURE));
                    this.render(matrices, vertexConsumer, this.quarterLid, this.quarterBase, animationProgress, getLightLevel(entity.getWorld(), entity.getPos()), overlay);
                    break;
                case 4:
                    vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(HALF_TEXTURE));
                    this.render(matrices, vertexConsumer, this.halfLid, this.halfBase, animationProgress, getLightLevel(entity.getWorld(), entity.getPos()), overlay);
                    break;
                default:
                    vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(FULL_TEXTURE));
                    this.render(matrices, vertexConsumer, this.fullLid, this.fullBase, animationProgress, getLightLevel(entity.getWorld(), entity.getPos()), overlay);
                    break;
            }
            matrices.pop();
        }
    }

    private void render(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart base, float openFactor, int light, int overlay) {
        lid.pitch = -(openFactor * (float) (Math.PI / 2));
        lid.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }
    private DefaultedList<ItemStack> getRenderStacks(
            JewelleryBoxBlockEntity entity,
            DefaultedList<ItemStack> inventory
    ) {

        ItemStack stack1 = ItemStack.EMPTY;
        ItemStack stack2 = ItemStack.EMPTY;
        ItemStack stack3 = ItemStack.EMPTY;
        ItemStack stack4 = ItemStack.EMPTY;
        assert entity.size != 0 || !entity.inventory.isEmpty();
        for (int k1 = 0; k1 < inventory.size(); k1++) {
            if (inventory.get(k1) != ItemStack.EMPTY && inventory.size() >= 2) {
                for (int k2 = k1 + 1; k2 < inventory.size(); k2++) {
                    if (inventory.get(k2) != ItemStack.EMPTY && inventory.size() >= 4) {
                        for (int k3 = k2 + 1; k3 < inventory.size(); k3++) {
                            if (inventory.get(k3) != ItemStack.EMPTY && inventory.size() >= 8) {
                                for (int k4 = k3 + 1; k4 < inventory.size(); k4++) {
                                    if (inventory.get(k4) != ItemStack.EMPTY) {
                                        stack4 = inventory.get(k4);
                                        break;
                                    }
                                }
                                stack3 = inventory.get(k3);
                                break;
                            }
                        }
                        stack2 = inventory.get(k2);
                        break;
                    }
                }
                stack1 = inventory.get(k1);
                break;
            }
        }
        DefaultedList<ItemStack> availableStacks = DefaultedList.of();
        if (stack1 == ItemStack.EMPTY) {
            return availableStacks;
        } else {
            availableStacks.add(0, stack1);
            if (stack2 == ItemStack.EMPTY) {
                return availableStacks;
            } else {
                availableStacks.add(1, stack2);
                if (stack3 == ItemStack.EMPTY) {
                    return availableStacks;
                } else {
                    availableStacks.add(2, stack3);
                    if (stack4 == ItemStack.EMPTY) {
                        return availableStacks;
                    } else {
                        availableStacks.add(3, stack4);
                    }
                }
            }
        }
        return availableStacks;
    }

    private void renderEvenItems(
            boolean halfSize,
            int maxColumnSize,
            int maxRowSize,
            ItemStack stack1,
            ItemStack stack2,
            ItemStack stack3,
            ItemStack stack4,
            MatrixStack matrices,
            ItemRenderer itemRenderer,
            int light,
            VertexConsumerProvider vertexConsumers,
            World world,
            int seed
    ) {
        ItemStack renderedStack;
        for (int i = 1; i <= maxRowSize; i++) {
            for (int j = 1; j <= maxColumnSize; j++) {
                if (j == 2) {
                    if (i == 2) {
                        renderedStack = stack4;
                    } else {
                        renderedStack = stack2;
                    }
                } else {
                    if (i == 2) {
                        renderedStack = stack3;
                    } else {
                        renderedStack = stack1;
                    }
                }
                if (renderedStack != ItemStack.EMPTY) {
                    matrices.push();
                    matrices.translate(1.0 - (1.8 * (j)), 1.0 - (1.8 * (i)), 0.0f);
                    if (j == 2) {
                        matrices.translate(0.5f, 0.0f, 0.0f);
                    }
                    if (i == 2) {
                        matrices.translate(0.0f, 0.5f, 0.0f);
                    }
                    if (maxRowSize == 2) {
                        matrices.translate(1.45f, 1.45f, 0.0f);
                    } else {
                        matrices.translate(1.45f, 0.8f, 0.0f);
                    }
                    if (halfSize) {
                        matrices.translate(0.0f, 0.0f, 0.35f);
                    }
                    itemRenderer.renderItem(
                            renderedStack,
                            ModelTransformationMode.FIXED,
                            light,
                            OverlayTexture.DEFAULT_UV,
                            matrices,
                            vertexConsumers,
                            world,
                            seed + j
                    );
                    matrices.pop();
                }
            }
        }
    }

    private void renderOddItems(
            boolean quarterSize,
            int maxColumnSizeR1,
            int maxRowSize,
            int maxColumnSizeR2,
            ItemStack stack1,
            ItemStack stack2,
            ItemStack stack3,
            MatrixStack matrices,
            ItemRenderer itemRenderer,
            int light,
            VertexConsumerProvider vertexConsumers,
            World world,
            int seed
    ) {
        ItemStack renderedStack;
        for (int i = 1; i <= maxRowSize; i++) {
            for (int j = 1; j <= (i == 1 ? maxColumnSizeR1 : maxColumnSizeR2); j++) {
                if (j == 2) {
                    if (i == 2) {
                        renderedStack = stack3;
                    } else {
                        renderedStack = stack2;
                    }
                } else {
                        renderedStack = stack1;
                }
                if (renderedStack != ItemStack.EMPTY) {
                    matrices.push();
                    matrices.translate(1.0 - (1.8 * (j)), 1.0 - (1.8 * (i)), 0.0f);
                    if (i == 2 && j == 1) {
                        matrices.translate(1.0 - 1.65, 0.0f, 0.0f);
                    }
                    if (j == 2) {
                        matrices.translate(0.5f, 0.0f, 0.0f);
                    }
                    if (i == 2) {
                        matrices.translate(0.0f, 0.5f, 0.0f);
                    }
                    if (maxRowSize == 2) {
                        matrices.translate(1.45f, 1.45f, 0.0f);
                    } else {
                        matrices.translate(0.675f, 0.675f, 0.0f);
                    }
                    if (i == 1 && j == 1 && stack2 == ItemStack.EMPTY) {
                        matrices.translate(0.125f, 0.125f, 0.0f);
                        if (quarterSize) {
                            matrices.translate(0.0f, 0.0f, 0.5f);
                        }
                    }
                    itemRenderer.renderItem(
                            renderedStack,
                            ModelTransformationMode.FIXED,
                            light,
                            OverlayTexture.DEFAULT_UV,
                            matrices,
                            vertexConsumers,
                            world,
                            seed + j
                    );
                    matrices.pop();
                }
            }
        }
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
