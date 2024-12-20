package net.midget807.jewellery_box.mixin.client;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.sugar.Local;
import net.midget807.jewellery_box.client.entity.ModEntityModelLayers;
import net.midget807.jewellery_box.client.renderer.FullJewelleryBoxEntityRenderer;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityModels.class)
public class EntityModelsMixin {
    @Inject(method = "getModels", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/model/EntityModelLayers;ALLAY:Lnet/minecraft/client/render/entity/model/EntityModelLayer;"))
    private static void jb$addModel(CallbackInfoReturnable<Map<EntityModelLayer, TexturedModelData>> cir, @Local ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builderLocalRef) {
        builderLocalRef.put(ModEntityModelLayers.JEWELLERY_BOX, FullJewelleryBoxEntityRenderer.getTextureModelData());

    }
}
