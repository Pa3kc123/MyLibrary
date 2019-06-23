package sk.pa3kc.mylibrary.util.arr;

import java.lang.reflect.Array;

import sk.pa3kc.mylibrary.pojo.ObjectPointer;
import sk.pa3kc.mylibrary.util.NumberUtils;

class ObjectTypeUtils
{
    ObjectTypeUtils() {}

    @SuppressWarnings("unchecked")
    public <T> boolean add(Class<T> type, ObjectPointer<T[]> pointer, T value)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) return false;

        if (find(type, pointer.value, value) != -1) return false;
     
        System.arraycopy(pointer.value, 0, (pointer.value = (T[])new Object[pointer.value.length + 1]), 0, pointer.value.length - 1);

        pointer.value[pointer.value.length - 1] = value;

        pointer.value = cast(type, pointer.value);

        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> boolean[] addAll(Class<T> type, ObjectPointer<T[]> pointer, T... values)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (values == null) return new boolean[] { false };

        if (values.length == 1)
        {
            boolean[] results = new boolean[1];
            results[0] = add(type, pointer, values[1]);
            return results;
        }

        boolean[] results = new boolean[values.length];
        T[] tmpArr = (T[]) new Object[values.length];
        int size = 0;

        for (int i = 0; i < values.length; i++)
        if (find(type, pointer.value, values[i]) == -1)
        {
            tmpArr[size++] = values[i];
            results[i] = true;
        }
        else results[i] = false;

        int index = pointer.value.length;
        System.arraycopy(pointer.value, 0, (pointer.value = (T[])new Object[pointer.value.length + size]), 0, pointer.value.length - size);
        for (int i = index, j = 0; i < index + size; i++, j++)
            pointer.value[i] = values[j];

        pointer.value = cast(type, pointer.value);

        return results;
    }

    public <T> int find(Class<T> type, T[] arr, T value)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");
        
        for (int i = 0; i < arr.length; i++)
        if (arr[i].equals(value) == true)
            return i;
        return -1;
    }

    public <T> int find(Class<T> type, T[] arr, T value, int index, int length)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (arr == null) throw new NullPointerException("arr cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");
        if (NumberUtils.isWithinRange(arr.length, index, length) == false)
            throw new IndexOutOfBoundsException("Arr length" + arr.length + " | index = " + index + " | length = " + length);

        for (int i = index; i < index + length; i++)
            if (arr[i].equals(value)) return i;
        return -1;
    }

    public <T> int[] findAll(Class<T> type, T[] arr, T value)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
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

    @SuppressWarnings("unchecked")
    public <T> boolean remove(Class<T> type, ObjectPointer<T[]> pointer, T value)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (pointer == null) throw new NullPointerException("arr cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");
        
        int index = this.find(type, pointer.value, value);

        if (index != -1)
        {
            for (int i = index; i < pointer.value.length; i++)
                pointer.value[i] = i+1 != pointer.value.length ? pointer.value[i] : null;

            System.arraycopy(pointer.value, 0, (pointer.value = (T[])new Object[pointer.value.length - 1]), 0, pointer.value.length);

        }

        pointer.value = cast(type, pointer.value);
        return index != -1;
    }

    @SuppressWarnings("unchecked")
    public <T> boolean removeAll(Class<T> type, ObjectPointer<T[]> pointer, T value)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (pointer == null) throw new NullPointerException("arr cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (value == null) throw new NullPointerException("value cannot be null");
        
        int[] indexes = findAll(type, pointer.value, value);

        T[] arr = (T[])Array.newInstance(type, pointer.value.length - indexes.length);

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
    public <T> boolean removeDuplicates(Class<T>type, ObjectPointer<T[]> pointer)
    {
        if (type == null) throw new NullPointerException("type cannot be null");
        if (pointer == null) throw new NullPointerException("arr cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        
        T[] tmpArr = (T[])Array.newInstance(type, pointer.value.length);
        int size = 0;

        for (int i = 0; i < pointer.value.length; i++)
        if (find(type, tmpArr, pointer.value[i], 0, size) == -1)
            tmpArr[size++] = pointer.value[i];

        pointer.value = tmpArr;

        return size != 0;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] cast(Class<T> type, Object arr) throws ClassCastException
    {
        if (arr.getClass().isArray() == false) throw new IllegalArgumentException("arr must be array type");

        int arrLength = Array.getLength(arr);
        T[] result = (T[])Array.newInstance(type, arrLength);

        for (int i = 0; i < arrLength; i++)
            result[i] = type.cast(Array.get(arr, i));

        return result;
    }
}
