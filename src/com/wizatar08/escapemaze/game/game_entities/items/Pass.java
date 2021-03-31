package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

import java.util.ArrayList;

public class Pass implements ItemClass {
    private Tile tile;

    public Pass() {
        tile = null;
    }

    @Override
    public void update(Item item, Game game, Player player) {
    }

    @Override
    public boolean canPickUp(Item item, Game game, Player player) {
        return true;
    }

    @Override
    public void onHit(Item item, Game game, Player player) {
    }

    @Override
    public void updateInven(Item item, Game game, Player player) {

    }

    @Override
    public boolean canUseItem(Item item, Game game, Player player) {
        for (Tile tile : player.getAllSurroundingTiles()) {
            if (TileType.TILE_IDS.get(tile.getId()).isAuthorityDoor()) {
                int[] passLevel = new int[]{0, 0, 0, 0, 0};
                for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                    Item item1 = player.getInventory().getItems().get(i);
                    if (item1 != null && item1.isPass()) {
                        passLevel[i] = item1.getPassLevel();
                    }
                }
                int matches = 0;
                boolean hasChecked;
                for (int k : passLevel) {
                    hasChecked = false;
                    for (int i = 0; i < tile.getRequiredPassLevels().length; i++) {
                        if (tile.getRequiredPassLevels()[i] == k && !hasChecked) {
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
    public void use(Item item, Game game, Player player) {
        tile.setActive(false);
    }

    @Override
    public void onDropItem(Item item, Game game, Player player) {

    }
}
