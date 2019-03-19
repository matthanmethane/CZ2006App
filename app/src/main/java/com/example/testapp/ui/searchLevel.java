package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testapp.R;
import com.example.testapp.searchView;

import androidx.appcompat.app.AppCompatActivity;

public class searchLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_level);

        Button primaryBtn = findViewById(R.id.primary);
        Button secondaryBtn = findViewById(R.id.secondary);
        Button jcBtn = findViewById(R.id.jc);

        primaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openPri = new Intent(getApplicationContext(), searchView.class);
                startActivity(openPri);
            }
        });
        secondaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSec = new Intent(getApplicationContext(),searchView.class);
                startActivity(openSec);
            }
        });
        jcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openJc = new Intent(getApplicationContext(),searchView.class);
                startActivity(openJc);
            }
        });

    }
}
