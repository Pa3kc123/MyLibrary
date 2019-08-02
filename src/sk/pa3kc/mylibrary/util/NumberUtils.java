package sk.pa3kc.mylibrary.util;

public class NumberUtils
{
    private NumberUtils() {}

    /**
     * Checks if range of numbers are in range of array
     * @param arrLength Length of array
     * @param index Start pointer
     * @param length Length of searched range
     * @return {@code true} if range is within range of arrLength, otherwise {@code false}
     */
    public static boolean isWithinRange(int arrLength, int index, int length) {
        if (index >= 0 && index < arrLength)
        if (length >= 0 && index < arrLength)
        if (index + length >= 0 && index + length < arrLength)
            return true;
        return false;
    }

    /**
     * Rounds {@code value} to number of decimal {@code places}
     * @param value number to value
     * @param places number of rounded places
     * @return {@code value} rounded to number of decimal {@code places}
     */
    public static double round(double value, int places) {
        if (places <= 0) places = 0;

        double mult = 1;

        for (int i = 0; i < places; i++)
            mult *= 10d;

        return (double)(Math.round(value * mult) / mult);
    }

    /**
     * Rounds {@code value} to number of decimal {@code places}
     * @param value number to value
     * @param places number of rounded places
     * @return {@code value} rounded to number of decimal {@code places}
     */
    public static float round(float value, int places) {
        if (places <= 0) places = 0;

        float mult = 1;

        for (int i = 0; i < places; i++)
            mult *= 10f;

        return (float)(Math.round(value * mult) / mult);
    }

    /**
     * Returns the value of the {@code long} argument;
     * throwing an exception if the value overflows an {@code int}.
     * @param value the long value
     * @return the argument as an int
     * @throws ArithmeticException if the {@code argument} overflows an int
     * @since 1.6
     */
    public static int toIntExact(long value) throws ArithmeticException {
        if ((int)value != value)
            throw new ArithmeticException("integer overflow");
        else return (int)value;
    }

    /**
     * Returns lowest value from passed values
     * @param values Values to compare
     * @return Lowest value from array, -1 if array.length is 0
     */
    public static int min(int... values) {
        if (values.length == 0) return -1;

        int result = 0x7FFFFFFF;
        for (int value : values)
            result = result > value ? value : result;

        return result;
    }

    /**
     * Returns highest value from passed values
     * @param values Values to compare
     * @return Highest value from array, -1 if array.length is 0
     */
    public static int max(int... values) {
        if (values.length == 0) return -1;

        int result = 0x80000000;
        for (int value : values)
            result = result < value ? value : result;

        return result;
    }

    public static int map(int val, int sourceMinRange, int sourceMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        return val < sourceMinRange ? sourceMinRange : val > sourceMaxRange ? sourceMaxRange : val;
    }
    public static long map(long val, long sourceMinRange, long sourceMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        return val < sourceMinRange ? sourceMinRange : val > sourceMaxRange ? sourceMaxRange : val;
    }
    public static float map(float val, float sourceMinRange, float sourceMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        return val < sourceMinRange ? sourceMinRange : val > sourceMaxRange ? sourceMaxRange : val;
    }
    public static double map(double val, double sourceMinRange, double sourceMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        return val < sourceMinRange ? sourceMinRange : val > sourceMaxRange ? sourceMaxRange : val;
    }

    public static int map(int val, int sourceMinRange, int sourceMaxRange, int targetMinRange, int targetMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        if (val < sourceMinRange || val > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< val !< sourceMaxRange");
        return ((val - sourceMinRange) / (sourceMaxRange - sourceMinRange)) * (targetMinRange - targetMaxRange) + targetMinRange;
    }
    public static long map(long val, long sourceMinRange, long sourceMaxRange, long targetMinRange, long targetMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        if (val < sourceMinRange || val > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< val !< sourceMaxRange");
        return ((val - sourceMinRange) / (sourceMaxRange - sourceMinRange)) * (targetMinRange - targetMaxRange) + targetMinRange;
    }
    public static float map(float val, float sourceMinRange, float sourceMaxRange, float targetMinRange, float targetMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        if (val < sourceMinRange || val > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< val !< sourceMaxRange");
        return ((val - sourceMinRange) / (sourceMaxRange - sourceMinRange)) * (targetMinRange - targetMaxRange) + targetMinRange;
    }
    public static double map(double val, double sourceMinRange, double sourceMaxRange, double targetMinRange, double targetMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        if (val < sourceMinRange || val > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< val !< sourceMaxRange");
        return ((val - sourceMinRange) / (sourceMaxRange - sourceMinRange)) * (targetMinRange - targetMaxRange) + targetMinRange;
    }
}
