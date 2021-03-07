package com.wizatar08.escapemaze.menus;

import com.google.gson.Gson;
import com.wizatar08.escapemaze.game.JSONLevel;
import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;
import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.helpers.Drawer;
import com.wizatar08.escapemaze.helpers.ui.UI;
import com.wizatar08.escapemaze.map.TileMap;
import com.wizatar08.escapemaze.helpers.ExternalMapHandler;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.Project;
import org.newdawn.slick.opengl.Texture;

import java.io.InputStreamReader;
import java.util.ArrayList;

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

    public Game() {
        gson = new Gson();
        levelNumber = MenuRun.getLevel();
        InputStreamReader reader = new InputStreamReader(Project.class.getClassLoader().getResourceAsStream("resources/level_data/lvl" + levelNumber + ".json"));
        level = gson.fromJson(reader, JSONLevel.class);
        System.out.println(level.getPlayerStartPos()[0] + ", " + level.getPlayerStartPos()[1]);
        map = ExternalMapHandler.LoadMap(level.getMap());
        player = new Player(level.getPlayerStartPos()[0], level.getPlayerStartPos()[1], map);
        enemies = level.getEnemies(this);
        currentGameState = GameStates.NORMAL;
        ui = new UI();
        createMenu();
    }

    private void createMenu() {
    }

    private enum GameStates {
        NORMAL,
        ALARM,
        GAME_END,
        PAUSED;
    }

    public void update() {
        drawAll();
        detectKey();
        if (currentGameState != GameStates.GAME_END && currentGameState != GameStates.PAUSED) {
            player.update();
            updateEnemies();
        }
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
        draw();
        player.draw();
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

    private void draw() {
        drawMap();
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
