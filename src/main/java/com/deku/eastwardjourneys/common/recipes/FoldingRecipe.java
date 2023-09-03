package com.deku.eastwardjourneys.common.recipes;

import com.deku.eastwardjourneys.common.items.ModItems;
import com.deku.eastwardjourneys.utils.ModConfiguration;
import com.google.common.collect.Lists;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.*;

public class FoldingRecipe extends CustomRecipe {
    private ItemStack katana;
    private ItemStack ingot;

    public FoldingRecipe(ResourceLocation resourceLocation, CraftingBookCategory category) {
        super(resourceLocation, category);
    }

    /**
     * Checks if the ingredients in the crafting grid match what's expected for this recipe.
     * The recipe should only match on a combination of a katana (singular) and iron ingots (plural).
     * The ordering or position of the ingredients in the crafting grid doesn't matter.
     *
     * @param craftingInventory The crafting menu and inventory
     * @param level The level the player is crafting within
     * @return If the ingredients in this recipe are fulfilled
     */
    @Override
    public boolean matches(CraftingContainer craftingInventory, Level level) {
        List<ItemStack> list = Lists.newArrayList();

        for(int i = 0; i < craftingInventory.getContainerSize(); ++i) {
            ItemStack itemstack = craftingInventory.getItem(i);
            if (!itemstack.isEmpty()) {
                list.add(itemstack);
                if (list.size() > 2) {
                    return false;
                }
                if (list.size() == 2) {
                    ItemStack firstItemStack = list.get(0);
                    ItemStack secondItemStack = list.get(1);
                    if ((assignKatanaIfMatching(firstItemStack) && assignIronIngotIfMatching(secondItemStack)) || (assignKatanaIfMatching(secondItemStack) && assignIronIngotIfMatching(firstItemStack))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the given item stack is a single repairable katana.
     * Assigns the item stack to the global variable for use in assembly.
     *
     * @param itemStack The item we are checking if we want to assign as a katana
     * @return Whether the item stack is a katana
     */
    private boolean assignKatanaIfMatching(ItemStack itemStack) {
        if (itemStack.getItem() != ModItems.KATANA || itemStack.getCount() != 1 || !itemStack.isRepairable()) {
            katana = null;
            return false;
        }
        katana = itemStack;
        return true;
    }

    /**
     * Checks if the given item stack is stack of iron ingots.
     * Assigns the item stack to the global variable for use in assembly.
     *
     * @param itemStack The item we are checking if we want to assign as an iron ingot
     * @return Whether the item stack is an iron ingot
     */
    private boolean assignIronIngotIfMatching(ItemStack itemStack) {
        if (itemStack.getItem() != Items.IRON_INGOT) {
            ingot = null;
            return false;
        }
        ingot = itemStack;
        return true;
    }

    /**
     * Assembles the resulting item for this recipe.
     * In this case, the recipe is hardcoded to make a copy of a given katana and update its fold count via NBT.
     * If there's no existing fold count in NBT then we assume the current count as 1 and update it to 2.
     * Performing this recipe consumes a single iron ingot and the katana to clone it with the updated fold count.
     * Exits early if the katana or ingot global variables aren't assigned (which occurs during recipe matching)
     *
     * @param craftingInventory The crafting menu and inventory the katana is being assembled within
     * @param registryAccess The accessor for registries
     * @return The instance of the Katana with NBT data saved that will build our result
     */
    @Override
    public ItemStack assemble(CraftingContainer craftingInventory, RegistryAccess registryAccess) {
        if (katana == null || ingot == null) {
            return ItemStack.EMPTY;
        }
        ItemStack assembledKatana = katana.copy();
        CompoundTag compoundnbt = assembledKatana.getTag();

        if (compoundnbt != null) {
            int currentFolds = compoundnbt.getInt("folds");
            if(currentFolds >= 0 && currentFolds < ModConfiguration.maxFolds.get()) {
                currentFolds++;
            } else {
                return ItemStack.EMPTY;
            }
            compoundnbt.putInt("folds", currentFolds);
            assembledKatana.setTag(compoundnbt);
        }

        return assembledKatana;
    }

    /**
     * Determines if the crafting grid is large enough to craft this recipe.
     * This recipe only uses two ingredients so any crafting grid should be fine but we'll check just in case
     *
     * @param craftingWidth The width of the grid this recipe is being crafted in
     * @param craftingHeight The height of the grid this recipe is being crafted in
     * @return Whether this recipe can be crafted within the given dimensions
     */
    @Override
    public boolean canCraftInDimensions(int craftingWidth, int craftingHeight) {
        return craftingWidth * craftingHeight >= 2;
    }

    /**
     * The recipe serializer associated with this recipe
     *
     * @return The serializer associated with this recipe
     */
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializerInitializer.FOLDING_SERIALIZER.get();
    }
}

