package com.wizatar08.escapemaze.visuals;

import com.wizatar08.escapemaze.helpers.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static com.wizatar08.escapemaze.visuals.Drawer.LoadPNG;
import static com.wizatar08.escapemaze.visuals.Drawer.drawQuadTex;

public class AnimatedTex extends Tex {
    private final int imageHeight, totalFrames;
    private int frame;
    private boolean fade;
    private float secondsBetweenFrames;
    private final Timer timer;
    private Texture texture;
    private String texturePath;

    /**
     * Creates an animated texture with default values
     * @param textureName
     * @param imageHeight
     */
    public AnimatedTex(String textureName, int imageHeight) {
        this(textureName, imageHeight, 1);
    }

    /**
     * Creates a sudden transition animated texture where you can specify seconds between frames
     * @param textureName
     * @param imageHeight
     * @param secondsBetweenFrames
     */
    public AnimatedTex(String textureName, int imageHeight, float secondsBetweenFrames) {
        this(textureName, imageHeight, secondsBetweenFrames, false);
    }

    /**
     * Creates an animated texture with all customization options
     * @param textureName
     * @param imageHeight
     * @param secondsBetweenFrames
     * @param fade
     */
    public AnimatedTex(String textureName, int imageHeight, float secondsBetweenFrames, boolean fade) {
        super(textureName);
        this.texture = LoadPNG(textureName);
        this.texturePath = textureName;
        this.imageHeight = imageHeight;
        this.secondsBetweenFrames = secondsBetweenFrames;
        this.fade = fade;
        this.timer = new Timer(Timer.TimerModes.COUNT_DOWN, secondsBetweenFrames);
        this.frame = (int) Math.floor(texture.getImageHeight() / imageHeight);
        this.totalFrames = frame;
        this.timer.unpause();
    }

    private int getNextFrameNum() {
        int f = frame;
        f++;
        if (f >= totalFrames) {
            f = 0;
        }
        return f;
    }

    @Override
    public void draw(float x, float y, float customWidth, float leftX, float rightX, float topY, float bottomY, float angle, Color color) {
        float calculatedTopY = topY * (1.0f / totalFrames);
        float calculatedBottomY = bottomY * (1.0f / totalFrames);
        timer.update();
        if (timer.getTotalSeconds() <= 0 || timer.getTotalSeconds() > 256) {
            timer.setTime(timer.getStartingSeconds());
            frame = getNextFrameNum();
        }
        drawQuadTex(texture, x, y, customWidth, imageHeight * (bottomY - topY), angle, calculatedTopY + ((1.0f / totalFrames) * frame - 1), calculatedBottomY + ((1.0f / totalFrames) * frame - 1), leftX, rightX, color.r, color.g, color.b, color.a);
        if (fade) {
            drawQuadTex(texture, x, y, customWidth, imageHeight * (bottomY - topY), angle, calculatedTopY + ((1.0f / totalFrames) * getNextFrameNum()), calculatedBottomY + ((1.0f / totalFrames) * getNextFrameNum()), leftX, rightX, color.r, color.g, color.b, color.a * ((timer.getStartingSeconds() - timer.getTotalSeconds()) / timer.getStartingSeconds()));
        }
    }

    public int getImageHeight() {
        return imageHeight;
    }
    public float getSecondsBetweenFrames() {
        return secondsBetweenFrames;
    }
    public boolean isFading() {
        return fade;
    }
}
