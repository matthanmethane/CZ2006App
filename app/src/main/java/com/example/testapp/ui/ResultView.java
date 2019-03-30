package com.example.testapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ResultView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);

        Intent intent = getIntent();
        int schoolLevel = intent.getIntExtra("schoolLevel",-1);
        String address = intent.getStringExtra("address");
        String[] courses = intent.getStringArrayExtra("courses");
        String[] ccas = intent.getStringArrayExtra("ccas");

        // Convert courses and ccas to array list
        List<String> selectedCourses = new ArrayList<>();
        List<String> selectedCcas = new ArrayList<>();
        if (courses.length != 0) {
            selectedCourses = Arrays.asList(courses);
        }
        if (ccas.length != 0) {
            selectedCcas = Arrays.asList(ccas);
        }

        // Display school results
        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        List<SchoolEntity> schools = dataRepository.findSchools("",selectedCcas,selectedCourses,schoolLevel,-1);

        LinearLayout schoolList = findViewById(R.id.schoolList);
        for (int i =0; i < schools.size(); i ++) {
            TextView schoolName = new TextView(this);
            // Do formatting for school box here
            schoolName.setText(schools.get(i).getSchoolName());
            schoolList.addView(schoolName);

            SchoolEntity school = schools.get(i);
            schoolName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent openSearch = new Intent(getApplicationContext(),InformationView.class);
                    // Insert school info here
                    openSearch.putExtra("schoolName",school.getSchoolName());
                    openSearch.putExtra("schoolMission",school.getMission());
                    startActivity(openSearch);
                }
            });
            }
    }
}