package com.wizatar08.escapemaze.helpers;

import apple.laf.JRSUIUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;

public class Drawer {

    public static boolean checkCollision(float x1, float y1, float width1, float height1, float x2, float y2, float width2, float height2) {
        if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2) {
            return true;
        }
        return false;
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

    public static void drawQuadTex(Texture tex, float x, float y, float width, float height) {
        tex.bind();
        glTranslatef(x, y, 0);
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
        InputStream in = ResourceLoader.getResourceAsStream(path);
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
            tex = LoadTexture("resources/images/" + name + ".png", "png");
            return tex;
        }
        return null;
    }


}
