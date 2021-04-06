package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.menus.Game;
import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;

public class Booster extends Item {
    private Game gameController;
    private boolean running;

    public Booster(Game game, ItemType type, Texture texture, float x, float y) {
        super(game, type, texture, x, y);
        this.gameController = game;
        running = false;
    }

    @Override
    public void update() {
        super.update();
        if (running && gameController.getCurrentPlayer().getInventory().getClosestNonOccupiedPowerSource() != null) {
            ((DurabilityItem) gameController.getCurrentPlayer().getInventory().getClosestNonOccupiedPowerSource()).deplete();
            this.setSpeedBoost(1.4f);
        } else {
            this.setSpeedBoost(0.0f);
            running = false;
        }
    }

    @Override
    public void drop() {
        running = false;
    }

    @Override
    public void use() {
        super.use();
        running = !running;
    }

    @Override
    public boolean canUse() {
        return true;
    }
}
