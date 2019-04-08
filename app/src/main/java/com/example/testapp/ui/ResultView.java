package com.example.testapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

/**
 * Result view.
 * Display the schools that matches the search result inserted by the user.
 */
public class ResultView extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Search Result");

        Intent intent = getIntent();
        int schoolLevel = intent.getIntExtra("schoolLevel",-1);
        String address = intent.getStringExtra("address");
        String[] courses = intent.getStringArrayExtra("courses");
        String[] ccas = intent.getStringArrayExtra("ccas");

        // Display school results
        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();

        // Initialize viewModel and sort schools
        final ResultViewModel viewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        viewModel.setCcas(ccas);
        viewModel.setCourses(courses);
        viewModel.setSchoolLevel(schoolLevel);
        viewModel.setRepo(((EmpathyApp)this.getApplication()).getRepository());

        List<SchoolEntity> sorted = viewModel.sortSchools();
        displaySchool(sorted);

        // Filter button
        Spinner spinner = findViewById(R.id.filterList);
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
                sorted = viewModel.sortSchools();
                displaySchool(sorted);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button mapResultButton = findViewById(R.id.mapResultButton);
        mapResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent openMap = new Intent(getApplicationContext(),ResultMapView.class);
                openMap.putExtra("schoolLevel",schoolLevel);
                openMap.putExtra("address",address);
                openMap.putExtra("courses",courses);
                openMap.putExtra("ccas",ccas);
                startActivity(openMap);
            }
        });

        // Search by school name
        Button schoolNameButton = findViewById(R.id.schoolNameButton);
        schoolNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText schoolNameInput = findViewById(R.id.schoolNameInput);
                String name = schoolNameInput.getText().toString();

                viewModel.setSchoolName(name);
                displaySchool(viewModel.sortSchools());
            }
        });
    }

    /**
     * Given the ordered list of schools, the schools are display onto the UI.
     * @param schools list of schools
     */
    public void displaySchool(List<SchoolEntity> schools) {
        Intent intent = getIntent();
        int schoolLevel = intent.getIntExtra("schoolLevel",-1);
        DataRepository dataRepository = ((EmpathyApp) getApplication()).getRepository();
        LinearLayout schoolList = findViewById(R.id.schoolList);
        if(schoolList.getChildCount() > 0)
            schoolList.removeAllViews();

        for (int i = 0; i < schools.size(); i++) {
            RelativeLayout box = new RelativeLayout(this);

            RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            RelativeLayout.LayoutParams LayoutParamsButton = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            RelativeLayout.LayoutParams LayoutParamsText = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            LayoutParamsButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            LayoutParamsText.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            box.setPadding(20,0,20,0);
            box.setLayoutParams(layoutparams);

            //box.setOrientation(LinearLayout.HORIZONTAL);
            TextView schoolNameTextView = new TextView(this);
            schoolNameTextView.setText(schools.get(i).getSchoolName());
            schoolNameTextView.setTextColor(Color.BLACK);
            schoolNameTextView.setTextSize(15);
            schoolNameTextView.setLayoutParams(LayoutParamsText);

            SchoolEntity school = schools.get(i);

            CheckBox button = new CheckBox(this,null,android.R.attr.starStyle);
            if (dataRepository.isBookmark(school.getSchoolName())) {
                button.setChecked(true);
            }
            button.setLayoutParams(LayoutParamsButton);

            box.addView(schoolNameTextView);
            box.addView(button);
            schoolList.addView(box);

            schoolNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent openSearch = new Intent(getApplicationContext(), InformationView.class);
                    // Insert school info here
                    openSearch.putExtra("schoolLevel",schoolLevel);
                    openSearch.putExtra("name", school.getSchoolName());
                    openSearch.putExtra("address", school.getPhysicalAddress());
                    openSearch.putExtra("postalCode", school.getPostalCode());
                    openSearch.putExtra("telephoneNumber1", school.getTelephoneNumber1());
                    openSearch.putExtra("telephoneNumber2", school.getTelephoneNumber2());
                    openSearch.putExtra("vision", school.getVision());
                    openSearch.putExtra("mission", school.getMission());
                    openSearch.putExtra("autonomyType", school.getSchoolAutonomyType());
                    openSearch.putExtra("gender", school.getSchoolGender());
                    openSearch.putExtra("giftedEducation", school.getGiftedEducationProgramOffered());
                    openSearch.putExtra("integratedProgram", school.getIntegratedProgram());
                    openSearch.putExtra("sap", school.getSAPSchool());
                    openSearch.putExtra("zoneCode", school.getZoneCode());
                    openSearch.putExtra("clusterCode", school.getClusterCode());
                    openSearch.putExtra("website", school.getHomePageAddress());
                    openSearch.putExtra("longitude", school.getLongitude());
                    openSearch.putExtra("latitude", school.getLatitude());
                    startActivity(openSearch);
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (button.isChecked()) {
                        dataRepository.addNewBookmark(school.getSchoolName());
                    } else {
                        dataRepository.deleteBookmark(school.getSchoolName());
                    }
                }
            });
        }
    }
}