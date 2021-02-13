package com.wizatar08.escapemaze.menus;

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
        background = LoadPNG("backgrounds/main_menu");
    }

    private void detectKey() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
                ExternalMapHandler.SaveMap("map.wtremm", map);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_EQUALS && Keyboard.getEventKeyState()) {
                try {
                    System.out.println(Integer.parseInt(JOptionPane.showInputDialog("FIUBVHJDFUH")));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,"Inputted number is not an integer.");
                }
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
        editorUI.draw();
    }


    private void createMenus() {
        editorUI.createMenu("Tiles1", WIDTH - (64 * 3), 20, (64 * 3), HEIGHT - 64, 3, 11);
    }

    private void addButtons() {
        editorUI.getMenu("Tiles1").addButton("Tile1", LoadPNG("tiles/null"));
    }

}
