package sk.pa3kc.mylibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import sk.pa3kc.mylibrary.util.StreamUtils;

public class FileProperties extends Properties {
    private static final long serialVersionUID = 1L;
    private final String filePath;

    public FileProperties(String saveFilePath) {
        super();
        this.filePath = new File(saveFilePath).exists() == true ? saveFilePath : null;
    }

    public FileProperties(Properties properties, String saveFilePath) {
        super(properties);
        this.filePath = new File(saveFilePath).exists() == true ? saveFilePath : null;
    }

    public boolean saveToFile() {
        FileOutputStream stream = null;

        try {
            stream = new FileOutputStream(new File(this.filePath));
            super.defaults.store(stream, "Configuration");
        } catch (Throwable ex) {
            ex.printStackTrace();
            return false;
        } finally {
            StreamUtils.closeStreams(stream);
        }

        return true;
    }
}
