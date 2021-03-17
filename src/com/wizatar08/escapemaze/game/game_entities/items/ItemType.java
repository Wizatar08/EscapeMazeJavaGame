package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.helpers.IDTypes;
import com.wizatar08.escapemaze.helpers.VariationID;

import java.util.*;

public enum ItemType {
    NULL(new VariationID(IDTypes.ITEM, "000", "00"), "null", new Builder()),
    RED_KEY(new VariationID(IDTypes.ITEM, "001", "01"), "red_key", new Builder().keyType(ItemTypes.KeyTypes.RED)),
    ORANGE_KEY(new VariationID(IDTypes.ITEM, "001", "02"), "orange_key", new Builder().keyType(ItemTypes.KeyTypes.ORANGE)),
    YELLOW_KEY(new VariationID(IDTypes.ITEM, "001", "03"), "yellow_key", new Builder().keyType(ItemTypes.KeyTypes.YELLOW)),
    GREEN_KEY(new VariationID(IDTypes.ITEM, "001", "04"), "green_key", new Builder().keyType(ItemTypes.KeyTypes.GREEN)),
    BLUE_KEY(new VariationID(IDTypes.ITEM, "001", "05"), "blue_key", new Builder().keyType(ItemTypes.KeyTypes.BLUE)),
    DARK_BLUE_KEY(new VariationID(IDTypes.ITEM, "001", "06"), "dark_blue_key", new Builder().keyType(ItemTypes.KeyTypes.DARK_BLUE)),
    PURPLE_KEY(new VariationID(IDTypes.ITEM, "001", "07"), "purple_key", new Builder().keyType(ItemTypes.KeyTypes.PURPLE)),
    PINK_KEY(new VariationID(IDTypes.ITEM, "001", "08"), "pink_key", new Builder().keyType(ItemTypes.KeyTypes.PINK));

    // Initialize variables
    private String id;
    private String texture;
    private ItemTypes.KeyTypes keyType;
    private ItemTypes.Jewels jewelType;

    private static HashMap<String, ItemType> ITEM_IDS;

    ItemType(VariationID id, String texture, Builder builder) {
        createIdMapAndArrays();
        addToMap(id.getFullId(), this);
        this.id = id.getFullId();
        this.texture = texture;
        this.keyType = builder.getKeyType();
        this.jewelType = builder.getJewelType();
    }

    private void createIdMapAndArrays() {
        if (ITEM_IDS == null) ITEM_IDS = new HashMap<>();
    }

    private void addToMap(String id, ItemType type) {
        System.out.println("Added to map: " + id + ", " + type);
        ITEM_IDS.put(id, type);
    }

    public ItemTypes.KeyTypes getKeyType() {
        return keyType;
    }
    public ItemTypes.Jewels getJewelType() {
        return jewelType;
    }
    // Getters
    public String getId() {
        return id;
    }
    public String getTexture() {
        return texture;
    }

    public static ItemType getType(String type) {
        if (ITEM_IDS.get(type) != null) {
            return ITEM_IDS.get(type);
        } else {
            return NULL;
        }
    }

    /**
     * Tile builder class
     */
    private static class Builder {
        private static ItemTypes.KeyTypes keyType;
        private static ItemTypes.Jewels jewelType;

        private Builder() {
            keyType = ItemTypes.KeyTypes.NONE;
            jewelType = ItemTypes.Jewels.NONE;
        }

        public Builder keyType(ItemTypes.KeyTypes key) {
            keyType = key;
            return this;
        }

        public Builder jewel(ItemTypes.Jewels jewel) {
            jewelType = jewel;
            return this;
        }

        public ItemTypes.KeyTypes getKeyType() {
            return keyType;
        }

        public ItemTypes.Jewels getJewelType() {
            return jewelType;
        }
    }
}