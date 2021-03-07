package com.wizatar08.escapemaze.helpers;

import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;

public class EnemyPathfinder {
    private Enemy enemy;
    private boolean scanResult;
    private RayTraceObject playerRayTrace;

    public EnemyPathfinder(Enemy enemy) {
        this.enemy = enemy;
        scanResult = false;
        playerRayTrace = new RayTraceObject(enemy);
    }

    public float getRotInDegrees(float currentX, float currentY, float toX, float toY) {
        return getRotInRadians(currentX, currentY, toX, toY) * 180 / (float) Math.PI;
    }

    public float getRotInRadians(float currentX, float currentY, float toX, float toY) {
        return (float) Math.atan2(toY - currentY, toX - currentX);
    }
    public float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    public float[] getAddedCoords(float rot) {
        float x = ((float) Math.cos(rot));
        float y = ((float) Math.sin(rot));
        return new float[]{x, y};
    }

    public boolean scanForWalls(int distanceView) {
        if (playerRayTrace.scanDistance(distanceView)) {
            return playerRayTrace.scanForWalls(16, playerRayTrace.getDistance(enemy.getX(), enemy.getY(), enemy.getPlayer().getX(), enemy.getPlayer().getY()), enemy.getX(), enemy.getY(), enemy.getPlayer().getX() - (enemy.getPlayer().getWidth() / 2), enemy.getPlayer().getY() - (enemy.getPlayer().getHeight() / 2));
        }
        return false;
    }

    public boolean scanResult() {
        return scanResult;
    }

    public void update() {
        playerRayTrace.newPos(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(), enemy.getPlayer().getX(), enemy.getPlayer().getY(), enemy.getPlayer().getWidth(), enemy.getPlayer().getHeight());
    }
}
