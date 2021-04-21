package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.game.game_entities.items.subclasses.durability.RechargableBattery;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.helpers.visuals.Drawer;
import com.wizatar08.escapemaze.helpers.visuals.Tex;
import com.wizatar08.escapemaze.menus.Game;

import java.util.ArrayList;

public class HelperBot extends RechargableBattery {
    private Game gameController;
    private Tex unpoweredTex, poweredTex, activeTex;
    private boolean isPowered, isActive;
    private ArrayList<Item> itemsTouching;
    private Timer timerToTransform;

    public HelperBot(Game game, ItemType type, Tex texture, JsonObject data, float x, float y) {
        super(game, type, texture, data, x, y);
        gameController = game;
        unpoweredTex = texture;
        poweredTex = (Tex) type.getClassArgs()[1];
        activeTex = (Tex) type.getClassArgs()[2];
        setDurability(0);
        isPowered = false;
        isActive = false;
        itemsTouching = new ArrayList<>();
        timerToTransform = new Timer(Timer.TimerModes.COUNT_DOWN, 10);
    }

    @Override
    public void update() {
        timerToTransform.update();
        super.update();
        if (!isPowered) {
            isActive = false;
        }
        isPowered = getDurabilityPercentage() >= 1.0f;

        if (!isInInventory()) {
            for (Item item : gameController.getItems()) {
                if (Drawer.checkCollision(getX(), getY(), getWidth(), getHeight(), item.getX(), item.getY(), item.getWidth(), item.getHeight())) {
                    addToTouching(item);
                } else {
                    removeFromTouching(item);
                }
            }
            if (isPowered) {
                gameController.doHelperBotTasks(itemsTouching, this);
            }
        } else {
            itemsTouching.clear();
        }
    }

    public void addToTouching(Item item) {
        if (!itemsTouching.contains(item)) {
            itemsTouching.add(item);
        }
    }

    public void removeFromTouching(Item item) {
        itemsTouching.remove(item);
    }



    @Override
    public void draw(float xVal, float yVal) {
        getCurrentTex().draw(xVal, yVal);
        if (isInInventory()) {
            super.getBackgroundBarTex().draw(xVal + 2, yVal);
            super.getBarTex().draw(xVal + 2, yVal + 56, 0, super.getBarTex().getOpenGLTex().getImageWidth() * super.getDurabilityPercentage());
        }
    }

    public void beginCountdown() {
        timerToTransform.unpause();
        isActive = true;
    }

    public void endCountdown() {
        timerToTransform.setTime(10);
        timerToTransform.pause();
        isActive = false;
    }

    @Override
    public void onPickup() {
        endCountdown();
    }

    public boolean countdownAtZero() {
        return timerToTransform.getTotalSeconds() <= 0;
    }

    @Override
    public void draw() {
        getCurrentTex().draw(getX() + Game.DIS_X, getY() + Game.DIS_Y);
    }

    @Override
    public Tex getBatteryChargingTexture() {
        return getCurrentTex();
    }

    private Tex getCurrentTex() {
        if (isPowered) {
            if (isActive) {
                return activeTex;
            } else {
                return poweredTex;
            }
        } else {
            return unpoweredTex;
        }
    }
}
