package com.wizatar08.escapemaze.game;

import org.lwjgl.util.glu.Project;

import java.io.*;
import java.util.function.Consumer;

public class WRTEMMHandler {
    public static int MAX_MAP_HEIGHT = 1024;

    public static String[] getMapAsArray(InputStream mapFile) throws IOException {
        InputStreamReader isr = new InputStreamReader(mapFile);
        BufferedReader br = new BufferedReader(isr);
        String[] mapArray = new String[MAX_MAP_HEIGHT];
        String[] mapArrayFinal;
        int line = 0;
        while((mapArray[line] = br.readLine()) != null) {
            line++;
        }
        mapArrayFinal = new String[line];
        for (int i = 0; i < line; i++) {
            mapArrayFinal[i] = mapArray[i];
        }
        isr.close();
        br.close();
        for (int i = 0; i < mapArrayFinal.length; i++) {
            System.out.println(mapArrayFinal[i]);

        }
        return mapArrayFinal;
    }

    public static String getMapAsString(String[] mapAsArray) {
        String mapAsString = "";
        for (int i = 0; i < mapAsArray.length; i++) {
            mapAsString += mapAsArray[i];
        }
        return mapAsString;
    }
}
