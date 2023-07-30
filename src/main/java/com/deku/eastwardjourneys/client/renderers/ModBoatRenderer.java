package com.deku.eastwardjourneys.client.renderers;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatEntity;
import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.stream.Stream;

import static com.deku.eastwardjourneys.Main.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class ModBoatRenderer extends BoatRenderer {
    private final Map<ModBoatTypes, Pair<ResourceLocation, ListModel<Boat>>> modBoatResources;

    public ModBoatRenderer(EntityRendererProvider.Context renderContext, boolean isChestBoot) {
        super(renderContext, isChestBoot);

        // NOTE: Basically a copy of how we build boat resources but using our modded type enums since there's no easy way to add to the vanilla types
        //  Also note that I am using the standard oak boat vanilla model and just applying the cherry blossom wood texture over it
        //  Leaving out any logic around using the newer raft model since I have no use for it right now
        modBoatResources = Stream.of(ModBoatTypes.values()).collect(ImmutableMap.toImmutableMap((boatType) -> {
            return boatType;
        }, (boatType) -> {
            return Pair.of(
                new ResourceLocation(MOD_ID, getTextureLocation(boatType, isChestBoot)),
                createModBoatModel(renderContext, boatType, isChestBoot)
            );
        }));
    }

    public ModBoatRenderer(EntityRendererProvider.Context renderContext) {
        this(renderContext, false);
    }

    /**
     * Fetches the location for the texture of this model
     *
     * @param boatType The modded boat type needed to identify the stored texture
     * @param isChestBoat Whether the texture is for a boat or chest boat
     * @return The location of the texture for this boat
     */
    private String getTextureLocation(ModBoatTypes boatType, boolean isChestBoat) {
        return isChestBoat ? "textures/entity/chest_boat/" + boatType.getName() + ".png" : "textures/entity/boat/" + boatType.getName() + ".png";
    }

    /**
     * Creates the model for this modded boat.
     * Note: This method has been left flexible for fetching boat and chest boat models even though in this class we're just going to use it to get boat models
     * Additional Note: Though we pass in modded boat types for posterity, we're actually just using the boat model from vanilla Minecraft, so we're not gonna use this value for now
     *
     * @param renderContext The render context for this entity
     * @param modBoatTypes The modded boat type that will be used to locate the model layer (if we were loading our own model)
     * @param isChestBoat Whether the boat model we are loading is for a chest boat
     * @return List of models for this boat
     */
    private ListModel<Boat> createModBoatModel(EntityRendererProvider.Context renderContext, ModBoatTypes modBoatTypes, boolean isChestBoat) {
        Boat.Type defaultType = Boat.Type.OAK;
        ModelLayerLocation modellayerlocation = isChestBoat ? ModelLayers.createChestBoatModelName(defaultType) : ModelLayers.createBoatModelName(defaultType);
        ModelPart modelpart = renderContext.bakeLayer(modellayerlocation);
        return isChestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
    }

    /**
     * Fetches the model layer location for this boat model
     *
     * @param modBoatTypes The modded boat type that will be used to locate the model layer
     * @return The model layer location for this boat model
     */
    private ModelLayerLocation createModBoatModelName(ModBoatTypes modBoatTypes) {
        return new ModelLayerLocation(new ResourceLocation("minecraft", "boat/"  + modBoatTypes.getName()), "main");
    }

    /**
     * Gets the resources (including model) for a modded boat given its type.
     * Override of the vanilla getter so that we can utilize our modded boat types over vanilla boat types without re-coding the vanilla render logic for boats
     *
     * @param boat The boat entity we are getting rendering resources for
     * @return A pair containing the resource location for the textures of this boat and its model
     */
    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        ModBoatEntity moddedBoat = (ModBoatEntity) boat;
        return modBoatResources.get(moddedBoat.getModBoatType());
    }
}
