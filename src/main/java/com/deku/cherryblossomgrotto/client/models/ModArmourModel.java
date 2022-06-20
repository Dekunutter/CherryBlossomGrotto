package com.deku.cherryblossomgrotto.client.models;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public abstract class ModArmourModel extends HumanoidModel<LivingEntity> {
    private ResourceLocation texture;

    public ModArmourModel(ModelPart root, String textureName) {
        super(root);
        texture = new ResourceLocation("cherryblossomgrotto:textures/model/" + textureName + ".png");

    }

    /**
     * Gets the texture resource location as a string
     *
     * @return String representing the location of the texture for this model
     */
    public final String getTexture() {
        return texture.toString();
    }
}
