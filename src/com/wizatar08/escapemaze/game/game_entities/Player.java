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
    private Texture tex, detectTex;
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
        this.inventory = new Inventory(5);
    }



    // Detects if Player is near a safe spot, return true if so and false if not
    public boolean isAtSecurityComputer() {
        return map.getTile(Math.round((x - (TILE_SIZE / 4)) / TILE_SIZE), Math.round((y - (TILE_SIZE / 4)) / TILE_SIZE)).isSecurityComputer();
    }

    // Detects if Player is near a safe spot, return true if so and false if not
    public boolean isNearSafeSpot() {
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

    public void detectKey() {
        while (Keyboard.next()) {
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

            if (gameController.currentState() == Game.GameStates.NORMAL || gameController.currentState() == Game.GameStates.ALARM) {
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
            if (keyDown(Keyboard.KEY_ESCAPE)) {
                gameController.switchPauseState();
            }
        }
        if (!isSafe) {
            if (keyDown(Keyboard.KEY_W)) {
                moveCharacter(0, -1.5f / weightInfluence);
            }
            if (keyDown(Keyboard.KEY_S)) {
                moveCharacter(0, 1.5f / weightInfluence);
            }
            if (keyDown(Keyboard.KEY_A)) {
                moveCharacter(-1.5f / weightInfluence, 0);
            }
            if (keyDown(Keyboard.KEY_D)) {
                moveCharacter(1.5f / weightInfluence, 0);
            }
        }

        while (Keyboard.next()) {

        }
    }

    // Go into a safe spot. Make yourself immune to anything and set your position to where you will appear when exiting the safe spot
    public void goIntoSafeSpot() {
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
        for (int i = 0; i < inventory.getItems().size(); i++) {
            if (inventory.getItems().get(i) != null) {
                if (inventory.getItems().get(i).isRequired()) {
                    inventory.remove(i);
                    gameController.stealItem();
                }
            }
        }
        if (gameController.getStolenItems() >= gameController.getRequiredItems()) {
            gameController.setState(Game.GameStates.GAME_END);
            gameController.endGame(true);
        }
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
        if (inventory.canAdd()) {
            gameController.removeItemFromGame(item);
            item.setIsInInventory(true);
            inventory.add(item);
        }
    }

    private void detectIfHitItem() {
        Item hitItem = null;
        for (Item item : gameController.getItems()) {
            if (checkCollision(x, y, width, height, item.getTexX(), item.getTexY(), item.getWidth(), item.getHeight()) && !item.isInInventory() && item.canPickUp()) {
                hitItem = item;
            }
        }
        if (hitItem != null) {
            if (gameController.getMap().getTile((int) hitItem.getX() / TILE_SIZE, (int) hitItem.getY() / TILE_SIZE).getX() == hitItem.getX() && gameController.getMap().getTile((int) hitItem.getX() / TILE_SIZE, (int) hitItem.getY() / TILE_SIZE).getY() == hitItem.getY() && gameController.getMap().getTile((int) hitItem.getX() / TILE_SIZE, (int) hitItem.getY() / TILE_SIZE).isSecure()) {
                gameController.setState(Game.GameStates.ALARM);
            }
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

    private void detectIfAtSpecificTile() {
        ArrayList<Tile> list = getAllSurroundingTiles();
        for (Tile tile : list) {
            for (Item item : inventory.getItems()) {
                if (item != null) {
                    if (tile.isLockedDoor() && !tile.canPass()) {
                        if (ItemType.getType(item.getId()) == tile.unlockableBy()) {
                            drawQuadTex(detectTex, tile.getX() + Game.DIS_X, tile.getY() + Game.DIS_Y);
                        }
                    }
                    if (tile.isSecure() && ItemType.getType(item.getId()) == ItemType.LASER_DEACTIVATOR) {
                        drawQuadTex(detectTex, tile.getX() + Game.DIS_X, tile.getY() + Game.DIS_Y);
                    }
                }
            }
        }
        //drawQuadTex(LoadPNG("tiles/null"), (int) Math.floor((x + 16) / 64) * 64 + Game.DIS_X, (int) Math.floor((y + 16) / 64) * 64 + Game.DIS_Y);
    }

    public void update() {
        if (gameController.currentState() == Game.GameStates.NORMAL || gameController.currentState() == Game.GameStates.ALARM) {
            detectKey();
        }
        detectIfAtSpecificTile();
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
    
    public void setIfSafe(boolean isSafe) {
        this.isSafe = isSafe;
    }
}
