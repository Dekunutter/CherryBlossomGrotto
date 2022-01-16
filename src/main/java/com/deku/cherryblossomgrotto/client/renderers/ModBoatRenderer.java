package com.deku.cherryblossomgrotto.client.renderers;

import com.deku.cherryblossomgrotto.common.entity.item.ModBoatEntity;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class ModBoatRenderer extends BoatRenderer {
    private static final ResourceLocation[] BOAT_TEXTURES = new ResourceLocation[] {
            new ResourceLocation(MOD_ID,"textures/entity/boat/cherry_blossom.png")
    };
    protected final BoatModel model = new BoatModel();

    public ModBoatRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    /**
     * Ensures that if the boat is an instance of one of our mod boats that we load our custom texture over the standard
     * set used by the vanilla file base and fetch it from the boat entity subdirectory of textures.
     *
     * @param entity The entity we are trying to texture
     * @return The resource location of the texture we want to use
     */
    @Override
    public ResourceLocation getTextureLocation(BoatEntity entity) {
        if (entity instanceof ModBoatEntity) {
            return BOAT_TEXTURES[((ModBoatEntity) entity).getModBoatType().ordinal()];
        } else {
            return super.getTextureLocation(entity);
        }
    }
}
