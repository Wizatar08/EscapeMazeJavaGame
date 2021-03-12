package com.wizatar08.escapemaze.menus;

import com.wizatar08.escapemaze.helpers.TextBlock;
import com.wizatar08.escapemaze.helpers.ui.UI;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static com.wizatar08.escapemaze.helpers.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

public class MainMenu {
    // Initialize variables
    private UI ui;
    private Texture background;

    // Constructor
    public MainMenu() {
        background = LoadPNG("backgrounds/main_menu");
        ui = new UI();
        ui.addButton("PlayBtn", new Texture[]{LoadPNG("buttons/playbtn")}, WIDTH / 2 - (256 / 2), (int) (HEIGHT * 0.45));
        ui.addButton("QuitBtn", new Texture[]{LoadPNG("buttons/quitbtn")}, WIDTH / 2 - (256 / 2), (int) (HEIGHT * 0.55));
        ui.addButton("EditBtn", new Texture[]{LoadPNG("buttons/editorbtn")}, WIDTH / 2 - (256 / 2), (int) (HEIGHT * 0.65));
    }

    // Detect if a button is pressed
    private void detectIfButtonHit() {
        if (Mouse.isButtonDown(0)) {
            if (ui.isButtonClicked("PlayBtn")) MenuRun.setState(Menus.LEVEL_SELECT);
            if (ui.isButtonClicked("QuitBtn")) System.exit(0);
            if (ui.isButtonClicked("EditBtn")) MenuRun.setState(Menus.EDITOR);
        }
    }

    // Loop
    public void update() {
        draw();
        detectIfButtonHit();
    }

    // Draw objects on display
    private void draw() {
        drawQuadTex(background, 0, 0, WIDTH * 2, HEIGHT);
        ui.draw();
    }

}
