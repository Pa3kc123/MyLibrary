package sk.pa3kc.mylibrary.util;

import java.io.Closeable;

public class StreamUtils
{
    public static void closeStreams(Closeable... streams)
    {
        if (streams != null)
        {
            for (Closeable stream : streams)
            {
                try
                {
                    stream.close();
                }
                catch (Throwable ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
