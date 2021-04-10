package com.wizatar08.escapemaze.game.game_entities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import com.wizatar08.escapemaze.menus.Game;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class JSONItemClass {
    @SerializedName("items")
    private JsonArray items;

    private Gson gson;

    public JSONItemClass(JsonArray items) {
        this.items = items;
    }

    public ArrayList<Item> getItems(Game game) {
        gson = new Gson();
        ArrayList<Item> itemList = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            String id = items.get(i).getAsJsonObject().get("id").getAsString();
            int x = items.get(i).getAsJsonObject().get("x").getAsInt();
            int y = items.get(i).getAsJsonObject().get("y").getAsInt();

            Class clazz = ItemType.getType(id).getClassname();
            if (clazz != null) {
                try {
                    itemList.add((Item) clazz.getConstructor(Game.class, ItemType.class, Tex.class, float.class, float.class).newInstance(game, ItemType.getType(id), new Tex("game/items/" + ItemType.getType(id).getTexture()), x, y));
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                Tex t = new Tex("game/items/" + ItemType.getType(id).getTexture());
                itemList.add(new Item(game, ItemType.getType(id), t, x, y));
            }
        }
        return itemList;
    }
}
