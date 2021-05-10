package com.wizatar08.escapemaze.game.events;

import com.wizatar08.escapemaze.menus.Game;

public class Event {
    private Game gameController;

    public Event() {
        EventManager.addToEvents(this);
    }

    public void update() {}

    public boolean isOccuring() {
        return false;
    }

    public void onOccurance() {
    }

    public void setGameObject(Game game) {
        this.gameController = game;
    }

    public Game getGameController() {
        return gameController;
    }
}
