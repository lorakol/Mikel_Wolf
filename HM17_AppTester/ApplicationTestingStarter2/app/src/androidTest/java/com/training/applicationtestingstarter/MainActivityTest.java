package com.training.applicationtestingstarter;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void exitListFragment() {
        Espresso.onView(withId(R.id.fragment_container)) // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion
    }

    @Test
    public void clickActionBarShowFormActivity() {
        Espresso.onView(withId(R.id.action_add)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.action_add)).perform(click());
        Espresso.onView(withId(R.id.edit_first_name)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() {
        mActivityRule.getScenario().close();
    }
}
