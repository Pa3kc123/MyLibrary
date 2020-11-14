package sk.pa3kc.mylibrary.utils;

@SuppressWarnings("unused")
public abstract class NumberUtils {
    private NumberUtils() {}

    /**
     * Rounds {@code value} to number of decimal {@code places}
     * @param value number to value
     * @param places number of rounded places
     * @return {@code value} rounded to number of decimal {@code places}
     */
    public static double round(double value, int places) {
        if (places <= 0) places = 0;

        int mult = 1;

        for (int i = 0; i < places; i++)
            mult *= 10d;

        return ((double)((int)((value * mult) + 0.5d))) / mult;
    }

    /**
     * Rounds {@code value} to number of decimal {@code places}
     * @param value number to value
     * @param places number of rounded places
     * @return {@code value} rounded to number of decimal {@code places}
     */
    public static float round(float value, int places) {
        if (places <= 0) places = 0;

        int mult = 1;

        for (int i = 0; i < places; i++)
            mult *= 10f;

        return ((float)((int)((value * mult) + 0.5d))) / mult;
    }

    /**
     * Returns the value of the {@code long} argument;
     * throwing an exception if the value overflows an {@code int}.
     * @param value the long value
     * @return the argument as an int
     * @throws ArithmeticException if the {@code argument} overflows an int
     */
    public static int toIntExact(long value) throws ArithmeticException {
        if ((int)value != value)
            throw new ArithmeticException("integer overflow");
        else return (int)value;
    }

    /**
     * Returns lowest value from passed values
     * @param values values to compare
     * @return lowest value from array, -1 if array.length is 0
     */
    public static int min(int... values) {
        if (values.length == 0) return -1;

        int result = 0x7FFFFFFF;
        for (int value : values)
            result = Math.min(result, value);

        return result;
    }

    /**
     * Returns highest value from passed values
     * @param values values to compare
     * @return highest value from array, -1 if array.length is 0
     */
    public static int max(int... values) {
        if (values.length == 0) return -1;

        int result = 0x80000000;
        for (int value : values)
            result = Math.max(result, value);

        return result;
    }

    public static int map(int val, int sourceMinRange, int sourceMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        return val < sourceMinRange ? sourceMinRange : Math.min(val, sourceMaxRange);
    }
    public static long map(long val, long sourceMinRange, long sourceMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        return val < sourceMinRange ? sourceMinRange : Math.min(val, sourceMaxRange);
    }
    public static float map(float val, float sourceMinRange, float sourceMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        return val < sourceMinRange ? sourceMinRange : Math.min(val, sourceMaxRange);
    }
    public static double map(double val, double sourceMinRange, double sourceMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        return val < sourceMinRange ? sourceMinRange : Math.min(val, sourceMaxRange);
    }

