package com.deku.cherryblossomgrotto.common.ui;

import com.deku.cherryblossomgrotto.common.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static RegistryObject<CreativeModeTab> MOD_CREATIVE_TAB = CREATIVE_MOD_TABS.register("mod_creative_tab", () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0).icon(() -> new ItemStack(ModItems.MAPLE_LEAF)).title(Component.translatable("tab.mod_tab")).displayItems((featureFlags, output) -> {
        // Maple blocks
        output.accept(ModItems.MAPLE_LOG);
        output.accept(ModItems.MAPLE_WOOD);
        output.accept(ModItems.STRIPPED_MAPLE_LOG);
        output.accept(ModItems.STRIPPED_MAPLE_WOOD);
        output.accept(ModItems.MAPLE_PLANKS);
        output.accept(ModItems.MAPLE_STAIRS);
        output.accept(ModItems.MAPLE_SLAB);
        output.accept(ModItems.MAPLE_FENCE);
        output.accept(ModItems.MAPLE_FENCE_GATE);
        output.accept(ModItems.MAPLE_DOOR);
        output.accept(ModItems.MAPLE_TRAPDOOR);
        output.accept(ModItems.MAPLE_PRESSURE_PLATE);
        output.accept(ModItems.MAPLE_BUTTON);
        output.accept(ModItems.MAPLE_LEAVES);
        output.accept(ModItems.MAPLE_SAPLING);
        output.accept(new ItemStack(ModItems.MAPLE_LEAF));
        output.accept(ModItems.MAPLE_LEAF_PILE);
        output.accept(new ItemStack(ModItems.MAPLE_BOAT));
        output.accept(new ItemStack(ModItems.MAPLE_CHEST_BOAT));
        output.accept(ModItems.MAPLE_SIGN);
        output.accept(ModItems.MAPLE_HANGING_SIGN);

        // Black pine blocks
        output.accept(ModItems.BLACK_PINE_LOG);
        output.accept(ModItems.BLACK_PINE_WOOD);
        output.accept(ModItems.STRIPPED_BLACK_PINE_LOG);
        output.accept(ModItems.STRIPPED_BLACK_PINE_WOOD);
        output.accept(ModItems.BLACK_PINE_PLANKS);
        output.accept(ModItems.BLACK_PINE_STAIRS);
        output.accept(ModItems.BLACK_PINE_SLAB);
        output.accept(ModItems.BLACK_PINE_FENCE);
        output.accept(ModItems.BLACK_PINE_FENCE_GATE);
        output.accept(ModItems.BLACK_PINE_DOOR);
        output.accept(ModItems.BLACK_PINE_TRAPDOOR);
        output.accept(ModItems.BLACK_PINE_PRESSURE_PLATE);
        output.accept(ModItems.BLACK_PINE_BUTTON);
        output.accept(ModItems.BLACK_PINE_LEAVES);
        output.accept(ModItems.BLACK_PINE_SAPLING);
        output.accept(new ItemStack(ModItems.BLACK_PINE_BOAT));
        output.accept(new ItemStack(ModItems.BLACK_PINE_CHEST_BOAT));
        output.accept(ModItems.BLACK_PINE_SIGN);
        output.accept(ModItems.BLACK_PINE_HANGING_SIGN);

        // Hinoki blocks
        output.accept(ModItems.HINOKI_LOG);
        output.accept(ModItems.HINOKI_WOOD);
        output.accept(ModItems.STRIPPED_HINOKI_LOG);
        output.accept(ModItems.STRIPPED_HINOKI_WOOD);
        output.accept(ModItems.HINOKI_PLANKS);
        output.accept(ModItems.HINOKI_STAIRS);
        output.accept(ModItems.HINOKI_SLAB);
        output.accept(ModItems.HINOKI_FENCE);
        output.accept(ModItems.HINOKI_FENCE_GATE);
        output.accept(ModItems.HINOKI_DOOR);
        output.accept(ModItems.HINOKI_TRAPDOOR);
        output.accept(ModItems.HINOKI_PRESSURE_PLATE);
        output.accept(ModItems.HINOKI_BUTTON);
        output.accept(ModItems.HINOKI_LEAVES);
        output.accept(ModItems.HINOKI_SAPLING);
        output.accept(new ItemStack(ModItems.HINOKI_BOAT));
        output.accept(new ItemStack(ModItems.HINOKI_CHEST_BOAT));
        output.accept(ModItems.HINOKI_SIGN);
        output.accept(ModItems.HINOKI_HANGING_SIGN);

        // Water Fir blocks
        output.accept(ModItems.WATER_FIR_LOG);
        output.accept(ModItems.WATER_FIR_WOOD);
        output.accept(ModItems.STRIPPED_WATER_FIR_LOG);
        output.accept(ModItems.STRIPPED_WATER_FIR_WOOD);
        output.accept(ModItems.WATER_FIR_PLANKS);
        output.accept(ModItems.WATER_FIR_STAIRS);
        output.accept(ModItems.WATER_FIR_SLAB);
        output.accept(ModItems.WATER_FIR_FENCE);
        output.accept(ModItems.WATER_FIR_FENCE_GATE);
        output.accept(ModItems.WATER_FIR_DOOR);
        output.accept(ModItems.WATER_FIR_TRAPDOOR);
        output.accept(ModItems.WATER_FIR_PRESSURE_PLATE);
        output.accept(ModItems.WATER_FIR_BUTTON);
        output.accept(ModItems.WATER_FIR_LEAVES);
        output.accept(ModItems.WATER_FIR_SAPLING);
        output.accept(new ItemStack(ModItems.WATER_FIR_BOAT));
        output.accept(new ItemStack(ModItems.WATER_FIR_CHEST_BOAT));
        output.accept(ModItems.WATER_FIR_SIGN);
        output.accept(ModItems.WATER_FIR_HANGING_SIGN);

        // Crops & Food
        output.accept(new ItemStack(ModItems.RICE));
        output.accept(new ItemStack(ModItems.ONIGIRI));
        output.accept(new ItemStack(ModItems.CONGEE));
        output.accept(new ItemStack(ModItems.MAPLE_SYRUP_BOTTLE));

        // Wildlife
        output.accept(new ItemStack(ModItems.KOI));
        output.accept(new ItemStack(ModItems.COOKED_KOI));
        output.accept(new ItemStack(ModItems.KOI_BUCKET));
        output.accept(new ItemStack(ModItems.KOI_SPAWN_EGG));
        output.accept(new ItemStack(ModItems.TANOOKI_SPAWN_EGG));

        // Monsters
        output.accept(new ItemStack(ModItems.TERRACOTTA_WARRIOR_SPAWN_EGG));

        // Misc building blocks
        output.accept(ModItems.SHOJI_SCREEN);
        output.accept(ModItems.TATAMI_MAT);
        output.accept(ModItems.LONG_TATAMI_MAT);
        output.accept(ModItems.AGED_TATAMI_MAT);
        output.accept(ModItems.LONG_AGED_TATAMI_MAT);
        output.accept(ModItems.ZEN_LANTERN);
        output.accept(ModItems.SOUL_ZEN_LANTERN);
        output.accept(ModItems.PAPER_LANTERN);
        output.accept(ModItems.TERRACOTTA_WARRIOR_STATUE);

        // Weapons & Armour
        output.accept(new ItemStack(ModItems.KATANA));
        output.accept(new ItemStack(ModItems.KUNAI));
        output.accept(new ItemStack(ModItems.SHURIKEN));
        output.accept(new ItemStack(ModItems.NINJA_MASK));
        output.accept(new ItemStack(ModItems.NINJA_TUNIC));
        output.accept(new ItemStack(ModItems.NINJA_LEGGINGS));
        output.accept(new ItemStack(ModItems.NINJA_SANDALS));
        output.accept(new ItemStack(ModItems.KABUTO_HELMET));
        output.accept(new ItemStack(ModItems.KABUTO_CUIRASS));
        output.accept(new ItemStack(ModItems.KABUTO_GREAVES));
        output.accept(new ItemStack(ModItems.KABUTO_SANDALS));

        // Hidden trapdoors
        output.accept(ModItems.MAPLE_PLANKS_TRAP_DOOR);
        output.accept(ModItems.BLACK_PINE_PLANKS_TRAP_DOOR);
        output.accept(ModItems.HINOKI_PLANKS_TRAP_DOOR);
        output.accept(ModItems.WATER_FIR_PLANKS_TRAP_DOOR);
        output.accept(ModItems.ACACIA_PLANKS_TRAP_DOOR);
        output.accept(ModItems.BIRCH_PLANKS_TRAP_DOOR);
        output.accept(ModItems.DARK_OAK_PLANKS_TRAP_DOOR);
        output.accept(ModItems.JUNGLE_PLANKS_TRAP_DOOR);
        output.accept(ModItems.OAK_PLANKS_TRAP_DOOR);
        output.accept(ModItems.SPRUCE_PLANKS_TRAP_DOOR);
        output.accept(ModItems.MANGROVE_PLANKS_TRAP_DOOR);
        output.accept(ModItems.BAMBOO_PLANKS_TRAP_DOOR);
        output.accept(ModItems.CHERRY_PLANKS_TRAP_DOOR);
        output.accept(ModItems.SMOOTH_STONE_TRAP_DOOR);
        output.accept(ModItems.STONE_TRAP_DOOR);
        output.accept(ModItems.SMOOTH_STONE_TRAP_DOOR);
        output.accept(ModItems.COBBLESTONE_TRAP_DOOR);
    }).build());
}
