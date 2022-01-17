package com.deku.cherryblossomgrotto.client.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class KabutoArmourModel extends BipedModel<LivingEntity> {
	private ModelRenderer helmet;
	private ModelRenderer cuirass;
	private ModelRenderer rightArmSleeve;
	private ModelRenderer leftArmSleeve;
	private ModelRenderer rightGreave;
	private ModelRenderer leftGreave;
	private ModelRenderer rightSandal;
	private ModelRenderer leftSandal;

	private String texture;

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
	public KabutoArmourModel() {
		super(1.0f, 0, 128, 128);

		texture = new ResourceLocation("cherryblossomgrotto:textures/model/kabuto_armour.png").toString();

		helmet = new ModelRenderer(this);
		helmet.setPos(0, 0, 0);
		head.addChild(helmet);
		setRotationAngle(helmet, 0.0F, 0.0F, 0.0F);
		helmet.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
		helmet.texOffs(13, 64).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
		helmet.texOffs(44, 71).addBox(4.0F, -8.0F, -5.0F, 1.0F, 5.0F, 9.0F, 0.0F, false);
		helmet.texOffs(85, 60).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 5.0F, 9.0F, 0.0F, false);
		helmet.texOffs(43, 64).addBox(-5.0F, -8.0F, 4.0F, 10.0F, 6.0F, 1.0F, 0.0F, false);
		helmet.texOffs(7, 70).addBox(-1.0F, -7.0F, -6.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		helmet.texOffs(0, 64).addBox(-2.0F, -9.0F, -6.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		helmet.texOffs(0, 68).addBox(-3.0F, -11.0F, -6.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		helmet.texOffs(0, 73).addBox(-4.0F, -11.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		helmet.texOffs(3, 73).addBox(3.0F, -11.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		helmet.texOffs(3, 68).addBox(2.0F, -11.0F, -6.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		helmet.texOffs(17, 64).addBox(-6.0F, -7.0F, -6.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		helmet.texOffs(11, 64).addBox(4.0F, -7.0F, -6.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		helmet.texOffs(17, 67).addBox(-5.0F, -5.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		helmet.texOffs(11, 67).addBox(4.0F, -5.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		cuirass = new ModelRenderer(this);
		cuirass.setPos( 0, 0, 0);
		body.addChild(cuirass);
		setRotationAngle(cuirass, 0.0F, 0.0F, 0.0F);
		cuirass.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
		cuirass.texOffs(68, 65).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 9.0F, 1.0F, 0.0F, false);
		cuirass.texOffs(77, 75).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 12.0F, 1.0F, 0.0F, false);

		rightArmSleeve = new ModelRenderer(this);
		rightArm.addChild(rightArmSleeve);
		setRotationAngle(rightArmSleeve, 0.0F, 0.0F, 0.0F);
		rightArmSleeve.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
		rightArmSleeve.texOffs(0, 76).addBox(-4.0F, -3.0F, -3.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);
		rightArmSleeve.texOffs(16, 77).addBox(-4.0F, -2.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
		rightArmSleeve.texOffs(2, 83).addBox(-4.0F, 7.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
		rightArmSleeve.texOffs(30, 77).addBox(-4.0F, 3.0F, -3.0F, 1.0F, 2.0F, 6.0F, 0.0F, false);
		rightArmSleeve.texOffs(0, 93).addBox(-3.0F, 10.0F, -3.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);
		rightArmSleeve.texOffs(16, 87).addBox(-3.0F, 7.0F, 2.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		rightArmSleeve.texOffs(24, 77).addBox(-3.0F, 3.0F, 2.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		rightArmSleeve.texOffs(0, 83).addBox(-3.0F, 7.0F, -3.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		rightArmSleeve.texOffs(24, 80).addBox(-3.0F, 3.0F, -3.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);

		leftArmSleeve = new ModelRenderer(this);
		leftArm.addChild(leftArmSleeve);
		setRotationAngle(leftArmSleeve, 0.0F, 0.0F, 0.0F);
		leftArmSleeve.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
		leftArmSleeve.texOffs(18, 87).addBox(-1.0F, -3.0F, -3.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);
		leftArmSleeve.texOffs(34, 88).addBox(3.0F, -2.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
		leftArmSleeve.texOffs(58, 90).addBox(3.0F, 7.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
		leftArmSleeve.texOffs(48, 88).addBox(3.0F, 3.0F, -3.0F, 1.0F, 2.0F, 6.0F, 0.0F, false);
		leftArmSleeve.texOffs(66, 89).addBox(0.0F, 10.0F, -3.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);
		leftArmSleeve.texOffs(56, 90).addBox(0.0F, 7.0F, -3.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		leftArmSleeve.texOffs(42, 91).addBox(0.0F, 3.0F, -3.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
		leftArmSleeve.texOffs(56, 86).addBox(0.0F, 7.0F, 2.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		leftArmSleeve.texOffs(42, 88).addBox(0.0F, 3.0F, 2.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);

		rightGreave = new ModelRenderer(this);
		rightLeg.addChild(rightGreave);
		setRotationAngle(rightGreave, 0.0F, 0.0F, 0.0F);
		rightGreave.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
		rightGreave.texOffs(18, 94).addBox(-3.0F, 0.0F, -3.0F, 5.0F, 4.0F, 1.0F, 0.0F, false);
		rightGreave.texOffs(0, 100).addBox(-3.0F, 5.0F, -3.0F, 5.0F, 4.0F, 1.0F, 0.0F, false);
		rightGreave.texOffs(25, 95).addBox(-3.0F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
		rightGreave.texOffs(19, 99).addBox(-2.0F, 0.0F, 2.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
		rightGreave.texOffs(0, 105).addBox(-2.0F, 5.0F, 2.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
		rightGreave.texOffs(7, 100).addBox(-3.0F, 5.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);

		leftGreave = new ModelRenderer(this);
		leftLeg.addChild(leftGreave);
		setRotationAngle(leftGreave, 0.0F, 0.0F, 0.0F);
		leftGreave.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
		leftGreave.texOffs(37, 99).addBox(-2.0F, 0.0F, -3.0F, 5.0F, 4.0F, 1.0F, 0.0F, false);
		leftGreave.texOffs(32, 104).addBox(-2.0F, 5.0F, -3.0F, 5.0F, 4.0F, 1.0F, 0.0F, false);
		leftGreave.texOffs(44, 99).addBox(2.0F, 0.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);
		leftGreave.texOffs(51, 99).addBox(0.0F, 0.0F, 2.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
		leftGreave.texOffs(37, 109).addBox(0.0F, 5.0F, 2.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
		leftGreave.texOffs(25, 104).addBox(2.0F, 5.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, false);

		rightSandal = new ModelRenderer(this);
		rightLeg.addChild(rightSandal);
		setRotationAngle(rightSandal, 0.0F, 0.0F, 0.0F);
		rightSandal.texOffs(43, 108).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);

		leftSandal = new ModelRenderer(this);
		leftLeg.addChild(leftSandal);
		setRotationAngle(leftSandal, 0.0F, 0.0F, 0.0F);
		leftSandal.texOffs(61, 108).addBox(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);
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
	public final KabutoArmourModel applyEntityStats(BipedModel defaultArmor){
		this.crouching = defaultArmor.crouching;
		this.rightArmPose = defaultArmor.rightArmPose;
		this.leftArmPose = defaultArmor.leftArmPose;

		return this;
	}

	/**
	 * Applies some changes to the entity based on what equipment slot this model is equipped to.
	 * Controls whether individual pieces of the model should be displayed or not by their renderer.
	 *
	 * @param slot The equipment slot that this piece of armour is being applied to.
 	 * @return This armour model
	 */
	public BipedModel applySlot(EquipmentSlotType slot){
		helmet.visible = false;
		cuirass.visible = false;
		rightArmSleeve.visible = false;
		leftArmSleeve.visible = false;
		rightGreave.visible = false;
		leftGreave.visible = false;
		rightSandal.visible = false;
		leftSandal.visible = false;

		switch(slot){
			case HEAD:
				helmet.visible = true;
				break;
			case CHEST:
				cuirass.visible = true;
				rightArmSleeve.visible = true;
				leftArmSleeve.visible = true;
				break;
			case LEGS:
				rightGreave.visible = true;
				leftGreave.visible = true;
				break;
			case FEET:
				rightSandal.visible = true;
				leftSandal.visible = true;
				break;
			default:
				break;
		}

		return this;
	}

	/**
	 * Sets the rotation of the given model renderer
	 *
	 * @param modelRenderer The renderer for the model we want to render
	 * @param x Rotation on the X axis
	 * @param y Rotation on the Y axis
	 * @param z Rotation on the Z axis
	 */
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}