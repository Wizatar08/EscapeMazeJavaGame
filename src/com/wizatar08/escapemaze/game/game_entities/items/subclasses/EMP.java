package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.menus.Game;

public class EMP extends Item {
    private Game gameController;
    private Timer countdown;

    public EMP(Game game, ItemType type, JsonObject data, float x, float y) {
        super(game, type, data, x, y);
        gameController = game;
        countdown = new Timer(Timer.TimerModes.COUNT_DOWN, 40);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void use() {
        super.use();
        if (/*gameController.getEnemies().size() != 0 && */gameController.getCurrentPlayer().getInventory().getClosestNonOccupiedPowerSource(40f) != null) {
            ((DurabilityItem) gameController.getCurrentPlayer().getInventory().getClosestNonOccupiedPowerSource(40f)).deplete(0.4f);
            gameController.getEnemies().forEach((e) -> {
                e.freeze(20);
                countdown.unpause();
            });
            countdown.setTime(40);
            destroy();
        }
    }

    @Override
    public void update() {
        super.update();
        countdown.update();
        if (countdown.getTotalSeconds() <= 0) {
            gameController.setState(Game.GameStates.ALARM);
        }
    }
}
