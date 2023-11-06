package com.training.unittestingstarter;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
//import androidx.test.espresso. .intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import com.training.unittestingstarter.fragment.PersonListFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class MainActivityTest {

    //@Before
    //public void setUp() {
      //  Intents.init();
    //}
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testPersonListFragmentIsDisplayed() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(PersonListFragment.TAG);
                assertNotNull(fragment);
                assertTrue(fragment instanceof PersonListFragment);
            });
        }
    }

    @Test
    public void testFormActivityIsStarted() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            //Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
            Espresso.onView(withId(R.id.action_add)).perform(click());

            Espresso.onView(withId(R.id.edit_first_name)).check(matches(isDisplayed()));
            //intended(hasComponent(FormActivity.class.getName()));
        }
    }

    @Test
    public void testSettingsActivityIsStarted() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            //Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
            Espresso.onView(withId(R.id.action_settings)).perform(click());
            //intended(hasComponent(SettingsActivity.class.getName()));
        }
    }

    // Add more tests if you expand functionality in MainActivity.

    @After
    public void cleanUp() {
        mActivityRule.getScenario().close();
    }
}

