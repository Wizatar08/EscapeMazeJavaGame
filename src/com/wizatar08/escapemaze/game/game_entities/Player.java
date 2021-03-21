package com.wizatar08.escapemaze.game.game_entities;

import com.wizatar08.escapemaze.game.Inventory;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.map.TileDetectionSpot;
import com.wizatar08.escapemaze.map.TileMap;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.menus.Game;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;

import static com.wizatar08.escapemaze.render.Renderer.*;
import static com.wizatar08.escapemaze.helpers.Drawer.*;

public class Player implements Entity {
    // Initialize variables
    private float x, y;
    private float width, height;
    private TileMap map;
    private boolean isSafe;
    private Texture tex, detectTex, detectLockedDoorTex;
    private Game gameController;
    private Inventory inventory;
    private float weightInfluence;

    // Constructor for Player object
    public Player(Game game, float startXTile, float startYTile, TileMap map) {
        this.gameController = game;
        setX(startXTile * TILE_SIZE + (TILE_SIZE / 4) - TILE_SIZE);
        setY(startYTile * TILE_SIZE + (TILE_SIZE / 4) - TILE_SIZE);
        this.width = 32;
        this.height = 32;
        this.map = map;
        this.isSafe = false;
        this.tex = LoadPNG("entities/player");
        this.detectTex = LoadPNG("tiles/selectors/safe_space_selector");
        this.detectLockedDoorTex = LoadPNG("tiles/selectors/safe_space_selector");
        this.inventory = new Inventory(5);
    }



    // Detects if Player is near a safe spot, return true if so and false if not
    private boolean isAtSecurityComputer() {
        return map.getTile(Math.round((x - (TILE_SIZE / 4)) / TILE_SIZE), Math.round((y - (TILE_SIZE / 4)) / TILE_SIZE)).isSecurityComputer();
    }

    // Detects if Player is near a safe spot, return true if so and false if not
    private boolean isNearSafeSpot() {
        if (map.getSafeSpots() != null) {
            int i = 0;
            for (TileDetectionSpot tileDetectionSpot : map.getSafeSpots()) {
                Tile tile = tileDetectionSpot.getDetectTile();
                float distX = tileDetectionSpot.getDetectTile().getX() - tileDetectionSpot.getSafeTile().getX();
                float distY = tileDetectionSpot.getDetectTile().getY() - tileDetectionSpot.getSafeTile().getY();
                if (checkCollision( tile.getX() + ((float) TILE_SIZE / 2) - 8 - (distX / 2), tile.getY() + ((float) TILE_SIZE / 2) - 8 - (distY / 2), (float) tile.getWidth() / 4, (float) tile.getHeight() / 4, x, y, width, height)) {
                    i++;
                }
            }
            return i > 0;
        }
        return false;
    }

    // Code that detects whether a *certain* key is pressed
    public void playerTapKeyDetection() {
        if (keyDown(Keyboard.KEY_SPACE)) {
            if (isNearSafeSpot() && !isSafe) {
                goIntoSafeSpot();
            } else if (isSafe){
                isSafe = false;
            }
            if (isAtSecurityComputer() && gameController.currentState() == Game.GameStates.ALARM) {
                gameController.setState(Game.GameStates.NORMAL);
            }
        }
    }

    public void playerPressAndHoldKeyDetection() {
        if (!isSafe) {
            if (keyDown(Keyboard.KEY_W)) {
                moveCharacter(0, -1 / weightInfluence);
            }
            if (keyDown(Keyboard.KEY_S)) {
                moveCharacter(0, 1 / weightInfluence);
            }
            if (keyDown(Keyboard.KEY_A)) {
                moveCharacter(-1 / weightInfluence, 0);
            }
            if (keyDown(Keyboard.KEY_D)) {
                moveCharacter(1 / weightInfluence, 0);
            }
        }
    }

    // Go into a safe spot. Make yourself immune to anything and set your position to where you will appear when exiting the safe spot
    private void goIntoSafeSpot() {
        isSafe = true;
        for (TileDetectionSpot tileDetectionSpot : map.getSafeSpots()) {
            Tile tile = tileDetectionSpot.getDetectTile();
            float distX = tile.getX() - tileDetectionSpot.getSafeTile().getX();
            float distY = tile.getY() - tileDetectionSpot.getSafeTile().getY();
            if (checkCollision(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight(), x, y, width, height)) {
                x = tile.getX() - (distX / 4) + ((float) TILE_SIZE / 2) - 16;
                y = tile.getY() - (distY / 4) + ((float) TILE_SIZE / 2) - 16;
                System.out.println(tileDetectionSpot.getSafeTile().isEscapeDoor());
                if (tileDetectionSpot.getSafeTile().isEscapeDoor()) {
                    escapeDoor();
                }
            }
        }
    }

