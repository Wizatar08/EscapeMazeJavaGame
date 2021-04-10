package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.items.subclasses.durability.RechargableBattery;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

public class RechargeStation extends Tile {
    private Game gameController;
    private Tex batteryTex, detectItemTex, takeItemTex, hasItemTex, hasItemFullTex;
    private RechargableBattery itemCharging;

    public RechargeStation(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        itemCharging = null;
        detectItemTex = new Tex("tiles/selectors/item_use_selector");
        takeItemTex = new Tex("tiles/selectors/tile_selector");
        hasItemTex = Tex.newInstance((Tex) type.subClassArgs()[0]);
        hasItemFullTex = Tex.newInstance((Tex) type.subClassArgs()[1]);
    }

    public void chargeBattery(Tex tex) {
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
            itemCharging.update();
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (batteryTex != null) {
            if (itemCharging.getDurabilityPercentage() < 1.0f) {
                hasItemTex.draw(getX(), getY());
            } else {
                hasItemFullTex.draw(getX(), getY());
            }
            batteryTex.draw(getX(), getY());
        }
    }

    @Override
    public void useTilePlayerNear() {
        if (itemCharging != null && gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() == null) {
            gameController.getCurrentPlayer().getInventory().add(itemCharging);
            itemCharging.stopCharging();
            takeBattery();
            itemCharging = null;
        }
    }

    @Override
    public void playerNearTile() {
        if (itemCharging == null && gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() instanceof RechargableBattery) {
            detectItemTex.draw(getX(), getY());
        }
        if (gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() == null && itemCharging != null) {
            takeItemTex.draw(getX(), getY());
        }
    }

    public void useRechargeStation(RechargableBattery battery) {
        itemCharging = battery;
        chargeBattery(itemCharging.getBatteryChargingTexture());
        itemCharging.destroy();
    }
}
