package com.wizatar08.escapemaze.menus;

import com.wizatar08.escapemaze.enumerators.TileType;
import com.wizatar08.escapemaze.game.TileMap;
import com.wizatar08.escapemaze.helpers.ExternalMapHandler;
import com.wizatar08.escapemaze.helpers.ui.UI;
import com.wizatar08.escapemaze.objects.Tile;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import javax.swing.*;

import static com.wizatar08.escapemaze.helpers.Drawer.*;

import java.util.ArrayList;
import java.util.Map;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class Editor {
    // Initialize variables
    private Texture background;
    private TileMap map;
    private UI editorUI;
    private ArrayList<UI.Menu> menus;
    public static int displacementX;
    public static int displacementY;
    private TileType tileSelected;

    public Editor() {
        editorUI = new UI();
        menus = new ArrayList<>();
        tileSelected = TileType.METAL_WALL;
        createMenus();
        try {
            map = ExternalMapHandler.LoadMap("map.wtremm");
        } catch (NullPointerException e) {
            map = new TileMap();
        }
        displacementX = 0;
        displacementY = 0;
        background = LoadPNG("backgrounds/main_menu");
    }

    private void detectKey() {
        while (Keyboard.next()) {
            if (keyDown(Keyboard.KEY_S)) {
                ExternalMapHandler.SaveMap("map.wtremm", map);
            }
            if (keyDown(Keyboard.KEY_EQUALS)) {
                try {
                    System.out.println(Integer.parseInt(JOptionPane.showInputDialog("FIUBVHJDFUH")));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,"Inputted number is not an integer.");
                }
            }
        }
        if (keyDown(Keyboard.KEY_UP)) displacementY -= 1;
        if (keyDown(Keyboard.KEY_DOWN)) displacementY += 1;
        if (keyDown(Keyboard.KEY_LEFT)) displacementX -= 1;
        if (keyDown(Keyboard.KEY_RIGHT)) displacementX += 1;
    }

    private boolean keyDown(int key) {
        return (Keyboard.getEventKey() == key) && (Keyboard.getEventKeyState());
    }

    private void detectIfButtonDown() {
        if (Mouse.isButtonDown(0)) {
            for (int i = 0; i < TileType.TILE_TYPES.size(); i++) {
                int ceil = (int) Math.ceil(i / (3 * 11));
                for (int j = 0; j < editorUI.getMenu("Tiles" + ceil).getButtons().size(); j++) {
                    if (editorUI.getMenu("Tiles" + ceil).isButtonClicked(editorUI.getMenu("Tiles" + ceil).getButtons().get(j).getName())) {
                        tileSelected = TileType.TILE_IDS.get(editorUI.getMenu("Tiles" + ceil).getButtons().get(j).getName());
                    }
                }
            }
            map.setTile((int) Math.floor(((Mouse.getX() - displacementX) / TILE_SIZE)), (int) Math.floor(((HEIGHT - Mouse.getY() - 1 - displacementY) / TILE_SIZE)), tileSelected);
        }
    }

    public void update() {
        detectKey();
        detectIfButtonDown();
        draw();
    }

    private void draw() {
        drawQuadTex(background, 0, 0, WIDTH * 2, HEIGHT);
        map.draw();
        editorUI.draw();
    }


    private void createMenus() {
        int w = 3;
        int h = 11;
        for (int i = 0; i < TileType.TILE_TYPES.size(); i++) {
            int ceil = (int) Math.ceil(i / (w * h));
            if (editorUI.getMenu("Tiles" + ceil) == null) editorUI.createMenu("Tiles" + ceil, WIDTH - (64 * 3), 20, (64 * 3), HEIGHT - 64, 3, 11);;
            System.out.println(editorUI.getMenu("Tiles" + ceil) + ", " + ("Tiles" + ceil) + ", " + ceil + ", " + TileType.TILE_TYPES.size());
            editorUI.getMenu("Tiles" + ceil).addButton(TileType.TILE_TYPES.get(i).getId(), new Texture[]{LoadPNG("tiles/" + TileType.TILE_TYPES.get(i).getTexture()), TileType.TILE_TYPES.get(i).getOverlayTex()}, new int[]{0, TileType.TILE_TYPES.get(i).getOverlayTexRot()});
        }
    }

}
