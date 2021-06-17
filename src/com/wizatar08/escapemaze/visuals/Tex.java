package com.wizatar08.escapemaze.visuals;

import com.wizatar08.escapemaze.helpers.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.util.ArrayList;

import static com.wizatar08.escapemaze.visuals.Drawer.*;

public class Tex {
    private Texture texture;
    private String texturePath;
    private static final Color DEFAULT_COLOR = new Color(1.0f, 1.0f, 1.0f, 1.0f);

    /**
     * Creates a texture;
     * @param textureName
     */
    public Tex(String textureName) {
        this.texture = LoadPNG(textureName);
        this.texturePath = textureName;
    }

    public static Tex newInstance(Tex t) {
        return new Tex(t.getTexturePath());
    }

    /**
     * Draw an image on the screen in the specified coordinates
     * @param x
     * @param y
     */
    public void draw(float x, float y) {
        draw(x, y, 0);
    }

    /**
     * Draw a rotated texture
     * @param x
     * @param y
     * @param angle
     */
    public void draw(float x, float y, float angle) {
        draw(x, y, angle, DEFAULT_COLOR);
    }

    /**
     * Draw a texture with a color tint
     * @param x
     * @param y
     * @param color
     */
    public void draw(float x, float y, Color color) {
        draw(x, y, 0, color);
    }

    /**
     * Draw a texture with angle and color tint customization
     * @param x
     * @param y
     * @param angle
     * @param color
     */
    public void draw(float x, float y, float angle, Color color) {
        draw(x, y, texture.getImageWidth(), 0, 1, 0, 1, angle, color);
    }

    /**
     * Draw a texture with angle and a set width
     * @param x
     * @param y
     * @param customWidth
     */
    public void draw(float x, float y, double customWidth) {
        draw(x, y, (float) customWidth, 0, 1, 0, 1, 0, DEFAULT_COLOR);
    }

    /**
     * Draw a portion of a texture
     * @param x
     * @param y
     * @param leftX
     * @param rightX
     * @param topY
     * @param bottomY
     */
    public void draw(float x, float y, float leftX, float rightX, float topY, float bottomY) {
        draw(x, y, texture.getImageWidth() * (rightX - leftX), leftX, rightX, topY, bottomY, 0, DEFAULT_COLOR);
    }

    public void draw(float x, float y, float customWidth, float leftX, float rightX, float topY, float bottomY, float angle, Color color) {
        drawQuadTex(texture, x, y, customWidth, texture.getImageHeight() * (bottomY - topY), angle, topY, bottomY, leftX, rightX, color.r, color.g, color.b, color.a);
    }

    public Texture getOpenGLTex() {
        return texture;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public Tex copy() {
        return new Tex(texturePath);
    }
}
