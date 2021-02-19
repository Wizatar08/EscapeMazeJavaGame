package com.wizatar08.escapemaze.game;

import com.google.gson.annotations.SerializedName;

public class JSONLevel {
    // Initialize variables

    @SerializedName("map")
    private String map;

    @SerializedName("level_name")
    private String levelName;

    public JSONLevel(String map, String levelName) {
        this.map = map;
        this.levelName = levelName;
    }

    // Getters

    public String getLevelName() {
        return levelName;
    }

    public String getMap() {
        return map;
    }
}
