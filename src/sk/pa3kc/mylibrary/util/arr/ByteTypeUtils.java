package sk.pa3kc.mylibrary.util.arr;

import sk.pa3kc.mylibrary.enums.ClassCode;
import sk.pa3kc.mylibrary.pojo.ObjectPointer;

class ByteTypeUtils extends ObjectTypeUtils
{
    private final Class<Byte> type = Byte.class;

    ByteTypeUtils() {}

    public boolean add(ObjectPointer<byte[]> pointer, byte value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Byte[]> tmpPointer = new ObjectPointer<Byte[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.add(this.type, tmpPointer, new Byte(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean[] addAll(ObjectPointer<byte[]> pointer, byte... values)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");
        if (values == null) throw new NullPointerException("values cannot be null");

        ObjectPointer<Byte[]> tmpPointer = new ObjectPointer<Byte[]>(ArrayUtils.wrap(pointer.value));

        boolean[] results = super.addAll(this.type, tmpPointer, ArrayUtils.wrap(values));

        if (ArrayUtils.find(ClassCode.BOOLEAN, results, true) == -1)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return results;
    }

    public int find(byte[] arr, byte value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.find(this.type, ArrayUtils.wrap(arr), new Byte(value));
    }

    public int find(byte[] arr, byte value, int index, int length)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.find(this.type, ArrayUtils.wrap(arr), new Byte(value), index, length);
    }

    public int[] findAll(byte[] arr, byte value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.findAll(this.type, ArrayUtils.wrap(arr), new Byte(value));
    }

    public boolean remove(ObjectPointer<byte[]> pointer, byte value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Byte[]> tmpPointer = new ObjectPointer<Byte[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.remove(this.type, tmpPointer, new Byte(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeAll(ObjectPointer<byte[]> pointer, byte value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Byte[]> tmpPointer = new ObjectPointer<Byte[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeAll(this.type, tmpPointer, new Byte(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeDuplicates(ObjectPointer<byte[]> pointer)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Byte[]> tmpPointer = new ObjectPointer<Byte[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeDuplicates(this.type, tmpPointer);

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public byte[] unwrap(Byte[] arr)
    {
        if (arr == null) return null;

        byte[] result = new byte[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].byteValue();

        return result;
    }

    public Byte[] wrap(byte[] arr)
    {
        if (arr == null) return null;

        Byte[] result = new Byte[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Byte(arr[i]);

        return result;
    }
}
