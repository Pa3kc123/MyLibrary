package sk.pa3kc.miniprojects;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import sk.pa3kc.mylibrary.utils.ArgsParser;
import sk.pa3kc.mylibrary.utils.DefaultSystemPropertyStrings;

public class App {
    final static GraphicsConfiguration CONFIG = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    final static File TMP_PATH = new File(DefaultSystemPropertyStrings.JAVA_IO_TMPDIR, "sheet-to-gif");

    public static void main(String[] args) throws Throwable {
        if (args.length == 0) return;

        final ArgsParser params = new ArgsParser(args);

        final int rowCount = Integer.parseInt(params.getOption("row-count"));
        final int colCount = Integer.parseInt(params.getOption("col-count"));
        final int rowOffset = Integer.parseInt(params.getOption("row-offset"));
        final int colOffset = Integer.parseInt(params.getOption("col-offset"));
        final float frameDelay = Float.parseFloat(params.getOption("delay"));
        final int width = Integer.parseInt(params.getOption("width"));
        final int height = Integer.parseInt(params.getOption("height"));
        final File sheet = new File(params.getOption("sheet"));
        final File output = new File(params.getOption("out"));

        System.out.println("Sheet = " + sheet);
        System.out.println("Path = " + TMP_PATH);

        if (!TMP_PATH.exists()) TMP_PATH.mkdir();
        if (!output.exists()) output.createNewFile();

        // #BEAIFUL
        if (rowCount <= 0)           new IllegalArgumentException("row-count cannot be less or equal to 0");
        if (colCount <= 0)           new IllegalArgumentException("col-count cannot be less or equal to 0");
        if (width <= 0)              new IllegalArgumentException("width cannot be less or equal to 0");
        if (height <= 0)             new IllegalArgumentException("height cannot be less or equal to 0");
        if (rowOffset >= rowCount)   new IllegalArgumentException("row-offset cannot be more or equal to row-count");
        if (colOffset >= colCount)   new IllegalArgumentException("col-offset cannot be more or equal to col-count");
        if (!sheet.exists())         new IllegalArgumentException("Sheet file not found");

        final BufferedImage sheetImg = ImageIO.read(sheet);
        final BufferedImage[] images = new BufferedImage[(rowCount - rowOffset) * (colCount - colOffset)];

        File currFile;
        BufferedImage currImg;
        int index = 0;
        for (int row = 0 + rowOffset; row < rowCount; row++) {
            for (int col = 0 + colOffset; col < colCount; col++) {
                currFile = File.createTempFile(String.format("%03d", index), ".png", TMP_PATH);
                currImg = CONFIG.createCompatibleImage(width, height, BufferedImage.TRANSLUCENT);
                currImg.createGraphics().clearRect(0, 0, width, height);

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        currImg.setRGB(x, y, sheetImg.getRGB(x + (width * col), y + (height * row)));
                    }
                }

                images[index++] = currImg;

                ImageIO.write(currImg, "png", currFile);
            }
        }

        final ImageOutputStream ios = new FileImageOutputStream(output);
        final GifSequenceWriter writer = new GifSequenceWriter(ios, images[0].getType(), (int)frameDelay, true);

        for (final BufferedImage image : images) {
            writer.writeToSequence(image);
        }

        writer.close();
        ios.close();
    }
}
