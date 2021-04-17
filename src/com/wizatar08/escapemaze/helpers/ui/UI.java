package com.wizatar08.escapemaze.helpers.ui;

import static com.wizatar08.escapemaze.render.Renderer.*;

import com.wizatar08.escapemaze.helpers.TextBlock;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import java.util.ArrayList;

public class UI {
    private ArrayList<Button> buttonList;
    private ArrayList<Menu> menuList;
    private ArrayList<TextBlock> textList;
    private TextBlock blank;

    public UI() {
        buttonList = new ArrayList<>();
        menuList = new ArrayList<>();
        textList = new ArrayList<>();
        blank = new TextBlock(null, "blank", "", 0, 0, 24f, Color.white);
    }

    public void drawString(TextBlock text) {
        textList.add(text);
    }

    public void eraseString(String name) {
        for (TextBlock text : textList) {
            if (text.getName().equals(name)) {
                textList.remove(text);
                return;
            }
        }
        System.err.println("eraseString - TextBlock not found: " + name);
    }

    public void showString(String name, boolean show) {
        for (TextBlock text : textList) {
            if (text.getName().equals(name)) {
                if (show) {
                    text.show();
                } else {
                    text.hide();
                }
                return;
            }
        }
        System.err.println("showString - TextBlock not found: " + name);
    }

    public void setStringPos(String name, float x, float y) {
        for (TextBlock text : textList) {
            if (text.getName().equals(name)) {
                text.setX(x);
                text.setY(y);
                return;
            }
        }
        System.err.println("setStringPos - TextBlock not found: " + name);
    }

    public void changeString(String name, String newText) {
        for (TextBlock text : textList) {
            if (text.getName().equals(name)) {
                text.setChars(newText);
                return;
            }
        }
        System.err.println("changeString - TextBlock not found: " + name);
    }

    public TextBlock getString(String name) {
        for (TextBlock text : textList) {
            if (text.getName().equals(name)) {
                return text;
            }
        }
        System.err.println("getString - TextBlock not found: " + name);
        return null;
    }

    public void changeStringColor(String name, Color color) {
        for (TextBlock text : textList) {
            if (text.getName().equals(name)) {
                text.setTextColor(color);
                return;
            }
        }
        System.err.println("changeStringColor - TextBlock not found: " + name);
    }

    public void drawAllStrings() {
        for (TextBlock text : textList) {
            text.draw();
        }
    }

    public void addButton(String name, Tex[] textureNames, int x, int y) {
        int[] rots = new int[textureNames.length];
        for (int i = 0; i < textureNames.length; i++) {
            rots[i] = 0;
        }
        addButton(name, textureNames, x, y, rots);
    }

    public void addButton(String name, Tex[] textureNames, int x, int y, int[] rots) {
        buttonList.add(new Button(name, textureNames, x, y, rots));
    }

    public void addButton(String name, Tex[] textureNames, int x, int y, TextBlock text) {
        addButton(name, textureNames, x, y, text, false, false);
    }

