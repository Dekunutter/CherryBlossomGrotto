package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.items.black_pine.BlackPineBoat;
import com.deku.cherryblossomgrotto.common.items.black_pine.BlackPineChestBoat;
import com.deku.cherryblossomgrotto.common.items.hinoki.HinokiBoat;
import com.deku.cherryblossomgrotto.common.items.hinoki.HinokiChestBoat;
import com.deku.cherryblossomgrotto.common.items.maple.MapleBoat;
import com.deku.cherryblossomgrotto.common.items.maple.MapleChestBoat;
import com.deku.cherryblossomgrotto.common.items.maple.MapleLeaf;
import com.deku.cherryblossomgrotto.common.items.maple.MapleSyrupBottleItem;
import com.deku.cherryblossomgrotto.common.items.water_fir.WaterFirBoat;
import com.deku.cherryblossomgrotto.common.items.water_fir.WaterFirChestBoat;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModItems {
    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_log")
    public static BlockItem MAPLE_LOG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":stripped_maple_log")
    public static BlockItem STRIPPED_MAPLE_LOG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_wood")
    public static BlockItem MAPLE_WOOD;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":stripped_maple_wood")
    public static BlockItem STRIPPED_MAPLE_WOOD;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_planks")
    public static BlockItem MAPLE_PLANKS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_slab")
    public static BlockItem MAPLE_SLAB;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_stairs")
    public static BlockItem MAPLE_STAIRS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_button")
    public static BlockItem MAPLE_BUTTON;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_fence")
    public static BlockItem MAPLE_FENCE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_fence_gate")
    public static BlockItem MAPLE_FENCE_GATE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_pressure_plate")
    public static BlockItem MAPLE_PRESSURE_PLATE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_sign")
    public static SignItem MAPLE_SIGN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_hanging_sign")
    public static HangingSignItem MAPLE_HANGING_SIGN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_door")
    public static DoubleHighBlockItem MAPLE_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_trapdoor")
    public static BlockItem MAPLE_TRAPDOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_planks_trapdoor")
    public static BlockItem MAPLE_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_leaf")
    public static MapleLeaf MAPLE_LEAF;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_leaves")
    public static BlockItem MAPLE_LEAVES;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_leaf_pile")
    public static BlockItem MAPLE_LEAF_PILE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_sapling")
    public static BlockItem MAPLE_SAPLING;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_boat")
    public static MapleBoat MAPLE_BOAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_chest_boat")
    public static MapleChestBoat MAPLE_CHEST_BOAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":maple_syrup_bottle")
    public static MapleSyrupBottleItem MAPLE_SYRUP_BOTTLE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_log")
    public static BlockItem BLACK_PINE_LOG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":stripped_black_pine_log")
    public static BlockItem STRIPPED_BLACK_PINE_LOG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_wood")
    public static BlockItem BLACK_PINE_WOOD;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":stripped_black_pine_wood")
    public static BlockItem STRIPPED_BLACK_PINE_WOOD;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_planks")
    public static BlockItem BLACK_PINE_PLANKS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_slab")
    public static BlockItem BLACK_PINE_SLAB;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_stairs")
    public static BlockItem BLACK_PINE_STAIRS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_button")
    public static BlockItem BLACK_PINE_BUTTON;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_fence")
    public static BlockItem BLACK_PINE_FENCE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_fence_gate")
    public static BlockItem BLACK_PINE_FENCE_GATE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_pressure_plate")
    public static BlockItem BLACK_PINE_PRESSURE_PLATE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_sign")
    public static SignItem BLACK_PINE_SIGN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_hanging_sign")
    public static HangingSignItem BLACK_PINE_HANGING_SIGN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_door")
    public static DoubleHighBlockItem BLACK_PINE_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_trapdoor")
    public static BlockItem BLACK_PINE_TRAPDOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_planks_trapdoor")
    public static BlockItem BLACK_PINE_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_leaves")
    public static BlockItem BLACK_PINE_LEAVES;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_sapling")
    public static BlockItem BLACK_PINE_SAPLING;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_boat")
    public static BlackPineBoat BLACK_PINE_BOAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":black_pine_chest_boat")
    public static BlackPineChestBoat BLACK_PINE_CHEST_BOAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_log")
    public static BlockItem HINOKI_LOG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":stripped_hinoki_log")
    public static BlockItem STRIPPED_HINOKI_LOG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_wood")
    public static BlockItem HINOKI_WOOD;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":stripped_hinoki_wood")
    public static BlockItem STRIPPED_HINOKI_WOOD;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_planks")
    public static BlockItem HINOKI_PLANKS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_slab")
    public static BlockItem HINOKI_SLAB;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_stairs")
    public static BlockItem HINOKI_STAIRS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_button")
    public static BlockItem HINOKI_BUTTON;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_fence")
    public static BlockItem HINOKI_FENCE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_fence_gate")
    public static BlockItem HINOKI_FENCE_GATE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_pressure_plate")
    public static BlockItem HINOKI_PRESSURE_PLATE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_sign")
    public static SignItem HINOKI_SIGN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_hanging_sign")
    public static HangingSignItem HINOKI_HANGING_SIGN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_door")
    public static DoubleHighBlockItem HINOKI_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_trapdoor")
    public static BlockItem HINOKI_TRAPDOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_planks_trapdoor")
    public static BlockItem HINOKI_PLANKS_TRAP_DOOR;


    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_leaves")
    public static BlockItem HINOKI_LEAVES;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_sapling")
    public static BlockItem HINOKI_SAPLING;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_boat")
    public static HinokiBoat HINOKI_BOAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":hinoki_chest_boat")
    public static HinokiChestBoat HINOKI_CHEST_BOAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_log")
    public static BlockItem WATER_FIR_LOG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":stripped_water_fir_log")
    public static BlockItem STRIPPED_WATER_FIR_LOG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_wood")
    public static BlockItem WATER_FIR_WOOD;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":stripped_water_fir_wood")
    public static BlockItem STRIPPED_WATER_FIR_WOOD;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_planks")
    public static BlockItem WATER_FIR_PLANKS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_slab")
    public static BlockItem WATER_FIR_SLAB;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_stairs")
    public static BlockItem WATER_FIR_STAIRS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_button")
    public static BlockItem WATER_FIR_BUTTON;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_fence")
    public static BlockItem WATER_FIR_FENCE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_fence_gate")
    public static BlockItem WATER_FIR_FENCE_GATE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_pressure_plate")
    public static BlockItem WATER_FIR_PRESSURE_PLATE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_sign")
    public static SignItem WATER_FIR_SIGN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_hanging_sign")
    public static HangingSignItem WATER_FIR_HANGING_SIGN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_door")
    public static DoubleHighBlockItem WATER_FIR_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_trapdoor")
    public static BlockItem WATER_FIR_TRAPDOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_planks_trapdoor")
    public static BlockItem WATER_FIR_PLANKS_TRAP_DOOR;


    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_leaves")
    public static BlockItem WATER_FIR_LEAVES;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_sapling")
    public static BlockItem WATER_FIR_SAPLING;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_boat")
    public static WaterFirBoat WATER_FIR_BOAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":water_fir_chest_boat")
    public static WaterFirChestBoat WATER_FIR_CHEST_BOAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":katana")
    public static Katana KATANA;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":kunai")
    public static Kunai KUNAI;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":shuriken")
    public static Shuriken SHURIKEN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":koi")
    public static Koi KOI;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":cooked_koi")
    public static CookedKoi COOKED_KOI;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":rice")
    public static Rice RICE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":onigiri")
    public static Onigiri ONIGIRI;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":congee")
    public static Congee CONGEE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":koi_bucket")
    public static KoiBucket KOI_BUCKET;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":zen_lantern")
    public static DoubleHighBlockItem ZEN_LANTERN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":soul_zen_lantern")
    public static DoubleHighBlockItem SOUL_ZEN_LANTERN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":paper_lantern")
    public static BlockItem PAPER_LANTERN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":shoji_screen")
    public static DoubleHighBlockItem SHOJI_SCREEN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":dark_shoji_screen")
    public static DoubleHighBlockItem DARK_SHOJI_SCREEN;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":tatami_mat")
    public static BlockItem TATAMI_MAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":long_tatami_mat")
    public static BlockItem LONG_TATAMI_MAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":aged_tatami_mat")
    public static BlockItem AGED_TATAMI_MAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":long_aged_tatami_mat")
    public static BlockItem LONG_AGED_TATAMI_MAT;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":enoki_mushroom")
    public static BlockItem ENOKI_MUSHROOM;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":shiitake_mushroom")
    public static BlockItem SHIITAKE_MUSHROOM;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":enoki_mushroom_block")
    public static BlockItem ENOKI_MUSHROOM_BLOCK;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":terracotta_warrior_statue")
    public static DoubleHighBlockItem TERRACOTTA_WARRIOR_STATUE;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":ninja_mask")
    public static ArmorItem NINJA_MASK;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":ninja_tunic")
    public static ArmorItem NINJA_TUNIC;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":ninja_leggings")
    public static ArmorItem NINJA_LEGGINGS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":ninja_sandals")
    public static ArmorItem NINJA_SANDALS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":kabuto_helmet")
    public static ArmorItem KABUTO_HELMET;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":kabuto_cuirass")
    public static ArmorItem KABUTO_CUIRASS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":kabuto_greaves")
    public static ArmorItem KABUTO_GREAVES;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":kabuto_sandals")
    public static ArmorItem KABUTO_SANDALS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":acacia_planks_trapdoor")
    public static BlockItem ACACIA_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":birch_planks_trapdoor")
    public static BlockItem BIRCH_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":dark_oak_planks_trapdoor")
    public static BlockItem DARK_OAK_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":jungle_planks_trapdoor")
    public static BlockItem JUNGLE_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":oak_planks_trapdoor")
    public static BlockItem OAK_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":spruce_planks_trapdoor")
    public static BlockItem SPRUCE_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":mangrove_planks_trapdoor")
    public static BlockItem MANGROVE_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":bamboo_planks_trapdoor")
    public static BlockItem BAMBOO_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":cherry_planks_trapdoor")
    public static BlockItem CHERRY_PLANKS_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":smooth_stone_trapdoor")
    public static BlockItem SMOOTH_STONE_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":stone_trapdoor")
    public static BlockItem STONE_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":cobblestone_trapdoor")
    public static BlockItem COBBLESTONE_TRAP_DOOR;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":koi_spawn_egg")
    public static ForgeSpawnEggItem KOI_SPAWN_EGG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":tanooki_spawn_egg")
    public static ForgeSpawnEggItem TANOOKI_SPAWN_EGG;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":terracotta_warrior_spawn_egg")
    public static ForgeSpawnEggItem TERRACOTTA_WARRIOR_SPAWN_EGG;
}
