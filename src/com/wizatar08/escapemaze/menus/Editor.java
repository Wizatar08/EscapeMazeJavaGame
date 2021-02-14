package com.wizatar08.escapemaze.menus;

import com.wizatar08.escapemaze.enumerators.TileType;
import com.wizatar08.escapemaze.game.TileMap;
import com.wizatar08.escapemaze.helpers.ExternalMapHandler;
import com.wizatar08.escapemaze.helpers.ui.UI;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import javax.swing.*;

import static com.wizatar08.escapemaze.helpers.Drawer.*;

import java.util.ArrayList;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class Editor {
    // Initialize variables
    private Texture background;
    private TileMap map;
    private UI editorUI;
    private ArrayList<UI.Menu> menus;
    public static int displacementX;
    public static int displacementY;

    public Editor() {
        editorUI = new UI();
        menus = new ArrayList<>();
        createMenus();
        addButtons();
        try {
            map = ExternalMapHandler.LoadMap("map.wtremm");
        } catch (NullPointerException e) {
            map = new TileMap();
        }
        displacementX = 0;
        displacementY = 0;
        background = LoadPNG("backgrounds/main_menu");
    }

    private void detectKey() {
        while (Keyboard.next()) {
            if (keyDown(Keyboard.KEY_S)) {
                ExternalMapHandler.SaveMap("map.wtremm", map);
            }
            if (keyDown(Keyboard.KEY_EQUALS)) {
                try {
                    System.out.println(Integer.parseInt(JOptionPane.showInputDialog("FIUBVHJDFUH")));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,"Inputted number is not an integer.");
                }
            }
        }
        if (keyDown(Keyboard.KEY_UP)) displacementY -= 1;
        if (keyDown(Keyboard.KEY_DOWN)) displacementY += 1;
        if (keyDown(Keyboard.KEY_LEFT)) displacementX -= 1;
        if (keyDown(Keyboard.KEY_RIGHT)) displacementX += 1;
    }

    private boolean keyDown(int key) {
        return (Keyboard.getEventKey() == key) && (Keyboard.getEventKeyState());
    }

    public void update() {
        detectKey();
        draw();
    }

    private void draw() {
        drawQuadTex(background, 0, 0, WIDTH * 2, HEIGHT);
        map.draw();
        editorUI.draw();
    }


    private void createMenus() {
        editorUI.createMenu("Tiles1", WIDTH - (64 * 3), 20, (64 * 3), HEIGHT - 64, 3, 11);
    }

    private void addButtons() {
        editorUI.getMenu("Tiles1").addButton(TileType.METAL_WALL.getId(), new Texture[]{LoadPNG("tiles/metal_wall")});
        editorUI.getMenu("Tiles1").addButton(TileType.METAL_WALL_B.getId(), new Texture[]{LoadPNG("tiles/metal_wall"), LoadPNG("tile_overlays/wall_side")}, new int[]{0, 0});
        editorUI.getMenu("Tiles1").addButton(TileType.METAL_WALL_L.getId(), new Texture[]{LoadPNG("tiles/metal_wall"), LoadPNG("tile_overlays/wall_side")}, new int[]{0, 90});
        editorUI.getMenu("Tiles1").addButton(TileType.METAL_WALL_T.getId(), new Texture[]{LoadPNG("tiles/metal_wall"), LoadPNG("tile_overlays/wall_side")}, new int[]{0, 180});
        editorUI.getMenu("Tiles1").addButton(TileType.METAL_WALL_R.getId(), new Texture[]{LoadPNG("tiles/metal_wall"), LoadPNG("tile_overlays/wall_side")}, new int[]{0, 270});
    }

}
