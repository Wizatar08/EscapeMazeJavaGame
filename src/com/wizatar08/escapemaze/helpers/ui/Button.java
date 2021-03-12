package com.wizatar08.escapemaze.helpers.ui;

import org.newdawn.slick.opengl.Texture;

public class Button {
    private String name;
    private Texture[] textures;
    private int[] rots;
    private int x, y, width, height;
    private String text;

    public Button(String name, Texture[] textures, int x, int y, int[] rots) {
        this(name, textures, x, y, rots, "", 0, 0);
    }

    public Button(String name, Texture[] textures, int x, int y, int[] rots, String text, int textX, int textY) {
        this.name = name;
        this.textures = textures;
        this.x = x;
        this.y = y;
        this.rots = rots;
        int biggestWidth = 1;
        int biggestHeight = 1;
        for (int i = 0; i < textures.length; i++) {
            if (textures[i].getImageWidth() > biggestWidth) {
                biggestWidth = textures[i].getImageWidth();
            }
            if (textures[i].getImageHeight() > biggestHeight) {
                biggestHeight = textures[i].getImageHeight();
            }
        }
        this.width = biggestWidth;
        this.height = biggestHeight;
    }


    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Texture[] getTextures() {
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
