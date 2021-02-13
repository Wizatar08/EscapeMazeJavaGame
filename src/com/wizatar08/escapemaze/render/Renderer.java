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
    public static float xOffset = 0;
    public static float yOffset = 0;

    public static void begin() {
        Display.setTitle("Escape Maze <DEV>"); // Set title
        try {

            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setResizable(true);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
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
        //xOffset = (Display.getWidth() / 2) - (WIDTH / 2);
        //yOffset = (Display.getHeight() / 2) - (HEIGHT / 2);
        stretchedMultiplierW = (Display.getWidth() / (float) WIDTH);
        stretchedMultiplierH = (Display.getHeight() / (float) HEIGHT);
        stretchedMultiplierTotal = Math.min(stretchedMultiplierW, stretchedMultiplierH);
        //System.out.println(stretchedMultiplierW + ", " + stretchedMultiplierH + ", " + stretchedMultiplierTotal);
        glViewport(0, 0, Math.round(WIDTH * stretchedMultiplierTotal), Math.round(HEIGHT * stretchedMultiplierTotal));
    }
}
