package com.wizatar08.escapemaze.game;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.wizatar08.escapemaze.game.game_entities.JSONItemClass;
import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;
import com.wizatar08.escapemaze.game.game_entities.JSONEnemyClass;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.helpers.Lang;
import com.wizatar08.escapemaze.helpers.visuals.TextBlock;
import com.wizatar08.escapemaze.helpers.ui.UI;
import com.wizatar08.escapemaze.menus.Game;
import org.lwjgl.util.glu.Project;
import org.newdawn.slick.Color;

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
    private JsonArray playerSettings;

    @SerializedName("enemies")
    private String enemyFileName;

    @SerializedName("items")
    private String itemsFileName;

    @SerializedName("scroll")
    private String scroll;

    @SerializedName("alarm_seconds")
    private int alarmSeconds;

    @SerializedName("inventory_slots")
    private int inventorySlots;

    @SerializedName("text")
    private JsonArray text;

    private Gson gson;

    public JSONLevel(String map, String levelName, JsonArray playerStartPos, String enemies, String items, String scroll, int alarmSeconds, int inventorySlots, JsonArray text) {
        this.map = map;
        this.levelName = levelName;
        this.playerSettings = playerStartPos;
        this.enemyFileName = enemies;
        this.itemsFileName = items;
        this.scroll = scroll;
        this.alarmSeconds = alarmSeconds;
        this.inventorySlots = inventorySlots;
        this.text = text;
    }

    public ArrayList<Enemy> getEnemies(Game game) {
        System.out.println(enemyFileName);
        this.gson = new Gson();
        System.out.println(Project.class.getClassLoader().getResourceAsStream("resources/level_enemies/" + enemyFileName + ".json"));
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Project.class.getClassLoader().getResourceAsStream("resources/level_enemies/" + enemyFileName + ".json")));
        JSONEnemyClass enemies = gson.fromJson(reader, JSONEnemyClass.class);
        return enemies.getEnemies(game);

    }

    public ArrayList<Item> getItems(Game game) {
        this.gson = new Gson();
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Project.class.getClassLoader().getResourceAsStream("resources/level_items/" + itemsFileName + ".json")));
        JSONItemClass items = gson.fromJson(reader, JSONItemClass.class);
        return items.getItems(game);

    }

    // Getters

    public String getLevelName() {
        return levelName;
    }

    public String getMap() {
        return map;
    }

    public int[][] getPlayerStartPos() {
        int[][] poss = new int[playerSettings.size()][2];
        for (int i = 0; i < playerSettings.getAsJsonArray().size(); i++) {
            poss[i][0] = playerSettings.get(i).getAsJsonObject().get("x").getAsInt();
            poss[i][1] = playerSettings.get(i).getAsJsonObject().get("y").getAsInt();
        }
        return poss;
    }

    public TextBlock[] getText(UI gameTextUI) {
        try {
            TextBlock[] textBlocks = new TextBlock[text.getAsJsonArray().size()];
            for (int i = 0; i < text.getAsJsonArray().size(); i++) {
                JsonObject obj = text.get(i).getAsJsonObject();
                String t = Lang.get(obj.get("text").getAsString());
                int size = obj.get("size").getAsInt();
                String color = obj.get("color").getAsString();
                int x = obj.get("x").getAsInt();
                int y = obj.get("y").getAsInt();
                textBlocks[i] = new TextBlock(gameTextUI, "string" + i, t, x, y, size, Color.decode(color));
            }
            return textBlocks;
        } catch (NullPointerException e) {
            return new TextBlock[0];
        }
    }

    public String getScroll() {
        return scroll;
    }

    public int getAlarmSeconds() {
        return alarmSeconds;
    }

    public int getInventorySlots() {
        return inventorySlots;
    }

    public int[] getPlayerTexNames() {
        int[] names = new int[playerSettings.size()];
        for (int i = 0; i < playerSettings.getAsJsonArray().size(); i++) {
            names[i] = Integer.parseInt(playerSettings.get(i).getAsJsonObject().get("color").getAsString(), 16);
        }
        return names;
    }
}
