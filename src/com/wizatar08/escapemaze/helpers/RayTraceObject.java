package com.wizatar08.escapemaze.helpers;

import com.wizatar08.escapemaze.game.game_entities.enemies.Enemy;
import com.wizatar08.escapemaze.render.Renderer;
import com.wizatar08.escapemaze.visuals.Tex;

public class RayTraceObject {
    private float x, y, targetX, targetY, width, height, targetWidth, targetHeight;
    private int angle;
    private Enemy rayTraceEnemy;

    // For testing purposes
    private Tex dot = new Tex("shapes/black_64");

    public RayTraceObject(Enemy rayTraceMainObject) {
        this.rayTraceEnemy = rayTraceMainObject;
    }

    /**
     * Scan for distance between two objects and return whether that distance is less than the specified distance.
     * @param distanceView
     * @return
     */
    public boolean scanDistance(int distanceView) {
        float dist = getDistance(x + (width / 2), y + (height / 2), targetX + (targetWidth / 2), targetY + (targetHeight / 2));
        return dist <= distanceView;
    }

    /**
     * Determine if, in a ray trace, there is a wall.
     * @param precision
     * @param rayTraceDistance
     * @return
     */
    public boolean scanForWalls(int precision, float rayTraceDistance, float startX, float startY, float endX, float endY) {
        float angle = getRotInRadians(startX, startY, endX, endY);
        for (int i = 0; i < Math.round(rayTraceDistance / precision); i++) {
            double x = startX + 32 + (i * Math.cos(angle) * precision);
            double y = startY + 32 + (i * Math.sin(angle) * precision);
            int xPlace = (int) Math.floor(x / Renderer.TILE_SIZE);
            int yPlace = (int) Math.floor(y / Renderer.TILE_SIZE);
            if (rayTraceEnemy != null) {
                if (!rayTraceEnemy.getGameController().getMap().getMapAsArray()[xPlace][yPlace].getType().isRayTraceSeeable().condition(rayTraceEnemy.getGameController().getMap().getMapAsArray()[xPlace][yPlace])) {
                    return false;
                }
            }
            // Testing purposes:
            //dot.draw((float) x, (float) y, 0, 3, 3);
        }
        return true;
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

    public void newPos(float yourX, float yourY, float yourWidth, float yourHeight, float targetX, float targetY, float targetWidth, float targetHeight) {
        setX(yourX);
        setY(yourY);
        setTargetX(targetX);
        setTargetY(targetY);
        setWidth(yourWidth);
        setHeight(yourHeight);
        setTargetWidth(targetWidth);
        setTargetWidth(targetHeight);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    public void setTargetWidth(float targetWidth) {
        this.targetWidth = targetWidth;
    }

    public void setTargetHeight(float targetHeight) {
        this.targetHeight = targetHeight;
    }
}
