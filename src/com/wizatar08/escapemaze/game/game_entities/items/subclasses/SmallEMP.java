package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.visuals.Tex;
import com.wizatar08.escapemaze.menus.Game;

public class SmallEMP extends Item {
    private Game gameController;

    public SmallEMP(Game game, ItemType type, Tex texture, JsonObject data, float x, float y) {
        super(game, type, texture, data, x, y);
        gameController = game;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void use() {
        super.use();
        if (gameController.getCurrentPlayer().getClosestEnemy() != null) {
            gameController.getCurrentPlayer().getClosestEnemy().freeze(20);
            destroy();
        }
    }
}
