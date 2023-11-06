package com.training.applicationtestingstarter.object;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
    private String mFirstName;
    private String mLastName;
    private int mAge;

    public Person() {
        mFirstName = mLastName = "";
        mAge = -1;
    }

    public Person(String _first, String _last, int _age) {
        mFirstName = _first;
        mLastName = _last;
        mAge = _age;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public int getAge() {
        return mAge;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Person)) {
            return false;
        }

        Person other = (Person)obj;

        return Objects.equals(other.mFirstName, mFirstName) &&
                Objects.equals(other.mLastName, mLastName) &&
                other.mAge == mAge;
    }

    @Override
    public String toString() {
        return mFirstName + " " + mLastName + " - " + mAge;
    }
}
