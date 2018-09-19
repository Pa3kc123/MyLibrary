package sk.pa3kc.mylibrary;

@SuppressWarnings ({ "WeakerAccess", "unused", "PointlessBooleanExpression" })
public class Universal
{
    public static void main(String[] args)
    {
        try
        {
            Device[] devices = getUsableDevices();
            assert devices != null;
            for (Device device : devices)
            {
                System.out.print("Device name = " + device.getDeviceName() + "\n");
                System.out.print("Device IP addresses:\n");
                System.out.print("Network = " + device.getNetworkIP().AsFormattedString() + "\n");
                System.out.print("Local = " + device.getLocalIP().AsFormattedString() + "\n");
                System.out.print("Broadcast = " + device.getBroadcastIP().AsFormattedString() + "\n");
                System.out.print("Test of subnet range = " + (device.getSubNetRange().doesInvolve(device.getLocalIP()) == true ? "PASSED" : "FAILED") + "\n");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace(System.out);
        }
    }

    public static Device[] getUsableDevices()
    {
        java.util.List<Device> devices = new java.util.ArrayList<>();

        try
        {
            for (java.net.NetworkInterface device : java.util.Collections.list(java.net.NetworkInterface.getNetworkInterfaces()))
            {
                if (device.isVirtual() == true || device.isUp() == false) continue;

                for (java.net.InetAddress address : java.util.Collections.list(device.getInetAddresses()))
                {
                    if (address.isLoopbackAddress() == false && address instanceof java.net.Inet6Address == false)
                        devices.add(new Device(device, (java.net.Inet4Address) address));
                }
            }
        }
        catch (Throwable ex)
        {
            ex.printStackTrace(System.out);
        }

        return devices.size() != 0 ? devices.toArray(new Device[0]) : null;
    }
}
