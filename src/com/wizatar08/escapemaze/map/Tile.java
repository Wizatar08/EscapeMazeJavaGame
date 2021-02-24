package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.enumerators.TileType;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.interfaces.TileEntity;
import com.wizatar08.escapemaze.menus.Editor;
import org.newdawn.slick.opengl.Texture;
import static com.wizatar08.escapemaze.helpers.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

public class Tile implements Entity, TileEntity {
    private float x, y, initialX, initialY;
    private int width, height;
    private Texture texture;
    private Texture overlapTexture[];
    private int overlapTexRot[];
    private TileType type;
    private SafeSpots safeSpotDir;
    private SafeSpot safeSpot;

    public Tile(float x, float y, int width, int height, TileType type){
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.height = height;
        this.width = width;
        this.type = type;
        this.texture = LoadPNG("tiles/" + type.getTexture());
        this.overlapTexture = type.getOverlayTex();
        this.overlapTexRot = type.getOverlayTexRot();
        this.safeSpotDir = type.getSafeSpot();
    }

    public void draw(){
        setX(initialX + Editor.displacementX);
        setY(initialY + Editor.displacementY);
        drawQuadTex(texture, x, y, width, height);
        if (overlapTexture != null) {
            for (int i = 0; i < overlapTexture.length; i++) {
                drawQuadTex(overlapTexture[i], x, y, width, height, overlapTexRot[i]);
            }
        }
    }

    public void update() {
        draw();
    }

    private void createSafeSpot() {

    }

    // Getters
    public float getHeight() {
        return height;
    }



    public float getWidth() {
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
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
         this.height = height;
    }
    public boolean canPass() {
        return this.type.isPassable();
    }
    public boolean isHarmful() {
        return false;
    }
    public boolean canHide() {
        return false;
    }
}
