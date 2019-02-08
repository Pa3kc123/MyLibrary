package sk.pa3kc.mylibrary;

import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

public class Device
{
    private final IPAddress localIP;
    private final IPAddress networkIP;
    private final IPAddress broadcastIP;
    private final IPAddress mask;
    private final String deviceName;
    private final List<IPAddress> addresses = new ArrayList<IPAddress>();
    private final SubNetRange subNetRange;
    
    public Device(NetworkInterface device, Inet4Address localAddress)
    {
        this.deviceName = device.getName();

        int tmp = 0;

        {
            byte[] addresses = localAddress.getAddress();
            for (int i = 0; i < 4; i++)
                tmp |= (addresses[i] & 0x000000FF) << 8 * (3 - i);;
        }

        this.localIP = new IPAddress(tmp);
        tmp = 0;

        for (InterfaceAddress address : device.getInterfaceAddresses())
        if (address.getAddress() instanceof Inet4Address == true)
        for (int i = 0; i < address.getNetworkPrefixLength(); i++)
            tmp |= 1 << 31 - i;

        this.mask = new IPAddress(tmp);

        this.networkIP = new IPAddress(this.localIP.asDecimal() & this.mask.asDecimal());
        this.broadcastIP = new IPAddress(this.localIP.asDecimal() | ~(this.mask.asDecimal()));

        for (int i = this.networkIP.asDecimal() + 1; i < this.broadcastIP.asDecimal(); i++)
            if (i != this.localIP.asDecimal())
            	this.addresses.add(new IPAddress(i));

        this.subNetRange = new SubNetRange(this.mask);
    }

    public final IPAddress getLocalIP() { return this.localIP; }
    public final IPAddress getBroadcastIP() { return this.broadcastIP; }
    public final IPAddress getNetworkIP() { return this.networkIP; }
    public final IPAddress getMask() { return this.mask; }
    public final IPAddress[] getAddresses() { return this.addresses.toArray(new IPAddress[0]); }
    public final int getAddressesCount() { return this.addresses.size(); }
    public final String getDeviceName() { return this.deviceName; }
    public final SubNetRange getSubNetRange() { return this.subNetRange; }
}
