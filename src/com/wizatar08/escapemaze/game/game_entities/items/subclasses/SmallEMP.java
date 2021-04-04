package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

public class SmallEMP extends Item {
    private Game gameController;

    public SmallEMP(Game game, ItemType type, Texture texture, float x, float y) {
        super(game, type, texture, x, y);
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
