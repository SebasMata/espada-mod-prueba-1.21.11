package com.mataflex.entity;

import com.mataflex.ValhallaEchoes;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.resources.Identifier;

public class MysticalVikingRenderer extends PiglinRenderer {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(
                    ValhallaEchoes.MOD_ID,
                    "textures/entity/mystical_viking.png"
            );

    public MysticalVikingRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                ModelLayers.PIGLIN,
                ModelLayers.PIGLIN_BABY,
                ModelLayers.PIGLIN_ARMOR,
                ModelLayers.PIGLIN_BABY_ARMOR
        );
    }

    @Override
    public Identifier getTextureLocation(PiglinRenderState renderState) {
        return TEXTURE;
    }}