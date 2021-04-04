package com.wizatar08.escapemaze.menus;

import com.google.gson.Gson;
import com.wizatar08.escapemaze.game.JSONLevel;
import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;
import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.*;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.helpers.ui.UI;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileMap;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.render.Renderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.Project;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import javax.swing.*;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class Game {
    private int levelNumber, requiredItems, stolenItems, inventorySlots;
    private TileMap map;
    private String levelName;
    private ArrayList<Player> playerInstances;
    private ArrayList<Enemy> enemies;
    private ArrayList<Item> items;
    private GameStates currentGameState, prevGameState;
    private UI ui, gameEndUI;
    private TextBlock fpsDisplay, timeDisplay, blank, alarmTimer, requiredItemsStolen;
    private Timer timeOnLevel, internalAlarmTimer;
    private MapScroll scrollType;
    public static float DIS_X, DIS_Y;
    private Texture redScreen, alertBox, gameEndTex;
    private int currentPlayer;
    private boolean pressurePlatesActive;

    public Game() {
        ItemType nullItem = ItemType.NULL; // This is so all items in ItemType can be created before all tiles in TileType are (because weird glitches happen when you create all items while creating all tiles)
        // Initialize variables
        Gson gson = new Gson();
        levelNumber = MenuRun.getLevel();
        InputStreamReader reader = new InputStreamReader(Project.class.getClassLoader().getResourceAsStream("resources/level_data/lvl" + levelNumber + ".json"));
        JSONLevel level = gson.fromJson(reader, JSONLevel.class);
        map = ExternalMapHandler.LoadMap(this, level.getMap());
        inventorySlots = level.getInventorySlots();
        playerInstances = new ArrayList<>();
        for (int i = 0; i < level.getPlayerStartPos().length; i++) {
            playerInstances.add(new Player(this, level.getPlayerTexNames()[i], level.getPlayerStartPos()[i][0] + 1, level.getPlayerStartPos()[i][1] + 1, map));
        }
        enemies = level.getEnemies(this);
        items = level.getItems(this);
        currentGameState = GameStates.NORMAL;
        ui = new UI();
        gameEndUI = new UI();
        blank = new TextBlock(ui, "blank", "", 0, 0, 24f, Color.white);
        fpsDisplay = new TextBlock(ui, "fps", "", 924, 4, 24f, Color.gray);
        timeDisplay = new TextBlock(ui, "time", "", 4, 4, 24f, Color.white);
        alarmTimer = new TextBlock(ui, "alarm", "", 0, 38, 40f, Color.red);
        requiredItemsStolen = new TextBlock(ui, "itemsStolen", "", 0, 0, 32f, Color.red);
        ui.drawString(fpsDisplay);
        ui.drawString(timeDisplay);
        ui.drawString(alarmTimer);
        ui.drawString(requiredItemsStolen);
        ui.drawString(blank);
        ui.showString("alarm", false);
        redScreen = Drawer.LoadPNG("backgrounds/red_screen");
        alertBox = Drawer.LoadPNG("game/alert");
        gameEndTex = Drawer.LoadPNG("backgrounds/game_end");
        timeOnLevel = new Timer();
        internalAlarmTimer = new Timer(level.getAlarmSeconds());
        timeOnLevel.unpause();
        redScreen = Drawer.LoadPNG("backgrounds/red_screen");
        requiredItems = 0;
        for (Item item : items) {
            if (item.isRequired()) {
                requiredItems++;
            }
        }
        stolenItems = 0;
        currentPlayer = 0;
        pressurePlatesActive = true;

        switch (level.getScroll()) {
            case "none":
                scrollType = MapScroll.NONE;
                break;
            case "horizontal_scroll":
                scrollType = MapScroll.HORIZONTAL_SCROLL;
                break;
            case "vertical_scroll":
                scrollType = MapScroll.VERTICAL_SCROLL;
                break;
            case "move_with_player":
                scrollType = MapScroll.MOVE;
                break;
            default:
                System.err.println("Invalid scroll type: " + level.getScroll() + " - Using default scroll type: none");
                scrollType = MapScroll.NONE;
                break;
        }
    }

    public enum GameStates {
        NORMAL,
        ALARM,
        GAME_END,
        PAUSED
    }

    private enum MapScroll {
        NONE,
        HORIZONTAL_SCROLL,
        VERTICAL_SCROLL,
        MOVE
    }

    public void switchPauseState() {
        if (currentGameState == Game.GameStates.NORMAL || currentGameState == Game.GameStates.ALARM) {
            prevGameState = currentGameState;
            currentGameState = Game.GameStates.PAUSED;
        } else if (currentGameState == Game.GameStates.PAUSED) {
            currentGameState = prevGameState;
        }
    }

    public void changePlayerInstance() {
        currentPlayer++;
        if (currentPlayer >= playerInstances.size()) {
            currentPlayer = 0;
        }
    }

    public void update() {
        if (currentGameState == GameStates.GAME_END) {
            detectIfButtonHitOnGameEnd();
        } else {
            for (int i = 0; i < playerInstances.size(); i++) {
                if (i != currentPlayer) {
                    playerInstances.get(i).setSelected(false);
                } else {
                    playerInstances.get(i).setSelected(true);
                }
            }
            updateDisplacement();
            drawAll();
            playerInstances.forEach(Player::draw);
            if (currentGameState != GameStates.PAUSED) {
                updateEnemies();
                updateTime();
                playerInstances.forEach(Player::update);
                for (Item item : items) {
                    item.update();
                }
                for (Tile[] tileRow : map.getMapAsArray()) {
                    for (Tile tile : tileRow) {
                        if (tile.isSeen()) {
                            tile.update();
                        }
                    }
                }
            }
            if (internalAlarmTimer.getTotalSeconds() <= 0) {
                currentGameState = GameStates.GAME_END;
                endGame(false);
            }
        }
    }

    private void alarm() {
        if (Math.floor(internalAlarmTimer.getTotalSeconds()) % 2 == 0) {
            Drawer.drawQuadTex(redScreen, 0, 0);
        }
        Drawer.drawQuadTex(alertBox, WIDTH / 2 - (alertBox.getImageWidth()) / 2, 24);
        ui.showString("alarm", true);
    }

    private void updateDisplacement() {
        switch (scrollType) {
            case NONE:
                DIS_X = 0;
                DIS_Y = 0;
                break;
            case MOVE:
                if (playerInstances.get(currentPlayer).getX() > (map.getTilesWide() * TILE_SIZE - (WIDTH / 2))) {
                    DIS_X = -(map.getTilesWide() * TILE_SIZE - WIDTH);
                } else if (-playerInstances.get(currentPlayer).getX() + WIDTH / 2 < 0) {
                    DIS_X = -playerInstances.get(currentPlayer).getX() + WIDTH / 2;
                } else {
                    DIS_X = 0;
                }
                if (playerInstances.get(currentPlayer).getY() > (map.getTilesHigh() * TILE_SIZE - (HEIGHT / 2))) {
                    DIS_Y = -(map.getTilesHigh() * TILE_SIZE - HEIGHT);
                } else if (-playerInstances.get(currentPlayer).getY() + Renderer.HEIGHT / 2 < 0) {
                    DIS_Y = -playerInstances.get(currentPlayer).getY() + Renderer.HEIGHT / 2;
                } else {
                    DIS_Y = 0;
                }
                break;
            case HORIZONTAL_SCROLL:
                DIS_X -= Clock.Delta() * 16;
                DIS_Y = 0;
                break;
            case VERTICAL_SCROLL:
                DIS_X = 0;
                DIS_Y += Clock.Delta() * 16;
                break;
        }
    }

    private void updateTime() {
        timeOnLevel.update();
        internalAlarmTimer.update();
        if (currentGameState == GameStates.ALARM) {
            internalAlarmTimer.unpause();
        } else {
            internalAlarmTimer.pause();
        }
    }

    private void detectIfButtonHitOnGameEnd() {
        if (Mouse.isButtonDown(0)) {
            if (gameEndUI.isButtonClicked("GoBack")) MenuRun.setState(Menus.MAIN_MENU);
        }
    }

    private void drawAll() {
        drawMap();
        for (Enemy enemy : enemies) {
            enemy.draw();
        }
        for (Item item : items) {
            item.draw();
        }
        playerInstances.forEach((p) -> p.draw());
        ui.draw();
        updateText();
        if (currentGameState == GameStates.ALARM) {
            alarm();
        } else {
            ui.showString("alarm", false);
        }
        ui.drawAllStrings();
    }

    /**
     * Method to update all enemies
     */
    private void updateEnemies() {
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    private void updateText() {
        ui.changeString("fps", Lang.get("game.ui.fps") + MenuRun.framesInLastSecond);
        ui.changeString("time", Lang.get("game.ui.time") + timeOnLevel.getString());
        ui.changeString("itemsStolen", Lang.get("game.ui.stolen_items") + stolenItems + "/" + requiredItems);
        ui.setStringPos("itemsStolen", ((float) WIDTH / 2) - (ui.getString("itemsStolen").getWidth() / 2), HEIGHT - 112);
        if (timeOnLevel.getHours() == 0 && timeOnLevel.getSeconds() == 0) {
            ui.changeStringColor("time", Color.green);
        } else {
            ui.changeStringColor("time", Color.white);
        }
        if (currentGameState == GameStates.ALARM) {
            ui.changeString("alarm", Lang.get("game.ui.alarm") + (int) Math.ceil(internalAlarmTimer.getTotalSeconds()));
            alarmTimer.setX(((float) WIDTH / 2) - (alarmTimer.getWidth() / 2));
        }
    }

    public void removeItemFromGame(Item item) {
        items.remove(item);
    }

    public void addItemToGame(Item item) {
        items.add(item);
    }

    public void endGame(boolean win) {
        String sWin = "lose";
        if (win) {
            sWin = "win";
        }
        currentGameState = GameStates.GAME_END;
        Drawer.drawQuadTex(gameEndTex, ((float) WIDTH / 2) - ((float) gameEndTex.getImageWidth()) / 2, ((float) HEIGHT / 2) - ((float) gameEndTex.getImageHeight() / 2));
        gameEndUI.drawString(new TextBlock(gameEndUI, "GameOver", Lang.get("game.end." + sWin), 0, 256, 56f, Color.orange));
        gameEndUI.addButton("GoBack", new Texture[]{Drawer.LoadPNG("buttons/game_buttons/end_game_btn")}, (WIDTH / 2) - (Drawer.LoadPNG("buttons/game_buttons/end_game_btn").getImageWidth() / 2), 512 - 64, new TextBlock(gameEndUI, "EndGame", Lang.get("game.end.go_back"), 0, 0, 32f), true, true);
        gameEndUI.getString("GameOver").setX(((float) WIDTH / 2) - (gameEndUI.getString("GameOver").getWidth()));
        gameEndUI.setStringPos("GameOver", ((float) WIDTH / 2) - (gameEndUI.getString("GameOver").getX() / 2), 256);
        gameEndUI.drawAllStrings();
        gameEndUI.draw();
    }

    public void stealItem() {
        stolenItems++;
    }

    private void drawMap() {
        map.draw();
    }

    public ArrayList<Player> getPlayerInstances() {
        return playerInstances;
    }

    public TileMap getMap() {
        return map;
    }

    public void setState(GameStates state) {
        this.currentGameState = state;
    }

    public GameStates currentState() {
        return currentGameState;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getRequiredItems() {
        return requiredItems;
    }

    public int getStolenItems() {
        return stolenItems;
    }

    public int getMaxInventorySlots() {
        return inventorySlots;
    }

    public void setPressurePlateActive(boolean active) {
        pressurePlatesActive = active;
    }

    public Player getCurrentPlayer() {
        return playerInstances.get(currentPlayer);
    }
    public boolean pressurePlatesActive() {
        return pressurePlatesActive;
    }
    public int getCurrentPlayerIndex() {
        return currentPlayer;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
