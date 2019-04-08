package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testapp.DataRepository;
import com.example.testapp.EmpathyApp;
import com.example.testapp.R;
import com.example.testapp.db.entity.SchoolEntity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
        Button newsBtn = findViewById(R.id.news);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSearch = new Intent(getApplicationContext(), SearchLevelView.class);
                startActivity(openSearch);
            }
        });

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String token = "yJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjI1NTcsInVzZXJfaWQiOjI1NTcsImVtYWlsIjoiamVzc2x5bi5jaGV3LnMueUBnbWFpbC5jb20iLCJmb3JldmVyIjpmYWxzZSwiaXNzIjoiaHR0cDpcL1wvb20yLmRmZS5vbmVtYXAuc2dcL2FwaVwvdjJcL3VzZXJcL3Nlc3Npb24iLCJpYXQiOjE1NTQ2NTkyODMsImV4cCI6MTU1NTA5MTI4MywibmJmIjoxNTU0NjU5MjgzLCJqdGkiOiI1OGE2MmQ4ZDNjMGRjYmEwZTg0NmNlZmE3MzcwZWVlZSJ9.tXWjnCJ4E78eo3CPAKc2XKvWBdy8sACjtnmSEK3tT-0";
                try {
                    URL url = new URL("https://developers.onemap.sg/privateapi/auth/post/getToken");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("cache-control", "no-cache");
                    conn.setRequestProperty("content-type","multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

                    Map<String,String> arguments = new HashMap<>();
                    arguments.put("email", "jesslyn.chew.s.y@gmail.com");
                    arguments.put("password", "zAch1pam");


                } catch (Exception e) {
                    System.out.println("Exception: " +e.getMessage());
                }*/
                Intent openBookmark = new Intent(getApplicationContext(), BookmarkView.class);
                startActivity(openBookmark);
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openArticle = new Intent(getApplicationContext(), ArticleView.class);
                startActivity(openArticle);
            }
        });
    }
}