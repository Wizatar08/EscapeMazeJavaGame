package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import com.wizatar08.escapemaze.menus.Game;

public class HoveringDevice extends Item {
    private final Game gameController;
    private boolean running, canRun;

    public HoveringDevice(Game game, ItemType type, Tex texture, JsonObject data, float x, float y) {
        super(game, type, texture, data, x, y);
        gameController = game;
        running = false;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void use() {
        super.use();
        running = !running;
    }

    @Override
    public void update() {
        super.update();
        if (running && gameController.getCurrentPlayer().getInventory().getClosestNonOccupiedGasSource() != null) {
            ((DurabilityItem) gameController.getCurrentPlayer().getInventory().getClosestNonOccupiedGasSource()).deplete();
            this.setSpeedBoost(0.7f);
            this.displayOnPlayer(true);
            canRun = true;
        } else {
            this.setSpeedBoost(0.0f);
            this.displayOnPlayer(false);
            canRun = false;
            running = false;
        }
    }

    @Override
    public void drop() {
        running = false;
        canRun = false;
    }

    @Override
    public boolean isRunning() {
        return canRun;
    }
}
