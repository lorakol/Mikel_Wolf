package com.training.digginggame;

import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.training.digginggame.activity.GameActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GameBoardViewTest {

    @Rule
    public ActivityTestRule<GameActivity> activityRule = new ActivityTestRule<>(GameActivity.class);

    @Test
    public void testGameBoardInteraction() {
        Espresso.onView(withId(R.id.gameBoardView))
                .perform(swipeRight())
                .check(// Add assertions or further checks);
    }

    // ... other tests
}
