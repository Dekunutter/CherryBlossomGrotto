package com.deku.cherryblossomgrotto.common.world.gen.trunkPlacers;

import com.deku.cherryblossomgrotto.CherryBlossomGrotto;
import com.deku.cherryblossomgrotto.common.utils.ForgeReflection;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CherryBlossomTrunkPlaceType {
    /**
     * Builds a trunk placer type for our cherry blossom tree.
     * Since TrunkPlacerType in the source is privatized we need to use reflection.
     * TrunkPlacerType is also not yet set up to be a registry entry so we cant register these in the standard way.
     *
     *
     * @param trunkPlacerCodec The serialized codec for the associated trunk placer
     * @return The trunk placer type instantiated through reflection
     */
    public static TrunkPlacerType<CherryBlossomTrunkPlacer> createTrunkPlacerType(Codec trunkPlacerCodec) {

        try {
            Constructor<?> constructor = ForgeReflection.getFirstPrivateConstructor(TrunkPlacerType.class);
            Class<?>[] constructorParameters = constructor.getParameterTypes();
            if (constructorParameters.length == 1 && constructorParameters[0] == Codec.class) {
                return (TrunkPlacerType<CherryBlossomTrunkPlacer>) constructor.newInstance(trunkPlacerCodec);
            } else {
                CherryBlossomGrotto.LOGGER.error("Cherry Blossom Trunk Place Type instantiating with UNEXPECTED PARAMS");
                return null;
            }
        } catch (NullPointerException exception) {
            CherryBlossomGrotto.LOGGER.error("Cherry Blossom Trunk Place Type NOT FOUND");
        } catch (InstantiationException exception) {
            CherryBlossomGrotto.LOGGER.error("Cherry Blossom Trunk Place Type is instantiating an ABSTRACT CLASS");
            return null;
        } catch (IllegalAccessException exception) {
            CherryBlossomGrotto.LOGGER.error("Cherry Blossom Trunk Place Type is instantiating with INCORRECT PARAMS");
        }
        catch (InvocationTargetException exception) {
            CherryBlossomGrotto.LOGGER.error("Cherry Blossom Trunk Place Type is instantiating with an EXCEPTION");
        }
        return null;
    }
}
