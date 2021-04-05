package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.items.subclasses.RechargableBattery;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.helpers.Drawer;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

public class RechargeStation extends Tile {
    private Game gameController;
    private Texture batteryTex;
    private boolean isOccupied;
    private RechargableBattery itemCharging;

    public RechargeStation(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        itemCharging = null;
    }

    public void chargeBattery(Texture tex) {
        batteryTex = tex;
    }
    public void takeBattery() {
        batteryTex = null;
    }

    @Override
    public void update() {
        if (itemCharging != null) {
            if (itemCharging.getDurabilityPercentage() < 1.0f) {
                itemCharging.add(Clock.Delta() * 0.01f);
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (batteryTex != null) {
            Drawer.drawQuadTex(batteryTex, getX(), getY());
        }
    }

    @Override
    public void useTilePlayerNear() {
        if (gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() == null && itemCharging != null) {
            gameController.getCurrentPlayer().getInventory().add(itemCharging);
            itemCharging.stopCharging();
            takeBattery();
            itemCharging = null;
        }
    }

    public void useRechargeStation(RechargableBattery battery) {
        itemCharging = battery;
        chargeBattery(itemCharging.getBatteryChargingTexture());
        itemCharging.destroy();
    }
}
