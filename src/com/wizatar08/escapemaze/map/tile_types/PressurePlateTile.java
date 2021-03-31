package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.helpers.Drawer;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

import java.util.ArrayList;

public class PressurePlateTile extends Tile {
    private Game gameController;

    public PressurePlateTile(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        this.gameController = game;
    }

    @Override
    public void update() {
        ArrayList<Player> player = gameController.getPlayer();
        player.forEach((p) -> {
            if (Drawer.checkCollision(super.getX(), super.getY(), super.getWidth(), super.getHeight(), p.getX(), p.getY(), p.getWidth(), p.getHeight())) {
                gameController.setState(Game.GameStates.ALARM);
            }
        });
    }
}
