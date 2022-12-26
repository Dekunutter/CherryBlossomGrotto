package com.deku.cherryblossomgrotto.common.world.gen.biomes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
    private static final SurfaceRules.RuleSource POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);

    /**
     * Makes a state rule based off of a given block
     *
     * @param block The block whose default state we want to turn into a surface rule
     * @return Rule for spreading a given block across the surface
     */
    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    /**
     * Builds the surface rules that this mod adds to the game.
     * Currently builds surface rules for:
     * - Cherry Blossom Slopes - to ensure dirt and snow layering similar to a vanilla grove biome
     *
     * @return The surface rules to be added to the game
     */
    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomeInitializer.CHERRY_BLOSSOM_SLOPES),
                cherryBlossomSlopesSurfaceRules()
            )
        );
    }

    /**
     * Condition to check if the surface area is above water level
     *
     * @return Returns true if the surface area is above water level
     */
    private static SurfaceRules.ConditionSource isAboveWaterLevel() {
        return SurfaceRules.waterBlockCheck(0, 0);
    }

    /**
     * Condition to check if the surface area is at or above water level
     *
     * @return Returns true if the surface area is at or above water level
     */
    private static SurfaceRules.ConditionSource isAtOrAboveWaterLevel() {
        return SurfaceRules.waterBlockCheck(-1, 0);
    }

    /**
     * Condition to check if the surface area is slightly below water level
     * Slightly below water level being up to 6 blocks below.
     *
     * @return Returns true if the surface area is around 6 blocks below water level
     */
    private static SurfaceRules.ConditionSource isSlightlyBelowWaterLevel() {
        return SurfaceRules.waterStartCheck(-6, -1);
    }

    /**
     * Checks if the surface is covered in powdered snow.
     * Uses some level to noise to randomize the change and only makes the change above water level
     *
     * @return A surface rule that will randomly mark some powdered snow for change
     */
    private static SurfaceRules.RuleSource surfacePowderSnowCheck() {
        return SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.45D, 0.58D),
            SurfaceRules.ifTrue(
                isAboveWaterLevel(),
                POWDER_SNOW
            )
        );
    }

    /**
     * Checks if the surface is covered in powdered snow.
     * Uses some level of noise to randomize the change and only makes the change above water level
     *
     * @return A surface rule that will randomly mark some powdered snow for change
     */
    private static SurfaceRules.RuleSource surfaceDeepPowderSnowCheck() {
        return SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35D, 0.6D),
            SurfaceRules.ifTrue(
                isAboveWaterLevel(),
                POWDER_SNOW
            )
        );
    }

    /**
     * Using the powdered snow conditional, convert marked snow blocks to powdered snow and layer the rest as normal snow blocks
     *
     * @return The rule for layering snow but converting some to powdered snow
     */
    private static SurfaceRules.RuleSource surfaceSnowWithPowderedSnow() {
        return SurfaceRules.sequence(
            surfacePowderSnowCheck(),
            SurfaceRules.ifTrue(
                isAboveWaterLevel(),
                SNOW_BLOCK
            )
        );
    }

    /**
     * Using the powdered snow condition, convert marked blocks to powdered snow and layer the rest as dirt
     *
     * @return The rule for layering dirt but converting some to powdered snow
     */
    private static SurfaceRules.RuleSource surfaceDirtWithPowderedSnow() {
        return SurfaceRules.sequence(
            surfaceDeepPowderSnowCheck(),
            DIRT
        );
    }

    /**
     * Builds the surface rules for the cherry blossom slopes biome
     * These rules do the following:
     * - On the surface, one layer of snow peppered with powdered snow
     * - Below the surface (just a block or two deep) layer dirt peppered with powdered snow
     *
     * @return The surface rules for the cherry blossom slopes biome
     */
    private static SurfaceRules.RuleSource cherryBlossomSlopesSurfaceRules() {
        // Surface rules for spawning powdered snow, replacing some of the generated snow
        SurfaceRules.RuleSource groundRules = SurfaceRules.ifTrue(
            SurfaceRules.ON_FLOOR,
            SurfaceRules.ifTrue(
                isAtOrAboveWaterLevel(),
                surfaceSnowWithPowderedSnow()
            )
        );

        // Surface rules for spawning dirt underneath top layers of the surface, peppered with powdered snow
        SurfaceRules.RuleSource buriedGroundRules = SurfaceRules.ifTrue(
            isSlightlyBelowWaterLevel(),
            SurfaceRules.ifTrue(
                SurfaceRules.UNDER_FLOOR,
                SurfaceRules.sequence(
                    surfaceDirtWithPowderedSnow(),
                    DIRT
                )
            )
        );

        // Surface rules for spawning snow where there would otherwise be dirt in underground caves
        // NOTE: To use this it needs to happen OUTSIDE of the abovePreliminarySurface() conditional
        SurfaceRules.RuleSource undergroundRules = SurfaceRules.ifTrue(
            isSlightlyBelowWaterLevel(),
            SurfaceRules.ifTrue(
                SurfaceRules.UNDER_FLOOR,
                SurfaceRules.sequence(
                    surfaceDirtWithPowderedSnow(),
                    DIRT
                )
            )
        );

        return SurfaceRules.ifTrue(
            SurfaceRules.abovePreliminarySurface(),
            SurfaceRules.sequence(
                groundRules,
                buriedGroundRules
            )
        );
    }
}


