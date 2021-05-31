package com.wizatar08.escapemaze.interfaces;

public interface Entity {

    /**
     * Get X position
     * @return
     */
    float getX();

    /**
     * Get Y position
     */
    float getY();

    /**
     * Get width of entity
     */
    float getWidth();

    /**
     * Get height of entity
     */
    float getHeight();

    /**
     * Set X position of entity
     */
    void setX(float x);

    /**
     * Set Y position of entity
     */
    void setY(float y);

    /**
     * Set width of entity
     */
    void setWidth(float width);

    /**
     * Set height of entity
     */
    void setHeight(float height);

    /**
     * Draw the entity
     */
    void draw();

    /**
     * Update the entity
     */
    void update();

    /**
     * Run this when collided with another entity
     */
    void onCollisionWith(Entity entity);
}
