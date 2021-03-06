package sk.pa3kc.mylibrary;

import sk.pa3kc.mylibrary.cmd.CmdColor;
import sk.pa3kc.mylibrary.cmd.CmdUtils;
import sk.pa3kc.mylibrary.json.JsonParser;
import sk.pa3kc.mylibrary.net.Device;
import sk.pa3kc.mylibrary.utils.ArgsParser;
import sk.pa3kc.mylibrary.utils.NumberUtils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class MyLibrary {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        final ArgsParser parser = new ArgsParser(args);

        for (String arg : parser.getAllArgs()) {
            System.out.println(arg);
        }

        for (String key : parser.getAllOptions().keySet()) {
            System.out.println(key + " = " + parser.getAllOptions().get(key));
        }

        CmdUtils.setColor(CmdColor.RED);
        System.out.println("This text should be red");
        CmdUtils.setColor(CmdColor.GREEN);
        System.out.println("This text should be green");
        CmdUtils.setColor(CmdColor.BLUE);
        System.out.println("This text should be blue");
        CmdUtils.resetColor();

        CmdUtils.setForegroundRGB(255, 0, 0);
        System.out.println("This text has RGB #F00");
        CmdUtils.resetColor();

        System.out.println(NumberUtils.round(0.12345d, 3));

        try {
            final Device[] devices = Device.getUsableDevices();

            for (Device device : devices) {
                System.out.println("Device name = " + device.getDeviceName());
                System.out.println("Device IP addresses:");
                System.out.println("Local = " + device.getLocalIP().asFormattedString());
                if (!device.isOnlyLoopback()) {
                    System.out.println("Network = " + device.getNetworkIP().asFormattedString());
                    System.out.println("Broadcast = " + device.getBroadcastIP().asFormattedString());
                    System.out.println("Test of subnet range = " + (device.getSubNetRange().doesInvolve(device.getLocalIP()) ? "PASSED" : "FAILED"));
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
        }

        Map<String, Object> json = null;
        if (args.length != 0) {
            final String val;
            InputStreamReader isr = null;

            try {
                final char[] buffer = new char[4096];
                isr = new InputStreamReader(new FileInputStream(parser.getArg(0)), "utf-8");

                final StringBuilder builder = new StringBuilder();

                for (int bytes = isr.read(buffer); bytes != -1; bytes = isr.read(buffer)) {
                    builder.append(buffer);
                }

                val = builder.toString();
            } catch (Throwable ex) {
                ex.printStackTrace();
                return;
            } finally {
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            json = JsonParser.decodeJsonObject(val);
        }

        final Map<String, Object> web_app = (Map<String, Object>)json.get("web-app");
        final List<Object> servlet = (List<Object>)web_app.get("servlet");
        final Map<String, Object> servlet0 = (Map<String, Object>)servlet.get(0);
        final Map<String, Object> init_param = (Map<String, Object>)servlet0.get("init-param");
        final Boolean useJSP = (Boolean)init_param.get("useJSP");

        System.out.println("useJSP = " + useJSP);
    }
}
