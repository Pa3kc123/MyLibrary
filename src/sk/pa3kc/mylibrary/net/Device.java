package sk.pa3kc.mylibrary.net;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
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
    private final boolean onlyLoopback;

    private Device(NetworkInterface netInterface)
    {
        this.onlyLoopback = true;
        this.deviceName = netInterface.getName();

        IPAddress tmp;

        try
        {
            tmp = new IPAddress(new byte[] { 127, 0, 0, 1 });
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
            tmp = null;
        }

        this.localIP = tmp;
        this.networkIP = null;
        this.broadcastIP = null;
        this.mask = null;
        this.subNetRange = null;
    }

    public Device(NetworkInterface netInterface, Inet4Address localAddress)
    {
        this.onlyLoopback = false;
        this.deviceName = netInterface.getName();

        {
            int tmp = 0;
            byte[] addresses = localAddress.getAddress();
            for (int i = 0; i < 4; i++)
                tmp |= (addresses[i] & 0x000000FF) << 8 * (3 - i);;

            this.localIP = new IPAddress(tmp);
        }

        this.mask = getMask(netInterface);
        this.networkIP = new IPAddress(this.localIP.asDecimal() & this.mask.asDecimal());
        this.broadcastIP = new IPAddress(this.localIP.asDecimal() | ~(this.mask.asDecimal()));

        for (int i = this.networkIP.asDecimal() + 1; i < this.broadcastIP.asDecimal(); i++)
        if (i != this.localIP.asDecimal())
            this.addresses.add(new IPAddress(i));

        this.subNetRange = new SubNetRange(this.mask);
    }

    public static Device onlyLocalHost(NetworkInterface netInterface)
    {
        return new Device(netInterface);
    }

    public final IPAddress getLocalIP() { return this.localIP; }
    public final IPAddress getBroadcastIP() { return this.broadcastIP; }
    public final IPAddress getNetworkIP() { return this.networkIP; }
    public final IPAddress getMask() { return this.mask; }
    public final IPAddress[] getAddresses() { return this.addresses.toArray(new IPAddress[0]); }
    public final int getAddressesCount() { return this.addresses.size(); }
    public final String getDeviceName() { return this.deviceName; }
    public final SubNetRange getSubNetRange() { return this.subNetRange; }
    public final boolean isOnlyLoopback() { return this.onlyLoopback; }

    private IPAddress getMask(NetworkInterface netInterface)
    {
        int mask = 0;

        for (InterfaceAddress address : netInterface.getInterfaceAddresses())
        if (address.getAddress() instanceof Inet4Address == true)
        for (int i = 0; i < address.getNetworkPrefixLength(); i++)
            mask |= 1 << 31 - i;

        return new IPAddress(mask);
    }

    public static Device[] getUsableDevices()
    {
        List<NetworkInterface> interfaces = new ArrayList<NetworkInterface>();
        List<Device> usableDevices = new ArrayList<Device>();

        try
        {
            Enumeration<NetworkInterface> netInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (netInterfaceEnumeration.hasMoreElements() == true)
            {
                NetworkInterface netInterface = netInterfaceEnumeration.nextElement();

                if (netInterface.isVirtual() == true || netInterface.isUp() == false) continue;

                interfaces.add(netInterface);

                Enumeration<InetAddress> netAddressEnumeration = netInterface.getInetAddresses();
                while (netAddressEnumeration.hasMoreElements() == true)
                {
                    InetAddress netAddress = netAddressEnumeration.nextElement();
                    if (netAddress.isLoopbackAddress() == false && netAddress.getClass() != Inet6Address.class)
                        usableDevices.add(new Device(netInterface, (Inet4Address)netAddress));
                }
            }
        }
        catch (Throwable ex)
        {
            ex.printStackTrace(System.out);
        }

        if (usableDevices.size() == 0)
        {
            Device[] devices = new Device[interfaces.size()];

            for (int i = 0; i < devices.length; i++)
                devices[i] = Device.onlyLocalHost(interfaces.get(i));

            return devices;
        }
        
        return usableDevices.toArray(new Device[0]);
    }
}
