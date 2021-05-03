package com.wizatar08.escapemaze.helpers.visuals;

import com.wizatar08.escapemaze.helpers.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static com.wizatar08.escapemaze.helpers.visuals.Drawer.*;

public class Tex {
    private final int imageHeight, totalFrames;
    private int frame;
    private boolean fade;
    private float secondsBetweenFrames;
    private final Timer timer;
    private Texture texture;
    private String texturePath;

    /**
     * Creates an unanimated texture;
     * @param textureName
     */
    public Tex(String textureName) {
        this(textureName, LoadPNG(textureName).getImageHeight());
    }

    /**
     * Creates an animated texture with default values
     * @param textureName
     * @param imageHeight
     */
    public Tex(String textureName, int imageHeight) {
        this(textureName, imageHeight, 1);
    }

    /**
     * Creates a sudden transition animated texture where you can specify seconds between frames
     * @param textureName
     * @param imageHeight
     * @param secondsBetweenFrames
     */
    public Tex(String textureName, int imageHeight, float secondsBetweenFrames) {
        this(textureName, imageHeight, secondsBetweenFrames, false);
    }

    /**
     * Creates an animated texture with all customization options
     * @param textureName
     * @param imageHeight
     * @param secondsBetweenFrames
     * @param fade
     */
    public Tex(String textureName, int imageHeight, float secondsBetweenFrames, boolean fade) {
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

    public static Tex newInstance(Tex t) {
        return new Tex(t.getTexturePath(), t.getImageHeight(), t.getSecondsBetweenFrames(), t.isFading());
    }

    public static Tex[] getTexInFolder(String folderName) {
        File folder = new File("src/resources/images/" + folderName).getAbsoluteFile();
        File[] files = folder.listFiles();
        System.out.println(folder);
        ArrayList<Tex> texArray = new ArrayList<>();
        for (File file : files) {
            if (file.toString().endsWith(".png")) {
                texArray.add(new Tex(folderName + "/" + file.getName()));
            }
        }
        Tex[] texList = new Tex[texArray.size()];
        for (int i = 0; i < texArray.size(); i++) {
            texList[i] = texArray.get(i);
            System.out.println(texList[i]);
        }
        return texList;
    }

    public int getNextFrameNum() {
        int f = frame;
        f++;
        if (f >= totalFrames) {
            f = 0;
        }
        return f;
    }

    public void draw(float x, float y) {
        draw(x, y, 0);
    }

    public void draw(float x, float y, float angle) {
        draw(x, y, angle, texture.getImageWidth());
    }

    public void draw(float x, float y, float angle, float width) {
        draw(x, y, angle, width, imageHeight);
    }

    public void draw(float x, float y, float angle, float width, float height) {
        draw(x, y, angle, width, height, new Color(1.0f, 1.0f, 1.0f));
    }

    public void draw(float x, float y, Color color) {
        draw(x, y, 0, texture.getImageWidth(), texture.getImageHeight(), color);
    }

    public void draw(float x, float y, float angle, float width, float height, Color color) {
        timer.update();
        if (timer.getTotalSeconds() <= 0 || timer.getTotalSeconds() > 256) {
            timer.setTime(timer.getStartingSeconds());
            frame = getNextFrameNum();
        }
        drawQuadTex(texture, x, y, width, height, angle, (1.0f / totalFrames) * frame, (1.0f / totalFrames) * frame + (1.0f / totalFrames), color.r, color.g, color.b, 1.0f);
        if (fade) {
            drawQuadTex(texture, x, y, width, height, angle, (1.0f / totalFrames) * getNextFrameNum(), (1.0f / totalFrames) * getNextFrameNum() + (1.0f / totalFrames), color.r, color.g, color.b, ((timer.getStartingSeconds() - timer.getTotalSeconds()) / timer.getStartingSeconds()));
        }
    }

    public Texture getOpenGLTex() {
        return texture;
    }

    public String getTexturePath() {
        return texturePath;
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
