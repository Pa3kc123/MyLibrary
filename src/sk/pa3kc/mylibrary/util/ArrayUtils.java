package sk.pa3kc.mylibrary.util;

import java.lang.reflect.Array;

import sk.pa3kc.mylibrary.Universal;
import sk.pa3kc.mylibrary.pojo.ObjectPointer;

public class ArrayUtils {
    private ArrayUtils() {}

    public static <T> boolean add(ObjectPointer<T[]> pointer, T value) {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) return false;

        if (find(pointer.value, value) != -1) return false;

        resizeArr(pointer, pointer.value.length + 1);

        pointer.value[pointer.value.length - 1] = value;

        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean[] addAll(ObjectPointer<T[]> pointer, T... values) {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (values == null) return new boolean[] { false };

        if (values.length == 1) {
            boolean[] results = new boolean[1];
            results[0] = add(pointer, values[0]);
            return results;
        }

        boolean[] results = new boolean[values.length];
        Class<?> pointerType = getComponentType(pointer.value);
        T[] tmpArr = (T[])cast(pointerType, (T[])Array.newInstance(pointerType, values.length));
        int size = 0;

        for (int i = 0; i < values.length; i++)
        if (find(pointer.value, values[i]) == -1) {
            tmpArr[size++] = values[i];
            results[i] = true;
        } else results[i] = false;

        int index = pointer.value.length;
        resizeArr(pointer, pointer.value.length + size);
        for (int i = index, j = 0; i < index + size; i++, j++)
            pointer.value[i] = values[j];

        return results;
    }

