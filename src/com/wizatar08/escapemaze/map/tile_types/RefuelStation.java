package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.items.subclasses.durability.RefuelableGasCan;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import com.wizatar08.escapemaze.map.Direction;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;

public class RefuelStation extends Tile {
    private Game gameController;
    private Tex gasCanTex, detectItemTex, takeItemTex, hasItemTex, hasItemFullTex;
    private RefuelableGasCan itemCharging;

    public RefuelStation(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        itemCharging = null;
        detectItemTex = new Tex("tiles/selectors/item_use_selector");
        takeItemTex = new Tex("tiles/selectors/tile_selector");
        hasItemTex = Tex.newInstance((Tex) type.subClassArgs()[0]);
        hasItemFullTex = Tex.newInstance((Tex) type.subClassArgs()[1]);
    }

    public void chargeGasCan(Tex tex) {
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
            itemCharging.update();
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (gasCanTex != null) {
            if (itemCharging.getDurabilityPercentage() < 1.0f) {
                hasItemTex.draw(getX(), getY());
            } else {
                hasItemFullTex.draw(getX(), getY());
            }
            gasCanTex.draw(getX(), getY());
        }
    }

    @Override
    public void playerNearTile(Direction direction) {
        if (itemCharging == null && gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() instanceof RefuelableGasCan) {
            detectItemTex.draw(getX(), getY());
        }
        if (gameController.getCurrentPlayer().getInventory().getCurrentSelectedItem() == null && itemCharging != null) {
            takeItemTex.draw(getX(), getY());
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
