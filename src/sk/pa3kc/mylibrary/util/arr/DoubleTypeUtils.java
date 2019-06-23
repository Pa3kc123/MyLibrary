package sk.pa3kc.mylibrary.util.arr;

import sk.pa3kc.mylibrary.enums.ClassCode;
import sk.pa3kc.mylibrary.pojo.ObjectPointer;
import sk.pa3kc.mylibrary.util.NumberUtils;

class DoubleTypeUtils extends ObjectTypeUtils
{
    private final Class<Double> type = Double.class;
    
    DoubleTypeUtils() {}
    
    public boolean add(ObjectPointer<double[]> pointer, double value)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");

        ObjectPointer<Double[]> tmpPointer = new ObjectPointer<Double[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.add(this.type, tmpPointer, new Double(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean[] addAll(ObjectPointer<double[]> pointer, double... values)
    {
        if (pointer == null) throw new IllegalArgumentException("pointer cannot be null");
        if (values == null) return new boolean[] { false };

        ObjectPointer<Double[]> tmpPointer = new ObjectPointer<Double[]>(ArrayUtils.wrap(pointer.value));

        boolean[] results = super.addAll(Double.class, tmpPointer, ArrayUtils.wrap(values));

        if (ArrayUtils.find(ClassCode.BOOLEAN, results, true) != -1)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return results;
    }
    
    public int find(double[] arr, double value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.find(this.type, ArrayUtils.wrap(arr), new Double(value));
    }

    public int find(double[] arr, double value, int index, int length)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        if (NumberUtils.isWithinRange(arr.length, index, length) == false)
            throw new IndexOutOfBoundsException("Arr length" + arr.length + " | index = " + index + " | length = " + length);

        return super.find(this.type, ArrayUtils.wrap(arr), new Double(value), index, length);
    }
    
    public int[] findAll(double[] arr, double value)
    {
        if (arr == null) throw new NullPointerException("arr cannot be null");

        return super.findAll(this.type, ArrayUtils.wrap(arr), new Double(value));
    }

    public boolean remove(ObjectPointer<double[]> pointer, double value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Double[]> tmpPointer = new ObjectPointer<Double[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.remove(this.type, tmpPointer, new Double(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeAll(ObjectPointer<double[]> pointer, double value)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Double[]> tmpPointer = new ObjectPointer<Double[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeAll(this.type, tmpPointer, new Double(value));

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public boolean removeDuplicates(ObjectPointer<double[]> pointer)
    {
        if (pointer == null) throw new NullPointerException("pointer cannot be null");
        if (pointer.value == null) throw new NullPointerException("value inside of pointer cannot be null");

        ObjectPointer<Double[]> tmpPointer = new ObjectPointer<Double[]>(ArrayUtils.wrap(pointer.value));

        boolean result = super.removeDuplicates(this.type, tmpPointer);

        if (result == true)
            pointer.value = ArrayUtils.unwrap(tmpPointer.value);

        return result;
    }

    public double[] unwrap(Double[] arr)
    {
        if (arr == null) return null;

        double[] result = new double[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i].doubleValue();

        return result;
    }

    public Double[] wrap(double[] arr)
    {
        if (arr == null) return null;

        Double[] result = new Double[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = new Double(arr[i]);

        return result;
    }
}
