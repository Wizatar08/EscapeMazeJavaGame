package com.wizatar08.escapemaze.game.game_entities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.game.game_entities.items.ItemTypes;
import com.wizatar08.escapemaze.helpers.Drawer;
import com.wizatar08.escapemaze.menus.Game;

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
            System.out.println(id + ", " + x + ", " + y);
            itemList.add(new Item(ItemType.getType(id),
                    Drawer.LoadPNG("game/items/" + ItemType.getType(id).getTexture()),
                    x, y));
        }
        return itemList;
    }
}
