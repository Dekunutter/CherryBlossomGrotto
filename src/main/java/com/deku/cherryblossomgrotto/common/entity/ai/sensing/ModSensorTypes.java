package com.deku.cherryblossomgrotto.common.entity.ai.sensing;

import com.deku.cherryblossomgrotto.common.entity.animal.tanooki.TanookiAI;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModSensorTypes<U extends Sensor<?>> {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, MOD_ID);

    public static RegistryObject<SensorType<TanookiAttackablesSensor>> TANOOKI_ATTACKABLES = SENSOR_TYPES.register("tanooki_attackables", () -> new SensorType<>(TanookiAttackablesSensor::new));
    public static RegistryObject<SensorType<TemptingSensor>> TANOOKI_TEMPTATIONS = SENSOR_TYPES.register("tanooki_temptations", () -> new SensorType<>(() -> new TemptingSensor(TanookiAI.getTemptations())));
}
