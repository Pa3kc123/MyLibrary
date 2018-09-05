package sk.pa3kc.mylibrary.MyConfig;

@SuppressWarnings ({ "WeakerAccess", "unused" })
public class MyConfigCategoryCollection
{
    private MyConfigCategory[] _categories = null;
    private Short _length = 0;

    public MyConfigCategory getCategory(Short index) { return this._categories[index]; }
    public MyConfigCategory getCategory(Integer index) { return this._categories[index]; }
    public Void setCategory(Short index, MyConfigCategory value) { this._categories[index] = value; return null; }
    public Short getLength() { return this._length; }

    public void Add()
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
        this._length = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
