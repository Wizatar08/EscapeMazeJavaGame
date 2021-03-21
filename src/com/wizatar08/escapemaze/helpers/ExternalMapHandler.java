package com.wizatar08.escapemaze.helpers;

import com.wizatar08.escapemaze.menus.Menus;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.map.TileDetectionSpot;
import com.wizatar08.escapemaze.map.EntityDetectDirection;
import com.wizatar08.escapemaze.map.TileMap;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.menus.MenuRun;
import org.lwjgl.util.glu.Project;

import java.io.*;
import static com.wizatar08.escapemaze.map.WRTEMMHandler.*;

public class ExternalMapHandler {

    /**
     * DEV ONLY. Saves a map.
     * @param mapName
     * @param grid
     */
    public static void SaveMap(String mapName, TileMap grid) {
        String[] mapData = new String[100]; // Craete a string variable
        for (int i = 0; i < grid.getTilesWide(); i++) {
            for (int j = 0; j < grid.getTilesHigh(); j++) {
                mapData[i] += getTileID(grid.getTile(i, j));
            }
        }
        try {
            File file = new File(mapName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < mapData.length; i++) {
                if (mapData[i] != null) {
                    mapData[i] = mapData[i].replace("null", "");
                    bw.write(mapData[i]);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TileMap LoadMap(String mapName) {
        String map = "";
        String[] mapAsArray = new String[]{};
        try {
            mapAsArray = getMapAsArray(Project.class.getClassLoader().getResourceAsStream("resources/maps/" + mapName));
            map = getMapAsString(mapAsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TileMap grid = new TileMap(mapAsArray.length, (int) Math.floor(mapAsArray[0].length() / 6));
        try {
            for (int i = 0; i < grid.getTilesWide(); i++) {
                for (int j = 0; j < grid.getTilesHigh(); j++) {
                    grid.setTile(i, j, getTileType(map.substring((i * grid.getTilesHigh() + j) * 6, (i * grid.getTilesHigh() + j + 1) * 6)));

                    if (MenuRun.MENU == Menus.GAME) {
                        if (grid.getTile(i, j).getType().getSafeSpot() != EntityDetectDirection.NONE) {
                            grid.addSafeSpot(new TileDetectionSpot(TileDetectionSpot.detectAt(grid, grid.getTile(i, j), grid.getTile(i, j).getType().getSafeSpot()), grid.getTile(i, j), grid.getTile(i, j).isEscapeDoor()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grid;
    }

    public static TileType getTileType(String ID) {
        if (TileType.TILE_IDS.get(ID) == null) {
            return TileType.NULL;
        }
        return TileType.TILE_IDS.get(ID);
    }

    public static String getTileID(Tile t) {
        String ID = "E";
        ID = t.getType().getId();
        return ID;
    }

}
