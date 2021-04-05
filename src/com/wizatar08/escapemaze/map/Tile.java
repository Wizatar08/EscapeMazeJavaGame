package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.interfaces.TileEntity;
import com.wizatar08.escapemaze.map.tile_types.*;
import com.wizatar08.escapemaze.menus.Editor;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

import java.lang.reflect.InvocationTargetException;

import static com.wizatar08.escapemaze.helpers.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

public class Tile implements Entity, TileEntity {
    private float x, y, initialX, initialY;
    private int width, height;
    private String id;
    private final Texture defaultTexture;
    private final Texture[] activeTexture, defaultOverlapTexture;
    private final int[] defaultOverlapTexRots, activeTextureRots, requiredPassLevels;
    private final TileType type;
    private boolean isActive, activeInfluencesPassable, canBeSeen;
    private final boolean canPass, hasMultipleTexs;
    private Game game;
    private Class<? extends Tile> subClass;

    public Tile(Game game, float x, float y, int width, int height, TileType type) {
        this.game = game;
        this.id = type.getId();
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.height = height;
        this.width = width;
        this.type = type;
        this.subClass = type.getSubClass();
        this.canPass = type.isPassable();
        this.defaultTexture = LoadPNG("tiles/" + type.getTexture());
        this.defaultOverlapTexture = type.getOverlayTex();
        this.defaultOverlapTexRots = type.getOverlayTexRot();
        this.activeTexture = type.getActiveTileTexture();
        this.activeTextureRots = type.getActiveTileTextureRots();
        this.isActive = type.startsActive();
        this.activeInfluencesPassable = type.activeInfluencesPassable();
        this.requiredPassLevels = type.cardPassesNeeded();
        this.hasMultipleTexs = type.getActiveTileTexture() != null;
    }

    public boolean testIfPassable() {
        return canPass && (!activeInfluencesPassable || !isActive);
    }

    public void draw(){
        setX(initialX + Editor.displacementX);
        setY(initialY + Editor.displacementY);
        if (x + Game.DIS_X > -TILE_SIZE && x + Game.DIS_X < WIDTH + TILE_SIZE && y + Game.DIS_Y > -TILE_SIZE && y + Game.DIS_Y < HEIGHT + TILE_SIZE) {
            drawQuadTex(defaultTexture, x + Game.DIS_X, y + Game.DIS_Y, width, height);
            canBeSeen = true;
            if (hasMultipleTexs && !isActive){
                for (int i = 0; i < activeTexture.length; i++) {
                    drawQuadTex(activeTexture[i], x + Game.DIS_X, y + Game.DIS_Y, width, height, activeTextureRots[i]);
                }
            } else {
                if (defaultOverlapTexture != null) {
                    for (int i = 0; i < defaultOverlapTexture.length; i++) {
                        drawQuadTex(defaultOverlapTexture[i], x + Game.DIS_X, y + Game.DIS_Y, width, height, defaultOverlapTexRots[i]);
                    }
                }
            }
        } else {
            canBeSeen = false;
        }
    }

    /**
     * Overridden in subclasses. Used to constantly update this tile.
     */
    public void update() {}
    /**
     * Overridden in subclasses. Used to determine if a player is NEAR (Not on) this tile.
     */
    public void playerNearTile() {}
    public boolean isOnTile() {
        Player player = game.getCurrentPlayer();
        return checkCollision(player.getX(), player.getY(), player.getWidth(), player.getHeight(), x + 16, y + 16, x + width - 16, y - height - 16);
    }

    /**
     * Overridden in subclasses. Used to carry out a function when a player presses the USE button when on the tile.
     */
    public void useTile() {}

    /**
     * Overridden in subclasses. Used to carry out a function when a player is ontop of this tile.
     */
    public void onTile() {}

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
        return defaultTexture;
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
    public boolean isSeen() {
        return canBeSeen;
    }
    public int[] getRequiredPassLevels() {
        return requiredPassLevels;
    }
    public String getId() {
        return id;
    }
    public void setActive(boolean lock) {
        this.isActive = lock;
    }
    public boolean isActive() {
        return isActive;
    }
    public Class<? extends Tile> getSubClass() {
        return subClass;
    }
}
