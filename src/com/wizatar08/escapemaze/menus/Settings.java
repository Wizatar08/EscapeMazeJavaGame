package com.wizatar08.escapemaze.menus;

import com.wizatar08.escapemaze.helpers.Lang;
import com.wizatar08.escapemaze.helpers.visuals.TextBlock;
import com.wizatar08.escapemaze.helpers.visuals.Tex;
import com.wizatar08.escapemaze.helpers.ui.UI;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import static com.wizatar08.escapemaze.render.Renderer.*;

import java.io.*;
import java.util.*;

public class Settings {
    private String currentLang, windowSettings;
    private UI ui;
    private Tex background;
    private boolean buttonDown;

    private String[] langs = new String[]{"en_US", "te_ST"};
    private int langIndex = 0;

    public Settings() {
        background = new Tex("backgrounds/main_menu");
        ui = new UI();
        buttonDown = false;
        createBtns();
        try {
            setSettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
        createUI();
    }

    private void createBtns() {
        ui.addButton("Apply", new Tex[]{new Tex("buttons/settings_buttons/apply")}, WIDTH / 2 - 128, HEIGHT - 96, new TextBlock(ui, "Apply", Lang.get("settings.apply"), 0, 0, 32f, Color.blue), true, true);
        ui.addButton("Back", new Tex[]{new Tex("buttons/settings_buttons/back")}, 64, HEIGHT - 96);
        ui.addButton("Lang", new Tex[]{new Tex("buttons/settings_buttons/change")}, WIDTH - 320, 128, new TextBlock(ui, "Lang", Lang.get("settings.lang.change"), 0, 0, 32f, Color.white), true, true);
        ui.addButton("Window", new Tex[]{new Tex("buttons/settings_buttons/change")}, WIDTH - 320, 128 + 96, new TextBlock(ui, "Window", Lang.get("settings.window.change"), 0, 0, 32f, Color.white), true, true);
    }

    private void setSettings() throws Exception {
        Properties prop = new Properties();
        try {
            InputStream file = new FileInputStream("src/resources/settings/settings.properties");
            prop.load(file);
        } catch (FileNotFoundException e) {
            throw new Exception("Could not load settings, file seems to be missing: " + e.getCause());
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentLang = prop.getProperty("lang");
        windowSettings = prop.getProperty("updateWindow");

        for (int i = 0; i < langs.length; i++) {
            if (currentLang == langs[i]) {
                langIndex = i;
            }
        }
    }

    private void createUI() {
        ui.drawString(new TextBlock(ui, "Lang", Lang.get("settings.lang.current") + Lang.get("settings.lang." + langs[langIndex]), 64, 128, 32f));
        ui.drawString(new TextBlock(ui, "Window", Lang.get("settings.window.current") + Lang.get("settings.window." + windowSettings), 64, 128 + 96, 32f));
    }

    public void update() {
        draw();
        ui.drawAllStrings();
        detectIfButtonHit();
    }

    // Check if a key is pressed
    private boolean mouseDown(int key) {
        return (Mouse.getEventButton() == key) && (Mouse.getEventButtonState());
    }

    private void detectIfButtonHit() {
        while (Mouse.next()) {
            if (mouseDown(0)) {
                if (ui.isButtonClicked("Apply")) {
                    try {
                        updateSettings();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (ui.isButtonClicked("Back")) {
                    MenuRun.setState(Menus.MAIN_MENU);
                }
                if (ui.isButtonClicked("Lang")) {
                    langIndex++;
                    if (langIndex >= langs.length) {
                        langIndex = 0;
                    }
                    ui.changeString("Lang", (Lang.get("settings.lang.current") + Lang.get("settings.lang." + langs[langIndex])));
                }
                if (ui.isButtonClicked("Window")) {
                    if (windowSettings == "true") {
                        windowSettings = "false";
                    } else {
                        windowSettings = "true";
                    }
                    ui.changeString("Window", (Lang.get("settings.window.current") + Lang.get("settings.window." + windowSettings)));
                }
            }
        }
    }

    private void draw() {
        background.draw(0, 0);
        ui.draw();

    }

    public void updateSettings() throws Exception {
        Properties prop = new Properties();
        currentLang = langs[langIndex];
        prop.setProperty("lang", langs[langIndex]);
        prop.setProperty("updateWindow", windowSettings);

        Lang.changeLang(new Locale(langs[langIndex].substring(0, 2), langs[langIndex].substring(3, 5)));
        WINDOW_RESIZE = Boolean.parseBoolean(windowSettings);

        try {
            OutputStream outputStream = new FileOutputStream("src/resources/settings/settings.properties");
            prop.store(outputStream, "");
        } catch (FileNotFoundException e) {
            throw new Exception("Could not save settings, file seems to be missing: " + e.getCause());
        }

        updateStrings();
        ui.drawAllStrings();
    }

    private void updateStrings() {
        ui.changeString("Lang", Lang.get("settings.lang.current") + Lang.get("settings.lang." + langs[langIndex]));
        ui.changeString("Window", Lang.get("settings.window.current") + Lang.get("settings.window." + windowSettings));
        ui.getButton("Apply").changeString(Lang.get("settings.apply"));
        ui.getButton("Lang").changeString(Lang.get("settings.lang.change"));
        ui.getButton("Window").changeString(Lang.get("settings.window.change"));
    }
}
