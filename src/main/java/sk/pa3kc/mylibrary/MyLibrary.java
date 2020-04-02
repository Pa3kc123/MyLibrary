package sk.pa3kc.mylibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import sk.pa3kc.mylibrary.cmd.CmdColor;
import sk.pa3kc.mylibrary.cmd.CmdUtils;
import sk.pa3kc.mylibrary.net.Device;
import sk.pa3kc.mylibrary.json.JsonParser;
import sk.pa3kc.mylibrary.util.ArgsParser;
import sk.pa3kc.mylibrary.util.FloatMath;
import sk.pa3kc.mylibrary.util.NumberUtils;
import sk.pa3kc.mylibrary.util.StreamUtils;

public class MyLibrary {
    static boolean initialized = false;

    public static boolean init() {
        final String tmpPath = DefaultSystemPropertyStrings.JAVA_IO_TMPDIR;
        final String osName = DefaultSystemPropertyStrings.OS_NAME;
        final String nativeFileName = "FloatMath";

        final String nativeFileExt;
        if ("Linux".equals(osName)) {
            nativeFileExt = ".so";
        } else if ("Windows".equals(osName)) {
            final String arch = DefaultSystemPropertyStrings.OS_ARCH;

            if (arch.contains("64")) {
                nativeFileExt = "64.dll";
            } else {
                nativeFileExt = "32.dll";
            }
        } else {
            return false;
        }

        final String nativeFile = nativeFileName + nativeFileExt;
        final File tmpFile = new File(tmpPath, nativeFile);

        if (!tmpFile.exists()) {
            System.out.println("Unpacking native files to " + tmpFile.getAbsolutePath());
            InputStream is = ClassLoader.getSystemResourceAsStream(nativeFile);
            FileOutputStream fos = null;

            try {
                tmpFile.createNewFile();

                fos = new FileOutputStream(tmpFile);

                final byte[] buffer = new byte[2048];

                int checksum = -1;
                while((checksum = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, checksum);
                    fos.flush();
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            } finally {
                StreamUtils.closeStreams(fos, is);
            }
        } else {
            System.out.println("Native files already exists in " + tmpFile.getAbsolutePath());
        }

        try {
            Runtime.getRuntime().load(tmpPath + '/' + nativeFile);
        } catch (Throwable ex) {
            if (ex instanceof SecurityException) {}
            if (ex instanceof UnsatisfiedLinkError) {}
            if (ex instanceof NullPointerException) {}

            return false;
        }

        return (initialized = true);
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        final ArgsParser parser = new ArgsParser(args);

        MyLibrary.init();

        for (String arg : parser.getAllArguments()) {
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
            String val = null;

            try {
                val = new String(Files.readAllBytes(Paths.get(parser.getArgument(0))), "utf-8");
            } catch (Throwable ex) {
                ex.printStackTrace();
                return;
            }

            json = JsonParser.decodeJsonObject(val);
        }

        final Map<String, Object> web_app = (Map<String, Object>)json.get("web-app");
        final List<Object> servlet = (List<Object>)web_app.get("servlet");
        final Map<String, Object> servlet0 = (Map<String, Object>)servlet.get(0);
        final Map<String, Object> init_param = (Map<String, Object>)servlet0.get("init-param");
        final Boolean useJSP = (Boolean)init_param.get("useJSP");

        System.out.println("useJSP = " + useJSP);

        long milis;
        int cycles = 1000000;

        milis = System.currentTimeMillis();
        for (int i = 1; i <= cycles; i++) {
            StrictMath.sqrt((double)i);
        }
        System.out.println("Java sqrt took " + (System.currentTimeMillis() - milis) + "ms");

        milis = System.currentTimeMillis();
        for (int i = 1; i <= cycles; i++) {
            FloatMath.sqrt((float)i);
        }
        System.out.println("Native sqrt took " + (System.currentTimeMillis() - milis) + "ms");
    }
}