package sk.pa3kc.mylibrary.utils;

import java.io.Closeable;

public class StreamUtils {
    public static void closeStreams(Closeable... streams) {
        if (streams != null)
        for (Closeable stream : streams) {
            if (stream == null) continue;

            try {
                stream.close();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }
}
