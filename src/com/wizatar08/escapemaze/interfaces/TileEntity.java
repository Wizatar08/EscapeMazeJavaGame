package com.wizatar08.escapemaze.interfaces;

import org.newdawn.slick.opengl.Texture;

public interface TileEntity {

    /**
     * Get if player can pass through this tile.
     */
    boolean canPass();

    /**
     * Get if this tile is harmful.
     */
    boolean isHarmful();

    /**
     * Get if this tile is a hiding tile
     */
    boolean canHide();

    /**
     * Get x location of tile on map array
     */
    int getXPlace();

    /**
     * Get y location of tile on map array
     */
    int getYPlace();

    /**
     * Get the texture of the tile
     */
    Texture getTexture();
}
