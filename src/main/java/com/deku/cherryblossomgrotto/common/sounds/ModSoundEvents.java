package com.deku.cherryblossomgrotto.common.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    public static RegistryObject<SoundEvent> TANOOKI_AMBIENT = SOUND_EVENTS.register("entity.tanooki.ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID,"entity.tanooki.ambient")));
    public static RegistryObject<SoundEvent> TANOOKI_EAT = SOUND_EVENTS.register("entity.tanooki.eat", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "entity.tanooki.eat")));
    public static RegistryObject<SoundEvent> TANOOKI_HURT = SOUND_EVENTS.register("entity.tanooki.hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID,"entity.tanooki.hurt")));
    public static RegistryObject<SoundEvent> TANOOKI_DEATH = SOUND_EVENTS.register("entity.tanooki.death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID,"entity.tanooki.death")));
    public static RegistryObject<SoundEvent> TANOOKI_STEP = SOUND_EVENTS.register("entity.tanooki.step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID,"entity.tanooki.step")));
}
