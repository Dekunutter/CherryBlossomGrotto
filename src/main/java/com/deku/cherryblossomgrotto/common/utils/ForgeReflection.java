package com.deku.cherryblossomgrotto.common.utils;

import com.deku.cherryblossomgrotto.Main;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ForgeReflection {
    /**
     * Gets the first private constructor on a given class and returns it.
     * This uses reflection to override the accessibility of the constructor and access code that is private in the source
     *
     * @param classObject The class which we want to parse a private constructor from
     * @return The first private constructor on the class
     */
    public static Constructor<?> getFirstPrivateConstructor(Class classObject) {

        Constructor<?>[] constructors = classObject.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPrivate(constructor.getModifiers())) {
                constructor.setAccessible(true);
                return constructor;
            }
        }
        return null;
    }

    /**
     * Gets a static final field from a class by a given name and sets it to a given value using reflection.
     * Makes the field accessible, then changes its modifier to make it not final before setting the new value in place
     * Expects the SRG name of the field to work around obfuscation.
     *
     * @param classToCheck The class containing the static final field we want to change
     * @param fieldName The SRG name of the field we want to change the value of
     * @param newValue The new value of the field
     */
    public static void setObfuscatedStaticFinalFieldToValue(Class classToCheck, String fieldName, Object newValue) {
        try {
            Field field = ObfuscationReflectionHelper.findField(classToCheck, fieldName);
            field.setAccessible(true);
            Field fieldModifier = Field.class.getDeclaredField("modifiers");
            fieldModifier.setAccessible(true);
            fieldModifier.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, newValue);
        } catch (ObfuscationReflectionHelper.UnableToFindFieldException | NoSuchFieldException e) {
            Main.LOGGER.error("Failed to replace value of static final field " + fieldName + " on " + classToCheck.getName());
        } catch (IllegalAccessException e) {
            Main.LOGGER.error("Failed to access field " + fieldName + " on " + classToCheck.getName());
        }
    }

    /**
     * Gets the value of a private field from a given class using reflection.
     * Call this version of the function if you only want to get the value of a privatized static field.
     * Expects the SRG name of the field to work around obfuscation.
     *
     * @param classToCheck Class that contains the field we want to extract a value from
     * @param fieldName SRG Name of the field we want to extract a value from
     * @return The value in the field we want to read from, or null if nothing could be read
     */
    public static Object getObfuscatedPrivatizedFieldValue(Class classToCheck, String fieldName) {
        return getObfuscatedPrivatizedFieldValue(classToCheck, fieldName, null);
    }

    /**
     * Overloaded version of the privatized field getter that works on an object instance. If no object instance
     * was provided then this assumes the field is static, otherwise an instance value can be fetched.
     * Expects the SRG name of the field to work around obfuscation.
     *
     * @param classToCheck Class that contains the field we want to extract a value from
     * @param fieldName SRG Name of the field we want to extract a value from
     * @param instance The instance of the class that we want to get the value from
     * @return The value in the field we want to read from, or null if nothing could be read
     */
    public static Object getObfuscatedPrivatizedFieldValue(Class classToCheck, String fieldName, Object instance) {
        try {
            Field field = ObfuscationReflectionHelper.findField(classToCheck, fieldName);
            field.setAccessible(true);

            // TODO: Come up with a Java 16-friendly way of using reflection on final values (or swap to access transformers
            //  This is not going to work, access transformers are needed for places trying to reflect a final field
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup());
            VarHandle modifierHandle = lookup.findVarHandle(Field.class, "modifiers", int.class);

            int modifiers = field.getModifiers();
            if (Modifier.isFinal(modifiers)) {
                modifierHandle.set(field, modifiers & ~Modifier.FINAL);
            }


            //Field fieldModifier = Field.class.getDeclaredField("modifiers");
            //fieldModifier.setAccessible(true);
            //fieldModifier.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            if (instance != null) {
                return field.get(instance);
            } else {
                return field.get(classToCheck);
            }
        } catch (ObfuscationReflectionHelper.UnableToFindFieldException | IllegalAccessException | NoSuchFieldException e) {
            Main.LOGGER.error("Failed to access field " + fieldName + " on " + classToCheck.getName());
            return null;
        }
    }

    /**
     * Sets the privatized field on a class to the given value.
     * Call this version of the function if you know the value you want to update is a privatized static field.
     * Expects the SRG name of the field to work around obfuscation.
     *
     * @param classToCheck Class we want to update the value on
     * @param fieldName The SRG name of the variable we want to update
     * @param newValue The value we want to set the variable to.
     */
    public static void setObfuscatedPrivatizedFieldToValue(Class classToCheck, String fieldName, Object newValue) {
        setObfuscatedPrivatizedFieldToValue(classToCheck, fieldName, newValue, null);
    }

    /**
     * Overloadded version of the privatized field setter. If no object instance was provided then this assumes the
     * field is static, otherwise an instance value can be fetched.
     * Expects the SRG name of the field to work around obfuscation.
     *
     * @param classToCheck Class we want to update the value on
     * @param fieldName The SRG name of the variable we want to update
     * @param newValue The value we want to set the variable to
     * @param instance Instance of the class we want to update the value on
     */
    public static void setObfuscatedPrivatizedFieldToValue(Class classToCheck, String fieldName, Object newValue, Object instance) {
        try {
            Field field = ObfuscationReflectionHelper.findField(classToCheck, fieldName);
            field.setAccessible(true);
            if (instance != null) {
                field.set(instance, newValue);
            } else {
                field.set(null, newValue);
            }
        } catch (IllegalAccessException e) {
            Main.LOGGER.error("Failed to access field " + fieldName + " on " + classToCheck.getName());
            return;
        } catch (ObfuscationReflectionHelper.UnableToFindFieldException e) {
            Main.LOGGER.error("Field of name " + fieldName + " was not found on " + classToCheck.getName());
        }
    }
}
