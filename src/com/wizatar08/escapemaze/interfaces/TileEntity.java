package com.wizatar08.escapemaze.interfaces;

import com.wizatar08.escapemaze.helpers.visuals.Tex;

public interface TileEntity {

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
    Tex getTexture();
}
