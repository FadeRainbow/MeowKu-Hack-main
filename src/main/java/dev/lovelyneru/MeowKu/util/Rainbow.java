package dev.lovelyneru.MeowKu.util;

import java.awt.*;

public class Rainbow {

    public static Color getColour() {
        return Colour.fromHSB((System.currentTimeMillis() % (360 * 32)) / (360f * 32), 1, 1);
    }

    public static Color getRainbowColor(float speed, float saturation, float brightness) {
        return new Color(Rainbow.getRainbow(speed, saturation, brightness));
    }




    public static int getRainbow(float speed, float saturation, float brightness) {
        long add=1;
        float hue = (float)((System.currentTimeMillis() + add) % 11520L) / 11520.0f * speed;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }


}
