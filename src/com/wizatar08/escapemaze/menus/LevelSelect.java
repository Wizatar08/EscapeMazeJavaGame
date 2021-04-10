package com.wizatar08.escapemaze.menus;

import com.wizatar08.escapemaze.helpers.Lang;
import com.wizatar08.escapemaze.helpers.TextBlock;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import com.wizatar08.escapemaze.helpers.ui.UI;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class LevelSelect {
    // Initialize variables
    private UI levelSelectionUI;
    private UI.Menu levelMenu;
    private String path;
    private Tex background;

    // Constructor
    public LevelSelect() {
        background = new Tex("backgrounds/main_menu");
        path = "buttons/level_buttons/";
        levelSelectionUI = new UI();
        levelSelectionUI.createMenu("Levels", 100, 100, WIDTH - 200, 96 * 7, 7, 3);
        levelMenu = levelSelectionUI.getMenu("Levels");
        levelMenu.addButton("L1", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L1", Lang.get("level_select.level_button.1"), 25, 10, 48, Color.red), true, true);
        levelMenu.addButton("L2", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L2", Lang.get("level_select.level_button.2"), 25, 10, 48, Color.red), true, true);
        levelMenu.addButton("L3", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L3", Lang.get("level_select.level_button.3"), 25, 10, 48, Color.red), true, true);
        levelMenu.addButton("L4", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L4", Lang.get("level_select.level_button.4"), 25, 10, 48, Color.red), true, true);
        levelMenu.addButton("L5", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L5", Lang.get("level_select.level_button.5"), 25, 10, 48, Color.red), true, true);
        levelMenu.addButton("L6", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L6", Lang.get("level_select.level_button.6"), 25, 10, 48, Color.red), true, true);
        levelMenu.addButton("L7", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L7", Lang.get("level_select.level_button.7"), 25, 10, 48, Color.red), true, true);
        levelMenu.addButton("L8", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L8", Lang.get("level_select.level_button.8"), 25, 10, 48, Color.red), true, true);
        levelMenu.addButton("L9", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L9", Lang.get("level_select.level_button.9"), 25, 10, 48, Color.red), true, true);
        levelMenu.addButton("L10", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L10", Lang.get("level_select.level_button.10"), 15, 10, 48, Color.red), true, true);
        levelMenu.addButton("L11", new Tex[]{new Tex(path + "lvlselect")}, new TextBlock(levelSelectionUI, "L11", Lang.get("level_select.level_button.11"), 15, 10, 48, Color.red), true, true);
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
        background.draw(0, 0);
        detectIfButtonHit();
        draw();
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
