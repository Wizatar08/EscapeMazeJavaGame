package com.wizatar08.escapemaze.helpers;

import apple.laf.JRSUIUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.Project;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Drawer {
    public static java.awt.Font FONT;
    public static TrueTypeFont TRUE_TYPE_FONT;

    public static void createFont() {
        // load font from file
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("src/resources/font/game_font.ttf");

            FONT = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            FONT = FONT.deriveFont(24f); // set font size

            TRUE_TYPE_FONT = new TrueTypeFont(FONT, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        try{
            InputStream inputStream = ResourceLoader.getResourceAsStream("resources/font/game_font.ttf");
            FONT = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            FONT = FONT.deriveFont(24f); // set font size
            TRUE_TYPE_FONT = new TrueTypeFont(FONT, true);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

         */
    }

    public static boolean checkCollision(float x1, float y1, float width1, float height1, float x2, float y2, float width2, float height2) {
        return x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2;
    }

    public static void drawQuad(float x, float y, float width, float height){
        glBegin(GL_QUADS);
        glVertex2f(x, y); // TOp left
        glVertex2f(x + width, y); // Top right
        glVertex2f(x + width, y + height); // Bottom right
        glVertex2f(x, y + height); // Bottom right
        glEnd();
    }

    public static void drawLine(float x1, float y1, float x2, float y2) {
        glBegin(GL_LINES);
        glVertex2f(x1, y1);
        glVertex2f(x2, y2);
        glEnd();
    }

    /**
     * Use this <code>drawQuadTex</code> to load an image using the default image's height and width.
     * @param tex Make sure to have a <code>Texture</code> variable initialized and not use <code>LoadPNG()</code> as the argument, otherwise a crash and freeze will occur over time.
     * @param x x position
     * @param y y position
     */
    public static void drawQuadTex(Texture tex, float x, float y) {
        drawQuadTex(tex, x, y, tex.getImageWidth(), tex.getImageHeight());
    }

    /**
     * This method will take a <code>Texture</code> object, which will be the image file, and display it on the screen at the desired location. To get a <code>Texture</code> object, use the method <code>LoadPNG()</code> in this class. The height and width of image should be equal to the actual image's height and width, otherwise the stretching of images may occur. Alternatively, you can use the other <code>drawQuadTex</code> method, the one that does not require height and width.
     *
     * @param tex Make sure to have a <code>Texture</code> variable initialized and not use <code>LoadPNG()</code> as the argument, otherwise a crash and freeze will occur over time.
     * @param x x position
     * @param y y position
     * @param width width of image
     * @param height height of image
     */
    public static void drawQuadTex(Texture tex, float x, float y, float width, float height) {
        drawQuadTex(tex, x, y, width, height, 0);
    }

    /**
     * If you want to rotate an image, use this <code>drawQuadTex</code> method.
     * @param tex Make sure to have a <code>Texture</code> variable initialized and not use <code>LoadPNG()</code> as the argument, otherwise a crash and freeze will occur over time.
     * @param x x position
     * @param y y position
     * @param width width of image
     * @param height height of image
     * @param angle angle of rotation
     */
    public static void drawQuadTex(Texture tex, float x, float y, float width, float height, float angle) {
        tex.bind();
        glTranslatef(x + width / 2, y + height / 2, 0);
        glRotatef(angle, 0, 0, 1);
        glTranslatef(-width / 2, -height / 2, 0);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0, 0);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }

    public static Texture LoadTexture(String path, String fileType) {
        Texture tex = null;
        InputStream in;
        try {
            in = ResourceLoader.getResourceAsStream(path);
        } catch (RuntimeException e) {
            in = ResourceLoader.getResourceAsStream("src/resources/images/null.png");
        }
        try {
            tex = TextureLoader.getTexture(fileType.toUpperCase(), in);
        } catch (IOException e){
            e.printStackTrace();
        }
        return tex;
    }
    public static Texture LoadPNG(String name) {
        if (name != null) {
            Texture tex = null;
            tex = LoadTexture("src/resources/images/" + name + ".png", "png");
            return tex;
        }
        return null;
    }
    public static Texture LoadGIF(String name) {
        if (name != null) {
            Texture tex = null;
            tex = LoadTexture("src/resources/images/" + name + ".gif", "gif");
            return tex;
        }
        return null;
    }


}
