package sk.pa3kc.mylibrary;

@SuppressWarnings ({ "WeakerAccess", "unused" })
public class IPAddress
{
    private int binaryAddress;
    private byte[] byteAddress;

    public IPAddress(int value)
    {
        this.binaryAddress = value;
        this.byteAddress = new byte[4];

        for (int i = 3; i >= 0; i--) this.byteAddress[i] = (byte)((this.binaryAddress >>> 8 * (3 - i)) & 0b11111111);
    }
    public IPAddress(byte[] value)
    {
        this.binaryAddress = 0;
        this.byteAddress = value;

        for (int i = 0; i < 4; i++) this.binaryAddress |= ByteToInt(value[i]) & 0b11111111;
    }

    private int ByteToInt(byte value)
    {
        return value > 0 ? value : value + 255;
    }

    public final int AsDecimal() { return this.binaryAddress; }
    public final byte[] AsByteArray() { return this.byteAddress; }
    public final String AsDecimalString() { return String.valueOf(this.binaryAddress); }
    public final String AsBinaryString() { return Integer.toBinaryString(this.binaryAddress); }
    public final String AsFormattedString()
    {
        StringBuilder result = new StringBuilder();
        int[] val = new int[4];

        for (int i = 3; i >= 0; i--) val[i] = (this.binaryAddress >>> 8 * (3 - i)) & 0b11111111;
        for (int i = 0; i < 4; i++) result.append(String.valueOf(val[i])).append(i != 3 ? "." : "");

        return result.toString();
    }
}
