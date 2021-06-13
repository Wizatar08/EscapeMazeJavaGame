package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.helpers.Hitbox;
import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.interfaces.TileEntity;
import com.wizatar08.escapemaze.menus.Editor;
import com.wizatar08.escapemaze.menus.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static com.wizatar08.escapemaze.visuals.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

public class Tile implements Entity, TileEntity {
    private float x, y, initialX, initialY;
    private int width, height;
    private String id;
    private final Tex defaultTexture;
    private final Tex[] activeTexture, defaultOverlapTexture;
    private final int[] defaultOverlapTexRots, activeTextureRots, requiredPassLevels;
    private final TileType type;
    private boolean isActive, activeInfluencesPassable, canBeSeen;
    private final boolean canPass, hasMultipleTexs;
    private Game game;
    private Class<? extends Tile> subClass;
    private Condition rayTraceSeeable;
    private Tex.Cluster tileDecorations;
    private Hitbox hitBox;
    private RegularTileTextureSettings textureSettings;

    public Tile(Game game, float x, float y, int width, int height, TileType type) {
        this.game = game;
        this.id = type.getId();
        this.rayTraceSeeable = type.isRayTraceSeeable();
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.height = height;
        this.width = width;
        this.type = type;
        this.subClass = type.getSubClass();
        this.canPass = type.isPassable();
        this.defaultTexture = Tex.newInstance(type.getTexture()[(int) Math.floor(Math.random() * type.getTexture().length)]);
        this.defaultOverlapTexture = type.getOverlayTex();
        this.defaultOverlapTexRots = type.getOverlayTexRot();
        this.activeTexture = type.getActiveTileTexture();
        this.activeTextureRots = type.getActiveTileTextureRots();
        this.isActive = type.startsActive();
        this.activeInfluencesPassable = type.activeInfluencesPassable();
        this.requiredPassLevels = type.cardPassesNeeded();
        if (type.getTileDecorations() != null) {
            this.tileDecorations = new Tex.Cluster((Tex[]) type.getTileDecorations()[0], (int) type.getTileDecorations()[1], (int) type.getTileDecorations()[2], (int) type.getTileDecorations()[3]);
        }
        this.hasMultipleTexs = type.getActiveTileTexture() != null;
        this.hitBox = new Hitbox(this, type.getHitbox()[0], type.getHitbox()[1], type.getHitbox()[2], type.getHitbox()[3]);
        this.textureSettings = type.getTextureSettings();
    }

    public enum Condition {
        ALWAYS, NONE, ONLY_IF_ACTIVE, ONLY_IF_NOT_ACTIVE,
        PLAYER_ON_TILE, PLAYER_NOT_ON_TILE, PLAYER_TOUCHING_TILE, PLAYER_NOT_TOUCHING_TILE;

        public boolean condition(Tile tile) {
            switch (this) {
                case NONE: return false;
                case ALWAYS: return true;
                case ONLY_IF_ACTIVE: return tile.isActive();
                case ONLY_IF_NOT_ACTIVE: return !tile.isActive();
                case PLAYER_ON_TILE:
                    boolean isTrue = false;
                    for (Player player : tile.game.getPlayerInstances()) {
                        if (player.getOnTile() == tile) {
                            isTrue = true;
                        }
                    }
                    return isTrue;
                case PLAYER_NOT_ON_TILE:
                    boolean isTrue1 = false;
                    for (Player player : tile.game.getPlayerInstances()) {
                        if (player.getOnTile() == tile) {
                            isTrue1 = true;
                        }
                    }
                    return !isTrue1;
                case PLAYER_TOUCHING_TILE:
                    boolean isTrue2 = false;
                    for (Player player : tile.game.getPlayerInstances()) {
                        for (Tile tile1 : player.getAllSurroundingTiles()) {
                            if (tile1 == tile && checkCollision(player.getX(), player.getY(), player.getWidth(), player.getHeight(), tile.x, tile.y, tile.width, tile.height)) {
                                isTrue2 = true;
                            }
                        }
                    }
                    return isTrue2;
                case PLAYER_NOT_TOUCHING_TILE:
                    boolean isTrue3 = false;
                    for (Player player : tile.game.getPlayerInstances()) {
                        for (Tile tile1 : player.getAllSurroundingTiles()) {
                            if (tile1 == tile && checkCollision(player.getX(), player.getY(), player.getWidth(), player.getHeight(), tile.x, tile.y, tile.width, tile.height)) {
                                isTrue3 = true;
                            }
                        }
                    }
                    return !isTrue3;
            }
            return false;
        }

