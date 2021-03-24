package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.menus.Game;

public class LaserDeactivator implements ItemClass{
    private Tile tile;

    public LaserDeactivator() {
        this.tile = null;
    }

    @Override
    public void update(Item item, Game game, Player player) {

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
            if (ItemType.getType(item.getId()) == tile.unlockableBy() && !tile.canPass()) {
                this.tile = tile;
                use(item, game, player);
                return true;
            }
        }
        return false;
    }

    @Override
    public void use(Item item, Game game, Player player) {
        tile.setSecurity(false);
    }
}
