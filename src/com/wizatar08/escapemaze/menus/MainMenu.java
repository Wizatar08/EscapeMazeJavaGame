package com.wizatar08.escapemaze.menus;

import com.wizatar08.escapemaze.helpers.Lang;
import com.wizatar08.escapemaze.visuals.AnimatedTex;
import com.wizatar08.escapemaze.visuals.TextBlock;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.helpers.ui.UI;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class MainMenu {
    // Initialize variables
    private UI ui;
    private Tex background;
    private boolean buttonDown;
    private Timer cooldownTimer;

    // Constructor
    public MainMenu() {
        background = new Tex("backgrounds/main_menu");
        ui = new UI();
        buttonDown = false;
        cooldownTimer = new Timer(Timer.TimerModes.COUNT_DOWN, 1);
        cooldownTimer.unpause();
        ui.addButton("PlayBtn", new Tex[]{new Tex("buttons/main_menu_btn")}, WIDTH / 2 - (256 / 2), (int) (HEIGHT * 0.45), new TextBlock(ui, "Play", Lang.get("main_menu.buttons.play"), 0, 0, 56f, Color.yellow), true, true);
        ui.addButton("QuitBtn", new Tex[]{new Tex("buttons/main_menu_btn")}, WIDTH / 2 - (256 / 2), (int) (HEIGHT * 0.55), new TextBlock(ui, "Quit", Lang.get("main_menu.buttons.quit"), 0, 0, 56f, Color.yellow), true, true);
        ui.addButton("EditBtn", new Tex[]{new Tex("buttons/main_menu_btn")}, WIDTH / 2 - (256 / 2), (int) (HEIGHT * 0.65), new TextBlock(ui, "Editor", Lang.get("main_menu.buttons.editor"), 0, 0, 56f, Color.yellow), true, true);
        ui.addButton("SettingsBtn", new Tex[]{new Tex("buttons/settings")}, WIDTH - 128, HEIGHT - 128);
    }

    // Detect if a button is pressed
    private void detectIfButtonHit() {
        if (Mouse.isButtonDown(0) && !buttonDown && cooldownTimer.isPaused()) {
            if (ui.isButtonClicked("PlayBtn")) MenuRun.setState(Menus.LEVEL_SELECT);
            if (ui.isButtonClicked("QuitBtn")) System.exit(0);
            if (ui.isButtonClicked("EditBtn")) MenuRun.setState(Menus.EDITOR);
            if (ui.isButtonClicked("SettingsBtn")) MenuRun.setState(Menus.SETTINGS);
            buttonDown = true;
        } else {
            buttonDown = false;
        }
    }

    // Loop
    public void update() {
        draw();
        detectIfButtonHit();
        cooldownTimer.update();
        if (cooldownTimer.getTotalSeconds() >= 4) {
            cooldownTimer.setTime(1);
        }
        if (cooldownTimer.getTotalSeconds() <= 0) {
            cooldownTimer.pause();
        }
    }

    // Draw objects on display
    private void draw() {
        background.draw(0, 0);
        ui.draw();
    }

}
