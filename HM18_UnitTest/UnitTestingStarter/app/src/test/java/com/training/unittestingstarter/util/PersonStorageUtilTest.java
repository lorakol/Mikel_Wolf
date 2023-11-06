package com.training.unittestingstarter.util;

import android.content.Context;

import com.training.unittestingstarter.object.Person;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class PersonStorageUtilTest {

    @Mock
    private Context mockContext;

    @Mock
    private File mockExternalStorage, mockPersonFile;

    @Mock
    private FileOutputStream mockOutputStream;

    @Mock
    private FileInputStream mockInputStream;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mockContext.getExternalFilesDir(PersonStorageUtil.FILE_FOLDER)).thenReturn(mockExternalStorage);
        when(mockExternalStorage.exists()).thenReturn(true);
        when(new File(mockExternalStorage, PersonStorageUtil.FILE_NAME)).thenReturn(mockPersonFile);
        when(mockPersonFile.exists()).thenReturn(true);
        when(mockPersonFile.delete()).thenReturn(true);
        when(new FileOutputStream(mockPersonFile)).thenReturn(mockOutputStream);
        when(new FileInputStream(mockPersonFile)).thenReturn(mockInputStream);
        doNothing().when(mockOutputStream).write(any());
        doNothing().when(mockOutputStream).close();
    }

    @Test
    public void testSavePerson() {
        Person person = new Person("John", "Doe", "123-456-7890", 25);
        PersonStorageUtil.savePerson(mockContext, person);
        // Here you can add assertions based on expected behaviors or outcomes.
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person("John", "Doe", "123-456-7890", 25);
        PersonStorageUtil.deletePerson(mockContext, person);
        // Here you can add assertions based on expected behaviors or outcomes.
    }

    @Test
    public void testLoadPeople() {
        ArrayList<Person> people = PersonStorageUtil.loadPeople(mockContext);
        // Assuming an empty file, the list should be empty.
        assertEquals(0, people.size());
    }

    @Test
    public void testDeletePersonFile() {
        PersonStorageUtil.deletePersonFile(mockContext);
        // Here you can add assertions based on expected behaviors or outcomes.
    }
}

