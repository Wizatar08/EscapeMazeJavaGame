package com.wizatar08.escapemaze.visuals;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;

public class Drawer {
    public static java.awt.Font FONT;
    public static TrueTypeFont TRUE_TYPE_FONT;

    public static void createFont() {
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("src/resources/font/game_font.ttf");

            FONT = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            FONT = FONT.deriveFont(24f); // set font size

            TRUE_TYPE_FONT = new TrueTypeFont(FONT, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void drawQuadTex(Texture tex, float x, float y, float width, float height, float angle, float topY, float bottomY) {
        drawQuadTex(tex, x, y, width, height, angle, topY, bottomY, 1.0f);
    }

    public static void drawQuadTex(Texture tex, float x, float y, float width, float height, float angle, float topY, float bottomY, float alpha) {
        drawQuadTex(tex, x, y, width, height, angle, topY, bottomY, 1.0f, 1.0f, 1.0f, alpha);
    }

    public static void drawQuadTex(Texture tex, float x, float y, float width, float height, float angle, float topY, float bottomY, float r, float g, float b, float alpha) {
        tex.bind();
        glTranslatef(x + width / 2, y + height / 2, 0);
        glColor4f(r, g, b, alpha);
        glRotatef(angle, 0, 0, 1);
        glScalef(1, -1, 0); // For some reason the new way of using textures in this game flips all the textures vertically, so we have to flip it back.
        glTranslatef(-width / 2, -height / 2, 0);
        glBegin(GL_QUADS);
        glClear(GL_COLOR_BUFFER_BIT);
        glTexCoord2f(0, bottomY); glVertex2f(0, 0);
        glTexCoord2f(1, bottomY); glVertex2f(width, 0);
        glTexCoord2f(1, topY); glVertex2f(width, height);
        glTexCoord2f(0, topY); glVertex2f(0, height);
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
}
