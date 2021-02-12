package com.wizatar08.escapemaze.helpers;

import com.wizatar08.escapemaze.enumerators.TileType;
import com.wizatar08.escapemaze.game.TileMap;
import com.wizatar08.escapemaze.objects.Tile;
import org.lwjgl.util.glu.Project;

import java.io.*;

public class ExternalMapHandler {

    /**
     * DEV ONLY. Saves a map.
     * @param mapName
     * @param grid
     */
    public static void SaveMap(String mapName, TileMap grid) {
        String mapData = ""; // Craete a string variable
        for (int i = 0; i < grid.getTilesWide(); i++) {
            for (int j = 0; j < grid.getTilesHigh(); j++) {
                mapData += getTileID(grid.getTile(i, j));
            }
        }
        try {
            File file = new File(mapName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(mapData);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TileMap LoadMap(String mapName) {
        TileMap grid = new TileMap();
        try {
            InputStream is = Project.class.getClassLoader().getResourceAsStream("resources/maps/" + mapName);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String data = br.readLine();
            for (int i = 0; i < grid.getTilesWide(); i++) {
                for (int j = 0; j < grid.getTilesHigh(); j++) {
                    grid.setTile(i, j, getTileType(data.substring((i * grid.getTilesHigh() + j) * 6, (i * grid.getTilesHigh() + j + 1) * 6)));
                }
            }
            br.close();
            is.close();
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
