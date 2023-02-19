package com.deku.cherryblossomgrotto.common.entity.vehicle;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * An enum containing possible boat types associated with this mod.
 * We use a new type since the types that are possible in vanilla cannot be overridden or extended directly.
 */
public enum ModBoatTypes {
    CHERRY(ModBlocks.CHERRY_PLANKS, "cherry_blossom"),
    MAPLE(ModBlocks.MAPLE_PLANKS, "maple");

    private final String name;
    private final Block planks;

    ModBoatTypes(Block planks, String name) {
        this.name = name;
        this.planks = planks;
    }

    /**
     * Gets the name of the boat type
     *
     * @return Name of the boat type
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the planks block that corresponds to the same wood type of this boat
     *
     * @return The planks block of the same wood type as this boat
     */
    public Block getPlanks() {
        return this.planks;
    }

    /**
     * Converts this enum value to a string
     *
     * @return The name of the boat type
     */
    public String toString() {
        return this.name;
    }

    /**
     * Gets the enum value for this boat type by its ID
     *
     * @param id The ID we want the boat type for
     * @return The boat type with the given ID
     */
    public static ModBoatTypes byId(int id) {
        ModBoatTypes[] boatEntityType = values();
        if (id < 0 || id >= boatEntityType.length) {
            id = 0;
        }

        return boatEntityType[id];
    }

    /**
     * Gets the enum value for this boat type by its name
     *
     * @param name The name of the boat type we want
     * @return The boat type with the given name
     */
    public static ModBoatTypes byName(String name) {
        ModBoatTypes[] boatEntityType = values();

        for(int i = 0; i < boatEntityType.length; ++i) {
            if (boatEntityType[i].getName().equals(name)) {
                return boatEntityType[i];
            }
        }

        return boatEntityType[0];
    }
}