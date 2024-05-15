package org.example;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class BeanUtils {
    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) throws InvocationTargetException, IllegalAccessException {
        var methodsFrom = Arrays.stream(from.getClass().getMethods()).filter(method -> method.getName().startsWith("get")).toList();
        var methodsTo = Arrays.stream(to.getClass().getMethods()).filter(method -> method.getName().startsWith("set")).toList();

        for(var m : methodsFrom)
        {
            var m2 = methodsTo.stream().filter(method -> method.getName().endsWith(m.getName().substring(1))).filter(method -> method.getParameterCount() == 1).filter(method -> method.getParameterTypes()[0].isAssignableFrom(m.getReturnType())).findFirst();
            if(m2.isPresent())
            {
                m2.get().invoke(to,m.invoke(from));

            }
        }
    }
}
