package com.wizatar08.escapemaze.game.game_entities.items.events;

import com.wizatar08.escapemaze.menus.Game;

import java.util.ArrayList;

public class ItemEventManager {
    private static ArrayList<ItemEvent> itemEvents;

    public static void update(Game game) {
        if (itemEvents == null) {
            itemEvents = new ArrayList<>();
        }
        for (ItemEvent itemEvent : itemEvents) {
            if (itemEvent.getGameController() == null) {
                itemEvent.setGameObject(game);
            }
            itemEvent.update();
        }
    }

    public static void addToEvents(ItemEvent itemEvent) {
        if (itemEvents == null) {
            itemEvents = new ArrayList<>();
        }
        itemEvents.add(itemEvent);
    }

    public ArrayList<ItemEvent> getAllEvents() {
        return itemEvents;
    }
}
