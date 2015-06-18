package hu.pilar.tilemapvisualizer;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Parameter store for TileMapVisualizer application.
 *
 * @author SZIGETI János
 */
public class ParameterStore {

    // list of command line options
    private static final String OPTION_HELP = "-h";
    private static final String OPTION_WIDTH = "-x";
    private static final String OPTION_HEIGHT = "-y";
    private static final String OPTION_SHO = "-sho";
    private static final String OPTION_SHE = "-she";
    private static final String OPTION_STROKE = "-l";
    private static final String OPTION_COLORMAP = "-cm";

    // list of default values
    private static final int DEFAULT_WIDTH = 15;
    private static final int DEFAULT_HEIGHT = 15;
    private static final int DEFAULT_STROKE = 0;

    // help text
    public static final String HELP = " "
            + OPTION_WIDTH + " num\ttile width (px, default: 15)\n "
            + OPTION_HEIGHT + " num\ttile height (px, default: 15)\n "
            + OPTION_COLORMAP + " filename\ncolormap properties (default built-in color map)\n "
            + OPTION_SHO + "\tshift odd rows half tile width (default: do not shift odd rows)\n "
            + OPTION_SHE + "\tshift even rows half tile width (default: do not shift even rows)\n "
            + OPTION_STROKE + " num\tstroke line width (px, default: 0 - no stroke)";

    // object attributes storing the application parameters
    private int tileWidth;
    private int tileHeight;
    private int stroke;
    private ColorMap colorMap;
    private boolean shiftEven;
    private boolean shiftOdd;
    private boolean help;

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getStroke() {
        return stroke;
    }

    public Color getColor(Character ch) {
        return colorMap.getColor(ch);
    }

    public boolean isShiftEven() {
        return shiftEven;
    }

    public boolean isShiftOdd() {
        return shiftOdd;
    }

    public boolean isHelp() {
        return help;
    }

    /**
     * Standard constuctor that uses command line arguments.
     *
     * @param args
     */
    public ParameterStore(String[] args) {
        // initialize internal variables
        boolean she = false;
        boolean sho = false;
        int width = DEFAULT_WIDTH;
        int height = DEFAULT_HEIGHT;
        int stroke = DEFAULT_STROKE;
        String cmapFilename = null;

        // set internal variables
        for (int i = 0; i < args.length; ++i) {
            if (OPTION_WIDTH.equals(args[i])) {
                width = Integer.valueOf(args[i + 1]);
                ++i;
            } else if (OPTION_HEIGHT.equals(args[i])) {
                height = Integer.valueOf(args[i + 1]);
                ++i;
            } else if (OPTION_STROKE.equals(args[i])) {
                stroke = Integer.valueOf(args[i + 1]);
                ++i;
            } else if (OPTION_SHE.equals(args[i])) {
                she = true;
            } else if (OPTION_SHO.equals(args[i])) {
                sho = true;
            } else if (OPTION_COLORMAP.equals(args[i])) {
                cmapFilename = args[i + 1];
                ++i;
            } else if (OPTION_HELP.equals(args[i])) {
                this.help = true;
            } else {
                throw new IllegalArgumentException("Cannot parse option " + args[i]);
            }
        }

        // initialize object attributed with internal variables.
        this.tileWidth = width;
        this.tileHeight = height;
        this.stroke = stroke;
        this.shiftEven = she;
        this.shiftOdd = sho;
        Properties xcmap = new Properties();
        try {
            if (cmapFilename != null) {
                xcmap.load(new FileReader(cmapFilename));
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Cannot read file " + cmapFilename, ex);
        }
        this.colorMap = new ColorMap(xcmap);
    }
}
