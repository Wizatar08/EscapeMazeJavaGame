package com.wizatar08.escapemaze.menus;

import com.google.gson.Gson;
import com.wizatar08.escapemaze.game.JSONLevel;
import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;
import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.map.TileMap;
import com.wizatar08.escapemaze.helpers.ExternalMapHandler;
import org.lwjgl.util.glu.Project;

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

    public Game() {
        gson = new Gson();
        levelNumber = MenuRun.getLevel();
        InputStreamReader reader = new InputStreamReader(Project.class.getClassLoader().getResourceAsStream("resources/level_data/lvl" + levelNumber + ".json"));
        level = gson.fromJson(reader, JSONLevel.class);
        System.out.println(level.getPlayerStartPos()[0] + ", " + level.getPlayerStartPos()[1]);
        map = ExternalMapHandler.LoadMap(level.getMap());
        player = new Player(level.getPlayerStartPos()[0], level.getPlayerStartPos()[1], map);
        enemies = level.getEnemies(this);
    }

    public void update() {
        draw();
        player.update();
        updateEnemies();
    }

    /**
     * Method to update all enemies
     */
    private void updateEnemies() {
        for (Enemy enemy : enemies) {
            enemy.update();
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
