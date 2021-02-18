package com.wizatar08.escapemaze.helpers.ui;

import static com.wizatar08.escapemaze.helpers.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    private ArrayList<Button> buttonList;
    private ArrayList<Menu> menuList;
    private TrueTypeFont font;
    private Font awtFont;

    public UI() {
        buttonList = new ArrayList<Button>();
        menuList = new ArrayList<Menu>();
        awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, false);
    }

    public void drawString(int x, int y, String text) {
        font.drawString(x, y, text);
    }

    public void addButton(String name, Texture[] textureNames, int x, int y) {
        int[] rots = new int[textureNames.length];
        for (int i = 0; i < textureNames.length; i++) {
            rots[i] = 0;
        }
        addButton(name, textureNames, x, y, rots);
    }

    public void addButton(String name, Texture[] textureNames, int x, int y, int[] rots) {
        buttonList.add(new Button(name, textureNames, x, y, rots));
    }

    public boolean isButtonClicked(String buttonName) {
        Button b = getButton(buttonName);
        float mouseY = (HEIGHT - Mouse.getY() - 1) - (Display.getHeight() - ((float) HEIGHT * stretchedMultiplierTotal) - (Display.getHeight() - HEIGHT));
        return
                Mouse.getX() > ((float) b.getX() * stretchedMultiplierTotal) &&
                Mouse.getX() < (((float) b.getX() + b.getWidth()) * stretchedMultiplierTotal) &&
                mouseY > ((float) b.getY() * stretchedMultiplierTotal) &&
                mouseY < (((float) b.getY() + b.getHeight()) * stretchedMultiplierTotal);
    }

    private Button getButton(String buttonName) {
        for (Button b: buttonList) {
            if (b.getName().equals(buttonName)) {
                return b;
            }
        }
        return null;
    }

    public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight, Texture background, int xOffset, int yOffset) {
        menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight, background, xOffset, yOffset));
    }

    public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
        menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight, null, 0, 0));
    }

    public Menu getMenu(String name) {
        for (Menu m: menuList) {
            if (name.equals(m.getName())) {
                return m;
            }
        }
        return null;
    }

    public void draw() {
        for (Button b: buttonList) {
            for (int i = 0; i < b.getTextures().length; i++) {
                drawQuadTex(b.getTextures()[i], b.getX(), b.getY(), b.getWidth(), b.getHeight());
            }
        }
        for (Menu m: menuList) {
            m.draw();
        }
    }

    public ArrayList<Menu> getMenuList() {
        return menuList;
    }

    public class Menu {
        String name;
        private ArrayList<Button> menuButtons;
        private int x, y, width, height, buttonAmount, optionsWidth, optionsHeight, padding, xOffset, yOffset;
        private boolean show;
        private Texture background;

        public Menu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight, Texture background, int xOffset, int yOffset) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.optionsWidth = optionsWidth;
            this.optionsHeight = optionsHeight;
            this.background = background;
            this.padding = (width - (optionsWidth * TILE_SIZE)) / (optionsWidth + 1);
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.buttonAmount = 0;
            this.menuButtons = new ArrayList<Button>();
            this.show = true;
        }

        public void addButton(String name, Texture[] buttonTextures) {
            int[] rots = new int[buttonTextures.length];
            for (int i = 0; i < buttonTextures.length; i++) {
                rots[i] = 0;
            }
            Button b = new Button(name, buttonTextures, 0, 0, rots);
            setButton(b);
        }

        /**
         * Editor UI
         * @param name
         * @param buttonTexture
         * @param rot
         */
        public void addButton(String name, Texture[] buttonTexture, int[] rot) {
            Button b = new Button(name, buttonTexture, 0, 0, rot);
            setButton(b);
        }

        private void setButton(Button b) {
            if (optionsWidth != 0) b.setY(y + (buttonAmount / optionsWidth) * TILE_SIZE);
            b.setX(x + (buttonAmount % optionsWidth) * (padding + TILE_SIZE) + padding);
            buttonAmount++;
            menuButtons.add(b);
        }

        public boolean isButtonClicked(String buttonName) {
            Button b = getButton(buttonName);
            float mouseY = (HEIGHT - Mouse.getY() - 1) - (Display.getHeight() - ((float) HEIGHT * stretchedMultiplierTotal) - (Display.getHeight() - HEIGHT));
            return
                    (
                    Mouse.getX() > ((float) b.getX() * stretchedMultiplierTotal) &&
                    Mouse.getX() < (((float) b.getX() + b.getWidth()) * stretchedMultiplierTotal) &&
                    mouseY > ((float) b.getY() * stretchedMultiplierTotal) &&
                    mouseY < (((float) b.getY() + b.getHeight()) * stretchedMultiplierTotal)
                    ) && show;
        }

        private Button getButton(String buttonName) {
            for (Button b: menuButtons) {
                if (b.getName().equals(buttonName)) {
                    return b;
                }
            }
            return null;
        }

        public void draw() {
            if (show) {
                //if (background != null) drawQuadTex(background, -xOffset + x, -yOffset + y, width, height);
                for (Button b : menuButtons) {
                    for (int i = 0; i < b.getTextures().length; i++) {
                        drawQuadTex(b.getTextures()[i], b.getX(), b.getY(), b.getWidth(), b.getHeight(), b.getRots()[i]);
                    }
                }
            }
        }

        public String getName() {
            return name;
        }

        public void showMenu(boolean showMenu) {
            show = showMenu;
        }

        public ArrayList<Button> getButtons() {
            return menuButtons;
        }
    }

}
