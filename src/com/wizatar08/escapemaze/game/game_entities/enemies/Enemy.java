package com.wizatar08.escapemaze.game.game_entities.enemies;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.helpers.EnemyPathfinder;
import com.wizatar08.escapemaze.helpers.Hitbox;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.visuals.Tex;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.menus.Game;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.wizatar08.escapemaze.visuals.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

public class Enemy implements Entity {
    private int currentPathPoint, distanceView, hitBoxSize;
    private float x, y, width, height, rot, rotDiff, alarmSpeed;
    private String id;
    private final BigDecimal[][] pathCoords;
    private final Tex texture;
    private final EnemyType type;
    private final EnemyPathfinder pathfinder;
    private ArrayList<Player> playerInstances;
    private final Game gameController;
    private final Timer freezeTime;
    private final BigDecimal tileSize;
    private BigDecimal hypotenuse;
    private final Hitbox hitbox;

    public Enemy(Game game, int id, int[][] path) {
        this.id = String.valueOf(id);
        this.pathCoords = new BigDecimal[path.length][2];

        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                this.pathCoords[i][j] = new BigDecimal(path[i][j] * TILE_SIZE);
            }
        }

        this.tileSize = new BigDecimal(TILE_SIZE);

        this.currentPathPoint = 0;
        this.type = EnemyType.ENEMY_IDS.get(this.id);
        this.texture = new Tex("enemies/" + type.getTexture());
        this.x = path[0][0] * TILE_SIZE;
        this.y = path[0][1] * TILE_SIZE;
        this.width = texture.getOpenGLTex().getImageWidth();
        this.height = texture.getOpenGLTex().getImageHeight();
        this.hypotenuse = new BigDecimal(0);
        this.pathfinder = new EnemyPathfinder(this);
        this.distanceView = type.getViewDistance();
        this.playerInstances = game.getPlayerInstances();
        this.gameController = game;
        this.alarmSpeed = type.getAlarmSpeed();
        this.hitBoxSize = type.getHitBoxSize();
        this.freezeTime = new Timer(Timer.TimerModes.COUNT_DOWN, 0);
        this.rotDiff = 0f;

        this.hitbox = new Hitbox(this, (width - this.hitBoxSize) / 2, (height - this.hitBoxSize) / 2, this.hitBoxSize, this.hitBoxSize, true);
        multiplyPaths();
    }

    private void multiplyPaths() {
        for (BigDecimal[] pathCoord : pathCoords) {
            for (BigDecimal bigDecimal : pathCoord) {
                bigDecimal.multiply(tileSize);
            }
        }
    }

    public String getId() {
        return id;
    }

    public BigDecimal[][] getPathCoords() {
        return pathCoords;
    }

    public int getCurrentPathIndex() {
        return currentPathPoint;
    }

    public void nextPath() {
        currentPathPoint++;
        if (currentPathPoint >= pathCoords.length) {
            currentPathPoint = 0;
        }
    }

    public void draw() {
        texture.draw(x + Game.DIS_X, y + Game.DIS_Y, rot);
    }

    private void moveToNextPath() {
        setX(pathCoords[currentPathPoint][0].floatValue());
        setY(pathCoords[currentPathPoint][1].floatValue());
        nextPath();
        hypotenuse = BigDecimal.valueOf(Math.hypot(x - pathCoords[currentPathPoint][0].floatValue(), y - pathCoords[currentPathPoint][1].floatValue()));
    }

    private void move() {
        float lastRot = rot;
        rot = pathfinder.getRotInDegrees(x, y, pathCoords[currentPathPoint][0].floatValue(), pathCoords[currentPathPoint][1].floatValue());
        rotDiff = lastRot - rot;
        float disX = pathCoords[currentPathPoint][0].floatValue() - x;
        float disY = pathCoords[currentPathPoint][1].floatValue() - y;
        if ((Math.abs(disX) + Math.abs(disY) < 4) || hypotenuse.intValue() <= 1) {
        //if (hypotenuse.intValue() < -1 && checkCollision(x, y, width + 4, height + 4, pathCoords[currentPathPoint][0].intValue() - 2 + TILE_SIZE, pathCoords[currentPathPoint][1].intValue() - 2 + TILE_SIZE, 5, 5)) {
            moveToNextPath();
        }
        float diffX = x;
        float diffY = y;
        float aSpeed = 1.0f;
        if (gameController.currentState() == Game.GameStates.ALARM) {
            aSpeed = alarmSpeed;
        }
        x += pathfinder.getAddedCoords(rot)[0] * Clock.Delta() * Clock.Delta() * Clock.FPS * type.getSpeed() * aSpeed;
        y += pathfinder.getAddedCoords(rot)[1] * Clock.Delta() * Clock.Delta() * Clock.FPS * type.getSpeed() * aSpeed;
        diffX -= x;
        diffY -= y;
        hypotenuse = hypotenuse.subtract(BigDecimal.valueOf(Math.sqrt((diffX * diffX) + (diffY * diffY))));
    }

    public void update() {
        if (freezeTime.isPaused()) {
            move();
            pathfinder.update();
            detectPlayer();
        }
        if (freezeTime.getTotalSeconds() <= 0) {
            freezeTime.pause();
        }
        freezeTime.update();
        hitbox.update();
    }

    public void freeze(float seconds) {
        freezeTime.setTime(seconds);
        freezeTime.unpause();
    }

    private void detectPlayer() {
        playerInstances.forEach((p) -> {
            if (pathfinder.scanForWalls(distanceView, p) && (getAngleOfPlayerRelativeToEnemy() < ((float) type.getAngleOfView() / 2) && getAngleOfPlayerRelativeToEnemy() > ((float) -type.getAngleOfView() / 2)) && !playerInstances.get(gameController.getCurrentPlayerIndex()).isSafe()) {
                gameController.setState(Game.GameStates.ALARM);
            }
            if (hitbox.collidesWith(p) && !p.isSafe()) {
            //if (checkCollision((width - hitBoxSize) / 2 + x, (height - hitBoxSize) / 2 + y, hitBoxSize, hitBoxSize, p.getX(), p.getY(), p.getWidth(), p.getHeight()) && !p.isSafe()) {
                gameController.setState(Game.GameStates.GAME_END);
                gameController.endGame(false);
            }
            //test.draw((width - hitBoxSize) / 2 + x, (height - hitBoxSize) / 2 + y, 0, hitBoxSize, hitBoxSize);
        });
    }

    public float getAngleOfPlayerRelativeToEnemy() {
        float rotComparison = pathfinder.getRotInDegrees(x, y, playerInstances.get(gameController.getCurrentPlayerIndex()).getX(), playerInstances.get(gameController.getCurrentPlayerIndex()).getY());
        return rotComparison - rot;
    }

    // Getters and setters

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setX(float x) {

    }

    @Override
    public void setY(float y) {

    }

    @Override
    public void setWidth(float width) {

    }

    @Override
    public void setHeight(float height) {

    }

    public EnemyType getType() {
        return type;
    }

    public ArrayList<Player> getPlayer() {
        return playerInstances;
    }

    public Game getGameController() {
        return gameController;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public String toString() {
        return "Entity Enemy{Type:" + type + ",x=" + x + ",y=" + y + "}";
    }
}
