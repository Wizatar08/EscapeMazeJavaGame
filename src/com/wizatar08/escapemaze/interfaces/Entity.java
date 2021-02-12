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
    int getWidth();

    /**
     * Get height of entity
     */
    int getHeight();

    /**
     * Set X position of entity
     */
    void setX(int x);

    /**
     * Set Y position of entity
     */
    void setY(int y);

    /**
     * Set width of entity
     */
    void setWidth(int width);

    /**
     * Set height of entity
     */
    void setHeight(int height);
}
