package sk.pa3kc.mylibrary.myconfig;

@SuppressWarnings({ "unused", "WeakerAccess", "UnusedReturnValue" })
public class MyConfigCategory
{
    private MyConfigRowCollection _rowCollection = new MyConfigRowCollection();
    private String _name = null;

    public MyConfigRowCollection getRowCollection() { return this._rowCollection; }
    public void setRowCollection(MyConfigRowCollection value) { this._rowCollection = value; }
    public String getName() { return this._name; }
    public void setName(String value) { this._name = value; }

    @Override
    public void finalize()
    {
        this._rowCollection = null;
        this._name = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
