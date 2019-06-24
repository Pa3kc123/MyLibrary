package sk.pa3kc.mylibrary.util;

public class StringUtils
{
    private StringUtils() {}

    public static String build(Object... values)
    {
        String result = "";

        for (Object value : values)
            result.concat(value.toString());

        return result;
    }

    public static boolean isEmpty(String value)
    {
        return value != null && value != "";
    }
}
