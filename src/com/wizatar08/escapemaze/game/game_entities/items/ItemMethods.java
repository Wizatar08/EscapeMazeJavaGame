package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.menus.Game;

public interface ItemMethods {
    /**
     * This method is used when an object needs to be constantly updated when on the ground.
     */
    void update();

    /**
     * This method is used when a player hits the item
     */
    void onHit(Game game, Player player);

    /**
     * This method is used to update an item when it is in a player's inventory
     */
    void updateInven(Game game, Player player);

    /**
     * This method is used if an item can be used. True: Uses item, False: Drops item
     */
    boolean canUseItem(Game game, Player player);

    /**
     * This method carries out an item's use ability
     */
    void use(Game game, Player player);
}
