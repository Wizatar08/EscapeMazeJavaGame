package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.helpers.IDTypes;
import com.wizatar08.escapemaze.helpers.VariationID;

import java.util.*;

public enum ItemType {
    NULL(new VariationID(IDTypes.ITEM, "000", "00"), "null", new Builder()),
    RED_KEY(new VariationID(IDTypes.ITEM, "001", "01"), "red_key", new Builder().weight(0.1f)),
    ORANGE_KEY(new VariationID(IDTypes.ITEM, "001", "02"), "orange_key", new Builder().weight(0.1f)),
    YELLOW_KEY(new VariationID(IDTypes.ITEM, "001", "03"), "yellow_key", new Builder().weight(0.1f)),
    GREEN_KEY(new VariationID(IDTypes.ITEM, "001", "04"), "green_key", new Builder().weight(0.1f)),
    BLUE_KEY(new VariationID(IDTypes.ITEM, "001", "05"), "blue_key", new Builder().weight(0.1f)),
    DARK_BLUE_KEY(new VariationID(IDTypes.ITEM, "001", "06"), "dark_blue_key", new Builder().weight(0.1f)),
    PURPLE_KEY(new VariationID(IDTypes.ITEM, "001", "07"), "purple_key", new Builder().weight(0.1f)),
    PINK_KEY(new VariationID(IDTypes.ITEM, "001", "08"), "pink_key", new Builder().weight(0.1f)),
    DIAMOND(new VariationID(IDTypes.ITEM, "002", "01"), "diamond", new Builder().weight(0.2f).required()),
    LARGE_DIAMOND(new VariationID(IDTypes.ITEM, "002", "02"), "large_diamond", new Builder().weight(0.65f).required()),
    RUBY(new VariationID(IDTypes.ITEM, "002", "03"), "ruby", new Builder().weight(0.3f).required());

    // Initialize variables
    private String id;
    private String texture;
    private float weight;
    private boolean required;

    private static HashMap<String, ItemType> ITEM_IDS;

    ItemType(VariationID id, String texture, Builder builder) {
        createIdMapAndArrays();
        addToMap(id.getFullId(), this);
        this.id = id.getFullId();
        this.texture = texture;
        this.weight = builder.getWeight();
        this.required = builder.getRequired();
    }

    private void createIdMapAndArrays() {
        if (ITEM_IDS == null) ITEM_IDS = new HashMap<>();
    }

    private void addToMap(String id, ItemType type) {
        System.out.println("Added to map: " + id + ", " + type);
        ITEM_IDS.put(id, type);
    }
    // Getters
    public String getId() {
        return id;
    }
    public String getTexture() {
        return texture;
    }

    public float getWeight() {
        return weight;
    }
    public boolean isRequired() {
        return required;
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
        private float weight;
        private boolean required;

        private Builder() {
            this.weight = 0.0f;
            this.required = false;
        }

        public Builder weight(float weight) {
            this.weight = weight;
            return this;
        }

        public Builder required() {
            this.required = true;
            return this;
        }

        public float getWeight() {
            return weight;
        }
        public boolean getRequired() {
            return required;
        }
    }
}