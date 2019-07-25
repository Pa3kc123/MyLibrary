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

    public static double round(double value, int places) {
        if (places <= 0) places = 0;

        double mult = 1;

        for (int i = 0; i < places; i++) {
            mult *= 10;
        }

        return (double)(Math.round(value * mult) / mult);
    }

    public static float round(float value, int places) {
        if (places <= 0) places = 0;

        float mult = 1;

        for (int i = 0; i < places; i++) {
            mult *= 10;
        }

        return (float)(Math.round(value * mult) / mult);
    }
}
