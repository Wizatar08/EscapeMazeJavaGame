package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
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
    METAL_WALL_B_DOOR(new VariationID(IDTypes.TILE, "003", "01"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_door")}).safeSpot(EntityDetectDirection.DOWN)),
    METAL_WALL_L_DOOR(new VariationID(IDTypes.TILE, "003", "02"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_door")}, new int[]{90, 90}).safeSpot(EntityDetectDirection.LEFT)),
    METAL_WALL_T_DOOR(new VariationID(IDTypes.TILE, "003", "03"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_door")}, new int[]{180, 180}).safeSpot(EntityDetectDirection.UP)),
    METAL_WALL_R_DOOR(new VariationID(IDTypes.TILE, "003", "04"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_door")}, new int[]{270, 270}).safeSpot(EntityDetectDirection.RIGHT)),
    COMPUTER_DEF_FLOOR(new VariationID(IDTypes.TILE, "004", "01"), "default_floor", new Builder().isPassable().isSecurityComputer(true).overlayTex(new Texture[]{LoadPNG("tile_overlays/security_computers")}, new int[]{0})),
    COMPUTER_DEF_FLOOR_90(new VariationID(IDTypes.TILE, "004", "02"), "default_floor", new Builder().isPassable().isSecurityComputer(true).overlayTex(new Texture[]{LoadPNG("tile_overlays/security_computers")}, new int[]{90})),
    COMPUTER_DEF_FLOOR_180(new VariationID(IDTypes.TILE, "004", "03"), "default_floor", new Builder().isPassable().isSecurityComputer(true).overlayTex(new Texture[]{LoadPNG("tile_overlays/security_computers")}, new int[]{180})),
    COMPUTER_DEF_FLOOR_270(new VariationID(IDTypes.TILE, "004", "04"), "default_floor", new Builder().isPassable().isSecurityComputer(true).overlayTex(new Texture[]{LoadPNG("tile_overlays/security_computers")}, new int[]{270})),
    METAL_WALL_BE_DOOR(new VariationID(IDTypes.TILE, "005", "01"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_escape_door")}).escapeDoor().safeSpot(EntityDetectDirection.DOWN)),
    METAL_WALL_LE_DOOR(new VariationID(IDTypes.TILE, "005", "02"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_escape_door")}, new int[]{90, 90}).escapeDoor().safeSpot(EntityDetectDirection.LEFT)),
    METAL_WALL_TE_DOOR(new VariationID(IDTypes.TILE, "005", "03"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_escape_door")}, new int[]{180, 180}).escapeDoor().safeSpot(EntityDetectDirection.UP)),
    METAL_WALL_RE_DOOR(new VariationID(IDTypes.TILE, "005", "04"), "metal_wall", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/wall_side"), LoadPNG("tile_overlays/basic_escape_door")}, new int[]{270, 270}).escapeDoor().safeSpot(EntityDetectDirection.RIGHT)),
    DEFAULT_FLOOR_RED_LOCK(new VariationID(IDTypes.TILE, "006", "01"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/red_tint"), LoadPNG("tile_overlays/lock")}, new int[]{0, 0}).isPassable().unlockableBy(ItemType.RED_KEY, new Texture[]{LoadPNG("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_ORANGE_LOCK(new VariationID(IDTypes.TILE, "006", "02"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/orange_tint"), LoadPNG("tile_overlays/lock")}, new int[]{0, 0}).isPassable().unlockableBy(ItemType.ORANGE_KEY, new Texture[]{LoadPNG("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_YELLOW_LOCK(new VariationID(IDTypes.TILE, "006", "03"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/yellow_tint"), LoadPNG("tile_overlays/lock")}, new int[]{0, 0}).isPassable().unlockableBy(ItemType.YELLOW_KEY, new Texture[]{LoadPNG("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_GREEN_LOCK(new VariationID(IDTypes.TILE, "006", "04"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/green_tint"), LoadPNG("tile_overlays/lock")}, new int[]{0, 0}).isPassable().unlockableBy(ItemType.GREEN_KEY, new Texture[]{LoadPNG("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_BLUE_LOCK(new VariationID(IDTypes.TILE, "006", "05"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/blue_tint"), LoadPNG("tile_overlays/lock")}, new int[]{0, 0}).isPassable().unlockableBy(ItemType.BLUE_KEY, new Texture[]{LoadPNG("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_DARK_BLUE_LOCK(new VariationID(IDTypes.TILE, "006", "06"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/dark_blue_tint"), LoadPNG("tile_overlays/lock")}, new int[]{0, 0}).isPassable().unlockableBy(ItemType.DARK_BLUE_KEY, new Texture[]{LoadPNG("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_PURPLE_LOCK(new VariationID(IDTypes.TILE, "006", "07"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/purple_tint"), LoadPNG("tile_overlays/lock")}, new int[]{0, 0}).isPassable().unlockableBy(ItemType.PURPLE_KEY, new Texture[]{LoadPNG("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_PINK_LOCK(new VariationID(IDTypes.TILE, "006", "08"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/pink_tint"), LoadPNG("tile_overlays/lock")}, new int[]{0, 0}).isPassable().unlockableBy(ItemType.PINK_KEY, new Texture[]{LoadPNG("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_SECURE_PODIUM(new VariationID(IDTypes.TILE, "007", "01"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/default_activated_secure_podium")}, new int[]{0}).isPassable().laserSecure(new Texture[]{LoadPNG("tile_overlays/default_deactivated_secure_podium")}, new int[]{0})),
    AUTHORITY_DOOR(new VariationID(IDTypes.TILE, "008", "01"), "default_floor", new Builder().overlayTex(new Texture[]{LoadPNG("tile_overlays/vault_locked")}, new int[]{0}).isPassable().authorityLock(new int[]{3, 2, 1}, new Texture[]{LoadPNG("tile_overlays/vault_unlocked")}, new int[]{0}));

    /* IDEAS FOR TILES:
     * - Secure door: Must have multiple PASSES to unlock
     * - Pressure plate floor: Activates alarm when player hits it. Can be deactivated it by overriding pressure plate management computer or fly over it
     * - Recharge station: Can recharge battery there.
     * - Refuel station: Can refuel gas tank there.
     * - Movement detector: Activates alarm when player hits the tile. Can be deactivated by overriding movement detector management computer.
     * - Material detector: Activates alarm when you hold a required item when moving onto this tile. Can be deactivated by overriding material detector computer.
     * - Pressure plate mamanement computer: Managed pressure plates, can be overridden
     * - Movement detector computer: Manages movement detectors, can be overridden
     * - Material detector computer: Manages material detectors, can be overridden
     *
     * LEVEL THEMES:
     * - Level 1-20: Storage facility
     * - Level 21-30: Museum
     * - Level 31-40: Factory
     * - Level 41-50: Secret facility
     * - Level 51-60: Airport
     * - Level 61-...: Space
     */


    // Initialize variables
    private String id, texture;
    private boolean isPassable, isSafeSpot, isEscapeDoor, isSecurityComputer, isSecure, doorLocked, authorityLocked;
    private EntityDetectDirection safeSpot;
    private Texture overlayTex[], unlockedTexture[], deactivatedTexture[];
    private ItemType unlockableBy;
    private int unlockedTextureRots[], deactivatedTextureRots[], overlayTexRot[], cardPassesNeeded[];
    public static Map<String, TileType> TILE_IDS; // ArrayList to store all different tile ids
    public static ArrayList<TileType> TILE_TYPES;

    TileType(VariationID id, String texture, Builder builder) {
        createIdMapAndArrays();
        addToMap(id.getFullId(), this);
        this.id = id.getFullId();
        this.texture = texture;
        this.isPassable = builder.getIsPassable();
        this.isSafeSpot = builder.getIsSafeSpot();
        this.isEscapeDoor = builder.isEscapeDoor();
        this.safeSpot = builder.getSafeSpot();
        this.overlayTex = builder.getOverlayTex();
        this.overlayTexRot = builder.getOverlayTexRot();
        this.isSecurityComputer = builder.isSecurityComputer();
        this.unlockableBy = builder.unlockableBy();
        this.unlockedTexture = builder.getUnlockedTileTexture();
        this.unlockedTextureRots = builder.getUnlockedTileTextureRots();
        this.deactivatedTexture = builder.getDeactivatedTileTexture();
        this.deactivatedTextureRots = builder.getDeactivatedTileTextureRots();
        this.isSecure = builder.isSecure();
        this.doorLocked = builder.isDoorLocked();
        this.cardPassesNeeded = builder.getCardPassesNeeded();
        this.authorityLocked = builder.isAuthorityDoor();
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
    public EntityDetectDirection getSafeSpot() {
        return safeSpot;
    }
    public boolean isEscapeDoor() {
        return isEscapeDoor;
    }
    public Texture[] getOverlayTex() {
        return overlayTex;
    }
    public int[] getOverlayTexRot() {
        return overlayTexRot;
    }
    public boolean isSecurityComputer() {
        return isSecurityComputer;
    }
    public ItemType unlockableBy() {
        return unlockableBy;
    }
    public Texture[] getUnlockedTileTexture() {
        return unlockedTexture;
    }
    public int[] getUnlockedTileTextureRots() {
        return unlockedTextureRots;
    }
    public Texture[] getDeactivatedTileTexture() {
        return deactivatedTexture;
    }
    public int[] getDeactivatedTextureRots() {
        return deactivatedTextureRots;
    }
    public boolean isSecure() {
        return isSecure;
    }
    public boolean isDoorLocked() {
        return doorLocked;
    }
    public boolean isAuthorityDoor() {
        return authorityLocked;
    }
    public int[] cardPassesNeeded() {
        return cardPassesNeeded;
    }

    /**
     * Tile builder class
     */
    private static class Builder {
        public static EntityDetectDirection safeSpot;
        public static ItemType unlockableBy;
        public static Texture unlockedTex[], daTex[], overlayTex[];
        public static int unlockedTexRots[], daTexRots[], overlayTexRot[], cardPassesNeeded[];
        public static boolean isSecure, doorLocked, isSecurityComputer, escapeDoor, isSafeSpot, isPassable, authorityLocked;

        /**
         * Builder constructor. Defines all variables to its default value.
         */
        private Builder() {
            isPassable = false;
            isSafeSpot = false;
            safeSpot = EntityDetectDirection.NONE;
            escapeDoor = false;
            overlayTex = new Texture[]{LoadPNG("tiles/blank")};
            overlayTexRot = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            isSecurityComputer = false;
            unlockableBy = ItemType.NULL;
            unlockedTex = null;
            unlockedTexRots = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            daTex = null;
            daTexRots = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            isSecure = false;
            doorLocked = false;
            authorityLocked = false;
            cardPassesNeeded = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
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
        private Builder safeSpot(EntityDetectDirection direction) {
            isSafeSpot = true;
            safeSpot = direction;
            return this;
        }

        /**
         * Is door tile
         */
        private Builder escapeDoor() {
            escapeDoor = true;
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

        /**
         * Set if this door can be unlocked with an item
         */
        private Builder unlockableBy(ItemType key, Texture[] unlockedTileTexture, int[] unlockedTileTextureRots) {
            unlockableBy = key;
            unlockedTex = unlockedTileTexture;
            unlockedTexRots = unlockedTileTextureRots;
            doorLocked = true;
            return this;
        }

        /**
         * Set if this door is a authority
         */
        private Builder authorityLock(int[] cardPassesNeeded, Texture[] unlockedTileTexture, int[] unlockedTileTextureRots) {
            this.cardPassesNeeded = cardPassesNeeded;
            unlockedTex = unlockedTileTexture;
            unlockedTexRots = unlockedTileTextureRots;
            authorityLocked = true;
            return this;
        }

        /**
         * Set if this door can be unlocked
         */
        private Builder laserSecure(Texture[] deactivatedTileTexture, int[] deactivatedTileTextureRots) {
            daTex = deactivatedTileTexture;
            daTexRots = deactivatedTileTextureRots;
            isSecure = true;
            return this;
        }

        /**
         * Determine whether or not this is a security computer (can disable alarms)
         */
        private Builder isSecurityComputer(boolean is) {
            isSecurityComputer = is;
            return this;
        }

        // Getters
        public boolean getIsPassable() {
            return isPassable;
        }
        public boolean getIsSafeSpot() {
            return isSafeSpot;
        }
        public EntityDetectDirection getSafeSpot() {
            return safeSpot;
        }
        public boolean isEscapeDoor() {
            return escapeDoor;
        }
        public Texture[] getOverlayTex() {
            return overlayTex;
        }
        public int[] getOverlayTexRot() {
            return overlayTexRot;
        }
        public boolean isSecurityComputer() {
            return isSecurityComputer;
        }
        public ItemType unlockableBy() {
            return unlockableBy;
        }
        public Texture[] getUnlockedTileTexture() {
            return unlockedTex;
        }
        public int[] getUnlockedTileTextureRots() {
            return unlockedTexRots;
        }
        public Texture[] getDeactivatedTileTexture() {
            return daTex;
        }
        public int[] getDeactivatedTileTextureRots() {
            return daTexRots;
        }
        public boolean isSecure() {
            return isSecure;
        }
        public boolean isDoorLocked() {
            return doorLocked;
        }
        public boolean isAuthorityDoor() {
            return authorityLocked;
        }
        public int[] getCardPassesNeeded() {
            return cardPassesNeeded;
        }
    }
}