        public Condition inverse() {
            switch (this) {
                case ALWAYS: return NONE;
                case NONE: return ALWAYS;
                case ONLY_IF_ACTIVE: return ONLY_IF_NOT_ACTIVE;
                case ONLY_IF_NOT_ACTIVE: return ONLY_IF_ACTIVE;
            }
            return null;
        }
    }

    public enum RegularTileTextureSettings {
        UNIQUE,
        SPLIT_CENTER_HORIZONTAL,
        SPLIT_CENTER_VERTICAL;
    }

    public boolean testIfPassable() {
        return canPass && (!activeInfluencesPassable || !isActive);
    }

    public void draw(){
        setX(initialX + Editor.displacementX);
        setY(initialY + Editor.displacementY);
        if (x + Game.DIS_X > -TILE_SIZE && x + Game.DIS_X < WIDTH + TILE_SIZE && y + Game.DIS_Y > -TILE_SIZE && y + Game.DIS_Y < HEIGHT + TILE_SIZE) {
            if (textureSettings == RegularTileTextureSettings.UNIQUE) {
                defaultTexture.draw(x + Game.DIS_X, y + Game.DIS_Y);
            } else if (textureSettings == RegularTileTextureSettings.SPLIT_CENTER_VERTICAL) {
                Tex leftTex = getLeftTile().getTexture();
                Tex rightTile = getRightTile().getTexture();
                rightTile.draw(x + ((float) TILE_SIZE / 2), y, (float) TILE_SIZE / 2, TILE_SIZE, 0.5f, 1.0f, 0.0f, 1.0f);
                leftTex.draw(x, y, (float) TILE_SIZE / 2, TILE_SIZE, 0.0f, 0.5f, 0.0f, 1.0f);
            } else if (textureSettings == RegularTileTextureSettings.SPLIT_CENTER_HORIZONTAL) {
                Tex upTile = getAboveTile().getTexture();
                Tex downTile = getBelowTile().getTexture();
                downTile.draw(x, y + ((float) TILE_SIZE / 2), TILE_SIZE, (float) TILE_SIZE / 2, 0.0f, 1.0f, 0.5f, 1.0f);
                upTile.draw(x, y, TILE_SIZE, (float) TILE_SIZE / 2, 0.0f, 1.0f, 0.0f, 0.5f);
            }
            canBeSeen = true;
            if (hasMultipleTexs && !isActive) {
                for (int i = 0; i < activeTexture.length; i++) {
                    activeTexture[i].draw(x + Game.DIS_X, y + Game.DIS_Y, activeTextureRots[i]);
                }
            } else {
                if (defaultOverlapTexture != null) {
                    for (int i = 0; i < defaultOverlapTexture.length; i++) {
                        defaultOverlapTexture[i].draw(x + Game.DIS_X, y + Game.DIS_Y, defaultOverlapTexRots[i]);
                    }
                }
            }
        } else {
            canBeSeen = false;
        }
    }

    public void drawTileDecorations() {
        if (tileDecorations != null) {
            tileDecorations.draw(x + Game.DIS_X, y + Game.DIS_Y);
        }
    }

    /**
     * Overridden in subclasses. Used to constantly update this tile.
     */
    public void update() {
        hitBox.update();
    }

    /**
     * Runs when something hits the player
     * @param entity
     */
    @Override
    public void onCollisionWith(Entity entity) {
        if (entity instanceof Player && !this.testIfPassable()) {
            ((Player) entity).onWallCollision();
        }
    }

    /**
     * Overridden in subclasses. Used to carry out a function when a player presses the USE button when NEAR the tile.
     */
    public void useTilePlayerNear() {}
    /**
     * Overridden in subclasses. Used to determine if a player is NEAR (Not on) this tile.
     */
    public void playerNearTile(Direction direction) {}
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

    /**
     * Overridden in subclasses. Used to carry out a function when the whole map is loaded.
     */
    public void onMapCreation() {
        for (Player player : game.getPlayerInstances()) {
            hitBox.addCollidingEntityDetector(player);
        }
    }

    public Tile getAboveTile() {
        return game.getMap().getTile(this.getXPlace(), this.getYPlace() - 1);
    }
    public Tile getLeftTile() {
        return game.getMap().getTile(this.getXPlace() - 1, this.getYPlace());
    }
    public Tile getBelowTile() {
        return game.getMap().getTile(this.getXPlace(), this.getYPlace() + 1);
    }
    public Tile getRightTile() {
        return game.getMap().getTile(this.getXPlace() + 1, this.getYPlace());
    }

    /**
     * Overridden in subclasses
     */
    public void onPlayerPickupItemFromTile() {
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
    public Tex getTexture() {
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
    public boolean rayTraceSeeable() {
        return rayTraceSeeable.condition(this);
    }
}
