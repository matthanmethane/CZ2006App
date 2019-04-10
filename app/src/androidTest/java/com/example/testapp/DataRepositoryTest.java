package com.example.testapp;

import com.example.testapp.db.entity.SchoolEntity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static junit.framework.TestCase.assertEquals;

public class DataRepositoryTest extends AppCompatActivity {

    @Test
    public void findSchools() {
        // Test 1
        String pattern = "Tao";
        List<String> ccas = new ArrayList<>();
        ccas.add("ADVENTURE CLUB");
        List<String> courses = new ArrayList<>();
        courses.add("SCIENCE");
        int schoolLevel = 1;

        List<SchoolEntity> output;
        List<String> expected = new ArrayList<>();
        expected.add("TAO NAN SCHOOL");

        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        output = dataRepository.findSchools(pattern,ccas,courses,schoolLevel,-1);

        assertEquals(expected,output.get(0).getSchoolName());

    }
}