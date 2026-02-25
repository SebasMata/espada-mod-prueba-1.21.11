package com.mataflex.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class HornedHelmetModel extends HumanoidModel<HumanoidRenderState> {
    public HornedHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createArmorLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("Horns", CubeListBuilder.create()
                        .texOffs(16, 22).addBox(-8.0F, -6.0F, -1.0F, 3.0F, 2.0F, 2.0F)
                        .texOffs(16, 22).addBox(-9.0F, -7.0F, -3.0F, 3.0F, 1.0F, 2.0F)
                        .texOffs(18, 22).addBox(-8.0F, -8.0F, -5.0F, 1.0F, 2.0F, 2.0F),
                PartPose.offset(1.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("Horns3", CubeListBuilder.create()
                        .texOffs(28, 22).addBox(5.0F, -6.0F, -1.0F, 3.0F, 2.0F, 2.0F)
                        .texOffs(28, 22).addBox(6.0F, -7.0F, -3.0F, 3.0F, 1.0F, 2.0F)
                        .texOffs(30, 22).addBox(7.0F, -8.0F, -5.0F, 1.0F, 2.0F, 2.0F),
                PartPose.offset(-1.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}