package com.wizatar08.escapemaze.game.game_entities.enemies;

import com.wizatar08.escapemaze.helpers.IDTypes;
import com.wizatar08.escapemaze.helpers.VariationID;

import java.util.*;

public enum EnemyType {
    // IDTYPE: 2
    CUBE_SCANNER(new VariationID(IDTypes.ENEMY, "001", "00"), "cube_scanner", new Builder().speed(30.0f).viewDistance(256).angleOfView(60)),
    SPEED_GUARD(new VariationID(IDTypes.ENEMY, "002", "00"), "speed_guard", new Builder().speed(72.0f).viewDistance(128).angleOfView(60));

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
    private int viewDistance, angleOfView;

    public static HashMap<String, EnemyType> ENEMY_IDS;

    EnemyType(VariationID id, String texture, Builder builder) {
        createIdMapAndArrays();
        addToMap(id.getFullId(), this);
        this.id = id.getFullId();
        this.texture = texture;
        this.speed = builder.getSpeed();
        this.viewDistance = builder.getViewDistance();
        this.angleOfView = builder.getangleOfView();
        this.alarmSpeed = builder.getAlarmSpeed();
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

    /**
     * Tile builder class
     */
    private static class Builder {
        private float speed;
        private float alarmSpeed;
        private int viewDistance;
        private int angleOfView;

        /**
         * Builder constructor. Defines all variables to its default value.
         */
        private Builder() {
            speed = 1.0f;
            viewDistance = 64;
            angleOfView = 45;
            alarmSpeed = 2.0f;
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
    }
}
