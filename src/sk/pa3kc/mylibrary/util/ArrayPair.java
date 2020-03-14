package sk.pa3kc.mylibrary.util;

import java.util.Iterator;

public class ArrayPair<T> implements Iterable<T> {
    private final T[] arr1;
    private final T[] arr2;
    private int index = 0;

    public ArrayPair(T[] arr1, T[] arr2) {
        this.arr1 = arr1;
        this.arr2 = arr2;
    }
    @SuppressWarnings("unchecked")
    public ArrayPair(T[] arr1, T[] arr2, boolean equalInLength) {
        if (equalInLength) {
            int size = arr1.length > arr2.length ? arr1.length : arr2.length;

            this.arr1 = (T[]) new Object[size];
            this.arr2 = (T[]) new Object[size];

            for (int i = 0; i < size; i++) {
                this.arr1[i] = i < arr1.length ? arr1[i] : null;
                this.arr2[i] = i < arr2.length ? arr2[i] : null;
            }
        } else {
            this.arr1 = arr1;
            this.arr2 = arr2;
        }
    }

    public void setIndex(int value) throws IndexOutOfBoundsException {
        if (value < 0 || value == arr1.length || value == arr2.length) {
            throw new IndexOutOfBoundsException("Index was set to " + value);
        }
        this.index = value;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return (index >= arr1.length || index >= arr2.length) == false;
            }

            @Override
            public T next() {
                return arr1[index++];
            }

            @Override
            public void remove() {}
        };
    }
}
