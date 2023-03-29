package com.example.ce04;

import android.graphics.drawable.Drawable;

public class Person {

    private String mFirstName;
    private String mLastName;
    private String mBirthday;
    private Drawable mPicture;

    public Person(String firstName, String lastName, String birthday, Drawable picture) {
        mFirstName = firstName;
        mLastName = lastName;
        mBirthday = birthday;
        mPicture = picture;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public Drawable getPicture() {
        return mPicture;
    }

    public void setPicture(Drawable picture) {
        mPicture = picture;
    }
}

