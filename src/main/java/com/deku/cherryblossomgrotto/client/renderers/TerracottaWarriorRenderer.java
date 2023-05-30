package com.deku.cherryblossomgrotto.client.renderers;

import com.deku.cherryblossomgrotto.client.models.TerracottaWarriorModel;
import com.deku.cherryblossomgrotto.common.entity.monster.terracotta_warrior.TerracottaWarrior;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class TerracottaWarriorRenderer extends GeoEntityRenderer<TerracottaWarrior> {
    public TerracottaWarriorRenderer(EntityRendererProvider.Context context) {
        super(context, new TerracottaWarriorModel());
    }
}
