package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

import static com.wizatar08.escapemaze.helpers.Drawer.LoadPNG;
import static com.wizatar08.escapemaze.helpers.Drawer.drawQuadTex;

public class PressurePlateComputer extends Tile {
    private Game gameController;
    private Texture detectTex;

    public PressurePlateComputer(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        detectTex = LoadPNG("tiles/selectors/tile_selector");
    }

    @Override
    public void useTile() {
        gameController.setPressurePlateActive(false);
    }

    @Override
    public void update() {
        this.setActive(gameController.pressurePlatesActive());
    }

    @Override
    public void onTile() {
        if (gameController.pressurePlatesActive()) {
            drawQuadTex(detectTex, getX(), getY());
        }
    }
}
