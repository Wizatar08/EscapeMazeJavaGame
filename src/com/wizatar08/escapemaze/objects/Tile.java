package com.wizatar08.escapemaze.objects;

import com.wizatar08.escapemaze.enumerators.TileType;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.interfaces.TileEntity;
import org.newdawn.slick.opengl.Texture;
import static com.wizatar08.escapemaze.helpers.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

public class Tile implements Entity, TileEntity {
    private float x, y;
    private int width, height;
    private Texture texture;
    private Texture overlapTexture;
    private int overlapTexRot;
    private TileType type;

    public Tile(float x, float y, int width, int height, TileType type){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.type = type;
        this.texture = LoadPNG("tiles/" + type.getTexture());
        this.overlapTexture = type.getOverlayTex();
        this.overlapTexRot = type.getOverlayTexRot();
    }

    public void draw(){
        drawQuadTex(texture, x, y, width, height);
        if (overlapTexture != null) {
            drawQuadTex(overlapTexture, x, y, width, height, overlapTexRot);
        }
    }

    // Getters
    public int getHeight() {
        return height;
    }



    public int getWidth() {
        return width;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public Texture getTexture() {
        return texture;
    }
    public TileType getType() {
        return type;
    }
    public int getXPlace() {
        return (int) x / TILE_SIZE;
    }
    public int getYPlace() {
        return (int) y / TILE_SIZE;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
         this.height = height;
    }
    public boolean canPass() {
        return false;
    }
    public boolean isHarmful() {
        return false;
    }
    public boolean canHide() {
        return false;
    }
}
