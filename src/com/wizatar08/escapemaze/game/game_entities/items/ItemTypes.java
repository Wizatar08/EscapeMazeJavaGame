package com.wizatar08.escapemaze.game.game_entities.items;

public class ItemTypes {
    public enum KeyTypes {
        NONE, RED, ORANGE, YELLOW, GREEN, BLUE, DARK_BLUE, PURPLE, PINK, ALL;
    }

    public enum Jewels {
        NONE(0.0f), DIAMOND(1.0f), RUBY(2.0f), EMERALD(2.5f);
        private float weight;

        Jewels(float weight) {
            this.weight = weight;
        }

        public float getWeight() {
            return weight;
        }
    }
}
