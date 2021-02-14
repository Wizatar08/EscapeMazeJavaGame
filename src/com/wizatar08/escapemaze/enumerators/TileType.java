package com.wizatar08.escapemaze.enumerators;

import com.wizatar08.escapemaze.helpers.IDTypes;
import com.wizatar08.escapemaze.helpers.VariationID;
import org.newdawn.slick.opengl.Texture;

import java.util.*;
import static com.wizatar08.escapemaze.helpers.Drawer.*;

public enum TileType {
    NULL(new VariationID(IDTypes.TILE), "null", new Builder().isPassable()),
    METAL_WALL(new VariationID(IDTypes.TILE, "001", "00"), "metal_wall", new Builder().isSafeSpot()),
    METAL_WALL_B(new VariationID(IDTypes.TILE, "001", "11"), "metal_wall", new Builder().overlayTex(LoadPNG("tile_overlays/wall_side"))),
    METAL_WALL_L(new VariationID(IDTypes.TILE, "001", "12"), "metal_wall", new Builder().overlayTex(LoadPNG("tile_overlays/wall_side"), 90)),
    METAL_WALL_T(new VariationID(IDTypes.TILE, "001", "13"), "metal_wall", new Builder().overlayTex(LoadPNG("tile_overlays/wall_side"), 180)),
    METAL_WALL_R(new VariationID(IDTypes.TILE, "001", "14"), "metal_wall", new Builder().overlayTex(LoadPNG("tile_overlays/wall_side"), 270));

    // Initialize variables
    private String id;
    private String texture;
    private boolean isPassable;
    private boolean isSafeSpot;
    private Texture overlayTex;
    private int overlayTexRot;
    public static Map<String, TileType> TILE_IDS; // ArrayList to store all different tile ids

    TileType(VariationID id, String texture, Builder builder) {
        createIdMap();
        addToMap(id.getFullId(), this);
        this.id = id.getFullId();
        this.texture = texture;
        this.isPassable = builder.getIsPassable();
        this.isSafeSpot = builder.getIsSafeSpot();
        this.overlayTex = builder.getOverlayTex();
        this.overlayTexRot = builder.getOverlayTexRot();
    }

    private void createIdMap() {
        if (TILE_IDS == null) TILE_IDS = new HashMap<>();
    }

    private void addToMap(String id, TileType type) {
        TILE_IDS.put(id, type);
    }


    // Getters
    public String getId() {
        return id;
    }
    public String getTexture() {
        return texture;
    }
    public boolean isPassable() {
        return isPassable;
    }
    public boolean isSafeSpot() {
        return isSafeSpot;
    }
    public Texture getOverlayTex() {
        return overlayTex;
    }
    public int getOverlayTexRot() {
        return overlayTexRot;
    }



    /**
     * Tile builder class
     */
    private static class Builder {
        public static boolean isPassable;
        public static boolean isSafeSpot;
        public static Texture overlayTex;
        public static int overlayTexRot;


        /**
         * Builder constructor. Defines all variables to its default value.
         */
        private Builder() {
            isPassable = false;
            isSafeSpot = false;
            overlayTex = null;
            overlayTexRot = 0;
        }

        /**
         * Is passable tile
         */
        private Builder isPassable() {
            isPassable = true;
            return this;
        }

        /**
         * Is safety tile
         */
        private Builder isSafeSpot() {
            isSafeSpot = true;
            return this;
        }

        /**
         * Set overlay texture, if any
         */
        private Builder overlayTex(Texture tex) {
            overlayTex = tex;
            return this;
        }

        /**
         * Set and rotate overlay texture if needed. Rotates clockwise.
         */
        private Builder overlayTex(Texture tex, int rot) {
            overlayTex = tex;
            overlayTexRot = rot;
            return this;
        }




        // Getters
        public boolean getIsPassable() {
            return isPassable;
        }
        public boolean getIsSafeSpot() {
            return isSafeSpot;
        }
        public Texture getOverlayTex() {
            return overlayTex;
        }
        public int getOverlayTexRot() {
            return overlayTexRot;
        }
    }
}
