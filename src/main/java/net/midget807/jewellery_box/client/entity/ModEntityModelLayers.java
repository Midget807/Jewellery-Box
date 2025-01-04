package net.midget807.jewellery_box.client.entity;

import com.google.common.collect.Sets;
import net.midget807.jewellery_box.JewelleryBoxMain;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import java.util.Set;

public class ModEntityModelLayers {
    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();
    public static final EntityModelLayer FULL_JEWELLERY_BOX = registerMain("full_jewellery_box");
    public static final EntityModelLayer HALF_JEWELLERY_BOX = registerMain("half_jewellery_box");
    public static final EntityModelLayer QUARTER_JEWELLERY_BOX = registerMain("quarter_jewellery_box");

    public static EntityModelLayer registerMain(String name) {
        EntityModelLayer entityModelLayer = new EntityModelLayer(JewelleryBoxMain.id(name), "main");
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        } else {
            return entityModelLayer;
        }
    }
}
