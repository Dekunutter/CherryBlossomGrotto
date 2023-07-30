package com.deku.eastwardjourneys.client.models;

import com.deku.eastwardjourneys.common.entity.monster.terracotta_warrior.TerracottaWarrior;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class TerracottaWarriorModel extends DefaultedEntityGeoModel<TerracottaWarrior> {
    public TerracottaWarriorModel() {
        super(new ResourceLocation(MOD_ID, "monsters/terracotta_warrior"), false);
    }
}
