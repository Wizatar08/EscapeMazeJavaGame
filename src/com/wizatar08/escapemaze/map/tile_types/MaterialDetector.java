package com.wizatar08.escapemaze.map.tile_types;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.map.Direction;
import com.wizatar08.escapemaze.map.Tile;
import com.wizatar08.escapemaze.map.TileType;
import com.wizatar08.escapemaze.menus.Game;
import org.jetbrains.annotations.Contract;

import javax.xml.ws.spi.WebServiceFeatureAnnotation;
import java.util.ArrayList;

import static com.wizatar08.escapemaze.visuals.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

public class MaterialDetector extends Tile {
    private Game gameController;
    private Direction detectDirection;
    private ArrayList<Integer> xPoses, yPoses;

    public MaterialDetector(Game game, float x, float y, int width, int height, TileType type) {
        super(game, x, y, width, height, type);
        gameController = game;
        detectDirection = (Direction) type.subClassArgs()[0];
        xPoses = new ArrayList<>();
        yPoses = new ArrayList<>();
    }

    @Override
    public void onMapCreation() {
        super.onMapCreation();
        createDetectionAreas();
    }

    private void createDetectionAreas() {
        xPoses.add(Math.round(getX() + ((float) TILE_SIZE / 2) + (40 * detectDirection.getxDir())));
        yPoses.add(Math.round(getY() + ((float) TILE_SIZE / 2) + (40 * detectDirection.getyDir())));
        while ((gameController.getMap().getTile(xPoses.get(xPoses.size() - 1) / TILE_SIZE, yPoses.get(yPoses.size() - 1) / TILE_SIZE)).testIfPassable()) {
            xPoses.add(Math.round(getX() + ((float) TILE_SIZE / 2) + (((float) TILE_SIZE / 2 * detectDirection.getxDir()) + 16 * detectDirection.getxDir() * xPoses.size())));
            yPoses.add(Math.round(getY() + ((float) TILE_SIZE / 2) + (((float) TILE_SIZE / 2 * detectDirection.getyDir()) + 16 * detectDirection.getyDir() * yPoses.size())));
        }
    }

    @Override
    public void update() {
        super.update();
        Player p = gameController.getCurrentPlayer();
        if (gameController.materialDetectorsActive()) {
            for (int i = 0; i < xPoses.size(); i++) {
                if (p.getHitbox().intersectsWith(xPoses.get(i), yPoses.get(i))) {
                    if (p.getInventory().hasRequiredItem()) {
                        gameController.setState(Game.GameStates.ALARM);
                    }
                }
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
    }
}
