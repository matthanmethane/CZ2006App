package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class CcaView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cca_view);

        Intent intent = getIntent();
        String schoolLevel = intent.getStringExtra("schoolLevel");
        String address = intent.getStringExtra("address");

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSearch = new Intent(getApplicationContext(),SearchView.class);
                openSearch.putExtra("schoolLevel",schoolLevel);
                openSearch.putExtra("address",address);
                startActivity(openSearch);
            }
        });

    }
}
