package sk.pa3kc.mylibrary.cmd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import sk.pa3kc.mylibrary.DefaultSystemPropertyStrings;
import sk.pa3kc.mylibrary.util.StreamUtils;

public class CmdUtils
{
    static final char escCode = 0x1B;

    private static CmdUtils instance = null;

    private CmdColor color = null;
    private OutputStream stream = System.out;

    public static CmdUtils getInstance() { return instance == null ? (instance = new CmdUtils()) : instance; }
    public static boolean instanceExists() { return instance != null; }

    private CmdUtils()
    {
        if (DefaultSystemPropertyStrings.OS_NAME.contains("Windows") == true)
        {
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
            if (installedLibFile.exists() == false)
            {
                InputStream resource = null;
                OutputStream stream = null;
                try
                {
                    if (installedLibFile.exists() == false)
                    {
                        installedLibFile.getParentFile().mkdirs();
                        installedLibFile.createNewFile();
                    }
                    resource = this.getClass().getClassLoader().getResourceAsStream(libName);
                    stream = new FileOutputStream(installedLibFile);
    
                    byte[] buffer = new byte[1024];
                    for (int checksum = resource.read(buffer); checksum != -1; checksum = resource.read(buffer))
                        stream.write(buffer);
                }
                catch (Throwable ex)
                {
                    ex.printStackTrace();
                }
                finally
                {
                    StreamUtils.closeStreams(stream, resource);
                }
            }

            try
            {
                System.load(installedLibFile.getAbsolutePath());
            }
            catch (Throwable ex)
            {
                ex.printStackTrace();
            }

            init();
        }
    }

    public CmdColor getColor() { return this.color; }
    public OutputStream getStream() { return this.stream; }

    public void setColor(CmdColor value) { this.onColorChanged(this.color, value); }
    public void setStream(OutputStream value) { this.stream = value; }

    public static native void init();

    public void onColorChanged(CmdColor oldColor, CmdColor newColor)
    {
        this.print(newColor);
        this.color = newColor;
    }

    public void moveCursorTo(int x, int y) { this.print("%c[%d;%dH", escCode, x, y); }
    public void moveUp(int count) { this.print("%c[%dA", escCode, count); }
    public void moveDown(int count) { this.print("%c[%dB", escCode, count); }
    public void moveRight(int count) { this.print("%c[%dC", escCode, count); }
    public void moveLeft(int count) { this.print("%c[%dD", escCode, count); }
    public void clearScreen() { this.print("%c[2J", escCode); }
    public void clearLine() { this.print("%c[K", escCode); }

    private void print(CmdColor color) { this.print(color.code); }
    private void print(String format, Object... args) { this.print(String.format(format, args)); }
    private void print(String text)
    {
        try
        {
            this.stream.write(text.getBytes());
            this.stream.flush();
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
        }
    }
}
