package sk.pa3kc.mylibrary.myconfig;

@SuppressWarnings({ "unused", "WeakerAccess", "UnusedReturnValue" })
public class MyConfigRowCollection
{
    private MyConfigRow[] _rows = null;
    private int _length = 0;

    public MyConfigRow getRow(int index) { return this._rows[index]; }
    public void setRow(int index, MyConfigRow value) { this._rows[index] = value; }

    public int getLength() { return this._length; }

    public void addRow()
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
        try { super.finalize(); } catch (Throwable ex) { ex.printStackTrace(System.out); }
    }
}
