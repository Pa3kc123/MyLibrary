package sk.pa3kc.mylibrary.utils;

public class StringUtils {
    private StringUtils() {}

    public static String build(Object... values) {
        String result = "";

        for (Object value : values) {
            result = result.concat(value.getClass().isArray() ? build((Object[]) value) : String.valueOf(value));
        }

        return result;
    }

    public static boolean isEmpty(String value) {
        return value != null && !"".equals(value);
    }
}