    private void escapeDoor() {
        // Update escape door stuffs
    }

    // Check if a key is pressed
    private boolean keyDown(int key) {
        return (Keyboard.getEventKey() == key) && (Keyboard.getEventKeyState());
    }

    // Move the player and detect if the player hit any walls
    private void moveCharacter(float xDir, float yDir) {
        Tile leftTile = map.getTile((int) Math.floor(x / TILE_SIZE) - 1, (int) Math.floor(y / TILE_SIZE));
        Tile rightTile = map.getTile((int) Math.floor(x / TILE_SIZE) + 1, (int) Math.floor(y / TILE_SIZE));
        Tile upTile = map.getTile((int) Math.floor(x / TILE_SIZE), (int) Math.floor(y / TILE_SIZE) - 1);
        Tile downTile = map.getTile((int) Math.floor(x / TILE_SIZE), (int) Math.floor(y / TILE_SIZE) + 1);

        Tile currTile = map.getTile((int) Math.floor((x + 32) / TILE_SIZE), (int) Math.round(y / TILE_SIZE));
        Tile currTile2 = map.getTile((int) Math.ceil(x / TILE_SIZE), (int) Math.floor(y / TILE_SIZE) - 1);
        Tile currTile3 = map.getTile((int) Math.floor(x / TILE_SIZE) - 1, (int) Math.ceil(y / TILE_SIZE));

        if (xDir < 0) {
            x += xDir * Clock.Delta() * Clock.FPS;
            if (!leftTile.canPass() && checkCollision(leftTile.getX(), leftTile.getY(), leftTile.getWidth(), leftTile.getHeight(), x, y, width, height) ||
                (checkCollision(currTile3.getX(), currTile3.getY(), currTile3.getWidth(), currTile3.getHeight(), x, y, width, height) && !currTile3.canPass())) {
                x -= xDir * Clock.Delta() * Clock.FPS;
            }
        }
        if (xDir > 0) {
            x += xDir * Clock.Delta() * Clock.FPS;
            if (!rightTile.canPass() && checkCollision(rightTile.getX(), rightTile.getY(), rightTile.getWidth(), rightTile.getHeight(), x, y, width, height ) ||
                (checkCollision(currTile.getX(), currTile.getY(), currTile.getWidth(), currTile.getHeight(), x, y, width, height) && !currTile.canPass())) {
                x -= xDir * Clock.Delta() * Clock.FPS;
            }
        }
        if (yDir < 0) {
            y += yDir * Clock.Delta() * Clock.FPS;
            if (!upTile.canPass() && checkCollision(upTile.getX(), upTile.getY(), upTile.getWidth(), upTile.getHeight(), x, y, width, height) ||
                    (checkCollision(currTile2.getX(), currTile2.getY(), currTile2.getWidth(), currTile2.getHeight(), x, y, width, height) && !currTile2.canPass())) {
                y -= yDir * Clock.Delta() * Clock.FPS;
            }
        }
        if (yDir > 0) {
            y += yDir * Clock.Delta() * Clock.FPS;
            if ((!downTile.canPass() && checkCollision(downTile.getX(), downTile.getY(), downTile.getWidth(), downTile.getHeight(), x, y, width, height)) ||
                (checkCollision(currTile.getX(), currTile.getY(), currTile.getWidth(), currTile.getHeight(), x, y, width, height) && !currTile.canPass())) {
                y -= yDir * Clock.Delta() * Clock.FPS;
            }
        }
    }

    private void useItem(int slot) {
        try {
            Item item = inventory.getItems().get(slot);
            if (!item.doUse(gameController, item)) {
                item.setX(x - (item.getWidth() / 2));
                item.setY(y - (item.getHeight() / 2));
                gameController.addItemToGame(item);
                item.setIsInInventory(false);
                inventory.remove(slot);
            }
        } catch (IndexOutOfBoundsException | NullPointerException ignored) {}
    }

    private void detectKey() {
        if (keyDown(Keyboard.KEY_1)) {
            useItem(0);
        }
        if (keyDown(Keyboard.KEY_2)) {
            useItem(1);
        }
        if (keyDown(Keyboard.KEY_3)) {
            useItem(2);
        }
        if (keyDown(Keyboard.KEY_4)) {
            useItem(3);
        }
        if (keyDown(Keyboard.KEY_5)) {
            useItem(4);
        }
    }

