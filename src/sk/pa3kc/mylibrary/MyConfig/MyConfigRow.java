package sk.pa3kc.mylibrary.MyConfig;

@SuppressWarnings ({ "unused", "WeakerAccess" })
public class MyConfigRow
{
    private MyConfigColumnCollection _columnCollection = new MyConfigColumnCollection();

    public MyConfigColumnCollection getColumnCollection() { return this._columnCollection; }
    public Void setColumn(MyConfigColumnCollection value) { this._columnCollection = value; return null; }
    public Short getLength() { return this._columnCollection.getLength(); }

    @Override
    public void finalize()
    {
        this._columnCollection = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}