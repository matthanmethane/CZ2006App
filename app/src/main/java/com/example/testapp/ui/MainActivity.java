package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

        // this is how the data repository can be fetched
        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CallingFromSearchButtonListener");

                //
                // this is how to interact with the data repository
                //

                // fetching secondary schools with the word 'SECONDARY' inside, and with Malay and Computing
                List<String> courses = new ArrayList<>();
                courses.add("MALAY");
                courses.add("COMPUTING");

                // in this case we are not specifying any CCAs in particular, so pass an empty arraylist (don't pass null because code will break)
                List<SchoolEntity> secondarySchools = dataRepository.findSchools("", new ArrayList<>(), courses, 2, -1);
                for (SchoolEntity school : secondarySchools)
                {
                    System.out.println(school.schoolName);
                }
            }
        });
    }
}