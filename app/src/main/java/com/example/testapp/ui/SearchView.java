package com.example.testapp.ui;

import android.os.Bundle;

import com.example.testapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class SearchView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        
        // put schoolLevel in args (and set args for each fragment)
        int schoolLevel = getIntent().getIntExtra("schoolLevel", -1);
        Bundle args = new Bundle();
        args.putInt("schoolLevel", schoolLevel);

        // Add school list fragment if this is first creation (subsequent creations will have it inside savedInstanceState)
        if (savedInstanceState == null) {
            SchoolListFragment schoolListFragment = new SchoolListFragment();
            schoolListFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, schoolListFragment, SchoolListFragment.TAG).commit();

            // add the cca search fragment
            CCASearchFragment ccaSearchFragment = new CCASearchFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, ccaSearchFragment).commit();

            // add the course search fragment
            CourseSearchFragment courseSearchFragment = new CourseSearchFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, courseSearchFragment).commit();
        }
    }
}
