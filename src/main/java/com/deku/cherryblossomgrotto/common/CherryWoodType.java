package com.deku.cherryblossomgrotto.common;

import net.minecraft.block.WoodType;

public class CherryWoodType extends WoodType {
    protected CherryWoodType(String name) {
        super(name);
        register(this);
    }
}
