package com.wizatar08.escapemaze.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public static final int WIDTH = 1024, HEIGHT = 768, TILE_SIZE = 64;
    private static float stretchedMultiplierW;
    private static float stretchedMultiplierH;
    public static float stretchedMultiplierTotal;
    public static boolean WINDOW_RESIZE;

    public static void begin() {

        try {
            Display.setTitle("Escape Maze <DEV>");
            Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
            Display.setResizable(true);
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }


        Properties prop = new Properties();
        try {
            InputStream file = new FileInputStream("src/resources/settings/settings.properties");
            prop.load(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WINDOW_RESIZE = Boolean.parseBoolean(prop.getProperty("updateWindow"));

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
        if (WINDOW_RESIZE) {
            stretchedMultiplierW = (Display.getWidth() / (float) WIDTH);
            stretchedMultiplierH = (Display.getHeight() / (float) HEIGHT);
            stretchedMultiplierTotal = Math.min(stretchedMultiplierW, stretchedMultiplierH);
        } else {
            stretchedMultiplierTotal = 1;
        }
        glViewport(0, 0, Math.round(WIDTH * stretchedMultiplierTotal), Math.round(HEIGHT * stretchedMultiplierTotal));
    }
}
