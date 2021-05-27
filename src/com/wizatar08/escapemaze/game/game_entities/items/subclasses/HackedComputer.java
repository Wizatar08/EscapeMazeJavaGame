package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.menus.Game;

public class HackedComputer extends Item {
    private Game gameController;

    public HackedComputer(Game game, ItemType type, JsonObject data, float x, float y) {
        super(game, type, data, x, y);
        gameController = game;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void use() {
        super.use();
        if (gameController.currentState() == Game.GameStates.ALARM && gameController.getCurrentPlayer().getInventory().hasItem(ItemType.ADMIN_ACCESSOR)) {
            gameController.setState(Game.GameStates.NORMAL);
            destroy();
        }
    }
}
