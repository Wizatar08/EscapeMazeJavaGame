package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;
import com.wizatar08.escapemaze.visuals.AnimatedTex;

public class SlidingGlassAutomaticDoor extends Tile {
    private boolean doorOpened, openDoor;

    public SlidingGlassAutomaticDoor(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        openDoor = false;
        doorOpened = false;
    }

    @Override
    public void update() {
        super.update();

        openDoor = playerWithinRange() || enemyWithinRange();

        Tile[] tiles = new Tile[]{getAboveTile(), getBelowTile(), getLeftTile(), getRightTile()};

        if (openDoor && !doorOpened) {
            for (int i = 0; i < getDefaultOverlapTexture().length; i++) {
                if (getDefaultOverlapTexture()[i] instanceof AnimatedTex) {
                    ((AnimatedTex) getDefaultOverlapTexture()[i]).setAnimationSettings(AnimatedTex.AnimationSettings.PLAY_ONCE);
                }
            }
            for (Tile tile : tiles) {
                if (tile instanceof SlidingGlassAutomaticDoor) {
                    ((SlidingGlassAutomaticDoor) tile).callToOpenDoor();
                }
            }
            doorOpened = true;
        } else if (!openDoor && doorOpened){
            for (int i = 0; i < getDefaultOverlapTexture().length; i++) {
                if (getDefaultOverlapTexture()[i] instanceof AnimatedTex) {
                    ((AnimatedTex) getDefaultOverlapTexture()[i]).setAnimationSettings(AnimatedTex.AnimationSettings.PLAY_BACKWARDS_ONCE);
                }
            }
            for (Tile tile : tiles) {
                if (tile instanceof SlidingGlassAutomaticDoor) {
                    ((SlidingGlassAutomaticDoor) tile).callToCloseDoor();
                }
            }
            doorOpened = false;
        }
    }

    public void callToOpenDoor() {
        for (int i = 0; i < getDefaultOverlapTexture().length; i++) {
            if (getDefaultOverlapTexture()[i] instanceof AnimatedTex) {
                ((AnimatedTex) getDefaultOverlapTexture()[i]).setAnimationSettings(AnimatedTex.AnimationSettings.PLAY_ONCE);
            }
        }
    }
    public void callToCloseDoor() {
        for (int i = 0; i < getDefaultOverlapTexture().length; i++) {
            if (getDefaultOverlapTexture()[i] instanceof AnimatedTex) {
                ((AnimatedTex) getDefaultOverlapTexture()[i]).setAnimationSettings(AnimatedTex.AnimationSettings.PLAY_BACKWARDS_ONCE);
            }
        }
    }
}
