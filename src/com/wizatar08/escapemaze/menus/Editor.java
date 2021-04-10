package com.wizatar08.escapemaze.menus;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.Lang;
import com.wizatar08.escapemaze.helpers.TextBlock;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.map.TileMap;
import com.wizatar08.escapemaze.helpers.ExternalMapHandler;
import com.wizatar08.escapemaze.helpers.ui.UI;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.swing.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class Editor {
    // Initialize variables
    private Tex background;
    private TileMap map;
    private UI editorUI;
    private ArrayList<UI.Menu> menus;
    public static int displacementX;
    public static int displacementY;
    private TileType tileSelected;
    private int menuIndex;
    private boolean isSelectingEnemy, isPlacingItem;
    private ArrayList<Item> items;
    private int[][] positions;
    private int index;
    private String id;
    private boolean buttonPressed;
    private TextBlock status;

    public Editor() {
        editorUI = new UI();
        menus = new ArrayList<>();
        tileSelected = null;
        menuIndex = 0;
        isSelectingEnemy = false;
        positions = new int[1024][2];
        buttonPressed = false;
        createMenus();
        try {
            map = ExternalMapHandler.LoadMap(null, "map_default");
        } catch (NullPointerException e) {
            map = new TileMap(null);
        }
        displacementX = 0;
        displacementY = 0;
        background = new Tex("backgrounds/main_menu");
        items = new ArrayList<>();
        status = new TextBlock(editorUI, "Status", "", 8, 8);
    }

    private void changeMapSize(int width, int height) {
        TileMap oldMap = map;
        map = new TileMap(null, width, height);
        for(int i = 0; i < map.getMapAsArray().length; i++){
            for(int j = 0; j < map.getMapAsArray()[i].length; j++){
                map.setTile(i, j, TileType.DEFAULT_FLOOR);
                try {
                    map.setTile(i, j, oldMap.getTile(i, j).getType());
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    private void detectKey() {
        while (Keyboard.next()) {
            if (keyDown(Keyboard.KEY_S)) {
                ExternalMapHandler.SaveMap("map", map);
            }
            if (keyDown(Keyboard.KEY_LSHIFT)) {
                String mapName = JOptionPane.showInputDialog(Lang.get("editor.load_map.popup"));
                TileMap newMap = ExternalMapHandler.LoadMap(null, mapName);
                if (newMap != null) {
                    map = newMap;
                } else {
                    JOptionPane.showMessageDialog(null, Lang.get("editor.load_map.error") + mapName + ".wtremm");
                }
            }
            if (keyDown(Keyboard.KEY_EQUALS)) {
                try {
                    int size = Integer.parseInt(JOptionPane.showInputDialog(Lang.get("editor.change_map.width.popup")));
                    if (size < 1) {
                        JOptionPane.showMessageDialog(null, Lang.get("editor.change_map.width.error.less"));
                    } else if (size > 48) {
                        JOptionPane.showMessageDialog(null, Lang.get("editor.change_map.width.error.more"));
                    } else changeMapSize(size, map.getTilesHigh());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, Lang.get("editor.change_map.width.error.nan"));
                }
            }
            if (keyDown(Keyboard.KEY_MINUS)) {
                try {
                    int size = Integer.parseInt(JOptionPane.showInputDialog(Lang.get("editor.change_map.height.popup")));
                    if (size < 1) {
                        JOptionPane.showMessageDialog(null, Lang.get("editor.change_map.height.error.less"));
                    } else if (size > 48) {
                        JOptionPane.showMessageDialog(null, Lang.get("editor.change_map.height.error.more"));
                    } else changeMapSize(map.getTilesWide(), size);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,Lang.get("editor.change_map.height.error.nan"));
                }
            }
            if (keyDown(Keyboard.KEY_E)) {
                if (!isPlacingItem) {
                    if (!isSelectingEnemy) {
                        id = JOptionPane.showInputDialog(Lang.get("editor.add_enemy.enter_id"));
                        index = 0;
                        isSelectingEnemy = true;
                    } else {
                        writeCoords();
                        JOptionPane.showMessageDialog(null, Lang.get("editor.add_enemy.save"));
                        isSelectingEnemy = false;
                    }
                }
            }
            if (keyDown(Keyboard.KEY_I)) {
                if (!isSelectingEnemy) {
                    if (!isPlacingItem) {
                        id = JOptionPane.showInputDialog(Lang.get("editor.add_item.enter_id"));
                        isPlacingItem = true;
                    } else {
                        JOptionPane.showMessageDialog(null, Lang.get("editor.add_item.save"));
                        writeItems();
                        isPlacingItem = false;
                    }
                }
            }
            if (keyDown(Keyboard.KEY_D)) {
                menuIndex++;
                int max = 0;
                for (int i = 0; i < editorUI.getMenuList().size(); i++) {
                    max++;
                }
                if (menuIndex >= max) {
                    menuIndex = 0;
                }
            }
        }
        if (keyDown(Keyboard.KEY_UP)) displacementY += 6;
        if (keyDown(Keyboard.KEY_DOWN)) displacementY -= 6;
        if (keyDown(Keyboard.KEY_LEFT)) displacementX += 6;
        if (keyDown(Keyboard.KEY_RIGHT)) displacementX -= 6;
    }

    private boolean keyDown(int key) {
        return (Keyboard.getEventKey() == key) && (Keyboard.getEventKeyState());
    }

    private void detectIfButtonDown() {
        boolean isOverButton = false;
        if (Mouse.isButtonDown(0)) {
            if (isSelectingEnemy) {
                if (!buttonPressed) {
                    buttonPressed = true;
                    int xPlace = (int) Math.floor((Mouse.getX() - displacementX) / TILE_SIZE);
                    int yPlace = (int) Math.floor(((HEIGHT - Mouse.getY() - 1 - displacementY) / TILE_SIZE));
                    positions[index][0] = xPlace;
                    positions[index][1] = yPlace;
                    index++;
                }
            } else if (isPlacingItem) {
                if (!buttonPressed) {
                    buttonPressed = true;
                    int xPlace = (int) Math.floor(Mouse.getX() - displacementX);
                    int yPlace = (int) Math.floor(HEIGHT - Mouse.getY() - 1 - displacementY);
                    boolean itemAlreadyThere = false;
                    int ind = 0;
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).getX() == (int) Math.floor(xPlace / TILE_SIZE) * TILE_SIZE && items.get(i).getY() == (int) Math.floor(yPlace / TILE_SIZE) * TILE_SIZE) {
                            ind = i;
                            itemAlreadyThere = true;
                        }
                    }
                    if (itemAlreadyThere) {
                        items.remove(ind);
                    } else {
                        System.out.println(ItemType.getType(id) + ", " + id);
                        items.add(new Item(null, ItemType.getType(id), new Tex("game/items/" + ItemType.getType(id).getTexture()), null,(int) Math.floor(xPlace / TILE_SIZE) * TILE_SIZE, (int) Math.floor(yPlace / TILE_SIZE) * TILE_SIZE));
                    }
                }
            } else {
                for (int i = 0; i < TileType.TILE_TYPES.size(); i++) {
                    int ceil = (int) Math.ceil(i / (3 * 11));
                    for (int j = 0; j < editorUI.getMenu("Tiles" + ceil).getButtons().size(); j++) {
                        if (editorUI.getMenu("Tiles" + ceil).isButtonClicked(editorUI.getMenu("Tiles" + ceil).getButtons().get(j).getName())) {
                            tileSelected = TileType.TILE_IDS.get(editorUI.getMenu("Tiles" + ceil).getButtons().get(j).getName());
                            isOverButton = true;
                        }
                    }
                }
                if (!isOverButton && tileSelected != null) {
                    map.setTile((int) Math.floor(((Mouse.getX() - displacementX) / TILE_SIZE)), (int) Math.floor(((HEIGHT - Mouse.getY() - 1 - displacementY) / TILE_SIZE)), tileSelected);
                }
            }
        } else {
            buttonPressed = false;
        }
    }

    private void showMenu() {
        for (int i = 0; i < editorUI.getMenuList().size(); i++) {
            if (editorUI.getMenu(editorUI.getMenuList().get(i).getName()).getName().equals("Tiles" + menuIndex)) {
                editorUI.getMenu(editorUI.getMenuList().get(i).getName()).showMenu(true);
            } else {
                editorUI.getMenu(editorUI.getMenuList().get(i).getName()).showMenu(false);
            }
        }
    }

    private void writeCoords() {
        try {
            File file = new File("enemyFile.json");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("{");
            bw.newLine();
            bw.write("  \"id\": " + id + ",");
            bw.newLine();
            String coords = "[";
            int[][] newArray = new int[index][2];
            for (int i = 0; i < index; i++) {
                newArray[i][0] = positions[i][0];
                newArray[i][1] = positions[i][1];
                coords += ("[" + newArray[i][0] + ", " + newArray[i][1] + "]");
                if (i != (index - 1)) {
                    coords += ", ";
                }
            }
            coords += "]";
            bw.write("  \"paths\": " + coords);
            bw.newLine();
            bw.write("}");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeItems() {
        String fileString = "";
        try {
            File file = new File("itemFile.json");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            fileString += "{\n";
            fileString += "  \"items\": [\n";
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                fileString += "    {\n";
                fileString += "      \"id\": " + item.getId() + ",\n";
                fileString += "      \"x\": " + item.getX() + ",\n";
                fileString += "      \"y\": " + item.getY() + "\n";
                fileString += "    }";
                if (i < items.size() - 1) {
                    fileString += ",\n";
                }
            }
            fileString += "\n  ]";
            bw.write(fileString);
            bw.newLine();
            bw.write("}");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPlacement() {
        Tex t = new Tex("tiles/selectors/safe_space_selector");
        t.draw((int) Math.floor((Mouse.getX() - displacementX) / TILE_SIZE) * TILE_SIZE + displacementX, (int) Math.floor(((HEIGHT - Mouse.getY() - 1 - displacementY) / TILE_SIZE)) * TILE_SIZE +  displacementY);
    }

    public void update() {
        detectKey();
        detectIfButtonDown();
        showMenu();
        draw();
        if (isSelectingEnemy || isPlacingItem) {
            setPlacement();
        }
    }

    private void draw() {
        background.draw(0, 0);
        map.draw();
        editorUI.draw();
        for (Item item : items) {
            item.getTexture().draw(item.getX() + displacementX, item.getY() + displacementY);
        }
        status.draw();
        if (isPlacingItem) {
            status.setChars(Lang.get("editor.status.status_text") + Lang.get("editor.status.items"));
        } else if (isSelectingEnemy) {
            status.setChars(Lang.get("editor.status.status_text") + Lang.get("editor.status.enemy"));
        } else {
            status.setChars(Lang.get("editor.status.status_text") + Lang.get("editor.status.tiles"));
        }
    }


    private void createMenus() {
        int w = 3;
        int h = 11;
        for (int i = 0; i < TileType.TILE_TYPES.size(); i++) {
            int ceil = (int) Math.ceil(i / (w * h));
            if (editorUI.getMenu("Tiles" + ceil) == null) editorUI.createMenu("Tiles" + ceil, WIDTH - (int) (64 * 3.3), 20, 220, 704, 3, 11);
            Tex[] textures = new Tex[TileType.TILE_TYPES.get(i).getOverlayTex().length + 1];
            int ind = 0;
            textures[0] = TileType.TILE_TYPES.get(i).getTexture();
            for (int j = 0; j < TileType.TILE_TYPES.get(i).getOverlayTex().length; j++) {
                textures[j + 1] = TileType.TILE_TYPES.get(i).getOverlayTex()[j];
            }
            editorUI.getMenu("Tiles" + ceil).addButton(TileType.TILE_TYPES.get(i).getId(), textures, TileType.TILE_TYPES.get(i).getOverlayTexRot());
        }
    }

}
