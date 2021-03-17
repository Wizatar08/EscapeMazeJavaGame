package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.helpers.Drawer;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.menus.Game;
import com.wizatar08.escapemaze.render.Renderer;
import org.newdawn.slick.opengl.Texture;

public class Item implements Entity {
    private float x, y, texX, texY, width, height;
    private Texture texture;
    private ItemType type;
    private String id;
    private boolean inInventory;
    private Timer cooldownPickupTimer;

    public Item(ItemType type, Texture texture, float x, float y) {
        this.texture = texture;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = texture.getImageWidth();
        this.height = texture.getImageHeight();
        this.texX = x + ((Renderer.TILE_SIZE - width) / 2);
        this.texY = y + ((Renderer.TILE_SIZE - height) / 2);
        this.id = type.getId();
        this.inInventory = false;
        cooldownPickupTimer = new Timer(Timer.TimerModes.COUNT_DOWN, 0);
    }

    private void updateTimer() {
        cooldownPickupTimer.update();
        if (cooldownPickupTimer.getTotalSeconds() <= 0) {
            cooldownPickupTimer.pause();
        } else {
            cooldownPickupTimer.unpause();
        }
    }

    @Override
    public void update() {
        updateTimer();
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setX(float x) {
        this.x = x;
        this.texX = x + ((Renderer.TILE_SIZE - width) / 2);
    }

    @Override
    public void setY(float y) {
        this.y = y;
        this.texY = y + ((Renderer.TILE_SIZE - height) / 2);
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void draw() {
        Drawer.drawQuadTex(texture, x + Game.DIS_X, y + Game.DIS_Y);
    }

    public String getId() {
        return id;
    }

    public float getTexX() {
        return texX;
    }

    public float getTexY() {
        return texY;
    }

    public boolean isInInventory() {
        return inInventory;
    }

    public boolean canPickUp() {
        return cooldownPickupTimer.getTotalSeconds() <= 0;
    }

    public void setIsInInventory(boolean inInventory) {
        this.cooldownPickupTimer.setTime(5);
        this.inInventory = inInventory;
    }
}
