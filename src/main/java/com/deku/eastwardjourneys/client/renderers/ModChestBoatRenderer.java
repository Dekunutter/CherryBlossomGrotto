package com.deku.eastwardjourneys.client.renderers;

import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatTypes;
import com.deku.eastwardjourneys.common.entity.vehicle.ModChestBoatEntity;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import java.util.Map;
import java.util.stream.Stream;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModChestBoatRenderer extends BoatRenderer {
    private final Map<ModBoatTypes, Pair<ResourceLocation, ListModel<Boat>>> modChestBoatResources;

    public ModChestBoatRenderer(EntityRendererProvider.Context renderContext, boolean isChestBoot) {
        super(renderContext, isChestBoot);

        // NOTE: Basically a copy of how we build boat resources but using our modded type enums since there's no easy way to add to the vanilla types
        //  Also note that I ripped this straight from ModBoatRenderer rather than trying to build my polymorphic solution
        //  Leaving out any logic around using the newer raft model since I have no use for it right now
        modChestBoatResources = Stream.of(ModBoatTypes.values()).collect(ImmutableMap.toImmutableMap((boatType) -> {
            return boatType;
        }, (boatType) -> {
            return Pair.of(
                    new ResourceLocation(MOD_ID, getTextureLocation(boatType, isChestBoot)),
                    createModBoatModel(renderContext)
            );
        }));
    }

    public ModChestBoatRenderer(EntityRendererProvider.Context renderContext) {
        this(renderContext, true);
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
     * Creates the model for this modded chest boat.
     * Note: This method just gets the model for a chest boat from vanilla Minecraft rather than loading in a unique model
     *
     * @param renderContext The render context for this entity
     * @return List of models for this chest boat
     */
    private ListModel<Boat> createModBoatModel(EntityRendererProvider.Context renderContext) {
        Boat.Type defaultType = Boat.Type.OAK;
        ModelLayerLocation modellayerlocation = ModelLayers.createChestBoatModelName(defaultType);
        ModelPart modelpart = renderContext.bakeLayer(modellayerlocation);
        return new ChestBoatModel(modelpart);
    }

    /**
     * Gets the resources (including model) for a modded chest boat given its type.
     * Override of the vanilla getter so that we can utilize our modded boat types over vanilla boat types without re-coding the vanilla render logic for boats
     * Note: Currently in Minecraft boats and chest boats are separate entities that are non-inheritable. This function is what stopped me from making one flexible renderer,
     * it was easier and faster to just duplicate things for now.
     *
     * @param boat The boat entity we are getting rendering resources for
     * @return A pair containing the resource location for the textures of this chest boat and its model
     */
    @Override
    public Pair<ResourceLocation,  ListModel<Boat>> getModelWithLocation(Boat boat) {
        ModChestBoatEntity moddedBoat = (ModChestBoatEntity) boat;
        return modChestBoatResources.get(moddedBoat.getModChestBoatType());
    }
}
