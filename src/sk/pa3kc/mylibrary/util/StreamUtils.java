package sk.pa3kc.mylibrary.util;

import java.io.Closeable;

public class StreamUtils
{
    public static void closeStreams(Closeable... streams)
    {
        closeStreams(null, streams);
    }
    public static void closeStreams(AutoCloseable... encoders)
    {
        closeStreams(encoders, null);
    }
    public static void closeStreams(AutoCloseable[] encoders, Closeable[] streams)
    {
        if (encoders != null)
        {
            for (AutoCloseable encoder : encoders)
            {
                try
                {
                    encoder.close();
                }
                catch (Throwable ex)
                {
                    ex.printStackTrace();
                }
            }
        }

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
