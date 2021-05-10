package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.game.game_entities.items.subclasses.HoveringDevice;
import com.wizatar08.escapemaze.helpers.visuals.Drawer;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

import java.util.ArrayList;

public class PressurePlate extends Tile {
    private final Game gameController;

    public PressurePlate(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        this.gameController = game;
    }

    @Override
    public void update() {
        if (gameController.pressurePlatesActive()) {
            ArrayList<Player> player = gameController.getPlayerInstances();
            player.forEach((p) -> {
                if (Drawer.checkCollision(super.getX(), super.getY(), super.getWidth(), super.getHeight(), p.getX(), p.getY(), p.getWidth(), p.getHeight()) && !gameController.getCurrentPlayer().getInventory().hasActiveItem(HoveringDevice.class)) {
                    gameController.setState(Game.GameStates.ALARM);
                }
            });
        }
    }
}
