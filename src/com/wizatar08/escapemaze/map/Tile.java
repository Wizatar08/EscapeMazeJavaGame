package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.interfaces.TileEntity;
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
    private boolean isActive, canBeSeen;
    private final boolean canPass;
    private final Function function;
    private Game game;

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
        this.function = type.getFunction();
        this.canPass = type.isPassable();
        this.defaultTexture = LoadPNG("tiles/" + type.getTexture());
        this.defaultOverlapTexture = type.getOverlayTex();
        this.defaultOverlapTexRots = type.getOverlayTexRot();
        this.activeTexture = type.getActiveTileTexture();
        this.activeTextureRots = type.getActiveTileTextureRots();
        this.isActive = function.STARTS_ACTIVE;
        this.requiredPassLevels = type.cardPassesNeeded();

    }

    public enum Function {
        SIMPLE_WALL(false, false),
        SAFE_SPOT(false, false),
        EXIT_SPOT(false, false),
        ITEM_UNLOCK_DOOR(true, true),
        AUTHORITY_DOOR(true, true),
        LASER_SECURE(true, false),
        PRESSURE_PLATE(true, false),
        MAIN_COMPUTER(false, false),
        PRESSURE_PLATE_COMPUTER(true, false);

        public final boolean STARTS_ACTIVE, ACTIVE_INFLUENCES_PASSABLE;

        Function(boolean startsActive, boolean activeInfluencesPassable) {
            this.STARTS_ACTIVE = startsActive;
            this.ACTIVE_INFLUENCES_PASSABLE = activeInfluencesPassable;
        }
    }

    public boolean testIfPassable() {
        return canPass && (!function.ACTIVE_INFLUENCES_PASSABLE || !isActive);
    }

    public void draw(){
        setX(initialX + Editor.displacementX);
        setY(initialY + Editor.displacementY);
        if (x + Game.DIS_X > -TILE_SIZE && x + Game.DIS_X < WIDTH + TILE_SIZE && y + Game.DIS_Y > -TILE_SIZE && y + Game.DIS_Y < HEIGHT + TILE_SIZE) {
            drawQuadTex(defaultTexture, x + Game.DIS_X, y + Game.DIS_Y, width, height);
            canBeSeen = true;
            // Add computer textures here
            if ((function == Function.PRESSURE_PLATE_COMPUTER
                    && !game.pressurePlatesActive()) ||
                    // Door functionality here
                    (function == Function.LASER_SECURE && !isActive) ||
                    (function == Function.ITEM_UNLOCK_DOOR && !isActive) ||
                    (function == Function.AUTHORITY_DOOR && !isActive)) {
                if (activeTexture != null) {
                    for (int i = 0; i < activeTexture.length; i++) {
                        drawQuadTex(activeTexture[i], x + Game.DIS_X, y + Game.DIS_Y, width, height, activeTextureRots[i]);
                    }
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

    public void update() {
    }

    public void unlockDoor() {
        isActive = false;
    }
    public void lockDoor() {
        isActive = true;
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
    public boolean canPass() {
        return canPass;
    }
    public boolean isHarmful() {
        return false;
    }
    public boolean canHide() {
        return false;
    }
    public boolean isSecurityComputer() {
        return function == Function.MAIN_COMPUTER;
    }
    public boolean isEscapeDoor() {
        return function == Function.EXIT_SPOT;
    }
    public boolean isLockedDoor() {
        return function == Function.ITEM_UNLOCK_DOOR;
    }
    public boolean isAuthorityDoor() {
        return function == Function.AUTHORITY_DOOR;
    }
    public boolean isAuthorityDoorLocked() {
        return isActive;
    }
    public boolean isSeen() {
        return canBeSeen;
    }

    public boolean isSecureTile() {
        return function == Function.LASER_SECURE;
    }

    public int[] getRequiredPassLevels() {
        return requiredPassLevels;
    }

    public String getId() {
        return id;
    }

    public Function getFunction() {
        return function;
    }

    public void setActive(boolean lock) {
        this.isActive = lock;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isPressurePlateComputer() {
        return function == Function.PRESSURE_PLATE_COMPUTER;
    }
    public boolean isPressurePlate() {
        return function == Function.PRESSURE_PLATE;
    }
}
