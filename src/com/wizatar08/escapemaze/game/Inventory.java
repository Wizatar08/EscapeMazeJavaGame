package com.wizatar08.escapemaze.game;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.game.game_entities.items.subclasses.DurabilityItem;
import com.wizatar08.escapemaze.helpers.visuals.Tex;

import java.util.ArrayList;

import static com.wizatar08.escapemaze.render.Renderer.*;

public class Inventory {
    private int slots, currentSelected;
    private ArrayList<Item> items;
    private Tex slotTex, selectedTex;
    private boolean hasPowerSource, hasGasSource;

    public Inventory(int slots) {
        this.slots = slots;
        items = new ArrayList<>();
        for (int i = 0; i < slots; i++) {
            items.add(i, null);
        }
        slotTex = new Tex("game/inventory_slot");
        selectedTex = new Tex("game/inventory_slot_selected");
        this.hasPowerSource = false;
        this.hasGasSource = false;
        this.currentSelected = 0;
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
        items.set(item, null);
    }

    public void draw() {
        for (int i = 0; i < slots; i++) {
            slotTex.draw(((float) WIDTH / 2) - (((float) TILE_SIZE / 2) * slots) + (i * 64), HEIGHT - 72);
        }
        selectedTex.draw(((float) WIDTH / 2) - (((float) TILE_SIZE / 2) * slots) + (currentSelected * 64) - 4, HEIGHT - 81);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) != null) {
                items.get(i).draw(((float) WIDTH / 2) - (((float) TILE_SIZE / 2) * slots) + (i * 64), HEIGHT - 72);
            }
        }
    }

    public void update() {
        boolean power = false, gas = false;
        for (Item item : items) {
            if (item != null) {
                item.update();
                if (item.isPowerSource()) {
                    power = true;
                }
                if (item.isGasSource()) {
                    gas = true;
                }
            }
        }
        hasPowerSource = power;
        hasGasSource = gas;
    }

    public void setSelected(int selected) {
        if (selected < slots) {
            this.currentSelected = selected;
        }
    }

    public int getItemIndex(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) != null && item == items.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasItem(ItemType item) {
        for (Item item1 : items) {
            if (item1 != null && item == item1.getType()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasItem(Item item) {
        for (Item item1 : items) {
            if (item1 != null && item == item1) {
                return true;
            }
        }
        return false;
    }

    public boolean hasItem(Class<? extends Item> itemClass) {
        for (Item item : items) {
            if (item != null && item.getClass() == itemClass) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRequiredItem() {
        for (Item item : items) {
            if (item != null && item.isRequired()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActiveItem(Class<? extends Item> itemClass) {
        for (Item item : items) {
            if (item != null && item.getClass() == itemClass && item.isRunning()) {
                return true;
            }
        }
        return false;
    }



    public Item getClosestNonOccupiedPowerSource() {
        return this.getClosestNonOccupiedPowerSource(0f);
    }

    public Item getClosestNonOccupiedPowerSource(float minimumPercentage) {
        for (Item item : items) {
            if (item instanceof DurabilityItem && item.isPowerSource() && !((DurabilityItem) item).isBeingUsed() && (((DurabilityItem) item).getDurabilityPercentage() * 100) >= minimumPercentage && ((DurabilityItem) item).hasDurability()) {
                return item;
            }
        }
        return null;
    }

    public Item getClosestNonOccupiedGasSource() {
        return this.getClosestNonOccupiedGasSource(0f);
    }

    public Item getClosestNonOccupiedGasSource(float minimumPercentage) {
        Item currItem = null;
        for (Item item : items) {
            if (item instanceof DurabilityItem && item.isGasSource() && !((DurabilityItem) item).isBeingUsed() && ((DurabilityItem) item).getDurabilityPercentage() >= minimumPercentage && ((DurabilityItem) item).hasDurability()) {
                currItem = item;
                break;
            }
        }
        return currItem;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    public boolean hasPowerSource() {
        return hasPowerSource;
    }
    public boolean hasGasSource() {
        return hasGasSource;
    }
    public int getCurrentSelected() {
        return currentSelected;
    }
    public Item getCurrentSelectedItem() {
        return items.get(currentSelected);
    }
}
