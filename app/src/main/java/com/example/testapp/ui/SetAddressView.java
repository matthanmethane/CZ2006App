package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testapp.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SetAddressView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address_view);

        Intent intent = getIntent();
        String schoolLevel = intent.getStringExtra("schoolLevel");

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Set Address");

        EditText setAddress = (EditText) findViewById(R.id.set_address);
        Button submitAddress = (Button) findViewById(R.id.submit_address);

        submitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSubject = new Intent(getApplicationContext(),SubjectView.class);
                openSubject.putExtra("schoolLevel",schoolLevel);
                openSubject.putExtra("address",setAddress.getText().toString());
                startActivity(openSubject);
            }
        });


    }

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
}
