package com.wizatar08.escapemaze.game.game_entities.enemies;

import java.util.*;

public enum EnemyType {
    // IDTYPE: 2
    NULL("200000", "null", new Builder()),
    CUBE_SCANNER("200100", "cube_scanner", new Builder().speed(30.0f).viewDistance(256).angleOfView(60).hitBoxSize(48)),
    SPEED_GUARD("200200", "speed_guard", new Builder().speed(72.0f).viewDistance(128).angleOfView(60).hitBoxSize(44));

    /*
     * IDEAS FOR NEW ENEMIES:
     * - Magnifying bot (Slow, has high distance and angle of view)
     * - Sensor bot (Has 360 degree vision)
     * - Rotating camera (stationary, has high distance and angle of view, rotates)
     * - Guard (Sets off alarm if it sees a deactivated laser OR if a door is tampered (player uses key))
     * - Warden (Sets off alarm if any ITEM is not where it should be or is moved)
     * - Officer (Shoots bullets at player when seen)
     * - Atom bot (Follows players through walls when alarm is on)
     * - Magma trailer (Creates a trail of impassable tiles when alarm is on)
     * - Automatic gun (Shoots at player)
     */


    // Initialize variables
    private String id;
    private String texture;
    private float speed, alarmSpeed;
    private int viewDistance, angleOfView, hitBoxSize;

    public static HashMap<String, EnemyType> ENEMY_IDS;

    EnemyType(String id, String texture, Builder builder) {
        createIdMapAndArrays();
        addToMap(id, this);
        this.id = id;
        this.texture = texture;
        this.speed = builder.getSpeed();
        this.viewDistance = builder.getViewDistance();
        this.angleOfView = builder.getangleOfView();
        this.alarmSpeed = builder.getAlarmSpeed();
        this.hitBoxSize = builder.getHitBoxSize();
    }

    private void createIdMapAndArrays() {
        if (ENEMY_IDS == null) ENEMY_IDS = new HashMap<>();
    }

    private void addToMap(String id, EnemyType type) {
        ENEMY_IDS.put(id, type);
    }


    // Getters
    public String getId() {
        return id;
    }
    public String getTexture() {
        return texture;
    }
    public float getSpeed() {
        return speed;
    }
    public int getViewDistance() {
        return viewDistance;
    }
    public int getAngleOfView() {
        return angleOfView;
    }
    public float getAlarmSpeed() {
        return alarmSpeed;
    }
    public int getHitBoxSize() {
        return hitBoxSize;
    }

    /**
     * Tile builder class
     */
    private static class Builder {
        private float speed, alarmSpeed;
        private int viewDistance, angleOfView, hitBoxSize;

        /**
         * Builder constructor. Defines all variables to its default value.
         */
        private Builder() {
            speed = 1.0f;
            viewDistance = 64;
            angleOfView = 45;
            alarmSpeed = 2.0f;
            hitBoxSize = 64;
        }

        private Builder speed(float speed) {
            this.speed = speed;
            return this;
        }

        private Builder alarmSpeed(float speed) {
            this.alarmSpeed = speed;
            return this;
        }

        private Builder viewDistance(int viewDistance) {
            this.viewDistance = viewDistance;
            return this;
        }

        private Builder angleOfView(int angleOfView) {
            this.angleOfView = angleOfView;
            return this;
        }

        private Builder hitBoxSize(int hbs) {
            this.hitBoxSize = hbs;
            return this;
        }


        public float getSpeed() {
            return speed;
        }
        public int getViewDistance() {
            return viewDistance;
        }
        public int getangleOfView() {
            return angleOfView;
        }
        public float getAlarmSpeed() {
            return alarmSpeed;
        }
        public int getHitBoxSize() {
            return hitBoxSize;
        }
    }
}
