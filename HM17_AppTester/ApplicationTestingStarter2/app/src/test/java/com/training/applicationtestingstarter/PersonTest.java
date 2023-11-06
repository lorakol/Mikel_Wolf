package com.training.applicationtestingstarter;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.training.applicationtestingstarter.object.Person;

import org.junit.Test;

public class PersonTest {

    @Test
    public void personInitializationTest() {
        // Create a new person
        Person person = new Person("John", "Doe", 25);

        // Test methods
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals(25, person.getAge());
    }

    @Test
    public void equalsMethodTest() {
        Person person1 = new Person("John", "Doe", 25);
        Person person2 = new Person("John", "Doe", 25);
        Person person3 = new Person("Jane", "Doe", 30);

        // Test similar objects
        assertTrue(person1.equals(person2));

        // Test different objects
        assertFalse(person1.equals(person3));
    }

    @Test
    public void toStringMethodTest() {
        Person person = new Person("John", "Doe", 25);

        // Test the toString method
        assertEquals("John Doe - 25", person.toString());
    }

}
