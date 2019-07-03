package sk.pa3kc.mylibrary.util;

import java.lang.reflect.Array;

import sk.pa3kc.mylibrary.pojo.ObjectPointer;

public class ArrayUtils
{
    private ArrayUtils() {}

    public static <T> boolean add(ObjectPointer<T[]> pointer, T value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) return false;

        if (find(pointer.value, value) != -1) return false;

        resizeArr(pointer, pointer.value.length + 1);

        pointer.value[pointer.value.length - 1] = value;

        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean[] addAll(ObjectPointer<T[]> pointer, T... values)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (values == null) return new boolean[] { false };

        if (values.length == 1)
        {
            boolean[] results = new boolean[1];
            results[0] = add(pointer, values[1]);
            return results;
        }

        boolean[] results = new boolean[values.length];
        Class<?> pointerType = getComponentType(pointer.value);
        T[] tmpArr = (T[])cast(pointerType, (T[])Array.newInstance(pointerType, values.length));
        int size = 0;

        for (int i = 0; i < values.length; i++)
        if (find(pointer.value, values[i]) == -1)
        {
            tmpArr[size++] = values[i];
            results[i] = true;
        }
        else results[i] = false;

        int index = pointer.value.length;
        resizeArr(pointer, pointer.value.length + size);
        for (int i = index, j = 0; i < index + size; i++, j++)
            pointer.value[i] = values[j];