    public void addButton(String name, Tex[] textureNames, int x, int y, TextBlock text, boolean centerW, boolean centerH) {
        Button b;
        int[] rots = new int[textureNames.length];
        for (int i = 0; i < textureNames.length; i++) {
            rots[i] = 0;
        }
        buttonList.add(b = new Button(name, textureNames, x, y, rots, text));
        if (b.getText() != null) {
            if (!centerW) {
                b.getText().setX(x + b.getText().getX());
            } else {
                b.getText().setX(b.getX() + (float) b.getWidth() / 2 - (b.getText().getWidth() / 2));
            }
            if (!centerH) {
                b.getText().setY(y + b.getText().getY());
            } else {
                b.getText().setY(b.getY() + (float) b.getHeight() / 2 - (b.getText().getHeight() / 2));
            }
        }
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

    public Button getButton(String buttonName) {
        for (Button b: buttonList) {
            if (b.getName().equals(buttonName)) {
                return b;
            }
        }
        return null;
    }

    public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight, Tex background) {
        menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight, background));
    }

    public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
        menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight, null));
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
                b.getTextures()[i].draw(b.getX(), b.getY());
            }
            if (b.getText() != null) {
                b.getText().draw();
            }
        }
        for (Menu m: menuList) {
            m.draw();
        }
        blank.draw();
    }

    public ArrayList<Menu> getMenuList() {
        return menuList;
    }

    public class Menu {
        String name;
        private ArrayList<Button> menuButtons;
        private int x, y, width, height, buttonAmount, optionsWidth, yDist;
        private boolean show;
        private Tex background;
        private TextBlock blank;

        public Menu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight, Tex background) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.optionsWidth = optionsWidth;
            this.background = background;
            this.buttonAmount = 0;
            this.menuButtons = new ArrayList<Button>();
            this.show = true;
            this.blank = new TextBlock(null, "blank", "", 0, 0, 24f, Color.white);
            this.yDist = height / optionsHeight;
        }

        public void addButton(String name, Tex[] buttonTextures) {
            addButton(name, buttonTextures, (TextBlock) null, false, false);
        }

        public void addButton(String name, Tex[] buttonTextures, TextBlock text, boolean centerW, boolean centerH) {
            int[] rots = new int[buttonTextures.length];
            for (int i = 0; i < buttonTextures.length; i++) {
                rots[i] = 0;
            }
            Button b = new Button(name, buttonTextures, 0, 0, rots, text);
            setButton(b);
            if (b.getText() != null) {
                if (!centerW) {
                    b.getText().setX(b.getX() + b.getText().getX());
                } else {
                    b.getText().setX(b.getX() + (float) b.getWidth() / 2 - (b.getText().getWidth() / 2));
                }
                if (!centerH) {
                    b.getText().setY(b.getY() + b.getText().getY());
                } else {
                    b.getText().setY(b.getY() + (float) b.getHeight() / 2 - (b.getText().getHeight() / 2));
                }
            }
        }

        /**
         * Editor UI
         * @param name
         * @param buttonTexture
         * @param rot
         */
        public void addButton(String name, Tex[] buttonTexture, int[] rot) {
            Button b = new Button(name, buttonTexture, 0, 0, rot);
            setButton(b);
        }

        private void setButton(Button b) {
            if (optionsWidth != 0) {
                b.setY(y + (buttonAmount / optionsWidth) * yDist);
            }
            b.setX(x + (buttonAmount % optionsWidth) * (width / optionsWidth));
            buttonAmount++;
            menuButtons.add(b);
        }

        public boolean isButtonClicked(String buttonName) {
            try {
                Button b = getButton(buttonName);
                float mouseY = (HEIGHT - Mouse.getY() - 1) - (Display.getHeight() - ((float) HEIGHT * stretchedMultiplierTotal) - (Display.getHeight() - HEIGHT));
                return
                        (
                                Mouse.getX() > ((float) b.getX() * stretchedMultiplierTotal) &&
                                        Mouse.getX() < (((float) b.getX() + b.getWidth()) * stretchedMultiplierTotal) &&
                                        mouseY > ((float) b.getY() * stretchedMultiplierTotal) &&
                                        mouseY < (((float) b.getY() + b.getHeight()) * stretchedMultiplierTotal)
                        ) && show;
            } catch (NullPointerException e) {
                return false;
            }
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
                    for (int i = 0; i < b.getTextures().length; i++) {
                        if (i == 0) {
                            b.getTextures()[0].draw(b.getX(), b.getY());
                        } else {
                            b.getTextures()[i].draw(b.getX(), b.getY(), b.getRots()[i - 1]);
                        }
                    }
                    if (b.getText() != null) {
                        b.getText().draw();
                        blank.draw();
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

        public ArrayList<TextBlock> getTextBlocks() {
            return textList;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

}
