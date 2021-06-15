package com.wizatar08.escapemaze.helpers;

import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.visuals.Drawer;
import com.wizatar08.escapemaze.visuals.Tex;

import java.util.ArrayList;

public class Hitbox {
    private float x, y, width, height;
    private Entity object;

    // Testing variable
    private Tex testBox;

    public Hitbox(Entity object, float x, float y, float width, float height) {
        this(object, x, y, width, height, false);
    }


    public Hitbox(Entity object, float x, float y, float width, float height, boolean test) {
        this.object = object;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if (test) {
            testBox = new Tex("shapes/box");
        }
    }

    public void update() {
        if (testBox != null) {
            //testBox.draw(object.getX() + x, object.getY() + y);
        }
    }

    /**
     * Detects if this hitbox is touching the entity's hitbox
     * @param entity
     * @return
     */
    public boolean collidesWith(Entity entity) {
        return checkCollision(object.getX() + x, object.getY() + y, width, height, entity.getHitbox().getX() + entity.getX(), entity.getHitbox().getY() + entity.getY(), entity.getHitbox().getWidth(), entity.getHitbox().getHeight());
    }

    /**
     * Detects if this hitbox is touching a point
     * @param xPoint
     * @param yPoint
     * @return
     */
    public boolean intersectsWith(float xPoint, float yPoint) {
        return checkCollision(object.getX() + x, object.getY() + y, width, height, xPoint, yPoint, 1, 1);
    }

    private boolean checkCollision(float x1, float y1, float width1, float height1, float x2, float y2, float width2, float height2) {
        return x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
