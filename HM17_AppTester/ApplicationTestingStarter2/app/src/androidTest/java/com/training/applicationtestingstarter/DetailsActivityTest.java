package com.training.applicationtestingstarter;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void fillOutDetailAndDelete() {

        ActivityScenario<MainActivity> scenario = mActivityRule.getScenario();
        String firstName = "John";
        String lastName = "Doe";
        int age = 25;
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_FIRST_NAME, firstName);
        intent.putExtra(DetailsActivity.EXTRA_LAST_NAME, lastName);
        intent.putExtra(DetailsActivity.EXTRA_AGE, age);
        //ActivityScenario<DetailsActivity> test = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            // Launch SecondActivity with intent containing extras
            activity.startActivity(intent);
        });
        //check show view
        onView(withId(R.id.text_first_name)).check(matches(isDisplayed()));
        onView(withId(R.id.text_last_name)).check(matches(isDisplayed()));
        onView(withId(R.id.text_age)).check(matches(isDisplayed()));

        //check text value
        onView(withId(R.id.text_first_name)).check(matches(withText(firstName)));
        onView(withId(R.id.text_last_name)).check(matches(withText(lastName)));
        onView(withId(R.id.text_age)).check(matches(withText(String.valueOf(age))));

        onView(withId(R.id.action_delete)).perform(click());

    }

    @After
    public void tearDown() {
        mActivityRule.getScenario().close();
    }
}
