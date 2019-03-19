package com.example.testapp;

import android.os.Bundle;

import com.example.testapp.ui.SchoolListFragment;

import androidx.appcompat.app.AppCompatActivity;

public class searchView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);


        // Add school list fragment if this is first creation (subsequent creations will have it inside savedInstanceState)
        if (savedInstanceState == null) {
            SchoolListFragment fragment = new SchoolListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, SchoolListFragment.TAG).commit();
        }
    }
}
