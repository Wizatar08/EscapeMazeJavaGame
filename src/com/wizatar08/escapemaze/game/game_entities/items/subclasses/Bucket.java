package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.events.Event;
import com.wizatar08.escapemaze.game.events.ItemTouchEvent;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.menus.Game;
import com.wizatar08.escapemaze.visuals.Tex;
import org.newdawn.slick.Color;

import java.util.ArrayList;

public class Bucket extends Item {
    private Contents bucketContents;
    private Color contentColorTint;
    private int color;
    private JsonObject data;

    public Bucket(Game game, ItemType type, JsonObject data, float x, float y) {
        super(game, type, data, x, y);
        this.data = data;
        changeContents(Contents.getContentsFromString(data.get("contents").getAsString()));
        try {
            color = Integer.parseInt(data.get("content_color").getAsString(), 16);
            changeColorTint(color);
        } catch (NullPointerException e) {
            color = 0xFFFFFF;
            changeColorTint(color);
        }
        createItemEvents(
                new ItemTouchEvent(this, "paint", new Condition(), 5, ItemType.PARTS)
        );
    }

    private void changeContents(Contents contents) {
        bucketContents = contents;
    }

    public void changeColorTint(int hexCode) {
        contentColorTint = new Color(hexCode);
        color = hexCode;
    }

    public void changeContents(Contents contents, int colorTint) {
        bucketContents = contents;
        contentColorTint = new Color(colorTint);
        color = colorTint;
    }

    public void paint(ArrayList<Item> items) {
        for (Item item : items) {
            if (item.getType() == ItemType.PARTS) {
                ((RobotPartsItem) item).setColor(color);
            }
        }
        this.changeContents(Contents.EMPTY);
    }

    @Override
    public void draw(float xVal, float yVal) {
        super.draw(xVal, yVal);
        if (bucketContents.canBeTinted) {
            bucketContents.getContentTex().draw(xVal, yVal, contentColorTint);
        } else if (bucketContents.getContentTex() != null)  {
            bucketContents.getContentTex().draw(xVal, yVal);
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (bucketContents.canBeTinted) {
            bucketContents.getContentTex().draw(getX() + Game.DIS_X, getY() + Game.DIS_Y, contentColorTint);
        } else if (bucketContents.getContentTex() != null) {
            bucketContents.getContentTex().draw(getX() + Game.DIS_X, getY() + Game.DIS_Y);
        }
    }

    public Color getContentColorTint() {
        return contentColorTint;
    }

    public Contents getBucketContents() {
        return bucketContents;
    }

    private enum Contents {
        EMPTY(null, false),
        WATER(new Tex("game/items/bucket_contents/water"), false),
        PAINT(new Tex("game/items/bucket_contents/paint"), true);

        private Tex tex;
        private boolean canBeTinted;
        Contents(Tex bucketTex, boolean canBeTinted) {
            this.tex = bucketTex;
            this.canBeTinted = canBeTinted;
        }

        public Tex getContentTex() {
            return tex;
        }

        public static Contents getContentsFromString(String s) {
            switch (s) {
                case "water": return WATER;
                case "paint": return PAINT;
                default: return EMPTY;
            }
        }
    }
}
