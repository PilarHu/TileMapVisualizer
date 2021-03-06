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
    private static final String HELP_PARAMS = "Options:\n" + ParameterStore.HELP;

    /**
     * @param args Command line arguments (Options).
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ParameterStore options = new ParameterStore(args);
        if (options.isHelp()) {
            System.err.println(HELP_USAGE);
            System.err.println(HELP_PARAMS);
            return;
        }

        // read map
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int maxHalfCols = 0;

        List<char[]> char2D = new ArrayList<>();
        while (true) {
            String line = br.readLine();
            if (line==null) {
                break;
            }
            char[] chars = line.toCharArray();
            int lineHalfCells=2 * chars.length + (((char2D.size()&1)==1?options.isShiftOdd():options.isShiftEven())?1:0);
            if (lineHalfCells > maxHalfCols) {
                maxHalfCols = lineHalfCells;
            }
            char2D.add(chars);
        }

        // calculate dimensions
        int imageWidth = options.getTileWidth() * maxHalfCols / 2;
        int shiftWidth = (options.getTileWidth() + 1) / 2;

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
