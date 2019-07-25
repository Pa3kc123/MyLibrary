package sk.pa3kc.mylibrary.cmd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import sk.pa3kc.mylibrary.DefaultSystemPropertyStrings;
import sk.pa3kc.mylibrary.util.StreamUtils;
import sk.pa3kc.mylibrary.util.StringUtils;

public class CmdUtils {
    static final char escCode = 0x1B;

    static CmdUtils instance = null;

    private CmdColor color = null;
    private OutputStream stream = System.out;

    static CmdUtils getInstance() { return instance == null ? (instance = new CmdUtils()) : instance; }

    private CmdUtils() {
        if (DefaultSystemPropertyStrings.OS_NAME.contains("Windows") == true) {
            StringBuilder builder = new StringBuilder();
            builder.append(this.getClass().getSimpleName());
            builder.append(DefaultSystemPropertyStrings.OS_ARCH.equals("amd64") == true ? 64 : 32);
            builder.append(".dll");
            String libName = builder.toString();

            builder.delete(0, builder.length());
            builder.append(DefaultSystemPropertyStrings.JAVA_IO_TMPDIR);
            builder.append(DefaultSystemPropertyStrings.FILE_SEPARATOR);
            builder.append(this.getClass().getSimpleName());
            builder.append(DefaultSystemPropertyStrings.FILE_SEPARATOR);
            builder.append(libName);

            File installedLibFile = new File(builder.toString());
            if (installedLibFile.exists() == false) {
                InputStream resource = null;
                OutputStream stream = null;
                try {
                    if (installedLibFile.exists() == false) {
                        installedLibFile.getParentFile().mkdirs();
                        installedLibFile.createNewFile();
                    }
                    resource = this.getClass().getClassLoader().getResourceAsStream(libName);
                    stream = new FileOutputStream(installedLibFile);

                    byte[] buffer = new byte[1024];
                    for (int checksum = resource.read(buffer); checksum != -1; checksum = resource.read(buffer)) {
                        stream.write(buffer);
                        stream.flush();
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                } finally {
                    StreamUtils.closeStreams(stream, resource);
                }
            }

            try {
                System.load(installedLibFile.getAbsolutePath());
            } catch (Throwable ex) {
                ex.printStackTrace();
            }

            init();
        }
    }

    public CmdColor getColorX() { return this.color; }
    public OutputStream getStreamX() { return this.stream; }

    private void setColorX(CmdColor value) { this.onColorChanged(this.color, value); }
    private void setStreamX(OutputStream value) { this.stream = value; }

    public static native void init();

    private void onColorChanged(CmdColor oldColor, CmdColor newColor) {
        this.print(newColor);
        this.color = newColor;
    }

    private void moveCursorToX(int x, int y) { this.print(StringUtils.build(escCode, '[', x, ';', y, 'H')); }
    private void moveUpX(int count) { this.print(StringUtils.build(escCode, '[', count, 'A')); }
    private void moveDownX(int count) { this.print(StringUtils.build(escCode, '[', count, 'B')); }
    private void moveRightX(int count) { this.print(StringUtils.build(escCode, '[', count, 'C')); }
    private void moveLeftX(int count) { this.print(StringUtils.build(escCode, '[', count, 'D')); }
    private void clearScreenX() { this.print(StringUtils.build(escCode, "[2J")); }
    private void clearLineX() { this.print(StringUtils.build(escCode, "[K")); }
    private void resetColorX() { this.print(CmdColor.RESET); }

    private void print(CmdColor color) { this.print(color.code); }
    private void print(String text) {
        try {
            this.stream.write(text.getBytes());
            this.stream.flush();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static CmdColor getColor() { return CmdUtils.getInstance().getColorX(); }
    public static OutputStream getStream() { return CmdUtils.getInstance().getStreamX(); }

    public static void setColor(CmdColor value) { CmdUtils.getInstance().setColorX(value); }
    public static void setForegroundRGB(int r, int g, int b) { CmdUtils.getInstance().print(StringUtils.build(escCode, "[38;2;", r, ";", g, ";", b, "m")); }
    public static void setBackgroundRGB(int r, int g, int b) { CmdUtils.getInstance().print(StringUtils.build(escCode, "[48;2;", r, ";", g, ";", b, "m")); }
    public static void setStream(OutputStream value) { CmdUtils.getInstance().setStreamX(value); }

    public static void moveCursorTo(int x, int y) { CmdUtils.getInstance().moveCursorToX(x, y); }
    public static void moveUp(int count) { CmdUtils.getInstance().moveUpX(count); }
    public static void moveDown(int count) { CmdUtils.getInstance().moveDownX(count); }
    public static void moveRight(int count) { CmdUtils.getInstance().moveRightX(count); }
    public static void moveLeft(int count) { CmdUtils.getInstance().moveLeftX(count); }
    public static void clearScreen() { CmdUtils.getInstance().clearScreenX(); }
    public static void clearLine() { CmdUtils.getInstance().clearLineX(); }
    public static void resetColor() { CmdUtils.getInstance().resetColorX(); }
}
