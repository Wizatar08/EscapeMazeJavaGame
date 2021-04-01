package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

public class LaserDeactivator extends Item{
    private Game gameController;
    private Tile tile;

    public LaserDeactivator(Game game, ItemType type, Texture texture, float x, float y) {
        super(game, type, texture, x, y);
        gameController = game;
        this.tile = null;
    }

    @Override
    public boolean canUse() {
        for (Tile tile : gameController.getCurrentPlayer().getAllSurroundingTiles()) {
            if (tile.getFunction() == Tile.Function.LASER_SECURE) {
                this.tile = tile;
                return true;
            }
        }
        return false;
    }

    @Override
    public void use() {
        tile.setActive(false);
    }
}
