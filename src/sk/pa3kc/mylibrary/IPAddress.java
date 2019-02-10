package sk.pa3kc.mylibrary;

public class IPAddress
{
    private int binaryAddress;
    private byte[] byteAddress;

    public IPAddress(int value)
    {
        this.binaryAddress = value;
        this.byteAddress = new byte[4];

        for (int i = 3; i >= 0; i--)
            this.byteAddress[i] = (byte)(this.binaryAddress >>> 8 * (3 - i));
    }
    public IPAddress(byte[] value)
    {
        this.binaryAddress = 0;
        this.byteAddress = value;

        for (int i = 0; i < 4; i++)
            this.binaryAddress |= (value[i] & 0x000000FF) << 8 * (3 - i);
    }

    private int byteToInt(byte value)
    {
        return value > 0 ? value : value + 255;
    }

    public final int asDecimal() { return this.binaryAddress; }
    public final byte[] asByteArray() { return this.byteAddress; }
    public final String asDecimalString() { return String.valueOf(this.binaryAddress); }
    public final String asBinaryString() { return Integer.toBinaryString(this.binaryAddress); }
    public final String asFormattedString()
    {
        StringBuilder result = new StringBuilder();
        int[] val = new int[4];

        for (int i = 0; i < val.length; i++)
            val[i] = (this.binaryAddress << 24 - (8 * (3 - i))) >>> 24;

        for (int i = 0; i < 4; i++)
            result.append(String.valueOf(val[i])).append(i != 3 ? "." : "");

        return result.toString();
    }
}
