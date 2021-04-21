package com.wizatar08.escapemaze.helpers.visuals;

import com.wizatar08.escapemaze.helpers.ui.Button;
import com.wizatar08.escapemaze.helpers.ui.UI;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import static com.wizatar08.escapemaze.render.Renderer.HEIGHT;
import static com.wizatar08.escapemaze.render.Renderer.stretchedMultiplierTotal;

public class InputTextBlock extends TextBlock{
    private float width, height;
    private boolean isSelected, hasPlacedChar;

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
        super(ui, name, text, x, y, fontSize, color);
        this.width = width;
        this.height = height;
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
        if (isSelected) {
            inputText();
        }
        while (Mouse.next()) {
            if (isClicked() && Mouse.getEventButtonState()) {
                isSelected = !isSelected;
            }
        }
    }

    public void inputText() {
        while (Keyboard.next()) {
            if (!hasPlacedChar) {
                if (Keyboard.getEventKey() != Keyboard.KEY_BACK) {
                    setChars(getChars() + Keyboard.getEventCharacter());
                } else if (getChars().length() != 0) {
                    setChars(getChars().substring(0, getChars().length() - 1));
                }
                hasPlacedChar = true;
            }
        }
        if (!Keyboard.getEventKeyState()) {
            hasPlacedChar = false;
        }
    }

    public void select(boolean setSelect) {
        this.isSelected = setSelect;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
