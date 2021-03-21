package com.wizatar08.escapemaze.map;

import org.jetbrains.annotations.NotNull;

public class TileDetectionSpot {
    // Initialize variables
    private Tile safeTile;
    private Tile detectTile;
    private boolean isEscape;

    public TileDetectionSpot(Tile detectTile, Tile goToTile, boolean isEscapeTile) {
        this.detectTile = detectTile;
        this.safeTile = goToTile;
        this.isEscape = isEscapeTile;
    }

    public static Tile detectAt(TileMap map, Tile tile, @NotNull EntityDetectDirection dir) {
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

    public boolean isEscapeDoor() {
        return isEscape;
    }
}
