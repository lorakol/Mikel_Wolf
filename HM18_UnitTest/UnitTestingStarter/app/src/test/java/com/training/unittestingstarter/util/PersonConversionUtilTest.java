package com.training.unittestingstarter.util;

import com.training.unittestingstarter.object.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PersonConversionUtilTest {

    private ArrayList<Person> samplePeople;
    private JSONArray sampleJsonArray;

    @Before
    public void setUp() throws JSONException {
        samplePeople = new ArrayList<>();

        JSONObject sampleJsonObject1 = new JSONObject();
        sampleJsonObject1.put("first_name", "John");
        sampleJsonObject1.put("last_name", "Doe");
        sampleJsonObject1.put("phone_number", "123-456-7890");
        sampleJsonObject1.put("age", 25);

        JSONObject sampleJsonObject2 = new JSONObject();
        sampleJsonObject2.put("first_name", "Jane");
        sampleJsonObject2.put("last_name", "Smith");
        sampleJsonObject2.put("phone_number", "987-654-3210");
        sampleJsonObject2.put("age", 30);

        sampleJsonArray = new JSONArray();
        sampleJsonArray.put(sampleJsonObject1);
        sampleJsonArray.put(sampleJsonObject2);

        samplePeople.add(new Person(sampleJsonObject1));
        samplePeople.add(new Person(sampleJsonObject2));
    }

    @Test
    public void testGetPeopleListFromJSON() throws JSONException {
        ArrayList<Person> result = PersonConversionUtil.getPeopleListFromJSON(sampleJsonArray);

        assertEquals(2, result.size());

        Person person1 = result.get(0);
        assertEquals("John", person1.getFirstName());
        assertEquals("Doe", person1.getLastName());
        assertEquals("123-456-7890", person1.getPhoneNumber());
        assertEquals(25, person1.getAge());

        Person person2 = result.get(1);
        assertEquals("Jane", person2.getFirstName());
        assertEquals("Smith", person2.getLastName());
        assertEquals("987-654-3210", person2.getPhoneNumber());
        assertEquals(30, person2.getAge());
    }

    @Test
    public void testGetPeopleJSONFromList() throws JSONException {
        JSONArray result = PersonConversionUtil.getPeopleJSONFromList(samplePeople);

        assertEquals(2, result.length());

        JSONObject personJson1 = result.getJSONObject(0);
        assertEquals("John", personJson1.getString("first_name"));
        assertEquals("Doe", personJson1.getString("last_name"));
        assertEquals("123-456-7890", personJson1.getString("phone_number"));
        assertEquals(25, personJson1.getInt("age"));

        JSONObject personJson2 = result.getJSONObject(1);
        assertEquals("Jane", personJson2.getString("first_name"));
        assertEquals("Smith", personJson2.getString("last_name"));
        assertEquals("987-654-3210", personJson2.getString("phone_number"));
        assertEquals(30, personJson2.getInt("age"));
    }
}
