package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

public class MainComputer extends Tile {
    private Game gameController;
    private Tex detectTex;

    public MainComputer(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        detectTex = new Tex("tiles/selectors/tile_selector");
    }

    @Override
    public void useTile() {
        if (gameController.currentState() == Game.GameStates.ALARM) {
            gameController.setState(Game.GameStates.NORMAL);
        }
    }

    @Override
    public void onTile() {
        if (gameController.currentState() == Game.GameStates.ALARM) {
            detectTex.draw(getX(), getY());
        }
    }
}
