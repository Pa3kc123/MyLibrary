package sk.pa3kc.mylibrary.util;

import java.lang.reflect.Array;

import sk.pa3kc.mylibrary.pojo.ObjectPointer;

public class StringUtils
{
    private StringUtils() {}

    private static void buildX(ObjectPointer<String> pointer, Object value)
    {
        if (value.getClass().isArray() == false)
        {
            pointer.value = pointer.value.concat(value.toString());
            return;
        }

        for (int i = 0; i < Array.getLength(value); i++)
            buildX(pointer, Array.get(value, i));
    }

    public static String build(Object... values)
    {
        String result = "";

        for (Object value : values)
        {
            if (value.getClass().isArray() == true)
            {
                ObjectPointer<String> pointer = new ObjectPointer<String>("");
                buildX(pointer, value);
                result = result.concat(pointer.value);
            }
            else
                result.concat(value.toString());
        }

        return result;
    }

    public static boolean isEmpty(String value)
    {
        return value != null && value != "";
    }
}
