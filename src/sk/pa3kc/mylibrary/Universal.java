package sk.pa3kc.mylibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sk.pa3kc.mylibrary.cmd.CmdColor;
import sk.pa3kc.mylibrary.cmd.CmdUtils;
import sk.pa3kc.mylibrary.net.Device;
import sk.pa3kc.mylibrary.util.JsonParser;
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
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

        System.out.println(JsonParser.encodeJsonObject(new HashMap<String, Object>() {{
            super.put("glossary", new HashMap<String, Object>() {{
                super.put("title", "example glossary");
                super.put("GlossDiv", new HashMap<String, Object>() {{
                    super.put("title", "S");
                    super.put("GlossList", new HashMap<String, Object>() {{
                        super.put("GlossEntry", new HashMap<String, Object>() {{
                            super.put("ID", "SGML");
                            super.put("SortAs", "SGML");
                            super.put("GlossTerm", "Standard Generalized Markup Language");
                            super.put("Acronym", "SGML");
                            super.put("Abbrev", "ISO 8879:1986");
                            super.put("GlossDef", new HashMap<String, Object>() {{
                                super.put("para", "A meta-markup language, used to create markup languages such as DocBook.");
                                super.put("GlossSeeAlso", new ArrayList<Object>() {{
                                    super.add("GML");
                                    super.add("XML");
                                }});
                            }});
                            super.put("GlossSee", "markup");
                        }});
                    }});
                }});
            }});
        }}));
    }
}
