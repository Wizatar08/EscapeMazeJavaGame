package com.wizatar08.escapemaze.map;

import java.util.ArrayList;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class TileMap {
    private Tile[][] map;
    public static int tilesWide, tilesHigh;
    private ArrayList<SafeSpot> safeSpots;

    public TileMap(){
        this.tilesWide = 16;
        this.tilesHigh = 12;
        map = new Tile[tilesWide][tilesHigh];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = new Tile(TILE_SIZE*i,j*TILE_SIZE,TILE_SIZE,TILE_SIZE, TileType.METAL_WALL);
            }
        }
    }

    public TileMap(int width, int height){
        this.tilesWide = width;
        this.tilesHigh = height;
        map = new Tile[tilesWide][tilesHigh];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = new Tile(TILE_SIZE*i,j*TILE_SIZE,TILE_SIZE,TILE_SIZE, TileType.METAL_WALL);
            }
        }
    }

    public void draw(){
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                tile.draw();
            }
        }
    }

    public void setTile(int xCoord, int yCoord, TileType type) {
        if (!(xCoord >= tilesWide || yCoord >= tilesHigh || xCoord < 0 || yCoord < 0))
            map[xCoord][yCoord] = new Tile(xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
    }
    public Tile getTile(int xPlace, int yPlace) {
        if (xPlace < tilesWide && yPlace < tilesHigh && xPlace > -1 && yPlace > -1) {
            return map[xPlace][yPlace];
        } else {
            return new Tile(0, 0, 0, 0, TileType.NULL);
        }
    }

    public void addSafeSpot(SafeSpot safeSpot) {
        if (safeSpots == null) {
            safeSpots = new ArrayList<>();
        }
        safeSpots.add(safeSpot);
    }

    public int getTilesHigh() {
        return tilesHigh;
    }

    public int getTilesWide() {
        return tilesWide;
    }

    public Tile[][] getMapAsArray() {
        return map;
    }

    public ArrayList<SafeSpot> getSafeSpots() {
        return safeSpots;
    }
}
