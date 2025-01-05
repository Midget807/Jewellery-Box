package net.midget807.jewellery_box.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.midget807.jewellery_box.block.ModBlocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.FULL_JEWELLERY_BOX.asItem(), 2)
                .pattern("SGS")
                .pattern("SWS")
                .pattern("SSS")
                .input('S', Items.DARK_OAK_SLAB)
                .input('G', Items.GLASS)
                .input('W', Items.BLACK_WOOL)
                .criterion(hasItem(Items.DARK_OAK_SLAB), conditionsFromItem(Items.DARK_OAK_SLAB))
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .criterion(hasItem(Items.BLACK_WOOL), conditionsFromItem(Items.BLACK_WOOL))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FULL_JEWELLERY_BOX)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.FULL_JEWELLERY_BOX.asItem(), 1)
                .input(ModBlocks.HALF_JEWELLERY_BOX.asItem(), 2)
                .criterion(hasItem(ModBlocks.HALF_JEWELLERY_BOX.asItem()), conditionsFromItem(ModBlocks.HALF_JEWELLERY_BOX.asItem()))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.FULL_JEWELLERY_BOX) + "_compacting"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.HALF_JEWELLERY_BOX.asItem(), 2)
                .input(ModBlocks.FULL_JEWELLERY_BOX.asItem(), 1)
                .criterion(hasItem(ModBlocks.FULL_JEWELLERY_BOX.asItem()), conditionsFromItem(ModBlocks.FULL_JEWELLERY_BOX.asItem()))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.HALF_JEWELLERY_BOX)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.HALF_JEWELLERY_BOX.asItem(), 1)
                .input(ModBlocks.QUARTER_JEWELLERY_BOX.asItem(), 2)
                .criterion(hasItem(ModBlocks.QUARTER_JEWELLERY_BOX.asItem()), conditionsFromItem(ModBlocks.QUARTER_JEWELLERY_BOX.asItem()))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.HALF_JEWELLERY_BOX) + "_compacting"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.QUARTER_JEWELLERY_BOX.asItem(), 2)
                .input(ModBlocks.HALF_JEWELLERY_BOX.asItem(), 1)
                .criterion(hasItem(ModBlocks.HALF_JEWELLERY_BOX.asItem()), conditionsFromItem(ModBlocks.HALF_JEWELLERY_BOX.asItem()))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.QUARTER_JEWELLERY_BOX)));
    }
}
