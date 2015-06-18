package hu.pilar.tilemapvisualizer;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Store for character => color mapping.
 *
 * @author SZIGETI János
 */
public class ColorMap {

    private static final String COLOR_PREFIX = "color.";
    private final Properties preColors;
    private Map<Character, Color> colors = new HashMap<>();

    /**
     * Standard constructor with predefined color mappings.
     *
     * @param preColors Properties that contains predefined colors.
     */
    public ColorMap(Properties preColors) {
        this.preColors = preColors;
    }

    /**
     * Get color by character. If the character is key in the predefined color
     * map, the corresponding color value is returned. Otherwise a default color
     * value will be assinged to the given character.
     *
     * @param ch
     * @return
     */
    Color getColor(Character ch) {
        if (colors.containsKey(ch)) {
            return colors.get(ch);
        }
        Color cx = lookupColor(preColors, ch);
        if (cx == null) {
            cx = assingDefaultColor(ch);
        }
        colors.put(ch, cx);
        return cx;
    }

    /**
     * Looks up a color for a given character in preColors. If the color for the
     * given character is not defined in preColors, returns null. If the defined
     * color cannot be parsed, throws IllegalArgumentException.
     *
     * @param preColors
     * @param ch
     * @return
     */
    public static Color lookupColor(Properties preColors, Character ch) {
        String colorStr = preColors.getProperty(COLOR_PREFIX + ch);
        if (colorStr == null) {
            return null;
        }
        if (colorStr.startsWith("#")) {
            return new Color(Integer.valueOf(colorStr.substring(1), 16));
        } else {
            throw new IllegalArgumentException("Color format of color " + colorStr + " not recognized.");
        }
    }

    /**
     * Invariant character => color mapping defined by arithmetic function.
     *
     * @param ch
     * @return
     */
    private static Color assingDefaultColor(Character ch) {
        return new Color((ch * 17) & 0xff, (ch * 41) & 0xff, (ch * 101) & 0xff);
    }
}
