package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.helpers.IDTypes;
import com.wizatar08.escapemaze.helpers.VariationID;
import org.newdawn.slick.opengl.Texture;

import java.util.*;
import static com.wizatar08.escapemaze.helpers.Drawer.*;

public enum TileType {
    NULL(new VariationID(IDTypes.TILE), "null", new Builder().isPassable()),
    METAL_WALL(new VariationID(IDTypes.TILE, "001", "00"), "metal_wall", new Builder()),
    METAL_WALL_B(new VariationID(IDTypes.TILE, "001", "11"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side")})),
    METAL_WALL_L(new VariationID(IDTypes.TILE, "001", "12"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side")}, new int[]{90})),
    METAL_WALL_T(new VariationID(IDTypes.TILE, "001", "13"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side")}, new int[]{180})),
    METAL_WALL_R(new VariationID(IDTypes.TILE, "001", "14"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side")}, new int[]{270})),
    METAL_WALL_BL(new VariationID(IDTypes.TILE, "001", "15"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_edge")})),
    METAL_WALL_LT(new VariationID(IDTypes.TILE, "001", "16"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_edge")}, new int[]{90})),
    METAL_WALL_TR(new VariationID(IDTypes.TILE, "001", "17"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_edge")}, new int[]{180})),
    METAL_WALL_RB(new VariationID(IDTypes.TILE, "001", "18"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_edge")}, new int[]{270})),
    METAL_WALL_BL_C(new VariationID(IDTypes.TILE, "001", "19"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_corner")})),
    METAL_WALL_LT_C(new VariationID(IDTypes.TILE, "001", "20"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_corner")}, new int[]{90})),
    METAL_WALL_TR_C(new VariationID(IDTypes.TILE, "001", "21"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_corner")}, new int[]{180})),
    METAL_WALL_RB_C(new VariationID(IDTypes.TILE, "001", "22"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_corner")}, new int[]{270})),
    METAL_WALL_TB(new VariationID(IDTypes.TILE, "001", "23"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_double")}, new int[]{0})),
    METAL_WALL_LR(new VariationID(IDTypes.TILE, "001", "24"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_double")}, new int[]{90})),
    METAL_WALL_ET(new VariationID(IDTypes.TILE, "001", "25"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_end")}, new int[]{0})),
    METAL_WALL_ER(new VariationID(IDTypes.TILE, "001", "26"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_end")}, new int[]{90})),
    METAL_WALL_EB(new VariationID(IDTypes.TILE, "001", "27"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_end")}, new int[]{180})),
    METAL_WALL_EL(new VariationID(IDTypes.TILE, "001", "28"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_end")}, new int[]{270})),
    METAL_WALL_BTC(new VariationID(IDTypes.TILE, "001", "29"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_one_corner")}, new int[]{0})),
    METAL_WALL_BRC(new VariationID(IDTypes.TILE, "001", "30"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_one_corner_2")}, new int[]{0})),
    METAL_WALL_LTC(new VariationID(IDTypes.TILE, "001", "31"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_one_corner")}, new int[]{90})),
    METAL_WALL_LBC(new VariationID(IDTypes.TILE, "001", "32"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_one_corner_2")}, new int[]{90})),
    METAL_WALL_TRC(new VariationID(IDTypes.TILE, "001", "33"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_one_corner")}, new int[]{180})),
    METAL_WALL_TLC(new VariationID(IDTypes.TILE, "001", "34"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_one_corner_2")}, new int[]{180})),
    METAL_WALL_RBC(new VariationID(IDTypes.TILE, "001", "35"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_one_corner")}, new int[]{270})),
    METAL_WALL_RTC(new VariationID(IDTypes.TILE, "001", "36"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_one_corner_2")}, new int[]{270})),
    METAL_WALL_BWT(new VariationID(IDTypes.TILE, "001", "37"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_two_corners")}, new int[]{0})),
    METAL_WALL_LWR(new VariationID(IDTypes.TILE, "001", "38"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_two_corners")}, new int[]{90})),
    METAL_WALL_TWB(new VariationID(IDTypes.TILE, "001", "39"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_two_corners")}, new int[]{180})),
    METAL_WALL_RWL(new VariationID(IDTypes.TILE, "001", "40"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_two_corners")}, new int[]{270})),
    METAL_WALL_CB(new VariationID(IDTypes.TILE, "001", "41"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_two_corners_blank")}, new int[]{0})),
    METAL_WALL_CL(new VariationID(IDTypes.TILE, "001", "42"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_two_corners_blank")}, new int[]{90})),
    METAL_WALL_CT(new VariationID(IDTypes.TILE, "001", "43"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_two_corners_blank")}, new int[]{180})),
    METAL_WALL_CR(new VariationID(IDTypes.TILE, "001", "44"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_two_corners_blank")}, new int[]{270})),
    METAL_WALL_BTL(new VariationID(IDTypes.TILE, "001", "45"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_three_corners")}, new int[]{0})),
    METAL_WALL_LTR(new VariationID(IDTypes.TILE, "001", "46"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_three_corners")}, new int[]{90})),
    METAL_WALL_TBR(new VariationID(IDTypes.TILE, "001", "47"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_three_corners")}, new int[]{180})),
    METAL_WALL_RBL(new VariationID(IDTypes.TILE, "001", "48"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_three_corners")}, new int[]{270})),
    METAL_WALL_AC(new VariationID(IDTypes.TILE, "001", "49"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_four_corners")}, new int[]{0})),
    METAL_WALL_FBL(new VariationID(IDTypes.TILE, "001", "50"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_corner_edge")}, new int[]{0})),
    METAL_WALL_FTL(new VariationID(IDTypes.TILE, "001", "51"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_corner_edge")}, new int[]{90})),
    METAL_WALL_FTR(new VariationID(IDTypes.TILE, "001", "52"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_corner_edge")}, new int[]{180})),
    METAL_WALL_FBR(new VariationID(IDTypes.TILE, "001", "53"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side_corner_edge")}, new int[]{270})),
    METAL_WALL_FULL(new VariationID(IDTypes.TILE, "001", "54"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_full")}, new int[]{0})),
    DEFAULT_FLOOR(new VariationID(IDTypes.TILE, "002", "00"), "default_floor", new Builder().isPassable()),
    METAL_WALL_B_DOOR(new VariationID(IDTypes.TILE, "003", "01"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_door")}).safeSpot(SafeSpots.DOWN)),
    METAL_WALL_L_DOOR(new VariationID(IDTypes.TILE, "003", "02"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_door")}, new int[]{90, 90}).safeSpot(SafeSpots.LEFT)),
    METAL_WALL_T_DOOR(new VariationID(IDTypes.TILE, "003", "03"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_door")}, new int[]{180, 180}).safeSpot(SafeSpots.UP)),
    METAL_WALL_R_DOOR(new VariationID(IDTypes.TILE, "003", "04"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_door")}, new int[]{270, 270}).safeSpot(SafeSpots.RIGHT));

    // Initialize variables
    private String id;
    private String texture;
    private boolean isPassable;
    private boolean isSafeSpot;
    private SafeSpots safeSpot;
    private Texture[] overlayTex;
    private int[] overlayTexRot;
    public static Map<String, TileType> TILE_IDS; // ArrayList to store all different tile ids
    public static ArrayList<TileType> TILE_TYPES;

    TileType(VariationID id, String texture, Builder builder) {
        createIdMapAndArrays();
        addToMap(id.getFullId(), this);
        this.id = id.getFullId();
        this.texture = texture;
        this.isPassable = builder.getIsPassable();
        this.isSafeSpot = builder.getIsSafeSpot();
        this.safeSpot = builder.getSafeSpot();
        this.overlayTex = builder.getOverlayTex();
        this.overlayTexRot = builder.getOverlayTexRot();
    }

    private void createIdMapAndArrays() {
        if (TILE_IDS == null) TILE_IDS = new HashMap<>();
        if (TILE_TYPES == null) TILE_TYPES = new ArrayList<TileType>();
    }

    private void addToMap(String id, TileType type) {
        TILE_IDS.put(id, type);
        TILE_TYPES.add(type);
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
    public SafeSpots getSafeSpot() {
        return safeSpot;
    }
    public Texture[] getOverlayTex() {
        return overlayTex;
    }
    public int[] getOverlayTexRot() {
        return overlayTexRot;
    }



    /**
     * Tile builder class
     */
    private static class Builder {
        public static boolean isPassable;
        public static boolean isSafeSpot;
        public static SafeSpots safeSpot;
        public static Texture[] overlayTex;
        public static int overlayTexRot[];


        /**
         * Builder constructor. Defines all variables to its default value.
         */
        private Builder() {
            isPassable = false;
            isSafeSpot = false;
            safeSpot = SafeSpots.NONE;
            overlayTex = new Texture[]{LoadPNG("tiles/blank")};
            overlayTexRot = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
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
        private Builder safeSpot(SafeSpots direction) {
            isSafeSpot = true;
            safeSpot = direction;
            return this;
        }

        /**
         * Set overlay texture, if any
         * Only 16 of these are allowed, otherwise game will throw ArrayIndexOutOfBoundsException
         */
        private Builder overlayTex(Texture[] tex) {
            overlayTex = tex;
            return this;
        }

        /**
         * Set and rotate overlay texture if needed. Rotates clockwise.
         * Only 16 of these are allowed, otherwise game will throw ArrayIndexOutOfBoundsException
         */
        private Builder overlayTex(Texture[] tex, int[] rot) {
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
        public SafeSpots getSafeSpot() {
            return safeSpot;
        }
        public Texture[] getOverlayTex() {
            return overlayTex;
        }
        public int[] getOverlayTexRot() {
            return overlayTexRot;
        }
    }
}
