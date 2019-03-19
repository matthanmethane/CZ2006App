package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testapp.R;

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

        Button searchBtn =  findViewById(R.id.search);
        Button bookmarkBtn = findViewById(R.id.bookmark);
        Button settingBtn = findViewById(R.id.setting);

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent openSearch = new Intent(getApplicationContext(), searchLevel.class);
                startActivity(openSearch);
            }
        });
    }
}