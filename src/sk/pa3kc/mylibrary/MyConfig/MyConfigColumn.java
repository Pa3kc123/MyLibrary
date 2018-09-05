package sk.pa3kc.mylibrary.MyConfig;

@SuppressWarnings({ "unused", "WeakerAccess", "UnusedReturnValue" })
public class MyConfigColumn
{
    private String _column = null;

    public String getValue() { return this._column; }
    public Void setValue(String value) { this._column = value; return null; }

    @Override
    public void finalize()
    {
        this._column = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
