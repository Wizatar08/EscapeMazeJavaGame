package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

public class MaterialDetectorComputer extends Tile {
    private Game gameController;
    private Tex detectTex;

    public MaterialDetectorComputer(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        detectTex = new Tex("tiles/selectors/tile_selector");
    }

    @Override
    public void useTile() {
        gameController.setMaterialDetectorsActive(false);
    }

    @Override
    public void update() {
        super.update();
        this.setActive(gameController.materialDetectorsActive());
    }

    @Override
    public void onTile() {
        if (gameController.materialDetectorsActive()) {
            detectTex.draw(getX(), getY());
        }
    }
}
