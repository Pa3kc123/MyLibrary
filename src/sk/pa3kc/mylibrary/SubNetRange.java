package sk.pa3kc.mylibrary;

public class SubNetRange
{
    private final int mask;

    public SubNetRange(int mask)
    {
        this.mask = mask;
    }
    public SubNetRange(IPAddress mask)
    {
        this.mask = mask.asDecimal();
    }

    public boolean doesInvolve(int ipAddress)
    {
        return Integer.toBinaryString(ipAddress & ~this.mask).length() <= Integer.toBinaryString(~this.mask).length();
    }
    public boolean doesInvolve(IPAddress ipAddress)
    {
        return doesInvolve(ipAddress.asDecimal());
    }

    public static boolean doesInvolve(int ipAddress, int mask)
    {
        return Integer.toBinaryString(ipAddress & ~mask).length() <= Integer.toBinaryString(~mask).length();
    }
    public static boolean doesInvolve(int ipAddress, IPAddress mask)
    {
        return doesInvolve(ipAddress, mask.asDecimal());
    }
    public static boolean doesInvolve(IPAddress ipAddress, int mask)
    {
        return doesInvolve(ipAddress.asDecimal(), mask);
    }
    public static boolean doesInvolve(IPAddress ipAddress, IPAddress mask)
    {
        return doesInvolve(ipAddress.asDecimal(), mask.asDecimal());
    }
}
