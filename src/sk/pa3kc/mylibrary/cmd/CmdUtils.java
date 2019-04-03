package sk.pa3kc.mylibrary.cmd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

import sk.pa3kc.mylibrary.DefaultSystemPropertyStrings;
import sk.pa3kc.mylibrary.util.StreamUtils;

public class CmdUtils extends PrintStream
{
    private static CmdUtils instance = null;
    private CmdColor color = null;

    public static CmdUtils getInstance() { return instance == null ? (instance = new CmdUtils(System.out)) : instance; }
    static CmdUtils getSecretInstance() { return instance; }

    private CmdUtils(OutputStream out)
    {
        super(out);
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

    public void setColor(CmdColor color) { this.color = color; }

    public static native void init();

    public void moveCursorTo1(int x, int y) { super.printf(Locale.getDefault(), "\033[%d;%dH", x, y); }
    public void moveCursorTo2(int x, int y) { super.printf(Locale.getDefault(), "\033[%d;%df", x, y); }
    public void moveUp(int count) { super.printf(Locale.getDefault(), "\033[%dA", count); }
    public void moveDown(int count) { super.printf(Locale.getDefault(), "\033[%dB", count); }
    public void moveRight(int count) { super.printf(Locale.getDefault(), "\033[%dC", count); }
    public void moveLeft(int count) { super.printf(Locale.getDefault(), "\033[%dD", count); }
    public void clearScreen() { super.print("\033[2J"); }
    public void clearLine() { super.print("\033[K"); }
}
