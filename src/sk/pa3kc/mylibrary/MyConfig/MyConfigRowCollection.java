package sk.pa3kc.mylibrary.MyConfig;

@SuppressWarnings({ "unused", "WeakerAccess", "UnusedReturnValue" })
public class MyConfigRowCollection
{
    private MyConfigRow[] _rows = null;
    private Short _length = 0;

    public MyConfigRow getRow(Short index) { return this._rows[index]; }
    public MyConfigRow getRow(Integer index) { return this._rows[index]; }
    public Void setRow() { this._rows = null; return null; }
    public Void setRow(Short index, MyConfigRow value) { this._rows[index] = value; return null; }

    public Short getLength() { return this._length; }

    public void Add()
    {
        MyConfigRow[] tmp = this._rows;
        this._rows = new MyConfigRow[++this._length];

        if (tmp != null) System.arraycopy(tmp, 0, this._rows, 0, tmp.length);
        this._rows[this._length - 1] = new MyConfigRow();
    }
    @Override
    public void finalize()
    {
        this._rows = null;
        this._length = null;
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
