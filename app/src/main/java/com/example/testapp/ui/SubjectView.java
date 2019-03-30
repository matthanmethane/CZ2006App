package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SubjectView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_view);

        Intent intent = getIntent();
        int schoolLevel = intent.getIntExtra("schoolLevel", -1);
        String address = intent.getStringExtra("address");

        // Display all Subjects
        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        List<String> allCourses = dataRepository.getCoursesForLevel(schoolLevel);
        LinearLayout ll = findViewById(R.id.courseLL);

        for (String course : allCourses) {
            CheckBox courseCheckbox = new CheckBox(this);
            courseCheckbox.setText(course);
            ll.addView(courseCheckbox);
        }

        // Submission
        Button submitSubject = (Button) findViewById(R.id.submit_subject);
        submitSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get chosen courses
                List<String> selectedCourses = new ArrayList<String>();
                final int childCount = ll.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    CheckBox box = (CheckBox)ll.getChildAt(i);
                    if (box.isChecked()) {
                        selectedCourses.add(box.getText().toString());
                    }
                }
                String[] courses = selectedCourses.toArray(new String[0]);

                Intent openCca = new Intent(getApplicationContext(), CcaView.class);
                openCca.putExtra("schoolLevel",schoolLevel);
                openCca.putExtra("address",address);
                openCca.putExtra("courses",courses);
                startActivity(openCca);
            }
        });
    }
}