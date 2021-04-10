package com.wizatar08.escapemaze.helpers.ui;

import com.wizatar08.escapemaze.helpers.TextBlock;
import com.wizatar08.escapemaze.helpers.drawings.Tex;

public class Button {
    private String name;
    private Tex[] textures;
    private int[] rots;
    private int x, y, width, height;
    private TextBlock text;

    public Button(String name, Tex[] textures, int x, int y, int[] rots) {
        this(name, textures, x, y, rots, null);
    }

    public Button(String name, Tex[] textures, int x, int y, int[] rots, TextBlock text) {
        this.name = name;
        this.textures = textures;
        this.x = x;
        this.y = y;
        this.rots = rots;
        int biggestWidth = 1;
        int biggestHeight = 1;
        for (int i = 0; i < textures.length; i++) {
            if (textures[i].getOpenGLTex().getImageWidth() > biggestWidth) {
                biggestWidth = textures[i].getOpenGLTex().getImageWidth();
            }
            if (textures[i].getOpenGLTex().getImageHeight() > biggestHeight) {
                biggestHeight = textures[i].getOpenGLTex().getImageHeight();
            }
        }
        this.width = biggestWidth;
        this.height = biggestHeight;
        this.text = text;
    }


    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tex[] getTextures() {
        return textures;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public int[] getRots() {
        return rots;
    }

    public TextBlock getText() {
        return text;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void changeString(String newText) {
        text.setChars(newText);
    }
}
