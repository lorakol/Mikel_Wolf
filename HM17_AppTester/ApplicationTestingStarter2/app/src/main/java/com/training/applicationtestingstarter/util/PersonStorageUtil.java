package com.training.applicationtestingstarter.util;

import android.content.Context;


import com.training.applicationtestingstarter.object.Person;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PersonStorageUtil {
    private static final String FILE_NAME = "people.dat";

    public static void savePerson(Context _context, Person _person) {
        ArrayList<Person> people = loadPeople(_context);
        people.add(_person);
        savePeople(_context, people);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    public static void deletePerson(Context _context, Person _person) {
        ArrayList<Person> people = loadPeople(_context);
        while(people.remove(_person));
        savePeople(_context, people);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Person> loadPeople(Context _context) {
        ArrayList<Person> people = null;

        try {
            FileInputStream fis = _context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            people = (ArrayList<Person>)ois.readObject();
            ois.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(people == null) {
            people = new ArrayList<>();
        }

        return people;
    }

    private static void savePeople(Context _context, ArrayList<Person> _people) {
        try {
            FileOutputStream fos = _context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_people);
            oos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
