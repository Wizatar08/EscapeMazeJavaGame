package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.menus.Game;

public class Booster implements ItemClass {

    public Booster() {
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
        if (player.getInventory().hasPowerSource()) {
            item.setSpeedBoost(1.25f);
        } else {
            item.setSpeedBoost(0.0f);
        }
    }

    @Override
    public boolean canUseItem(Item item, Game game, Player player) {
        return false;
    }

    @Override
    public void use(Item item, Game game, Player player) {

    }

    @Override
    public void onDropItem(Item item, Game game, Player player) {

    }
}
