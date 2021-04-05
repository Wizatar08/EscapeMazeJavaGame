package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

public class MainComputer extends Tile {
    private Game gameController;

    public MainComputer(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
    }

    @Override
    public void useTile() {
        if (gameController.currentState() == Game.GameStates.ALARM) {
            gameController.setState(Game.GameStates.NORMAL);
        }
    }
}
