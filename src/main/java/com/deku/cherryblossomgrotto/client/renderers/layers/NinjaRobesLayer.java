package com.deku.cherryblossomgrotto.client.renderers.layers;

import com.deku.cherryblossomgrotto.client.models.InnerNinjaRobesModel;
import com.deku.cherryblossomgrotto.client.models.NinjaRobesModel;
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
public class NinjaRobesLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    public static NinjaRobesModel MODEL;
    public static InnerNinjaRobesModel INNER_MODEL;

    public NinjaRobesLayer(RenderLayerParent<T, M> armourRenderer, EntityModelSet modelSet) {
        super(armourRenderer, (A) new InnerNinjaRobesModel(modelSet.bakeLayer(ModModelLayerLocations.INNER_NINJA_ROBES)), (A) new NinjaRobesModel(modelSet.bakeLayer(ModModelLayerLocations.NINJA_ROBES)), Minecraft.getInstance().getModelManager());

        INNER_MODEL = (InnerNinjaRobesModel) this.innerModel;
        MODEL = (NinjaRobesModel) this.outerModel;
    }
}