    private static void castX(Class<?> castType, Object source, Object output) {
        if (!source.getClass().isArray()) return;
        int dimCount = getDimensionCount(source);

        for (int i = 0; i < Array.getLength(source) && i < Array.getLength(output); i++) {
            castX(castType, Array.get(source, i), Array.get(output, i));

            if (dimCount == 1) Array.set(output, i, Array.get(source, i));
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> T cast(Class<T> type, Object arr) throws ClassCastException {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (type == arr.getClass()) return (T)arr;
        if (!arr.getClass().isArray())
            try { return type.cast(arr); } catch (ClassCastException ex) { return null; }

        Class<?> castType = getComponentType(type);
        T result = (T)Array.newInstance(castType, createDimensionMap(arr));

        castX(castType, arr, result);

        return result;
    }

    public static boolean compareAll(Object arr, Object val) {
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (val == null) throw new NullPointerException("val cannot be null");

        Class<?> arrType = getComponentType(arr);
        if (arrType.isPrimitive()) {
            arrType = ClassUtils.getWrapperType(arrType);
            arr = wrap(arr);
        }

        if (val.getClass() != arrType) return false;
        if (arr.getClass().isArray()) {
            int length = Array.getLength(arr);
            boolean result = false;

            for (int i = 0; i < length; i++) {
                result = compareAll(Array.get(arr, i), val);
                if (result) break;
            }

            return result;
        }
        return arr.equals(val);
    }

    private static void createDimensionMapX(ObjectPointer<Integer> index, int[] map, Object arr) {
        int indexValue = ((Number)index.value).intValue();

        if (arr != null && arr.getClass().isArray()) {
            int length = Array.getLength(arr);
            map[indexValue] = map[indexValue] < length ? length : map[indexValue];
            index.value = ++indexValue;
            for (int i = 0; i < length; i++)
            createDimensionMapX(index, map, Array.get(arr, i));
            index.value = --indexValue;
        }
    }
    public static int[] createDimensionMap(Object arr) {
        if (!arr.getClass().isArray())
            return new int[0];

        int[] map = new int[getDimensionCount(arr)];
        createDimensionMapX(new ObjectPointer<Integer>(0), map, arr);

        return map;
    }

    @SuppressWarnings("unchecked")
    private static <T> void deepArrCopyX(T source, ObjectPointer<T> output) {
        if (!source.getClass().isArray()) {
            output.value = source;
            return;
        }

        for (int i = 0; i < Array.getLength(source); i++) {
            ObjectPointer<T> pointer = new ObjectPointer<T>((T)Array.get(output.value, i));
            deepArrCopyX((T)Array.get(source, i), pointer);
            Array.set(output.value, i, pointer.value);
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> T deepArrCopy(T arr) {
        if (!arr.getClass().isArray()) throw new IllegalArgumentException("arr must be array type");

        ObjectPointer<T> pointer = new ObjectPointer<T>((T)Array.newInstance(getComponentType(arr), createDimensionMap(arr)));
        deepArrCopyX(arr, pointer);

        return pointer.value;
    }

    public static <T> int find(T[] arr, T value) {
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");

        for (int i = 0; i < arr.length; i++)
        if (arr[i].equals(value))
            return i;
        return -1;
    }

    public static <T> int find(T[] arr, T value, int index, int length) {
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");
        if (!NumberUtils.isWithinRange(arr.length, index, length))
            throw new IndexOutOfBoundsException("Arr length" + arr.length + " | index = " + index + " | length = " + length);

        for (int i = index; i < index + length; i++)
            if (arr[i].equals(value)) return i;
        return -1;
    }

    public static <T> int[] findAll(T[] arr, T value) {
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

    public static Class<?> getComponentType(Class<?> arr) {
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (arr.isArray() == false) return arr;

        while ((arr = arr.getComponentType()).isArray()) {}

        return arr;
    }
    public static Class<?> getComponentType(Object arr) {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        Class<?> type;
        for (type = arr.getClass(); type.isArray(); type = type.getComponentType()) {}

        return type;
    }

    private static void getDimensionCountX(Object arr, ObjectPointer<Integer> counter, ObjectPointer<Integer> index) {
        int indexInt = index.value.intValue();
        if (indexInt > counter.value.intValue())
            counter.value = indexInt;

        if (arr != null && arr.getClass().isArray()) {
            index.value = indexInt + 1;
            for (int i = 0; i < Array.getLength(arr); i++)
                getDimensionCountX(Array.get(arr, i), counter, index);
            index.value = indexInt;
        }
    }
    public static int getDimensionCount(Object arr) {
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (!arr.getClass().isArray()) return 0;

        ObjectPointer<Integer> counter = new ObjectPointer<Integer>(0);
        getDimensionCountX(arr, counter, new ObjectPointer<Integer>(0));
        return counter.value.intValue();
    }

    public static <T> boolean remove(ObjectPointer<T[]> pointer, T value) {
        if (pointer == null) throw new NullPointerException("arr cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");

        int index = find(pointer.value, value);

        if (index != -1) {
            for (int i = index; i < pointer.value.length; i++)
                pointer.value[i] = i+1 != pointer.value.length ? pointer.value[i] : null;

            resizeArr(pointer, pointer.value.length - 1);
        }

        return index != -1;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean removeAll(ObjectPointer<T[]> pointer, T value) {
        if (pointer == null) throw new NullPointerException("arr cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");

        int[] indexes = findAll(pointer.value, value);

        T[] arr = (T[])Array.newInstance(getComponentType(pointer.value), pointer.value.length - indexes.length);

        for (int i = 0, j = 0; i < indexes.length; i++)
        if (i != indexes[j]) {
            arr[i] = pointer.value[i];
            j++;
        }

        pointer.value = arr;

        return indexes.length != 0;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean removeDuplicates(ObjectPointer<T[]> pointer) {
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
    public static <T> void resizeArr(ObjectPointer<T[]> pointer, int newLength) {
        T[] arr = pointer.value;
        Class<?> pointerType = getComponentType(pointer.value);
        pointer.value = (T[])Array.newInstance(pointerType, newLength);

        for (int i = 0; i < newLength && i < arr.length; i++)
            pointer.value[i] = arr[i];
    }

    public static Object wrap(Object arr) {
        Class<?> type = getComponentType(arr);
        return cast(type.isPrimitive() ? ClassUtils.getWrapperType(type) : ClassUtils.getPrimitiveType(type), deepArrCopy(arr));
    }
}
