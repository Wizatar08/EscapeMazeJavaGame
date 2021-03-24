package com.wizatar08.escapemaze.game;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.helpers.Drawer;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class Inventory {
    private int slots, nextAvailable;
    private ArrayList<Item> items;
    private Texture slotTex;

    public Inventory(int slots) {
        this.slots = slots;
        items = new ArrayList<>();
        for (int i = 0; i < slots; i++) {
            items.add(i, null);
        }
        slotTex = Drawer.LoadPNG("game/inventory_slot");
        nextAvailable = 0;
    }

    public boolean canAdd() {
        int nullSlots = 0;
        for (Item item : items) {
            if (item == null) {
                nullSlots++;
            }
        }
        return nullSlots != 0;
    }

    public boolean add(Item item) {
        if (canAdd()) {
            nextAvailable++;
            int ind = 0;
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) == null) {
                    ind = i;
                    break;
                }
            }
            items.set(ind, item);
            return true;
        }
        return false;
    }

    public void remove(int item) {
        nextAvailable--;
        items.set(item, null);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void draw() {
        //System.out.println(items.get(0) + ", " + items.get(1) + ", " + items.get(2) + ", " + items.get(3) + ", " + items.get(4));
        for (int i = 0; i < slots; i++) {
            Drawer.drawQuadTex(slotTex, ((float) WIDTH / 2) - (((float) TILE_SIZE / 2) * slots) + (i * 64), HEIGHT - 72);
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) != null) {
                Drawer.drawQuadTex(items.get(i).getTexture(), ((float) WIDTH / 2) - (((float) TILE_SIZE / 2) * slots) + (i * 64), HEIGHT - 72);
            }
        }
    }

    public void update() {
        for (Item item : items) {
            if (item != null) {
                item.update();
            }
        }
    }

    public int getMaxSlots() {
        return slots;
    }

    public int nextAvailable() {
        return nextAvailable;
    }
}
