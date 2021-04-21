package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.helpers.visuals.Tex;
import com.wizatar08.escapemaze.map.Direction;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;
import com.wizatar08.escapemaze.render.Renderer;

public class Dispenser extends Tile {
    private Game gameController;
    private Direction direction;
    private Tex itemUseTex;
    private boolean canUse;

    public Dispenser(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        direction = (Direction) type.subClassArgs()[0];
        itemUseTex = new Tex("tiles/selectors/tile_selector");
        canUse = false;
    }

    @Override
    public void playerNearTile(Direction direction) {
        if (this.direction == direction && gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() != null) {
            itemUseTex.draw(getX(), getY());
            canUse = true;
        } else {
            canUse = false;
        }
    }

    @Override
    public void useTilePlayerNear() {
        if (canUse) {
            Item item = gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem();
            gameController.getCurrentPlayer().getInventory().remove(gameController.getCurrentPlayer().getInventory().getItemIndex(gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem()));
            item.setX(this.getX() + (Renderer.TILE_SIZE * direction.getxDir()));
            item.setY(this.getY() + (Renderer.TILE_SIZE * direction.getyDir()));
            item.setIsInInventory(false);
            item.drop();
            item.displayOnPlayer(false);
            gameController.addItemToGame(item);
        }
    }
}
