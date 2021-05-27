package com.wizatar08.escapemaze.game.events;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.visuals.Drawer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

public class ItemTouchAndRemoveEvent extends Event {
    private final Item centerItem;
    private final ArrayList<ItemType> itemsNeeded;
    private final Timer timeTillTrigger;
    private Item.Condition condition;
    private String triggerMethodName;

    public ItemTouchAndRemoveEvent(Item centerItem, String triggerMethodName, Item.Condition condition, int timeTillTrigger, ItemType... otherItems) {
        super();
        this.centerItem = centerItem;
        this.timeTillTrigger = new Timer(Timer.TimerModes.COUNT_DOWN, timeTillTrigger);
        this.timeTillTrigger.pause();
        this.condition = condition;
        this.triggerMethodName = triggerMethodName;
        itemsNeeded = new ArrayList<>();
        Collections.addAll(itemsNeeded, otherItems);
    }

    @Override
    public boolean isOccuring() {
        return true;
    }

    @Override
    public void update() {
        timeTillTrigger.update();
        ArrayList<ItemType> types = new ArrayList<ItemType>(itemsNeeded);
        ArrayList<Item> items = new ArrayList<>();
        for (Item item1 : getGameController().getItems()) {
            if (Drawer.checkCollision(item1.getX(), item1.getY(), item1.getWidth(), item1.getHeight(), centerItem.getX(), centerItem.getY(), centerItem.getWidth(), centerItem.getHeight()) && itemsNeeded.contains(item1.getType())
                && item1 != centerItem) {
                types.remove(item1.getType());
                items.add(item1);
            }
        }
        if (types.size() <= 0 && condition.passes()) {
            timeTillTrigger.unpause();
            if (timeTillTrigger.getTotalSeconds() <= 0) {
                try {
                    centerItem.getClass().getMethod(triggerMethodName, ArrayList.class).invoke(centerItem, items);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                for (Item item : items) {
                    getGameController().removeItemFromGame(item);
                    timeTillTrigger.pause();
                    timeTillTrigger.setTime(timeTillTrigger.getStartingSeconds());
                }
            }
        } else {
            timeTillTrigger.pause();
            timeTillTrigger.setTime(timeTillTrigger.getStartingSeconds());
        }
    }
}
