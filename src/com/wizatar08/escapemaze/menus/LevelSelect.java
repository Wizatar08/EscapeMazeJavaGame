package com.wizatar08.escapemaze.menus;

import com.wizatar08.escapemaze.enumerators.Menus;
import com.wizatar08.escapemaze.helpers.ui.UI;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static com.wizatar08.escapemaze.helpers.Drawer.*;

public class LevelSelect {
    // Initialize variables
    private UI levelSelectionUI;
    private UI.Menu levelMenu;
    private String path;
    private Texture background;

    // Constructor
    public LevelSelect() {
        background = LoadPNG("backgrounds/main_menu");
        path = "level_buttons/";
        levelSelectionUI = new UI();
        levelSelectionUI.createMenu("Levels", 100, 100, WIDTH - 200, 96 * 7, 7, 3);
        levelMenu = levelSelectionUI.getMenu("Levels");
        levelMenu.addButton("L1", path + "lvl1");
        levelMenu.addButton("L2", path + "lvl2");
        levelMenu.addButton("L3", path + "lvl3");
        levelMenu.addButton("L4", path + "lvl4");
        levelMenu.addButton("L5", path + "lvl5");
        levelMenu.addButton("L6", path + "lvl6");
        levelMenu.addButton("L7", path + "lvl7");
        levelMenu.addButton("L8", path + "lvl8");
        levelMenu.addButton("L9", path + "lvl9");
        levelMenu.addButton("L10", path + "lvl10");
        levelMenu.addButton("L11", path + "lvl11");
    }

    // Detect if button is pressed
    private void detectIfButtonHit() {
        if (Mouse.isButtonDown(0)) {
            if (levelMenu.isButtonClicked("L1")) setLevel(1);
            if (levelMenu.isButtonClicked("L2")) setLevel(2);
            if (levelMenu.isButtonClicked("L3")) setLevel(3);
            if (levelMenu.isButtonClicked("L4")) setLevel(4);
            if (levelMenu.isButtonClicked("L5")) setLevel(5);
            if (levelMenu.isButtonClicked("L6")) setLevel(6);
            if (levelMenu.isButtonClicked("L7")) setLevel(7);
            if (levelMenu.isButtonClicked("L8")) setLevel(8);
            if (levelMenu.isButtonClicked("L9")) setLevel(9);
            if (levelMenu.isButtonClicked("L10")) setLevel(10);
            if (levelMenu.isButtonClicked("L11")) setLevel(11);
        }
    }

    // Loop
    public void update() {
        drawQuadTex(background, 0, 0, WIDTH * 2, HEIGHT);
        draw();
        detectIfButtonHit();
    }

    // Draw objects on stage
    private void draw() {
        levelSelectionUI.draw();
    }

    // Set the level and send information to MenuRun
    private void setLevel(int level) {
        MenuRun.setLevel(level);
        MenuRun.setState(Menus.GAME);
    }
}
