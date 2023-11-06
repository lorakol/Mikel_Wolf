package com.training.applicationtestingstarter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.training.applicationtestingstarter.object.Person;
import com.training.applicationtestingstarter.util.PersonStorageUtil;

import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class PersonStorageUtilTest {
    @Test
    public void saveOnePersonAndLoadTest() {
        Context mockContext = InstrumentationRegistry.getInstrumentation().getTargetContext(); //ApplicationProvider.getApplicationContext();
        ArrayList<Person> emptyPersons = PersonStorageUtil.loadPeople(mockContext);
        int currentSize = emptyPersons.size();

        // Create a new person
        Person person = new Person("John", "Doe", 25);

        PersonStorageUtil.savePerson(mockContext, person);
        ArrayList<Person> onePersons = PersonStorageUtil.loadPeople(mockContext);

        assertEquals(currentSize + 1, onePersons.size());
        Person onePerson = onePersons.get(currentSize);
        // Test methods
        assertEquals("John", onePerson.getFirstName());
        assertEquals("Doe", onePerson.getLastName());
        assertEquals(25, onePerson.getAge());
    }

    @Test
    public void saveMultiPersonAndDeleteTest() {
        Context mockContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<Person> oldPersons = PersonStorageUtil.loadPeople(mockContext);
        int currentSize = oldPersons.size();

        // Create a new person
        Person person1 = new Person("John", "Doe", 25);
        Person person2 = new Person("Smith", "With", 35);
        Person person3 = new Person("Kall", "Bench", 15);

        PersonStorageUtil.savePerson(mockContext, person1);
        PersonStorageUtil.savePerson(mockContext, person2);
        PersonStorageUtil.savePerson(mockContext, person3);
        ArrayList<Person> multiPersons = PersonStorageUtil.loadPeople(mockContext);
        assertEquals(currentSize + 3, multiPersons.size());

        PersonStorageUtil.deletePerson(mockContext, person2);
        ArrayList<Person> deletePersons = PersonStorageUtil.loadPeople(mockContext);
        assertEquals(currentSize + 2, deletePersons.size());
        Person onePerson = deletePersons.get(currentSize);
        Person secondPerson = deletePersons.get(currentSize);

        // Test methods
        assertEquals("John", onePerson.getFirstName());
        assertEquals("Doe", onePerson.getLastName());
        assertEquals(25, onePerson.getAge());

        assertEquals("Kall", secondPerson.getFirstName());
        assertEquals("Bench", secondPerson.getLastName());
        assertEquals(15, secondPerson.getAge());
    }


}
