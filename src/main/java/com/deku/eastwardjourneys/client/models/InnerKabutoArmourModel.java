package com.deku.eastwardjourneys.client.models;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class InnerKabutoArmourModel extends ModArmourModel  {
    public ResourceLocation texture;

    public InnerKabutoArmourModel(ModelPart root) {
        super(root, "inner_kabuto_armour");
    }

    /**
     * NOTE TO GENERATE ARMOUR MODELS:
     * - Use BlockBench
     * - Open up a new project of the Minecraft Skin type
     * - Convert project to a Modded Entity from the File menu
     * - In the Project settings of the File menu (File > Project) change the export version to the correct
     *   version of Forge (1.18 in this case with Mojmap mappings) and update the texture size to match the
     *   size of the texture for this model (128 x 128 in this case)
     * - Add blocks and model the armour piece as intended. Make sure blocks are nested under their desired
     *   bone. (aka: a helmet needs to be nested under the head bone so that it has the same pivot point)
     * - Texture your model with the paint tools. Make sure that the texture is renderered outside of the original
     *   64 x 64 texture that would render most armours so that it does not interfere with any default texture
     *   co-ordinates or layers. This will stop the biped model from rendering models incorrectly when equipped.
     *   (I find its best to increase the texture size to 128 x 128 and work in the lower half, but you could
     *   possibly work from the right side, outside of 64 x 32 of the original texture, too).
     * - Remove all blocks from the original skin that you dont want to render so that just your model remains
     * - Save your model as a bbmodel file somewhere
     * - Export your model as a Java file. Rip out the texOffs and box co-ords into your renderer, adding them
     *   as a child to the default modelling parts of the biped model.
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        PartDefinition head = part.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat = part.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cuirass = part.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightArmSleeve = part.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftArmSleeve = part.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightGreave = part.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                        .texOffs(18, 94).addBox(-3.0F, 0.0F, -3.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(0, 100).addBox(-3.0F, 5.0F, -3.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(25, 95).addBox(-3.0F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(19, 99).addBox(-2.0F, 0.0F, 2.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(0, 105).addBox(-2.0F, 5.0F, 2.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(7, 100).addBox(-3.0F, 5.0F, -2.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        PartDefinition leftGreave = part.addOrReplaceChild("left_leg", CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                        .texOffs(37, 99).addBox(-2.0F, 0.0F, -3.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(32, 104).addBox(-2.0F, 5.0F, -3.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(44, 99).addBox(2.0F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(51, 99).addBox(0.0F, 0.0F, 2.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(37, 109).addBox(0.0F, 5.0F, 2.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(25, 104).addBox(2.0F, 5.0F, -2.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(mesh, 128, 128);
    }
}
