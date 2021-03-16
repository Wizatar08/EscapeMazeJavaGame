package com.wizatar08.escapemaze.game;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;
import com.wizatar08.escapemaze.game.game_entities.JSONEnemyClass;
import com.wizatar08.escapemaze.menus.Game;
import org.lwjgl.util.glu.Project;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class JSONLevel {
    // Initialize variables

    @SerializedName("map")
    private String map;

    @SerializedName("level_name")
    private String levelName;

    @SerializedName("player_start")
    private int[] playerStartPos;

    @SerializedName("enemies")
    private String enemyFileName;

    @SerializedName("scroll")
    private String scroll;

    @SerializedName("alarm_seconds")
    private int alarmSeconds;

    private Gson gson;

    public JSONLevel(String map, String levelName, int[] playerStartPos, String enemies, String scroll, int alarmSeconds) {
        this.map = map;
        this.levelName = levelName;
        this.playerStartPos = playerStartPos;
        this.enemyFileName = enemies;
        this.scroll = scroll;
        this.alarmSeconds = alarmSeconds;
    }

    public ArrayList<Enemy> getEnemies(Game game) {
        this.gson = new Gson();
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Project.class.getClassLoader().getResourceAsStream("resources/level_enemies/" + enemyFileName + ".json")));
        JSONEnemyClass enemies = gson.fromJson(reader, JSONEnemyClass.class);
        return enemies.getEnemies(game);

    }

    // Getters

    public String getLevelName() {
        return levelName;
    }

    public String getMap() {
        return map;
    }

    public int[] getPlayerStartPos() {
        return playerStartPos;
    }

    public String getScroll() {
        return scroll;
    }

    public int getAlarmSeconds() {
        return alarmSeconds;
    }
}
