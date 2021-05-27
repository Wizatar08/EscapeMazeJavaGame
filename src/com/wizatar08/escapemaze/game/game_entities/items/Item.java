package com.wizatar08.escapemaze.game.game_entities.items;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.visuals.Drawer;
import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.menus.Game;
import com.wizatar08.escapemaze.render.Renderer;
import org.lwjgl.Sys;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Item implements Entity {
    private final Game gameController;
    private float x, y, texX, texY, width, height, weight, speedBoost;
    private int passLevel, playerInd;
    private Tex texture;
    private String id;
    private boolean inInventory, required, isPass, isPowerSource, isGasSource, isAdminAccessor, displayOnPlayer;
    private Timer cooldownPickupTimer;
    private ItemType type;
    public Condition condition;

    public Item(Game game, ItemType type, Tex texture, JsonObject data, float x, float y) {
        this.gameController = game;
        this.type = type;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = texture.getOpenGLTex().getImageWidth();
        this.height = texture.getOpenGLTex().getImageHeight();
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
        this.displayOnPlayer = false;

        this.speedBoost = 0;

        cooldownPickupTimer = new Timer(Timer.TimerModes.COUNT_DOWN, 0);

        createCondition();
    }

    /**
     * Class meant to be overwritten by subclasses. This will determine if a particular property of an item is valid for an event.
     */
    public void createCondition() {
        condition = null;
    }

    private void updateTimer() {
        cooldownPickupTimer.update();
        if (cooldownPickupTimer.getTotalSeconds() <= 0) {
            cooldownPickupTimer.pause();
        } else {
            cooldownPickupTimer.unpause();
        }
    }

    public void displayOnPlayer(boolean display) {
        playerInd = gameController.getCurrentPlayerIndex();
        displayOnPlayer = display;
    }

    private void drawOnPlayer() {
        if (displayOnPlayer && inInventory) {
            Player p = gameController.getPlayerInstances().get(playerInd);
            texture.draw(p.getX() - ((width - p.getWidth()) / 2), p.getY() - ((height - p.getHeight()) / 2));
        }
    }

    public void destroy() {
        if (inInventory) {
            gameController.getPlayerInstances().forEach((p) -> {
                if (p.getInventory().hasItem(this)) {
                    p.getInventory().remove(p.getInventory().getItemIndex(this));
                }
            });
        } else {
            gameController.removeItemFromGame(this);
        }
    }

    public boolean isTouching(Item... items) {
        int required = items.length, found = 0;
        ArrayList<? extends Item> itemsTouched;
        for (Item item : items) {
            if (Drawer.checkCollision(x, y, width, height, item.getX(), item.getY(), item.getWidth(), item.getHeight())) {
                found++;
            }
        }
        return required >= found;
    }

    @Override
    public void update() {
        updateTimer();
        drawOnPlayer();
        if (condition != null) {
            condition.update();
        }
    }

    /**
     * Called when an event with this is called.
     */
    public void onTrigger(ArrayList<Item> items) {

    }

    public void drop() {}

    public void onPickup() {}

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

    public Tex getTexture() {
        return texture;
    }

    @Override
    public void draw() {
        texture.draw(x + Game.DIS_X, y + Game.DIS_Y);
    }

    public void draw(float xVal, float yVal) {
        texture.draw(xVal, yVal);
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
    public boolean isDisplayedOnPlayer() {
        return displayOnPlayer;
    }
    public ItemType getType() {
        return type;
    }
    public boolean isRunning() {
        return false;
    }
    public Condition getConditions() {
        return condition;
    }


    public class Condition {
        public ArrayList<Field> conditions;
        private Item className;

        public Condition() {
            className = null;
            conditions = new ArrayList<>();
        }

        public Condition(Item clazz, String... boolVarNames) {
            conditions = new ArrayList<>();
            this.className = clazz;
            for (String b : boolVarNames) {
                try {
                    this.conditions.add(clazz.getClass().getDeclaredField(b));
                } catch (NoSuchFieldException e) {
                    System.err.println("Could not find field in class " + clazz.getClass().getName() + ": " + b);
                }
            }
        }

        public boolean passes() {
            boolean test = true;
            for (Field b : conditions) {
                try {
                    test = test && ((boolean) b.get(className));
                } catch (IllegalAccessException e) {
                    System.err.println("Cannot find method " + b.getName() + " in class " + className.getClass().getName());
                }
            }
            System.out.println(test);
            return test;
        }

        public void update() {

        }

    }
}
