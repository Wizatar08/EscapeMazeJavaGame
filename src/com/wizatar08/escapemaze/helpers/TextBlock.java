package com.wizatar08.escapemaze.helpers;

import com.wizatar08.escapemaze.helpers.ui.UI;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static com.wizatar08.escapemaze.helpers.Drawer.*;

public class TextBlock {
    private UI ui;
    private String text, name;
    private float x, y;
    private float fontSize;
    private Color textColor;

    public TextBlock(UI ui, String name, String text, int x, int y) {
        this(ui, name, text, x, y, 24f, Color.white);
    }

    public TextBlock(UI ui, String name, String text, int x, int y, float fontSize) {
        this(ui, name, text, x, y, fontSize, Color.white);
    }

    public TextBlock(UI ui, String name, String text, int x, int y, Color color) {
        this(ui, name, text, x, y, 24f, color);
    }

    public TextBlock(UI ui, String name, String text, int x, int y, float fontSize, Color color) {
        this.ui = ui;
        this.name = name;
        this.text = text;
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.textColor = color;
        changeFontSize(fontSize);
    }

    public void draw() {
        TRUE_TYPE_FONT.drawString(x, y, text, textColor);
    }

    public void changeFontSize(float size) {
        java.awt.Font newFont = FONT.deriveFont(size);
        TRUE_TYPE_FONT = new TrueTypeFont(newFont, true);
        FONT = newFont;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getText() {
        return text;
    }

    public float getFontSize() {
        return fontSize;
    }

    public Color getColor() {
        return textColor;
    }

    public String getName() {
        return name;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }
}
