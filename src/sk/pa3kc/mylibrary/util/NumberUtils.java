package sk.pa3kc.mylibrary.util;

public class NumberUtils
{
    private NumberUtils() {}

    public static boolean isWithinRange(int arrLength, int index, int length) {
        if (index >= 0 && index < arrLength)
        if (length >= 0 && index < arrLength)
        if (index + length >= 0 && index + length < arrLength)
            return true;
        return false;
    }
}