    /**
     * Re-maps a number from one range to another
     * @param val incoming value to be converted
     * @param sourceMinRange lower bound of the value's current range
     * @param sourceMaxRange upper bound of the value's current range
     * @param targetMinRange lower bound  of the value's target range
     * @param targetMaxRange upper bound  of the value's target range
     * @return remmaped value
     */
    public static int map(int val, int sourceMinRange, int sourceMaxRange, int targetMinRange, int targetMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        if (val < sourceMinRange || val > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< val !< sourceMaxRange");
        return ((val - sourceMinRange) / (sourceMaxRange - sourceMinRange)) * (targetMinRange - targetMaxRange) + targetMinRange;
    }
    /**
     * Re-maps a number from one range to another
     * @param val incoming value to be converted
     * @param sourceMinRange lower bound of the value's current range
     * @param sourceMaxRange upper bound of the value's current range
     * @param targetMinRange lower bound  of the value's target range
     * @param targetMaxRange upper bound  of the value's target range
     * @return remmaped value
     */
    public static long map(long val, long sourceMinRange, long sourceMaxRange, long targetMinRange, long targetMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        if (val < sourceMinRange || val > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< val !< sourceMaxRange");
        return ((val - sourceMinRange) / (sourceMaxRange - sourceMinRange)) * (targetMinRange - targetMaxRange) + targetMinRange;
    }
    /**
     * Re-maps a number from one range to another
     * @param val incoming value to be converted
     * @param sourceMinRange lower bound of the value's current range
     * @param sourceMaxRange upper bound of the value's current range
     * @param targetMinRange lower bound  of the value's target range
     * @param targetMaxRange upper bound  of the value's target range
     * @return remmaped value
     */
    public static float map(float val, float sourceMinRange, float sourceMaxRange, float targetMinRange, float targetMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        if (val < sourceMinRange || val > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< val !< sourceMaxRange");
        return ((val - sourceMinRange) / (sourceMaxRange - sourceMinRange)) * (targetMinRange - targetMaxRange) + targetMinRange;
    }
    /**
     * Re-maps a number from one range to another
     * @param val incoming value to be converted
     * @param sourceMinRange lower bound of the value's current range
     * @param sourceMaxRange upper bound of the value's current range
     * @param targetMinRange lower bound  of the value's target range
     * @param targetMaxRange upper bound  of the value's target range
     * @return remmaped value
     */
    public static double map(double val, double sourceMinRange, double sourceMaxRange, double targetMinRange, double targetMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        if (val < sourceMinRange || val > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< val !< sourceMaxRange");
        return ((val - sourceMinRange) / (sourceMaxRange - sourceMinRange)) * (targetMinRange - targetMaxRange) + targetMinRange;
    }

    /**
     * Parse {@code source} as a integer literal and returns its value
     *
     * <p>Like {@code Integer.parseInt} except that this function returns -1 instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @return parsed value or -1 when parsing fails
     */
    public static int tryParseInt(String value) {
        return tryParseInt(value, -1, 10);
    }
    /**
     * Parse {@code source} as a integer literal and returns its value
     *
     * <p>Like {@code Integer.parseInt} except that this function returns {@code onFail} value instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @param onFail value returned, when parsing fails
     * @return parsed value or {@code onFail} when parsing fails
     */
    public static int tryParseInt(String value, int onFail) {
        return tryParseInt(value, onFail, 10);
    }
    /**
     * Parse {@code source} as a integer literal and returns its value
     *
     * <p>Like {@code Integer.parseInt} except that this function returns {@code onFail} value instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @param onFail value returned, when parsing fails
     * @param radix the radix to be used while parsing value.
     * @return parsed value or {@code onFail} when parsing fails
     */
    public static int tryParseInt(String value, int onFail, int radix) {
        if (value == null) return onFail;
        try {
            return Integer.parseInt(value, radix);
        } catch (NumberFormatException ex) {
            return onFail;
        }
    }
    /**
     * Parse {@code source} as a long literal and returns its value
     *
     * <p>Like {@code Long.parseLong} except that this function returns -1 instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @return parsed value or -1 when parsing fails
     */
    public static long tryParseLong(String value) {
        return tryParseLong(value, -1L, 10);
    }
    /**
     * Parse {@code source} as a long literal and returns its value
     *
     * <p>Like {@code Long.parseLong} except that this function returns {@code onFail} value instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @param onFail value returned, when parsing fails
     * @return parsed value or {@code onFail} when parsing fails
     */
    public static long tryParseLong(String value, long onFail) {
        return tryParseLong(value, onFail, 10);
    }
    /**
     * Parse {@code source} as a long literal and returns its value
     *
     * <p>Like {@code Long.parseLong} except that this function returns {@code onFail} value instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @param onFail value returned, when parsing fails
     * @param radix the radix to be used while parsing value.
     * @return parsed value or {@code onFail} when parsing fails
     */
    public static long tryParseLong(String value, long onFail, int radix) {
        if (value == null) return onFail;
        try {
            return Long.parseLong(value, radix);
        } catch (NumberFormatException ex) {
            return onFail;
        }
    }
    /**
     * Parse {@code source} as a float literal and returns its value
     *
     * <p>Like {@code Float.parseFloat} except that this function returns -1 instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @return parsed value or -1 when parsing fails
     */
    public static float tryParseFloat(String value) {
        return tryParseFloat(value, -1.0f);
    }
    /**
     * Parse {@code source} as a float literal and returns its value
     *
     * <p>Like {@code Float.parseFloat} except that this function returns {@code onFail} value instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @param onFail value returned, when parsing fails
     * @return parsed value or {@code onFail} when parsing fails
     */
    public static float tryParseFloat(String value, float onFail) {
        if (value == null) return onFail;
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException ex) {
            return onFail;
        }
    }
    /**
     * Parse {@code source} as a double literal and returns its value
     *
     * <p>Like {@code Double.parseDouble} except that this function returns -1 instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @return parsed value or -1 when parsing fails
     */
    public static double tryParseDouble(String value) {
        return tryParseDouble(value, -1.0d);
    }
    /**
     * Parse {@code source} as a double literal and returns its value
     *
     * <p>Like {@code Double.parseDouble} except that this function returns {@code onFail} value instead of throwing {@code NumberFormatException}</p>
     * @param value source
     * @param onFail value returned, when parsing fails
     * @return parsed value or {@code onFail} when parsing fails
     */
    public static double tryParseDouble(String value, double onFail) {
        if (value == null) return onFail;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return onFail;
        }
    }
}
