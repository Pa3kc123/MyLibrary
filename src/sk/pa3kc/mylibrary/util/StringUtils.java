package sk.pa3kc.mylibrary.util;

import java.lang.reflect.Array;

public class StringUtils
{
    private StringUtils() {}

    private static void buildX(String text, Object value)
    {
        if (value.getClass().isArray() == false)
        {
            text.concat(value.toString());
            return;
        }

        for (int i = 0; i < Array.getLength(value); i++)
            buildX(text, Array.get(value, i));
    }

    public static String build(Object... values)
    {
        String result = "";

        for (Object value : values)
        {
            if (value.getClass().isArray() == true)
                buildX(result, value);
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
