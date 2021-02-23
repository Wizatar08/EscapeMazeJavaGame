package com.wizatar08.escapemaze.game.game_entities;

import com.wizatar08.escapemaze.enumerators.TileType;
import com.wizatar08.escapemaze.game.TileMap;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.objects.Tile;
import org.lwjgl.input.Keyboard;

import static com.wizatar08.escapemaze.render.Renderer.*;
import static com.wizatar08.escapemaze.helpers.Drawer.*;

public class Player implements Entity {
    private float x, y;
    private float width, height;
    private TileMap map;

    public Player(float startXTile, float startYTile, TileMap map) {
        setX(startXTile * TILE_SIZE + (TILE_SIZE / 4) - TILE_SIZE);
        setY(startYTile * TILE_SIZE + (TILE_SIZE / 4) - TILE_SIZE);
        this.width = 32;
        this.height = 32;
        this.map = map;
    }

    public void update() {
        detectKeyAndMove();
    }

    private void detectKeyAndMove() {
        Keyboard.next();
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

    private boolean keyDown(int key) {
        return (Keyboard.getEventKey() == key) && (Keyboard.getEventKeyState());
    }

    private void moveCharacter(int xDir, int yDir) {
        Tile leftTile = map.getTile((int) Math.floor(x / TILE_SIZE) - 1, (int) Math.floor(y / TILE_SIZE));
        Tile rightTile = map.getTile((int) Math.floor(x / TILE_SIZE) + 1, (int) Math.floor(y / TILE_SIZE));
        Tile upTile = map.getTile((int) Math.floor(x / TILE_SIZE), (int) Math.floor(y / TILE_SIZE) - 1);
        Tile downTile = map.getTile((int) Math.floor(x / TILE_SIZE), (int) Math.floor(y / TILE_SIZE) + 1);

        Tile currTile = map.getTile((int) Math.floor((x + 32) / TILE_SIZE), (int) Math.round(y / TILE_SIZE));
        Tile currTile2 = map.getTile((int) Math.ceil(x / TILE_SIZE), (int) Math.floor(y / TILE_SIZE) - 1);
        Tile currTile3 = map.getTile((int) Math.floor(x / TILE_SIZE) - 1, (int) Math.ceil(y / TILE_SIZE));

        //System.out.println(leftTile.getType().isPassable() + ", " + rightTile.getType().isPassable() + ", " + upTile.getType().isPassable() + ", " + downTile.getType().isPassable());
        //drawQuadTex(LoadPNG("tiles/null"), currTile.getX(), currTile.getY(), currTile.getWidth(), currTile.getHeight());
        //drawQuadTex(LoadPNG("tiles/null"), currTile2.getX(), currTile2.getY(), currTile2.getWidth(), currTile2.getHeight());
        //drawQuadTex(LoadPNG("tiles/null"), currTile3.getX(), currTile3.getY(), currTile3.getWidth(), currTile3.getHeight());
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

    public void draw() {
        drawQuadTex(LoadPNG("entities/player"), x, y, width, height);
    }


    // Getters and setters

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
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
}
