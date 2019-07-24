package sk.pa3kc.mylibrary;

import sk.pa3kc.mylibrary.cmd.CmdColor;
import sk.pa3kc.mylibrary.cmd.CmdUtils;
import sk.pa3kc.mylibrary.net.Device;

public class Universal {
    public static void main(String[] args) {
        final String NEWLINE = DefaultSystemPropertyStrings.LINE_SEPARATOR;

        CmdUtils.setColor(CmdColor.RED);
        System.out.print("This text should be red" + NEWLINE);
        CmdUtils.setColor(CmdColor.GREEN);
        System.out.print("This text should be green" + NEWLINE);
        CmdUtils.setColor(CmdColor.BLUE);
        System.out.print("This text should be blue" + NEWLINE);
        CmdUtils.resetColor();

        try {
            final Device[] devices = Device.getUsableDevices();

            for (Device device : devices) {
                System.out.print("Device name = " + device.getDeviceName() + NEWLINE);
                System.out.print("Device IP addresses:" + NEWLINE);
                System.out.print("Local = " + device.getLocalIP().asFormattedString() + NEWLINE);
                if (device.isOnlyLoopback() == false) {
                    System.out.print("Network = " + device.getNetworkIP().asFormattedString() + NEWLINE);
                    System.out.print("Broadcast = " + device.getBroadcastIP().asFormattedString() + NEWLINE);
                    System.out.print("Test of subnet range = " + (device.getSubNetRange().doesInvolve(device.getLocalIP()) == true ? "PASSED" : "FAILED") + NEWLINE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Returns the value of the {@code long} argument;
     * throwing an exception if the value overflows an {@code int}.
     *
     * @param value the long value
     * @return the argument as an int
     * @throws ArithmeticException if the {@code argument} overflows an int
     * @since 1.6
     */
    public static int toIntExact(long value) throws ArithmeticException {
        if ((int)value != value)
            throw new ArithmeticException("integer overflow");
        else return (int)value;
    }

    /**
     * Returns lowest value from passed values
     * @param values Values to compare
     * @return Lowest value from array, -1 if array.length is 0
     */
    public static int min(int... values) {
        if (values.length == 0) return -1;

        int result = 0x7FFFFFFF;
        for (int value : values)
            result = result > value ? value : result;

        return result;
    }

    /**
     * Returns highest value from passed values
     * @param values Values to compare
     * @return Highest value from array, -1 if array.length is 0
     */
    public static int max(int... values) {
        if (values.length == 0) return -1;

        int result = 0x80000000;
        for (int value : values)
            result = result < value ? value : result;

        return result;
    }

    public static float map(float val, float sourceMinRange, float sourceMaxRange, float targetMinRange, float targetMaxRange) throws ArithmeticException {
        if (sourceMinRange > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< sourceMaxRange");
        if (val < sourceMinRange || val > sourceMaxRange) throw new ArithmeticException("sourceMinRange !< val !< sourceMaxRange");
        return ((val - sourceMinRange) / (sourceMaxRange - sourceMinRange)) * (targetMinRange - targetMaxRange) + targetMinRange;
    }
}
