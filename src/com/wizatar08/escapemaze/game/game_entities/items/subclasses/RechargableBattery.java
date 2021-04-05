package com.wizatar08.escapemaze.game.game_entities.items.subclasses;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.tile_types.LaserSecure;
import com.wizatar08.escapemaze.map.tile_types.RechargeStation;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

import java.util.Arrays;

public class RechargableBattery extends DurabilityItem {
    private final Game gameController;
    private Tile tile;
    private ItemType type;

    public RechargableBattery(Game game, ItemType type, Texture texture, float x, float y) {
        super(game, type, texture, x, y);
        gameController = game;
        tile = null;
        this.type = type;
    }

    @Override
    public boolean canUse() {
        for (Tile tile : gameController.getCurrentPlayer().getAllSurroundingTiles()) {
            if (tile.getSubClass() == RechargeStation.class) {
                this.tile = tile;
                return true;
            }
        }
        return false;
    }

    public void startCharging() {}
    public void stopCharging() {}

    public Texture getBatteryChargingTexture() {
        return (Texture) type.getClassArgs()[1];
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void use() {
        gameController.getCurrentPlayer().getAllSurroundingTiles().forEach((t) -> {
            if (t instanceof RechargeStation) {
                ((RechargeStation) t).useRechargeStation(this);
            }
        });
    }
}
