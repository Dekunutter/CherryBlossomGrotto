package com.deku.cherryblossomgrotto.client.models;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class NinjaRobesModel extends HumanoidModel<LivingEntity> {
	public ResourceLocation texture;

	public NinjaRobesModel(ModelPart root) {
		super(root);
		texture = new ResourceLocation("cherryblossomgrotto:textures/model/ninja_suit.png");
	}

	/**
	 * NOTE TO GENERATE ARMOUR MODELS:
	 * - Use BlockBench
	 * - Open up a new project of the Minecraft Skin type
	 * - Convert project to a Modded Entity from the File menu
	 * - In the Project settings of the File menu (File > Project) change the export version to the correct
	 *   version of Forge (1.16 in this case with Mojmap mappings) and update the texture size to match the
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

		PartDefinition helmet = part.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(1, 92).addBox(-4.0F, -9.0F, -5.0F, 8.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(13, 110).addBox(4.0F, -8.0F, -5.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(1, 102).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(25, 115).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(25, 111).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(25, 102).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		PartDefinition hat = part.addOrReplaceChild("hat", CubeListBuilder.create()
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 0.0F, 0.0F));


		PartDefinition tunic = part.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(75, 107).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(57, 107).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(71, 100).addBox(-4.0F, -1.0F, -3.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(65, 121).addBox(-9.0F, -1.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(93, 102).addBox(-5.0F, 0.0F, -3.0F, 1.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(43, 102).addBox(4.0F, 0.0F, -3.0F, 1.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(51, 99).addBox(4.0F, -1.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		PartDefinition rightArmSleeve = part.addOrReplaceChild("right_arm", CubeListBuilder.create()
				.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(16, 82).addBox(-4.0F, -2.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(1, 78).addBox(-3.0F, -2.0F, 2.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(1, 78).addBox(-3.0F, -2.0F, -3.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(-5.0F, 2.0F, 0.0F)
		);

		PartDefinition leftArmSleeve = part.addOrReplaceChild("left_arm", CubeListBuilder.create()
				.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(11, 73).addBox(3.0F, -2.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 87).addBox(-1.0F, -2.0F, -3.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(1, 82).addBox(-1.0F, -2.0F, 2.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(5.0F, 2.0F, 0.0F)
		);

		PartDefinition rightLegging = part.addOrReplaceChild("right_leg", CubeListBuilder.create()
				.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false),
			PartPose.offset(-1.9F, 12.0F, 0.0F)
		);

		PartDefinition leftLegging = part.addOrReplaceChild("left_leg", CubeListBuilder.create()
						.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false),
				PartPose.offset(1.9F, 12.0F, 0.0F)
		);

		PartDefinition rightBoot = part.addOrReplaceChild("rightBoot", CubeListBuilder.create()
				.texOffs(30, 84).addBox(-3.0F, 11.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(-1.9F, 12.0F, 0.0F)
		);

		PartDefinition leftBoot = part.addOrReplaceChild("leftBoot", CubeListBuilder.create()
				.texOffs(54, 84).addBox(-3.0F, 11.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(1.9F, 12.0F, 0.0F)
		);

		return LayerDefinition.create(mesh, 128, 128);
	}

	/**
	 * Getter for the resource location of the texture for this model
	 *
	 * @return The resource location of the texture of this model converted to a string
	 */
	public final String getTexture() {
		return texture.toString();
	}

	/**
	 * Applies some basic stats to the entity on top of their default armor
	 * Used in place of copyPropertiesTo like vanilla does (trying to avoid getting stuck in generics hell for now)
	 *
	 * @param defaultArmor Default armour model of the entity
	 * @return This armour model
	 */
	public final NinjaRobesModel applyEntityStats(HumanoidModel<?> defaultArmor){
		this.attackTime = defaultArmor.attackTime;
		this.riding = defaultArmor.riding;
		this.young = defaultArmor.young;
		this.leftArmPose = defaultArmor.leftArmPose;
		this.rightArmPose = defaultArmor.rightArmPose;
		this.crouching = defaultArmor.crouching;
		this.head.copyFrom(defaultArmor.head);
		this.body.copyFrom(defaultArmor.body);
		this.rightArm.copyFrom(defaultArmor.rightArm);
		this.leftArm.copyFrom(defaultArmor.leftArm);
		this.rightLeg.copyFrom(defaultArmor.rightLeg);
		this.leftLeg.copyFrom(defaultArmor.leftLeg);

		return this;
	}
}