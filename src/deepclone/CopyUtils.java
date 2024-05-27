package deepclone;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class CopyUtils {

    public static <T> T deepCopy(T original) {
        return deepCopy(original, new IdentityHashMap<>());
    }

    private static <T> T deepCopy(T original, Map<Object, Object> visited) {
        if (original == null) {
            return null;
        }
        if (visited.containsKey(original)) {
            return (T) visited.get(original);
        }

        Class<?> clazz = original.getClass();
        if (isImmutable(clazz)) {
            return original;
        }
        if (clazz.isArray()) {
            return deepCopyArray(original);
        }
        if (Collection.class.isAssignableFrom(clazz)) {
            return deepCopyCollection(original);
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return (T) new HashMap<>((Map) original);
        }

        return deepCopyObject(original, visited);
    }


    private static <T> T deepCopyArray(T original) {
        Class<?> clazz = original.getClass();
        int length = Array.getLength(original);
        Class<?> componentType = clazz.getComponentType();

        Object arrayCopy = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Object element = Array.get(original, i);
            Array.set(arrayCopy, i, deepCopy(element));
        }
        return (T) arrayCopy;
    }

    private static <T> T deepCopyCollection(T original) {
        Class<?> clazz = original.getClass();
        try {
            Constructor<?> collectionConstructor = clazz.getDeclaredConstructor(Collection.class);
            return (T) collectionConstructor.newInstance(original);
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy collection.", e);
        }
    }

    private static <T> T deepCopyObject(T original, Map<Object, Object> visited) {
        Class<?> clazz = original.getClass();
        try {
            T copy = initializeCopy(clazz);
            visited.put(original, copy);

            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object fieldValue = field.get(original);
                    Object copiedValue = deepCopy(fieldValue, visited);
                    field.set(copy, copiedValue);
                }
                clazz = clazz.getSuperclass();
            }
            return copy;
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy object.", e);
        }
    }

    private static <T> T initializeCopy(Class<?> clazz) {
        Constructor<?> constructor;
        List<Object> args = new ArrayList<>();
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException noSuchMethodException) {
            constructor = clazz.getDeclaredConstructors()[0];
            for (Class<?> type: constructor.getParameterTypes()) {
                args.add(getDefault(type));
            }
        }
        try {
            return (T) constructor.newInstance(args.toArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate object.", e);
        }
    }

    private static Object getDefault(Class<?> clazz) {
        if (clazz == byte.class) {
            return 0;
        } else if (clazz == short.class) {
            return 0;
        } else if (clazz == int.class) {
            return 0;
        } else if (clazz == long.class) {
            return 0L;
        } else if (clazz == float.class) {
            return 0.0f;
        } else if (clazz == double.class) {
            return 0.0d;
        } else if (clazz == char.class) {
            return '\u0000';
        } else if (clazz == boolean.class) {
            return false;
        }
        return null;
    }

    private static boolean isImmutable(Class<?> clazz) {
        return clazz.isPrimitive() ||
                isWrapper(clazz) ||
                clazz == String.class;
    }

    private static boolean isWrapper(Class<?> clazz) {
        return clazz == Byte.class ||
                clazz == Short.class ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Float.class ||
                clazz == Double.class ||
                clazz == Character.class ||
                clazz == Boolean.class;
    }
}
