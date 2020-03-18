package sk.pa3kc.mylibrary;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import sk.pa3kc.mylibrary.cmd.CmdColor;
import sk.pa3kc.mylibrary.cmd.CmdUtils;
import sk.pa3kc.mylibrary.net.Device;
import sk.pa3kc.mylibrary.json.JsonParser;
import sk.pa3kc.mylibrary.util.NumberUtils;

public class Universal {
    public static void main(String[] args) {
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

        System.out.println(NumberUtils.round(0.12345, 3));

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
            String val = null;

            try {
                val = new String(Files.readAllBytes(Paths.get(args[0])), "utf-8");
            } catch (Throwable ex) {
                ex.printStackTrace();
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
