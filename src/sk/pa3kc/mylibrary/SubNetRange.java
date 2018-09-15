package sk.pa3kc.mylibrary;

@SuppressWarnings ({ "WeakerAccess", "unused" })
public class SubNetRange
{
    private final int mask;

    public SubNetRange(int mask)
    {
        this.mask = mask;
    }
    public SubNetRange(IPAddress mask)
    {
        this.mask = mask.AsDecimal();
    }

    public boolean doesInvolve(int ipAddress)
    {
        return Integer.toBinaryString(ipAddress & ~this.mask).length() <= Integer.toBinaryString(~this.mask).length();
    }
    public boolean doesInvolve(IPAddress ipAddress)
    {
        return doesInvolve(ipAddress.AsDecimal());
    }

    public static boolean doesInvolve(int ipAddress, int mask)
    {
        return Integer.toBinaryString(ipAddress & ~mask).length() <= Integer.toBinaryString(~mask).length();
    }
    public static boolean doesInvolve(int ipAddress, IPAddress mask)
    {
        return doesInvolve(ipAddress, mask.AsDecimal());
    }
    public static boolean doesInvolve(IPAddress ipAddress, int mask)
    {
        return doesInvolve(ipAddress.AsDecimal(), mask);
    }
    public static boolean doesInvolve(IPAddress ipAddress, IPAddress mask)
    {
        return doesInvolve(ipAddress.AsDecimal(), mask.AsDecimal());
    }
}
