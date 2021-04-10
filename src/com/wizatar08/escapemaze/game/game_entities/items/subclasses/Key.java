package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.tile_types.ItemUnlocksDoor;
import com.wizatar08.escapemaze.menus.Game;

public class Key extends Item {
    private Game gameController;
    private Tile tile;

    public Key(Game game, ItemType type, Tex texture, JsonObject data, float x, float y) {
        super(game, type, texture, data, x, y);
        gameController = game;
    }

    @Override
    public boolean canUse() {
        for (Tile tile : gameController.getCurrentPlayer().getAllSurroundingTiles()) {
            if (tile instanceof ItemUnlocksDoor) {
                if (ItemType.getType(super.getId()) == ((ItemUnlocksDoor) tile).unlockableBy() && tile.isActive()) {
                    this.tile = tile;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void use() {
        tile.setActive(false);
    }
}
