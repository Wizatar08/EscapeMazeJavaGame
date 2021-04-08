package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

import static com.wizatar08.escapemaze.helpers.Drawer.LoadPNG;
import static com.wizatar08.escapemaze.helpers.Drawer.drawQuadTex;

public class MaterialDetectorComputer extends Tile {
    private Game gameController;
    private Texture detectTex;

    public MaterialDetectorComputer(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        detectTex = LoadPNG("tiles/selectors/tile_selector");
    }

    @Override
    public void useTile() {
        gameController.setMaterialDetectorsActive(false);
    }

    @Override
    public void update() {
        this.setActive(gameController.materialDetectorsActive());
    }

    @Override
    public void onTile() {
        if (gameController.materialDetectorsActive()) {
            drawQuadTex(detectTex, getX(), getY());
        }
    }
}
