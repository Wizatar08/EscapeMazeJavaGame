package com.wizatar08.escapemaze.menus;

import com.google.gson.Gson;
import com.oracle.javafx.jmx.json.JSONReader;
import com.wizatar08.escapemaze.game.JSONLevel;
import com.wizatar08.escapemaze.game.TileMap;
import org.lwjgl.util.glu.Project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Game {
    // Initialize variables
    private Gson gson;
    private int levelNumber;
    private TileMap map;
    private String levelName;

    public Game() {
        gson = new Gson();
        levelNumber = MenuRun.getLevel();
        InputStreamReader reader = new InputStreamReader(Project.class.getClassLoader().getResourceAsStream("resources/level_data/lvl" + levelNumber + ".json"));
        JSONLevel level = gson.fromJson(reader, JSONLevel.class);
        System.out.println(level.getLevelName() + ", " + level.getMap());
    }

    public void update() {
        draw();
    }

    private void draw() {
        drawMap();
    }

    private void drawMap() {
    }
}
