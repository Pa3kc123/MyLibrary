package sk.pa3kc.mylibrary;

@SuppressWarnings ({ "StringConcatenationInLoop", "unused", "WeakerAccess" })
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

    public int AsDecimal() { return this.binaryAddress; }
    public byte[] AsByteArray() { return this.byteAddress; }
    public String AsDecimalString() { return String.valueOf(this.binaryAddress); }
    public String AsBinaryString() { return Integer.toBinaryString(this.binaryAddress); }
    public String AsFormattedString()
    {
        String result = "";
        int[] val = new int[4];

        for (int i = 3; i >= 0; i--) val[i] = (this.binaryAddress >>> 8 * (3 - i)) & 0b11111111;
        for (int i = 0; i < 4; i++) result = result + String.valueOf(val[i]) + (i != 3 ? "." : "");

        return result;
    }
}
