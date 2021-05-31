package com.wizatar08.escapemaze.helpers;

import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.visuals.Drawer;
import com.wizatar08.escapemaze.visuals.Tex;

import java.util.ArrayList;
import java.util.Collections;

public class Hitbox {
    private float x, y, width, height;
    private Entity object;
    private ArrayList<Entity> checkCollisionWith;

    public Hitbox(Entity object, float x, float y, float width, float height) {
        this.object = object;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.checkCollisionWith = new ArrayList<>();
    }

    public void addCollidingEntityDetector(Entity entity) {
        checkCollisionWith.add(entity);
    }

    public void removeCollidingEntityDetector(Entity entity) {
        checkCollisionWith.remove(entity);
    }

    public void update() {
        for (Entity collider : checkCollisionWith) {
            if (Drawer.checkCollision(object.getX() + x, object.getY() + y, width, height, collider.getX(), collider.getY(), collider.getWidth(), collider.getHeight())) {
                object.onCollisionWith(collider);
            }
        }
    }
}
