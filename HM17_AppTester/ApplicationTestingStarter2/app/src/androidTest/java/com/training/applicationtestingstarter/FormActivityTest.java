package com.training.applicationtestingstarter;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
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
public class FormActivityTest {

    @Rule
    public ActivityScenarioRule<FormActivity> mActivityRule = new ActivityScenarioRule<>(FormActivity.class);

    @Test
    public void fillOutFormAndSave() {
        // Assuming you have EditTexts with the IDs firstName, lastName, and age.
        onView(withId(R.id.edit_first_name)).perform(typeText("John"));
        onView(withId(R.id.edit_last_name)).perform(typeText("Doe"));
        onView(withId(R.id.edit_age)).perform(typeText("25"));

        // Close the keyboard (sometimes necessary if the button we want to click next is covered by the keyboard)
        onView(withId(R.id.edit_age)).perform(closeSoftKeyboard());

        // Assuming you have a save button with the ID saveButton.
        onView(withId(R.id.action_save)).perform(click());

    }

    @After
    public void tearDown() {
        mActivityRule.getScenario().close();
    }
}
