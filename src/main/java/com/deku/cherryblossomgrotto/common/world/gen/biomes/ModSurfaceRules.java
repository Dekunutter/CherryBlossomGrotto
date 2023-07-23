package com.deku.cherryblossomgrotto.common.world.gen.biomes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

// NOTE: Surface rules can be really finnicky to get right, sometimes I find its easiest to look at vanilla conditions and format them online with a tool like this: https://formatter.org/
//  This makes it more readable and allows me to get a better idea on how checks need to be structured to work as expected
//  Note that vanilla does things a little differently whereas I am doing checks on a biome-by-biome basis so there's some retinkering needed to get them right
public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);

    private static final SurfaceRules.RuleSource GRASS = makeStateRule(Blocks.GRASS_BLOCK);

    private static final SurfaceRules.RuleSource SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);

    private static final SurfaceRules.RuleSource POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);

    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);

    private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);

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
            ),
            SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomeInitializer.SHRUBLANDS),
                shrublandsSurfaceRules()
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
     * Checks if the surface is covered in sand.
     * Uses some level to noise to randomize the change and only makes the change above water level
     * Noise pattern in use means small patches of grass will form within the sand
     *
     * @return A surface rule that will randomly mark some patches of sand for change
     */
    private static SurfaceRules.RuleSource surfaceSandAndGrassCheck() {
        return SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(Noises.PATCH, 0.45D, 0.58D),
            SurfaceRules.ifTrue(
                isAboveWaterLevel(),
                GRASS
            )
        );
    }

    /**
     * Using the sand and grass condition, convert marked blocks to grass and layer the rest as sand
     * Sandstone replaces any sand that is over a gap and is therefore a "ceiling" block
     *
     * @return The rule for layering sand and sandstone but converting some surface sand to grass
     */
    private static SurfaceRules.RuleSource surfaceWithSandAndSandstoneCeilings() {
        return SurfaceRules.sequence(
            surfaceSandAndGrassCheck(),
            SurfaceRules.ifTrue(
                SurfaceRules.ON_CEILING,
                SANDSTONE
            ),
            SAND
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
        // NOTE: To use this it needs to happen OUTSIDE of the abovePreliminarySurface() conditional, see how the shrublands biome does that to establish underground rules
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

    private static SurfaceRules.RuleSource shrublandsSurfaceRules() {
        // Surface rules for making the ground sand unless it's over a space of air,
        // In which case it is sandstone. Also takes some patches to convert to grass on the surface
        SurfaceRules.RuleSource groundRules = SurfaceRules.ifTrue(
            SurfaceRules.ON_FLOOR,
            SurfaceRules.ifTrue(
                isAtOrAboveWaterLevel(),
                surfaceWithSandAndSandstoneCeilings()
            )
        );

        // Makes the underground similar to the surface, where sandstone ceilings and sand replace stone
        SurfaceRules.RuleSource undergroundRules = SurfaceRules.ifTrue(
            isSlightlyBelowWaterLevel(),
            SurfaceRules.ifTrue(
                SurfaceRules.UNDER_FLOOR,
                surfaceWithSandAndSandstoneCeilings()
            )
        );

        // Deep underground blocks in this biome are sandstone
        SurfaceRules.RuleSource deepUndergroundRules = SurfaceRules.ifTrue(
            isSlightlyBelowWaterLevel(),
            SurfaceRules.ifTrue(
                SurfaceRules.VERY_DEEP_UNDER_FLOOR,
                SANDSTONE
            )
        );

        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.abovePreliminarySurface(),
                groundRules
            ),
            undergroundRules,
            deepUndergroundRules
        );
    }
}


