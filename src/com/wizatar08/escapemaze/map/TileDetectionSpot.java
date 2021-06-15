package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.helpers.Hitbox;
import org.jetbrains.annotations.NotNull;

import static com.wizatar08.escapemaze.render.Renderer.TILE_SIZE;

public class TileDetectionSpot {
    // Initialize variables
    private Tile safeTile;
    private Tile detectTile;
    private boolean isEscape;
    private Hitbox detectionArea;

    public TileDetectionSpot(Tile detectTile, Tile goToTile, boolean isEscapeTile) {
        this.detectTile = detectTile;
        this.safeTile = goToTile;
        this.isEscape = isEscapeTile;
        this.detectionArea = new Hitbox(detectTile, 16, 16, TILE_SIZE - 16, TILE_SIZE - 16);
    }

    public static Tile detectAt(TileMap map, Tile tile, @NotNull Direction dir) {
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

    public Hitbox getDetectionArea() {
        return detectionArea;
    }
}
