package com.wizatar08.escapemaze.game.events;

import com.wizatar08.escapemaze.menus.Game;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class EventManager {
    private static ArrayList<Event> events;

    public static void update(Game game) {
        if (events == null) {
            events = new ArrayList<>();
        }
        for (Event event : events) {
            if (event.getGameController() == null) {
                event.setGameObject(game);
            }
            event.update();
        }
    }

    public static void addToEvents(Event event) {
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
    }

    public ArrayList<Event> getAllEvents() {
        return events;
    }
}
