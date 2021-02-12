package com.wizatar08.escapemaze.game;

import com.wizatar08.escapemaze.enumerators.TileType;
import com.wizatar08.escapemaze.objects.Tile;
import static com.wizatar08.escapemaze.helpers.Drawer.*;

public class TileMap {
    public Tile[][] map;
    private int tilesWide, tilesHigh;

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
    public TileMap(int[][] newMap) {
        this.tilesWide = newMap[0].length;
        this.tilesHigh = newMap.length;
        map = new Tile[tilesWide][tilesHigh];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = new Tile(TILE_SIZE*i,j*TILE_SIZE,TILE_SIZE,TILE_SIZE, TileType.TILE_IDS.get(newMap[j][i]));
            }
        }
    }

    public void draw(){
        for (int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                map[i][j].draw();
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

    public int getTilesHigh() {
        return tilesHigh;
    }

    public int getTilesWide() {
        return tilesWide;
    }
}
