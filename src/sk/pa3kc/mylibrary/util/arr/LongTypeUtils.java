package sk.pa3kc.mylibrary.util.arr;

import sk.pa3kc.mylibrary.enums.ClassCode;
import sk.pa3kc.mylibrary.pojo.ObjectPointer;
import sk.pa3kc.mylibrary.util.NumberUtils;

class LongTypeUtils extends ObjectTypeUtils
{
    private final Class<Long> type = Long.class;
    
    LongTypeUtils() {}
    
    public boolean add(ObjectPointer<long[]> pointer, long value)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");

        ObjectPointer<Long[]> tmpPointer = new ObjectPointer<Long[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.add(this.type, tmpPointer, new Long(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean[] addAll(ObjectPointer<long[]> pointer, long... values)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");
        if (values == null) return new boolean[] { false };

        ObjectPointer<Long[]> tmpPointer = new ObjectPointer<Long[]>(ArrayUtils.wrap(pointer.value));

        boolean[] results = super.addAll(Long.class, tmpPointer, ArrayUtils.wrap(values));

        if (ArrayUtils.find(ClassCode.BOOLEAN, results, true) != -1)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return results;
    }
    
    public int find(long[] arr, long value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.find(this.type, ArrayUtils.wrap(arr), new Long(value));
    }

    public int find(long[] arr, long value, int index, int length)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        if (NumberUtils.isWithinRange(arr.length, index, length) == false)
            throw new IndexOutOfBoundsException("Arr length" + arr.length + " | index = " + index + " | length = " + length);

        return super.find(this.type, ArrayUtils.wrap(arr), new Long(value), index, length);
    }
    
    public int[] findAll(long[] arr, long value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.findAll(this.type, ArrayUtils.wrap(arr), new Long(value));
    }

    public boolean remove(ObjectPointer<long[]> pointer, long value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Long[]> tmpPointer = new ObjectPointer<Long[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.remove(this.type, tmpPointer, new Long(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeAll(ObjectPointer<long[]> pointer, long value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Long[]> tmpPointer = new ObjectPointer<Long[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeAll(this.type, tmpPointer, new Long(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeDuplicates(ObjectPointer<long[]> pointer)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Long[]> tmpPointer = new ObjectPointer<Long[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeDuplicates(this.type, tmpPointer);

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public long[] unwrap(Long[] arr)
    {
        if (arr == null) return null;

        long[] result = new long[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].longValue();

        return result;
    }

    public Long[] wrap(long[] arr)
    {
        if (arr == null) return null;

        Long[] result = new Long[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Long(arr[i]);

        return result;
    }
}
