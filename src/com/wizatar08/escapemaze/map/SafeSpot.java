package com.wizatar08.escapemaze.map;

import org.jetbrains.annotations.NotNull;

public class SafeSpot {
    // Initialize variables
    private Tile safeTile;
    private Tile detectTile;


    public SafeSpot(Tile detectTile, Tile goToTile) {
        this.detectTile = detectTile;
        this.safeTile = goToTile;
    }

    public static Tile detectAt(TileMap map, Tile tile, @NotNull SafeSpots dir) {
        switch (dir) {
            default:
                return tile;
            case DOWN:
                return map.getTile(tile.getXPlace(), tile.getYPlace() + 1);
            case UP:
                return map.getTile(tile.getXPlace(), tile.getYPlace() - 1);
            case LEFT:
                return map.getTile(tile.getXPlace() - 1, tile.getYPlace());
            case RIGHT:
                return map.getTile(tile.getXPlace() + 1, tile.getYPlace());
        }
    }

    public Tile getDetectTile() {
        return detectTile;
    }

    public Tile getSafeTile() {
        return safeTile;
    }
}
