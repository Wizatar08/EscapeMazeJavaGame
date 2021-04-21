package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.helpers.visuals.Tex;
import com.wizatar08.escapemaze.map.Direction;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

public class AuthorityDoor extends Tile {
    private Game gameController;
    private Tex detectTex;

    public AuthorityDoor(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        this.detectTex = new Tex("tiles/selectors/item_use_selector");
    }

    @Override
    public void playerNearTile(Direction direction) {
        if (gameController.getCurrentPlayer().getInventory().getItems().get(gameController.getCurrentPlayer().getInventory().getCurrentSelected()) != null) {
            if (isActive()) {
                detectTex.draw(getX() + Game.DIS_X, getY() + Game.DIS_Y);
            }
        }
    }
}
