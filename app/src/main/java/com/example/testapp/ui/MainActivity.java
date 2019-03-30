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

        Button searchBtn = findViewById(R.id.search);
        Button bookmarkBtn = findViewById(R.id.bookmark);
        Button articlBtn = findViewById(R.id.article);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSearch = new Intent(getApplicationContext(), SearchLevelView.class);
                startActivity(openSearch);
            }
        });
        /*openBookmark to be implemented later
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openBookmark = new Intent(getApplicationContext(),#PUT_ACTIVITY_NAME);
                startActivity();
            }
        });*/
        articlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSetting = new Intent(getApplicationContext(), ArticleView.class);
                startActivity(openSetting);
            }
        });
    }
}