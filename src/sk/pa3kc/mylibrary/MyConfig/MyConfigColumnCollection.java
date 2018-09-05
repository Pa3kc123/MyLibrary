package sk.pa3kc.mylibrary.MyConfig;

@SuppressWarnings ({ "unused", "WeakerAccess", "UnusedReturnValue" })
public class MyConfigColumnCollection
{
    private MyConfigColumn[] _columns = new MyConfigColumn[] { new MyConfigColumn(), new MyConfigColumn() };

    public MyConfigColumn getColumn(Short index) { return this._columns[index]; }
    public MyConfigColumn getColumn(Integer index) { return this._columns[index]; }
    public Void setColumn(Short index, MyConfigColumn value) { this._columns[index] = value; return null; }
    public Short getLength() { return 2; }

    @Override
    public void finalize()
    {
        this._columns = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
