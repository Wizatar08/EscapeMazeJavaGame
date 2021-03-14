package com.wizatar08.escapemaze.menus;

import com.google.gson.Gson;
import com.wizatar08.escapemaze.game.JSONLevel;
import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;
import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.helpers.*;
import com.wizatar08.escapemaze.helpers.ui.UI;
import com.wizatar08.escapemaze.map.TileMap;
import com.wizatar08.escapemaze.render.Renderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.Project;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class Game {
    // Initialize variables
    private Gson gson;
    private int levelNumber;
    private TileMap map;
    private String levelName;
    private JSONLevel level;
    private Player player;
    private ArrayList<Enemy> enemies;
    private GameStates currentGameState, prevGameState;
    private UI ui;
    private TextBlock fpsDisplay, timeDisplay, blank;
    private Timer timeOnLevel;
    private MapScroll scrollType;
    public static float DIS_X, DIS_Y;

    public Game() {
        gson = new Gson();
        levelNumber = MenuRun.getLevel();
        InputStreamReader reader = new InputStreamReader(Project.class.getClassLoader().getResourceAsStream("resources/level_data/lvl" + levelNumber + ".json"));
        level = gson.fromJson(reader, JSONLevel.class);
        map = ExternalMapHandler.LoadMap(level.getMap());
        player = new Player(level.getPlayerStartPos()[0], level.getPlayerStartPos()[1], map);
        enemies = level.getEnemies(this);
        currentGameState = GameStates.NORMAL;
        ui = new UI();
        blank = new TextBlock(ui, "blank", "", 0, 0, 24f, Color.white);
        fpsDisplay = new TextBlock(ui, "fps", "", 924, 4, 24f, Color.gray);
        timeDisplay = new TextBlock(ui, "time", "", 4, 4, 24f, Color.white);
        ui.drawString(fpsDisplay);
        ui.drawString(timeDisplay);
        ui.drawString(blank);
        timeOnLevel = new Timer();
        timeOnLevel.unpause();

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
        createMenu();
    }

    private void createMenu() {
    }

    private enum GameStates {
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

    public void update() {
        updateDisplacement();
        drawAll();
        detectKey();
        if (currentGameState != GameStates.GAME_END && currentGameState != GameStates.PAUSED) {
            updateEnemies();
            updateTime();
        }
    }

    private void updateDisplacement() {
        switch (scrollType) {
            case NONE:
                DIS_X = 0;
                DIS_Y = 0;
                break;
            case MOVE:
                if (player.getX() > (map.getTilesWide() * TILE_SIZE - (WIDTH / 2))) {
                    DIS_X = -(map.getTilesWide() * TILE_SIZE - WIDTH);
                } else if (-player.getX() + WIDTH / 2 < 0) {
                    DIS_X = -player.getX() + WIDTH / 2;
                } else {
                    DIS_X = 0;
                }
                if (player.getY() > (map.getTilesHigh() * TILE_SIZE - (HEIGHT / 2))) {
                    DIS_Y = -(map.getTilesHigh() * TILE_SIZE - HEIGHT);
                } else if (-player.getY() + Renderer.HEIGHT / 2 < 0) {
                    DIS_Y = -player.getY() + Renderer.HEIGHT / 2;
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
    }

    private void detectKey() {
        while (Keyboard.next()) {
            if (currentGameState == GameStates.NORMAL || currentGameState == GameStates.ALARM) {
                player.playerTapKeyDetection();
            }
            if (keyDown(Keyboard.KEY_ESCAPE)) {
                if (currentGameState == GameStates.NORMAL || currentGameState == GameStates.ALARM) {
                    prevGameState = currentGameState;
                    currentGameState = GameStates.PAUSED;
                } else if (currentGameState == GameStates.PAUSED) {
                    currentGameState = prevGameState;
                }
            }
        }
        if (currentGameState == GameStates.NORMAL || currentGameState == GameStates.ALARM) {
            player.playerPressAndHoldKeyDetection();
        }
    }

    private boolean keyDown(int key) {
        return (Keyboard.getEventKey() == key) && (Keyboard.getEventKeyState());
    }

    private void drawAll() {
        drawGame();
        player.draw(); // <--- This line of code is what makes the game freeze up
        drawEnemies();
    }

    /**
     * Method to update all enemies
     */
    private void updateEnemies() {
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    private void drawEnemies() {
        for (Enemy enemy : enemies) {
            enemy.draw();
        }
    }

    private void drawGame() {
        drawMap();
        ui.drawAllStrings();
        ui.draw();
        drawText();
    }

    private void drawText() {
        ui.changeString("fps", Lang.get("game.ui.fps") + MenuRun.framesInLastSecond);
        ui.changeString("time", Lang.get("game.ui.time") + timeOnLevel.getString());
        if (timeOnLevel.getHours() == 0 && timeOnLevel.getSeconds() == 0) {
            ui.changeColor("time", Color.green);
        } else {
            ui.changeColor("time", Color.white);
        }
    }

    private void drawMap() {
        map.draw();
    }

    public Player getPlayer() {
        return player;
    }

    public TileMap getMap() {
        return map;
    }
}
