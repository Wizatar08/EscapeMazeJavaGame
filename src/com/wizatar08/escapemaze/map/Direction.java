package com.wizatar08.escapemaze.map;

public enum Direction {
    NONE(0, 0),
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private float xDir, yDir;

    Direction(float xDirection, float yDirection) {
        xDir = xDirection;
        yDir = yDirection;
        System.out.println(this + ", " + xDir + ", " + yDir);
    }

    public float getxDir() {
        return xDir;
    }

    public float getyDir() {
        return yDir;
    }

    public Direction inverse(Direction direction) {
        switch (direction) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: return NONE;
        }
    }
    public boolean isOpposite(Direction direction) {
        return inverse(direction) == this;
    }
}
