package com.mataflex.entity;

import com.mataflex.EspadaModPrueba;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.resources.Identifier;

public class MysticalVikingRenderer extends PiglinRenderer {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(
                    EspadaModPrueba.MOD_ID,
                    "textures/entity/mystical_viking.png"
            );

    public MysticalVikingRenderer(EntityRendererProvider.Context ctx) {
        super(
                ctx,
                ModelLayers.ZOMBIFIED_PIGLIN,           // ModelLayer para piglin zombie
                ModelLayers.ZOMBIFIED_PIGLIN_BABY, // Capa interior de armadura
                ModelLayers.ZOMBIFIED_PIGLIN_BABY_ARMOR, // Capa exterior de armadura
                ModelLayers.ZOMBIFIED_PIGLIN_ARMOR
        );
    }

    @Override
    public Identifier getTextureLocation(PiglinRenderState renderState) {
        return TEXTURE;
    }
}