package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.menus.Game;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class TileMap {
    private Tile[][] map;
    public static int tilesWide, tilesHigh;
    private ArrayList<TileDetectionSpot> tileDetectionSpots;
    private Game game;

    public TileMap(Game game){
        this.game = game;
        this.tilesWide = 16;
        this.tilesHigh = 12;
        map = new Tile[tilesWide][tilesHigh];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = new Tile(game, TILE_SIZE*i,j*TILE_SIZE,TILE_SIZE,TILE_SIZE, TileType.METAL_WALL);
            }
        }
    }

    public TileMap(Game game, int width, int height){
        this.game = game;
        this.tilesWide = width;
        this.tilesHigh = height;
        map = new Tile[tilesWide][tilesHigh];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = new Tile(game, TILE_SIZE*i,j*TILE_SIZE,TILE_SIZE,TILE_SIZE, TileType.METAL_WALL);
            }
        }
    }

    public void draw(){
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                tile.draw();
            }
        }
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                tile.drawTileDecorations();
            }
        }
    }

    public void setTile(int xCoord, int yCoord, TileType type) {
        if (!(xCoord >= tilesWide || yCoord >= tilesHigh || xCoord < 0 || yCoord < 0)) {
            if (type.getSubClass() != null) {
                Class clazz = type.getSubClass();
                try {
                    map[xCoord][yCoord] = (Tile) clazz.getConstructor(Game.class, float.class, float.class, int.class, int.class, TileType.class).newInstance(game, xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                map[xCoord][yCoord] = new Tile(game, xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
            }
        }
    }
    public Tile getTile(int xPlace, int yPlace) {
        if (xPlace < tilesWide && yPlace < tilesHigh && xPlace > -1 && yPlace > -1) {
            return map[xPlace][yPlace];
        } else {
            return new Tile(game, 0, 0, 0, 0, TileType.NULL);
        }
    }

    public void addSafeSpot(TileDetectionSpot tileDetectionSpot) {
        if (tileDetectionSpots == null) {
            tileDetectionSpots = new ArrayList<>();
        }
        tileDetectionSpots.add(tileDetectionSpot);
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

    public ArrayList<TileDetectionSpot> getSafeSpots() {
        return tileDetectionSpots;
    }
}
