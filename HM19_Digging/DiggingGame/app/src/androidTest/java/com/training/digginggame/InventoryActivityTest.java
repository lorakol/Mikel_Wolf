package com.training.digginggame;



import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.training.digginggame.activity.InventoryActivity;

@RunWith(AndroidJUnit4.class)
public class InventoryActivityTest {

    @Rule
    public ActivityScenarioRule<InventoryActivity> activityRule = new ActivityScenarioRule<>(InventoryActivity.class);

    @Test
    public void testInventoryDisplay() {
        // Here's a simple test to ensure the inventory list is displayed.
        // You'll need to replace R.id.inventoryList with your actual RecyclerView/List id.
//        Espresso.onView(withId(R.id.inventoryList))
//                .check(matches(isDisplayed()));

        // You can add more specific checks, e.g., if a specific item exists in the inventory.
        // Espresso.onView(withText("Some item name")).check(matches(isDisplayed()));
    }

    // ... add more tests based on other behaviors of the InventoryActivity
}

