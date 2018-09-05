package sk.pa3kc.mylibrary.MyConfig;

@SuppressWarnings({ "unused", "WeakerAccess", "UnusedReturnValue" })
public class MyConfigCategory
{
    private MyConfigRowCollection _rowCollection = new MyConfigRowCollection();
    private String _name = null;

    public MyConfigRowCollection getRowCollection() { return this._rowCollection; }

    public Void setRow(MyConfigRowCollection value) { this._rowCollection = value; return null; }
    public String getName() { return this._name; }
    public Void setName(String value) { this._name = value; return null; }

    @Override
    public void finalize()
    {
        this._rowCollection = null;
        this._name = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
