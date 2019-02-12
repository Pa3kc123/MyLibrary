package sk.pa3kc.mylibrary;

import sk.pa3kc.mylibrary.net.Device;

public class Universal
{
    public static void main(String[] args)
    {
        try
        {
            Device[] devices = Device.getUsableDevices();
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
}
