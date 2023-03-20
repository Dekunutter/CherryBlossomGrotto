package com.deku.cherryblossomgrotto.client.renderers.layers;

import com.deku.cherryblossomgrotto.client.models.InnerKabutoArmourModel;
import com.deku.cherryblossomgrotto.client.models.KabutoArmourModel;
import com.deku.cherryblossomgrotto.client.models.geom.ModModelLayerLocations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KabutoArmourLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    public static KabutoArmourModel MODEL;
    public static InnerKabutoArmourModel INNER_MODEL;

    public KabutoArmourLayer(RenderLayerParent<T, M> armourRenderer, EntityModelSet modelSet) {
        super(armourRenderer, (A) new InnerKabutoArmourModel(modelSet.bakeLayer(ModModelLayerLocations.INNER_KABUTO_ARMOUR)), (A) new KabutoArmourModel(modelSet.bakeLayer(ModModelLayerLocations.KABUTO_ARMOUR)), Minecraft.getInstance().getModelManager());

        INNER_MODEL = (InnerKabutoArmourModel) this.innerModel;
        MODEL = (KabutoArmourModel) this.outerModel;
    }
}
