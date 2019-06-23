package sk.pa3kc.mylibrary.util.arr;

import sk.pa3kc.mylibrary.enums.ClassCode;
import sk.pa3kc.mylibrary.pojo.ObjectPointer;
import sk.pa3kc.mylibrary.util.NumberUtils;

class ShortTypeUtils extends ObjectTypeUtils
{
    private final Class<Short> type = Short.class;
    
    ShortTypeUtils() {}
    
    public boolean add(ObjectPointer<short[]> pointer, short value)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");

        ObjectPointer<Short[]> tmpPointer = new ObjectPointer<Short[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.add(this.type, tmpPointer, new Short(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean[] addAll(ObjectPointer<short[]> pointer, short... values)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");
        if (values == null) return new boolean[] { false };

        ObjectPointer<Short[]> tmpPointer = new ObjectPointer<Short[]>(ArrayUtils.wrap(pointer.value));

        boolean[] results = super.addAll(Short.class, tmpPointer, ArrayUtils.wrap(values));

        if (ArrayUtils.find(ClassCode.BOOLEAN, results, true) != -1)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return results;
    }
    
    public int find(short[] arr, short value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.find(this.type, ArrayUtils.wrap(arr), new Short(value));
    }

    public int find(short[] arr, short value, int index, int length)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        if (NumberUtils.isWithinRange(arr.length, index, length) == false)
            throw new IndexOutOfBoundsException("Arr length" + arr.length + " | index = " + index + " | length = " + length);

        return super.find(this.type, ArrayUtils.wrap(arr), new Short(value), index, length);
    }
    
    public int[] findAll(short[] arr, short value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.findAll(this.type, ArrayUtils.wrap(arr), new Short(value));
    }

    public boolean remove(ObjectPointer<short[]> pointer, short value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Short[]> tmpPointer = new ObjectPointer<Short[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.remove(this.type, tmpPointer, new Short(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeAll(ObjectPointer<short[]> pointer, short value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Short[]> tmpPointer = new ObjectPointer<Short[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeAll(this.type, tmpPointer, new Short(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeDuplicates(ObjectPointer<short[]> pointer)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Short[]> tmpPointer = new ObjectPointer<Short[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeDuplicates(this.type, tmpPointer);

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public short[] unwrap(Short[] arr)
    {
        if (arr == null) return null;

        short[] result = new short[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].shortValue();

        return result;
    }

    public Short[] wrap(short[] arr)
    {
        if (arr == null) return null;

        Short[] result = new Short[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Short(arr[i]);

        return result;
    }
}
