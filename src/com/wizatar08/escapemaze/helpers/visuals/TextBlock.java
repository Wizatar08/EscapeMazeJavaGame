package com.wizatar08.escapemaze.helpers.visuals;

import com.wizatar08.escapemaze.helpers.ui.UI;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import static com.wizatar08.escapemaze.helpers.visuals.Drawer.*;

public class TextBlock {
    private UI ui;
    private String text, name;
    private float x, y;
    private float fontSize;
    private Color textColor;
    private boolean show;
    private Font awtFont;
    private TrueTypeFont trueTypeFont;

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
        this.awtFont = FONT;
        this.trueTypeFont = TRUE_TYPE_FONT;
        changeFontSize(fontSize);
        show = true;
    }

    public void draw() {
        if (show) {
            trueTypeFont.drawString(x, y, text, textColor);
        }
    }

    public void changeFontSize(float size) {
        java.awt.Font newFont = awtFont.deriveFont(size);
        trueTypeFont = new TrueTypeFont(newFont, true);
        awtFont = newFont;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getChars() {
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

    public float getWidth() {
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        return (float) (awtFont.getStringBounds(text, frc).getWidth());
    }
    public float getHeight() {
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        return (float)(awtFont.getStringBounds(text, frc).getHeight());
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setChars(String text) {
        this.text = text;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public boolean isShown() {
        return show;
    }

    public void show() {
        show = true;
    }

    public void hide() {
        show = false;
    }

    public TrueTypeFont getTrueTypeFont () {
        return trueTypeFont;
    }
}
