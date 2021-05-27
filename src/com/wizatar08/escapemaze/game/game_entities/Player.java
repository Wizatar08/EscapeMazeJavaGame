package com.wizatar08.escapemaze.game.game_entities;

import com.wizatar08.escapemaze.game.Inventory;
import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.map.Direction;
import com.wizatar08.escapemaze.map.TileDetectionSpot;
import com.wizatar08.escapemaze.map.TileMap;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.tile_types.ExitSpot;
import com.wizatar08.escapemaze.map.tile_types.PressurePlate;
import com.wizatar08.escapemaze.menus.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.wizatar08.escapemaze.render.Renderer.*;
import static com.wizatar08.escapemaze.visuals.Drawer.*;

/*
PLAYER CONTROLS:
- WASD: Movement
- E: Change player instance
- Space: Interact with tile
- Number 1-9: Change inventory slot
- Left click: Use item
- Right click: Drop item
- Escape: Pause/unpause game
 */

public class Player implements Entity {
    // Initialize variables
    private float x, y;
    private float width, height;
    private TileMap map;
    private boolean isSafe;
    private Tex tex, detectTex;
    private final Game gameController;
    private Inventory inventory;
    private float weightInfluence, speedInfluence;
    private boolean selected;
    private org.newdawn.slick.Color texColor;

    // Constructor for Player object
    public Player(Game game, int texColor, float startXTile, float startYTile, TileMap map) {
        this.gameController = game;
        setX(startXTile * TILE_SIZE + (TILE_SIZE / 4) - TILE_SIZE);
        setY(startYTile * TILE_SIZE + (TILE_SIZE / 4) - TILE_SIZE);
        this.texColor = new Color(texColor);
        this.width = 32;
        this.height = 32;
        this.map = map;
        this.isSafe = false;
        this.tex = new Tex("players/player_base");
        this.detectTex = new Tex("tiles/selectors/tile_selector");
        this.inventory = new Inventory(gameController.getMaxInventorySlots());
        this.speedInfluence = 0.0f;
    }

    public Tile getOnTile() {
        return map.getTile(Math.round((x - (TILE_SIZE / 4)) / TILE_SIZE), Math.round((y - (TILE_SIZE / 4)) / TILE_SIZE));
    }

