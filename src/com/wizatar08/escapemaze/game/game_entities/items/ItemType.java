package com.wizatar08.escapemaze.game.game_entities.items;

import com.wizatar08.escapemaze.game.game_entities.items.subclasses.*;
import com.wizatar08.escapemaze.game.game_entities.items.subclasses.durability.RechargableBattery;
import com.wizatar08.escapemaze.game.game_entities.items.subclasses.durability.RefuelableGasCan;
import com.wizatar08.escapemaze.helpers.Drawer;
import com.wizatar08.escapemaze.helpers.IDTypes;
import com.wizatar08.escapemaze.helpers.VariationID;

import java.util.*;

public enum ItemType {
    // IDTYPE: 3
    NULL(new VariationID(IDTypes.ITEM, "000", "00"), "null", new Builder()),
    RED_KEY(new VariationID(IDTypes.ITEM, "001", "01"), "red_key", new Builder().weight(0.1f).className(Key.class)),
    ORANGE_KEY(new VariationID(IDTypes.ITEM, "001", "02"), "orange_key", new Builder().weight(0.1f).className(Key.class)),
    YELLOW_KEY(new VariationID(IDTypes.ITEM, "001", "03"), "yellow_key", new Builder().weight(0.1f).className(Key.class)),
    GREEN_KEY(new VariationID(IDTypes.ITEM, "001", "04"), "green_key", new Builder().weight(0.1f).className(Key.class)),
    BLUE_KEY(new VariationID(IDTypes.ITEM, "001", "05"), "blue_key", new Builder().weight(0.1f).className(Key.class)),
    DARK_BLUE_KEY(new VariationID(IDTypes.ITEM, "001", "06"), "dark_blue_key", new Builder().weight(0.1f).className(Key.class)),
    PURPLE_KEY(new VariationID(IDTypes.ITEM, "001", "07"), "purple_key", new Builder().weight(0.1f).className(Key.class)),
    PINK_KEY(new VariationID(IDTypes.ITEM, "001", "08"), "pink_key", new Builder().weight(0.1f).className(Key.class)),
    DIAMOND(new VariationID(IDTypes.ITEM, "002", "01"), "diamond", new Builder().weight(0.2f).required()),
    LARGE_DIAMOND(new VariationID(IDTypes.ITEM, "002", "02"), "large_diamond", new Builder().weight(0.65f).required()), // Must always be surrounded by lasers
    RUBY(new VariationID(IDTypes.ITEM, "002", "03"), "ruby", new Builder().weight(0.3f).required()),
    LASER_DEACTIVATOR(new VariationID(IDTypes.ITEM, "003", "01"), "laser_deactivator", new Builder().weight(0.15f).className(LaserDeactivator.class)),
    PASS_1(new VariationID(IDTypes.ITEM, "004", "01"), "pass_level_1", new Builder().weight(0.15f).pass(1).className(Pass.class)),
    PASS_2(new VariationID(IDTypes.ITEM, "004", "02"), "pass_level_2", new Builder().weight(0.15f).pass(2).className(Pass.class)),
    PASS_3(new VariationID(IDTypes.ITEM, "004", "03"), "pass_level_3", new Builder().weight(0.15f).pass(3).className(Pass.class)),
    BASIC_BATTERY(new VariationID(IDTypes.ITEM, "005", "01"), "battery", new Builder().weight(0.3f).powerSource().className(RechargableBattery.class, new Object[]{40, Drawer.LoadPNG("tile_overlays/battery_plugged")})),
    BASIC_GAS_CAN(new VariationID(IDTypes.ITEM, "006", "01"), "gas_can", new Builder().weight(0.4f).gasSource().className(RefuelableGasCan.class, new Object[]{40, Drawer.LoadPNG("tile_overlays/gas_can_refuel")})),
    BOOSTER(new VariationID(IDTypes.ITEM, "007", "01"), "booster", new Builder().weight(0.6f).className(Booster.class)),
    ADMIN_ACCESSOR(new VariationID(IDTypes.ITEM, "008", "01"), "admin_pass", new Builder().weight(0.2f)),
    HOVERING_DEVICE(new VariationID(IDTypes.ITEM, "009", "01"), "hovering_device", new Builder().weight(0.3f).className(HoveringDevice.class)),
    SMALL_EMP(new VariationID(IDTypes.ITEM, "010", "01"), "small_emp", new Builder().weight(0.2f).className(SmallEMP.class)),
    EMP(new VariationID(IDTypes.ITEM, "010", "02"), "emp", new Builder().weight(1.3f).className(EMP.class)),
    HACKED_COMPUTER(new VariationID(IDTypes.ITEM, "011", "01"), "hacked_computer", new Builder().weight(0.9f).className(HackedComputer.class));

