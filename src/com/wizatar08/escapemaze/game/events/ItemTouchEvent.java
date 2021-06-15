package com.wizatar08.escapemaze.game.events;

import com.wizatar08.escapemaze.game.game_entities.items.Item;
import com.wizatar08.escapemaze.game.game_entities.items.ItemType;
import com.wizatar08.escapemaze.helpers.Timer;
import com.wizatar08.escapemaze.visuals.Drawer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class ItemTouchEvent extends Event {
    private final Item centerItem;
    private final ArrayList<ItemType> itemsNeeded;
    private final Timer timeTillTrigger;
    private Item.Condition condition;
    private String triggerMethodName;
    private boolean isOccuring;

    public ItemTouchEvent(Item centerItem, String triggerMethodName, Item.Condition condition, int timeTillTrigger, ItemType... itemsToTouch) {
        super();
        this.centerItem = centerItem;
        this.timeTillTrigger = new Timer(Timer.TimerModes.COUNT_DOWN, timeTillTrigger);
        this.timeTillTrigger.pause();
        this.condition = condition;
        this.triggerMethodName = triggerMethodName;
        itemsNeeded = new ArrayList<>();
        Collections.addAll(itemsNeeded, itemsToTouch);
        this.isOccuring = false;
    }

    @Override
    public boolean isOccuring() {
        return isOccuring;
    }

    @Override
    public void update() {
        timeTillTrigger.update();
        ArrayList<ItemType> types = new ArrayList<ItemType>(itemsNeeded);
        ArrayList<Item> items = new ArrayList<>();
        for (Item item1 : getGameController().getItems()) {
            if (item1.getHitbox().collidesWith(centerItem) && itemsNeeded.contains(item1.getType()) && item1 != centerItem) {
                types.remove(item1.getType());
                items.add(item1);
            }
        }
        if (types.size() <= 0 && condition.passes()) {
            isOccuring = true;
            timeTillTrigger.unpause();
            if (timeTillTrigger.getTotalSeconds() <= 0) {
                try {
                    centerItem.getClass().getMethod(triggerMethodName, ArrayList.class).invoke(centerItem, items);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                timeTillTrigger.pause();
                timeTillTrigger.setTime(timeTillTrigger.getStartingSeconds());
            }
        } else {
            isOccuring = false;
            timeTillTrigger.pause();
            timeTillTrigger.setTime(timeTillTrigger.getStartingSeconds());
        }
    }

    public Item getCenterItem() {
        return centerItem;
    }
    public ArrayList<ItemType> getItemsNeeded() {
        return itemsNeeded;
    }
    public Timer getTimeTillTrigger() {
        return timeTillTrigger;
    }
    public Item.Condition getCondition() {
        return condition;
    }
    public String getTriggerMethodName() {
        return triggerMethodName;
    }
}
