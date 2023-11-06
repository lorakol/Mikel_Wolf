package com.training.digginggame;

import com.training.digginggame.object.Item;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private List<Item> items;
    private List<Item> foundItems;
    private int totalGold = 0;

    public GameModel() {
        items = new ArrayList<>();
        foundItems = new ArrayList<>();
        // Load items from CSV or any other source
        // Assign random positions to the items
    }

    /**
     * Handle tap and check if any items are close to the tapped location.
     * If yes, add them to the foundItems list and update the total gold.
     *
     * @param x x-coordinate of the tap
     * @param y y-coordinate of the tap
     */
    public void handleTap(float x, float y) {
        for (Item item : items) {
            if (!foundItems.contains(item) && isCloseToTap(x, y, item)) {
                foundItems.add(item);
                totalGold += item.getValue();
            }
        }
    }

    /**
     * Check if the item is close enough to the tapped location.
     * This is just a placeholder, you might need to replace it with
     * actual logic based on how you determine the item positions.
     *
     * @param x    x-coordinate of the tap
     * @param y    y-coordinate of the tap
     * @param item the item to check
     * @return true if the item is close to the tap, false otherwise
     */
    private boolean isCloseToTap(float x, float y, Item item) {
        // Placeholder logic. Adjust as necessary.
        float distance = (float) Math.sqrt(Math.pow(x - item.x, 2) + Math.pow(y - item.y, 2));
        return distance < 50; // Example threshold
    }

    /**
     * Get the list of items that have been found.
     *
     * @return a list of found items
     */
    public List<Item> getFoundItems() {
        return new ArrayList<>(foundItems);
    }

    /**
     * Get the total amount of gold from the found items.
     *
     * @return total gold
     */
    public int getTotalGold() {
        return totalGold;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void addFoundItem(Item item){
        foundItems.add(item);
        totalGold += item.getValue();
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    // Additional game-related methods...
}
