package com.training.unittestingstarter;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.training.unittestingstarter.fragment.SettingsFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

    @Rule
    public ActivityTestRule<SettingsActivity> activityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void testSettingsFragmentIsDisplayed() {
        // Get the activity instance
        SettingsActivity activity = activityRule.getActivity();

        // Fetch the fragment by tag
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(SettingsFragment.TAG);

        // Ensure the fragment is not null and it's an instance of SettingsFragment
        assertNotNull(fragment);
        assertTrue(fragment instanceof SettingsFragment);
    }


}

