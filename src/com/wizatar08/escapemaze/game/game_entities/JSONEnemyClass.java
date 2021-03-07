package com.wizatar08.escapemaze.game.game_entities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;
import com.wizatar08.escapemaze.menus.Game;

import java.util.ArrayList;

public class JSONEnemyClass {
    // Initialize variables

    @SerializedName("enemies")
    private JsonArray enemies;

    private Gson gson;

    public JSONEnemyClass(JsonArray enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Enemy> getEnemies(Game game) {
        gson = new Gson();
        int[][][] enemyList = new int[enemies.size()][1024][2];
        int index[] = new int[enemies.size()];
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < enemies.get(i).getAsJsonObject().get("paths").getAsJsonArray().size(); j++) {
                JsonElement jsonArray = enemies.get(i).getAsJsonObject().get("paths").getAsJsonArray().get(j);
                for (int k = 0; k < jsonArray.getAsJsonArray().size(); k++) {
                    enemyList[i][j][k] = jsonArray.getAsJsonArray().get(k).getAsInt();
                }
                index[i]++;
            }
        }
        ArrayList<Enemy> enemiesList = new ArrayList<>();
        for (int i = 0; i < enemies.size(); i++) {
            int[][] enemyPathList = new int[enemies.get(i).getAsJsonObject().get("paths").getAsJsonArray().size()][2];
            for(int j = 0; j < enemies.get(i).getAsJsonObject().get("paths").getAsJsonArray().size(); j++) {
                enemyPathList[j] = enemyList[i][j];
            }
            //System.out.println(enemyPathList.length + ", " + enemies.get(i).getAsJsonObject().get("paths").getAsJsonArray().size() + ", " + enemies.size());
            enemiesList.add(new Enemy(game,
                    enemies.get(i).getAsJsonObject().get("id").getAsInt(),
                    enemyPathList));
        }
        return enemiesList;
    }
}
