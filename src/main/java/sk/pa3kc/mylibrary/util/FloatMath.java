package sk.pa3kc.mylibrary.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import sk.pa3kc.mylibrary.DefaultSystemPropertyStrings;

public class FloatMath {
    static {
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
            nativeFileExt = "---";
        }

        if (nativeFileExt != "---") {
            final String nativeFile = nativeFileName + nativeFileExt;
            final File tmpFile = new File(tmpPath, nativeFile);

            if (!tmpFile.exists()) {
                InputStream is = ClassLoader.getSystemResourceAsStream(nativeFile);
                FileOutputStream fos = null;

                try {
                    tmpFile.createNewFile();

                    fos = new FileOutputStream(tmpFile);

                    final byte[] buffer = new byte[2048];

                    int checksum = -1;
                    while((checksum = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, checksum);
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                } finally {
                    StreamUtils.closeStreams(fos, is);
                }
            }

            Runtime.getRuntime().load(tmpPath + '/' + nativeFile);
        }
    }

    private FloatMath() {}

    public static native float sqrt(float a);
}
