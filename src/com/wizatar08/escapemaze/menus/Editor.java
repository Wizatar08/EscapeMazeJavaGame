package com.wizatar08.escapemaze.menus;

import com.wizatar08.escapemaze.game.TileMap;
import com.wizatar08.escapemaze.helpers.ExternalMapHandler;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import static com.wizatar08.escapemaze.helpers.Drawer.*;

public class Editor {
    // Initialize variables
    private Texture background;
    private TileMap map;

    public Editor() {
        try {
            map = ExternalMapHandler.LoadMap("map.wtremm");
        } catch (NullPointerException e) {
            map = new TileMap();
        }
        background = LoadPNG("backgrounds/main_menu");
    }

    private void detectKey() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
                ExternalMapHandler.SaveMap("map.wtremm", map);
            }
        }
    }

    public void update() {
        detectKey();
        draw();
    }

    private void draw() {
        drawQuadTex(background, 0, 0, WIDTH * 2, HEIGHT);
        map.draw();
    }

}
