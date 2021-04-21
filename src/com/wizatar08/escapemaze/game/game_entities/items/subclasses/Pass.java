package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.visuals.Tex;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

public class Pass extends Item {
    private Game gameController;
    private Tile tile;

    public Pass(Game game, ItemType type, Tex texture, JsonObject data, float x, float y) {
        super(game, type, texture, data, x, y);
        gameController = game;
        tile = null;
    }

    @Override
    public boolean canUse() {
        for (Tile tile : gameController.getCurrentPlayer().getAllSurroundingTiles()) {
            if (TileType.TILE_IDS.get(tile.getId()).isAuthorityDoor()) {
                int[] passLevel = new int[]{0, 0, 0, 0, 0};
                for (int i = 0; i < gameController.getCurrentPlayer().getInventory().getItems().size(); i++) {
                    Item item1 = gameController.getCurrentPlayer().getInventory().getItems().get(i);
                    if (item1 != null && item1.isPass()) {
                        passLevel[i] = item1.getPassLevel();
                    }
                }
                int matches = 0;
                boolean hasChecked;
                for (int k : passLevel) {
                    hasChecked = false;
                    for (int i = 0; i < tile.getRequiredPassLevels().length; i++) {
                        if ((tile.getRequiredPassLevels()[i] == k || k == -1) && !hasChecked) {
                            hasChecked = true;
                            matches++;
                        }
                    }
                }
                if (matches >= tile.getRequiredPassLevels().length) {
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
