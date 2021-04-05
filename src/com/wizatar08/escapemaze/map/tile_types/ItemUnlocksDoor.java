package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

import static com.wizatar08.escapemaze.helpers.Drawer.LoadPNG;
import static com.wizatar08.escapemaze.helpers.Drawer.drawQuadTex;

public class ItemUnlocksDoor extends Tile {
    private Game gameController;
    private ItemType unlockableBy;
    private Texture detectTex;

    public ItemUnlocksDoor(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        System.out.println(type + ", " + this.getId());
        this.unlockableBy = (ItemType) type.subClassArgs()[0];
        this.gameController = game;
        this.detectTex = LoadPNG("tiles/selectors/safe_space_selector");
    }

    public ItemType unlockableBy() {
        return unlockableBy;
    }

    @Override
    public void playerNearTile() {
        if (gameController.getCurrentPlayer().getInventory().getItems().get(gameController.getCurrentPlayer().getInventory().getCurrentSelected()) != null) {
            if (isActive() && ItemType.getType(gameController.getCurrentPlayer().getInventory().getItems().get(gameController.getCurrentPlayer().getInventory().getCurrentSelected()).getId()) == this.unlockableBy()) {
                drawQuadTex(detectTex, getX() + Game.DIS_X, getY() + Game.DIS_Y);
            }
        }
    }

    @Override
    public void useTile() {

    }
}
