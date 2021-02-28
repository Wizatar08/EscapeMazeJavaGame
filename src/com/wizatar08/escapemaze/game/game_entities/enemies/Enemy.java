package com.wizatar08.escapemaze.game.game_entities;

public class Enemy {
    private int id;
    private int[][] pathCoords;
    private int currentPathPoint;

    public Enemy(int id, int[][] path) {
        this.id = id;
        this.pathCoords = path;
        this.currentPathPoint = 0;
    }

    public int getId() {
        return id;
    }

    public int[][] getPathCoords() {
        return pathCoords;
    }

    public int getCurrentPathIndex() {
        return currentPathPoint;
    }

    public void nextPath() {
        currentPathPoint++;
        if (currentPathPoint >= pathCoords.length) {
            currentPathPoint = 0;
        }
    }
}
