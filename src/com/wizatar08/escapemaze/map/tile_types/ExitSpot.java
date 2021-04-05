package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

public class ExitSpot extends Tile {
    public ExitSpot(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
    }
}
