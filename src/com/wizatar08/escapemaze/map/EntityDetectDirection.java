package com.wizatar08.escapemaze.map;

public enum EntityDetectDirection {
    NONE(0, 0),
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private float xDir, yDir;

    EntityDetectDirection(float xDirection, float yDirection) {
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
}
