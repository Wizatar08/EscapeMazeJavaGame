package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

public class ItemUnlocksDoorTile extends Tile {
    private Game gameController;
    private ItemType unlockableBy;

    public ItemUnlocksDoorTile(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        System.out.println(type + ", " + this.getId());
        this.unlockableBy = (ItemType) type.subClassArgs()[0];
        this.gameController = game;
    }

    public ItemType unlockableBy() {
        return unlockableBy;
    }
}
