package com.wizatar08.escapemaze.helpers;

import com.wizatar08.escapemaze.enumerators.TileType;
import com.wizatar08.escapemaze.game.TileMap;
import com.wizatar08.escapemaze.game.WRTEMMHandler;
import com.wizatar08.escapemaze.objects.Tile;
import org.lwjgl.util.glu.Project;

import java.io.*;
import static com.wizatar08.escapemaze.game.WRTEMMHandler.*;

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
