package com.wizatar08.escapemaze.game.game_entities.enemies;

import com.wizatar08.escapemaze.game.game_entities.Player;
import com.wizatar08.escapemaze.helpers.Clock;
import com.wizatar08.escapemaze.helpers.EnemyPathfinder;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.helpers.drawings.Tex;
import com.wizatar08.escapemaze.interfaces.Entity;
import com.wizatar08.escapemaze.menus.Game;

import java.util.ArrayList;

import static com.wizatar08.escapemaze.helpers.drawings.Drawer.*;
import static com.wizatar08.escapemaze.render.Renderer.*;

public class Enemy implements Entity {
    private int currentPathPoint, distanceView;
    private float x, y, width, height, rot, hypotenuse, alarmSpeed;
    private String id;
    private int[][] pathCoords;
    private Tex texture;
    private EnemyType type;
    private EnemyPathfinder pathfinder;
    private ArrayList<Player> playerInstances;
    private Game gameController;
    private Timer freezeTime;

    public Enemy(Game game, int id, int[][] path) {
        this.id = String.valueOf(id);
        this.pathCoords = path;
        this.currentPathPoint = 0;
        this.type = EnemyType.ENEMY_IDS.get(this.id);
        this.texture = new Tex("enemies/" + type.getTexture());
        this.x = path[0][0] * TILE_SIZE;
        this.y = path[0][1] * TILE_SIZE;
        this.width = texture.getOpenGLTex().getImageWidth();
        this.height = texture.getOpenGLTex().getImageHeight();
        this.hypotenuse = 0;
        this.pathfinder = new EnemyPathfinder(this);
        this.distanceView = type.getViewDistance();
        this.playerInstances = game.getPlayerInstances();
        this.gameController = game;
        this.alarmSpeed = type.getAlarmSpeed();
        this.freezeTime = new Timer(Timer.TimerModes.COUNT_DOWN, 0);
        multiplyPaths();
    }

    private void multiplyPaths() {
        for (int i = 0; i < pathCoords.length; i++) {
            for (int j = 0; j < pathCoords[i].length; j++) {
                pathCoords[i][j] *= TILE_SIZE;
            }
        }
    }

    public String getId() {
        return id;
    }

    public int[][] getPathCoords() {
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

    private void move() {
        if (hypotenuse <= 0) {
            nextPath();
            hypotenuse = (float) Math.hypot(x - pathCoords[currentPathPoint][0], y - pathCoords[currentPathPoint][1]);
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
        hypotenuse -= Math.sqrt((diffX * diffX) + (diffY * diffY));
    }

    public void update() {
        if (freezeTime.isPaused()) {
            rot = pathfinder.getRotInDegrees(x, y, pathCoords[currentPathPoint][0], pathCoords[currentPathPoint][1]);
            move();
            pathfinder.update();
            detectPlayer();
        }
        if (freezeTime.getTotalSeconds() <= 0) {
            freezeTime.pause();
        }
        freezeTime.update();
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
        });
        if (checkCollision(x, y, width, height, playerInstances.get(gameController.getCurrentPlayerIndex()).getX(), playerInstances.get(gameController.getCurrentPlayerIndex()).getY(), playerInstances.get(gameController.getCurrentPlayerIndex()).getWidth(), playerInstances.get(gameController.getCurrentPlayerIndex()).getHeight())) {
            gameController.setState(Game.GameStates.GAME_END);
            gameController.endGame(false);
        }
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
}
