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
    private float x, y, texX, texY, width, height, weight;
    private Texture texture;
    private ItemType type;
    private String id;
    private boolean inInventory, required;
    private Timer cooldownPickupTimer;
    private Class<?> clazz;
    private Object itemClass;
    private Game gameController;

    public Item(Game game, ItemType type, Texture texture, float x, float y) {
        this.gameController = game;
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
        this.weight = type.getWeight();
        this.required = type.isRequired();
        cooldownPickupTimer = new Timer(Timer.TimerModes.COUNT_DOWN, 0);
        itemClass = null;
        if (type.getClassname() != null) {
            try {
                this.clazz = type.getClassname();
                itemClass = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            this.clazz = null;
        }
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
        try {
            if (clazz != null) {
                System.out.println(!this.isInInventory());
                if (!this.isInInventory()) {
                    clazz.getMethod("update", Item.class, Game.class, Player.class).invoke(itemClass, this, gameController, gameController.getPlayer());
                } else {
                    clazz.getMethod("updateInven", Item.class, Game.class, Player.class).invoke(itemClass, this, gameController, gameController.getPlayer());
                }
            }
        } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
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

    private void laserDeactivatorAbility(Tile tile) {
        tile.setSecurity(false);
    }

    public void hitItem(Game gameController) {
        try {
            if (clazz != null) {
                clazz.getMethod("onHit", Item.class, Game.class, Player.class).invoke(itemClass, this, gameController, gameController.getPlayer());
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public boolean doUse(Item item) {
        try {
            if (clazz != null) {
                if ((boolean) clazz.getMethod("canUseItem", Item.class, Game.class, Player.class).invoke(itemClass, item, gameController, gameController.getPlayer())) {
                    clazz.getMethod("use", Item.class, Game.class, Player.class).invoke(itemClass, item, gameController, gameController.getPlayer());
                    return true;
                } else {
                    return false;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isRequired() {
        return required;
    }
}
