package com.wizatar08.escapemaze.menus;

public class MenuRun {
    // Initialize variables
    public static Menus MENU = Menus.MAIN_MENU;
    private static int level;
    private MainMenu mainMenu;
    private LevelSelect levelSelect;
    private Game game;
    private Editor editor;

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
            case EDITOR: // DEV ONLY - Run if 'menu' == Menus.EDITOR
                if (editor == null) editor = new Editor();
                editor.update();
                break;
        }
    }

    public static void setState(Menus state) {
        MENU = state;
    }

    public static void setLevel(int setLevel) {
        level = setLevel;
    }

    public static int getLevel() {
        return level;
    }
}
