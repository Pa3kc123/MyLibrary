package sk.pa3kc.mylibrary.util.arr;

import sk.pa3kc.mylibrary.enums.ClassCode;
import sk.pa3kc.mylibrary.pojo.ObjectPointer;

class BoolTypeUtils extends ObjectTypeUtils
{
    private Class<Boolean> type = Boolean.class;

    BoolTypeUtils() {}

    public boolean add(ObjectPointer<boolean[]> pointer, boolean value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Boolean[]> tmpPointer = new ObjectPointer<Boolean[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.add(this.type, tmpPointer, new Boolean(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean[] addAll(ObjectPointer<boolean[]> pointer, boolean... values)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (values == null) throw new NullPointerException("values cannot be null");

        ObjectPointer<Boolean[]> tmpPointer = new ObjectPointer<Boolean[]>(ArrayUtils.wrap(pointer.value));

        boolean[] results = super.addAll(this.type, tmpPointer, ArrayUtils.wrap(values));

        if (find(results, true) != -1)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return results;
    }

    public int find(boolean[] arr, boolean value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.find(this.type, ArrayUtils.wrap(arr), new Boolean(value));
    }

    public int find(boolean[] arr, boolean value, int index, int length)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.find(this.type, ArrayUtils.wrap(arr), new Boolean(value), index, length);
    }

    public int[] findAll(boolean[] arr, boolean value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.findAll(this.type, ArrayUtils.wrap(arr), new Boolean(value));
    }

    public boolean remove(ObjectPointer<boolean[]> pointer, boolean value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Boolean[]> tmpPointer = new ObjectPointer<Boolean[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.remove(this.type, tmpPointer, new Boolean(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeAll(ObjectPointer<boolean[]> pointer, boolean value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Boolean[]> tmpPointer = new ObjectPointer<Boolean[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeAll(this.type, tmpPointer, new Boolean(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeDuplicates(ObjectPointer<boolean[]> pointer)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Boolean[]> tmpPointer = new ObjectPointer<Boolean[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeDuplicates(this.type, tmpPointer);

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public Boolean[] wrap(boolean[] arr)
    {
        if (arr == null) return null;

        Boolean[] result = new Boolean[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Boolean(arr[i]);

        return result;
    }
    public boolean[] unwrap(Boolean[] arr)
    {
        if (arr == null) return null;

        boolean[] result = new boolean[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].booleanValue();

        return result;
    }
}
