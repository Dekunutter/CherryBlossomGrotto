package com.deku.cherryblossomgrotto.client.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class NinjaSuitModel extends BipedModel<LivingEntity> {
	private ModelRenderer helmet;
	private ModelRenderer tunic;
	private ModelRenderer rightArmSleeve;
	private ModelRenderer leftArmSleeve;
	private ModelRenderer rightLegging;
	private ModelRenderer leftLegging;
	private ModelRenderer rightBoot;
	private ModelRenderer leftBoot;

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
	public NinjaSuitModel() {
		super(1.0f, 0, 128, 128);

		texture = new ResourceLocation("cherryblossomgrotto:textures/item/ninja_suit.png").toString();

		helmet = new ModelRenderer(this);
		helmet.setPos(0, 0, 0);
		head.addChild(helmet);
		setRotationAngle(helmet, 0.0F, 0.0F, 0.0F);

		helmet.texOffs(1, 92).addBox(-4.0F, -9.0F, -5.0F, 8.0F, 1.0F, 9.0F, 0.0F, false);
		helmet.texOffs(13, 110).addBox(4.0F, -8.0F, -5.0F, 1.0F, 8.0F, 10.0F, 0.0F, false);
		helmet.texOffs(1, 102).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 8.0F, 10.0F, 0.0F, false);
		helmet.texOffs(25, 115).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
		helmet.texOffs(25, 111).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);
		helmet.texOffs(25, 102).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 8.0F, 1.0F, 0.0F, false);

		tunic = new ModelRenderer(this);
		tunic.setPos( 0, 0, 0);
		body.addChild(tunic);
		setRotationAngle(tunic, 0.0F, 0.0F, 0.0F);

		tunic.texOffs(75, 107).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 13.0F, 1.0F, 0.0F, false);
		tunic.texOffs(57, 107).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 13.0F, 1.0F, 0.0F, false);
		tunic.texOffs(71, 100).addBox(-4.0F, -1.0F, -3.0F, 8.0F, 1.0F, 6.0F, 0.0F, false);
		tunic.texOffs(65, 121).addBox(-9.0F, -1.0F, -3.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);
		tunic.texOffs(93, 102).addBox(-5.0F, 0.0F, -3.0F, 1.0F, 13.0F, 6.0F, 0.0F, false);
		tunic.texOffs(43, 102).addBox(4.0F, 0.0F, -3.0F, 1.0F, 13.0F, 6.0F, 0.0F, false);
		tunic.texOffs(51, 99).addBox(4.0F, -1.0F, -3.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);

		rightArmSleeve = new ModelRenderer(this);
		rightArm.addChild(rightArmSleeve);
		setRotationAngle(rightArmSleeve, 0.0F, 0.0F, 0.0F);
		rightArmSleeve.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
		rightArmSleeve.texOffs(16, 82).addBox(-4.0F, -2.0F, -3.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
		rightArmSleeve.texOffs(1, 78).addBox(-3.0F, -2.0F, 2.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
		rightArmSleeve.texOffs(1, 78).addBox(-3.0F, -2.0F, -3.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);

		leftArmSleeve = new ModelRenderer(this);
		leftArm.addChild(leftArmSleeve);
		setRotationAngle(leftArmSleeve, 0.0F, 0.0F, 0.0F);
		leftArmSleeve.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
		leftArmSleeve.texOffs(11, 73).addBox(3.0F, -2.0F, -3.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
		leftArmSleeve.texOffs(0, 87).addBox(-1.0F, -2.0F, -3.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
		leftArmSleeve.texOffs(1, 82).addBox(-1.0F, -2.0F, 2.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);

		rightLegging = new ModelRenderer(this);
		rightLeg.addChild(rightLegging);
		setRotationAngle(rightLegging, 0.0F, 0.0F, 0.0F);
		rightLegging.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

		leftLegging = new ModelRenderer(this);
		leftLeg.addChild(leftLegging);
		setRotationAngle(leftLegging, 0.0F, 0.0F, 0.0F);
		leftLegging.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

		rightBoot = new ModelRenderer(this);
		rightLeg.addChild(rightBoot);
		setRotationAngle(rightBoot, 0.0F, 0.0F, 0.0F);
		rightBoot.texOffs(30, 84).addBox(-3.0F, 11.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);

		leftBoot = new ModelRenderer(this);
		leftLeg.addChild(leftBoot);
		setRotationAngle(leftBoot, 0.0F, 0.0F, 0.0F);
		leftBoot.texOffs(54, 84).addBox(-3.0F, 11.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
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
	public final NinjaSuitModel applyEntityStats(BipedModel defaultArmor){
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
		tunic.visible = false;
		rightArmSleeve.visible = false;
		leftArmSleeve.visible = false;
		rightLegging.visible = false;
		leftLegging.visible = false;
		rightBoot.visible = false;
		leftBoot.visible = false;

		switch(slot){
			case HEAD:
				helmet.visible = true;
				break;
			case CHEST:
				tunic.visible = true;
				rightArmSleeve.visible = true;
				leftArmSleeve.visible = true;
				break;
			case LEGS:
				rightLegging.visible = true;
				leftLegging.visible = true;
				break;
			case FEET:
				rightBoot.visible = true;
				leftBoot.visible = true;
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