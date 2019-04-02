package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The Main activity.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchBtn = findViewById(R.id.search);
        Button bookmarkBtn = findViewById(R.id.bookmark);
        Button newsBtn = findViewById(R.id.news);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSearch = new Intent(getApplicationContext(), SearchLevelView.class);
                startActivity(openSearch);
            }
        });

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
                List<String> courses = new ArrayList<>();
                courses.add("MALAY");
                courses.add("COMPUTING");
                //courses.add("MOBILE ROBOTICS");
                courses.add("HIGHER TAMIL");
                List<SchoolEntity> schools = dataRepository.findSchools("", new ArrayList<>(), courses, 2, -1);
                for (SchoolEntity school : schools)
                {
                    System.out.println(school.getSchoolName());
                }
                System.out.println("##################3");



                System.out.println("##################3");
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openArticle = new Intent(getApplicationContext(), ArticleView.class);
                startActivity(openArticle);
            }
        });
    }
}