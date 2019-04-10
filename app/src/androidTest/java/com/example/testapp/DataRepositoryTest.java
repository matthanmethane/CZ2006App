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
        String pattern = "";
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

    @Test
    public void findSchoolsTestCase3() {
        String pattern = "";
        List<String> ccas = new ArrayList<>();
        ccas.add("Badminton".toUpperCase());
        List<String> courses = new ArrayList<>();
        courses.add("French".toUpperCase());
        int schoolLevel = 3;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Dunman High School".toUpperCase();
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
    public void findSchoolsTestCase4() {
        String pattern = "";
        List<String> ccas = new ArrayList<>();
        ccas.add("Table Tennis".toUpperCase());
        ccas.add("Library".toUpperCase());
        ccas.add("Science Club".toUpperCase());

        List<String> courses = new ArrayList<>();
        int schoolLevel = 1;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Radin Mas Primary School".toUpperCase();
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
    public void findSchoolsTestCase5() {
        String pattern = "";
        List<String> ccas = new ArrayList<>();
        ccas.add("Aero-modelling".toUpperCase());
        ccas.add("Photographic Society".toUpperCase());
        List<String> courses = new ArrayList<>();
        int schoolLevel = 2;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Northbrooks Secondary School".toUpperCase();
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
    public void findSchoolsTestCase6() {
        String pattern = "";
        List<String> ccas = new ArrayList<>();
        ccas.add("Archery".toUpperCase());
        List<String> courses = new ArrayList<>();
        int schoolLevel = 3;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Tampines Meridian Junior College".toUpperCase();
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
    public void findSchoolsTestCase7() {
        String pattern = "";
        List<String> ccas = new ArrayList<>();
        List<String> courses = new ArrayList<>();
        courses.add("FOUNDATION TAMIL".toUpperCase());
        courses.add("FOUNDATION SCIENCE(NON EXAM)".toUpperCase());
        int schoolLevel = 1;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Blangah Rise Primary School".toUpperCase();
        String expectedSchlName2 = "Farrer Park Primary School".toUpperCase();
        String expectedSchlName3 = "Huamin Primary School".toUpperCase();

        expected.add(expectedSchlName1);
        expected.add(expectedSchlName2);
        expected.add(expectedSchlName3);

        List<SchoolEntity> rawSearchResults = dbRepo.findSchools(pattern,ccas,courses,schoolLevel,-1);
        List<String> searchResultsAsString = new ArrayList<>();
        for (SchoolEntity sekolah : rawSearchResults)
        {
            searchResultsAsString.add(sekolah.schoolName);
        }
        assertEquals(expected, searchResultsAsString);
    }

    @Test
    public void findSchoolsTestCase8() {
        String pattern = "";
        List<String> ccas = new ArrayList<>();
        List<String> courses = new ArrayList<>();
        courses.add("SCIENCE & TECHNOLOGY".toUpperCase());
        int schoolLevel = 2;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Spectra Secondary School".toUpperCase();
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
    public void findSchoolsTestCase9() {
        String pattern = "";
        List<String> ccas = new ArrayList<>();
        List<String> courses = new ArrayList<>();
        courses.add("H3 Mathematics".toUpperCase());
        courses.add("H2 Music".toUpperCase());
        int schoolLevel = 3;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Dunman High School".toUpperCase();
        String expectedSchlName2 = "National Junior College".toUpperCase();
        String expectedSchlName3 = "Temasek Junior College".toUpperCase();
        expected.add(expectedSchlName1);
        expected.add(expectedSchlName2);
        expected.add(expectedSchlName3);

        List<SchoolEntity> rawSearchResults = dbRepo.findSchools(pattern,ccas,courses,schoolLevel,-1);
        List<String> searchResultsAsString = new ArrayList<>();
        for (SchoolEntity sekolah : rawSearchResults)
        {
            searchResultsAsString.add(sekolah.schoolName);
        }
        assertEquals(expected, searchResultsAsString);
    }

    @Test
    public void findSchoolsTestCase10() {
        String pattern = "Chongfu".toUpperCase();
        List<String> ccas = new ArrayList<>();
        List<String> courses = new ArrayList<>();
        int schoolLevel = 1;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Chongfu School".toUpperCase();
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
    public void findSchoolsTestCase11() {
        String pattern = "Serangoon".toUpperCase();
        List<String> ccas = new ArrayList<>();
        List<String> courses = new ArrayList<>();
        int schoolLevel = 2;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Serangoon Garden Secondary School".toUpperCase();
        expected.add(expectedSchlName1);
        String expectedSchlName2 = "Serangoon Secondary School".toUpperCase();
        expected.add(expectedSchlName2);

        List<SchoolEntity> rawSearchResults = dbRepo.findSchools(pattern,ccas,courses,schoolLevel,-1);
        List<String> searchResultsAsString = new ArrayList<>();
        for (SchoolEntity sekolah : rawSearchResults)
        {
            searchResultsAsString.add(sekolah.schoolName);
        }
        assertEquals(expected, searchResultsAsString);
    }


    @Test
    public void findSchoolsTestCase12() {
        String pattern = "Raffles".toUpperCase();
        List<String> ccas = new ArrayList<>();
        List<String> courses = new ArrayList<>();
        int schoolLevel = 3;

        List<String> expected = new ArrayList<>();
        String expectedSchlName1 = "Raffles Institution".toUpperCase();
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