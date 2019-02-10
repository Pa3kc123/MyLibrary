package sk.pa3kc.mylibrary;

import java.io.Closeable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Universal
{
    public static void main(String[] args)
    {
        try
        {
            Device[] devices = getUsableDevices();
            String NEWLINE = DefaultSystemPropertyStrings.LINE_SEPARATOR;
            for (Device device : devices)
            {
                System.out.print("Device name = " + device.getDeviceName() + NEWLINE);
                System.out.print("Device IP addresses:" + NEWLINE);
                System.out.print("Local = " + device.getLocalIP().asFormattedString() + NEWLINE);
                if (device.isOnlyLoopback() == false)
                {
                    System.out.print("Network = " + device.getNetworkIP().asFormattedString() + NEWLINE);
                    System.out.print("Broadcast = " + device.getBroadcastIP().asFormattedString() + NEWLINE);
                    System.out.print("Test of subnet range = " + (device.getSubNetRange().doesInvolve(device.getLocalIP()) == true ? "PASSED" : "FAILED") + NEWLINE);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace(System.out);
        }
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
                    if (netAddress.isLoopbackAddress() == false && netAddress instanceof Inet6Address == false)
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
        else
        {
            return usableDevices.toArray(new Device[0]);
        }
    }

    public static void closeStreams(Closeable... streams)
    {
        closeStreams(null, streams);
    }
    public static void closeStreams(AutoCloseable... encoders)
    {
        closeStreams(encoders, null);
    }
    public static void closeStreams(AutoCloseable[] encoders, Closeable[] streams)
    {
        if (encoders != null)
        {
            for (AutoCloseable encoder : encoders)
            {
                try
                {
                    encoder.close();
                }
                catch (Throwable ex)
                {
                    ex.printStackTrace();
                }
            }
        }

        if (streams != null)
        {
            for (Closeable stream : streams)
            {
                try
                {
                    stream.close();
                }
                catch (Throwable ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
