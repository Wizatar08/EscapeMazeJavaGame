package com.wizatar08.escapemaze.map;

import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.IDTypes;
import com.wizatar08.escapemaze.helpers.VariationID;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import com.wizatar08.escapemaze.map.tile_types.*;
import com.wizatar08.escapemaze.render.Renderer;

import java.util.*;

public enum TileType {
    NULL(new VariationID(IDTypes.TILE), new Tex("null"), new Builder().isPassable()),
    METAL_WALL(new VariationID(IDTypes.TILE, "001", "00"), new Tex("tiles/metal_wall"), new Builder()),
    METAL_WALL_11(new VariationID(IDTypes.TILE, "001", "11"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side")})),
    METAL_WALL_12(new VariationID(IDTypes.TILE, "001", "12"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side")}, new int[]{90})),
    METAL_WALL_13(new VariationID(IDTypes.TILE, "001", "13"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side")}, new int[]{180})),
    METAL_WALL_14(new VariationID(IDTypes.TILE, "001", "14"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side")}, new int[]{270})),
    METAL_WALL_15(new VariationID(IDTypes.TILE, "001", "15"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_edge")})),
    METAL_WALL_16(new VariationID(IDTypes.TILE, "001", "16"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_edge")}, new int[]{90})),
    METAL_WALL_17(new VariationID(IDTypes.TILE, "001", "17"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_edge")}, new int[]{180})),
    METAL_WALL_18(new VariationID(IDTypes.TILE, "001", "18"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_edge")}, new int[]{270})),
    METAL_WALL_19(new VariationID(IDTypes.TILE, "001", "19"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_corner")})),
    METAL_WALL_20(new VariationID(IDTypes.TILE, "001", "20"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_corner")}, new int[]{90})),
    METAL_WALL_21(new VariationID(IDTypes.TILE, "001", "21"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_corner")}, new int[]{180})),
    METAL_WALL_22(new VariationID(IDTypes.TILE, "001", "22"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_corner")}, new int[]{270})),
    METAL_WALL_23(new VariationID(IDTypes.TILE, "001", "23"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_double")}, new int[]{0})),
    METAL_WALL_24(new VariationID(IDTypes.TILE, "001", "24"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_double")}, new int[]{90})),
    METAL_WALL_25(new VariationID(IDTypes.TILE, "001", "25"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_end")}, new int[]{0})),
    METAL_WALL_26(new VariationID(IDTypes.TILE, "001", "26"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_end")}, new int[]{90})),
    METAL_WALL_27(new VariationID(IDTypes.TILE, "001", "27"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_end")}, new int[]{180})),
    METAL_WALL_28(new VariationID(IDTypes.TILE, "001", "28"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_end")}, new int[]{270})),
    METAL_WALL_29(new VariationID(IDTypes.TILE, "001", "29"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_one_corner")}, new int[]{0})),
    METAL_WALL_30(new VariationID(IDTypes.TILE, "001", "30"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_one_corner_2")}, new int[]{0})),
    METAL_WALL_31(new VariationID(IDTypes.TILE, "001", "31"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_one_corner")}, new int[]{90})),
    METAL_WALL_32(new VariationID(IDTypes.TILE, "001", "32"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_one_corner_2")}, new int[]{90})),
    METAL_WALL_33(new VariationID(IDTypes.TILE, "001", "33"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_one_corner")}, new int[]{180})),
    METAL_WALL_34(new VariationID(IDTypes.TILE, "001", "34"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_one_corner_2")}, new int[]{180})),
    METAL_WALL_35(new VariationID(IDTypes.TILE, "001", "35"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_one_corner")}, new int[]{270})),
    METAL_WALL_36(new VariationID(IDTypes.TILE, "001", "36"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_one_corner_2")}, new int[]{270})),
    METAL_WALL_37(new VariationID(IDTypes.TILE, "001", "37"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_two_corners")}, new int[]{0})),
    METAL_WALL_38(new VariationID(IDTypes.TILE, "001", "38"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_two_corners")}, new int[]{90})),
    METAL_WALL_39(new VariationID(IDTypes.TILE, "001", "39"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_two_corners")}, new int[]{180})),
    METAL_WALL_40(new VariationID(IDTypes.TILE, "001", "40"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_two_corners")}, new int[]{270})),
    METAL_WALL_41(new VariationID(IDTypes.TILE, "001", "41"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_two_corners_blank")}, new int[]{0})),
    METAL_WALL_42(new VariationID(IDTypes.TILE, "001", "42"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_two_corners_blank")}, new int[]{90})),
    METAL_WALL_43(new VariationID(IDTypes.TILE, "001", "43"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_two_corners_blank")}, new int[]{180})),
    METAL_WALL_44(new VariationID(IDTypes.TILE, "001", "44"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_two_corners_blank")}, new int[]{270})),
    METAL_WALL_45(new VariationID(IDTypes.TILE, "001", "45"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_three_corners")}, new int[]{0})),
    METAL_WALL_46(new VariationID(IDTypes.TILE, "001", "46"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_three_corners")}, new int[]{90})),
    METAL_WALL_47(new VariationID(IDTypes.TILE, "001", "47"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_three_corners")}, new int[]{180})),
    METAL_WALL_48(new VariationID(IDTypes.TILE, "001", "48"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_three_corners")}, new int[]{270})),
    METAL_WALL_49(new VariationID(IDTypes.TILE, "001", "49"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_four_corners")}, new int[]{0})),
    METAL_WALL_50(new VariationID(IDTypes.TILE, "001", "50"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_corner_edge")}, new int[]{0})),
    METAL_WALL_51(new VariationID(IDTypes.TILE, "001", "51"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_corner_edge")}, new int[]{90})),
    METAL_WALL_52(new VariationID(IDTypes.TILE, "001", "52"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_corner_edge")}, new int[]{180})),
    METAL_WALL_53(new VariationID(IDTypes.TILE, "001", "53"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side_corner_edge")}, new int[]{270})),
    METAL_WALL_54(new VariationID(IDTypes.TILE, "001", "54"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_full")}, new int[]{0})),
    DEFAULT_FLOOR(new VariationID(IDTypes.TILE, "002", "00"), new Tex("tiles/default_floor"), new Builder().isPassable()),
    METAL_WALL_DOOR_1(new VariationID(IDTypes.TILE, "003", "01"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/basic_door")}).safeSpot(EntityDetectDirection.DOWN)),
    METAL_WALL_DOOR_2(new VariationID(IDTypes.TILE, "003", "02"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/basic_door")}, new int[]{90, 90}).safeSpot(EntityDetectDirection.LEFT)),
    METAL_WALL_DOOR_3(new VariationID(IDTypes.TILE, "003", "03"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/basic_door")}, new int[]{180, 180}).safeSpot(EntityDetectDirection.UP)),
    METAL_WALL_DOOR_4(new VariationID(IDTypes.TILE, "003", "04"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/basic_door")}, new int[]{270, 270}).safeSpot(EntityDetectDirection.RIGHT)),
    COMPUTER_DEF_FLOOR_1(new VariationID(IDTypes.TILE, "004", "01"), new Tex("tiles/default_floor"), new Builder().isPassable().securityComputer().overlayTex(new Tex[]{new Tex("tile_overlays/security_computers")}, new int[]{0})),
    COMPUTER_DEF_FLOOR_2(new VariationID(IDTypes.TILE, "004", "02"), new Tex("tiles/default_floor"), new Builder().isPassable().securityComputer().overlayTex(new Tex[]{new Tex("tile_overlays/security_computers")}, new int[]{90})),
    COMPUTER_DEF_FLOOR_3(new VariationID(IDTypes.TILE, "004", "03"), new Tex("tiles/default_floor"), new Builder().isPassable().securityComputer().overlayTex(new Tex[]{new Tex("tile_overlays/security_computers")}, new int[]{180})),
    COMPUTER_DEF_FLOOR_4(new VariationID(IDTypes.TILE, "004", "04"), new Tex("tiles/default_floor"), new Builder().isPassable().securityComputer().overlayTex(new Tex[]{new Tex("tile_overlays/security_computers")}, new int[]{270})),
    METAL_WALL_EDOOR_1(new VariationID(IDTypes.TILE, "005", "01"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/basic_escape_door")}).safeSpot(EntityDetectDirection.DOWN).escapeDoor()),
    METAL_WALL_EDOOR_2(new VariationID(IDTypes.TILE, "005", "02"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/basic_escape_door")}, new int[]{90, 90}).safeSpot(EntityDetectDirection.LEFT).escapeDoor()),
    METAL_WALL_EDOOR_3(new VariationID(IDTypes.TILE, "005", "03"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/basic_escape_door")}, new int[]{180, 180}).safeSpot(EntityDetectDirection.UP).escapeDoor()),
    METAL_WALL_EDOOR_4(new VariationID(IDTypes.TILE, "005", "04"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/basic_escape_door")}, new int[]{270, 270}).safeSpot(EntityDetectDirection.RIGHT).escapeDoor()),
    DEFAULT_FLOOR_RED_LOCK(new VariationID(IDTypes.TILE, "006", "01"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/red_tint"), new Tex("tile_overlays/lock")}, new int[]{0, 0}).isPassable().itemUnlockDoor(ItemType.RED_KEY, new Tex[]{new Tex("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_ORANGE_LOCK(new VariationID(IDTypes.TILE, "006", "02"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/orange_tint"), new Tex("tile_overlays/lock")}, new int[]{0, 0}).isPassable().itemUnlockDoor(ItemType.ORANGE_KEY, new Tex[]{new Tex("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_YELLOW_LOCK(new VariationID(IDTypes.TILE, "006", "03"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/yellow_tint"), new Tex("tile_overlays/lock")}, new int[]{0, 0}).isPassable().itemUnlockDoor(ItemType.YELLOW_KEY, new Tex[]{new Tex("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_GREEN_LOCK(new VariationID(IDTypes.TILE, "006", "04"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/green_tint"), new Tex("tile_overlays/lock")}, new int[]{0, 0}).isPassable().itemUnlockDoor(ItemType.GREEN_KEY, new Tex[]{new Tex("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_BLUE_LOCK(new VariationID(IDTypes.TILE, "006", "05"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/blue_tint"), new Tex("tile_overlays/lock")}, new int[]{0, 0}).isPassable().itemUnlockDoor(ItemType.BLUE_KEY, new Tex[]{new Tex("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_DARK_BLUE_LOCK(new VariationID(IDTypes.TILE, "006", "06"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/dark_blue_tint"), new Tex("tile_overlays/lock")}, new int[]{0, 0}).isPassable().itemUnlockDoor(ItemType.DARK_BLUE_KEY, new Tex[]{new Tex("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_PURPLE_LOCK(new VariationID(IDTypes.TILE, "006", "07"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/purple_tint"), new Tex("tile_overlays/lock")}, new int[]{0, 0}).isPassable().itemUnlockDoor(ItemType.PURPLE_KEY, new Tex[]{new Tex("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_PINK_LOCK(new VariationID(IDTypes.TILE, "006", "08"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/pink_tint"), new Tex("tile_overlays/lock")}, new int[]{0, 0}).isPassable().itemUnlockDoor(ItemType.PINK_KEY, new Tex[]{new Tex("tile_overlays/lock")}, new int[]{0})),
    DEFAULT_FLOOR_SECURE_PODIUM(new VariationID(IDTypes.TILE, "007", "01"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/default_activated_secure_podium")}, new int[]{0}).isPassable().laserSecure(new Tex[]{new Tex("tile_overlays/default_deactivated_secure_podium")}, new int[]{0})),
    AUTHORITY_DOOR(new VariationID(IDTypes.TILE, "008", "01"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/vault_locked")}, new int[]{0}).isPassable().authorityLock(new int[]{3, 2, 1}, new Tex[]{new Tex("tile_overlays/vault_unlocked")}, new int[]{0})),
    PRES_PLATE_DEF_FLOOR_COMP_1(new VariationID(IDTypes.TILE, "009", "01"), new Tex("tiles/default_floor"), new Builder().isPassable().isPressurePlateComputer(new Tex[]{new Tex("tile_overlays/pressure_plate_computer_deactivated")}, new int[]{0}).overlayTex(new Tex[]{new Tex("tile_overlays/pressure_plate_computer_activated")}, new int[]{0})),
    PRES_PLATE_DEF_FLOOR_COMP_2(new VariationID(IDTypes.TILE, "009", "02"), new Tex("tiles/default_floor"), new Builder().isPassable().isPressurePlateComputer(new Tex[]{new Tex("tile_overlays/pressure_plate_computer_deactivated")}, new int[]{90}).overlayTex(new Tex[]{new Tex("tile_overlays/pressure_plate_computer_activated")}, new int[]{90})),
    PRES_PLATE_DEF_FLOOR_COMP_3(new VariationID(IDTypes.TILE, "009", "03"), new Tex("tiles/default_floor"), new Builder().isPassable().isPressurePlateComputer(new Tex[]{new Tex("tile_overlays/pressure_plate_computer_deactivated")}, new int[]{180}).overlayTex(new Tex[]{new Tex("tile_overlays/pressure_plate_computer_activated")}, new int[]{180})),
    PRES_PLATE_DEF_FLOOR_COMP_4(new VariationID(IDTypes.TILE, "009", "04"), new Tex("tiles/default_floor"), new Builder().isPassable().isPressurePlateComputer(new Tex[]{new Tex("tile_overlays/pressure_plate_computer_deactivated")}, new int[]{270}).overlayTex(new Tex[]{new Tex("tile_overlays/pressure_plate_computer_activated")}, new int[]{270})),
    PRESSURE_PLATE_DEF_FLOOR(new VariationID(IDTypes.TILE, "010", "01"), new Tex("tiles/default_floor"), new Builder().isPassable().pressurePlate().overlayTex(new Tex[]{new Tex("tile_overlays/pressure_plate")}, new int[]{0})),
    DEFAULT_RECHARGE_STATION(new VariationID(IDTypes.TILE, "011", "01"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/recharge_station")}, new int[]{0}).batteryCharger(new Tex("tile_overlays/recharge_station_has_battery", Renderer.TILE_SIZE, 0.07f), new Tex("tile_overlays/recharge_station_has_battery_full", Renderer.TILE_SIZE, 0.5f))),
    DEFAULT_REFUEL_STATION(new VariationID(IDTypes.TILE, "012", "01"), new Tex("tiles/default_floor"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/refuel_station")}, new int[]{0}).gasRefueler(new Tex("tile_overlays/refuel_station_has_object", Renderer.TILE_SIZE, 0.15f), new Tex("tile_overlays/refuel_station_has_object_full", Renderer.TILE_SIZE, 0.5f))),
    METAL_WALL_MDET_0(new VariationID(IDTypes.TILE, "013", "01"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/material_detector")}, new int[]{0, 180}).materialDetector(EntityDetectDirection.DOWN, new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/deactivated_material_detector")}, new int[]{0, 180})),
    METAL_WALL_MDET_1(new VariationID(IDTypes.TILE, "013", "02"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/material_detector")}, new int[]{90, 270}).materialDetector(EntityDetectDirection.LEFT, new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/deactivated_material_detector")}, new int[]{90, 270})),
    METAL_WALL_MDET_2(new VariationID(IDTypes.TILE, "013", "03"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/material_detector")}, new int[]{180, 0}).materialDetector(EntityDetectDirection.UP, new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/deactivated_material_detector")}, new int[]{180, 0})),
    METAL_WALL_MDET_3(new VariationID(IDTypes.TILE, "013", "04"), new Tex("tiles/metal_wall"), new Builder().overlayTex(new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/material_detector")}, new int[]{270, 90}).materialDetector(EntityDetectDirection.RIGHT, new Tex[]{new Tex("tile_overlays/wall_side"), new Tex("tile_overlays/deactivated_material_detector")}, new int[]{270, 90})),
    MAT_DETECT_DEF_FLOOR_COMP_1(new VariationID(IDTypes.TILE, "014", "01"), new Tex("tiles/default_floor"), new Builder().isPassable().overlayTex(new Tex[]{new Tex("tile_overlays/material_detector_computer_activated")}, new int[]{0}).materialDetectorComputer(new Tex[]{new Tex("tile_overlays/material_detector_computer_deactivated")}, new int[]{0})),
    MAT_DETECT_DEF_FLOOR_COMP_2(new VariationID(IDTypes.TILE, "014", "02"), new Tex("tiles/default_floor"), new Builder().isPassable().overlayTex(new Tex[]{new Tex("tile_overlays/material_detector_computer_activated")}, new int[]{90}).materialDetectorComputer(new Tex[]{new Tex("tile_overlays/material_detector_computer_deactivated")}, new int[]{90})),
    MAT_DETECT_DEF_FLOOR_COMP_3(new VariationID(IDTypes.TILE, "014", "03"), new Tex("tiles/default_floor"), new Builder().isPassable().overlayTex(new Tex[]{new Tex("tile_overlays/material_detector_computer_activated")}, new int[]{180}).materialDetectorComputer(new Tex[]{new Tex("tile_overlays/material_detector_computer_deactivated")}, new int[]{180})),
    MAT_DETECT_DEF_FLOOR_COMP_4(new VariationID(IDTypes.TILE, "014", "04"), new Tex("tiles/default_floor"), new Builder().isPassable().overlayTex(new Tex[]{new Tex("tile_overlays/material_detector_computer_activated")}, new int[]{270}).materialDetectorComputer(new Tex[]{new Tex("tile_overlays/material_detector_computer_deactivated")}, new int[]{270}));

    /* IDEAS FOR TILES:
     * - DONE: Authority door: Must have multiple PASSES to unlock
     * - DONE: Pressure plate floor: Activates alarm when player hits it. Can be deactivated it by overriding pressure plate management computer or fly over it
     * - DONE: Recharge station: Can recharge battery there.
     * - DONE: Refuel station: Can refuel gas tank there.
     * - Movement detector: Activates alarm when player hits the tile. Can be deactivated by overriding movement detector management computer.
     * - Material detector: Activates alarm when you hold a required item when moving onto this tile. Can be deactivated by overriding material detector computer.
     * - DONE: Pressure plate mamanement computer: Managed pressure plates, can be overridden
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
    private final String id;
    private final Tex texture;
    private final boolean isPassable, isSafeSpot, isSecurityComputer, isSecure, authorityLocked, isPressurePlateComputer, startsActive, activeInfluencesPassasble;
    private final EntityDetectDirection safeSpot;
    private final Tex[] overlayTex, activeTexture;
    private final int[] activeTextureRots, overlayTexRot, cardPassesNeeded;
    public static Map<String, TileType> TILE_IDS; // ArrayList to store all different tile ids
    public static ArrayList<TileType> TILE_TYPES;
    private final Class<? extends Tile> subClass;
    private final Object[] subClassArgs;

    TileType(VariationID id, Tex texture, Builder builder) {
        createIdMapAndArrays();
        addToMap(id.getFullId(), this);
        this.id = id.getFullId();
        this.texture = texture;
        this.startsActive = builder.startsActive();
        this.activeInfluencesPassasble = builder.activeInfluencesPassable();
        this.isPassable = builder.getIsPassable();
        this.isSafeSpot = builder.getIsSafeSpot();
        this.safeSpot = builder.getSafeSpot();
        this.overlayTex = builder.getOverlayTex();
        this.overlayTexRot = builder.getOverlayTexRot();
        this.isSecurityComputer = builder.isSecurityComputer();
        this.subClassArgs = builder.getSubClassArgs();
        this.activeTexture = builder.getActiveTileTexture();
        this.activeTextureRots = builder.getActiveTileTextureRots();
        this.isSecure = builder.isSecure();
        this.cardPassesNeeded = builder.getCardPassesNeeded();
        this.authorityLocked = builder.isAuthorityDoor();
        this.isPressurePlateComputer = builder.isPressurePlateComputer();
        this.subClass = builder.getSubClass();
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
    public Class<? extends Tile> getSubClass() {
        return subClass;
    }
    public String getId() {
        return id;
    }
    public Tex getTexture() {
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
    public Tex[] getOverlayTex() {
        return overlayTex;
    }
    public int[] getOverlayTexRot() {
        return overlayTexRot;
    }
    public boolean isSecurityComputer() {
        return isSecurityComputer;
    }
    public boolean isPressurePlateComputer() {
        return isPressurePlateComputer;
    }
    public Object[] subClassArgs() {
        return subClassArgs;
    }
    public Tex[] getActiveTileTexture() {
        return activeTexture;
    }
    public int[] getActiveTileTextureRots() {
        return activeTextureRots;
    }
    public boolean isSecure() {
        return isSecure;
    }
    public boolean isAuthorityDoor() {
        return authorityLocked;
    }
    public int[] cardPassesNeeded() {
        return cardPassesNeeded;
    }
    public boolean startsActive() {
        return startsActive;
    }
    public boolean activeInfluencesPassable() {
        return activeInfluencesPassasble;
    }

    /**
     * Tile builder class.
     */
    private static class Builder {
        public static EntityDetectDirection safeSpot;
        public static Tex[] activeTexture, overlayTex;
        public static int[] activeTextureRots, overlayTexRot, cardPassesNeeded;
        public static boolean isSecure, doorLocked, isSecurityComputer, escapeDoor, isSafeSpot, isPassable, authorityLocked, isPressurePlateComputer, pressurePlate, startsActive, activeInfluencesPassable;
        public static Class<? extends Tile> subClass;
        public static Object[] subClassArgs;

        /**
         * Builder constructor. Defines all variables to its default value. Keep in mind that, unlike the other Builders for Items and Enemies, the order of which you call the methods MATTER. You should have the main functionality of the tile as the LAST method.
         */
        private Builder() {
            isPassable = false;
            isSafeSpot = false;
            safeSpot = EntityDetectDirection.NONE;
            escapeDoor = false;
            overlayTex = new Tex[]{new Tex("tiles/blank")};
            overlayTexRot = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            isSecurityComputer = false;
            subClassArgs = null;
            activeTexture = null;
            activeTextureRots = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            isSecure = false;
            doorLocked = false;
            authorityLocked = false;
            cardPassesNeeded = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            isPressurePlateComputer = false;
            pressurePlate = false;
            subClass = null;
            startsActive = false;
            activeInfluencesPassable = false;
        }

        /**
         * Define a custom subclass. This will run a special set of code dedicated for that type of tile only.
         */
        private Builder subclass(Class<? extends Tile> subclass) {
            this.subClass = subclass;
            return this;
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
            subclass(ExitSpot.class);
            return this;
        }

        /**
         * Set overlay texture, if any
         * Only 16 of these are allowed, otherwise game will throw ArrayIndexOutOfBoundsException
         */
        private Builder overlayTex(Tex[] tex) {
            overlayTex = tex;
            return this;
        }

        /**
         * Set and rotate overlay texture if needed. Rotates clockwise.
         * Only 16 of these are allowed, otherwise game will throw ArrayIndexOutOfBoundsException
         */
        private Builder overlayTex(Tex[] tex, int[] rot) {
            overlayTex = tex;
            overlayTexRot = rot;
            return this;
        }

        /**
         * Set if this door can be unlocked with an item
         */
        private Builder itemUnlockDoor(ItemType key, Tex[] unlockedTileTexture, int[] unlockedTileTextureRots) {
            subClassArgs = new Object[1];
            subClassArgs[0] = key;
            activeTexture = unlockedTileTexture;
            activeTextureRots = unlockedTileTextureRots;
            doorLocked = true;
            startsActive = true;
            activeInfluencesPassable = true;
            subclass(ItemUnlocksDoor.class);
            return this;
        }

        /**
         * Set if this door is a authority
         */
        private Builder authorityLock(int[] cardPassesNeeded, Tex[] unlockedTileTexture, int[] unlockedTileTextureRots) {
            this.cardPassesNeeded = cardPassesNeeded;
            activeTexture = unlockedTileTexture;
            activeTextureRots = unlockedTileTextureRots;
            authorityLocked = true;
            subclass(AuthorityDoor.class);
            startsActive = true;
            activeInfluencesPassable = true;
            return this;
        }

        /**
         * Set if this door can be unlocked
         */
        private Builder laserSecure(Tex[] deactivatedTileTexture, int[] deactivatedTileTextureRots) {
            activeTexture = deactivatedTileTexture;
            activeTextureRots = deactivatedTileTextureRots;
            isSecure = true;
            startsActive = true;
            return this;
        }

        /**
         * Determine whether or not this is a security computer (can disable alarms)
         */
        private Builder securityComputer() {
            isSecurityComputer = true;
            subclass(MainComputer.class);
            return this;
        }

        private Builder isPressurePlateComputer(Tex[] overlayTex, int[] rots) {
            isPressurePlateComputer = true;
            activeTexture = overlayTex;
            activeTextureRots = rots;
            subclass(PressurePlateComputer.class);
            startsActive = true;
            return this;
        }

        private Builder pressurePlate() {
            pressurePlate = true;
            subclass(PressurePlate.class);
            startsActive = true;
            return this;
        }

        private Builder batteryCharger(Tex tex, Tex doneTex) {
            subclass(RechargeStation.class);
            startsActive = true;
            subClassArgs = new Object[]{tex, doneTex};
            return this;
        }

        private Builder gasRefueler(Tex tex, Tex doneTex) {
            subclass(RefuelStation.class);
            startsActive = true;
            subClassArgs = new Object[]{tex, doneTex};
            return this;
        }

        private Builder materialDetector(EntityDetectDirection direction, Tex[] deactivatedTileTexture, int[] deactivatedTileTextureRots) {
            subClassArgs = new Object[]{direction};
            activeTexture = deactivatedTileTexture;
            activeTextureRots = deactivatedTileTextureRots;
            startsActive = true;
            subclass(MaterialDetector.class);
            return this;
        }

        private Builder materialDetectorComputer(Tex[] overlayTex, int[] rots) {
            activeTexture = overlayTex;
            activeTextureRots = rots;
            subclass(MaterialDetectorComputer.class);
            startsActive = true;
            return this;
        }

        // Getters
        public Class<? extends Tile> getSubClass() {
            return subClass;
        }
        public boolean getIsPassable() {
            return isPassable;
        }
        public boolean getIsSafeSpot() {
            return isSafeSpot;
        }
        public EntityDetectDirection getSafeSpot() {
            return safeSpot;
        }
        public Tex[] getOverlayTex() {
            return overlayTex;
        }
        public int[] getOverlayTexRot() {
            return overlayTexRot;
        }
        public boolean isSecurityComputer() {
            return isSecurityComputer;
        }
        public boolean isPressurePlateComputer() {
            return isPressurePlateComputer;
        }
        public Object[] getSubClassArgs() {
            return subClassArgs;
        }
        public Tex[] getActiveTileTexture() {
            return activeTexture;
        }
        public int[] getActiveTileTextureRots() {
            return activeTextureRots;
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
        public boolean isPressurePlate() {
            return pressurePlate;
        }
        public boolean startsActive() {
            return startsActive;
        }
        public boolean activeInfluencesPassable() {
            return activeInfluencesPassable;
        }
    }
}
