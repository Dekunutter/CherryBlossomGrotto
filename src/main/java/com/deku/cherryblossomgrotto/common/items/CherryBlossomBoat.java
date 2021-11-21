package com.deku.cherryblossomgrotto.common.items;

import com.deku.cherryblossomgrotto.common.entity.item.ModBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class CherryBlossomBoat extends Item {
    private static final Predicate<Entity> NOT_SPECTATING_AND_COLLIDABLE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);
    private final ModBoatEntity.ModType type;

    public CherryBlossomBoat() {
        super(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TRANSPORTATION));
        setRegistryName("cherry_blossom_boat");
        type = ModBoatEntity.ModType.CHERRY;
    }

    /**
     * Determines what happens whenever a player uses this item.
     * Raytraces to the nearest block in the direction the player initiated the item use and checks what that block is.
     * If no block was hit, the item is just returned to the player's inventory (essentially nothing happens).
     * If the block is within the player's bounding box, ignore it and the item is returned to the player's inventory.
     * If a block was found in the raytrace we place the boat into the world, rotate it to match the player's current
     * direction and ensure the boat is loaded onto the server.
     *
     * @param world The world in which the item was used
     * @param player The player that used the item
     * @param hand The hand that the item was held in at time of use
     * @return The action that will be taken as a result of this item use
     */
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);
        RayTraceResult rayTraceResult = getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.ANY);

        if (rayTraceResult.getType() == RayTraceResult.Type.MISS)
        {
            return ActionResult.pass(itemStack);
        }
        else
        {
            Vector3d view = player.getViewVector(1.0F);
            List<Entity> entityList = world.getEntities(player, player.getBoundingBox().expandTowards(view.scale(5.0D)).inflate(1.0D), NOT_SPECTATING_AND_COLLIDABLE);
            if (!entityList.isEmpty())
            {
                Vector3d eyePosition = player.getEyePosition(1.0F);

                for (Entity entity : entityList)
                {
                    AxisAlignedBB entityBoundingBox = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (entityBoundingBox.contains(eyePosition))
                    {
                        return ActionResult.pass(itemStack);
                    }
                }
            }

            if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
            {
                ModBoatEntity boat = new ModBoatEntity(world, rayTraceResult.getLocation().x, rayTraceResult.getLocation().y, rayTraceResult.getLocation().z);
                boat.setModBoatType(this.type);
                boat.yRot = player.yRot;
                if (!world.noCollision(boat, boat.getBoundingBox().inflate(-0.1D)))
                {
                    return ActionResult.fail(itemStack);
                }
                else
                {
                    if (!world.isClientSide)
                    {
                        world.addFreshEntity(boat);
                        if (!player.abilities.instabuild)
                        {
                            itemStack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.sidedSuccess(itemStack, world.isClientSide());
                }
            }
            else
            {
                return ActionResult.pass(itemStack);
            }
        }
    }

}
