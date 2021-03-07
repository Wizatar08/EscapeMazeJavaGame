package com.wizatar08.escapemaze.game.game_entities.enemies;

import com.wizatar08.escapemaze.helpers.IDTypes;
import com.wizatar08.escapemaze.helpers.VariationID;

import java.util.*;

public enum EnemyType {
    CUBE_SCANNER(new VariationID(IDTypes.ENEMY, "001", "00"), "cube_scanner", new Builder().speed(30.0f).viewDistance(256).angleOfView(60));

    // Initialize variables
    private String id;
    private String texture;
    private float speed;
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



    /**
     * Tile builder class
     */
    private static class Builder {
        private float speed;
        private int viewDistance;
        private int angleOfView;

        /**
         * Builder constructor. Defines all variables to its default value.
         */
        private Builder() {
            speed = 1.0f;
            viewDistance = 64;
            angleOfView = 45;
        }

        private Builder speed(float speed) {
            this.speed = speed;
            return this;
        }

        private Builder viewDistance(int viewDistance) {
            this.viewDistance = viewDistance;
            return this;
        }

        private Builder angleOfView(int angleOfView) {
            this.angleOfView = viewDistance;
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
    }
}
