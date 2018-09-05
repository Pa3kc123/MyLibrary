package sk.pa3kc.mylibrary;

@SuppressWarnings ({ "PointlessBooleanExpression", "WeakerAccess", "unused" })
public class Device
{
    private IPAddress localIP;
    private IPAddress networkIP;
    private IPAddress broadcastIP;
    private IPAddress mask;
    private String deviceName;
    private java.util.List<IPAddress> addresses = new java.util.ArrayList<>();

    public Device(java.net.NetworkInterface device, java.net.Inet4Address localAddress)
    {
        this.deviceName = device.getName();

        int tmp = 0;

        for (int i = 0; i < 4; i++)
        {
            tmp |= (localAddress.getAddress()[i] & 0b11111111) << 8 * (4 - (i+1));
        }

        this.localIP = new IPAddress(tmp);
        tmp = 0;

        for (java.net.InterfaceAddress address : device.getInterfaceAddresses())
        {
            if (address.getAddress() instanceof java.net.Inet4Address == true)
                for (int i = 0; i < address.getNetworkPrefixLength(); i++) tmp |= 1 << 31 - i;
        }

        this.mask = new IPAddress(tmp);

        this.networkIP = new IPAddress(this.localIP.AsDecimal() & this.mask.AsDecimal());
        this.broadcastIP = new IPAddress(this.localIP.AsDecimal() | ~(this.mask.AsDecimal()));

        for (int i = this.networkIP.AsDecimal() + 1; i < this.broadcastIP.AsDecimal(); i++)
        {
            if (i == this.localIP.AsDecimal()) continue;
            this.addresses.add(new IPAddress(i));
        }
    }

    public IPAddress getLocalIP() { return this.localIP; }
    public IPAddress getBroadcastIP() { return this.broadcastIP; }
    public IPAddress getNetworkIP() { return this.networkIP; }
    public IPAddress getMask() { return this.mask; }
    public IPAddress[] getAddresses() { return this.addresses.toArray(new IPAddress[0]); }
    public int getAddressesCount() { return this.addresses.size(); }
    public String getDeviceName() { return this.deviceName; }
}
