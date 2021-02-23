package com.wizatar08.escapemaze.game;

import com.google.gson.annotations.SerializedName;

public class JSONLevel {
    // Initialize variables

    @SerializedName("map")
    private String map;

    @SerializedName("level_name")
    private String levelName;

    @SerializedName("player_start")
    private int[] playerStartPos;

    public JSONLevel(String map, String levelName, int[] playerStartPos) {
        this.map = map;
        this.levelName = levelName;
        this.playerStartPos = playerStartPos;
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
}
