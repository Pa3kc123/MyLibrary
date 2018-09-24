package sk.pa3kc.mylibrary.myconfig;

@SuppressWarnings ({ "WeakerAccess", "unused" })
public class MyConfigCategoryCollection
{
    private MyConfigCategory[] _categories = null;
    private int _length = 0;

    public MyConfigCategory getCategory(int index) { return this._categories[index]; }
    public void setCategory(int index, MyConfigCategory value) { this._categories[index] = value; }

    public int getLength() { return this._length; }

    public void addCategory()
    {
        MyConfigCategory[] tmp = this._categories;
        this._categories = new MyConfigCategory[++this._length];

        if (tmp != null) System.arraycopy(tmp, 0, this._categories, 0, tmp.length);
        this._categories[this._length - 1] = new MyConfigCategory();
    }

    @Override
    public void finalize()
    {
        this._categories = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
