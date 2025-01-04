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
import org.joml.Quaternionf;

public class JewelleryBoxEntityRenderer implements BlockEntityRenderer<JewelleryBoxBlockEntity> {
    public static final Identifier FULL_TEXTURE = JewelleryBoxMain.id("textures/entity/jewellery_box/full.png");
    public static final Identifier HALF_TEXTURE = JewelleryBoxMain.id("textures/entity/jewellery_box/half.png");
    public static final Identifier QUARTER_TEXTURE = JewelleryBoxMain.id("textures/entity/jewellery_box/quarter.png");
    public static final float SCALE_MAGNITUDE = 0.35f;
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
        matrices.translate(0.5f, 0.75f, 0.5f);
        matrices.scale(SCALE_MAGNITUDE, SCALE_MAGNITUDE, SCALE_MAGNITUDE);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
        if (entity.size != 0 || !entity.inventory.isEmpty()) {
            itemRenderer.renderItem(
                    stacks.get(0),
                    ModelTransformationMode.GUI,
                    getLightLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV,
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    1
            );
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

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
