package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.menus.Game;
import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;

import static com.wizatar08.escapemaze.helpers.Drawer.*;

public class DurabilityItem extends Item {
    private final Timer durabilityTime;
    private Texture backgroundTex, barTex;
    private float percentage;
    private final float onePercent;

    public DurabilityItem(Game game, ItemType type, Texture texture, float x, float y) {
        super(game, type, texture, x, y);
        durabilityTime = new Timer(Timer.TimerModes.COUNT_DOWN, (int) type.getClassArgs()[0]);
        backgroundTex = LoadPNG("game/durability/background");
        barTex = LoadPNG("game/durability/bar");
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
            drawQuadTex(backgroundTex, getX() + 2, getY(), backgroundTex.getImageWidth(), backgroundTex.getImageHeight());
            drawQuadTex(barTex, getX() + 2, getY(), barTex.getImageWidth(), barTex.getImageHeight());
        }
        super.draw();
    }

    public void deplete(float percent) {
        durabilityTime.setTime(durabilityTime.getTotalSeconds() - (durabilityTime.getStartingSeconds() * percent));
    }

    @Override
    public void draw(float xVal, float yVal) {
        super.draw(xVal, yVal);
        drawQuadTex(backgroundTex, xVal + 2, yVal, backgroundTex.getImageWidth(), backgroundTex.getImageHeight());
        drawQuadTex(barTex, xVal + 2, yVal + 56, barTex.getImageWidth() * percentage, barTex.getImageHeight());
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

}
