package sk.pa3kc.mylibrary.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import sk.pa3kc.mylibrary.DefaultSystemPropertyStrings;

public class CmdColor
{
    private static final CmdColor instance = new CmdColor();

    private CmdColor()
    {
        if (DefaultSystemPropertyStrings.OS_NAME.contains("Windows") == true)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("CmdColor");
            builder.append(DefaultSystemPropertyStrings.OS_ARCH.equals("amd64") == true ? 64 : 32);
            builder.append(".dll");
            String libName = builder.toString();
            
            builder.delete(0, builder.length());
            builder.append(DefaultSystemPropertyStrings.JAVA_IO_TMPDIR);
            builder.append(DefaultSystemPropertyStrings.FILE_SEPARATOR);
            builder.append("CmdColor");
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

    public static CmdColor getInstance() { return instance; }

    private static native void init();

    public final String RESET = "\033[0m";

    // Regular Colors
    public final String BLACK = "\033[0;30m";    // BLACK
    public final String RED = "\033[0;31m";      // RED
    public final String GREEN = "\033[0;32m";    // GREEN
    public final String YELLOW = "\033[0;33m";   // YELLOW
    public final String BLUE = "\033[0;34m";     // BLUE
    public final String MAGENTA = "\033[0;35m";  // MAGENTA
    public final String CYAN = "\033[0;36m";     // CYAN
    public final String WHITE = "\033[0;37m";    // WHITE

    // Bold
    public final String BLACK_BOLD = "\033[1;30m";   // BLACK
    public final String RED_BOLD = "\033[1;31m";     // RED
    public final String GREEN_BOLD = "\033[1;32m";   // GREEN
    public final String YELLOW_BOLD = "\033[1;33m";  // YELLOW
    public final String BLUE_BOLD = "\033[1;34m";    // BLUE
    public final String MAGENTA_BOLD = "\033[1;35m"; // MAGENTA
    public final String CYAN_BOLD = "\033[1;36m";    // CYAN
    public final String WHITE_BOLD = "\033[1;37m";   // WHITE

    // Underline
    public final String BLACK_UNDERLINED = "\033[4;30m";     // BLACK
    public final String RED_UNDERLINED = "\033[4;31m";       // RED
    public final String GREEN_UNDERLINED = "\033[4;32m";     // GREEN
    public final String YELLOW_UNDERLINED = "\033[4;33m";    // YELLOW
    public final String BLUE_UNDERLINED = "\033[4;34m";      // BLUE
    public final String MAGENTA_UNDERLINED = "\033[4;35m";   // MAGENTA
    public final String CYAN_UNDERLINED = "\033[4;36m";      // CYAN
    public final String WHITE_UNDERLINED = "\033[4;37m";     // WHITE

    // Background
    public final String BLACK_BACKGROUND = "\033[40m";   // BLACK
    public final String RED_BACKGROUND = "\033[41m";     // RED
    public final String GREEN_BACKGROUND = "\033[42m";   // GREEN
    public final String YELLOW_BACKGROUND = "\033[43m";  // YELLOW
    public final String BLUE_BACKGROUND = "\033[44m";    // BLUE
    public final String MAGENTA_BACKGROUND = "\033[45m"; // MAGENTA
    public final String CYAN_BACKGROUND = "\033[46m";    // CYAN
    public final String WHITE_BACKGROUND = "\033[47m";   // WHITE

    // High Intensity
    public final String BLACK_BRIGHT = "\033[0;90m";     // BLACK
    public final String RED_BRIGHT = "\033[0;91m";       // RED
    public final String GREEN_BRIGHT = "\033[0;92m";     // GREEN
    public final String YELLOW_BRIGHT = "\033[0;93m";    // YELLOW
    public final String BLUE_BRIGHT = "\033[0;94m";      // BLUE
    public final String MAGENTA_BRIGHT = "\033[0;95m";   // MAGENTA
    public final String CYAN_BRIGHT = "\033[0;96m";      // CYAN
    public final String WHITE_BRIGHT = "\033[0;97m";     // WHITE

    // Bold High Intensity
    public final String BLACK_BOLD_BRIGHT = "\033[1;90m";    // BLACK
    public final String RED_BOLD_BRIGHT = "\033[1;91m";      // RED
    public final String GREEN_BOLD_BRIGHT = "\033[1;92m";    // GREEN
    public final String YELLOW_BOLD_BRIGHT = "\033[1;93m";   // YELLOW
    public final String BLUE_BOLD_BRIGHT = "\033[1;94m";     // BLUE
    public final String MAGENTA_BOLD_BRIGHT = "\033[1;95m";  // MAGENTA
    public final String CYAN_BOLD_BRIGHT = "\033[1;96m";     // CYAN
    public final String WHITE_BOLD_BRIGHT = "\033[1;97m";    // WHITE

    // High Intensity backgrounds
    public final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";     // BLACK
    public final String RED_BACKGROUND_BRIGHT = "\033[0;101m";       // RED
    public final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";     // GREEN
    public final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";    // YELLOW
    public final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";      // BLUE
    public final String MAGENTA_BACKGROUND_BRIGHT = "\033[0;105m";   // MAGENTA
    public final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";      // CYAN
    public final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";     // WHITE
}
