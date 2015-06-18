package hu.pilar.tilemapvisualizer;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author SZIGETI János
 */
public class ColorMap {

    private static final String COLOR_PREFIX="color.";
    private final Properties preColors;
    private Map<Character, Color> colors = new HashMap<>();

    public ColorMap(Properties preColors) {
        this.preColors = preColors;
    }

    public ColorMap() {
        this.preColors = new Properties();
    }

    Color getColor(Character ch) {
        if (colors.containsKey(ch)) {
            return colors.get(ch);
        }
        Color cx = lookupColor(preColors,ch);
        if (cx == null) {
            cx = assingDefaultColor(ch);
        }
        colors.put(ch, cx);
        return cx;
    }

    public static Color lookupColor(Properties preColors, Character ch) {
        String colorStr = preColors.getProperty(COLOR_PREFIX + ch);
        if (colorStr == null) {
            return null;
        }
        if (colorStr.startsWith("#")) {
            return new Color(Integer.valueOf(colorStr.substring(1), 16));
        } else {
            throw new IllegalArgumentException("Color format of color "+colorStr+" not recognized.");
        }
    }

    private Color assingDefaultColor(Character ch) {
        return new Color((ch*17)&0xff, (ch*41)&0xff, (ch*101)&0xff);
    }
}