    /* IDEAS FOR ITEMS:
     * - DONE: Pass: Can unlock vaults
     * - DONE*: Admin accessor: Can unlock special things. *Make sure to add other tiles/items that require the admin accessor
     * - DONE: Gas can: Can enable other items to be used. Has only certain amount of time before gas runs out.
     * - DONE: Simple battery: Can enable other items to be used Has only certain amount of time before electricity runs out.
     * - DONE: Hovering Device: Must have gas can to use. Can let the player fly, avoiding certain security issues (pressure plates)
     * - DONE: Booster: Must have extra battery to use. Increases player speed. Uses 2% battery per second.
     * - DONE: Small EMP: Must have 10% extra battery to use. Will shut down the nearest enemy robot or camera for 10 seconds.
     * - DONE: EMP: Must have 40% extra battery to use. Shuts down all enemy robots and cameras for 20 seconds (cannot move or sound alarm). Afterwards, alarm will activate. One time use.
     * - DONE: Hacked computer: Immediately turns off alarm no matter where you are. One time use. Requires admin accessor
     * - Smoke machine: Must have 30% gas can to use. This halves the vision of all robots for 20 seconds.
     * - Gas spot: Must have 40% gas can to use. Puts gas on the ground. The next enemy to step in it gets debuffed (loses half its speed and vision). Does not work on cameras or atoms bots. This clears the gas spot.
     * - Blockade: Item cannot be picked up, but instead pushed (This is considered an item because it shouldn't spawn as a tile). This prevents enemies from seeing you through it. If an enemy bumps into it, alarm will be set off.
     */


    // Initialize variables
    private String id;
    private String texture;
    private float weight;
    private boolean required, isPass, isAdminAccessor, power, gas;
    private Class<? extends Item> className;
    private int passLevel;
    private Object[] classArgs;

    private static HashMap<String, ItemType> ITEM_IDS;

    ItemType(VariationID id, String texture, Builder builder) {
        createIdMapAndArrays();
        addToMap(id.getFullId(), this);
        this.id = id.getFullId();
        this.texture = texture;
        this.weight = builder.getWeight();
        this.required = builder.getRequired();
        this.className = builder.getClassName();
        this.passLevel = builder.isPass();
        this.isPass = passLevel > 0;
        this.power = builder.isPower();
        this.gas = builder.isGas();
        this.isAdminAccessor = builder.isAdminAccessor();
        this.classArgs = builder.getClassArgs();
    }

    private void createIdMapAndArrays() {
        if (ITEM_IDS == null) ITEM_IDS = new HashMap<>();
    }

    private void addToMap(String id, ItemType type) {
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
    public Class<? extends Item> getClassname() {
        return className;
    }
    public int passLevel() {
        return passLevel;
    }
    public boolean isPass() {
        return isPass;
    }
    public boolean isAdminAccessor() {
        return isAdminAccessor;
    }
    public boolean isPower() {
        return power;
    }
    public boolean isGas() {
        return gas;
    }
    public Object[] getClassArgs() {
        return classArgs;
    }

    public static ItemType getType(String type) {
        if (ITEM_IDS.get(type) != null) {
            return ITEM_IDS.get(type);
        } else {
            return NULL;
        }
    }

    /**
     * Item builder class
     */
    private static class Builder {
        private float weight;
        private boolean required, isAdminAccessor, power, gas;
        private Class<? extends Item> className;
        private int passLevel;
        private Object[] classArgs;

        private Builder() {
            this.weight = 0.0f;
            this.required = false;
            this.className = null;
            this.passLevel = 0;
            this.power = false;
            this.gas = false;
            this.isAdminAccessor = false;
            this.classArgs = null;
        }

        public Builder weight(float weight) {
            this.weight = weight;
            return this;
        }

        public Builder required() {
            this.required = true;
            return this;
        }

        public Builder className(Class<? extends Item> clazz) {
            this.className = clazz;
            return this;
        }

        public Builder className(Class<? extends Item> clazz, Object[] args) {
            this.className = clazz;
            this.classArgs = args;
            return this;
        }

        public Builder pass(int level) {
            this.passLevel = level;
            return this;
        }

        public Builder powerSource() {
            this.power = true;
            return this;
        }

        public Builder gasSource() {
            this.gas = true;
            return this;
        }

        public Builder adminPass() {
            isAdminAccessor = true;
            return this;
        }

        public float getWeight() {
            return weight;
        }
        public boolean getRequired() {
            return required;
        }
        public Class<? extends Item> getClassName() {
            return className;
        }
        public int isPass() {
            return passLevel;
        }
        public boolean isGas() {
            return gas;
        }
        public boolean isPower() {
            return power;
        }
        public boolean isAdminAccessor() {
            return isAdminAccessor;
        }
        public Object[] getClassArgs() {
            return classArgs;
        }
    }
}