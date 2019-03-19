package com.example.testapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp.R;

import androidx.annotation.Nullable;

/**
 * The Main activity.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add school list fragment if this is first creation (subsequent creations will have it inside savedInstanceState)
        if (savedInstanceState == null) {
            SchoolListFragment fragment = new SchoolListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, SchoolListFragment.TAG).commit();
        }
    }
}