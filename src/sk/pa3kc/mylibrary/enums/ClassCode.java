package sk.pa3kc.mylibrary.enums;

public enum ClassCode
{
    OBJECT(0xFF),
    BOOLEAN(1),
    BYTE(2),
    CHAR(3),
    SHORT(4),
    INT(5),
    LONG(6),
    FLOAT(7),
    DOUBLE(8);

    public final Class<?> type;
    private ClassCode(int code)
    {
        switch (code)
        {
            case 1: this.type = boolean.class; break;
            case 2: this.type = byte.class; break;
            case 3: this.type = char.class; break;
            case 4: this.type = short.class; break;
            case 5: this.type = int.class; break;
            case 6: this.type = long.class; break;
            case 7: this.type = float.class; break;
            case 8: this.type = double.class; break;
            default: this.type = Object.class; break;
        }
    }
}
