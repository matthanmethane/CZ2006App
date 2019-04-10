package com.example.testapp;

import android.app.Application;
import android.content.Context;

import com.example.testapp.db.entity.SchoolEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DataRepositoryTest {
    private DataRepository dbRepo;

    @Before
    public void beforeEachTestMethod() {
        Context appContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
        Application app2 = (Application) appContext;
        EmpathyApp actualApp = (EmpathyApp) app2;
        dbRepo = actualApp.getRepository();
    }

    @Test
    public void findSchoolsTestCase1() {
        String pattern = "Tao";
        List<String> ccas = new ArrayList<>();
        ccas.add("ADVENTURE CLUB");
        List<String> courses = new ArrayList<>();
        courses.add("SCIENCE");
        int schoolLevel = 1;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "TAO NAN SCHOOL";
        expected.add(expectedSchlName1);

        List<SchoolEntity> rawSearchResults = dbRepo.findSchools(pattern,ccas,courses,schoolLevel,-1);
        List<String> searchResultsAsString = new ArrayList<>();
        for (SchoolEntity sekolah : rawSearchResults)
        {
            searchResultsAsString.add(sekolah.schoolName);
        }
        assertEquals(expected, searchResultsAsString);
    }

    @Test
    public void findSchoolsTestCase2() {
        String pattern = null;
        List<String> ccas = new ArrayList<>();
        ccas.add("ART AND CRAFT CLUB");
        ccas.add("NCC (LAND)");
        List<String> courses = new ArrayList<>();
        courses.add("HIGHER MUSIC");
        int schoolLevel = 2;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Nan Chiau High School".toUpperCase();
        expected.add(expectedSchlName1);

        List<SchoolEntity> rawSearchResults = dbRepo.findSchools(pattern,ccas,courses,schoolLevel,-1);
        List<String> searchResultsAsString = new ArrayList<>();
        for (SchoolEntity sekolah : rawSearchResults)
        {
            searchResultsAsString.add(sekolah.schoolName);
        }
        assertEquals(expected, searchResultsAsString);
    }
}