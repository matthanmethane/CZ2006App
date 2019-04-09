package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.testapp.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Search education level view.
 */
public class SearchLevelView extends AppCompatActivity {
    /**
     * Return to the previous page when the back button in the header is selected.
     * @param item header menu button
     * @return true
     */
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

    /**
     * Generate the UI display of the search on the education level.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_level_view);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Choose School Level(Step 1 of 4)");

        Button primaryBtn = findViewById(R.id.primary);
        Button secondaryBtn = findViewById(R.id.secondary);
        Button jcBtn = findViewById(R.id.jc);

        primaryBtn.getBackground().setAlpha(80);
        secondaryBtn.getBackground().setAlpha(80);
        jcBtn.getBackground().setAlpha(80);

        primaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openPri = new Intent(getApplicationContext(), SetAddressView.class);
                openPri.putExtra("schoolLevel", 1);
                startActivity(openPri);
            }
        });

        secondaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSec = new Intent(getApplicationContext(), SetAddressView.class);
                openSec.putExtra("schoolLevel", 2);
                startActivity(openSec);
            }
        });

        jcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openJc = new Intent(getApplicationContext(), SetAddressView.class);
                openJc.putExtra("schoolLevel", 3);
                startActivity(openJc);
            }
        });
    }
}