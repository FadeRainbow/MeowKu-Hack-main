package dev.lovelyneru.MeowKu.util;

import dev.lovelyneru.MeowKu.MeowKu;
import net.minecraft.client.Minecraft;

public class FontUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static float drawStringWithShadow(boolean customFont, String text, int x, int y, int color){
        if(customFont) return MeowKu.fontRenderer.drawStringWithShadow(text, x, y, color);
        else return mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public static int getStringWidth(boolean customFont, String str){
        if(customFont) return MeowKu.fontRenderer.getStringWidth(str);
        else return mc.fontRenderer.getStringWidth(str);
    }

    public static int getFontHeight(boolean customFont){
        if(customFont) return MeowKu.fontRenderer.getHeight();
        else return mc.fontRenderer.FONT_HEIGHT;
    }

    public static float drawKeyStringWithShadow(boolean customFont, String text, int x, int y, int color) {
        if(customFont) return MeowKu.fontRenderer.drawStringWithShadow(text, x, y, color);
        else return mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }
}