        return results;
    }

    public static Object[] cast(Class<?> type, Object[] arr) throws ClassCastException
    {
        if (type == null) throw new NullPointerException("type cannot be null");

        Object[] result = (Object[])Array.newInstance(type, arr.length);

        for (int i = 0; i < arr.length; i++)
            result[i] = type.cast(Array.get(arr, i));

        return result;
    }

    public static <T> boolean compareAll(T[] arr, T value)
    {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].equals(value) == false) return false;
        return true;
    }

    private static void createDimensionMapX(ObjectPointer<Integer> index, int[] map, Object arr)
    {
        int indexValue = ((Number)index.value).intValue();

        if (arr != null && arr.getClass().isArray() == true)
        {
            int length = Array.getLength(arr);
            map[indexValue] = map[indexValue] < length ? length : map[indexValue];
            index.value = ++indexValue;
            for (int i = 0; i < length; i++)
            createDimensionMapX(index, map, Array.get(arr, i));
            index.value = --indexValue;
        }
    }
    public static int[] createDimensionMap(Object arr)
    {
        if (arr.getClass().isArray() == false)
            return new int[0];

        int[] map = new int[getDimensionCount(arr)];
        createDimensionMapX(new ObjectPointer<Integer>(0), map, arr);

        return map;
    }

    private static void deepArrCopyX(Object source, ObjectPointer<Object> output)
    {
        if (source.getClass().isArray() == true)
            for (int i = 0; i < Array.getLength(source); i++)
            {
                ObjectPointer<Object> pointer = new ObjectPointer<Object>(Array.get(output.value, i));
                deepArrCopyX(Array.get(source, i), pointer);
                Array.set(output.value, i, pointer.value);
            }
        else output.value = source;
    }
    public static Object deepArrCopy(Object arr)
    {
        if (arr.getClass().isArray() == false) throw new IllegalArgumentException("arr must be array type");

        Class<?> arrComponentType = getComponentType(arr);
        int[] map = createDimensionMap(arr);
        Object arrObject = Array.newInstance(arrComponentType, map);

        ObjectPointer<Object> pointer = new ObjectPointer<Object>(arrObject);
        deepArrCopyX(arr, pointer);

        return pointer.value;
    }

    public static <T> int find(T[] arr, T value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");

        for (int i = 0; i < arr.length; i++)
        if (arr[i].equals(value) == true)
            return i;
        return -1;
    }

    public static <T> int find(T[] arr, T value, int index, int length)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");
        if (NumberUtils.isWithinRange(arr.length, index, length) == false)
            throw new IndexOutOfBoundsException("Arr length" + arr.length + " | index = " + index + " | length = " + length);

        for (int i = index; i < index + length; i++)
            if (arr[i].equals(value)) return i;
        return -1;
    }

    public static <T> int[] findAll(T[] arr, T value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");

        int[] indexes = new int[arr.length];
        int count = 0;

        for (int i = 0; i < arr.length; i++)
        if (arr[i].equals(value))
            indexes[count++] = i;

        System.arraycopy(indexes, 0, (indexes = new int[count]), 0, count);

        return indexes;
    }

    public static Class<?> getComponentType(Object arr)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        Class<?> type = arr.getClass();

        while (type.isArray() == true)
            type = type.getComponentType();

        return type;
    }

    private static void getDimensionCountX(Object arr, ObjectPointer<Integer> counter, ObjectPointer<Integer> index)
    {
        int indexInt = index.value.intValue();
        if (indexInt > counter.value.intValue())
            counter.value = indexInt;

        if (arr != null && arr.getClass().isArray() == true)
        {
            index.value = indexInt + 1;
            for (int i = 0; i < Array.getLength(arr); i++)
                getDimensionCountX(Array.get(arr, i), counter, index);
            index.value = indexInt;
        }
    }
    public static int getDimensionCount(Object arr)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (arr.getClass().isArray() == false) return 0;

        ObjectPointer<Integer> counter = new ObjectPointer<Integer>(0);
        getDimensionCountX(arr, counter, new ObjectPointer<Integer>(0));
        return counter.value.intValue();
    }

    public static <T> boolean remove(ObjectPointer<T[]> pointer, T value)
    {
        if (pointer == null) throw new NullPointerException("arr cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");

        int index = find(pointer.value, value);

        if (index != -1)
        {
            for (int i = index; i < pointer.value.length; i++)
                pointer.value[i] = i+1 != pointer.value.length ? pointer.value[i] : null;

            resizeArr(pointer, pointer.value.length - 1);
        }

        return index != -1;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean removeAll(ObjectPointer<T[]> pointer, T value)
    {
        if (pointer == null) throw new NullPointerException("arr cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");

        int[] indexes = findAll(pointer.value, value);

        T[] arr = (T[])Array.newInstance(getComponentType(pointer.value), pointer.value.length - indexes.length);

        for (int i = 0, j = 0; i < indexes.length; i++)
        if (i != indexes[j])
        {
            arr[i] = pointer.value[i];
            j++;
        }

        pointer.value = arr;

        return indexes.length != 0;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean removeDuplicates(ObjectPointer<T[]> pointer)
    {
        if (pointer == null) throw new NullPointerException("arr cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        T[] tmpArr = (T[])Array.newInstance(getComponentType(pointer.value), pointer.value.length);
        int size = 0;

        for (int i = 0; i < pointer.value.length; i++)
        if (find(tmpArr, pointer.value[i], 0, size) == -1)
            tmpArr[size++] = pointer.value[i];

        pointer.value = tmpArr;

        return size != 0;
    }

    @SuppressWarnings("unchecked")
    public static <T> void resizeArr(ObjectPointer<T[]> pointer, int newLength)
    {
        T[] arr = pointer.value;
        Class<?> pointerType = getComponentType(pointer.value);
        pointer.value = (T[])cast(pointerType, (T[])Array.newInstance(pointerType, newLength));

        for (int i = 0; i < newLength && i < arr.length; i++)
            pointer.value[i] = arr[i];
    }

    public static Object wrap(Object arr)
    {
        Class<?> type = getComponentType(arr);
        type = type.isPrimitive() == true ? ClassUtils.getWrapperType(type) : ClassUtils.getPrimitiveType(type);

        Object result = cast(type, deepArrCopy(arr));

        return null;
    }

    //region Wrappers
    public static Boolean[] wrap(boolean[] arr)
    {
        Boolean[] result = new Boolean[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Boolean(arr[i]);

        return result;
    }
    public static Byte[] wrap(byte[] arr)
    {
        Byte[] result = new Byte[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Byte(arr[i]);

        return result;
    }
    public static Character[] wrap(char[] arr)
    {
        Character[] result = new Character[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Character(arr[i]);

        return result;
    }
    public static Short[] wrap(short[] arr)
    {
        Short[] result = new Short[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Short(arr[i]);

        return result;
    }
    public static Integer[] wrap(int[] arr)
    {
        Integer[] result = new Integer[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Integer(arr[i]);

        return result;
    }
    public static Long[] wrap(long[] arr)
    {
        Long[] result = new Long[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Long(arr[i]);

        return result;
    }
    public static Float[] wrap(float[] arr)
    {
        Float[] result = new Float[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Float(arr[i]);

        return result;
    }
    public static Double[] wrap(double[] arr)
    {
        Double[] result = new Double[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Double(arr[i]);

        return result;
    }
    //endregion
    //region Unwrappers
    public static boolean[] unwrap(Boolean[] arr)
    {
        boolean[] result = new boolean[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].booleanValue();

        return result;
    }
    public static byte[] unwrap(Byte[] arr)
    {
        byte[] result = new byte[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].byteValue();

        return result;
    }
    public static char[] unwrap(Character[] arr)
    {
        char[] result = new char[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].charValue();

        return result;
    }
    public static short[] unwrap(Short[] arr)
    {
        short[] result = new short[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].shortValue();

        return result;
    }
    public static int[] unwrap(Integer[] arr)
    {
        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].intValue();

        return result;
    }
    public static long[] unwrap(Long[] arr)
    {
        long[] result = new long[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].longValue();

        return result;
    }
    public static float[] unwrap(Float[] arr)
    {
        float[] result = new float[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].floatValue();

        return result;
    }
    public static double[] unwrap(Double[] arr)
    {
        double[] result = new double[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].doubleValue();

        return result;
    }
    //#endregion
}
