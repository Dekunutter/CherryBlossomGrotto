package com.deku.cherryblossomgrotto.common.utils;

import java.lang.reflect.Constructor;
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
}
