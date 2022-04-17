package com.deku.cherryblossomgrotto.client.models;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class KabutoArmourModel extends HumanoidModel<LivingEntity> {
	private ModelPart helmet;
	private ModelPart hat;
	private ModelPart cuirass;
	private ModelPart rightArmSleeve;
	private ModelPart leftArmSleeve;
	private ModelPart rightGreave;
	private ModelPart leftGreave;
	private ModelPart rightSandal;
	private ModelPart leftSandal;

	private String texture;

	public KabutoArmourModel(ModelPart root) {
		super(root);
		texture = new ResourceLocation("cherryblossomgrotto:textures/model/kabuto_armour.png").toString();

		helmet = root.getChild("head");
		hat = root.getChild("hat");
		cuirass = root.getChild("body");
		rightArmSleeve = root.getChild("right_arm");
		leftArmSleeve = root.getChild("left_arm");
		rightGreave = root.getChild("right_leg");
		leftGreave = root.getChild("left_leg");
		rightSandal = root.getChild("right_foot");
		leftSandal = root.getChild("left_foot");
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
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(13, 64).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 71).addBox(4.0F, -8.0F, -5.0F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(85, 60).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(43, 64).addBox(-5.0F, -8.0F, 4.0F, 10.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(7, 70).addBox(-1.0F, -7.0F, -6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 64).addBox(-2.0F, -9.0F, -6.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 68).addBox(-3.0F, -11.0F, -6.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 73).addBox(-4.0F, -11.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(3, 73).addBox(3.0F, -11.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(3, 68).addBox(2.0F, -11.0F, -6.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(17, 64).addBox(-6.0F, -7.0F, -6.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(11, 64).addBox(4.0F, -7.0F, -6.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(17, 67).addBox(-5.0F, -5.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(11, 67).addBox(4.0F, -5.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		PartDefinition hat = part.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cuirass = part.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(68, 65).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(77, 75).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		PartDefinition rightArmSleeve = part.addOrReplaceChild("right_arm", CubeListBuilder.create()
				.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(0, 76).addBox(-4.0F, -3.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(16, 77).addBox(-4.0F, -2.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(2, 83).addBox(-4.0F, 7.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 77).addBox(-4.0F, 3.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 93).addBox(-3.0F, 10.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(16, 87).addBox(-3.0F, 7.0F, 2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 77).addBox(-3.0F, 3.0F, 2.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 83).addBox(-3.0F, 7.0F, -3.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 80).addBox(-3.0F, 3.0F, -3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		PartDefinition leftArmSleeve = part.addOrReplaceChild("left_arm", CubeListBuilder.create()
				.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(18, 87).addBox(-1.0F, -3.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(34, 88).addBox(3.0F, -2.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(58, 90).addBox(3.0F, 7.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(48, 88).addBox(3.0F, 3.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(66, 89).addBox(0.0F, 10.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 90).addBox(0.0F, 7.0F, -3.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(42, 91).addBox(0.0F, 3.0F, -3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 86).addBox(0.0F, 7.0F, 2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(42, 88).addBox(0.0F, 3.0F, 2.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);

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

		PartDefinition rightSandal = part.addOrReplaceChild("right_foot", CubeListBuilder.create()
				.texOffs(43, 108).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		PartDefinition leftSandal = part.addOrReplaceChild("left_foot", CubeListBuilder.create()
				.texOffs(61, 108).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		return LayerDefinition.create(mesh, 64, 32);
	}

	/**
	 * Getter for the resource location of the texture for this model
	 *
	 * @return The resource location of the texture of this model
	 */
	public final String getTexture() {
		return texture;
	}

	/**
	 * Applies some basic stats to the entity on top of their default armor
	 *
	 * @param defaultArmor Default armour model of the entity
	 * @return This armour model
	 */
	public final KabutoArmourModel applyEntityStats(HumanoidModel<?> defaultArmor){
		this.crouching = defaultArmor.crouching;
		this.rightArmPose = defaultArmor.rightArmPose;
		this.leftArmPose = defaultArmor.leftArmPose;

		return this;
	}

	/**
	 * Sets the rotation of the given model renderer
	 *
	 * @param part The part for the model we want to render
	 * @param x Rotation on the X axis
	 * @param y Rotation on the Y axis
	 * @param z Rotation on the Z axis
	 */
	public void setRotationAngle(ModelPart part, float x, float y, float z) {
		part.xRot = x;
		part.yRot = y;
		part.zRot = z;
	}
}