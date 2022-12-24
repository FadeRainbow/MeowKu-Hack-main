package dev.lovelyneru.MeowKu.util;


import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import dev.lovelyneru.MeowKu.features.gui.CFontRenderer;
import dev.lovelyneru.MeowKu.features.gui.font.CustomFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL41;

public class RenderUtils {
    public static void setColor(Color color) {
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    public static void setColor(int color) {
        RenderUtils.setColor(new Color(color));
    }

    public static void setColorHA(int color) {
        RenderUtils.setColor(new Color(color, true));
    }

    public static void setColor(int red, int green, int blue, int alpha) {
        RenderUtils.setColor(new Color(red, green, blue, alpha));
    }

    public static void setColor(int red, int green, int blue) {
        RenderUtils.setColor(red, green, blue, 255);
    }

    public static int toRGBA(Color c) {
        return c.getRed() | c.getGreen() << 8 | c.getBlue() << 16 | c.getAlpha() << 24;
    }

    public static Color alpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static void setLineWidth(float width) {
        GL11.glLineWidth((float)width);
    }

    public static void bindTexture(int textureId) {
        GL11.glBindTexture((int)3553, (int)textureId);
    }



    public static void drawBlockBox(BlockPos pos, Color color, Boolean outline) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        color = RenderUtils.alpha(color, color.getAlpha() - 20);
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)(x -= Minecraft.getMinecraft().getRenderManager().viewerPosX), (double)(y -= Minecraft.getMinecraft().getRenderManager().viewerPosY), (double)(z -= Minecraft.getMinecraft().getRenderManager().viewerPosZ));
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GL11.glBlendFunc((int)770, (int)771);
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(outline != false ? 1 : 7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.translate((double)(-x), (double)(-y), (double)(-z));
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public static void drawLine(double x1, double y1, double x2, double y2, float lineWidth, Color ColorStart, Color ColorEnd) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)lineWidth);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)2);
        RenderUtils.setColor(ColorStart);
        GL11.glVertex2d((double)x1, (double)y1);
        RenderUtils.setColor(ColorEnd);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawLine(double x1, double y1, double x2, double y2, float lineWidth, Color color) {
        RenderUtils.drawLine(x1, y1, x2, y2, lineWidth, color, color);
    }

    public static void drawArc(double cx, double cy, double r, double start_angle, double end_angle, int num_segments) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBegin((int)4);
        int i = (int)((double)num_segments / (360.0 / start_angle)) + 1;
        while ((double)i <= (double)num_segments / (360.0 / end_angle)) {
            double previousangle = Math.PI * 2 * (double)(i - 1) / (double)num_segments;
            double angle = Math.PI * 2 * (double)i / (double)num_segments;
            GL11.glVertex2d((double)cx, (double)cy);
            GL11.glVertex2d((double)(cx + Math.cos(angle) * r), (double)(cy + Math.sin(angle) * r));
            GL11.glVertex2d((double)(cx + Math.cos(previousangle) * r), (double)(cy + Math.sin(previousangle) * r));
            ++i;
        }
        if (end_angle == 360.0) {
            GL11.glVertex2d((double)cx, (double)cy);
            GL11.glVertex2d((double)(cx + Math.cos(360.0) * r), (double)(cy + Math.sin(360.0) * r));
        }
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawArcOutline(double cx, double cy, double r, double start_angle, double end_angle, int num_segments) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBegin((int)3);
        int i = (int)((double)num_segments / (360.0 / start_angle));
        while ((double)i <= (double)num_segments / (360.0 / end_angle)) {
            double angle = Math.PI * 2 * (double)i / (double)num_segments;
            GL11.glVertex2d((double)(cx + Math.cos(angle) * r), (double)(cy + Math.sin(angle) * r));
            ++i;
        }
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawCircle(double cx, double cy, double radius) {
        RenderUtils.drawArc(cx, cy, radius, 0.0, 360.0, 16);
    }

    public static void drawCircleOutline(double cx, double cy, double radius) {
        RenderUtils.drawArcOutline(cx, cy, radius, 0.0, 360.0, 16);
    }

    public static void drawCircle(double cx, double cy, double radius, Color color) {
        RenderUtils.setColor(color);
        RenderUtils.drawArc(cx, cy, radius, 0.0, 360.0, 16);
    }

    public static void drawCircleOutline(double cx, double cy, double radius, Color color) {
        RenderUtils.setColor(color);
        RenderUtils.drawArcOutline(cx, cy, radius, 0.0, 360.0, 16);
    }

    public static void drawColoredCircle(double x, double y, double radius, float saturation, float brightness) {
        GL11.glDisable((int)3553);
        GL11.glPushMatrix();
        GL11.glLineWidth((float)1.0f);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)3);
        for (int i = 0; i < 360; ++i) {
            RenderUtils.setColor(Color.HSBtoRGB(0.0f, 0.0f, brightness));
            GL11.glVertex2d((double)x, (double)y);
            RenderUtils.setColor(Color.HSBtoRGB((float)i / 360.0f, saturation, brightness));
            GL11.glVertex2d((double)(x + Math.sin(Math.toRadians(i)) * radius), (double)(y + Math.cos(Math.toRadians(i)) * radius));
        }
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
    }

    public static void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glBegin((int)6);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x3, (double)y3);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawTriangleOutline(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x3, (double)y3);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawGradientRectOutline(double x, double y, double width, double height, GradientDirection direction, Color startColor, Color endColor) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        Color[] result = RenderUtils.checkColorDirection(direction, startColor, endColor);
        GL11.glBegin((int)2);
        GL11.glColor4f((float)((float)result[2].getRed() / 255.0f), (float)((float)result[2].getGreen() / 255.0f), (float)((float)result[2].getBlue() / 255.0f), (float)((float)result[2].getAlpha() / 255.0f));
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glColor4f((float)((float)result[3].getRed() / 255.0f), (float)((float)result[3].getGreen() / 255.0f), (float)((float)result[3].getBlue() / 255.0f), (float)((float)result[3].getAlpha() / 255.0f));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glColor4f((float)((float)result[0].getRed() / 255.0f), (float)((float)result[0].getGreen() / 255.0f), (float)((float)result[0].getBlue() / 255.0f), (float)((float)result[0].getAlpha() / 255.0f));
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glColor4f((float)((float)result[1].getRed() / 255.0f), (float)((float)result[1].getGreen() / 255.0f), (float)((float)result[1].getBlue() / 255.0f), (float)((float)result[1].getAlpha() / 255.0f));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawGradientRect(double x, double y, double width, double height, GradientDirection direction, Color startColor, Color endColor) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        Color[] result = RenderUtils.checkColorDirection(direction, startColor, endColor);
        GL11.glBegin((int)7);
        RenderUtils.setColor(result[0]);
        GL11.glVertex2d((double)(x + width), (double)y);
        RenderUtils.setColor(result[1]);
        GL11.glVertex2d((double)x, (double)y);
        RenderUtils.setColor(result[2]);
        GL11.glVertex2d((double)x, (double)(y + height));
        RenderUtils.setColor(result[3]);
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public static void drawRectOutline(double x, double y, double width, double height, Color color) {
        RenderUtils.drawGradientRectOutline(x, y, width, height, GradientDirection.Normal, color, color);
    }

    public static void drawRect(double x, double y, double width, double height, Color color) {
        RenderUtils.drawGradientRect(x, y, width, height, GradientDirection.Normal, color, color);
    }

    public static void drawRoundedRectangle(double x, double y, double width, double height, double radius, GradientDirection direction, Color startColor, Color endColor) {
        if (width < radius * 2.0 || height < radius * 2.0) {
            return;
        }
        GL11.glShadeModel((int)7425);
        Color[] result = RenderUtils.checkColorDirection(direction, startColor, endColor);
        RenderUtils.setColor(result[0]);
        RenderUtils.drawArc(x + width - radius, y + height - radius, radius, 0.0, 90.0, 16);
        RenderUtils.setColor(result[1]);
        RenderUtils.drawArc(x + radius, y + height - radius, radius, 90.0, 180.0, 16);
        RenderUtils.setColor(result[2]);
        RenderUtils.drawArc(x + radius, y + radius, radius, 180.0, 270.0, 16);
        RenderUtils.setColor(result[3]);
        RenderUtils.drawArc(x + width - radius, y + radius, radius, 270.0, 360.0, 16);
        RenderUtils.drawGradientRect(x + radius, y, width - radius * 2.0, radius, GradientDirection.LeftToRight, result[2], result[3]);
        RenderUtils.drawGradientRect(x + radius, y + height - radius, width - radius * 2.0, radius, GradientDirection.LeftToRight, result[1], result[0]);
        RenderUtils.drawGradientRect(x, y + radius, radius, height - radius * 2.0, GradientDirection.DownToUp, result[1], result[2]);
        RenderUtils.drawGradientRect(x + width - radius, y + radius, radius, height - radius * 2.0, GradientDirection.DownToUp, result[0], result[3]);
        RenderUtils.drawGradientRect(x + radius, y + radius, width - radius * 2.0, height - radius * 2.0, direction, startColor, endColor);
        GL11.glShadeModel((int)7424);
    }

    public static void drawRoundedRectangle(double x, double y, double width, double height, double radius, Color color) {
        RenderUtils.drawRoundedRectangle(x, y, width, height, radius, GradientDirection.Normal, color, color);
    }

    public static void drawRoundedRectangleOutline(double x, double y, double width, double height, double radius, float lineWidth, GradientDirection direction, Color startColor, Color endColor) {
        Color[] result = RenderUtils.checkColorDirection(direction, startColor, endColor);
        GL11.glLineWidth((float)lineWidth);
        RenderUtils.setColor(result[0]);
        RenderUtils.drawArcOutline(x + width - radius, y + height - radius, radius, 0.0, 90.0, 16);
        RenderUtils.setColor(result[1]);
        RenderUtils.drawArcOutline(x + radius, y + height - radius, radius, 90.0, 180.0, 16);
        RenderUtils.setColor(result[2]);
        RenderUtils.drawArcOutline(x + radius, y + radius, radius, 180.0, 270.0, 16);
        RenderUtils.setColor(result[3]);
        RenderUtils.drawArcOutline(x + width - radius, y + radius, radius, 270.0, 360.0, 16);
        RenderUtils.drawLine(x + radius, y, x + width - radius, y, lineWidth, result[2], result[3]);
        RenderUtils.drawLine(x + radius, y + height, x + width - radius, y + height, lineWidth, result[1], result[0]);
        RenderUtils.drawLine(x, y + radius, x, y + height - radius, lineWidth, result[1], result[2]);
        RenderUtils.drawLine(x + width, y + radius, x + width, y + height - radius, lineWidth, result[0], result[3]);
    }

    public static void drawRoundedRectangleOutline(double x, double y, double width, double height, double radius, float lineWidth, Color color) {
        RenderUtils.drawRoundedRectangleOutline(x, y, width, height, radius, lineWidth, GradientDirection.Normal, color, color);
    }

    public static void drawHalfRoundedRectangle(double x, double y, double width, double height, double radius, HalfRoundedDirection direction, Color color) {
        RenderUtils.setColor(color);
        if (radius > height) {
            radius = height;
        }
        if (direction == HalfRoundedDirection.Top) {
            RenderUtils.drawArc(x + radius, y + radius, radius, 180.0, 270.0, 32);
            RenderUtils.drawArc(x + width - radius, y + radius, radius, 270.0, 360.0, 32);
            RenderUtils.drawRect(x, y + radius, radius, height - radius, color);
            RenderUtils.drawRect(x + width - radius, y + radius, radius, height - radius, color);
            RenderUtils.drawRect(x + radius, y, width - radius * 2.0, height, color);
        } else if (direction == HalfRoundedDirection.Bottom) {
            RenderUtils.drawArc(x + radius, y + height - radius, radius, 90.0, 180.0, 32);
            RenderUtils.drawArc(x + width - radius, y + height - radius, radius, 0.0, 90.0, 32);
            RenderUtils.drawRect(x, y, radius, height - radius, color);
            RenderUtils.drawRect(x + width - radius, y, radius, height - radius, color);
            RenderUtils.drawRect(x + radius, y, width - radius * 2.0, height, color);
        } else if (direction == HalfRoundedDirection.Left) {
            RenderUtils.drawArc(x + radius, y + radius, radius, 180.0, 270.0, 32);
            RenderUtils.drawArc(x + radius, y + height - radius, radius, 90.0, 180.0, 32);
            RenderUtils.drawRect(x, y + radius, width, height - radius * 2.0, color);
            RenderUtils.drawRect(x + radius, y, width - radius, radius, color);
            RenderUtils.drawRect(x + radius, y + height - radius, width - radius, radius, color);
        } else if (direction == HalfRoundedDirection.Right) {
            RenderUtils.drawArc(x + width - radius, y + radius, radius, 270.0, 360.0, 32);
            RenderUtils.drawArc(x + width - radius, y + height - radius, radius, 0.0, 90.0, 32);
            RenderUtils.drawRect(x, y, width - radius, radius, color);
            RenderUtils.drawRect(x, y + height - radius, width - radius, radius, color);
            RenderUtils.drawRect(x, y + radius, width, height - radius * 2.0, color);
        }
    }

    public static GLTexture loadTexture(InputStream file) {
        try {
           PNGDecoder decoder = new PNGDecoder(file);
            ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            int id = GL11.glGenTextures();
            GL11.glBindTexture((int)3553, (int)id);
            GL11.glPixelStorei((int)3317, (int)1);
            GL11.glTexParameterf((int)3553, (int)10241, (float)9728.0f);
            GL11.glTexParameterf((int)3553, (int)10240, (float)9728.0f);
            GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)decoder.getWidth(), (int)decoder.getHeight(), (int)0, (int)6408, (int)5121, (ByteBuffer)buffer);
            GL11.glBindTexture((int)3553, (int)0);
            return new GLTexture(id);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void drawTexture(double x, double y, double width, double height) {
        RenderUtils.drawTexture(x, y, width, height, 255);
    }

    public static void drawTexture(double x, double y, double width, double height, int alpha) {
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3042);
        RenderUtils.setColor(255, 255, 255, alpha);
        GL11.glPushMatrix();
        GL11.glBegin((int)4);
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glDisable((int)3042);
    }

    public static void setViewPort(double x, double y, double width, double height) {
        GL41.glClearDepthf((float)1.0f);
        GL11.glClear((int)256);
        GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
        GL11.glDepthFunc((int)513);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        RenderUtils.drawRect(x, y, width, height, Color.WHITE);
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glDepthMask((boolean)true);
        GL11.glDepthFunc((int)514);
    }

    public static void clearViewPort() {
        GL41.glClearDepthf((float)1.0f);
        GL11.glClearColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glClear((int)1280);
        GL11.glDisable((int)2929);
        GL11.glDepthFunc((int)515);
        GL11.glDepthMask((boolean)false);
    }

    private static Color[] checkColorDirection(GradientDirection direction, Color start, Color end) {
        Object[] dir = new Color[4];
        if (direction == GradientDirection.Normal) {
            Arrays.fill(dir, start);
        } else if (direction == GradientDirection.DownToUp) {
            dir[0] = start;
            dir[1] = start;
            dir[2] = end;
            dir[3] = end;
        } else if (direction == GradientDirection.UpToDown) {
            dir[0] = end;
            dir[1] = end;
            dir[2] = start;
            dir[3] = start;
        } else if (direction == GradientDirection.RightToLeft) {
            dir[0] = start;
            dir[1] = end;
            dir[2] = end;
            dir[3] = start;
        } else if (direction == GradientDirection.LeftToRight) {
            dir[0] = end;
            dir[1] = start;
            dir[2] = start;
            dir[3] = end;
        } else {
            Arrays.fill(dir, Color.WHITE);
        }
        return (Color[]) dir;
    }

    public static void drawBox(AxisAlignedBB bb, Color color, int sides) {
        RenderUtils.drawBox(bb, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), sides);
    }

    public static void drawBox(AxisAlignedBB bb, int r, int g, int b, int a, int sides) {
        RenderUtils.drawBox((float)bb.minX, (float)bb.minY, (float)bb.minZ, (float)bb.maxX - (float)bb.minX, (float)bb.maxY - (float)bb.minY, (float)bb.maxZ - (float)bb.minZ, r, g, b, a, sides);
    }

    public static void drawBox(float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, int sides) {
        GL11.glPushMatrix();
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth((float)1.5f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.setColor(r, g, b, a);
        GL11.glBegin((int)7);
        if ((sides & 1) != 0) {
            GL11.glVertex3d((double)(x + w), (double)y, (double)z);
            GL11.glVertex3d((double)(x + w), (double)y, (double)(z + d));
            GL11.glVertex3d((double)x, (double)y, (double)(z + d));
            GL11.glVertex3d((double)x, (double)y, (double)z);
        }
        if ((sides & 2) != 0) {
            GL11.glVertex3d((double)(x + w), (double)(y + h), (double)z);
            GL11.glVertex3d((double)x, (double)(y + h), (double)z);
            GL11.glVertex3d((double)x, (double)(y + h), (double)(z + d));
            GL11.glVertex3d((double)(x + w), (double)(y + h), (double)(z + d));
        }
        if ((sides & 4) != 0) {
            GL11.glVertex3d((double)(x + w), (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)(y + h), (double)z);
            GL11.glVertex3d((double)(x + w), (double)(y + h), (double)z);
        }
        if ((sides & 8) != 0) {
            GL11.glVertex3d((double)x, (double)y, (double)(z + d));
            GL11.glVertex3d((double)(x + w), (double)y, (double)(z + d));
            GL11.glVertex3d((double)(x + w), (double)(y + h), (double)(z + d));
            GL11.glVertex3d((double)x, (double)(y + h), (double)(z + d));
        }
        if ((sides & 0x10) != 0) {
            GL11.glVertex3d((double)x, (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)(z + d));
            GL11.glVertex3d((double)x, (double)(y + h), (double)(z + d));
            GL11.glVertex3d((double)x, (double)(y + h), (double)z);
        }
        if ((sides & 0x20) != 0) {
            GL11.glVertex3d((double)(x + w), (double)y, (double)(z + d));
            GL11.glVertex3d((double)(x + w), (double)y, (double)z);
            GL11.glVertex3d((double)(x + w), (double)(y + h), (double)z);
            GL11.glVertex3d((double)(x + w), (double)(y + h), (double)(z + d));
        }
        GL11.glEnd();
        GlStateManager.enableCull();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public static void drawBoundingBox(AxisAlignedBB bb, float width, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2929);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        RenderUtils.setColor(color);
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glEnd();
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void prepare3D() {
        GL11.glPushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth((float)1.0f);
    }

    public static void release3D() {
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        MelonTessellator.releaseGL();
        GL11.glPopMatrix();
    }

    public static void renderESP(AxisAlignedBB axisAlignedBB, float size, Color color, RenderMode mode2) {
        double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0;
        double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0;
        double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0;
        double full = axisAlignedBB.maxX - centerX;
        double progressValX = full * (double)size;
        double progressValY = full * (double)size;
        double progressValZ = full * (double)size;
        AxisAlignedBB axisAlignedBB1 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
        GL11.glPushMatrix();
        switch (mode2) {
            case SOLID: {
                AxisAlignedBB bb = axisAlignedBB1;
                RenderUtils.drawBox(bb, color, 63);
                break;
            }
            case OUTLINE: {
                RenderUtils.drawBoundingBox(axisAlignedBB1, 1.5f, new Color(color.getRed(), color.getGreen(), color.getBlue(), 255));
                break;
            }
            case SOLID_FLAT: {
                RenderUtils.drawBox(axisAlignedBB1, color, 1);
                break;
            }
            case FULL: {
                AxisAlignedBB bb = axisAlignedBB1;
                RenderUtils.drawBox(bb, color, 63);
                RenderUtils.drawBoundingBox(axisAlignedBB1, 1.5f, new Color(color.getRed(), color.getGreen(), color.getBlue(), 255));
                break;
            }
        }
        GL11.glPopMatrix();
    }

    public static enum RenderMode {
        SOLID,
        SOLID_FLAT,
        FULL,
        OUTLINE;

    }

    public static enum HalfRoundedDirection {
        Top,
        Bottom,
        Left,
        Right;

    }

    public static enum GradientDirection {
        LeftToRight,
        RightToLeft,
        UpToDown,
        DownToUp,
        Normal;

    }
}

