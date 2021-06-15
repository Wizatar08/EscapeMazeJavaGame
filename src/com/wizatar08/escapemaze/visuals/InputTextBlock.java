package com.wizatar08.escapemaze.visuals;

import com.wizatar08.escapemaze.helpers.ui.UI;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.Arrays;

import static com.wizatar08.escapemaze.render.Renderer.HEIGHT;
import static com.wizatar08.escapemaze.render.Renderer.stretchedMultiplierTotal;

public class InputTextBlock extends TextBlock{
    private float width, height;
    private boolean isSelected, hasPlacedChar, enter;
    private String noTextHover;
    private Tex cursorTex;
    private static ArrayList<Integer> NO_CHAR_BUTTONS = new ArrayList<>(Arrays.asList(Keyboard.KEY_LSHIFT, Keyboard.KEY_RSHIFT, Keyboard.KEY_FUNCTION, Keyboard.KEY_LCONTROL, Keyboard.KEY_RCONTROL, Keyboard.KEY_SYSRQ, Keyboard.KEY_SYSRQ, Keyboard.KEY_RIGHT, Keyboard.KEY_LEFT, Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_ESCAPE, Keyboard.KEY_F1, Keyboard.KEY_F2, Keyboard.KEY_F3, Keyboard.KEY_F4, Keyboard.KEY_F5, Keyboard.KEY_F6, Keyboard.KEY_F7, Keyboard.KEY_F8, Keyboard.KEY_F9, Keyboard.KEY_F10, Keyboard.KEY_F11, Keyboard.KEY_F12, Keyboard.KEY_F13, Keyboard.KEY_F14, Keyboard.KEY_F15, Keyboard.KEY_F16, Keyboard.KEY_F17, Keyboard.KEY_F18, Keyboard.KEY_F19, Keyboard.KEY_LMENU, Keyboard.KEY_RMENU, Keyboard.KEY_LMETA, Keyboard.KEY_RMETA));

    public InputTextBlock(UI ui, String name, String text, int x, int y, int width, int height) {
        this(ui, name, text, x, y, width, height, 24, Color.white);
    }

    public InputTextBlock(UI ui, String name, String text, int x, int y, int width, int height, float fontSize) {
        this(ui, name, text, x, y, width, height, fontSize, Color.white);
    }

    public InputTextBlock(UI ui, String name, String text, int x, int y, int width, int height, Color color) {
        this(ui, name, text, x, y, width, height, 24, color);
    }

    public InputTextBlock(UI ui, String name, String text, int x, int y, int width, int height, float fontSize, Color color) {
        this (ui, name, text, x, y, width, height, fontSize, color, "");
    }

    public InputTextBlock(UI ui, String name, String text, int x, int y, int width, int height, float fontSize, Color color, String noTextHover) {
        super(ui, name, text, x, y, fontSize, color);
        this.width = width;
        this.height = height;
        cursorTex = new Tex("ui/cursor");
        this.noTextHover = noTextHover;
    }

    public boolean isClicked() {
        float mouseY = (HEIGHT - Mouse.getY() - 1) - (Display.getHeight() - ((float) HEIGHT * stretchedMultiplierTotal) - (Display.getHeight() - HEIGHT));
        return
                Mouse.getX() > (getX() * stretchedMultiplierTotal) &&
                        Mouse.getX() < ((getX() + width) * stretchedMultiplierTotal) &&
                        mouseY > (getY() * stretchedMultiplierTotal) &&
                        mouseY < ((getY() + height) * stretchedMultiplierTotal);
    }

    public void update() {
        enter = false;
        if (isSelected) {
            inputText();
        }
        while (Mouse.next()) {
            if (Mouse.getEventButtonState()) {
                isSelected = isClicked();
            }
        }
    }

    @Override
    public void draw() {
        if (super.isShown()) {
            if (!getChars().equals("")) {
                super.getTrueTypeFont().drawString(getX(), getY(), getChars(), getColor());
            } else {
                super.getTrueTypeFont().drawString(getX(), getY(), noTextHover, getColor().darker(0.35f));
            }
        }
        if (isSelected) {
            cursorTex.draw(getX() + getWidth(), getY(), getColor());
        }
    }

    public void inputText() {
        while (Keyboard.next()) {
            if (!hasPlacedChar) {
                if ((Keyboard.getEventKey() == Keyboard.KEY_BACK || Keyboard.getEventKey() == Keyboard.KEY_DELETE)) {
                    if (getChars().length() != 0) {
                        setChars(getChars().substring(0, getChars().length() - 1));
                    }
                } else if (Keyboard.getEventKey() == Keyboard.KEY_RETURN || Keyboard.getEventKey() == Keyboard.KEY_NUMPADENTER) {
                    enter = true;
                } else {
                    boolean hasChar = true;
                    for (int charNum : NO_CHAR_BUTTONS) {
                        if (Keyboard.getEventKey() == charNum) {
                            hasChar = false;
                        }
                    }
                    if (hasChar) {
                        setChars(getChars() + Keyboard.getEventCharacter());
                    }
                }
                hasPlacedChar = true;
            }
        }
        if (!Keyboard.getEventKeyState()) {
            hasPlacedChar = false;
        }
    }

    public boolean activated() {
        return enter;
    }

    public void select(boolean setSelect) {
        this.isSelected = setSelect;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