    // Draw the player if not in a safe spot
    public void draw() {
        if (!isSafe) {
            drawQuadTex(tex, x + Game.DIS_X, y + Game.DIS_Y);
        }
        if (isNearSafeSpot()) {
            for (TileDetectionSpot tileDetectionSpot : map.getSafeSpots()) {
                Tile tile = tileDetectionSpot.getDetectTile();
                Tile safeTile = tileDetectionSpot.getSafeTile();
                float distX = tileDetectionSpot.getDetectTile().getX() - tileDetectionSpot.getSafeTile().getX();
                float distY = tileDetectionSpot.getDetectTile().getY() - tileDetectionSpot.getSafeTile().getY();
                if (checkCollision( tile.getX() + ((float) TILE_SIZE / 2) - 8 - (distX / 2), tile.getY() + ((float) TILE_SIZE / 2) - 8 - (distY / 2), (float) tile.getWidth() / 4, (float) tile.getHeight() / 4, x, y, width, height)) {
                    drawQuadTex(detectTex, safeTile.getX() + Game.DIS_X, safeTile.getY() + Game.DIS_Y, safeTile.getWidth(), safeTile.getHeight());
                }
            }
        }
        inventory.draw();
    }

    private void addItemToInventory(Item item) {
        gameController.removeItemFromGame(item);
        item.setIsInInventory(true);
        inventory.add(item);
    }

    private void detectIfHitItem() {
        Item hitItem = null;
        for (Item item : gameController.getItems()) {
            if (checkCollision(x, y, width, height, item.getTexX(), item.getTexY(), item.getWidth(), item.getHeight()) && !item.isInInventory() && item.canPickUp()) {
                hitItem = item;
            }
        }
        if (hitItem != null) {
            addItemToInventory(hitItem);
        }
    }

    private void calculateWeight() {
        float weightSum = 1;
        for (Item item : inventory.getItems()) {
            if (item != null) {
                weightSum += item.getWeight();
            }
        }
        weightInfluence = weightSum;
    }

    private Tile getUpTile() {
        return gameController.getMap().getTile((int) Math.floor((x + (TILE_SIZE / 4)) / TILE_SIZE), (int) Math.floor((y + (TILE_SIZE / 2) - 1) / TILE_SIZE) - 1);
    }
    private Tile getLeftTile() {
        return gameController.getMap().getTile((int) Math.floor((x + (TILE_SIZE / 2) - 1) / TILE_SIZE) - 1, (int) Math.floor((y + (TILE_SIZE / 4)) / TILE_SIZE));
    }
    private Tile getRightTile() {
        return gameController.getMap().getTile((int) Math.floor((x + 1) / TILE_SIZE) + 1, (int) Math.floor((y + (TILE_SIZE / 4)) / TILE_SIZE));
    }
    private Tile getDownTile() {
        return gameController.getMap().getTile((int) Math.floor((x + (TILE_SIZE / 4)) / TILE_SIZE), (int) Math.floor((y + 1) / TILE_SIZE) + 1);
    }

    private boolean nextToTile(Tile tile) {
        //drawQuadTex(LoadPNG("game/items/null"), tileDown.getX() + Game.DIS_X, tileDown.getY() + Game.DIS_Y);
        return getUpTile() == tile || getLeftTile() == tile || getRightTile() == tile || getDownTile() == tile;
    }
    public ArrayList<Tile> getAllSurroundingTiles() {
        ArrayList<Tile> list = new ArrayList<>();
        list.add(getUpTile());
        list.add(getRightTile());
        list.add(getDownTile());
        list.add(getLeftTile());
        return list;
    }

    private void detectIfAtLockedDoor() {
        ArrayList<Tile> list = getAllSurroundingTiles();
        for (Tile tile : list) {
            if (tile.isLockedDoor() && !tile.canPass()) {
                for (Item item : inventory.getItems()) {
                    if (item != null) {
                        if (ItemType.getType(item.getId()) == tile.unlockableBy()) {
                            drawQuadTex(detectLockedDoorTex, tile.getX() + Game.DIS_X, tile.getY() + Game.DIS_Y);
                        }
                    }
                }
            }
        }
    }

    public void update() {
        detectIfAtLockedDoor();
        detectKey();
        detectIfHitItem();
        calculateWeight();
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
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isSafe() {
        return isSafe;
    }
}
