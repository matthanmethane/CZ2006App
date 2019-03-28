package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class SubjectView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Intent intent = getIntent();
        String schoolLevel = intent.getStringExtra("schoolLevel");
        String address = intent.getStringExtra("address");

        Button submitSubject = (Button) findViewById(R.id.submit_subject);
        submitSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCca = new Intent(getApplicationContext(), CcaView.class);
                openCca.putExtra("schoolLevel",schoolLevel);
                openCca.putExtra("address",address);
                startActivity(openCca);
            }
        });


    }
}
