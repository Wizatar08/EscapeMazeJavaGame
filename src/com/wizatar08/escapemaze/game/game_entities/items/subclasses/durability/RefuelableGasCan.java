package com.wizatar08.escapemaze.game.game_entities.items.subclasses.durability;

import com.google.gson.JsonObject;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.game.game_entities.items.subclasses.DurabilityItem;
import com.wizatar08.escapemaze.helpers.visuals.Tex;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.tile_types.RefuelStation;
import com.wizatar08.escapemaze.menus.Game;

public class RefuelableGasCan extends DurabilityItem {
    private final Game gameController;
    private Tile tile;
    private ItemType type;

    public RefuelableGasCan(Game game, ItemType type, Tex texture, JsonObject data, float x, float y) {
        super(game, type, texture, data, x, y);
        gameController = game;
        tile = null;
        this.type = type;
    }

    @Override
    public boolean canUse() {
        for (Tile tile : gameController.getCurrentPlayer().getAllSurroundingTiles()) {
            if (tile.getSubClass() == RefuelStation.class) {
                this.tile = tile;
                return true;
            }
        }
        return false;
    }

    public void startCharging() {}
    public void stopCharging() {}

    public Tex getGasCanChargingTexture() {
        return (Tex) type.getClassArgs()[1];
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void use() {
        gameController.getCurrentPlayer().getAllSurroundingTiles().forEach((t) -> {
            if (t instanceof RefuelStation) {
                ((RefuelStation) t).useRefuelStation(this);
            }
        });
    }
}
