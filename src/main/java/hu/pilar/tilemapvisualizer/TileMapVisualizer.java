package hu.pilar.tilemapvisualizer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author SZIGETI János
 */
public class TileMapVisualizer {

    private static final String HELP_USAGE = "Usage:\n"
            + " stdin: tile map (txt file)\n"
            + " stdout: tile image (png file)";
    private static final String HELP_PARAMS = "Options:\n" + OptionStore.HELP;

    /**
     * @param args Command line arguments (Options).
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        OptionStore options = new OptionStore(args);
        if (options.isHelp()) {
            System.err.println(HELP_USAGE);
            System.err.println(HELP_PARAMS);
            return;
        }

        // read map
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int maxCols = 0;
        List<char[]> char2D = new ArrayList<>();
        while (br.ready()) {
            char[] chars = br.readLine().toCharArray();
            if (chars.length > maxCols) {
                maxCols = chars.length;
            }
            char2D.add(chars);
        }

        // calculate dimensions
        int imageWidth = options.getTileWidth() * maxCols;
        int shiftWidth = (options.getTileWidth() + 1) / 2;
        if (options.isShiftEven() || options.isShiftOdd()) {
            imageWidth += shiftWidth;
        }

        // generate image
        BufferedImage img = new BufferedImage(imageWidth, options.getTileHeight() * char2D.size(), BufferedImage.TYPE_BYTE_INDEXED);
        Graphics graphics = img.getGraphics();
        for (int i = 0; i < char2D.size(); ++i) {
            char[] charLine = char2D.get(i);
            int shift = (i % 2 == 0) ? (options.isShiftEven() ? shiftWidth : 0) : (options.isShiftOdd() ? shiftWidth : 0);
            for (int j = 0; j < charLine.length; ++j) {
                graphics.setColor(options.getColor(charLine[j]));
                graphics.fillRect(j * options.getTileWidth() + shift, i * options.getTileHeight(), options.getTileWidth(), options.getTileHeight());
                if (options.getStroke() != 0) {
                    graphics.setColor(Color.DARK_GRAY);
                    ((Graphics2D) graphics).setStroke(new BasicStroke(options.getStroke()));
                    graphics.drawRect(j * options.getTileWidth() + shift, i * options.getTileHeight(), options.getTileWidth(), options.getTileHeight());
                }
            }
        }
        ImageIO.write(img, "png", System.out);
    }
}
