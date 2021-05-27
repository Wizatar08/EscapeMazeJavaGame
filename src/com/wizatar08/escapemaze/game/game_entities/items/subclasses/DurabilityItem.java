package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.menus.Game;

public class DurabilityItem extends Item {
    private final Timer durabilityTime;
    private Tex backgroundTex, barTex;
    private float percentage;
    private final float onePercent;

    public DurabilityItem(Game game, ItemType type, JsonObject data, float x, float y) {
        super(game, type, data, x, y);
        durabilityTime = new Timer(Timer.TimerModes.COUNT_DOWN, (int) type.getClassArgs()[0]);
        backgroundTex = new Tex("game/durability/background");
        barTex = new Tex("game/durability/bar");
        onePercent = (float) durabilityTime.getStartingSeconds() / 100f;
    }

    @Override
    public void update() {
        super.update();
        durabilityTime.update();
        percentage = durabilityTime.getTotalSeconds() / durabilityTime.getStartingSeconds();
        if (percentage <= 0) {
            durabilityTime.pause();
            durabilityTime.setTime(0);
            percentage = 0;
        }
    }

    @Override
    public void draw() {
        if (isInInventory()) {
            backgroundTex.draw(getX() + 2, getY());
            barTex.draw(getX() + 2, getY());
        }
        super.draw();
    }

    public void deplete(float percent) {
        durabilityTime.setTime(durabilityTime.getTotalSeconds() - (durabilityTime.getStartingSeconds() * percent));
    }

    public void add(float percent) {
        durabilityTime.setTime(durabilityTime.getTotalSeconds() + (durabilityTime.getStartingSeconds() * percent));
        if (durabilityTime.getTotalSeconds() > durabilityTime.getStartingSeconds()) {
            durabilityTime.setTime(durabilityTime.getStartingSeconds());
        }
    }

    @Override
    public void draw(float xVal, float yVal) {
        super.draw(xVal, yVal);
        backgroundTex.draw(xVal + 2, yVal);
        barTex.draw(xVal + 2, yVal + 56, 0, barTex.getOpenGLTex().getImageWidth() * (durabilityTime.getTotalSeconds() / durabilityTime.getStartingSeconds()));
    }

    public void setDurability(float durability) {
        durabilityTime.setTime(durability);
    }

    public void fillDurability() {
        durabilityTime.setTime(durabilityTime.getStartingSeconds());
    }

    public void deplete() {
        durabilityTime.setTime(durabilityTime.getTotalSeconds() - (Clock.Delta() / 2));
    }
    public boolean hasDurability() {
        return percentage > 0;
    }
    public boolean isBeingUsed() {
        return !durabilityTime.isPaused();
    }
    public float getDurabilityPercentage() {
        return percentage;
    }
    public Tex getBackgroundBarTex() {
        return backgroundTex;
    }
    public Tex getBarTex() {
        return barTex;
    }

}
