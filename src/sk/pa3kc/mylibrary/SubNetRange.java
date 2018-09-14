package sk.pa3kc.mylibrary;

public class SubNetRange
{
    private int firstIPAddress;
    private int lastIPAddress;

    public SubNetRange(int firstIPAddress, int lastIPAddress)
    {
        this.firstIPAddress = firstIPAddress & 0b11111111;
        this.lastIPAddress = lastIPAddress & 0b11111111;
    }

    boolean involve(int IPAddress)
    {
        return (IPAddress & 0b11111111) > this.firstIPAddress && (IPAddress & 0b11111111) < lastIPAddress;
    }
}
