package com.training.applicationtestingstarter;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UiTest {

    private static final String APP_PACKAGE = "com.training.applicationtestingstarter";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(APP_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

    @Test
    public void addAndDeletePerson() {

        //add person
        mDevice.findObject(By.res(APP_PACKAGE, "action_add")).click();

        UiObject2 firstName = mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "edit_first_name")),LAUNCH_TIMEOUT);
        firstName.setText("John");
        mDevice.findObject(By.res(APP_PACKAGE, "edit_last_name")).setText("Doe");
        mDevice.findObject(By.res(APP_PACKAGE, "edit_age")).setText("25");
        mDevice.findObject(By.res(APP_PACKAGE, "action_save")).click();

        //show detial
        //UiObject2 listview = mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "person_list")),LAUNCH_TIMEOUT);
        UiScrollable listView = new UiScrollable(new UiSelector());
        String name = "John Doe - 25";
        listView.setMaxSearchSwipes(100);
        try {
            listView.scrollTextIntoView(name);
            listView.waitForExists(5000);
            UiObject listViewItem = listView.getChildByText(new UiSelector()
                    .className(android.widget.TextView.class.getName()), ""+name+"");
            listViewItem.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }


        //delete person
        UiObject2 first = mDevice.wait(Until.findObject(By.res(APP_PACKAGE, "text_first_name")),LAUNCH_TIMEOUT);
        mDevice.findObject(By.res(APP_PACKAGE, "action_delete")).click();


    }




    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
