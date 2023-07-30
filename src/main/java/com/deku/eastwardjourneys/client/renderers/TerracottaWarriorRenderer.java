package com.deku.eastwardjourneys.client.renderers;

import com.deku.eastwardjourneys.client.models.TerracottaWarriorModel;
import com.deku.eastwardjourneys.common.entity.monster.terracotta_warrior.TerracottaWarrior;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TerracottaWarriorRenderer extends GeoEntityRenderer<TerracottaWarrior> {
    public TerracottaWarriorRenderer(EntityRendererProvider.Context context) {
        super(context, new TerracottaWarriorModel());
    }
}
