package sk.pa3kc.mylibrary.util;

public class ClassUtils {
    private ClassUtils() {}

    public static Class<?> getPrimitiveType(Class<?> type) {
        if (type == null) throw new NullPointerException("type cannot be null");

        if (type.isPrimitive() == true) return type;

        if (type == Character.class) type = char.class;
        if (type == Byte.class) type = byte.class;
        if (type == Short.class) type = short.class;
        if (type == Integer.class) type = int.class;
        if (type == Long.class) type = long.class;
        if (type == Float.class) type = float.class;
        if (type == Double.class) type = double.class;
        if (type == Boolean.class) type = boolean.class;

        return type;
    }
    public static Class<?> getWrapperType(Class<?> type) {
        if (type == null) throw new NullPointerException("type cannot be null");

        if (type.isPrimitive() == false) return type;

        if (type == char.class) type = Character.class;
        if (type == byte.class) type = Byte.class;
        if (type == short.class) type = Short.class;
        if (type == int.class) type = Integer.class;
        if (type == long.class) type = Long.class;
        if (type == float.class) type = Float.class;
        if (type == double.class) type = Double.class;
        if (type == boolean.class) type = Boolean.class;

        return type;
    }
}
