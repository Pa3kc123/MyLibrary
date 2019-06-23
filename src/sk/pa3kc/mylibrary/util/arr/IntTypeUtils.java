package sk.pa3kc.mylibrary.util.arr;

import sk.pa3kc.mylibrary.enums.ClassCode;
import sk.pa3kc.mylibrary.pojo.ObjectPointer;
import sk.pa3kc.mylibrary.util.NumberUtils;

class IntTypeUtils extends ObjectTypeUtils
{
    private final Class<Integer> type = Integer.class;
    
    IntTypeUtils() {}
    
    public boolean add(ObjectPointer<int[]> pointer, int value)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");

        ObjectPointer<Integer[]> tmpPointer = new ObjectPointer<Integer[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.add(this.type, tmpPointer, new Integer(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean[] addAll(ObjectPointer<int[]> pointer, int... values)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");
        if (values == null) return new boolean[] { false };

        ObjectPointer<Integer[]> tmpPointer = new ObjectPointer<Integer[]>(ArrayUtils.wrap(pointer.value));

        boolean[] results = super.addAll(Integer.class, tmpPointer, ArrayUtils.wrap(values));

        if (ArrayUtils.find(ClassCode.BOOLEAN, results, true) != -1)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return results;
    }
    
    public int find(int[] arr, int value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.find(this.type, ArrayUtils.wrap(arr), new Integer(value));
    }

    public int find(int[] arr, int value, int index, int length)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        if (NumberUtils.isWithinRange(arr.length, index, length) == false)
            throw new IndexOutOfBoundsException("Arr length" + arr.length + " | index = " + index + " | length = " + length);

        return super.find(this.type, ArrayUtils.wrap(arr), new Integer(value), index, length);
    }
    
    public int[] findAll(int[] arr, int value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.findAll(this.type, ArrayUtils.wrap(arr), new Integer(value));
    }

    public boolean remove(ObjectPointer<int[]> pointer, int value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Integer[]> tmpPointer = new ObjectPointer<Integer[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.remove(this.type, tmpPointer, new Integer(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeAll(ObjectPointer<int[]> pointer, int value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Integer[]> tmpPointer = new ObjectPointer<Integer[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeAll(this.type, tmpPointer, new Integer(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeDuplicates(ObjectPointer<int[]> pointer)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Integer[]> tmpPointer = new ObjectPointer<Integer[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeDuplicates(this.type, tmpPointer);

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public int[] unwrap(Integer[] arr)
    {
        if (arr == null) return null;

        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].intValue();

        return result;
    }

    public Integer[] wrap(int[] arr)
    {
        if (arr == null) return null;

        Integer[] result = new Integer[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Integer(arr[i]);

        return result;
    }
}
