package com.wizatar08.escapemaze.helpers.ui;

import static com.wizatar08.escapemaze.helpers.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

import com.wizatar08.escapemaze.render.Renderer;
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

    public void addButton(String name, String textureName, int x, int y) {
        buttonList.add(new Button(name, LoadPNG("buttons/" + textureName), x, y));
    }

    public boolean isButtonClicked(String buttonName) {
        Button b = getButton(buttonName);
        float mouseY = (HEIGHT - Mouse.getY() - 1) - (Display.getHeight() - ((float) HEIGHT * stretchedMultiplierTotal) - (Display.getHeight() - HEIGHT));
        //System.out.println(((float) b.getX() * stretchedMultiplierTotal) + ", " + (((float) b.getX() + b.getWidth()) * stretchedMultiplierTotal) + ", " + ((float) b.getY() * stretchedMultiplierTotal) + ", " + (((float) b.getY() + b.getHeight()) * stretchedMultiplierTotal) + ", " + mouseY + ", " + stretchedMultiplierTotal);
        return
                Mouse.getX() > ((float) b.getX() * stretchedMultiplierTotal) && // 97.5
                Mouse.getX() < (((float) b.getX() + b.getWidth()) * stretchedMultiplierTotal) && // 162.5
                mouseY > ((float) b.getY() * stretchedMultiplierTotal) && // 87
                mouseY < (((float) b.getY() + b.getHeight()) * stretchedMultiplierTotal); // 103
    }

    private Button getButton(String buttonName) {
        for (Button b: buttonList) {
            if (b.getName().equals(buttonName)) {
                return b;
            }
        }
        return null;
    }

    public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
        menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight));
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
            drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }
        for (Menu m: menuList) {
            m.draw();
        }
    }

    public class Menu {
        String name;
        private ArrayList<Button> menuButtons;
        private int x, y, width, height, buttonAmount, optionsWidth, optionsHeight, padding;
        private boolean show;

        public Menu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.optionsWidth = optionsWidth;
            this.optionsHeight = optionsHeight;
            this.padding = (width - (optionsWidth * TILE_SIZE)) / (optionsWidth + 1);
            this.buttonAmount = 0;
            this.menuButtons = new ArrayList<Button>();
            this.show = true;
        }

        public void addButton(Button b) {
            setButton(b);
        }

        public void addButton(String name, String buttonTextureName) {
            Button b = new Button(name, LoadPNG("buttons/" + buttonTextureName), 0, 0);
            setButton(b);
        }

        /**
         * Editor UI
         * @param name
         * @param buttonTexture
         */
        public void addButton(String name, Texture buttonTexture) {
            Button b = new Button(name, buttonTexture, 0, 0);
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
            float mouseY = HEIGHT - Mouse.getY() - 1;
            if (Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() && mouseY > b.getY() && mouseY < b.getY() + b.getHeight()) {
                return true;
            }
            return false;
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
                for (Button b : menuButtons) {
                    drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
                }
            }
        }

        public String getName() {
            return name;
        }

        public void showMenu(boolean showMenu) {
            show = showMenu;
        }
    }

}
