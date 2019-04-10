package com.example.testapp;

import android.app.Application;
import android.content.Context;

import com.example.testapp.db.entity.Bookmark;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class DataRepositoryTest {

    @Before
    public void beforeEachTestMethod() {
//        EmpathyApp app = new EmpathyApp();
        Context appContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
        Application app2 = (Application) appContext;
        System.err.println(app2);
        EmpathyApp actualApp = (EmpathyApp) app2;
        DataRepository dbRepo = actualApp.getRepository();
        List<Bookmark> bookmarks = dbRepo.getBookmarks();
        System.err.println(bookmarks.get(0).getSchoolName());
//        System.out.println(appContext.getPackageName());
//        appContext.getApplicationContext();
//        System.out.println("testResult: " + (app.getPackageName()));
//        System.out.println("actualResult: " + (appContext instanceof Application));
//        app = (EmpathyApp) appContext;
//        System.out.println(app.getPackageCodePath());
        System.err.println("Invoked before each test method");
    }

    @Test
    public void findSchools() {
//        // Test 1
//        String pattern = "Tao";
//        List<String> ccas = new ArrayList<>();
//        ccas.add("ADVENTURE CLUB");
//        List<String> courses = new ArrayList<>();
//        courses.add("SCIENCE");
//        int schoolLevel = 1;
//
//        List<SchoolEntity> output;
//        List<String> expected = new ArrayList<>();
//        expected.add("TAO NAN SCHOOL");
//
//        DataRepository dataRepository = app.getRepository();
//        output = dataRepository.findSchools(pattern,ccas,courses,schoolLevel,-1);
//
//        assertEquals(expected,output.get(0).getSchoolName());

    }
}