    // Detects if Player is near a safe spot, return true if so and false if not. This is NOT in
    // a Tile subclass (at the moment) because safe spots are directional.
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
        if (selected) {
            while (Keyboard.next()) {
                if (keyDown(Keyboard.KEY_1)) {
                    inventory.setSelected(0);
                }
                if (keyDown(Keyboard.KEY_2)) {
                    inventory.setSelected(1);
                }
                if (keyDown(Keyboard.KEY_3)) {
                    inventory.setSelected(2);
                }
                if (keyDown(Keyboard.KEY_4)) {
                    inventory.setSelected(3);
                }
                if (keyDown(Keyboard.KEY_5)) {
                    inventory.setSelected(4);
                }
                if (keyDown(Keyboard.KEY_6)) {
                    inventory.setSelected(5);
                }
                if (keyDown(Keyboard.KEY_7)) {
                    inventory.setSelected(6);
                }
                if (keyDown(Keyboard.KEY_8)) {
                    inventory.setSelected(7);
                }
                if (keyDown(Keyboard.KEY_9)) {
                    inventory.setSelected(8);
                }

                if (keyDown(Keyboard.KEY_E)) {
                    gameController.changePlayerInstance();
                }
                if (gameController.currentState() == Game.GameStates.NORMAL || gameController.currentState() == Game.GameStates.ALARM) {
                    if (keyDown(Keyboard.KEY_SPACE)) {
                        if (isNearSafeSpot() && !isSafe) {
                            goIntoSafeSpot();
                        } else if (isSafe) {
                            isSafe = false;
                        }
                        getOnTile().useTile();
                        getAllSurroundingTiles().forEach(Tile::useTilePlayerNear);
                    }
                }
                if (keyDown(Keyboard.KEY_ESCAPE)) {
                    gameController.switchPauseState();
                }
            }
            while (Mouse.next()) {
                if (mouseDown(0)) {
                    useItem(inventory.getCurrentSelected());
                }
                if (mouseDown(1)) {
                    if (inventory.getItems().get(inventory.getCurrentSelected()) != null) {
                        dropItem(inventory.getItems().get(inventory.getCurrentSelected()), inventory.getCurrentSelected());
                    }
                }
            }
            if (!isSafe) {
                if (keyDown(Keyboard.KEY_W)) {
                    moveCharacter(0, -1.5f / weightInfluence - speedInfluence);
                }
                if (keyDown(Keyboard.KEY_S)) {
                    moveCharacter(0, 1.5f / weightInfluence + speedInfluence);
                }
                if (keyDown(Keyboard.KEY_A)) {
                    moveCharacter(-1.5f / weightInfluence - speedInfluence, 0);
                }
                if (keyDown(Keyboard.KEY_D)) {
                    moveCharacter(1.5f / weightInfluence + speedInfluence, 0);
                }
            }
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
                if (tileDetectionSpot.getSafeTile().getSubClass() == ExitSpot.class) {
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

    // Check if a key is pressed
    private boolean mouseDown(int key) {
        return (Mouse.getEventButton() == key) && (Mouse.getEventButtonState());
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
            if (!leftTile.testIfPassable()&& checkCollision(leftTile.getX(), leftTile.getY(), leftTile.getWidth(), leftTile.getHeight(), x, y, width, height) ||
                (checkCollision(currTile3.getX(), currTile3.getY(), currTile3.getWidth(), currTile3.getHeight(), x, y, width, height) && !currTile3.testIfPassable())) {
                x -= xDir * Clock.Delta() * Clock.FPS;
            }
        }
        if (xDir > 0) {
            x += xDir * Clock.Delta() * Clock.FPS;
            if (!rightTile.testIfPassable() && checkCollision(rightTile.getX(), rightTile.getY(), rightTile.getWidth(), rightTile.getHeight(), x, y, width, height ) ||
                (checkCollision(currTile.getX(), currTile.getY(), currTile.getWidth(), currTile.getHeight(), x, y, width, height) && !currTile.testIfPassable())) {
                x -= xDir * Clock.Delta() * Clock.FPS;
            }
        }
        if (yDir < 0) {
            y += yDir * Clock.Delta() * Clock.FPS;
            if (!upTile.testIfPassable() && checkCollision(upTile.getX(), upTile.getY(), upTile.getWidth(), upTile.getHeight(), x, y, width, height) ||
                    (checkCollision(currTile2.getX(), currTile2.getY(), currTile2.getWidth(), currTile2.getHeight(), x, y, width, height) && !currTile2.testIfPassable())) {
                y -= yDir * Clock.Delta() * Clock.FPS;
            }
        }
        if (yDir > 0) {
            y += yDir * Clock.Delta() * Clock.FPS;
            if ((!downTile.testIfPassable() && checkCollision(downTile.getX(), downTile.getY(), downTile.getWidth(), downTile.getHeight(), x, y, width, height)) ||
                (checkCollision(currTile.getX(), currTile.getY(), currTile.getWidth(), currTile.getHeight(), x, y, width, height) && !currTile.testIfPassable())) {
                y -= yDir * Clock.Delta() * Clock.FPS;
            }
        }
    }

    private void useItem(int slot) {
        try {
            Item item = inventory.getItems().get(slot);
            if (item != null && item.canUse()) {
                item.use();
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void dropItem(Item item, int fromSlot) {
        item.setX(x - (item.getWidth() / 2));
        item.setY(y - (item.getHeight() / 2));
        gameController.addItemToGame(item);
        item.setIsInInventory(false);
        item.drop();
        inventory.remove(fromSlot);
    }

    // Draw the player if not in a safe spot
    public void draw() {
        if (!isSafe) {
            tex.draw(x + Game.DIS_X, y + Game.DIS_Y, texColor);
        }
        if (isNearSafeSpot()) {
            for (TileDetectionSpot tileDetectionSpot : map.getSafeSpots()) {
                Tile tile = tileDetectionSpot.getDetectTile();
                Tile safeTile = tileDetectionSpot.getSafeTile();
                float distX = tileDetectionSpot.getDetectTile().getX() - tileDetectionSpot.getSafeTile().getX();
                float distY = tileDetectionSpot.getDetectTile().getY() - tileDetectionSpot.getSafeTile().getY();
                if (checkCollision( tile.getX() + ((float) TILE_SIZE / 2) - 8 - (distX / 2), tile.getY() + ((float) TILE_SIZE / 2) - 8 - (distY / 2), (float) tile.getWidth() / 4, (float) tile.getHeight() / 4, x, y, width, height)) {
                    detectTex.draw(safeTile.getX() + Game.DIS_X, safeTile.getY() + Game.DIS_Y);
                }
            }
        }
        inventory.update();
        if (selected) {
            inventory.draw();
        }
    }

    private void addItemToInventory(Item item) {
        if (inventory.canAdd()) {
            gameController.removeItemFromGame(item);
            item.setIsInInventory(true);
            item.onPickup();
            inventory.add(item);
        }
    }

    private void detectIfHitItem() {
        Item item = null;
        for (Item item1 : gameController.getItems()) {
            if (checkCollision(x, y, width, height, item1.getTexX(), item1.getTexY(), item1.getWidth(), item1.getHeight()) && !item1.isInInventory() && item1.canPickUp() && inventory.canAdd()) {
                item = item1;
            }
        }
        if (item != null) {
            Tile tile = gameController.getMap().getTile((int) item.getX() / TILE_SIZE, (int) item.getY() / TILE_SIZE);
            if (tile.getX() == item.getX() && tile.getY() == item.getY() && tile.getSubClass() == PressurePlate.class && tile.isActive()) {
                gameController.setState(Game.GameStates.ALARM);
            }
            addItemToInventory(item);
            item.hitItem(gameController);
        }
    }

    private void calculateSpeed() {
        float weightSum = 1;
        float speedSum = 0;
        for (Item item : inventory.getItems()) {
            if (item != null) {
                weightSum += item.getWeight();
                speedSum += item.getSpeedBoost();
            }
        }
        weightInfluence = weightSum;
        speedInfluence = speedSum;
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

    public ArrayList<Tile> getAllSurroundingTiles() {
        ArrayList<Tile> list = new ArrayList<>();
        list.add(getUpTile());
        list.add(getRightTile());
        list.add(getDownTile());
        list.add(getLeftTile());
        return list;
    }

    public Map<Tile, Direction> getAllSurroundingTilesWithDirections() {
        Map<Tile, Direction> list = new HashMap<>();
        list.put(getUpTile(), Direction.UP);
        list.put(getRightTile(), Direction.RIGHT);
        list.put(getDownTile(), Direction.DOWN);
        list.put(getLeftTile(), Direction.LEFT);
        return list;
    }

    private void detectIfAtSpecificTile() {
        Map<Tile, Direction> list = getAllSurroundingTilesWithDirections();
        for (int i = 0; i < list.size(); i++) {
            ((Tile) list.keySet().toArray()[i]).playerNearTile(list.get(list.keySet().toArray()[i]));
        }
        if (getOnTile().isOnTile()) {
            getOnTile().onTile();
        }
    }

    public Enemy getClosestEnemy() {
        ArrayList<Integer> distances = new ArrayList<>();
        for (Enemy enemy : gameController.getEnemies()) {
            distances.add((int) Math.round(Math.sqrt((enemy.getX() * enemy.getX()) + (enemy.getY() * enemy.getY()))));
        }
        int smallestDist = Integer.MAX_VALUE;
        int smallestDistEnemyIndex = 0;
        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) < smallestDist) {
                smallestDist = distances.get(i);
                smallestDistEnemyIndex = i;
            }
        }
        if (gameController.getEnemies().size() != 0) {
            return gameController.getEnemies().get(smallestDistEnemyIndex);
        }
        return null;
    }

    public void update() {
        if (gameController.currentState() == Game.GameStates.NORMAL || gameController.currentState() == Game.GameStates.ALARM) {
            detectKey();
        }
        detectIfAtSpecificTile();
        detectIfHitItem();
        calculateSpeed();
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
    public Inventory getInventory() {
        return inventory;
    }
    public void setSelected(boolean select) {
        this.selected = select;
    }
}
