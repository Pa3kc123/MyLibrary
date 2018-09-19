package sk.pa3kc.mylibrary.myconfig;

@SuppressWarnings ({ "unused", "WeakerAccess", "UnusedReturnValue" })
public class MyConfigColumnCollection
{
    private MyConfigColumn[] _columns = new MyConfigColumn[] { new MyConfigColumn(), new MyConfigColumn() };

    public MyConfigColumn getColumn(int index) { return this._columns[index]; }
    public void setColumn(int index, MyConfigColumn value) { this._columns[index] = value; }

    public int getLength() { return 2; }

    @Override
    public void finalize()
    {
        this._columns = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
