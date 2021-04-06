package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.items.subclasses.durability.RechargableBattery;
import com.wizatar08.escapemaze.game.game_entities.items.subclasses.durability.RefuelableGasCan;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.helpers.Drawer;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;
import org.newdawn.slick.opengl.Texture;

public class RefuelStation extends Tile {
    private Game gameController;
    private Texture gasCanTex, detectItemTex, takeItemTex;
    private RefuelableGasCan itemCharging;

    public RefuelStation(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        itemCharging = null;
        detectItemTex = Drawer.LoadPNG("tiles/selectors/item_use_selector");
        takeItemTex = Drawer.LoadPNG("tiles/selectors/tile_selector");
    }

    public void chargeGasCan(Texture tex) {
        gasCanTex = tex;
    }
    public void takeGasCan() {
        gasCanTex = null;
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
        if (gasCanTex != null) {
            Drawer.drawQuadTex(gasCanTex, getX(), getY());
        }
    }

    @Override
    public void playerNearTile() {
        if (itemCharging == null && gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() instanceof RefuelableGasCan) {
            Drawer.drawQuadTex(detectItemTex, getX(), getY());
        }
        if (gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() == null && itemCharging != null) {
            Drawer.drawQuadTex(takeItemTex, getX(), getY());
        }
    }

    @Override
    public void useTilePlayerNear() {
        if (gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() == null && itemCharging != null) {
            gameController.getCurrentPlayer().getInventory().add(itemCharging);
            itemCharging.stopCharging();
            takeGasCan();
            itemCharging = null;
        }
    }

    public void useRefuelStation(RefuelableGasCan gasSource) {
        itemCharging = gasSource;
        chargeGasCan(itemCharging.getGasCanChargingTexture());
        itemCharging.destroy();
    }
}
