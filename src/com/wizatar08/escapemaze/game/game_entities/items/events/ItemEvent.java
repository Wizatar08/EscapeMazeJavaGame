package com.wizatar08.escapemaze.game.game_entities.items.events;

import com.wizatar08.escapemaze.menus.Game;

public class ItemEvent {
    private Game gameController;

    public ItemEvent() {
        ItemEventManager.addToEvents(this);
    }

    public void update() {}

    public boolean isOccuring() {
        return false;
    }

    public void setGameObject(Game game) {
        this.gameController = game;
    }

    public Game getGameController() {
        return gameController;
    }
}
