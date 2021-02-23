package com.wizatar08.escapemaze.menus;

import com.google.gson.Gson;
import com.wizatar08.escapemaze.game.JSONLevel;
import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.game.TileMap;
import com.wizatar08.escapemaze.helpers.ExternalMapHandler;
import org.lwjgl.util.glu.Project;

import java.io.InputStreamReader;

public class Game {
    // Initialize variables
    private Gson gson;
    private int levelNumber;
    private TileMap map;
    private String levelName;
    private JSONLevel level;
    private Player player;

    public Game() {
        gson = new Gson();
        levelNumber = MenuRun.getLevel();
        InputStreamReader reader = new InputStreamReader(Project.class.getClassLoader().getResourceAsStream("resources/level_data/lvl" + levelNumber + ".json"));
        level = gson.fromJson(reader, JSONLevel.class);
        System.out.println(level.getPlayerStartPos()[0] + ", " + level.getPlayerStartPos()[1]);
        map = ExternalMapHandler.LoadMap(level.getMap());
        player = new Player(level.getPlayerStartPos()[0], level.getPlayerStartPos()[1], map);
    }

    public void update() {
        draw();
        player.update();
    }

    private void draw() {
        drawMap();
        player.draw();
    }

    private void drawMap() {
        map.draw();
    }
}
