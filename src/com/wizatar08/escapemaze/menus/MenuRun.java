package com.wizatar08.escapemaze.menus;

public class MenuRun {
    // Initialize variables
    public static Menus MENU = Menus.MAIN_MENU;
    private static int level;
    private static MainMenu mainMenu;
    private static LevelSelect levelSelect;
    private static Game game;
    private static Settings settings;
    private static Editor editor;
    public static long nextSecond = System.currentTimeMillis() + 1000;
    public static int framesInLastSecond = 0, framesInCurrentSecond = 0;

    // Update
    public void update() {
        switch (MENU) { // Loop through all possible values of 'menu'
            case MAIN_MENU: // Run if 'menu' == Menus.MAIN_MENU
                if (mainMenu == null) mainMenu = new MainMenu();
                mainMenu.update();
                break;
            case LEVEL_SELECT: // Run if 'menu' == Menus.LEVEL_SELECT
                if (levelSelect == null) levelSelect = new LevelSelect();
                levelSelect.update();
                break;
            case GAME: // Run if 'menu' == Menus.GAME
                if (game == null) game = new Game();
                game.update();
                break;
            case SETTINGS: // Run if 'menu' == Menus.SETTINGS
                if (settings == null) settings = new Settings();
                settings.update();
                break;
            case EDITOR: // DEV ONLY - Run if 'menu' == Menus.EDITOR
                if (editor == null) editor = new Editor();
                editor.update();
                break;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime > nextSecond) {
            nextSecond += 1000;
            framesInLastSecond = framesInCurrentSecond;
            framesInCurrentSecond = 0;
        }
        framesInCurrentSecond++;
    }

    public static void setState(Menus state) {
        mainMenu = null;
        levelSelect = null;
        game = null;
        settings = null;
        editor = null;
        MENU = state;
    }

    public static void setLevel(int setLevel) {
        level = setLevel;
    }

    public static int getLevel() {
        return level;
    }
}
