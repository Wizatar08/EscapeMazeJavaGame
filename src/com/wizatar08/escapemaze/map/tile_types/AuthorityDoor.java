package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

import static com.wizatar08.escapemaze.helpers.Drawer.LoadPNG;
import static com.wizatar08.escapemaze.helpers.Drawer.drawQuadTex;

public class AuthorityDoor extends Tile {
    private Game gameController;
    private Texture detectTex;

    public AuthorityDoor(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        this.detectTex = LoadPNG("tiles/selectors/item_use_selector");
    }

    @Override
    public void playerNearTile() {
        if (gameController.getCurrentPlayer().getInventory().getItems().get(gameController.getCurrentPlayer().getInventory().getCurrentSelected()) != null) {
            if (isActive()) {
                drawQuadTex(detectTex, getX() + Game.DIS_X, getY() + Game.DIS_Y);
            }
        }
    }
}
