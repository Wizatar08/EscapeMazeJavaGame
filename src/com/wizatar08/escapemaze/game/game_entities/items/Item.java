package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.helpers.Drawer;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.menus.Game;
import com.wizatar08.escapemaze.render.Renderer;
import org.newdawn.slick.opengl.Texture;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.wizatar08.escapemaze.helpers.Drawer.drawQuadTex;

public class Item implements Entity {
    private float x, y, texX, texY, width, height, weight, speedBoost;
    private int passLevel;
    private Texture texture;
    private String id;
    private boolean inInventory, required, isPass, isPowerSource, isGasSource, isAdminAccessor;
    private Timer cooldownPickupTimer;

    public Item(Game game, ItemType type, Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = texture.getImageWidth();
        this.height = texture.getImageHeight();
        this.texX = x + ((Renderer.TILE_SIZE - width) / 2);
        this.texY = y + ((Renderer.TILE_SIZE - height) / 2);
        this.id = type.getId();
        this.inInventory = false;
        this.weight = type.getWeight();
        this.required = type.isRequired();
        this.isPass = type.isPass();
        this.passLevel = type.passLevel();
        this.isAdminAccessor = type.isAdminAccessor();
        this.isPowerSource = type.isPower();
        this.isGasSource = type.isGas();

        this.speedBoost = 0;

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
        this.cooldownPickupTimer.setTime(3);
        this.inInventory = inInventory;
    }

    public float getWeight() {
        return weight;
    }

    public void hitItem(Game gameController) {
    }

    public boolean canUse() {
        return false;
    }

    public void use() {}

    public boolean isRequired() {
        return required;
    }
    public boolean isPass() {
        return isPass;
    }
    public int getPassLevel() {
        return passLevel;
    }
    public boolean isPowerSource() {
        return isPowerSource;
    }
    public boolean isGasSource() {
        return isGasSource;
    }
    public float getSpeedBoost() {
        return speedBoost;
    }
    public void setSpeedBoost(float speedBoost) {
        this.speedBoost = speedBoost;
    }
    public boolean isAdminAccessor() {
        return isAdminAccessor;
    }
}
