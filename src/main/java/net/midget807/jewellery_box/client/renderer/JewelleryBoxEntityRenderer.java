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
    public static final float SCALE_MAGNITUDE = 0.35f;
    private static final String BASE = "base";
    private static final String LID = "lid";
    public final ModelPart base;
    public final ModelPart lid;
    public JewelleryBoxEntityRenderer(BlockEntityRendererFactory.Context context) {
        ModelPart modelPart = context.getLayerModelPart(ModEntityModelLayers.JEWELLERY_BOX);
        this.base = modelPart.getChild(BASE);
        this.lid = modelPart.getChild(LID);
    }
    public static TexturedModelData getTextureModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                "lid",
                ModelPartBuilder.create()
                        .uv(48, 14).cuboid(-7.0F, 0.0F, -7.0F, 14.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 47).cuboid(6.0F, 0.0F, -6.0F, 1.0F, 2.0F, 12.0F, new Dilation(0.0F))
                        .uv(48, 17).cuboid(-7.0F, 0.0F, 6.0F, 14.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(26, 47).cuboid(-7.0F, 0.0F, -6.0F, 1.0F, 2.0F, 12.0F, new Dilation(0.0F))
                        .uv(0, 16).cuboid(-6.0F, 0.0F, -6.0F, 12.0F, 1.0F, 12.0F, new Dilation(0.0F))
                        .uv(48, 20).cuboid(-1.0F, 1.0F, -8.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.NONE
        );
        modelPartData.addChild(
                "base",
                ModelPartBuilder.create()
                        .uv(48, 0).cuboid(-7.0F, 2.0F, -7.0F, 14.0F, 6.0F, 1.0F, new Dilation(0.0F))
                        .uv(0, 29).cuboid(-7.0F, 2.0F, -6.0F, 1.0F, 6.0F, 12.0F, new Dilation(0.0F))
                        .uv(48, 7).cuboid(-7.0F, 2.0F, 6.0F, 14.0F, 6.0F, 1.0F, new Dilation(0.0F))
                        .uv(26, 29).cuboid(6.0F, 2.0F, -6.0F, 1.0F, 6.0F, 12.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-6.0F, 4.0F, -6.0F, 12.0F, 4.0F, 12.0F, new Dilation(0.0F)),
                ModelTransform.NONE
        );
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void render(JewelleryBoxBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        DefaultedList<ItemStack> stacks = entity.getRenderStacks();
        World world = entity.getWorld();
        boolean worldNull = world != null;
        BlockState blockState = worldNull ? entity.getCachedState() : ModBlocks.FULL_JEWELLERY_BOX.getDefaultState().with(JewelleryBoxBlock.FACING, Direction.SOUTH);
        float rotation = ((Direction) blockState.get(JewelleryBoxBlock.FACING)).asRotation();
        matrices.push();
        //Item Renderer
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
        if (blockState.getBlock() instanceof JewelleryBoxBlock jewelleryBoxBlock) {
            matrices.push();
            DoubleBlockProperties.PropertySource<JewelleryBoxBlockEntity> propertySource;
            propertySource = DoubleBlockProperties.PropertyRetriever::getFallback;
            float animationProgress = propertySource.apply(JewelleryBoxBlock.getAnimationProgressRetriever(entity)).get(tickDelta);
            animationProgress = 1.0f - animationProgress;
            animationProgress = 1.0f - animationProgress * animationProgress * animationProgress;
            matrices.translate(0.5f, 0.5f, 0.5f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-rotation));
            //matrices.translate(-0.5f, 0.0f, -0.5f);
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(180));
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(FULL_TEXTURE));
            this.render(matrices, vertexConsumer, this.lid, this.base, animationProgress, getLightLevel(entity.getWorld(), entity.getPos()), overlay);
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
