package com.wizatar08.escapemaze.game.game_entities;

import com.wizatar08.escapemaze.map.SafeSpot;
import com.wizatar08.escapemaze.map.TileMap;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.map.Tile;
import org.lwjgl.input.Keyboard;

import static com.wizatar08.escapemaze.render.Renderer.*;
import static com.wizatar08.escapemaze.helpers.Drawer.*;

public class Player implements Entity {
    // Initialize variables
    private float x, y;
    private float width, height;
    private TileMap map;
    private boolean isSafe;

    // Constructor for Player object
    public Player(float startXTile, float startYTile, TileMap map) {
        setX(startXTile * TILE_SIZE + (TILE_SIZE / 4) - TILE_SIZE);
        setY(startYTile * TILE_SIZE + (TILE_SIZE / 4) - TILE_SIZE);
        this.width = 32;
        this.height = 32;
        this.map = map;
        this.isSafe = false;
    }

    // Update (loop) method
    public void update() {
        detectKeyAndMove();
        draw();
    }

    // Detects if Player is near a safe spot, return true if so and false if not
    private boolean isNearSafeSpot() {
        if (map.getSafeSpots() != null) {
            int i = 0;
            for (SafeSpot safeSpot : map.getSafeSpots()) {
                Tile tile = safeSpot.getDetectTile();
                float distX = safeSpot.getDetectTile().getX() - safeSpot.getSafeTile().getX();
                float distY = safeSpot.getDetectTile().getY() - safeSpot.getSafeTile().getY();
                if (checkCollision( tile.getX() + ((float) TILE_SIZE / 2) - 8 - (distX / 2), tile.getY() + ((float) TILE_SIZE / 2) - 8 - (distY / 2), (float) tile.getWidth() / 4, (float) tile.getHeight() / 4, x, y, width, height)) {
                    i++;
                }
            }
            return i > 0;
        }
        return false;
    }

    // Code that detects whether a *certain* key is pressed
    private void detectKeyAndMove() {
        while(Keyboard.next()) { // Detect if a key is pressed ONCE
            if (keyDown(Keyboard.KEY_SPACE)) {
                if (isNearSafeSpot() && !isSafe) {
                    goIntoSafeSpot();
                } else if (isSafe){
                    isSafe = false;
                }
            }
        }
        // Below: Detect if a key is pressed and held
        if (!isSafe) {
            if (keyDown(Keyboard.KEY_W)) {
                moveCharacter(0, -1);
            }
            if (keyDown(Keyboard.KEY_S)) {
                moveCharacter(0, 1);
            }
            if (keyDown(Keyboard.KEY_A)) {
                moveCharacter(-1, 0);
            }
            if (keyDown(Keyboard.KEY_D)) {
                moveCharacter(1, 0);
            }
        }
    }

    // Go into a safe spot. Make yourself immune to anything and set your position to where you will appear when exiting the safe spot
    private void goIntoSafeSpot() {
        isSafe = true;
        for (SafeSpot safeSpot : map.getSafeSpots()) {
            Tile tile = safeSpot.getDetectTile();
            float distX = tile.getX() - safeSpot.getSafeTile().getX();
            float distY = tile.getY() - safeSpot.getSafeTile().getY();
            if (checkCollision(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight(), x, y, width, height)) {
                x = tile.getX() - (distX / 4) + ((float) TILE_SIZE / 2) - 16;
                y = tile.getY() - (distY / 4) + ((float) TILE_SIZE / 2) - 16;
            }
        }
    }

    // Check if a key is pressed
    private boolean keyDown(int key) {
        return (Keyboard.getEventKey() == key) && (Keyboard.getEventKeyState());
    }

    // Move the player and detect if the player hit any walls
    private void moveCharacter(int xDir, int yDir) {
        Tile leftTile = map.getTile((int) Math.floor(x / TILE_SIZE) - 1, (int) Math.floor(y / TILE_SIZE));
        Tile rightTile = map.getTile((int) Math.floor(x / TILE_SIZE) + 1, (int) Math.floor(y / TILE_SIZE));
        Tile upTile = map.getTile((int) Math.floor(x / TILE_SIZE), (int) Math.floor(y / TILE_SIZE) - 1);
        Tile downTile = map.getTile((int) Math.floor(x / TILE_SIZE), (int) Math.floor(y / TILE_SIZE) + 1);

        Tile currTile = map.getTile((int) Math.floor((x + 32) / TILE_SIZE), (int) Math.round(y / TILE_SIZE));
        Tile currTile2 = map.getTile((int) Math.ceil(x / TILE_SIZE), (int) Math.floor(y / TILE_SIZE) - 1);
        Tile currTile3 = map.getTile((int) Math.floor(x / TILE_SIZE) - 1, (int) Math.ceil(y / TILE_SIZE));

        if (xDir < 0) {
            x += xDir;
            if (!leftTile.getType().isPassable() && checkCollision(leftTile.getX(), leftTile.getY(), leftTile.getWidth(), leftTile.getHeight(), x, y, width, height) ||
                (checkCollision(currTile3.getX(), currTile3.getY(), currTile3.getWidth(), currTile3.getHeight(), x, y, width, height) && !currTile3.getType().isPassable())) {
                x -= xDir;
            }
        }
        if (xDir > 0) {
            x += xDir;
            if (!rightTile.getType().isPassable() && checkCollision(rightTile.getX(), rightTile.getY(), rightTile.getWidth(), rightTile.getHeight(), x, y, width, height ) ||
                (checkCollision(currTile.getX(), currTile.getY(), currTile.getWidth(), currTile.getHeight(), x, y, width, height) && !currTile.getType().isPassable())) {
                x -= xDir;
            }
        }
        if (yDir < 0) {
            y += yDir;
            if (!upTile.getType().isPassable() && checkCollision(upTile.getX(), upTile.getY(), upTile.getWidth(), upTile.getHeight(), x, y, width, height) ||
                    (checkCollision(currTile2.getX(), currTile2.getY(), currTile2.getWidth(), currTile2.getHeight(), x, y, width, height) && !currTile2.getType().isPassable())) {
                y -= yDir;
            }
        }
        if (yDir > 0) {
            y += yDir;
            if ((!downTile.getType().isPassable() && checkCollision(downTile.getX(), downTile.getY(), downTile.getWidth(), downTile.getHeight(), x, y, width, height)) ||
                (checkCollision(currTile.getX(), currTile.getY(), currTile.getWidth(), currTile.getHeight(), x, y, width, height) && !currTile.getType().isPassable())) {
                y -= yDir;
            }
        }
    }

    // Draw the player if not in a safe spot
    public void draw() {
        if (!isSafe) {
            drawQuadTex(LoadPNG("entities/player"), x, y, width, height);
        }
    }


    // Getters and setters
    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isSafe() {
        return isSafe;
    }
}
