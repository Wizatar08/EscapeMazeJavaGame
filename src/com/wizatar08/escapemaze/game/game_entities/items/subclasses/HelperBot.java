package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.events.Event;
import com.wizatar08.escapemaze.game.events.ItemTouchAndRemoveEvent;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.game.game_entities.items.subclasses.durability.RechargableBattery;
import com.wizatar08.escapemaze.visuals.Drawer;
import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.menus.Game;

import java.util.ArrayList;

public class HelperBot extends RechargableBattery {
    private Game gameController;
    private Tex unpoweredTex, poweredTex, activeTex;
    private boolean isActive;
    public boolean isPowered;
    private ArrayList<Item> itemsTouching;
    private Event[] events;

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
        createCondition();
        events = new Event[]{
                new ItemTouchAndRemoveEvent(this, "createPlayer", condition, 10, ItemType.MINI_GENERATOR, ItemType.INSTRUCTIONS, ItemType.PARTS)
        };
    }

    @Override
    public void createCondition() {
        super.condition = new Condition(this, "isPowered");
    }

    @Override
    public void update() {
        super.update();
        super.condition.passes();
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

    public void createPlayer(ArrayList<Item> items) {
        int color = 0;
        for (Item item : items) {
            if (item.getType() == ItemType.PARTS) {
                color = ((RobotPartsItem) item).getColorIntValue();
            }
        }
        setDurability(0);
        gameController.addNewPlayer(color, getX(), getY());
    }

    public void test(ArrayList<Item> items) {
        System.out.println("TEST");
    }
}

/*
0 - Helper Bot
1 - Battery
2 - Instructions
3 - Parts
1st column - alone
2nd column - through dispenser
3rd column - with player creation alone
4th column - player creation through dispenser

0123 - 0, 0, 0, 0
0132 - 0,  , 0
0213 - 0
0231 - 0
0312 - 0
0321 - 0
3210 - 0, 0, 0
3120 - 0
2130 - 0
2310 - 0
1320 - 0
1230 - 0
1203 - 0, 0
1302 - 0
2103 -
2301
3102
3201
1023 - 0, 0
 */
