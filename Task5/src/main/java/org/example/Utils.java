package org.example;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;

public class Utils {
    public static  boolean allConstantsContainsTheirNamesAsValues(Object o)
    {

        return Arrays.stream(o.getClass().getFields()).filter(field -> {
            int modifier = field.getModifiers();
            return field.getType().getName().equals("java.lang.String") && Modifier.isFinal(modifier) && Modifier.isStatic(modifier) && Modifier.isPublic(modifier);
        }).allMatch(field -> {
            try {
                return field.getName().equals((String) field.get(o));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
