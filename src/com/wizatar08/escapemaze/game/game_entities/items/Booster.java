package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

public class Booster extends Item {
    private Game gameController;

    public Booster(Game game, ItemType type, Texture texture, float x, float y) {
        super(game, type, texture, x, y);
        this.gameController = game;
    }

    @Override
    public void update() {
        super.update();
        gameController.getPlayer().forEach((p) -> {
            if (p.getInventory().hasPowerSource()) {
                this.setSpeedBoost(1.25f);
            } else {
                this.setSpeedBoost(0.0f);
            }
        });
    }
}
