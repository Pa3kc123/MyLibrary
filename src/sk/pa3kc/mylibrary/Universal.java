package sk.pa3kc.mylibrary;

import sk.pa3kc.mylibrary.async.AsyncResult;
import sk.pa3kc.mylibrary.net.Device;

public class Universal
{
    public static void main(String[] args)
    {
        System.out.print(doAsync(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                }
                catch (Throwable ex)
                {
                    ex.printStackTrace();
                }
            }
        }).equals(true) + NEWLINE);

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

    public static AsyncResult doAsync(final Runnable runnable)
    {
        final Object lock = new Object();
        final AsyncResult result = new AsyncResult();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    runnable.run();
                }
                catch (Throwable ex)
                {
                    result.setException(ex);
                    result.setExceptionType(ex.getClass());
                }
                finally
                {
                    synchronized (lock)
                    {
                        lock.notify();
                    }
                }
            }
        }).start();

        synchronized (lock)
        {
            try
            {
                lock.wait();
            }
            catch (Throwable ex)
            {
                ex.printStackTrace();
            }
        }

        return result;
    }
}
