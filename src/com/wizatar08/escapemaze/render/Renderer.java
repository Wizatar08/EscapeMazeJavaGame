package com.wizatar08.escapemaze.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public static final int WIDTH = 1024, HEIGHT = 768, TILE_SIZE = 64;
    private static float stretchedMultiplierW;
    private static float stretchedMultiplierH;
    public static float stretchedMultiplierTotal;

    public static void begin() {

        try {
        Display.setTitle("Escape Maze <DEV>"); // Set titletry {
        Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
        Display.create();
        Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        glViewport(0, 0, WIDTH, HEIGHT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void loop() {
        stretchedMultiplierW = (Display.getWidth() / (float) WIDTH);
        stretchedMultiplierH = (Display.getHeight() / (float) HEIGHT);
        stretchedMultiplierTotal = Math.min(stretchedMultiplierW, stretchedMultiplierH);
        glViewport(0, 0, Math.round(WIDTH * stretchedMultiplierTotal), Math.round(HEIGHT * stretchedMultiplierTotal));
    }
}
