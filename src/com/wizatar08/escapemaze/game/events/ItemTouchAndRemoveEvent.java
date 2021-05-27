package com.wizatar08.escapemaze.game.events;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.visuals.Drawer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

public class ItemTouchAndRemoveEvent extends ItemTouchEvent {

    public ItemTouchAndRemoveEvent(Item centerItem, String triggerMethodName, Item.Condition condition, int timeTillTrigger, ItemType... otherItems) {
        super(centerItem, triggerMethodName, condition, timeTillTrigger, otherItems);
    }

    @Override
    public void update() {
        getTimeTillTrigger().update();
        ArrayList<ItemType> types = new ArrayList<ItemType>(getItemsNeeded());
        ArrayList<Item> items = new ArrayList<>();
        for (Item item1 : getGameController().getItems()) {
            if (Drawer.checkCollision(item1.getX(), item1.getY(), item1.getWidth(), item1.getHeight(), getCenterItem().getX(), getCenterItem().getY(), getCenterItem().getWidth(), getCenterItem().getHeight()) && getItemsNeeded().contains(item1.getType())
                && item1 != getCenterItem()) {
                types.remove(item1.getType());
                items.add(item1);
            }
        }
        if (types.size() <= 0 && getCondition().passes()) {
            getTimeTillTrigger().unpause();
            if (getTimeTillTrigger().getTotalSeconds() <= 0) {
                try {
                    getCenterItem().getClass().getMethod(getTriggerMethodName(), ArrayList.class).invoke(getCenterItem(), items);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                for (Item item : items) {
                    getGameController().removeItemFromGame(item);
                    getTimeTillTrigger().pause();
                    getTimeTillTrigger().setTime(getTimeTillTrigger().getStartingSeconds());
                }
            }
        } else {
            getTimeTillTrigger().pause();
            getTimeTillTrigger().setTime(getTimeTillTrigger().getStartingSeconds());
        }
    }
}
