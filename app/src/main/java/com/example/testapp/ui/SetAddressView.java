package com.example.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testapp.R;

import org.w3c.dom.Text;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SetAddressView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address_view);

        Intent intent = getIntent();
        int schoolLevel = intent.getIntExtra("schoolLevel", -1);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Set Address");

        EditText setAddress = (EditText) findViewById(R.id.set_address);
        Button submitAddress = (Button) findViewById(R.id.submit_address);

        submitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Need to validate address
                String address = setAddress.getText().toString();
                if (address.length() != 6 | !address.matches("[0-9]+")) {
                    TextView text = findViewById(R.id.errorMsg);
                    text.setText("Please input valid postal code.");
                } else {
                    // TO DO: Check whether valid postal code

                    Intent openSubject = new Intent(getApplicationContext(), SubjectView.class);
                    openSubject.putExtra("schoolLevel", schoolLevel);
                    openSubject.putExtra("address", address);
                    startActivity(openSubject);
                }
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