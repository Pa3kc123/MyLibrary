package sk.pa3kc.mylibrary.myconfig;

@SuppressWarnings({ "unused", "WeakerAccess", "UnusedReturnValue" })
public class MyConfigColumn
{
    private String _column = null;

    public String getValue() { return this._column; }
    public void setValue(String value) { this._column = value; }

    @Override
    public void finalize()
    {
        this._column = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
