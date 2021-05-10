package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.visuals.Tex;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.Color;

public class RobotPartsItem extends Item {
    private Color color;
    private int colorInt;

    public RobotPartsItem(Game game, ItemType type, Tex texture, JsonObject data, float x, float y) {
        super(game, type, texture, data, x, y);
        colorInt = Integer.parseInt(data.get("color").getAsString(), 16);
        color = new Color(Integer.parseInt(data.get("color").getAsString(), 16));
    }

    @Override
    public void draw() {
        super.getTexture().draw(getX() + Game.DIS_X, getY() + Game.DIS_Y, color);
    }

    @Override
    public void draw(float xVal, float yVal) {
        getTexture().draw(xVal, yVal, color);
    }

    public Color getColor() {
        return color;
    }

    public int getColorIntValue() {
        return  colorInt;
    }
}