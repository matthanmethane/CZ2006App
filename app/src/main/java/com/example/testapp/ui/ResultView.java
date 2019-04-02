package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.viewmodel.ResultViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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
        final List<SchoolEntity> schools = dataRepository.findSchools("",selectedCcas,selectedCourses,schoolLevel,-1);

        // Sort schools
        final ResultViewModel viewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        List<SchoolEntity> sorted = viewModel.sortSchools(schools);
        displaySchool(sorted);

        // Filter button
        Spinner spinner = findViewById(R.id.fliterList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                System.out.println(position);
                List<SchoolEntity> sorted;
                switch(position) {
                    case 0:
                        viewModel.setFilterMode(0);
                        break;
                    case 1:
                        viewModel.setUserPostalCode(address);
                        viewModel.setFilterMode(1);
                        break;
                    case 2:
                        viewModel.setFilterMode(2);
                        break;
                }
                sorted = viewModel.sortSchools(schools);
                displaySchool(sorted);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void displaySchool(List<SchoolEntity> schools) {
        LinearLayout schoolList = findViewById(R.id.schoolList);
        if(schoolList.getChildCount() > 0)
            schoolList.removeAllViews();

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
                    openSearch.putExtra("name",school.getSchoolName());
                    openSearch.putExtra("address",school.getPhysicalAddress());
                    openSearch.putExtra("postalCode",school.getPostalCode());
                    openSearch.putExtra("telephoneNumber1",school.getTelephoneNumber1());
                    openSearch.putExtra("telephoneNumber2",school.getTelephoneNumber2());
                    openSearch.putExtra("vision",school.getVision());
                    openSearch.putExtra("mission",school.getMission());
                    openSearch.putExtra("autonomyType",school.getSchoolAutonomyType());
                    openSearch.putExtra("gender",school.getSchoolGender());
                    openSearch.putExtra("giftedEducation",school.getGiftedEducationProgramOffered());
                    openSearch.putExtra("integratedProgram",school.getIntegratedProgram());
                    openSearch.putExtra("sap",school.getSAPSchool());
                    openSearch.putExtra("zoneCode",school.getZoneCode());
                    openSearch.putExtra("clusterCode",school.getClusterCode());
                    openSearch.putExtra("website",school.getHomePageAddress());
                    openSearch.putExtra("longitude",school.getLongitude());
                    openSearch.putExtra("latitude",school.getLatitude());
                    startActivity(openSearch);
                }
            });
        }
    }
}