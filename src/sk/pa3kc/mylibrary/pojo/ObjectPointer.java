package sk.pa3kc.mylibrary.pojo;

public class ObjectPointer<T>
{
    public T value;

    public ObjectPointer(T value)
    {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public <E> ObjectPointer<E> as(Class<E> type) throws ClassCastException
    {
        return new ObjectPointer<E>((E)value);
    }

    @Override
    public String toString() { return this.value.toString(); }
}
