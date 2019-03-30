package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.SchoolToCCA;
import com.example.testapp.db.entity.SchoolToCourse;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class InformationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_view);

        Intent intent = getIntent();
        String schoolName = intent.getStringExtra("schoolName");
        String mission = intent.getStringExtra("schoolMission");

        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        List<SchoolToCCA> ccas = dataRepository.getSchoolCCAs(schoolName);
        String[] ccaList = new String[ccas.size()];
        for (int i = 0; i < ccas.size(); i ++) {
            ccaList[i] = ccas.get(i).ccaName;
        }
        List<SchoolToCourse> courses = dataRepository.getSchoolCourses(schoolName);
        String[] courseList = new String[courses.size()];
        for (int i = 0; i < courses.size(); i ++) {
            courseList[i] = courses.get(i).courseName;
        }

        // Display information
        TextView schoolNameText = findViewById(R.id.full_textbox_school_name);
        schoolNameText.setText(schoolName);

        TextView ccaText = findViewById(R.id.full_textbox_cca);
        ccaText.setText(String.join(", ",ccaList));

        TextView courseText = findViewById(R.id.full_textbox_course);
        courseText.setText(String.join(", ",courseList));

        // Connect button
    }
}
