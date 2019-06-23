package sk.pa3kc.mylibrary.util.arr;

import sk.pa3kc.mylibrary.enums.ClassCode;
import sk.pa3kc.mylibrary.pojo.ObjectPointer;
import sk.pa3kc.mylibrary.util.NumberUtils;

class FloatTypeUtils extends ObjectTypeUtils
{
    private final Class<Float> type = Float.class;
    
    FloatTypeUtils() {}
    
    public boolean add(ObjectPointer<float[]> pointer, float value)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");

        ObjectPointer<Float[]> tmpPointer = new ObjectPointer<Float[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.add(this.type, tmpPointer, new Float(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean[] addAll(ObjectPointer<float[]> pointer, float... values)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");
        if (values == null) return new boolean[] { false };

        ObjectPointer<Float[]> tmpPointer = new ObjectPointer<Float[]>(ArrayUtils.wrap(pointer.value));

        boolean[] results = super.addAll(Float.class, tmpPointer, ArrayUtils.wrap(values));

        if (ArrayUtils.find(ClassCode.BOOLEAN, results, true) != -1)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return results;
    }
    
    public int find(float[] arr, float value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.find(this.type, ArrayUtils.wrap(arr), new Float(value));
    }

    public int find(float[] arr, float value, int index, int length)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        if (NumberUtils.isWithinRange(arr.length, index, length) == false)
            throw new IndexOutOfBoundsException("Arr length" + arr.length + " | index = " + index + " | length = " + length);

        return super.find(this.type, ArrayUtils.wrap(arr), new Float(value), index, length);
    }
    
    public int[] findAll(float[] arr, float value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.findAll(this.type, ArrayUtils.wrap(arr), new Float(value));
    }

    public boolean remove(ObjectPointer<float[]> pointer, float value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Float[]> tmpPointer = new ObjectPointer<Float[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.remove(this.type, tmpPointer, new Float(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeAll(ObjectPointer<float[]> pointer, float value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Float[]> tmpPointer = new ObjectPointer<Float[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeAll(this.type, tmpPointer, new Float(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeDuplicates(ObjectPointer<float[]> pointer)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Float[]> tmpPointer = new ObjectPointer<Float[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeDuplicates(this.type, tmpPointer);

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public float[] unwrap(Float[] arr)
    {
        if (arr == null) return null;

        float[] result = new float[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].floatValue();

        return result;
    }

    public Float[] wrap(float[] arr)
    {
        if (arr == null) return null;

        Float[] result = new Float[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Float(arr[i]);

        return result;
    }
}
