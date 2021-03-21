package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.interfaces.TileEntity;
import com.wizatar08.escapemaze.menus.Editor;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;
import static com.wizatar08.escapemaze.helpers.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

public class Tile implements Entity, TileEntity {
    private float x, y, initialX, initialY;
    private int width, height;
    private Texture texture, unlockedTexture[], unsecureTexture[];
    private Texture overlapTexture[];
    private int overlapTexRot[], unlockedTexRot[], unsecureTexRot[];
    private TileType type;
    private boolean isSecurityComputer, isEscapeDoor, canBeSeen;
    private ItemType unlockableBy;
    private boolean isPassable, isLocked, isDoor, isSecureTile, isSecure;

    public Tile(float x, float y, int width, int height, TileType type){
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.height = height;
        this.width = width;
        this.type = type;
        this.isPassable = type.isPassable();
        this.texture = LoadPNG("tiles/" + type.getTexture());
        this.overlapTexture = type.getOverlayTex();
        this.overlapTexRot = type.getOverlayTexRot();
        this.isSecurityComputer = type.isSecurityComputer();
        this.isEscapeDoor = type.isEscapeDoor();
        this.unlockableBy = type.unlockableBy();
        this.unlockedTexture = type.getUnlockedTileTexture();
        this.unlockedTexRot = type.getUnlockedTileTextureRots();
        this.unsecureTexture = type.getDeactivatedTileTexture();
        this.unsecureTexRot = type.getDeactivatedTextureRots();
        this.isSecureTile = type.isSecure();
        this.isSecure = isSecureTile;
        this.isDoor = this.unlockableBy != ItemType.NULL;
    }

    public void draw(){
        setX(initialX + Editor.displacementX);
        setY(initialY + Editor.displacementY);
        if (x + Game.DIS_X > -TILE_SIZE && x + Game.DIS_X < WIDTH + TILE_SIZE && y + Game.DIS_Y > -TILE_SIZE && y + Game.DIS_Y < HEIGHT + TILE_SIZE) {
            drawQuadTex(texture, x + Game.DIS_X, y + Game.DIS_Y, width, height);
            canBeSeen = true;
            if (isSecureTile && !isSecure) {
                if (unsecureTexture != null) {
                    for (int i = 0; i < unsecureTexture.length; i++) {
                        drawQuadTex(unsecureTexture[i], x + Game.DIS_X, y + Game.DIS_Y, width, height, unsecureTexRot[i]);
                    }
                }
            } else if (!isDoor || (isDoor && !isPassable)) {
                if (overlapTexture != null) {
                    for (int i = 0; i < overlapTexture.length; i++) {
                        drawQuadTex(overlapTexture[i], x + Game.DIS_X, y + Game.DIS_Y, width, height, overlapTexRot[i]);
                    }
                }
            } else {
                if (unlockedTexture != null) {
                    for (int i = 0; i < unlockedTexture.length; i++) {
                        drawQuadTex(unlockedTexture[i], x + Game.DIS_X, y + Game.DIS_Y, width, height, unlockedTexRot[i]);
                    }
                }
            }
        } else {
            canBeSeen = false;
        }
    }

    public void update() {
    }

    public void unlock() {
        isPassable = true;
    }
    public void lock() {
        isPassable = false;
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
    public void setWidth(float width) {
        this.width = (int) width;
    }
    public void setHeight(float height) {
         this.height = (int) height;
    }
    public boolean canPass() {
        return this.isPassable;
    }
    public boolean isHarmful() {
        return false;
    }
    public boolean canHide() {
        return false;
    }
    public boolean isSecurityComputer() {
        return isSecurityComputer;
    }
    public boolean isEscapeDoor() {
        return isEscapeDoor;
    }
    public boolean isLockedDoor() {
        return this.type.unlockableBy() != ItemType.NULL;
    }
    public ItemType getKeyToDoor() {
        return this.type.unlockableBy();
    }
    public boolean isSeen() {
        return canBeSeen;
    }

    public ItemType unlockableBy() {
        return unlockableBy;
    }

    public boolean isSecureTile() {
        return isSecureTile;
    }

    public boolean isSecure() {
        return isSecure;
    }
    public void setSecurity(boolean set) {
        isSecure = set;
    }
}
